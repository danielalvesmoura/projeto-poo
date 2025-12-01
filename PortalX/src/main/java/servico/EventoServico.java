package servico;

import dao.EventoDAO;
import dao.InscricaoDAO;
import javafx.collections.transformation.SortedList;
import model.Evento;
import model.Inscricao;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoServico {
    EventoDAO eventoDAO = new EventoDAO();

    public void cadastrar(String nome, String descricao, String endereco, LocalDate dataInicio, String horaInicio, LocalDate dataFim, String horaFim, int capacidade) {
        Evento evento = new Evento(nome, descricao, endereco, dataInicio, horaInicio, dataFim, horaFim, capacidade);
        eventoDAO.inserir(evento);
    }

    public void remover(Evento evento) {
        eventoDAO.remover(evento);
    }

    public void alterar(Evento evento, String nome, String descricao, String endereco, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim, int capacidade) {

        evento.setNome(nome);
        evento.setDescricao(descricao);
        evento.setEndereco(endereco);
        evento.setDataInicio(dataInicio);
        evento.setHoraInicio(horaInicio);
        evento.setDataFim(dataFim);
        evento.setHoraFim(horaFim);
        evento.setCapacidade(capacidade);

        eventoDAO.alterar(evento);
    }

    public void gerarCSV(SortedList<Evento> lista, File destino) {
        try (PrintWriter writer = new PrintWriter(destino)) {

            // Cabeçalho
            writer.println("Título;Descrição;Tipo;Data Início;Hora Início;Data Fim;Hora Fim;Status");

            // Linhas
            for (Evento evento : lista) {
                writer.println(evento.getNome() + ";" +
                        evento.getDescricao() + ";" +
                        evento.getEndereco() + ";" +
                        evento.getDataInicio() + ";" +
                        evento.getHoraInicio() + ";" +
                        evento.getDataFim() + ";" +
                        evento.getHoraFim() + ";" +
                        evento.getCapacidade()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarExcel(SortedList<Evento> lista, File destino) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sessões");

        // Cabeçalho
        Row header = sheet.createRow(0);
        String[] colunas = {
                "Título", "Descrição", "Tipo", "Data Início", "Hora Início",
                "Data Fim", "Hora Fim", "Status"
        };

        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        // Conteúdo
        int rowIndex = 1;
        for (Evento evento : lista) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(evento.getNome());
            row.createCell(1).setCellValue(evento.getDescricao());
            row.createCell(2).setCellValue(String.valueOf(evento.getEndereco()));
            row.createCell(3).setCellValue(String.valueOf(evento.getDataInicio()));
            row.createCell(4).setCellValue(String.valueOf(evento.getHoraInicio()));
            row.createCell(5).setCellValue(String.valueOf(evento.getDataFim()));
            row.createCell(6).setCellValue(String.valueOf(evento.getHoraFim()));
            row.createCell(7).setCellValue(String.valueOf(evento.getCapacidade()));
        }

        // Auto ajustar
        for (int i = 0; i < colunas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Salvar arquivo
        try (FileOutputStream fileOut = new FileOutputStream(destino)) {
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
