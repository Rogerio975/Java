# 🔍 Active Directory Search — Java

Ferramenta de busca de usuários no **Active Directory** via protocolo **LDAP/LDAPS**.  
Sem dependências externas — usa apenas o `javax.naming` do JDK padrão.

---

## 📋 Pré-requisitos

| Requisito  | Versão mínima |
|------------|---------------|
| Java (JDK) | 11+           |
| Maven      | 3.6+ *(para build)* |
| Rede       | Acesso ao Domain Controller na porta 389 (ou 636 para SSL) |

---

## ⚙️ Configuração

### 1. Edite o arquivo `ad-config.properties`

```properties
ad.host=dc01.empresa.local        # IP ou FQDN do Domain Controller
ad.port=389                       # 389 = LDAP | 636 = LDAPS
ad.searchBase=DC=empresa,DC=local # Base DN do seu domínio
ad.bindDn=svc-ldap@empresa.local  # Conta de serviço (UPN ou DN completo)
ad.bindPassword=SuaSenha          # Senha da conta de serviço
ad.ssl=false                      # true para LDAPS
ad.maxResults=20                  # Máximo de resultados por busca
```

### 2. Alternativa: variáveis de ambiente

```bash
export AD_HOST=dc01.empresa.local
export AD_PORT=389
export AD_BASE="DC=empresa,DC=local"
export AD_BIND_DN="svc-ldap@empresa.local"
export AD_PASSWORD="SuaSenha"
```

---

## 🚀 Build e Execução

```bash
# 1. Compilar e empacotar
mvn clean package

# 2. Modo interativo (menu de busca)
java -jar target/ad-search.jar

# 3. Busca direta por nome, login ou e-mail
java -jar target/ad-search.jar "joao silva"
java -jar target/ad-search.jar joao.silva

# 4. Busca exata por login (sAMAccountName)
java -jar target/ad-search.jar --login joao.silva
```

---

## 🔎 Campos pesquisados

A busca é feita em paralelo nos seguintes atributos LDAP:

| Atributo AD       | Descrição          |
|-------------------|--------------------|
| `sAMAccountName`  | Login do Windows   |
| `cn`              | Nome completo      |
| `displayName`     | Nome de exibição   |
| `mail`            | E-mail             |
| `givenName`       | Primeiro nome      |
| `sn`              | Sobrenome          |

---

## 📄 Dados retornados

Para cada usuário encontrado são exibidos:

- Login (`sAMAccountName`)
- Nome completo e nome de exibição
- E-mail e telefone
- Cargo, departamento, empresa e escritório
- Gerente (extraído do DN)
- Status da conta (ativo / desabilitado)
- Último logon (timestamp Windows convertido)
- Data de criação da conta
- Lista de grupos
- Distinguished Name (DN) completo

---

## 🔐 Conta de serviço — Permissões mínimas

A conta de bind precisa apenas de permissão de **leitura** no AD.

No Active Directory Users and Computers:
1. Crie um usuário de serviço (ex.: `svc-ldap`)
2. Adicione ao grupo **"Read-only Domain Controllers"** ou conceda permissão de leitura na OU desejada
3. Marque **"Password never expires"** para conta de serviço

---

## 🔒 LDAPS (SSL/TLS) — Configuração

Para usar LDAPS (`ad.ssl=true`, porta 636), o certificado do DC deve ser confiado pelo Java:

```bash
# Exportar certificado do DC (execute no servidor Windows)
certutil -exportPFX -p "" "Nome do Certificado" dc-cert.cer

# Importar no truststore Java
keytool -import -alias ad-cert -keystore $JAVA_HOME/lib/security/cacerts \
        -file dc-cert.cer -storepass changeit -noprompt
```

---

## 🗂️ Estrutura do projeto

```
ad-search/
├── pom.xml
├── ad-config.properties          ← configuração (não versionar!)
└── src/main/java/com/adsearch/
    ├── Main.java                 ← CLI interativo
    ├── ActiveDirectorySearch.java← lógica LDAP
    ├── ADUser.java               ← modelo do usuário
    └── ADConfig.java             ← carregamento de configuração
```

---

## ⚠️ Segurança

- **Nunca** versione o `ad-config.properties` com senhas no Git — adicione ao `.gitignore`
- Prefira **variáveis de ambiente** em pipelines CI/CD
- Use **LDAPS** em produção (criptografa as credenciais em trânsito)
- Use uma conta de serviço com **permissões mínimas** (somente leitura)
