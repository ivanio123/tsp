package model;

public class NearestNeighborAlgorithm {
    public int[] nearestNeighborAlgorithm(int[][] distanceMatrix) {
        int size = distanceMatrix.length;
        boolean[] visited = new boolean[size];
        int[] tour = new int[size + 1];
        int currentCity = 0;

        tour[0] = currentCity;
        visited[currentCity] = true;

        for (int i = 1; i < size; i++) {
            int nearestCity = -1;
            int nearestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < size; j++) {
                if (!visited[j] && distanceMatrix[currentCity][j] < nearestDistance) {
                    nearestDistance = distanceMatrix[currentCity][j];
                    nearestCity = j;
                }
            }

            tour[i] = nearestCity;
            visited[nearestCity] = true;
            currentCity = nearestCity;
        }

        tour[size] = tour[0];
        return tour;
    }

    public int calculateTotalDistance(int[] tour, int[][] distanceMatrix) {
        int totalDistance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            if (distanceMatrix[tour[i]][tour[i + 1]] == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE; // Return a large value if an invalid path is detected
            }
            totalDistance += distanceMatrix[tour[i]][tour[i + 1]];
        }
        return totalDistance;
    }
}

