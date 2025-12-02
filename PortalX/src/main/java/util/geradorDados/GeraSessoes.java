package util.geradorDados;

import dao.EventoDAO;
import dao.SalaDAO;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sala;
import model.Sessao;
import servico.SessaoServico;
import util.Global;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GeraSessoes {

    static SessaoServico sessaoServico = new SessaoServico();
    static SalaDAO salaDAO = new SalaDAO();
    static EventoDAO eventoDAO = new EventoDAO();

    private static List<Sala> salas = salaDAO.buscarTodos(Sala.class);
    private static List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

    public static void geraSessoes(int qntd) {
        System.out.println("Gerando...");

        salas = salaDAO.buscarTodos(Sala.class);
        eventos = eventoDAO.buscarTodos(Evento.class);

        for (int i = 0; i < qntd; i++) {
            try {
                Sessao sessao = gerarSessao();
                sessaoServico.cadastrar(pegarSalaAleatoria(),pegarEventoAleatorio(),sessao.getTitulo(),sessao.getDescricao(),sessao.getTipo(),
                        sessao.getDataInicio(),sessao.getHoraInicio(),sessao.getDataFim(),sessao.getHoraFim());
            } catch (Exception ignored) {}
        }

        System.out.println("Concluído.");
    }

    public static Sessao gerarSessao() {

        Sala sala = pegarSalaAleatoria();
        Evento evento = pegarEventoAleatorio();

        String titulo = gerarTitulo();
        String descricao = gerarDescricao();

        TipoSessao tipo = gerarTipoSessao();
        StatusSessao status = gerarStatusSessao();

        // Datas coerentes dentro do evento
        LocalDate dataInicio = gerarDataDentroDoEvento(evento);
        LocalDate dataFim;

        // chance de terminar no mesmo dia ou depois
        if (Global.faker.bool().bool()) {
            dataFim = dataInicio;
        } else {
            dataFim = dataInicio.plusDays(Global.faker.number().numberBetween(1, 3));
        }

        LocalTime horaInicio = gerarHora();
        LocalTime horaFim = gerarHoraDepois(horaInicio);

        return new Sessao(
                sala,
                evento,
                titulo,
                descricao,
                tipo,
                dataInicio,
                horaInicio,
                dataFim,
                horaFim,
                status
        );
    }

    /* ------------------------ Métodos auxiliares ------------------------ */

    private static Sala pegarSalaAleatoria() {
        int index = Global.faker.number().numberBetween(0, salas.size());
        return salas.get(index);
    }

    private static Evento pegarEventoAleatorio() {
        int index = Global.faker.number().numberBetween(0, eventos.size());
        return eventos.get(index);
    }

    private static TipoSessao gerarTipoSessao() {
        return TipoSessao.values()[
                Global.faker.number().numberBetween(0, TipoSessao.values().length)
                ];
    }

    private static StatusSessao gerarStatusSessao() {
        return StatusSessao.values()[
                Global.faker.number().numberBetween(0, StatusSessao.values().length)
                ];
    }

    private static String gerarTitulo() {
        return Global.faker.book().title();
    }

    private static String gerarDescricao() {
        return Global.faker.lorem().sentence(12);
    }

    private static LocalDate gerarDataDentroDoEvento(Evento evento) {
        long dias = evento.getDataFim().toEpochDay() - evento.getDataInicio().toEpochDay();
        long add = Global.faker.number().numberBetween(0, (int) dias + 1);
        return evento.getDataInicio().plusDays(add);
    }

    private static LocalTime gerarHora() {
        int hour = Global.faker.number().numberBetween(8, 20);
        int minute = Global.faker.options().option(0, 15, 30, 45);
        return LocalTime.of(hour, minute);
    }

    private static LocalTime gerarHoraDepois(LocalTime inicio) {
        int addMin = Global.faker.number().numberBetween(30, 180);
        return inicio.plusMinutes(addMin);
    }

}
