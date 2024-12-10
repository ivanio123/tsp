package controller;

import model.TSPModel;
import model.NearestNeighborAlgorithm;
import model.TwoOptAlgorithm;
import model.ThreeOptAlgorithm;
import view.TSPView;
import view.PetriNetView;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

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
        int choice = view.showMainMenu();

        switch (choice) {
            case 1:
                // handle input size of the graph
                break;
            case 2:
                // handle input distance matrix manually
                break;
            case 3:
                // handle import distance matrix from CSV
                break;
            case 4:
                // handle use example incomplete graph
                break;
            default:
                view.showResult("Invalid choice. Exiting.");
                return;
        }

        if (model.getDistanceMatrix() == null) {
            view.showResult("Failed to initialize distance matrix. Exiting.");
            return;
        }

        int[] initialTour = new int[model.getDistanceMatrix().length + 1];
        for (int i = 0; i < model.getDistanceMatrix().length; i++) {
            initialTour[i] = i;
        }
        initialTour[model.getDistanceMatrix().length] = 0; // Return to the start

        choice = view.showAlgorithmMenu();

        switch (choice) {
            case 1:
                runNearestNeighbor();
                break;
            case 2:
                run2Opt(initialTour);
                break;
            case 3:
                run3Opt(initialTour);
                break;
            case 4:
                runAndCompareOptAlgorithms(initialTour);
                break;
            default:
                view.showResult("Invalid choice. Exiting.");
        }
    }

    private void runNearestNeighbor() {
        NearestNeighborAlgorithm nn = new NearestNeighborAlgorithm();
        int[] tour = nn.nearestNeighbor(model.getDistanceMatrix());
        view.showResult("Nearest Neighbor Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "Nearest Neighbor");
    }

    private void run2Opt(int[] initialTour) {
        TwoOptAlgorithm twoOpt = new TwoOptAlgorithm();
        int[] tour = twoOpt.twoOpt(initialTour, model.getDistanceMatrix());
        view.showResult("2-Opt Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "2-Opt");
    }

    private void run3Opt(int[] initialTour) {
        ThreeOptAlgorithm threeOpt = new ThreeOptAlgorithm();
        int[] tour = threeOpt.threeOpt(initialTour, model.getDistanceMatrix());
        view.showResult("3-Opt Tour: " + java.util.Arrays.toString(tour));
        displayAndSavePetriNet(tour, "3-Opt");
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
        Graph<String, DefaultEdge> petriNet = new SimpleDirectedGraph<>(DefaultEdge.class);
        int tCount = 0; // To keep track of the number of transitions created

        // Add places (vertices)
        for (int i = 0; i < distanceMatrix.length; i++) {
            petriNet.addVertex("P" + i);
        }

        // Add transitions and edges
        for (int i = 0; i < tour.length - 1; i++) {
            String fromPlace = "P" + tour[i];
            String toPlace = "P" + tour[i + 1];

            petriNet.addVertex("t" + tCount); // Add transition

            petriNet.addEdge(fromPlace, "t" + tCount);
            petriNet.addEdge("t" + tCount, toPlace);
            tCount++;
        }

        return petriNet;
    }
}
