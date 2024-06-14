package com.project.CheapCheese.model.generators;

import com.project.CheapCheese.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

/*
 *   TODA LA CLASE INTRODUCIDA AQUÍ ES BUSCADA A TRAVÉS DE INTERNET, QUEDA EN CONCIENCIA QUE NO HE TRABAJADO MUCHO CON
 *   EXTRACIONES DE DATOS DE UNA BASE DE DATOS, ENTONCES HE RECURRIDO A BUSCAR INFORMACIÓN SOBRE ESTO.
 *   LIBRERIAS ENCAGADAS EN EL ARCHIVO POM.XML
 */

public class GeneralExcelGenerator {

    private List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public GeneralExcelGenerator(List<Product> productList) {
        this.productList = productList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Productos");

        var row = sheet.createRow(0);
        var style = workbook.createCellStyle();
        var font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Precio", style);
        createCell(row, 3, "Tipo", style);
        createCell(row, 4, "Tienda", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);

        var cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        var rowCount = 1;
        var identificador = 1;
        var style = workbook.createCellStyle();
        var font = workbook.createFont();

        font.setFontHeight(14);
        style.setFont(font);
        for (Product record : productList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, identificador++, style);
            createCell(row, columnCount++, record.getNombre(), style);
            createCell(row, columnCount++, record.getPrecio(), style);
            createCell(row, columnCount++, record.getTipo(), style);
            createCell(row, columnCount++, record.getTienda(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();

        var outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
