import java.util.Arrays;
import java.util.Random;

class SimulatedAnnealing{
    private int[] currentSolution;
    private int[] bestSolution;
    private final double[][] distances;
    private final int numCities;
    private double temperature;
    private final double coolingRate;

    public SimulatedAnnealing(double[][] distances, double initialTemperature, double coolingRate) {

        this.distances = distances;
        this.numCities = distances.length;
        this.temperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.currentSolution = new int[numCities];
        this.bestSolution = new int[numCities];

        for (int i = 0; i < numCities; i++) {
            this.currentSolution[i] = i;
            this.bestSolution[i] = i;
        }
        shuffleArray(currentSolution);
    }

    public void solve(int numIterations) {
        double currentEnergy = getEnergy(currentSolution);
        double bestEnergy = currentEnergy;

        for (int i = 0; i < numIterations; i++) {
            int[] newSolution = Arrays.copyOf(currentSolution, numCities);
            int city1 = (int) (numCities * Math.random());
            int city2 = (int) (numCities * Math.random());
            swapCities(newSolution, city1, city2);
            double newEnergy = getEnergy(newSolution);
            if (shouldAccept(currentEnergy, newEnergy, temperature)) {
                currentSolution = newSolution;
                currentEnergy = newEnergy;
            }
            if (currentEnergy < bestEnergy) {
                bestSolution = Arrays.copyOf(currentSolution, numCities);
                bestEnergy = currentEnergy;
                bestSolution[numCities-1] = bestSolution[0];
            }
            temperature *= coolingRate;
        }
    }

    private double getEnergy(int[] solution) {
        double energy = 0;
        for (int i = 0; i < numCities; i++) {
            int j = (i + 1) % numCities;
            energy += distances[solution[i]][solution[j]];
        }
        return energy;
    }

    private boolean shouldAccept(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return true;
        }
        double acceptanceProbability = Math.exp((currentEnergy - newEnergy) / temperature);
        return Math.random() < acceptanceProbability;
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void swapCities(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public int[] getBestSolution() {
        return bestSolution;
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

    static double calculateCost(int[] solution, double[][] distances) {
        int numCities = solution.length;
        double cost = 0.0;
        for (int i = 0; i < numCities; i++) {
            int j = (i + 1) % numCities;
            cost += distances[solution[i]][solution[j]];
        }
        return cost;
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

}
