package model;

import java.util.Arrays;

public class ThreeOptAlgorithm {

    public int[] threeOptAlgorithm(int[] tour, int[][] distanceMatrix) {
        int n = tour.length;
        boolean improvement = true;

        while (improvement) {
            improvement = false;
            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        if (isFeasible(tour, distanceMatrix, i, j, k) && calculateGain(tour, distanceMatrix, i, j, k) > 0) {
                            reverseSegment(tour, i, j, k);
                            improvement = true;
                        }
                    }
                }
            }
        }

        return tour;
    }

    private boolean isFeasible(int[] tour, int[][] distanceMatrix, int i, int j, int k) {
        int a = tour[i - 1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j + 1];
        int e = tour[k];
        int f = tour[(k + 1) % tour.length];

        return distanceMatrix[a][c] != Integer.MAX_VALUE &&
                distanceMatrix[b][d] != Integer.MAX_VALUE &&
                distanceMatrix[c][e] != Integer.MAX_VALUE &&
                distanceMatrix[d][f] != Integer.MAX_VALUE;
    }

    private int calculateGain(int[] tour, int[][] distanceMatrix, int i, int j, int k) {
        int a = tour[i - 1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j + 1];
        int e = tour[k];
        int f = tour[(k + 1) % tour.length];

        int currentDistance = distanceMatrix[a][b] + distanceMatrix[c][d] + distanceMatrix[e][f];
        int newDistance = distanceMatrix[a][c] + distanceMatrix[b][d] + distanceMatrix[e][f];

        return currentDistance - newDistance;
    }

    private void reverseSegment(int[] tour, int i, int j, int k) {
        reverse(tour, i, j);
        reverse(tour, j + 1, k);
    }

    private void reverse(int[] tour, int start, int end) {
        while (start < end) {
            int temp = tour[start];
            tour[start] = tour[end];
            tour[end] = temp;
            start++;
            end--;
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


