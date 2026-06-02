package com.medexam.ui;

import com.medexam.model.Exame;
import com.medexam.service.ClinicaService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardPanel extends VBox {

    private final ClinicaService service = ClinicaService.getInstance();

    public DashboardPanel() {
        setSpacing(24);
        setPadding(new Insets(28, 32, 28, 32));
        buildUI();
    }

    private void buildUI() {
        getChildren().clear();

        // Cabeçalho
        Label title = new Label("Dashboard");
        title.getStyleClass().add("section-title");
        Label sub = new Label("Visão geral do sistema");
        sub.getStyleClass().add("section-subtitle");
        VBox header = new VBox(4, title, sub);

        // Cards
        int totalPacientes  = service.listarPacientes().size();
        int totalAgendados  = service.listarExamesPorStatus(Exame.Status.AGENDADO).size();
        int totalConcluidos = service.listarExamesPorStatus(Exame.Status.CONCLUIDO).size();
        int totalCancelados = service.listarExamesPorStatus(Exame.Status.CANCELADO).size();

        HBox cards = new HBox(16,
            criarCard(String.valueOf(totalPacientes),  "Pacientes Cadastrados", "#0D6EFD"),
            criarCard(String.valueOf(totalAgendados),  "Exames Agendados",      "#FFC107"),
            criarCard(String.valueOf(totalConcluidos), "Exames Concluídos",     "#198754"),
            criarCard(String.valueOf(totalCancelados), "Exames Cancelados",     "#DC3545")
        );
        cards.setFillHeight(true);
        HBox.setHgrow(cards.getChildren().get(0), Priority.ALWAYS);

        for (var node : cards.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
            ((Region) node).setMaxWidth(Double.MAX_VALUE);
        }

        // Próximos exames
        Label proxLabel = new Label("Próximos Agendamentos");
        proxLabel.getStyleClass().add("section-title");
        proxLabel.setStyle("-fx-font-size: 15px;");

        VBox listaProximos = new VBox(8);
        var agendados = service.listarExamesPorStatus(Exame.Status.AGENDADO);
        agendados.sort((a, b) -> a.getDataHora().compareTo(b.getDataHora()));

        if (agendados.isEmpty()) {
            Label vazio = new Label("Nenhum exame agendado.");
            vazio.setStyle("-fx-text-fill: #6C757D;");
            listaProximos.getChildren().add(vazio);
        } else {
            agendados.stream().limit(5).forEach(e -> {
                HBox row = criarLinhaExame(e);
                listaProximos.getChildren().add(row);
            });
        }

        VBox proxCard = new VBox(12, proxLabel, listaProximos);
        proxCard.getStyleClass().add("form-card");

        getChildren().addAll(header, cards, proxCard);
    }

    private VBox criarCard(String numero, String descricao, String cor) {
        Label num = new Label(numero);
        num.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + cor + ";");
        Label desc = new Label(descricao);
        desc.setStyle("-fx-font-size: 12px; -fx-text-fill: #6C757D;");
        VBox card = new VBox(6, num, desc);
        card.getStyleClass().add("stat-card");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        return card;
    }

    private HBox criarLinhaExame(Exame e) {
        java.time.format.DateTimeFormatter fmt =
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Label tipo    = new Label(e.getTipo().getDescricao());
        tipo.setStyle("-fx-font-weight: bold; -fx-min-width: 200px;");
        Label pacient = new Label(e.getPaciente().getNome());
        pacient.setStyle("-fx-text-fill: #495057; -fx-min-width: 200px;");
        Label data = new Label(e.getDataHora().format(fmt));
        data.setStyle("-fx-text-fill: #6C757D;");
        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(16, tipo, pacient, spacer, data);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 6;");
        return row;
    }

    public void refresh() { buildUI(); }
}
