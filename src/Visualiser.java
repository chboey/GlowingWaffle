import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualiser {

    public static void visualize(City[] cities, int[] bestSolution, String filename) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();


        Object[] vertexes = new Object[cities.length];
        for (int i = 0; i < cities.length; i++) {
            vertexes[i] = graph.insertVertex(parent, null, "City " + cities[i].getNode(), cities[i].getX(), cities[i].getY(), 80, 80);
            mxCell vertex = (mxCell) vertexes[i];
            vertex.setStyle("ROUNDED;strokeColor=red;fillColor=yellow");

        }

        Object[] edges = new Object[cities.length];
        double totalDistance = 0.0;
        for (int i = 0; i < bestSolution.length - 1; i++) {
            int cityAIndex = bestSolution[i];
            int cityBIndex = bestSolution[i + 1];
            double xDiff = cities[cityBIndex].getX() - cities[cityAIndex].getX();
            double yDiff = cities[cityBIndex].getY() - cities[cityAIndex].getY();
            double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
            totalDistance += distance;
            edges[i] = graph.insertEdge(parent, null, String.format("%.2f", distance), vertexes[cityAIndex], vertexes[cityBIndex]);
            mxCell edge = (mxCell) edges[i];
            edge.setStyle("strokeColor=blue");
        }
        saveGraphAsJpg(graph,filename);
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
