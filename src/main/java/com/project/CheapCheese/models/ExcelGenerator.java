package com.project.CheapCheese.models;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.project.CheapCheese.models.classes.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
*   TODA LA CLASE INTRODUCIDA AQUÍ ES BUSCADA A TRAVÉS DE INTERNET, QUEDA EN CONCIENCIA QUE NO HE TRABAJADO MUCHO CON
*   EXTRACIONES DE DATOS DE UNA BASE DE DATOS, ENTONCES HE RECURRIDO A BUSCAR INFORMACIÓN SOBRE ESTO.
*   LIBRERIAS ENCAGADAS EN EL ARCHIVO POM.XML
*/

public class ExcelGenerator {

    private List < Product > productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List < Product > productList) {
        this.productList = productList;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Products");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 3, "Precio", style);
        createCell(row, 4, "Tipo", style);
        createCell(row, 5, "Tienda", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        }  else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        int identificador = 1 ;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Product record: productList) {
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
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
