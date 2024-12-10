import model.TSPModel;
import view.TSPView;
import view.PetriNetView;
import controller.TSPController;

public class Main {
    public static void main(String[] args) {
        TSPView view = new TSPView();
        PetriNetView petriNetView = new PetriNetView();
        TSPModel model = new TSPModel(null); // Initialize with null, will be set later

        TSPController controller = new TSPController(model, view, petriNetView);
        controller.run();
    }
}
