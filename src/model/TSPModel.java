package model;

import java.io.FileWriter;
import java.io.IOException;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSPModel {
    private int[][] distanceMatrix;
    private String[] cityNames;
    private int[] bestRoute; // Знайдений маршрут
    private int totalCost; // Загальна вартість маршруту

    public void initializeMatrix(int size) {
        distanceMatrix = new int[size][size];
        cityNames = new String[size];
        for (int i = 0; i < size; i++) {
            cityNames[i] = "City " + (i + 1);
        }
    }

    public void setDistanceMatrix(int[][] matrix) {
        this.distanceMatrix = matrix;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public String[] getCityNames() {
        return cityNames;
    }

    public void setCityNames(String[] names) {
        this.cityNames = names;
    }
    public int[] getBestRoute() {
        // Якщо маршрут зберігається як поле в класі TSPModel (наприклад, bestRoute)
        return bestRoute;
    }
    public int getTotalCost() {
        // Якщо загальна вартість зберігається як поле в класі TSPModel (наприклад, totalCost)
        return totalCost;
    }

    public void generateRandomMatrix(int size, int maxDistance) {
        distanceMatrix = new int[size][size];
        cityNames = new String[size];

        for (int i = 0; i < size; i++) {
            cityNames[i] = "City " + (i + 1);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0; // Відстань до себе — 0
                } else {
                    distanceMatrix[i][j] = (int) (Math.random() * maxDistance) + 1; // Генерація випадкової відстані
                }
            }
        }
    }

    public int[][] importMatrixFromCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int size = 0;
            while ((line = br.readLine()) != null) {
                size++;
            }
            initializeMatrix(size);

            BufferedReader brAgain = new BufferedReader(new FileReader(filePath));
            int row = 0;
            while ((line = brAgain.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < values.length; col++) {
                    distanceMatrix[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
        }
        return distanceMatrix;
    }
    public void saveRouteToCSV(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("City, Next City, Distance\n");

            for (int i = 0; i < bestRoute.length - 1; i++) {
                int currentCity = bestRoute[i];
                int nextCity = bestRoute[i + 1];
                writer.append(cityNames[currentCity]).append(", ")
                        .append(cityNames[nextCity]).append(", ")
                        .append(String.valueOf(distanceMatrix[currentCity][nextCity])).append("\n");
            }
            // Додати зворотний шлях до початкового міста
            int lastCity = bestRoute[bestRoute.length - 1];
            writer.append(cityNames[lastCity]).append(", ")
                    .append(cityNames[bestRoute[0]]).append(", ")
                    .append(String.valueOf(distanceMatrix[lastCity][bestRoute[0]])).append("\n");

            writer.append("Total Cost:, ").append(String.valueOf(totalCost)).append("\n");
        }
    }

   /* public void saveRouteToXLSX(String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Best Route");

        // Заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("City");
        headerRow.createCell(1).setCellValue("Next City");
        headerRow.createCell(2).setCellValue("Distance");

        // Дані маршруту
        for (int i = 0; i < bestRoute.length - 1; i++) {
            Row row = sheet.createRow(i + 1);
            int currentCity = bestRoute[i];
            int nextCity = bestRoute[i + 1];

            row.createCell(0).setCellValue(cityNames[currentCity]);
            row.createCell(1).setCellValue(cityNames[nextCity]);
            row.createCell(2).setCellValue(distanceMatrix[currentCity][nextCity]);
        }

        // Зворотний шлях
        int lastCity = bestRoute[bestRoute.length - 1];
        Row lastRow = sheet.createRow(bestRoute.length);
        lastRow.createCell(0).setCellValue(cityNames[lastCity]);
        lastRow.createCell(1).setCellValue(cityNames[bestRoute[0]]);
        lastRow.createCell(2).setCellValue(distanceMatrix[lastCity][bestRoute[0]]);

        // Загальна вартість
        Row costRow = sheet.createRow(bestRoute.length + 1);
        costRow.createCell(0).setCellValue("Total Cost:");
        costRow.createCell(1).setCellValue(totalCost);

        // Збереження файлу
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }*/

}
