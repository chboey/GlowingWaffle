import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Euclidean_Distance {

    public static void CalculateED(City[] cities) throws IOException {

        double[][] distances = new double[cities.length][cities.length];
        int[] node = new int[cities.length];

        for (int i = 0; i < cities.length; i++) {
            for (int j = i+1; j < cities.length; j++) {
                double dx = cities[i].x_Coordinates - cities[j].x_Coordinates;
                double dy = cities[i].y_Coordinates - cities[j].y_Coordinates;

                double distance = Math.sqrt(dx*dx + dy*dy);

                distances[i][j] = distance;
                distances[j][i] = distance;

            }
        }
        double initialTemp=getAverageCost(distances,30);
        double coolingRate = 0.85;
        SimulatedAnnealing sa = new SimulatedAnnealing(distances, initialTemp, coolingRate);
        sa.solve(500);
        int[] bestSolution = sa.getBestSolution();

        double totalDistance=calculateCost(bestSolution,distances);

        System.out.println(Arrays.toString(bestSolution));
        System.out.println("Total distance traveled: " + totalDistance);
        
        Visualiser.visualize(cities, bestSolution);
    }

    public static double getAverageCost(double[][] distances, int numRandomSolutions) {
        int numCities = distances.length;
        double totalCost = 0.0;
        Random rand = new Random();

        for (int i = 0; i < numRandomSolutions; i++) {
            int[] solution = new int[numCities];
            for (int j = 0; j < numCities; j++) {
                solution[j] = j;
            }
            shuffleArray(solution, rand);
            double cost = calculateCost(solution, distances);
            totalCost += cost;
        }

        return numRandomSolutions*(totalCost / numRandomSolutions);
    }

    private static void shuffleArray(int[] array, Random rand) {
        int n = array.length;
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static double calculateCost(int[] solution, double[][] distances) {
        int numCities = solution.length;
        double cost = 0.0;
        for (int i = 0; i < numCities; i++) {
            int j = (i + 1) % numCities;
            cost += distances[solution[i]][solution[j]];
        }
        return cost;
    }

}
