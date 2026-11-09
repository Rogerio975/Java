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
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * CRUD gráfico de clientes com Swing (cadastrar, listar, buscar, atualizar e apagar).
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
        SwingUtilities.invokeLater(() -> new ClienteGUI(servico, arquivo).setVisible(true));
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
 * Interface gráfica
 * ====================================================================== */

class ClienteGUI extends JFrame {

    private final ClienteService servico;
    private final JTextField nome = new JTextField();
    private final JTextField cpf = new JTextField();
    private final JTextField email = new JTextField();
    private final JTextField telefone = new JTextField();
    private final JTextField buscaId = new JTextField(8);
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "CPF", "E-mail", "Telefone"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabela = new JTable(modelo);
    private final JLabel status = new JLabel(" ");

    ClienteGUI(ClienteService servico, Path arquivo) {
        super("Cadastro de clientes");
        this.servico = servico;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formulario = new JPanel(new GridLayout(2, 4, 8, 4));
        formulario.setBorder(BorderFactory.createTitledBorder("Dados do cliente"));
        formulario.add(new JLabel("Nome"));
        formulario.add(new JLabel("CPF"));
        formulario.add(new JLabel("E-mail"));
        formulario.add(new JLabel("Telefone (com DDD)"));
        formulario.add(nome);
        formulario.add(cpf);
        formulario.add(email);
        formulario.add(telefone);
        add(formulario, BorderLayout.NORTH);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(true);
        tabela.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                preencherFormularioSelecionado();
            }
        });
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.addActionListener(event -> executarComTratamento(this::cadastrar));
        JButton atualizar = new JButton("Atualizar selecionado");
        atualizar.addActionListener(event -> executarComTratamento(this::atualizar));
        JButton apagar = new JButton("Apagar selecionado");
        apagar.addActionListener(event -> executarComTratamento(this::apagar));
        JButton limpar = new JButton("Limpar campos");
        limpar.addActionListener(event -> limparFormulario());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(cadastrar);
        botoes.add(atualizar);
        botoes.add(apagar);
        botoes.add(limpar);

        JButton buscar = new JButton("Buscar por ID");
        buscar.addActionListener(event -> executarComTratamento(this::buscar));
        JButton listar = new JButton("Mostrar todos");
        listar.addActionListener(event -> executarComTratamento(this::atualizarTabela));
        JPanel busca = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        busca.add(new JLabel("ID:"));
        busca.add(buscaId);
        busca.add(buscar);
        busca.add(listar);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(botoes, BorderLayout.WEST);
        rodape.add(busca, BorderLayout.EAST);
        rodape.add(status, BorderLayout.SOUTH);
        add(rodape, BorderLayout.SOUTH);

        status.setText("Dados: " + arquivo.toAbsolutePath());
        executarComTratamento(this::atualizarTabela);
    }

    private void cadastrar() {
        Cliente cliente = servico.cadastrar(
                nome.getText(), cpf.getText(), email.getText(), telefone.getText());
        atualizarTabela();
        limparFormulario();
        status.setText("Cliente cadastrado com ID " + cliente.id() + ".");
    }

    private void atualizar() {
        Cliente selecionado = clienteSelecionado();
        servico.atualizar(selecionado.id(), nome.getText(), cpf.getText(),
                email.getText(), telefone.getText());
        atualizarTabela();
        status.setText("Cliente atualizado.");
    }

    private void apagar() {
        Cliente selecionado = clienteSelecionado();
        int resposta = JOptionPane.showConfirmDialog(this,
                "Apagar o cliente \"" + selecionado.nome() + "\"?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            servico.apagar(selecionado.id());
            atualizarTabela();
            limparFormulario();
            status.setText("Cliente apagado.");
        }
    }

    private void buscar() {
        int id = lerId(buscaId.getText());
        Cliente cliente = servico.buscar(id);
        atualizarTabela(List.of(cliente));
        tabela.setRowSelectionInterval(0, 0);
        status.setText("Cliente com ID " + id + " encontrado.");
    }

    private Cliente clienteSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            throw new ValidacaoException("Selecione um cliente na tabela.");
        }
        int linhaModelo = tabela.convertRowIndexToModel(linha);
        return servico.buscar((int) modelo.getValueAt(linhaModelo, 0));
    }

    private void preencherFormularioSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        int linhaModelo = tabela.convertRowIndexToModel(linha);
        Cliente cliente = servico.buscar((int) modelo.getValueAt(linhaModelo, 0));
        nome.setText(cliente.nome());
        cpf.setText(cliente.cpfFormatado());
        email.setText(cliente.email());
        telefone.setText(cliente.telefoneFormatado());
    }

    private void atualizarTabela() {
        atualizarTabela(servico.listar());
    }

    private void atualizarTabela(List<Cliente> clientes) {
        modelo.setRowCount(0);
        for (Cliente cliente : clientes) {
            modelo.addRow(new Object[]{cliente.id(), cliente.nome(), cliente.cpfFormatado(),
                    cliente.email(), cliente.telefoneFormatado()});
        }
    }

    private void limparFormulario() {
        tabela.clearSelection();
        nome.setText("");
        cpf.setText("");
        email.setText("");
        telefone.setText("");
    }

    private static int lerId(String texto) {
        try {
            int id = Integer.parseInt(texto == null ? "" : texto.trim());
            if (id < 1) {
                throw new NumberFormatException();
            }
            return id;
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Informe um ID válido.");
        }
    }

    private void executarComTratamento(Runnable operacao) {
        try {
            operacao.run();
        } catch (ValidacaoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Verifique os dados",
                    JOptionPane.WARNING_MESSAGE);
        } catch (UncheckedIOException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de armazenamento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
