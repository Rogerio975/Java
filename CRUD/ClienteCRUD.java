import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * CRUD de clientes (cadastrar, listar, buscar, atualizar e apagar).
 *
 * Requer Java 17+. Sem dependências externas.
 *
 * Executar:   java ClienteCRUD.java [caminho/do/arquivo.csv]
 * Ou compilar: javac ClienteCRUD.java && java ClienteCRUD
 *
 * Os dados são gravados em "clientes.csv" (separador ";") no diretório atual.
 */
public class ClienteCRUD {

    public static void main(String[] args) {
        Path arquivo = Path.of(args.length > 0 ? args[0] : "clientes.csv");
        ClienteRepository repositorio = new ArquivoClienteRepository(arquivo);
        ClienteService servico = new ClienteService(repositorio);
        System.out.println("Arquivo de dados: " + arquivo.toAbsolutePath()
                + " (" + servico.listar().size() + " cliente(s) carregado(s))");
        new Menu(servico).executar();
    }
}

/* ======================================================================
 * Modelo
 * ====================================================================== */

record Cliente(int id, String nome, String cpf, String email, String telefone) {

    Cliente {
        nome = Validador.nome(nome);
        cpf = Validador.cpf(cpf);
        email = Validador.email(email);
        telefone = Validador.telefone(telefone);
    }

    Cliente comId(int novoId) {
        return new Cliente(novoId, nome, cpf, email, telefone);
    }

    String cpfFormatado() {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    String telefoneFormatado() {
        int ddd = 2;
        int meio = telefone.length() == 11 ? 7 : 6;
        return "(" + telefone.substring(0, ddd) + ") "
                + telefone.substring(ddd, meio) + "-" + telefone.substring(meio);
    }
}

/* ======================================================================
 * Exceções
 * ====================================================================== */

class ValidacaoException extends RuntimeException {
    ValidacaoException(String mensagem) {
        super(mensagem);
    }
}

class ClienteNaoEncontradoException extends RuntimeException {
    ClienteNaoEncontradoException(int id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}

/* ======================================================================
 * Validação
 * ====================================================================== */

final class Validador {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    private Validador() {
    }

    static String nome(String valor) {
        String v = valor == null ? "" : valor.trim().replaceAll("\\s+", " ");
        if (v.length() < 3 || v.length() > 100) {
            throw new ValidacaoException("Nome deve ter entre 3 e 100 caracteres.");
        }
        if (v.chars().anyMatch(c -> c == ';' || Character.isISOControl(c))) {
            throw new ValidacaoException("Nome contém caracteres inválidos.");
        }
        return v;
    }

    static String email(String valor) {
        String v = valor == null ? "" : valor.trim().toLowerCase();
        if (v.length() > 120 || !EMAIL.matcher(v).matches()) {
            throw new ValidacaoException("E-mail inválido.");
        }
        return v;
    }

    static String telefone(String valor) {
        String v = apenasDigitos(valor);
        if (v.length() != 10 && v.length() != 11) {
            throw new ValidacaoException("Telefone deve ter DDD + 8 ou 9 dígitos.");
        }
        return v;
    }

    static String cpf(String valor) {
        String v = apenasDigitos(valor);
        if (v.length() != 11 || v.chars().distinct().count() == 1) {
            throw new ValidacaoException("CPF inválido.");
        }
        int d1 = digitoVerificador(v, 9);
        int d2 = digitoVerificador(v, 10);
        if (d1 != v.charAt(9) - '0' || d2 != v.charAt(10) - '0') {
            throw new ValidacaoException("CPF inválido.");
        }
        return v;
    }

    private static int digitoVerificador(String cpf, int tamanho) {
        int soma = 0;
        for (int i = 0; i < tamanho; i++) {
            soma += (cpf.charAt(i) - '0') * (tamanho + 1 - i);
        }
        int resto = (soma * 10) % 11;
        return resto == 10 ? 0 : resto;
    }

    static String apenasDigitos(String valor) {
        return valor == null ? "" : valor.replaceAll("\\D", "");
    }
}

/* ======================================================================
 * Persistência
 * ====================================================================== */

interface ClienteRepository {
    List<Cliente> listar();

