package com.adsearch;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.*;

/**
 * Busca de usuários no Active Directory via LDAP/LDAPS.
 * Suporta busca por nome completo, login (sAMAccountName) ou e-mail.
 */
public class ActiveDirectorySearch {

    // ─── Configurações de conexão ───────────────────────────────────────────────
    private final String ldapUrl;          // ex.: ldap://dc.empresa.local:389
    private final String searchBase;       // ex.: DC=empresa,DC=local
    private final String bindDn;           // ex.: CN=svc-ldap,OU=Servicos,DC=empresa,DC=local
    private final String bindPassword;
    private final boolean useSsl;

    // Atributos do AD que serão retornados na busca
    private static final String[] AD_ATTRIBUTES = {
        "sAMAccountName",   // Login (ex.: joao.silva)
        "cn",               // Nome completo
        "displayName",      // Nome de exibição
        "givenName",        // Primeiro nome
        "sn",               // Sobrenome
        "mail",             // E-mail
        "telephoneNumber",  // Telefone
        "department",       // Departamento
        "title",            // Cargo
        "company",          // Empresa
        "physicalDeliveryOfficeName", // Escritório
        "manager",          // Gerente (DN)
        "memberOf",         // Grupos
        "userAccountControl", // Status da conta
        "lastLogon",        // Último logon (timestamp Windows)
        "whenCreated",      // Data de criação
        "distinguishedName" // DN completo
    };

    // ─── Construtor ─────────────────────────────────────────────────────────────
    public ActiveDirectorySearch(String host, int port, String searchBase,
                                  String bindDn, String bindPassword, boolean useSsl) {
        String protocol = useSsl ? "ldaps" : "ldap";
        this.ldapUrl    = protocol + "://" + host + ":" + port;
        this.searchBase = searchBase;
        this.bindDn     = bindDn;
        this.bindPassword = bindPassword;
        this.useSsl     = useSsl;
    }

