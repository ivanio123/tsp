package view;

import model.TSPModel;
import javax.swing.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class TSPView {
    private JFrame tableFrame;
    private JFrame mainFrame;

    public int showMainMenu() {
        String[] options = {"Use Example", "Input Matrix Manually", "Import Matrix from CSV", "Generate Random Matrix",};
        return JOptionPane.showOptionDialog(null, "Choose an option:", "Main Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    }
    public void showExecutionTime(long executionTime, String algorithm) {
        JOptionPane.showMessageDialog(null,
                "Execution time for " + algorithm + " algorithm: " + executionTime + " ms",
                "Execution Time",
                JOptionPane.INFORMATION_MESSAGE);
    }




    public int[][] getManualMatrixInput(String[] cityNamesRef) {
        String sizeInput = JOptionPane.showInputDialog("Enter the number of cities:");
        int size = Integer.parseInt(sizeInput);

        String[] cityNames = new String[size];
        for (int i = 0; i < size; i++) {
            cityNames[i] = JOptionPane.showInputDialog("Enter the name of city " + (i + 1) + ":");
        }
        cityNamesRef[0] = String.join(",", cityNames);



        String[] columnNames = new String[size + 1];
        columnNames[0] = "Cities"; // Перший стовпець для назв міст
        System.arraycopy(cityNames, 0, columnNames, 1, size);


        DefaultTableModel tableModel = new DefaultTableModel(columnNames, size);


        for (int i = 0; i < size; i++) {
            tableModel.setValueAt(cityNames[i], i, 0);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    tableModel.setValueAt("0", i, j + 1);
                } else {
                    tableModel.setValueAt("", i, j + 1);
                }
            }
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        while (true) {

            int result = JOptionPane.showConfirmDialog(null, scrollPane,
                    "Enter distances (-1 for no path)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Input canceled. No matrix created.");
                return null;
            }


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
                        matrix[i][j] = 0;
                    }
                }
                if (!allDistancesFilled) break;
            }

            if (allDistancesFilled) {
                return matrix;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please fill in all distances correctly!\nEnsure all fields are filled and contain valid numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);

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
    public int showSaveFormatMenu() {
        String[] options = {"Save as CSV", "Save as XLSX"};
        return JOptionPane.showOptionDialog(null, "Choose format to save the route:", "Save Route",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
    public String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    public void displayRoute(String[] cityNames, int[] bestRoute, int[][] distanceMatrix, int totalCost, ActionListener saveListener) {
        tableFrame = new JFrame("Optimal Route");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setLayout(new BorderLayout());

        if (tableFrame != null && tableFrame.isVisible()) {
            tableFrame.toFront();
            tableFrame.requestFocus();
            return;
        }
        tableFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tableFrame.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

        String[] columnNames = {"City", "Next City", "Distance"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (int i = 0; i < bestRoute.length - 1; i++) {
            String currentCity = cityNames[bestRoute[i]];
            String nextCity = cityNames[bestRoute[i + 1]];
            int distance = distanceMatrix[bestRoute[i]][bestRoute[i + 1]];
            model.addRow(new Object[]{currentCity, nextCity, distance});
        }


        String lastCity = cityNames[bestRoute[bestRoute.length - 1]];
        String firstCity = cityNames[bestRoute[0]];
        int lastDistance = distanceMatrix[bestRoute[bestRoute.length - 1]][bestRoute[0]];
        model.addRow(new Object[]{lastCity, firstCity, lastDistance});

        JTable routeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(routeTable);


        JLabel totalCostLabel = new JLabel("Total Cost: " + totalCost);
        totalCostLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Додати кнопку "Зберегти маршрут"
        JButton saveButton = new JButton("Save Route");
        saveButton.addActionListener(saveListener);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalCostLabel, BorderLayout.CENTER);
        bottomPanel.add(saveButton, BorderLayout.EAST);


        tableFrame.add(scrollPane, BorderLayout.CENTER);
        tableFrame.add(bottomPanel, BorderLayout.SOUTH);

        tableFrame.pack();
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    public void closeRouteTable() {
        if (tableFrame != null) {
            tableFrame.dispose();
        }
    }
}
