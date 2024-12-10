package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSPModel {
    private int[][] distanceMatrix;
    private String[] cityNames;

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
}
