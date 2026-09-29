package com.medexam.ui;

import com.medexam.model.Paciente;
import com.medexam.service.ClinicaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PacientesPanel extends VBox {

    private final ClinicaService service = ClinicaService.getInstance();
    private TableView<Paciente> tabela;
    private ObservableList<Paciente> dados;
    private TextField campoBusca;

    public PacientesPanel() {
        setSpacing(20);
        setPadding(new Insets(28, 32, 28, 32));
        buildUI();
    }

    private void buildUI() {
        // Cabeçalho
        Label title = new Label("Pacientes");
        title.getStyleClass().add("section-title");
        Label sub = new Label("Gerencie o cadastro de pacientes");
        sub.getStyleClass().add("section-subtitle");
        VBox header = new VBox(4, title, sub);

        // Barra de ações
        campoBusca = new TextField();
        campoBusca.setPromptText("🔍  Buscar por nome ou CPF...");
        campoBusca.getStyleClass().add("search-field");
        campoBusca.textProperty().addListener((o, ov, nv) -> filtrar(nv));

        Button btnNovo = new Button("+ Novo Paciente");
        btnNovo.getStyleClass().add("btn-primary");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox barra = new HBox(12, campoBusca, spacer, btnNovo);
        barra.setAlignment(Pos.CENTER_LEFT);

        // Tabela
        dados = FXCollections.observableArrayList(service.listarPacientes());
        tabela = new TableView<>(dados);
        tabela.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Paciente, String> colNome  = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setMinWidth(180);

        TableColumn<Paciente, String> colCpf   = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Paciente, LocalDate> colNasc = new TableColumn<>("Nascimento");
        colNasc.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colNasc.setCellFactory(col -> new TableCell<>() {
            final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override protected void updateItem(LocalDate v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty || v == null ? null : v.format(fmt));
            }
        });

        TableColumn<Paciente, String> colTel  = new TableColumn<>("Telefone");
        colTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Paciente, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Paciente, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMinWidth(140);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            final Button editar  = new Button("Editar");
            final Button excluir = new Button("Excluir");
            final HBox box = new HBox(6, editar, excluir);
            {
                editar.getStyleClass().add("btn-outline");
                excluir.getStyleClass().add("btn-danger");
                editar.setStyle("-fx-font-size: 11px; -fx-padding: 4 10;");
                excluir.setStyle("-fx-font-size: 11px; -fx-padding: 4 10;");
                box.setAlignment(Pos.CENTER);
                editar.setOnAction(e -> abrirFormulario(getTableView().getItems().get(getIndex())));
                excluir.setOnAction(e -> confirmarExclusao(getTableView().getItems().get(getIndex())));
            }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : box);
            }
        });

        @SuppressWarnings({"unchecked", "unused"})
        var _unused = tabela.getColumns().addAll(
            colNome, colCpf, colNasc, colTel, colEmail, colAcoes
        );

        VBox tableCard = new VBox(tabela);
        tableCard.getStyleClass().add("form-card");
        tableCard.setPadding(new Insets(0));
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        getChildren().addAll(header, barra, tableCard);
    }

    private void filtrar(String termo) {
        if (termo == null || termo.isBlank()) {
            dados.setAll(service.listarPacientes());
        } else {
            dados.setAll(service.buscarPacientesPorNome(termo.trim()));
        }
    }

    private void abrirFormulario(Paciente paciente) {
        Dialog<Paciente> dialog = new Dialog<>();
        dialog.setTitle(paciente == null ? "Novo Paciente" : "Editar Paciente");
        dialog.setHeaderText(null);

        // Campos
        TextField fNome  = campo(paciente != null ? paciente.getNome() : "");
        TextField fCpf   = campo(paciente != null ? paciente.getCpf() : "");
        DatePicker fNasc = new DatePicker(paciente != null ? paciente.getDataNascimento() : null);
        fNasc.setPromptText("dd/mm/aaaa");
        TextField fTel   = campo(paciente != null ? paciente.getTelefone() : "");
        TextField fEmail = campo(paciente != null ? paciente.getEmail() : "");

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(10);
        grid.setPadding(new Insets(20, 28, 10, 28));
        addRow(grid, 0, "Nome *", fNome);
        addRow(grid, 1, "CPF *", fCpf);
        addRow(grid, 2, "Data de Nascimento", fNasc);
        addRow(grid, 3, "Telefone", fTel);
        addRow(grid, 4, "E-mail", fEmail);
        GridPane.setHgrow(fNome, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Salvar");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancelar");

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;
            String nome = fNome.getText().trim();
            String cpf  = fCpf.getText().trim();
            if (nome.isEmpty() || cpf.isEmpty()) {
                mostrarErro("Nome e CPF são obrigatórios.");
                return null;
            }
            String ignorarId = paciente != null ? paciente.getId() : "";
            if (service.cpfJaCadastrado(cpf, ignorarId)) {
                mostrarErro("CPF já cadastrado.");
                return null;
            }
            if (paciente == null) {
                Paciente novo = new Paciente(nome, cpf, fNasc.getValue(),
                        fTel.getText().trim(), fEmail.getText().trim());
                service.adicionarPaciente(novo);
                return novo;
            } else {
                paciente.setNome(nome);
                paciente.setCpf(cpf);
                paciente.setDataNascimento(fNasc.getValue());
                paciente.setTelefone(fTel.getText().trim());
                paciente.setEmail(fEmail.getText().trim());
                return paciente;
            }
        });

        Optional<Paciente> result = dialog.showAndWait();
        result.ifPresent(p -> filtrar(campoBusca.getText()));
    }

    private void confirmarExclusao(Paciente p) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir paciente?");
        alert.setContentText("\"" + p.getNome() + "\" será removido permanentemente.");
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                service.removerPaciente(p.getId());
                dados.remove(p);
            }
        });
    }

    private TextField campo(String valor) {
        TextField tf = new TextField(valor);
        tf.setPrefWidth(280);
        return tf;
    }

    private void addRow(GridPane g, int row, String label, javafx.scene.Node field) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("field-label");
        g.add(lbl, 0, row);
        g.add(field, 1, row);
        if (field instanceof TextField tf) GridPane.setHgrow(tf, Priority.ALWAYS);
        if (field instanceof DatePicker dp) { dp.setPrefWidth(280); GridPane.setHgrow(dp, Priority.ALWAYS); }
    }

    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public void refresh() {
        dados.setAll(service.listarPacientes());
    }
}