    Optional<Cliente> buscarPorId(int id);

    Optional<Cliente> buscarPorCpf(String cpf);

    Cliente inserir(Cliente cliente);

    void atualizar(Cliente cliente);

    boolean remover(int id);
}

/** Repositório em arquivo texto (CSV com ";"), com gravação atômica. */
class ArquivoClienteRepository implements ClienteRepository {

    private static final String SEPARADOR = ";";

    private final Path arquivo;
    private final Map<Integer, Cliente> clientes = new TreeMap<>();
    private int proximoId = 1;

    ArquivoClienteRepository(Path arquivo) {
        this.arquivo = arquivo.toAbsolutePath();
        carregar();
    }

    @Override
    public synchronized List<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public synchronized Optional<Cliente> buscarPorId(int id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public synchronized Optional<Cliente> buscarPorCpf(String cpf) {
        return clientes.values().stream().filter(c -> c.cpf().equals(cpf)).findFirst();
    }

    @Override
    public synchronized Cliente inserir(Cliente cliente) {
        Cliente novo = cliente.comId(proximoId++);
        clientes.put(novo.id(), novo);
        persistir();
        return novo;
    }

    @Override
    public synchronized void atualizar(Cliente cliente) {
        if (!clientes.containsKey(cliente.id())) {
            throw new ClienteNaoEncontradoException(cliente.id());
        }
        clientes.put(cliente.id(), cliente);
        persistir();
    }

    @Override
    public synchronized boolean remover(int id) {
        boolean removido = clientes.remove(id) != null;
        if (removido) {
            persistir();
        }
        return removido;
    }

    private void carregar() {
        if (!Files.exists(arquivo)) {
            return;
        }
        try {
            int numeroLinha = 0;
            for (String linha : Files.readAllLines(arquivo, StandardCharsets.UTF_8)) {
                numeroLinha++;
                if (linha.isBlank()) {
                    continue;
                }
                String[] campos = linha.split(SEPARADOR, -1);
                if (campos.length != 5) {
                    throw new IllegalStateException(
                            "Arquivo corrompido na linha " + numeroLinha + ": " + arquivo);
                }
                Cliente c = new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4]);
                clientes.put(c.id(), c);
                proximoId = Math.max(proximoId, c.id() + 1);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao ler " + arquivo, e);
        }
    }

