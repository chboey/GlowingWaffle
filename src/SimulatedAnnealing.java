import java.util.Arrays;
import java.util.Random;

class SimulatedAnnealing{
    private int[] currentSolution;
    private int[] bestSolution;
    private double[][] distances;
    private int numCities;
    private double temperature;
    private double coolingRate;

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
            int pos1 = (int) (numCities * Math.random());
            int pos2 = (int) (numCities * Math.random());
            swapCities(newSolution, pos1, pos2);
            double newEnergy = getEnergy(newSolution);
            if (shouldAccept(currentEnergy, newEnergy, temperature)) {
                currentSolution = newSolution;
                currentEnergy = newEnergy;
            }
            if (currentEnergy < bestEnergy) {
                bestSolution = Arrays.copyOf(currentSolution, numCities);
                bestEnergy = currentEnergy;
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

    public double getBestEnergy() {
        return getEnergy(bestSolution);
    }
}
