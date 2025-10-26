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

## Exemplo: gerar e abrir um arquivo HTML a partir do Java

O projeto contém `src/App.java` — um pequeno programa que cria `output.html` com conteúdo HTML de exemplo e tenta abri-lo no navegador padrão (Windows).

Como compilar e executar (PowerShell):

```powershell
# Compila e coloca as classes na pasta `out`
javac -d out src\App.java

# Executa a classe principal a partir da pasta `out`
java -cp out App
```

Se a API `Desktop` não for suportada no seu ambiente, abra manualmente o arquivo `output.html` gerado.
