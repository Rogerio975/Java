# Login App

Este projeto é uma aplicação de login simples desenvolvida em Java. A aplicação utiliza JavaFX para a interface gráfica e segue o padrão MVC (Model-View-Controller).

## Estrutura do Projeto

O projeto possui a seguinte estrutura de diretórios:

```
login-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── LoginApp.java          # Ponto de entrada da aplicação
│   │   │           ├── controller
│   │   │           │   └── LoginController.java # Lógica de autenticação
│   │   │           └── model
│   │   │               └── User.java           # Representação do usuário
│   │   └── resources
│   │       └── login.fxml                       # Interface gráfica
├── pom.xml                                       # Configuração do Maven
└── README.md                                     # Documentação do projeto
```

## Pré-requisitos

- JDK 11 ou superior
- Maven

## Como Executar

1. Clone o repositório:
   ```
   git clone <URL_DO_REPOSITORIO>
   cd login-app
   ```

2. Compile o projeto usando Maven:
   ```
   mvn clean install
   ```

3. Execute a aplicação:
   ```
   mvn javafx:run
   ```

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções. Abra um pull request ou crie uma issue para discutir mudanças.

## Licença

Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.