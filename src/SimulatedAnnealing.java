import java.util.Arrays;
import java.util.Random;

class SimulatedAnnealing{
    private int[] current_solution;
    private int[] best_solution;
    private final double[][] distances;
    private final int num_cities;
    private double temp;
    private final double cooling_rate;

    public SimulatedAnnealing(double[][] distances, double initial_temp, double cooling_rate) {

        this.distances = distances;
        this.num_cities = distances.length;
        this.temp = initial_temp;
        this.cooling_rate = cooling_rate;
        this.current_solution = new int[num_cities];
        this.best_solution = new int[num_cities];

        for (int i = 0; i < num_cities; i++) {
            this.current_solution[i] = i;
            this.best_solution[i] = i;
        }
        shuffle_arr(current_solution);
    }

    public int[] solve(int iteration) {
        double current_energy = get_energy(current_solution);
        double bestEnergy = current_energy;
        int[] bestSolution = Arrays.copyOf(current_solution, num_cities);

        for (int i = 0; i < iteration; i++) {
            int[] new_solution = Arrays.copyOf(current_solution, num_cities);

            int city_x = (int) (num_cities * Math.random());
            int city_y = (int) (num_cities * Math.random());

            swap_cities(new_solution, city_x, city_y);

            double new_energy = get_energy(new_solution);

            if (acceptance_rate(current_energy, new_energy, temp) == 1.0) {
                current_solution = new_solution;
                current_energy = new_energy;
            }
            if (current_energy < bestEnergy) {
                bestSolution = Arrays.copyOf(current_solution, num_cities);
                bestEnergy = current_energy;
                bestSolution[num_cities - 1] = bestSolution[0];
            }
            temp *= cooling_rate;
        }

        return bestSolution;
    }

    private double get_energy(int[] solution) {
        double energy = 0;
        for (int i = 0; i < num_cities; i++) {
            int j = (i + 1) % num_cities;
            energy += distances[solution[i]][solution[j]];
        }
        return energy;
    }

    private double acceptance_rate(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return 1.0;
        }
        return Math.exp((currentEnergy - newEnergy) / temperature);
    }

    private void shuffle_arr(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int idx = random.nextInt(i + 1);
            int temp = arr[idx];
            arr[idx] = arr[i];
            arr[i] = temp;
        }
    }


    private void swap_cities(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    private static void shuffle_array(int[] array, Random rand) {
        int n = array.length;
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    static double calculate_cost(int[] solution, double[][] distances) {
        int numCities = solution.length;
        double cost = 0.0;
        for (int i = 0; i < numCities; i++) {
            int j = (i + 1) % numCities;
            cost += distances[solution[i]][solution[j]];
        }
        return cost;
    }

    public static double get_average_cost(double[][] distances, int numRandomSolutions) {
        int num_cities = distances.length;
        double total_cost = 0.0;
        Random rand = new Random();

        for (int i = 0; i < numRandomSolutions; i++) {
            int[] solution = new int[num_cities];
            for (int j = 0; j < num_cities; j++) {
                solution[j] = j;
            }
            shuffle_array(solution, rand);
            double cost = calculate_cost(solution, distances);
            total_cost += cost;
        }

        return numRandomSolutions*(total_cost / numRandomSolutions);
    }

    public double[] getTotalDistances(int numIterations) {
        double[] totalDistances = new double[numIterations];

        for (int i = 0; i < numIterations; i++) {
            int[] currentSolution = solve(numIterations);
            double totalDistance = 0;

            for (int j = 0; j < num_cities - 1; j++) {
                int city1 = currentSolution[j];
                int city2 = currentSolution[j + 1];
                totalDistance += distances[city1][city2];
            }

            totalDistance += distances[currentSolution[num_cities - 1]][currentSolution[0]];
            totalDistances[i] = totalDistance;

        }

        return totalDistances;
    }






}
