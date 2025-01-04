import model.TSPModel;
import view.TSPView;
import view.PetriNetView;
import controller.TSPController;

public class Main {
    public static void main(String[] args) {

        TSPModel model = new TSPModel();


        int[][] matrix = {
                {0, 2, 9, 10},
                {1, 0, 6, 4},
                {15, 7, 0, 8},
                {6, 3, 12, 0}
        };
        String[] cityNames = {"New York", "Paris", "Tokyo", "London"};

        model.setDistanceMatrix(matrix);
        model.setCityNames(cityNames);


        TSPView view = new TSPView();


        PetriNetView petriNetView = new PetriNetView();
        TSPController controller = new TSPController(model, view, petriNetView);


        controller.run();
    }
}

