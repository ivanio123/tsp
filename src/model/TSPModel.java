package model;

public class TSPModel {
    private int[][] distanceMatrix;

    public TSPModel(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
}
