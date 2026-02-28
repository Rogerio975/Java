# Supermercado (Java + PostgreSQL)

Este projeto demonstra como criar o banco de dados `Supermercado` e a tabela `Produtos` usando Java e PostgreSQL.

Requisitos:
- PostgreSQL em execução.
- Driver JDBC do PostgreSQL (ex.: `postgresql-<versao>.jar`) colocado em `lib/` ou disponível no classpath.
- Java 8+

Execução rápida (linha de comando):

1. Coloque o driver JDBC em `lib/` ou adicione ao classpath.

2. Compile:

```bash
javac -cp lib/postgresql.jar -d bin src\*.java
```

3. Execute (substitua credenciais se necessário):

```bash
java -cp "bin;lib/postgresql.jar" App localhost 5432 postgres mypassword Supermercado
```

Parâmetros do `main` (opcionais): `host port user password dbName`.

Alternativamente, use o script SQL em `sql/create_supermercado.sql` com o `psql`:

```bash
psql -U postgres -f sql/create_supermercado.sql
```
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
