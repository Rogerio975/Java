package com.medexam.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Exame {
    public enum Status { AGENDADO, CONCLUIDO, CANCELADO }
    public enum TipoExame {
        HEMOGRAMA("Hemograma Completo"),
        GLICEMIA("Glicemia em Jejum"),
        COLESTEROL("Colesterol Total"),
        RAIO_X("Raio-X"),
        ULTRASSOM("Ultrassonografia"),
        ELETROCARDIOGRAMA("Eletrocardiograma"),
        RESSONANCIA("Ressonância Magnética"),
        TOMOGRAFIA("Tomografia Computadorizada"),
        OUTRO("Outro");

        private final String descricao;
        TipoExame(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
        @Override public String toString() { return descricao; }
    }

    private final String id;
    private Paciente paciente;
    private TipoExame tipo;
    private LocalDateTime dataHora;
    private String medico;
    private String observacoes;
    private Status status;

    public Exame(Paciente paciente, TipoExame tipo, LocalDateTime dataHora, String medico, String observacoes) {
        this.id = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.medico = medico;
        this.observacoes = observacoes;
        this.status = Status.AGENDADO;
    }

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public TipoExame getTipo() { return tipo; }
    public void setTipo(TipoExame tipo) { this.tipo = tipo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
