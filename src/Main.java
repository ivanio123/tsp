import model.TSPModel;
import view.TSPView;
import view.PetriNetView;
import controller.TSPController;

public class Main {
    public static void main(String[] args) {
        // Створити модель
        TSPModel model = new TSPModel();

        // Ініціалізувати матрицю відстаней та назви міст
        /* int[][] matrix = {
                {0, 2, 9, 10},
                {1, 0, 6, 4},
                {15, 7, 0, 8},
                {6, 3, 12, 0}
        };
        String[] cityNames = {"New York", "Paris", "Tokyo", "London"};

        model.setDistanceMatrix(matrix); // Оновлено метод
        model.setCityNames(cityNames);   // Додано окремий виклик для імен міст*/

        // Створити вид
        TSPView view = new TSPView();

        // Створити контролер (з PetriNetView)
        PetriNetView petriNetView = new PetriNetView();
        TSPController controller = new TSPController(model, view, petriNetView);

        // Запустити програму
        controller.run(); // Викликаємо основний метод запуску
    }
}

