package view;

import javax.swing.*;
import java.util.Scanner;

public class TSPView {
    private Scanner scanner;

    public TSPView() {
        this.scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("Traveling Salesman Problem with Nearest Neighbor, 2-Opt, and 3-Opt Algorithms");
        System.out.println("1. Input the size of the graph");
        System.out.println("2. Input the distance matrix manually");
        System.out.println("3. Import the distance matrix from CSV");
        System.out.println("4. Use example incomplete graph");
        return scanner.nextInt();
    }

    public int showAlgorithmMenu() {
        System.out.println("Choose the algorithm to run:");
        System.out.println("1. Nearest Neighbor");
        System.out.println("2. 2-Opt");
        System.out.println("3. 3-Opt");
        System.out.println("4. Run all and compare");
        return scanner.nextInt();
    }

    public void showResult(String result) {
        System.out.println(result);
    }

    public void showPetriNetImage(JFrame frame) {
        frame.setVisible(true);
    }

    public Scanner getScanner() {
        return scanner;
    }
}
