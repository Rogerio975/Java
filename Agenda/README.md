# Agendador

Aplicacao de console para criar, listar e cancelar compromissos, com validacao de horarios conflitantes.

## Requisitos

- JDK 17 ou superior

## Compilar

No PowerShell, a partir da raiz do projeto:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src\main\java).FullName
```

## Executar

```powershell
java -cp out br.com.agenda.AgendaApplication
```

As datas devem ser informadas no formato `dd/MM/yyyy HH:mm`.
