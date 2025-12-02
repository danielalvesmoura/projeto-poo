package servico;

import javafx.stage.FileChooser;
import util.Global;
import model.Exportavel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class ExportarServico<T> {

    public void escolheTipo(List<Exportavel> lista, String tipo) {
        System.out.println("Tipo escolhido: " + tipo);

        if(tipo.equals("CSV")) {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File arquivo = fc.showSaveDialog(null);

            if (arquivo != null) {

                gerarCSV(lista, arquivo);
                Global.mostraMensagem("Portal X","Relatório CSV exportado com sucesso!");
            }
        } else {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
            File arquivo = fc.showSaveDialog(null);

            if (arquivo != null) {
                gerarExcel(lista, arquivo);
                Global.mostraMensagem("Portal X","Relatório Excel exportado com sucesso!");
            }
        }
    }

    public static void gerarCSV(List<Exportavel> lista, File arquivo) {
        try {
            FileWriter writer = new FileWriter(arquivo);

            writer.write(lista.get(0).getCabecalho() + "\n");

            for(Exportavel e : lista) {
                writer.write(e.getCorpo() + "\n");
            }

            writer.close();

        } catch (Exception e) {
            Global.mostraErro("Não foi possível exportar tabela.");
        }


    }

    public void gerarExcel(List<Exportavel> lista, File destino) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(lista.get(0).getClass().toString());

        // Cabeçalho
        Row header = sheet.createRow(0);
        String[] colunas = lista.get(0).getCabecalhoVetor();

        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        // Conteúdo
        int rowIndex = 1;
        for (Exportavel objeto : lista) {
            Row row = sheet.createRow(rowIndex++);

            String[] corpo = objeto.getCorpoVetor();

            for(int i = 0; i < corpo.length; i++) {
                row.createCell(i).setCellValue(corpo[i]);
            }

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
