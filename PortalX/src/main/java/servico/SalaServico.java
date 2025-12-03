package servico;

import dao.SalaDAO;
import dao.SalaDAO;
import javafx.collections.transformation.SortedList;
import model.Sala;
import model.Sala;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class SalaServico {
    SalaDAO salaDAO = new SalaDAO();

    public void cadastrar(String nome, int capacidade, String localizacao) {
        Sala sala = new Sala(nome, capacidade, localizacao);
        salaDAO.inserir(sala);
    }

    public void remover(Sala sala) {
        salaDAO.remover(sala);
    }

    public void alterar(Sala sala, String nome, int capacidade, String localizacao) {

        sala.setNome(nome);
        sala.setCapacidade(capacidade);
        sala.setLocalizacao(localizacao);

        salaDAO.alterar(sala);
    }

    public void gerarCSV(SortedList<Sala> lista, File destino) {
        try (PrintWriter writer = new PrintWriter(destino)) {

            // Cabeçalho
            writer.println("Nome;Tipo;Capacidade;Localização");

            // Linhas
            for (Sala sala : lista) {
                writer.println(sala.getNome() + ";" +
                        sala.getCapacidade() + ";" +
                        sala.getLocalizacao() + ";"

                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarExcel(SortedList<Sala> lista, File destino) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sessões");

        // Cabeçalho
        Row header = sheet.createRow(0);
        String[] colunas = {"Nome", "Capacidade", "Localização"
        };

        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        // Conteúdo
        int rowIndex = 1;
        for (Sala sala : lista) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(sala.getNome());
            row.createCell(1).setCellValue(sala.getLocalizacao());
            row.createCell(7).setCellValue(String.valueOf(sala.getCapacidade()));
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
