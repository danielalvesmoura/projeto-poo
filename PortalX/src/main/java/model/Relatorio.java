package model;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class Relatorio {

    public static void gerarCSV(List<Sessao> sessoes, File destino) {
        try (PrintWriter writer = new PrintWriter(destino)) {

            // Cabeçalho
            writer.println("Título;Descrição;Tipo;Data Início;Hora Início;Data Fim;Hora Fim;Status");

            // Linhas
            for (Sessao s : sessoes) {
                writer.println(s.getTitulo() + ";" + s.getDescricao() + ";" +
                        s.getTipo() + ";" +
                        s.getDataInicio() + ";" +
                        s.getHoraInicio() + ";" +
                        s.getDataFim() + ";" +
                        s.getHoraFim() + ";" +
                        s.getStatus()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
