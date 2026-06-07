package com.adsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um usuário do Active Directory.
 */
public class ADUser {

    public String  sAMAccountName;   // Login
    public String  cn;               // Nome completo (LDAP)
    public String  displayName;      // Nome de exibição
    public String  givenName;        // Primeiro nome
    public String  sn;               // Sobrenome
    public String  mail;             // E-mail
    public String  telephone;        // Telefone
    public String  department;       // Departamento
    public String  title;            // Cargo
    public String  company;          // Empresa
    public String  office;           // Escritório
    public String  manager;          // Nome do gerente
    public String  lastLogon;        // Último logon (formatado)
    public String  whenCreated;      // Data de criação
    public String  distinguishedName;// DN completo
    public boolean enabled;          // Conta habilitada?
    public List<String> groups = new ArrayList<>(); // Grupos

    /** Resumo de uma linha para listagem */
    public String toSummary() {
        String status = enabled ? "✔ Ativo" : "✘ Inativo";
        String name   = displayName != null && !displayName.isEmpty() ? displayName : cn;
        String dept   = department  != null && !department.isEmpty()  ? " | " + department : "";
        return String.format("%-20s %-35s %-30s %s%s",
            sAMAccountName, name, mail, status, dept);
    }

    /** Exibição detalhada */
    public String toDetail() {
        StringBuilder sb = new StringBuilder();
        String sep = "─".repeat(60);

        sb.append("\n").append(sep).append("\n");
        sb.append(center("DADOS DO USUÁRIO", 60)).append("\n");
        sb.append(sep).append("\n");

        field(sb, "Login",        sAMAccountName);
        field(sb, "Nome",         displayName.isEmpty() ? cn : displayName);
        field(sb, "Primeiro nome",givenName);
        field(sb, "Sobrenome",    sn);
        field(sb, "E-mail",       mail);
        field(sb, "Telefone",     telephone);
        field(sb, "Cargo",        title);
        field(sb, "Departamento", department);
        field(sb, "Empresa",      company);
        field(sb, "Escritório",   office);
        field(sb, "Gerente",      manager);
        field(sb, "Status",       enabled ? "✔ Conta ativa" : "✘ Conta desabilitada");
        field(sb, "Último logon", lastLogon);
        field(sb, "Criado em",    formatDate(whenCreated));

        if (!groups.isEmpty()) {
            sb.append("\n  Grupos (").append(groups.size()).append("):\n");
            groups.forEach(g -> sb.append("    • ").append(g).append("\n"));
        }

        sb.append("\n  DN: ").append(distinguishedName).append("\n");
        sb.append(sep).append("\n");
        return sb.toString();
    }

    private void field(StringBuilder sb, String label, String value) {
        if (value != null && !value.isEmpty()) {
            sb.append(String.format("  %-16s: %s%n", label, value));
        }
    }

    private String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + text;
    }

    /** Converte data do AD (yyyyMMddHHmmss.0Z) para dd/MM/yyyy */
    private String formatDate(String raw) {
        if (raw == null || raw.length() < 8) return raw;
        try {
            // Formato AD: 20240115143022.0Z
            String year  = raw.substring(0, 4);
            String month = raw.substring(4, 6);
            String day   = raw.substring(6, 8);
            return day + "/" + month + "/" + year;
        } catch (Exception e) {
            return raw;
        }
    }
}
