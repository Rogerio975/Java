package com.medexam.service;

import com.medexam.model.Exame;
import com.medexam.model.Paciente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClinicaService {
    private static ClinicaService instance;
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Exame> exames = new ArrayList<>();

    private ClinicaService() { carregarDadosDemo(); }

    public static ClinicaService getInstance() {
        if (instance == null) instance = new ClinicaService();
        return instance;
    }

    // ── Pacientes ──────────────────────────────────────────────────────────────
    public void adicionarPaciente(Paciente p) { pacientes.add(p); }

    public boolean removerPaciente(String id) {
        return pacientes.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Paciente> buscarPacientePorId(String id) {
        return pacientes.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Paciente> listarPacientes() { return new ArrayList<>(pacientes); }

    public List<Paciente> buscarPacientesPorNome(String termo) {
        String t = termo.toLowerCase();
        return pacientes.stream()
                .filter(p -> p.getNome().toLowerCase().contains(t) || p.getCpf().contains(t))
                .collect(Collectors.toList());
    }

    public boolean cpfJaCadastrado(String cpf, String ignorarId) {
        return pacientes.stream()
                .filter(p -> !p.getId().equals(ignorarId))
                .anyMatch(p -> p.getCpf().equals(cpf));
    }

    // ── Exames ─────────────────────────────────────────────────────────────────
    public void adicionarExame(Exame e) { exames.add(e); }

    public boolean removerExame(String id) {
        return exames.removeIf(e -> e.getId().equals(id));
    }

    public Optional<Exame> buscarExamePorId(String id) {
        return exames.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public List<Exame> listarExames() { return new ArrayList<>(exames); }

    public List<Exame> listarExamesPorPaciente(String pacienteId) {
        return exames.stream()
                .filter(e -> e.getPaciente().getId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Exame> listarExamesPorStatus(Exame.Status status) {
        return exames.stream()
                .filter(e -> e.getStatus() == status)
                .collect(Collectors.toList());
    }

    // ── Dados demo ─────────────────────────────────────────────────────────────
    private void carregarDadosDemo() {
        Paciente p1 = new Paciente("Ana Paula Souza", "123.456.789-00",
                LocalDate.of(1985, 3, 15), "(71) 99999-0001", "ana@email.com");
        Paciente p2 = new Paciente("Carlos Eduardo Lima", "987.654.321-00",
                LocalDate.of(1972, 7, 22), "(71) 99999-0002", "carlos@email.com");
        Paciente p3 = new Paciente("Maria Fernanda Costa", "456.789.123-00",
                LocalDate.of(1990, 11, 5), "(71) 99999-0003", "maria@email.com");
        pacientes.addAll(List.of(p1, p2, p3));

        exames.add(new Exame(p1, Exame.TipoExame.HEMOGRAMA,
                LocalDateTime.now().plusDays(2).withHour(9).withMinute(0),
                "Dr. Roberto Silva", "Jejum de 8h"));
        exames.add(new Exame(p2, Exame.TipoExame.ELETROCARDIOGRAMA,
                LocalDateTime.now().plusDays(3).withHour(14).withMinute(30),
                "Dra. Juliana Mendes", ""));
        Exame e3 = new Exame(p3, Exame.TipoExame.RAIO_X,
                LocalDateTime.now().minusDays(1).withHour(10).withMinute(0),
                "Dr. Marcos Oliveira", "Tórax AP e Perfil");
        e3.setStatus(Exame.Status.CONCLUIDO);
        exames.add(e3);
    }
}
