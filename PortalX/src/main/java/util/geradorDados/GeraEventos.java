package util.geradorDados;

import model.Evento;
import servico.EventoServico;
import util.Global;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class GeraEventos {
    static EventoServico eventoServico = new EventoServico();

    public static void geraEventos(int qntd) {
        System.out.println("Gerando...");

        for(int i = 0; i < qntd; i++) {
            Evento evento = GeraEventos.gerarEvento();

            eventoServico.cadastrar(evento.getNome(),evento.getDescricao(), evento.getEndereco(), evento.getDataInicio(),evento.getHoraInicio().toString(),
                    evento.getDataFim(),evento.getHoraFim().toString(),Integer.parseInt(evento.getCapacidade()));
        }

        System.out.println("Concluído.");
    }

    public static Evento gerarEvento() {

        Evento evento = new Evento();

        // Nome
        evento.setNome(Global.faker.music().genre() + " Festival " + Global.faker.address().city());

        // Descrição
        evento.setDescricao(Global.faker.lorem().sentence(12));

        // Endereço
        evento.setEndereco(
                Global.faker.address().streetAddress() + ", " +
                        Global.faker.address().buildingNumber() + " - " +
                        Global.faker.address().city()
        );

        // Datas
        LocalDate dataInicio = gerarDataProxima();
        int duracaoDias = Global.faker.number().numberBetween(0, 4); // evento pode durar de 0 a 3 dias
        LocalDate dataFim = dataInicio.plusDays(duracaoDias);

        evento.setDataInicio(dataInicio);
        evento.setDataFim(dataFim);

        // Horários
        LocalTime horaInicio = gerarHora();
        LocalTime horaFim = gerarHoraPosterior(horaInicio, dataInicio, dataFim);

        evento.setHoraInicio(horaInicio);
        evento.setHoraFim(horaFim);

        // Capacidade
        evento.setCapacidade(Global.faker.number().numberBetween(50, 1000));

        return evento;
    }

    private static LocalDate gerarDataProxima() {
        long hoje = LocalDate.now().toEpochDay();
        long futuro = hoje + ThreadLocalRandom.current().nextInt(1, 365);
        return LocalDate.ofEpochDay(futuro);
    }

    private static LocalTime gerarHora() {
        int h = Global.faker.number().numberBetween(8, 23);
        int m = Global.faker.number().numberBetween(0, 3) * 15; // múltiplos de 15
        return LocalTime.of(h, m);
    }

    private static LocalTime gerarHoraPosterior(LocalTime horaInicio, LocalDate dataInicio, LocalDate dataFim) {

        // Se termina no mesmo dia, a hora deve ser maior
        if (dataInicio.equals(dataFim)) {
            int h = horaInicio.getHour() + Global.faker.number().numberBetween(1, 6);
            if (h > 23) h = 23;
            return LocalTime.of(h, horaInicio.getMinute());
        }

        // Se termina outro dia, gerar horário aleatório
        int h = Global.faker.number().numberBetween(8, 23);
        int m = Global.faker.number().numberBetween(0, 3) * 15;
        return LocalTime.of(h, m);
    }
}
