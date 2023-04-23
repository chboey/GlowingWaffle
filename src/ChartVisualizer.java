import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import java.awt.*;
import java.text.DecimalFormat;

public class ChartVisualizer {

    public static void visualize(int numIterations, double[] totalDistances) {
        // Create dataset
        XYSeries series = new XYSeries("Total Distance vs Iteration");
        for (int i = 0; i < numIterations; i++) {
            series.add(i, totalDistances[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Total Distance vs Iteration", // chart title
                "Iteration", // x axis label
                "Total Distance", // y axis label
                dataset
        );

        // Customize plot
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);

        // Customize x-axis
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0, Math.max(numIterations, 1000));
        domain.setTickUnit(new NumberTickUnit(1000));
        domain.setTickMarkInsideLength(5f);
        domain.setTickMarkOutsideLength(0f);
        domain.setMinorTickCount(0);

        // Customize y-axis
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        double maxTotalDistance = totalDistances[numIterations - 1];
        range.setRange(0, Math.max(maxTotalDistance, 10.0));
        range.setTickUnit(new NumberTickUnit(5000));
        range.setTickMarkInsideLength(5f);
        range.setTickMarkOutsideLength(0f);
        range.setMinorTickCount(0);
        range.setTickUnit(new NumberTickUnit(50000, new DecimalFormat("###,###,###,##0.00")));


        // Customize renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        // Create panel and add to frame
        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame("Total Distance vs Iteration");
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(500, 400));
        frame.pack();
        frame.setVisible(true);
    }

}
