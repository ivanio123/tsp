package controller;

import model.TSPModel;
import model.NearestNeighborAlgorithm;
import model.TwoOptAlgorithm;
import model.ThreeOptAlgorithm;
import view.TSPView;
import view.PetriNetView;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TSPController {
    private TSPModel model;
    private TSPView view;
    private PetriNetView petriNetView;

    public TSPController(TSPModel model, TSPView view, PetriNetView petriNetView) {
        this.model = model;
        this.view = view;
        this.petriNetView = petriNetView;
    }

    public void run() {
        while (true) {
            int choice = view.showMainMenu();

            switch (choice) {
                case 0:
                    // Введення розміру графа
                    int size = view.getInputGraphSize();
                    model.initializeMatrix(size);
                    view.showResult("Graph size set to: " + size);
                    break;

                case 1:
                    // Ручний ввід матриці відстаней
                    String[] cityNames = null;
                    int[][] manualMatrix = view.getManualMatrixInput(cityNames);
                    model.setDistanceMatrix(manualMatrix);
                    view.showResult("Distance matrix updated successfully.");
                    break;

                case 2:
                    // Імпорт матриці із CSV
                    try {
                        String filePath = view.getCSVFilePath();
                        int[][] importedMatrix = model.importMatrixFromCSV(filePath);
                        model.setDistanceMatrix(importedMatrix);
                        view.showResult("Distance matrix imported successfully from " + filePath);
                    } catch (Exception e) {
                        view.showResult("Failed to import matrix: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Запуск алгоритму "Найближчого сусіда"
                    runNearestNeighbor();
                    break;

                default:
                    view.showResult("Invalid choice. Exiting.");
                    return;
            }

            if (model.getDistanceMatrix() == null) {
                view.showResult("Failed to initialize distance matrix. Exiting.");
                return;
            }

            // Ініціалізація маршруту
            int[] initialTour = new int[model.getDistanceMatrix().length + 1];
            for (int i = 0; i < model.getDistanceMatrix().length; i++) {
                initialTour[i] = i;
            }
            initialTour[model.getDistanceMatrix().length] = 0;

            // Вибір алгоритму
            choice = view.showAlgorithmMenu();

            switch (choice) {
                case 0:
                    runNearestNeighbor();
                    break;
                case 1:
                    run2Opt(initialTour);
                    break;
                case 2:
                    run3Opt(initialTour);
                    break;
                case 3:
                    runAndCompareOptAlgorithms(initialTour);
                    break;
                default:
                    view.showResult("Invalid choice. Exiting.");
            }
        }
    }


    private void runNearestNeighbor() {
        NearestNeighborAlgorithm nn = new NearestNeighborAlgorithm();
        int[] tour = nn.nearestNeighborAlgorithm(model.getDistanceMatrix());

        view.showResult("Nearest Neighbor Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "Nearest Neighbor");

        showRouteTable(tour);
    }

    private void run2Opt(int[] initialTour) {
        TwoOptAlgorithm twoOpt = new TwoOptAlgorithm();
        int[] tour = twoOpt.twoOptAlgorithm(initialTour, model.getDistanceMatrix());

        view.showResult("2-Opt Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "2-Opt");

        showRouteTable(tour);
    }

    private void run3Opt(int[] initialTour) {
        ThreeOptAlgorithm threeOpt = new ThreeOptAlgorithm();
        int[] tour = threeOpt.threeOptAlgorithm(initialTour, model.getDistanceMatrix());

        view.showResult("3-Opt Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "3-Opt");

        showRouteTable(tour);
    }

    private void runAndCompareOptAlgorithms(int[] initialTour) {
        runNearestNeighbor();
        run2Opt(initialTour);
        run3Opt(initialTour);
    }

    private void displayAndSavePetriNet(int[] tour, String algorithm) {
        Graph<String, DefaultEdge> petriNet = createPetriNet(model.getDistanceMatrix(), tour);
        petriNetView.displayPetriNet(petriNet, algorithm + " Petri Net");
        petriNetView.savePetriNetImage(petriNet, algorithm + "_Petri_Net.png");
    }

    private Graph<String, DefaultEdge> createPetriNet(int[][] distanceMatrix, int[] tour) {
        Graph<String, DefaultEdge> petriNet = new org.jgrapht.graph.SimpleGraph<>(DefaultEdge.class);
        int tCount = 0;

        for (int i = 0; i < distanceMatrix.length; i++) {
            petriNet.addVertex("P" + i);
        }

        for (int i = 0; i < tour.length - 1; i++) {
            String fromPlace = "P" + tour[i];
            String toPlace = "P" + tour[i + 1];

            petriNet.addVertex("t" + tCount);
            petriNet.addEdge(fromPlace, "t" + tCount);
            petriNet.addEdge("t" + tCount, toPlace);

            tCount++;
        }

        return petriNet;
    }

    private void showRouteTable(int[] tour) {
        String[] columnNames = {"City Number", "City Name", "Distance to Previous"};
        DefaultTableModel modelTable = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(modelTable);

        int totalDistance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            int from = tour[i];
            int to = tour[i + 1];
            int distance = model.getDistanceMatrix()[from][to];

            totalDistance += distance;
            modelTable.addRow(new Object[]{i, model.getCityNames()[tour[i]], distance});
        }

        JFrame tableFrame = new JFrame("Route Table");
        tableFrame.setSize(400, 300);
        tableFrame.add(new JScrollPane(table), BorderLayout.CENTER);

        JLabel summaryLabel = new JLabel("Total Distance: " + totalDistance, SwingConstants.CENTER);
        tableFrame.add(summaryLabel, BorderLayout.SOUTH);

        tableFrame.setVisible(true);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
