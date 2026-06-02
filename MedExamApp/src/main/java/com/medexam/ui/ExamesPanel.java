package com.medexam.ui;

import com.medexam.model.Exame;
import com.medexam.model.Paciente;
import com.medexam.service.ClinicaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ExamesPanel extends VBox {

    private final ClinicaService service = ClinicaService.getInstance();
    private TableView<Exame> tabela;
    private ObservableList<Exame> dados;
    private ComboBox<String> filtroStatus;

    public ExamesPanel() {
        setSpacing(20);
        setPadding(new Insets(28, 32, 28, 32));
        buildUI();
    }

    private void buildUI() {
        // Cabeçalho
        Label title = new Label("Exames");
        title.getStyleClass().add("section-title");
        Label sub = new Label("Agende, edite e cancele exames médicos");
        sub.getStyleClass().add("section-subtitle");
        VBox header = new VBox(4, title, sub);

        // Barra de ações
        filtroStatus = new ComboBox<>(FXCollections.observableArrayList(
                "Todos", "Agendado", "Concluído", "Cancelado"));
        filtroStatus.setValue("Todos");
        filtroStatus.setOnAction(e -> aplicarFiltro());

        Button btnNovo = new Button("+ Agendar Exame");
        btnNovo.getStyleClass().add("btn-primary");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox barra = new HBox(12, new Label("Filtrar:"), filtroStatus, spacer, btnNovo);
        barra.setAlignment(Pos.CENTER_LEFT);

        // Tabela
        dados = FXCollections.observableArrayList(service.listarExames());
        tabela = new TableView<>(dados);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        TableColumn<Exame, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setMinWidth(160);
        colPaciente.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setText(null); return; }
                setText(((Exame) getTableRow().getItem()).getPaciente().getNome());
            }
        });

        TableColumn<Exame, Exame.TipoExame> colTipo = new TableColumn<>("Exame");
        colTipo.setMinWidth(180);
        colTipo.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Exame.TipoExame v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setText(null); return; }
                setText(((Exame) getTableRow().getItem()).getTipo().getDescricao());
            }
        });

        TableColumn<Exame, LocalDateTime> colData = new TableColumn<>("Data/Hora");
        colData.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(LocalDateTime v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setText(null); return; }
                setText(((Exame) getTableRow().getItem()).getDataHora().format(fmt));
            }
        });

        TableColumn<Exame, String> colMedico = new TableColumn<>("Médico");
        colMedico.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setText(null); return; }
                setText(((Exame) getTableRow().getItem()).getMedico());
            }
        });

        TableColumn<Exame, Exame.Status> colStatus = new TableColumn<>("Status");
        colStatus.setMinWidth(100);
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Exame.Status v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setGraphic(null); return; }
                Exame.Status s = ((Exame) getTableRow().getItem()).getStatus();
                Label badge = new Label(s.name().charAt(0) + s.name().substring(1).toLowerCase());
                switch (s) {
                    case AGENDADO  -> badge.setStyle("-fx-text-fill:#0D6EFD;-fx-font-weight:bold;");
                    case CONCLUIDO -> badge.setStyle("-fx-text-fill:#198754;-fx-font-weight:bold;");
                    case CANCELADO -> badge.setStyle("-fx-text-fill:#DC3545;-fx-font-weight:bold;");
                }
                setGraphic(badge);
            }
        });

        TableColumn<Exame, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMinWidth(200);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            final Button editar    = new Button("Editar");
            final Button concluir  = new Button("Concluir");
            final Button cancelar  = new Button("Cancelar");
            final HBox box = new HBox(5, editar, concluir, cancelar);
            {
                editar.getStyleClass().add("btn-outline");
                concluir.getStyleClass().add("btn-success");
                cancelar.getStyleClass().add("btn-danger");
                for (Button b : new Button[]{editar, concluir, cancelar})
                    b.setStyle(b.getStyle() + "-fx-font-size:11px;-fx-padding:4 8;");
                box.setAlignment(Pos.CENTER);

                editar.setOnAction(e -> {
                    Exame ex = getTableView().getItems().get(getIndex());
                    abrirFormulario(ex);
                });
                concluir.setOnAction(e -> {
                    Exame ex = getTableView().getItems().get(getIndex());
                    ex.setStatus(Exame.Status.CONCLUIDO);
                    tabela.refresh();
                });
                cancelar.setOnAction(e -> {
                    Exame ex = getTableView().getItems().get(getIndex());
                    confirmarCancelamento(ex);
                });
            }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) { setGraphic(null); return; }
                Exame.Status s = ((Exame) getTableRow().getItem()).getStatus();
                concluir.setDisable(s != Exame.Status.AGENDADO);
                cancelar.setDisable(s == Exame.Status.CANCELADO);
                editar.setDisable(s != Exame.Status.AGENDADO);
                setGraphic(box);
            }
        });

        tabela.getColumns().addAll(colPaciente, colTipo, colData, colMedico, colStatus, colAcoes);

        VBox tableCard = new VBox(tabela);
        tableCard.getStyleClass().add("form-card");
        tableCard.setPadding(new Insets(0));
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        getChildren().addAll(header, barra, tableCard);
    }

    private void aplicarFiltro() {
        String sel = filtroStatus.getValue();
        if ("Todos".equals(sel)) {
            dados.setAll(service.listarExames());
        } else {
            Exame.Status s = switch (sel) {
                case "Agendado"  -> Exame.Status.AGENDADO;
                case "Concluído" -> Exame.Status.CONCLUIDO;
                default          -> Exame.Status.CANCELADO;
            };
            dados.setAll(service.listarExamesPorStatus(s));
        }
    }

    private void abrirFormulario(Exame exame) {
        var pacientes = service.listarPacientes();
        if (pacientes.isEmpty()) {
            mostrarErro("Cadastre pelo menos um paciente antes de agendar exames.");
            return;
        }

        Dialog<Exame> dialog = new Dialog<>();
        dialog.setTitle(exame == null ? "Agendar Exame" : "Editar Agendamento");
        dialog.setHeaderText(null);

        ComboBox<Paciente> cbPaciente = new ComboBox<>(FXCollections.observableArrayList(pacientes));
        cbPaciente.setPrefWidth(280);
        if (exame != null) cbPaciente.setValue(exame.getPaciente());

        ComboBox<Exame.TipoExame> cbTipo = new ComboBox<>(
                FXCollections.observableArrayList(Exame.TipoExame.values()));
        cbTipo.setPrefWidth(280);
        if (exame != null) cbTipo.setValue(exame.getTipo());

        DatePicker dpData = new DatePicker(exame != null ? exame.getDataHora().toLocalDate() : null);
        dpData.setPromptText("dd/mm/aaaa");
        dpData.setPrefWidth(180);

        // Hora e minuto
        ComboBox<String> cbHora   = new ComboBox<>();
        ComboBox<String> cbMinuto = new ComboBox<>();
        for (int h = 6; h <= 20; h++)  cbHora.getItems().add(String.format("%02d", h));
        for (int m : new int[]{0, 15, 30, 45}) cbMinuto.getItems().add(String.format("%02d", m));
        if (exame != null) {
            cbHora.setValue(String.format("%02d", exame.getDataHora().getHour()));
            cbMinuto.setValue(String.format("%02d", exame.getDataHora().getMinute()));
        } else {
            cbHora.setValue("08"); cbMinuto.setValue("00");
        }
        HBox hDataHora = new HBox(8, dpData, new Label("às"), cbHora, new Label(":"), cbMinuto);
        hDataHora.setAlignment(Pos.CENTER_LEFT);

        TextField fMedico = new TextField(exame != null ? exame.getMedico() : "");
        fMedico.setPrefWidth(280);
        TextArea fObs = new TextArea(exame != null ? exame.getObservacoes() : "");
        fObs.setPrefRowCount(3); fObs.setPrefWidth(280);

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(10);
        grid.setPadding(new Insets(20, 28, 10, 28));
        addRow(grid, 0, "Paciente *",  cbPaciente);
        addRow(grid, 1, "Tipo de Exame *", cbTipo);
        addRow(grid, 2, "Data/Hora *", hDataHora);
        addRow(grid, 3, "Médico Solicitante", fMedico);
        addRow(grid, 4, "Observações", fObs);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setMinWidth(520);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Salvar");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancelar");

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;
            if (cbPaciente.getValue() == null || cbTipo.getValue() == null || dpData.getValue() == null) {
                mostrarErro("Paciente, tipo e data são obrigatórios.");
                return null;
            }
            LocalDateTime dt = LocalDateTime.of(
                    dpData.getValue(),
                    java.time.LocalTime.of(Integer.parseInt(cbHora.getValue()),
                                           Integer.parseInt(cbMinuto.getValue())));
            if (exame == null) {
                Exame novo = new Exame(cbPaciente.getValue(), cbTipo.getValue(), dt,
                        fMedico.getText().trim(), fObs.getText().trim());
                service.adicionarExame(novo);
                return novo;
            } else {
                exame.setPaciente(cbPaciente.getValue());
                exame.setTipo(cbTipo.getValue());
                exame.setDataHora(dt);
                exame.setMedico(fMedico.getText().trim());
                exame.setObservacoes(fObs.getText().trim());
                return exame;
            }
        });

        Optional<Exame> result = dialog.showAndWait();
        result.ifPresent(e -> { aplicarFiltro(); tabela.refresh(); });
    }

    private void confirmarCancelamento(Exame e) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Cancelar Exame");
        a.setHeaderText("Cancelar este exame?");
        a.setContentText(e.getTipo().getDescricao() + " de " + e.getPaciente().getNome());
        a.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                e.setStatus(Exame.Status.CANCELADO);
                tabela.refresh();
            }
        });
    }

    private void addRow(GridPane g, int row, String label, javafx.scene.Node field) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("field-label");
        lbl.setMinWidth(160);
        g.add(lbl, 0, row);
        g.add(field, 1, row);
    }

    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public void refresh() {
        aplicarFiltro();
        tabela.refresh();
    }
}
