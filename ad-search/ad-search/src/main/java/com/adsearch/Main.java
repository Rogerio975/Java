package com.adsearch;

import javax.naming.NamingException;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interface de linha de comando para busca de usuários no Active Directory.
 *
 * Uso:
 *   java -jar ad-search.jar                    → modo interativo
 *   java -jar ad-search.jar "joao.silva"       → busca direta
 *   java -jar ad-search.jar --login joao.silva → busca exata por login
 */
public class Main {

    private static final String BANNER =
        "\n" +
        "  ╔═══════════════════════════════════════════╗\n" +
        "  ║     🔍  Busca Active Directory  v1.0      ║\n" +
        "  ╚═══════════════════════════════════════════╝\n";

    public static void main(String[] args) {
        System.out.println(BANNER);

        // ── Carrega configuração ──────────────────────────────────────────────
        ADConfig config;
        try {
            // Tenta arquivo externo primeiro, depois classpath
            File externalConfig = new File("ad-config.properties");
            if (externalConfig.exists()) {
                config = ADConfig.fromFile(externalConfig.getPath());
                System.out.println("  ✔ Configuração carregada: " + externalConfig.getAbsolutePath());
            } else {
                config = ADConfig.fromClasspath();
                System.out.println("  ✔ Configuração carregada do classpath.");
            }
        } catch (IOException e) {
            System.err.println("  ✘ Erro ao carregar configuração: " + e.getMessage());
            System.err.println("  → Crie o arquivo ad-config.properties (veja o modelo incluído).");
            System.exit(1);
            return;
        }

        System.out.println("  Servidor: " + config);

        // ── Instancia o serviço de busca ──────────────────────────────────────
        ActiveDirectorySearch adSearch = new ActiveDirectorySearch(
            config.host, config.port, config.searchBase,
            config.bindDn, config.bindPassword, config.useSsl
        );

        // ── Teste de conexão ──────────────────────────────────────────────────
        System.out.print("\n  Testando conexão com o AD... ");
        if (!adSearch.testConnection()) {
            System.out.println("FALHOU");
            System.err.println("  ✘ Não foi possível conectar ao AD. Verifique as configurações.");
            System.exit(2);
        }
        System.out.println("OK ✔");

        // ── Modo argumento de linha de comando ────────────────────────────────
        if (args.length > 0) {
            handleArgs(args, adSearch, config);
            return;
        }

        // ── Modo interativo ───────────────────────────────────────────────────
        interactiveMode(adSearch, config);
    }

    // ─── Busca via argumento ──────────────────────────────────────────────────
    private static void handleArgs(String[] args, ActiveDirectorySearch adSearch, ADConfig config) {
        if ("--login".equals(args[0]) && args.length > 1) {
            // Busca exata por login
            String login = args[1];
            System.out.println("\n  Buscando login exato: " + login);
            try {
                Optional<ADUser> user = adSearch.findByLogin(login);
                if (user.isPresent()) {
                    System.out.println(user.get().toDetail());
                } else {
                    System.out.println("  Nenhum usuário encontrado com login: " + login);
                }
            } catch (NamingException e) {
                System.err.println("  ✘ Erro na busca: " + e.getMessage());
            }
        } else {
            // Busca geral pelo primeiro argumento
            String term = String.join(" ", args);
            performSearch(term, adSearch, config);
        }
    }

    // ─── Modo interativo ──────────────────────────────────────────────────────
    private static void interactiveMode(ActiveDirectorySearch adSearch, ADConfig config) {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.print("\n  🔍  Digite nome, login ou e-mail (ou 'sair'): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            if ("sair".equalsIgnoreCase(input) || "exit".equalsIgnoreCase(input)) {
                System.out.println("\n  Encerrando. Até logo!\n");
                break;
            }

            if ("ajuda".equalsIgnoreCase(input) || "help".equalsIgnoreCase(input)) {
                printHelp();
                continue;
            }

            performSearch(input, adSearch, config);
        }

        scanner.close();
    }

    // ─── Executa a busca e exibe resultados ───────────────────────────────────
    private static void performSearch(String term, ActiveDirectorySearch adSearch, ADConfig config) {
        System.out.println("\n  Buscando por: \"" + term + "\" (máx. " + config.maxResults + " resultados)...");

        try {
            List<ADUser> users = adSearch.search(term, config.maxResults);

            if (users.isEmpty()) {
                System.out.println("  Nenhum usuário encontrado.");
                return;
            }

            System.out.println("\n  " + users.size() + " resultado(s) encontrado(s):\n");
            System.out.printf("  %-20s %-35s %-30s %s%n",
                "LOGIN", "NOME", "E-MAIL", "STATUS");
            System.out.println("  " + "─".repeat(100));

            for (int i = 0; i < users.size(); i++) {
                System.out.println("  [" + (i + 1) + "] " + users.get(i).toSummary());
            }

            // Permite ver detalhes de um resultado
            if (users.size() > 1) {
                System.out.print("\n  Ver detalhes? Digite o número ou Enter para continuar: ");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine().trim();
                if (!choice.isEmpty()) {
                    try {
                        int idx = Integer.parseInt(choice) - 1;
                        if (idx >= 0 && idx < users.size()) {
                            System.out.println(users.get(idx).toDetail());
                        } else {
                            System.out.println("  Número inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("  Entrada inválida.");
                    }
                }
            } else {
                // Único resultado: exibe detalhes automaticamente
                System.out.println(users.get(0).toDetail());
            }

        } catch (NamingException e) {
            System.err.println("  ✘ Erro na busca: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("     Causa: " + e.getCause().getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("""

  ─────────────────────────────────────────────
  COMO USAR:
    • Digite parte do nome  → ex.: joao, silva
    • Digite o login        → ex.: joao.silva
    • Digite o e-mail       → ex.: joao@empresa.com
    • Digite 'ajuda'        → exibe esta mensagem
    • Digite 'sair'         → encerra o programa
  ─────────────────────────────────────────────""");
    }
}
