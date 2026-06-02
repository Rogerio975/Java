package com.medexam.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainWindow extends BorderPane {

    private final DashboardPanel dashboardPanel = new DashboardPanel();
    private final PacientesPanel pacientesPanel = new PacientesPanel();
    private final ExamesPanel    examesPanel    = new ExamesPanel();

    private Button btnAtivo;

    public MainWindow() {
        buildSidebar();
        setCenter(dashboardPanel);
    }

    private void buildSidebar() {
        // Marca
        Label logo  = new Label("🏥 MedExam");
        logo.getStyleClass().add("sidebar-title");
        Label versao = new Label("Sistema de Agendamentos");
        versao.getStyleClass().add("sidebar-subtitle");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #2A3F5A; -fx-padding: 0 20;");

        Button btnDash      = navButton("  📊  Dashboard");
        Button btnPacientes = navButton("  👤  Pacientes");
        Button btnExames    = navButton("  🔬  Exames");

        ativar(btnDash);

        btnDash.setOnAction(e      -> navegar(btnDash,      dashboardPanel));
        btnPacientes.setOnAction(e -> navegar(btnPacientes, pacientesPanel));
        btnExames.setOnAction(e    -> navegar(btnExames,    examesPanel));

        Region spacer = new Region(); VBox.setVgrow(spacer, Priority.ALWAYS);

        Label rodape = new Label("v1.0  •  Dados em memória");
        rodape.setStyle("-fx-text-fill: #4A6080; -fx-font-size: 11px; -fx-padding: 0 0 16 20;");

        VBox sidebar = new VBox(logo, versao, sep, btnDash, btnPacientes, btnExames, spacer, rodape);
        sidebar.getStyleClass().add("sidebar");
        setLeft(sidebar);
    }

    private Button navButton(String texto) {
        Button btn = new Button(texto);
        btn.getStyleClass().add("nav-btn");
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    private void ativar(Button btn) {
        if (btnAtivo != null) {
            btnAtivo.getStyleClass().remove("nav-btn-active");
        }
        btnAtivo = btn;
        btn.getStyleClass().add("nav-btn-active");
    }

    private void navegar(Button btn, javafx.scene.layout.Region painel) {
        ativar(btn);
        // Atualiza dados ao trocar de aba
        if (painel instanceof DashboardPanel dp) dp.refresh();
        if (painel instanceof PacientesPanel pp) pp.refresh();
        if (painel instanceof ExamesPanel    ep) ep.refresh();
        setCenter(painel);
    }
}
