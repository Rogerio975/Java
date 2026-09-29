package br.com.agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class AgendaApplication {
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Agendador agendador = new Agendador();

    private AgendaApplication() {
    }

    public static void main(String[] args) {
        boolean executando = true;
        System.out.println("=== Agendador ===");

        while (executando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();
            try {
                executando = executarOpcao(opcao);
            } catch (IllegalArgumentException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        }

        System.out.println("Agenda encerrada.");
    }

    private static void exibirMenu() {
        System.out.println("\n1 - Agendar compromisso");
        System.out.println("2 - Listar compromissos");
        System.out.println("3 - Cancelar compromisso");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static boolean executarOpcao(String opcao) {
        switch (opcao) {
            case "1" -> agendarCompromisso();
            case "2" -> listarCompromissos();
            case "3" -> cancelarCompromisso();
            case "0" -> { return false; }
            default -> System.out.println("Opcao invalida.");
        }
        return true;
    }

    private static void agendarCompromisso() {
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Descricao (opcional): ");
        String descricao = scanner.nextLine();
        LocalDateTime inicio = lerData("Inicio (dd/MM/yyyy HH:mm): ");
        LocalDateTime fim = lerData("Fim (dd/MM/yyyy HH:mm): ");

        Compromisso compromisso = agendador.agendar(titulo, descricao, inicio, fim);
        System.out.println("Compromisso agendado: " + compromisso);
    }

    private static void listarCompromissos() {
        if (agendador.listar().isEmpty()) {
            System.out.println("Nenhum compromisso cadastrado.");
            return;
        }

        System.out.println("\nCompromissos:");
        agendador.listar().forEach(System.out::println);
    }

    private static void cancelarCompromisso() {
        System.out.print("ID do compromisso: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (agendador.cancelar(id)) {
            System.out.println("Compromisso cancelado.");
        } else {
            System.out.println("Compromisso nao encontrado.");
        }
    }

    private static LocalDateTime lerData(String mensagem) {
        System.out.print(mensagem);
        try {
            return LocalDateTime.parse(scanner.nextLine().trim(), FORMATADOR);
        } catch (DateTimeParseException erro) {
            throw new IllegalArgumentException("Use o formato dd/MM/yyyy HH:mm.");
        }
    }
}
