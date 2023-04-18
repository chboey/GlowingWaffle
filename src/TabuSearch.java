import java.util.Arrays;
import java.util.Random;

public class TabuSearch {

    public int[] tabuSearch(double[][] distances, int maxIterations, int tabuSize) {

        int[] current_Solution = getRandomSolution(distances.length);
        int[] best_Solution = current_Solution.clone();

        int[] tabuList = new int[tabuSize];

        int tabuIndex = 0;

        int currentCost = getCost(current_Solution, distances);
        int bestCost = currentCost;

        for (int i = 0; i < maxIterations; i++) {
            int[] candidate = null;
            int candidateCost = Integer.MAX_VALUE;

            int[][] neighborhood = getNeighborhood(current_Solution);

            for (int[] neighbor : neighborhood) {
                int neighborCost = getCost(neighbor, distances);

                if (!contains(tabuList, neighbor) && neighborCost < candidateCost) {
                    candidate = neighbor;
                    candidateCost = neighborCost;
                }
            }

            if (candidate == null) {
                break; // break out of local minimum
            }

            current_Solution = candidate;
            currentCost = candidateCost;

            if (currentCost < bestCost) {
                best_Solution = current_Solution.clone();
                best_Solution[current_Solution.length-1] = current_Solution[0];
                bestCost = currentCost;
            }

            tabuList[tabuIndex % tabuSize] = Arrays.hashCode(candidate);
            tabuIndex++;
        }

        return best_Solution;
    }

    public static int[] getRandomSolution(int n) {
        int[] solution = new int[n];

        for (int i = 0; i < n; i++) {
            solution[i] = i;
        }

        shuffle(solution);
        return solution;
    }

    public static int[][] getNeighborhood(int[] solution) {
        int n = solution.length;
        int[][] neighborhood = new int[n*(n-1)/2][n];

        int count = 0;
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                neighborhood[count] = getNeighbor(solution, i, j);
                count++;
            }
        }

        return neighborhood;
    }

    public static int[] getNeighbor(int[] solution, int i, int j) {
        int[] neighbor = solution.clone();
        int tmp = neighbor[i];
        neighbor[i] = neighbor[j];
        neighbor[j] = tmp;
        return neighbor;
    }

    public int getCost(int[] solution, double[][] distances) {
        double cost = distances[solution[solution.length-1]][solution[0]];

        for (int i = 0; i < solution.length-1; i++) {
            cost += distances[solution[i]][solution[i+1]];
        }

        return (int) Math.round(cost);
    }


    public static boolean contains(int[] tabuList, int[] solution) {
        int hash = Arrays.hashCode(solution);

        for (int j : tabuList) {
            if (j == hash) {
                return true;
            }
        }

        return false;
    }

    public static void shuffle(int[] array) {
        Random random = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i+1);
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

}
