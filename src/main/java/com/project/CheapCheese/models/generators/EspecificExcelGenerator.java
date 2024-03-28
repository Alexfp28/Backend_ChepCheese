package com.project.CheapCheese.models.generators;

import com.project.CheapCheese.models.classes.Product;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xssf.usermodel.*;


public class EspecificExcelGenerator {

    private List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public EspecificExcelGenerator(List<Product> productList) {
        this.productList = productList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Products");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        createCell(row, 0, "Tipo", style);
        createCell(row, 1, "Precio Máximo", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);

        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);

        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);

        } else if (value instanceof String) {
            cell.setCellValue((String) value);

        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);

        } else {
            cell.setCellValue((Boolean) value);
        }
        cell.setCellStyle(style);
    }

    private void writeData() {
        Map<String, Double> maxPricesByType = new HashMap<>();

        // Calcular el precio máximo para cada tipo
        for (Product product : productList) {
            String type = product.getTipo();
            double price = product.getPrecio();
            if (!maxPricesByType.containsKey(type) || price > maxPricesByType.get(type)) {
                maxPricesByType.put(type, price);
            }
        }

        // Escribir los datos en la hoja de cálculo
        Set<String> types = maxPricesByType.keySet();
        int rowNumber = 1;
        for (String type : types) {
            Row row = sheet.createRow(rowNumber++);
            createCell(row, 0, type, null);
            createCell(row, 1, maxPricesByType.get(type), null);
        }
    }

    private void addChart() {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 0, 20, 15);
        XDDFChart chart = drawing.createChart(anchor);

        // Definir el rango de datos para el gráfico
        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, productList.size(), 0, 0));
        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, productList.size(), 1, 1));

        // Crear eje de categoría (X)
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Tipo");

        // Crear eje de valores (Y)
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Precio Máximo");

        // Crear el gráfico de líneas
        XDDFBar3DChartData data = (XDDFBar3DChartData) chart.createData(ChartTypes.BAR3D, bottomAxis, leftAxis);
        data.setBarDirection(BarDirection.COL);
        data.addSeries(categories, values);
        chart.plot(data);

    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        writeData();
        addChart();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
