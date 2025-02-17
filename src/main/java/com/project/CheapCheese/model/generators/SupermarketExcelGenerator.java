package com.project.CheapCheese.model.generators;

import com.project.CheapCheese.model.Product;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xssf.usermodel.*;


public class SupermarketExcelGenerator {

    private List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public SupermarketExcelGenerator(List<Product> productList) {
        this.productList = productList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Products");

        var row = sheet.createRow(0);
        var style = workbook.createCellStyle();
        var font = workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        createCell(row, 0, "Supermercado", style);
        createCell(row, 1, "Precio Máximo", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);

        var cell = row.createCell(columnCount);

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
        var maxPricesByMarket = new HashMap<String, Double>();

        // Calcular el precio máximo para cada tipo
        for (Product product : productList) {
            String supermarket = product.getTienda();
            double price = product.getPrecio();
            if (!maxPricesByMarket.containsKey(supermarket) || price > maxPricesByMarket.get(supermarket)) {
                maxPricesByMarket.put(supermarket, price);
            }
        }

        // Escribir los datos en la hoja de cálculo
        Set<String> supermarkets = maxPricesByMarket.keySet();
        int rowNumber = 1;
        for (String supermarket : supermarkets) {
            Row row = sheet.createRow(rowNumber++);
            createCell(row, 0, supermarket, null);
            createCell(row, 1, maxPricesByMarket.get(supermarket), null);
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
        bottomAxis.setTitle("Supermercado");

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

        var outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
