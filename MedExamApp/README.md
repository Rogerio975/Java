# MedExam — Sistema de Agendamento de Exames Médicos

Aplicativo desktop Java com interface gráfica JavaFX para gerenciar pacientes e agendar exames médicos.

---

## Pré-requisitos

| Ferramenta | Versão Mínima | Download |
|---|---|---|
| JDK | 21 (LTS) | https://adoptium.net |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| VS Code | Qualquer | https://code.visualstudio.com |

### Extensões VS Code recomendadas

Instale o **Extension Pack for Java** da Microsoft:
```
Ctrl+P → ext install vscjava.vscode-java-pack
```
Isso instala: Language Support for Java, Debugger for Java, Maven for Java, e outras.

---

## Como executar

### Opção 1 — Terminal integrado do VS Code (recomendado)

1. Abra a pasta `MedExamApp` no VS Code:
   ```
   File → Open Folder → selecione a pasta MedExamApp
   ```

2. Abra o terminal integrado:
   ```
   Ctrl + `  (acento grave)
   ```

3. Execute o aplicativo:
   ```bash
   mvn javafx:run
   ```

Na primeira execução o Maven vai baixar as dependências automaticamente (~50 MB).

---

### Opção 2 — Botão Run do VS Code

Com o Extension Pack instalado, abra `App.java` e clique em **▶ Run** que aparece acima do método `main`.

> ⚠️ Para que este método funcione corretamente com JavaFX, pode ser necessário configurar os argumentos da JVM (veja a seção abaixo).

---

### Configuração de launch.json (se necessário)

Se o VS Code reclamar de módulos JavaFX faltando ao usar o botão Run, crie `.vscode/launch.json` na raiz do projeto:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "MedExam",
            "request": "launch",
            "mainClass": "com.medexam.App",
            "vmArgs": "--module-path ${env:PATH_TO_FX} --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

> Com Maven (`mvn javafx:run`), isso **não é necessário** — o plugin cuida de tudo automaticamente.

---

## Estrutura do projeto

```
MedExamApp/
├── pom.xml                          ← Configuração Maven + dependências
└── src/
    └── main/
        ├── java/
        │   ├── module-info.java     ← Declaração do módulo Java
        │   └── com/medexam/
        │       ├── App.java         ← Ponto de entrada (main)
        │       ├── model/
        │       │   ├── Paciente.java
        │       │   └── Exame.java
        │       ├── service/
        │       │   └── ClinicaService.java  ← Lógica de negócio + dados em memória
        │       └── ui/
        │           ├── MainWindow.java      ← Layout principal com sidebar
        │           ├── DashboardPanel.java  ← Tela de visão geral
        │           ├── PacientesPanel.java  ← CRUD de pacientes
        │           └── ExamesPanel.java     ← Agendamento e gestão de exames
        └── resources/
            └── styles.css           ← Tema visual do aplicativo
```

---

## Funcionalidades

### Dashboard
- Contadores de pacientes e exames por status (Agendado / Concluído / Cancelado)
- Lista dos próximos exames agendados

### Pacientes
- Cadastrar novo paciente (nome, CPF, data de nascimento, telefone, e-mail)
- Editar dados de pacientes existentes
- Excluir paciente (com confirmação)
- Buscar por nome ou CPF em tempo real
- Validação de CPF duplicado

### Exames
- Agendar exame para um paciente (tipo, data, hora, médico solicitante, observações)
- Editar agendamento existente
- Marcar exame como Concluído
- Cancelar exame (com confirmação)
- Filtrar lista por status

---

## Dados de demonstração

O sistema inicia com 3 pacientes e 3 exames cadastrados para facilitar os testes.

---

## Notas técnicas

- **Persistência**: todos os dados ficam em memória (RAM). Ao fechar o app, os dados são perdidos.
- **Java**: usa records, switch expressions e outros recursos do Java 17+.
- **Módulo Java**: o projeto usa o sistema de módulos (`module-info.java`) exigido pelo JavaFX 11+.
