import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualiser {

    public static void visualize(City[] cities, int[] bestSolution, String filename, double bestDistance) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        Object[] vertexes = new Object[cities.length];
        for (int i = 0; i < cities.length; i++) {
            vertexes[i] = graph.insertVertex(parent, null, "City " + cities[i].getNode(), cities[i].getX(), cities[i].getY(), 80, 80);
            mxCell vertex = (mxCell) vertexes[i];
            vertex.setStyle("ROUNDED;strokeColor=blue;fillColor=black;fontColor=white");
        }

        Object[] edges = new Object[bestSolution.length];
        double totalDistance = 0.0;
        int lastCityIndex = bestSolution[bestSolution.length - 1];
        int firstCityIndex = bestSolution[0];
        for (int i = 0; i < bestSolution.length-1; i++) {
            int cityAIndex = bestSolution[i];
            int cityBIndex = bestSolution[i + 1];
            double xDiff = cities[cityBIndex].getX() - cities[cityAIndex].getX();
            double yDiff = cities[cityBIndex].getY() - cities[cityAIndex].getY();
            double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
            totalDistance += distance;
            edges[i] = graph.insertEdge(parent, null, String.format("%.2f", distance), vertexes[cityAIndex], vertexes[cityBIndex]);
            mxCell edge = (mxCell) edges[i];
            edge.setStyle("strokeColor=red;strokeWidth=9;");
        }
        edges[bestSolution.length - 1] = graph.insertEdge(parent, null, String.format("%.2f", totalDistance), vertexes[lastCityIndex], vertexes[firstCityIndex]);
        mxCell edge = (mxCell) edges[bestSolution.length - 1];
        edge.setStyle("strokeColor=red;strokeWidth=9;");

        graph.getModel().beginUpdate();
        try {
            mxCell textCell = (mxCell) graph.insertVertex(parent, null, String.format("Total Distance: %.2f", bestDistance), graph.getGraphBounds().getCenterX(), graph.getGraphBounds().getY() + 50, 0, 0);
            textCell.setStyle("fontSize=150;align=center;verticalAlign=top;");
        } finally {
            graph.getModel().endUpdate();
        }

        saveGraphAsJpg(graph, filename);
    }




    public static void saveGraphAsJpg(mxGraph graph, String filename) {
        try {
            BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);
            File output = new File(filename);
            ImageIO.write(image, "jpg", output);
            System.out.println("Saved graph as JPG file: "+ filename+ "\n");
        } catch (IOException e) {
            System.err.println("Error saving graph as JPG file: " + e.getMessage());
        }
    }

}