    private void persistir() {
        List<String> linhas = new ArrayList<>();
        for (Cliente c : clientes.values()) {
            linhas.add(String.join(SEPARADOR,
                    String.valueOf(c.id()), c.nome(), c.cpf(), c.email(), c.telefone()));
        }
        try {
            Path temporario = arquivo.resolveSibling(arquivo.getFileName() + ".tmp");
            Files.write(temporario, linhas, StandardCharsets.UTF_8);
            try {
                Files.move(temporario, arquivo,
                        StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao gravar " + arquivo, e);
        }
    }
}

/* ======================================================================
 * Regras de negócio
 * ====================================================================== */

class ClienteService {

    private final ClienteRepository repositorio;

    ClienteService(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    Cliente cadastrar(String nome, String cpf, String email, String telefone) {
        Cliente cliente = new Cliente(0, nome, cpf, email, telefone);
        garantirCpfDisponivel(cliente.cpf(), null);
        return repositorio.inserir(cliente);
    }

    Cliente atualizar(int id, String nome, String cpf, String email, String telefone) {
        buscar(id);
        Cliente atualizado = new Cliente(id, nome, cpf, email, telefone);
        garantirCpfDisponivel(atualizado.cpf(), id);
        repositorio.atualizar(atualizado);
        return atualizado;
    }

    void apagar(int id) {
        if (!repositorio.remover(id)) {
            throw new ClienteNaoEncontradoException(id);
        }
    }

    Cliente buscar(int id) {
        return repositorio.buscarPorId(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    List<Cliente> listar() {
        return repositorio.listar();
    }

    private void garantirCpfDisponivel(String cpf, Integer idAtual) {
        repositorio.buscarPorCpf(cpf).ifPresent(existente -> {
            if (idAtual == null || existente.id() != idAtual) {
                throw new ValidacaoException("Já existe um cliente com este CPF (ID " + existente.id() + ").");
            }
        });
    }
}

/* ======================================================================
 * Interface de console
 * ====================================================================== */

class Menu {

    private final ClienteService servico;
    private final Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8);

    Menu(ClienteService servico) {
        this.servico = servico;
    }

    void executar() {
        while (true) {
            System.out.println("""

                    ===== CADASTRO DE CLIENTES =====
                    1 - Cadastrar
                    2 - Listar
                    3 - Buscar por ID
                    4 - Atualizar
                    5 - Apagar
                    0 - Sair
                    """);
            String opcao = ler("Opção");
            if (opcao == null) {
                return;
            }
            try {
                switch (opcao) {
                    case "1" -> cadastrar();
                    case "2" -> listar();
                    case "3" -> buscar();
                    case "4" -> atualizar();
                    case "5" -> apagar();
                    case "0" -> {
                        System.out.println("Até logo!");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (ValidacaoException | ClienteNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (UncheckedIOException | IllegalStateException e) {
                System.out.println("Erro de armazenamento: " + e.getMessage());
            }
        }
    }

    private void cadastrar() {
        String nome = ler("Nome");
        String cpf = ler("CPF");
        String email = ler("E-mail");
        String telefone = ler("Telefone (com DDD)");
        Cliente c = servico.cadastrar(nome, cpf, email, telefone);
        System.out.println("Cliente cadastrado com ID " + c.id() + ".");
    }

    private void listar() {
        List<Cliente> lista = servico.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.printf("%-5s %-30s %-15s %-30s %-16s%n", "ID", "NOME", "CPF", "E-MAIL", "TELEFONE");
        lista.forEach(this::imprimirLinha);
    }

    private void buscar() {
        Cliente c = servico.buscar(lerId());
        System.out.println("ID:       " + c.id());
        System.out.println("Nome:     " + c.nome());
        System.out.println("CPF:      " + c.cpfFormatado());
        System.out.println("E-mail:   " + c.email());
        System.out.println("Telefone: " + c.telefoneFormatado());
    }

    private void atualizar() {
        Cliente atual = servico.buscar(lerId());
        System.out.println("Deixe em branco para manter o valor atual.");
        String nome = lerOuManter("Nome", atual.nome());
        String cpf = lerOuManter("CPF", atual.cpf());
        String email = lerOuManter("E-mail", atual.email());
        String telefone = lerOuManter("Telefone", atual.telefone());
        servico.atualizar(atual.id(), nome, cpf, email, telefone);
        System.out.println("Cliente atualizado.");
    }

    private void apagar() {
        Cliente c = servico.buscar(lerId());
        String confirma = ler("Apagar \"" + c.nome() + "\"? (s/N)");
        if (confirma != null && confirma.equalsIgnoreCase("s")) {
            servico.apagar(c.id());
            System.out.println("Cliente apagado.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void imprimirLinha(Cliente c) {
        System.out.printf("%-5d %-30s %-15s %-30s %-16s%n",
                c.id(), cortar(c.nome(), 30), c.cpfFormatado(), cortar(c.email(), 30), c.telefoneFormatado());
    }

    private int lerId() {
        String texto = ler("ID do cliente");
        try {
            return Integer.parseInt(texto == null ? "" : texto.trim());
        } catch (NumberFormatException e) {
            throw new ValidacaoException("ID inválido.");
        }
    }

    private String lerOuManter(String rotulo, String atual) {
        String valor = ler(rotulo + " [" + atual + "]");
        return valor == null || valor.isBlank() ? atual : valor;
    }

    private String ler(String rotulo) {
        System.out.print(rotulo + ": ");
        return entrada.hasNextLine() ? entrada.nextLine() : null;
    }

    private static String cortar(String texto, int max) {
        return texto.length() <= max ? texto : texto.substring(0, max - 1) + "…";
    }
}
