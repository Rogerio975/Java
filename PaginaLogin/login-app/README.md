# Login App

Este projeto é uma aplicação de login simples desenvolvida em Java. A aplicação permite que os usuários se autentiquem utilizando um nome de usuário e uma senha.

## Estrutura do Projeto

O projeto possui a seguinte estrutura de diretórios:

```
login-app
├── src
│   ├── Main.java               # Ponto de entrada da aplicação
│   ├── controller
│   │   └── LoginController.java # Lógica de autenticação
│   ├── model
│   │   └── User.java           # Representação do usuário
│   └── view
│       └── LoginView.java      # Interface do usuário
├── README.md                   # Documentação do projeto
└── pom.xml                     # Configuração do Maven
```

## Instruções de Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/seu_usuario/login-app.git
   ```

2. Navegue até o diretório do projeto:
   ```
   cd login-app
   ```

3. Compile o projeto usando Maven:
   ```
   mvn clean install
   ```

## Uso

Para executar a aplicação, utilize o seguinte comando:
```
mvn exec:java -Dexec.mainClass="Main"
```

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou relatar problemas.

## Licença

Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.