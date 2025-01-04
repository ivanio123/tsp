import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MatrixGenerator {


    public static void generateAdjacencyMatrixToCSV(int size, int maxDistance, String filePath) {
        if (size <= 1) {
            throw new IllegalArgumentException("Size of the matrix must be greater than 1.");
        }
        if (maxDistance <= 0) {
            throw new IllegalArgumentException("Maximum distance must be greater than 0.");
        }

        Random random = new Random();
        int[][] matrix = new int[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = random.nextInt(maxDistance) + 1;
                }
            }
        }

        // Запис у CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    writer.append(String.valueOf(matrix[i][j]));
                    if (j < size - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            System.out.println("Matrix saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing matrix to file: " + e.getMessage());
        }
    }

    // Тестовий запуск
    public static void main(String[] args) {
        generateAdjacencyMatrixToCSV(5, 100, "matrix.csv");
    }
}
