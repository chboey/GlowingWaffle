import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TabuSearch {

    public int[] tabuSearch(double[][] distances, int maxIterations, int tabuSize, boolean includeFirstNode) {
        int[] current_Solution = getRandomSolution(distances.length);
        int[] best_Solution = current_Solution.clone();

        Set<Integer> tabuList = new HashSet<>();
        int tabuIndex = 0;

        int currentCost = getCost(current_Solution, distances, includeFirstNode);
        int bestCost = currentCost;

        for (int i = 0; i < maxIterations; i++) {
            int[] candidate = null;
            int candidateCost = Integer.MAX_VALUE;

            int[][] neighborhood = getNeighborhood(current_Solution);

            for (int[] neighbor : neighborhood) {
                int neighborCost = getCost(neighbor, distances, includeFirstNode);

                if (!tabuList.contains(Arrays.hashCode(neighbor)) && neighborCost < candidateCost) {
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
                best_Solution = Arrays.copyOf(current_Solution, current_Solution.length + 1);
                best_Solution[current_Solution.length] = best_Solution[0];
                bestCost = currentCost;
            }


            tabuList.add(Arrays.hashCode(candidate));
            if (tabuList.size() > tabuSize) {
                tabuList.remove(tabuList.iterator().next());
            }
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

    public static int getCost(int[] solution, double[][] distances, boolean includeFirstNode) {
        int cost = 0;
        int n = solution.length - 1; // subtract 1 for the first node

        if (includeFirstNode) {
            for (int i = 0; i < n; i++) {
                cost += distances[solution[i]][solution[(i + 1) % n]];
            }
            cost += distances[solution[n]][solution[0]]; // add distance from last node to first node
        } else {
            for (int i = 1; i < n; i++) { // start at index 1 for the second node
                cost += distances[solution[i - 1]][solution[i]];
            }
            cost += distances[solution[n - 1]][solution[n]]; // add distance from second-last node to last node
        }

        return cost;
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
