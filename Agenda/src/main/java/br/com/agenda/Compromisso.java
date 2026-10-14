package br.com.agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Compromisso {
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private final String titulo;
    private final String descricao;
    private final LocalDateTime inicio;
    private final LocalDateTime fim;

    public Compromisso(int id, String titulo, String descricao, LocalDateTime inicio, LocalDateTime fim) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O titulo e obrigatorio.");
        }
        if (inicio == null || fim == null || !fim.isAfter(inicio)) {
            throw new IllegalArgumentException("O fim deve ser posterior ao inicio.");
        }

        this.id = id;
        this.titulo = titulo.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    @Override
    public String toString() {
        String detalhe = descricao.isBlank() ? "" : " | " + descricao;
        return String.format("[%d] %s - %s ate %s%s", id, titulo,
                inicio.format(FORMATADOR), fim.format(FORMATADOR), detalhe);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Compromisso outro)) {
            return false;
        }
        return id == outro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
