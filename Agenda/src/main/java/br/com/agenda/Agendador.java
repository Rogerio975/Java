package br.com.agenda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Agendador {
    private final List<Compromisso> compromissos = new ArrayList<>();
    private int proximoId = 1;

    public Compromisso agendar(String titulo, String descricao, LocalDateTime inicio, LocalDateTime fim) {
        validarDisponibilidade(inicio, fim);
        Compromisso compromisso = new Compromisso(proximoId++, titulo, descricao, inicio, fim);
        compromissos.add(compromisso);
        return compromisso;
    }

    public List<Compromisso> listar() {
        return compromissos.stream()
                .sorted(Comparator.comparing(Compromisso::getInicio))
                .toList();
    }

    public boolean cancelar(int id) {
        return compromissos.removeIf(compromisso -> compromisso.getId() == id);
    }

    public List<Compromisso> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return compromissos.stream()
                .filter(compromisso -> compromisso.getInicio().isBefore(fim)
                        && compromisso.getFim().isAfter(inicio))
                .sorted(Comparator.comparing(Compromisso::getInicio))
                .toList();
    }

    private void validarDisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null || !fim.isAfter(inicio)) {
            throw new IllegalArgumentException("Informe um periodo valido.");
        }

        boolean conflita = compromissos.stream()
                .anyMatch(compromisso -> compromisso.getInicio().isBefore(fim)
                        && compromisso.getFim().isAfter(inicio));
        if (conflita) {
            throw new IllegalArgumentException("Ja existe um compromisso nesse horario.");
        }
    }
}
