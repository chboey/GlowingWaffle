import java.util.Arrays;

public class Euclidean_Distance {

    public static void CalculateED(City[] cities) {

        double[][] distances = new double[cities.length][cities.length];

        for (int i = 0; i < cities.length; i++) {
            for (int j = i+1; j < cities.length; j++) {
                double dx = cities[i].x_Coordinates - cities[j].x_Coordinates;
                double dy = cities[i].y_Coordinates - cities[j].y_Coordinates;

                double distance = Math.sqrt(dx*dx + dy*dy);

                distances[i][j] = distance;
                distances[j][i] = distance;

            }
        }

        //Tabu Search
        TabuSearch ts = new TabuSearch();
        long start_time_TS = System.currentTimeMillis();
        int[] best_solution = ts.tabuSearch(distances, 400, 45);
        long end_time_TS = System.currentTimeMillis();

        System.out.println(Arrays.toString(best_solution));
        System.out.println("Total Distance travelled(TS): "+ts.getCost(best_solution,distances));
        System.out.println("Total Time Taken(in ms): " + (end_time_TS-start_time_TS));
        Visualiser.visualize(cities,best_solution,"TSP Solution for Tabu Search.jpg");
        //End


        //Simulated Annealing
        long start_time_SA = System.currentTimeMillis();
        double initialTemp= SimulatedAnnealing.getAverageCost(distances,100000);
        double coolingRate = 0.921;
        SimulatedAnnealing sa = new SimulatedAnnealing(distances, initialTemp, coolingRate);
        sa.solve(100000);
        int[] bestSolution = sa.getBestSolution();
        double totalDistance = SimulatedAnnealing.calculateCost(bestSolution, distances);
        long end_time_SA = System.currentTimeMillis();

        System.out.println(Arrays.toString(bestSolution));
        System.out.println("Total distance traveled(SA): " + totalDistance);
        System.out.println("Total Time Taken(in ms): " + (end_time_SA-start_time_SA));
        Visualiser.visualize(cities, bestSolution,"TSP Solution for Simulated Annealing.jpg");
        //End

        //Nearest Neighbour with 3-OPT
        NN_3OPT nn = new NN_3OPT(distances);
        long start_time_NN = System.currentTimeMillis();
        nn.run();
        long end_time_NN = System.currentTimeMillis();

        int[] bestRoute = nn.getBestRoute();
        System.out.println(Arrays.toString(bestRoute));
        System.out.println("Total Distance travelled(NN_3OPT):"+nn.getBestDistance());
        System.out.println("Total Time Taken(in ms): " + (end_time_NN-start_time_NN));
        Visualiser.visualize(cities,nn.getBestRoute(),"TSP Solution for Nearest Neighbour with 3 OPT.jpg");
        //End
    }

}
