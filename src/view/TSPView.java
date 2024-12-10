package view;

import model.TSPModel;
import javax.swing.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TSPView {
    public int showMainMenu() {
        String[] options = {"Set Graph Size", "Input Matrix Manually", "Import Matrix from CSV", "Run Nearest Neighbor"};
        return JOptionPane.showOptionDialog(null, "Choose an option:", "Main Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    /*public int[][] getManualMatrixInput(String[] cityNames) {
        // Введення розміру матриці та назв міст
        String sizeInput = JOptionPane.showInputDialog("Enter the number of cities:");
        int size = Integer.parseInt(sizeInput);

        cityNames = new String[size];
        for (int i = 0; i < size; i++) {
            cityNames[i] = JOptionPane.showInputDialog("Enter the name of city " + (i + 1) + ":");
        }

        // Ініціалізація матриці відстаней
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 0; // Відстань до себе завжди 0
                } else {
                    String input = JOptionPane.showInputDialog(
                            "Enter distance from " + cityNames[i] + " to " + cityNames[j] + " (enter -1 if no path exists):");
                    int distance = Integer.parseInt(input);
                    matrix[i][j] = (distance == -1) ? Integer.MAX_VALUE : distance;
                }
            }
        }

        return matrix;
    }*/


    public int[][] getManualMatrixInput(String[] cityNames) {
        // Введення розміру матриці
        String sizeInput = JOptionPane.showInputDialog("Enter the number of cities:");
        int size = Integer.parseInt(sizeInput);

        // Введення назв міст
        cityNames = new String[size];
        for (int i = 0; i < size; i++) {
            cityNames[i] = JOptionPane.showInputDialog("Enter the name of city " + (i + 1) + ":");
        }

        // Ініціалізація заголовків стовпців
        String[] columnNames = new String[size + 1];
        columnNames[0] = "Cities"; // Перший стовпець для назв міст
        System.arraycopy(cityNames, 0, columnNames, 1, size);

        // Ініціалізація моделі таблиці
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, size);

        // Заповнення початкових значень таблиці
        for (int i = 0; i < size; i++) {
            tableModel.setValueAt(cityNames[i], i, 0); // Назви міст у першій колонці
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    tableModel.setValueAt("0", i, j + 1); // Відстань до себе — 0
                } else {
                    tableModel.setValueAt("", i, j + 1); // Порожнє значення для вводу
                }
            }
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        while (true) {
            // Відображення таблиці у вікні
            int result = JOptionPane.showConfirmDialog(null, scrollPane,
                    "Enter distances (-1 for no path)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Input canceled. No matrix created.");
                return null; // Скасування вводу
            }

            // Перевірка на повноту заповнення таблиці
            boolean allDistancesFilled = true;
            int[][] matrix = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        String value = (String) table.getValueAt(i, j + 1);
                        if (value == null || value.trim().isEmpty()) {
                            allDistancesFilled = false;
                            break;
                        }
                        try {
                            int distance = Integer.parseInt(value.trim());
                            matrix[i][j] = (distance == -1) ? Integer.MAX_VALUE : distance;
                        } catch (NumberFormatException e) {
                            allDistancesFilled = false;
                            break;
                        }
                    } else {
                        matrix[i][j] = 0; // Відстань до себе — 0
                    }
                }
                if (!allDistancesFilled) break;
            }

            if (allDistancesFilled) {
                return matrix; // Успішне створення матриці
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please fill in all distances correctly!\nEnsure all fields are filled and contain valid numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                // Повернення до редагування таблиці
            }
        }

    }




    public String getCSVFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public int getInputGraphSize() {
        String input = JOptionPane.showInputDialog("Enter the size of the graph:");
        return Integer.parseInt(input);
    }

    public void showResult(String message) {
        JOptionPane.showMessageDialog(null, message, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public int showAlgorithmMenu() {
        String[] options = {"Nearest Neighbor", "2-Opt", "3-Opt", "Compare All"};
        return JOptionPane.showOptionDialog(null, "Choose an algorithm:", "Algorithm Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}