    // ─── Conexão LDAP ───────────────────────────────────────────────────────────
    private LdapContext connect() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL,            ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL,      bindDn);
        env.put(Context.SECURITY_CREDENTIALS,    bindPassword);
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");
        env.put("com.sun.jndi.ldap.read.timeout",    "10000");

        if (useSsl) {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialLdapContext(env, null);
    }

    // ─── Busca principal ────────────────────────────────────────────────────────

    /**
     * Busca usuários pelo termo informado.
     * Pesquisa em: sAMAccountName, cn, displayName, mail.
     *
     * @param term  Nome, login ou e-mail (parcial ou completo)
     * @param limit Número máximo de resultados
     */
    public List<ADUser> search(String term, int limit) throws NamingException {
        LdapContext ctx = connect();
        List<ADUser> results = new ArrayList<>();

        try {
            // Sanitiza o termo para evitar LDAP injection
            String safe = sanitize(term);

            // Filtro OR: qualquer campo que contenha o termo
            String filter = String.format(
                "(&(objectClass=user)(objectCategory=person)" +
                "(|(sAMAccountName=*%s*)(cn=*%s*)(displayName=*%s*)(mail=*%s*)(givenName=*%s*)(sn=*%s*)))",
                safe, safe, safe, safe, safe, safe
            );

            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(AD_ATTRIBUTES);
            controls.setCountLimit(limit);
            controls.setTimeLimit(10_000);

            NamingEnumeration<SearchResult> answer =
                ctx.search(searchBase, filter, controls);

            while (answer.hasMoreElements()) {
                SearchResult sr = answer.nextElement();
                results.add(mapToUser(sr.getAttributes()));
            }
            answer.close();

        } finally {
            ctx.close();
        }

        return results;
    }

    /**
     * Busca exata pelo login (sAMAccountName).
     */
    public Optional<ADUser> findByLogin(String login) throws NamingException {
        LdapContext ctx = connect();
        try {
            String safe   = sanitize(login);
            String filter = "(&(objectClass=user)(objectCategory=person)(sAMAccountName=" + safe + "))";

            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(AD_ATTRIBUTES);
            controls.setCountLimit(1);

            NamingEnumeration<SearchResult> answer =
                ctx.search(searchBase, filter, controls);

            if (answer.hasMoreElements()) {
                ADUser user = mapToUser(answer.nextElement().getAttributes());
                answer.close();
                return Optional.of(user);
            }
            return Optional.empty();

        } finally {
            ctx.close();
        }
    }

    // ─── Mapeamento de atributos ────────────────────────────────────────────────
    private ADUser mapToUser(Attributes attrs) throws NamingException {
        ADUser u = new ADUser();
        u.sAMAccountName = getAttr(attrs, "sAMAccountName");
        u.cn             = getAttr(attrs, "cn");
        u.displayName    = getAttr(attrs, "displayName");
        u.givenName      = getAttr(attrs, "givenName");
        u.sn             = getAttr(attrs, "sn");
        u.mail           = getAttr(attrs, "mail");
        u.telephone      = getAttr(attrs, "telephoneNumber");
        u.department     = getAttr(attrs, "department");
        u.title          = getAttr(attrs, "title");
        u.company        = getAttr(attrs, "company");
        u.office         = getAttr(attrs, "physicalDeliveryOfficeName");
        u.distinguishedName = getAttr(attrs, "distinguishedName");
        u.whenCreated    = getAttr(attrs, "whenCreated");

        // Gerente: extrai apenas o CN do DN
        String managerDn = getAttr(attrs, "manager");
        u.manager = extractCn(managerDn);

        // Último logon: converte timestamp Windows (100ns desde 01/01/1601)
        String lastLogonRaw = getAttr(attrs, "lastLogon");
        u.lastLogon = convertWindowsTimestamp(lastLogonRaw);

        // Status da conta
        String uac = getAttr(attrs, "userAccountControl");
        u.enabled = isAccountEnabled(uac);

        // Grupos: pega apenas os primeiros CNs
        Attribute memberOf = attrs.get("memberOf");
        if (memberOf != null) {
            NamingEnumeration<?> vals = memberOf.getAll();
            while (vals.hasMore()) {
                String groupDn = (String) vals.next();
                u.groups.add(extractCn(groupDn));
            }
            vals.close();
        }

        return u;
    }

    // ─── Utilitários ────────────────────────────────────────────────────────────
    private String getAttr(Attributes attrs, String name) throws NamingException {
        Attribute a = attrs.get(name);
        if (a == null) return "";
        Object val = a.get();
        return val != null ? val.toString() : "";
    }

    /** Remove caracteres especiais LDAP para evitar injection */
    private String sanitize(String input) {
        return input
            .replace("\\", "\\5c")
            .replace("*",  "\\2a")
            .replace("(",  "\\28")
            .replace(")",  "\\29")
            .replace("\0", "\\00");
    }

    /** Extrai o valor do primeiro RDN (ex.: CN=João Silva → João Silva) */
    private String extractCn(String dn) {
        if (dn == null || dn.isEmpty()) return "";
        String[] parts = dn.split(",");
        if (parts.length == 0) return dn;
        String rdn = parts[0];
        int eq = rdn.indexOf('=');
        return eq >= 0 ? rdn.substring(eq + 1).trim() : rdn.trim();
    }

    /** Converte timestamp Windows (100ns intervals desde 1601) para string legível */
    private String convertWindowsTimestamp(String raw) {
        if (raw == null || raw.isEmpty() || raw.equals("0")) return "Nunca";
        try {
            long windowsTime = Long.parseLong(raw);
            // Diferença entre 01/01/1601 e 01/01/1970 em milissegundos
            long javaTime = (windowsTime / 10_000L) - 11_644_473_600_000L;
            if (javaTime <= 0) return "Nunca";
            return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(new Date(javaTime));
        } catch (NumberFormatException e) {
            return raw;
        }
    }

    /** Verifica se a conta está habilitada via userAccountControl */
    private boolean isAccountEnabled(String uac) {
        if (uac == null || uac.isEmpty()) return true;
        try {
            int flags = Integer.parseInt(uac);
            return (flags & 0x2) == 0; // bit 1 = ACCOUNTDISABLE
        } catch (NumberFormatException e) {
            return true;
        }
    }

    // ─── Teste de conexão ────────────────────────────────────────────────────────
    public boolean testConnection() {
        try {
            LdapContext ctx = connect();
            ctx.close();
            return true;
        } catch (NamingException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
            return false;
        }
    }
}
