package model;

public class TwoOptAlgorithm {

    public int[] twoOptAlgorithm(int[] tour, int[][] distanceMatrix) {
        int n = tour.length;
        boolean improvement = true;

        while (improvement) {
            improvement = false;
            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    if (isFeasible(tour, distanceMatrix, i, j) && calculateGain(tour, distanceMatrix, i, j) > 0) {
                        reverseSegment(tour, i, j);
                        improvement = true;
                    }
                }
            }
        }

        return tour;
    }

    private boolean isFeasible(int[] tour, int[][] distanceMatrix, int i, int j) {
        int a = tour[i - 1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j + 1];

        return distanceMatrix[a][c] != Integer.MAX_VALUE &&
                distanceMatrix[b][d] != Integer.MAX_VALUE;
    }

    private int calculateGain(int[] tour, int[][] distanceMatrix, int i, int j) {
        int a = tour[i - 1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j + 1];

        int currentDistance = distanceMatrix[a][b] + distanceMatrix[c][d];
        int newDistance = distanceMatrix[a][c] + distanceMatrix[b][d];

        return currentDistance - newDistance;
    }

    private void reverseSegment(int[] tour, int i, int j) {
        while (i < j) {
            int temp = tour[i];
            tour[i] = tour[j];
            tour[j] = temp;
            i++;
            j--;
        }
    }

    public int calculateTotalDistance(int[] tour, int[][] distanceMatrix) {
        int totalDistance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            int dist = distanceMatrix[tour[i]][tour[i + 1]];
            if (dist == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            totalDistance += dist;
        }
        return totalDistance;
    }
}

