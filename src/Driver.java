import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Driver {

    public static void CalculateED(City[] cities) {

        double[][] distances = new double[cities.length][cities.length];

        for (int i = 0; i < cities.length; i++) {
            for (int j = i + 1; j < cities.length; j++) {
                double dx = cities[i].x_Coordinates - cities[j].x_Coordinates;
                double dy = cities[i].y_Coordinates - cities[j].y_Coordinates;

                double distance = Math.sqrt(dx * dx + dy * dy);

                distances[i][j] = distance;
                distances[j][i] = distance;

            }
        }


        //Tabu Search
        TabuSearch ts = new TabuSearch();
        long start_time_TS = System.nanoTime();
        int[] best_solution = ts.tabuSearch(distances, 1000, 55, true);
        long end_time_TS = System.nanoTime();

        System.out.println(Arrays.toString(best_solution));
        System.out.println("Total Distance travelled(TS): " + TabuSearch.getCost(best_solution, distances, true));
        System.out.println("Total Time Taken(in ns): " + (end_time_TS - start_time_TS));
        Visualiser.visualize(cities, best_solution, "TSP Solution for Tabu Search.jpg",TabuSearch.getCost(best_solution, distances, true));
        //End

        //Simulated Annealing
        long start_time_SA = System.nanoTime();
        double initialTemp = SimulatedAnnealing.get_average_cost(distances, 3);
        SimulatedAnnealing sa = new SimulatedAnnealing(distances, initialTemp, 0.005);
        double totalDistance = SimulatedAnnealing.calculate_cost(sa.solve(1000), distances);
        long end_time_SA = System.nanoTime();

        System.out.println(Arrays.toString(sa.solve(100000)));
        System.out.println("Total distance traveled(SA): " + totalDistance);
        System.out.println("Total Time Taken(in ns): " + (end_time_SA - start_time_SA));
        Visualiser.visualize(cities, sa.solve(100000), "TSP Solution for Simulated Annealing.jpg",totalDistance);
        //End

        //Nearest Neighbour with 3-OPT
        NN_3OPT nn = new NN_3OPT(distances);
        long start_time_NN = System.nanoTime();
        nn.run();
        long end_time_NN = System.nanoTime();

        int[] bestRoute = nn.getBestRoute();
        System.out.println(Arrays.toString(bestRoute));
        System.out.println("Total Distance travelled(NN_3OPT):" + nn.getBestDistance());
        System.out.println("Total Time Taken(in ms): " + (end_time_NN - start_time_NN));
        Visualiser.visualize(cities, nn.getBestRoute(), "TSP Solution for Nearest Neighbour with 3 OPT.jpg",nn.getBestDistance());
        //End


        //Visualiser for iterations!, Commented this part out as it was only necessary for the report-side of the assignment
        //double[] totalDistances = new double[10000];
        //for (int i = 1; i <= 10000; i++) {
        //    SimulatedAnnealing sa = new SimulatedAnnealing(distances,initialTemp,0.005);
        //    int[] currentSolution = sa.solve(10000);
        //    double tD = TabuSearch.getCost(currentSolution,distances,true);
        //   totalDistances[i-1] = tD;
        //}
        //ChartVisualizer.visualize(10000,totalDistances);
        //End

        //SA Visualiser for Cooling Rate
        /*DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (double coolingRate = 0; coolingRate <= 0.99; coolingRate += 0.01) {
            double initialTemp1 = SimulatedAnnealing.get_average_cost(distances, 3);
            SimulatedAnnealing sa1 = new SimulatedAnnealing(distances, initialTemp1, coolingRate);
            int[] solution = sa1.solve(100000);
            double totalDistance1 = SimulatedAnnealing.calculate_cost(solution, distances);
            dataset.addValue(totalDistance1, "Total Distance", String.format("%.2f", coolingRate));
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Total Distance vs Cooling Rate",
                "Cooling Rate",
                "Total Distance",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);

         */
    }
}
