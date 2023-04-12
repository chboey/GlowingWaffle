import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualiser {

    public static void visualize(City[] cities, int[] bestSolution) throws IOException {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // Create vertexes for each city
        Object[] vertexes = new Object[cities.length];
        for (int i = 0; i < cities.length; i++) {
            vertexes[i] = graph.insertVertex(parent, null, cities[i].node, cities[i].x_Coordinates * 4, cities[i].y_Coordinates * 4, 30, 30);
        }

        // Add edges between cities in the order specified by the solution
        for (int i = 0; i < bestSolution.length; i++) {
            int j = (i + 1) % bestSolution.length;
            graph.insertEdge(parent, null, "", vertexes[bestSolution[i]], vertexes[bestSolution[j]]);
        }

        // Apply some layout algorithms to make the graph look nicer
        mxParallelEdgeLayout parallelEdgeLayout = new mxParallelEdgeLayout(graph);
        parallelEdgeLayout.execute(parent);

        mxStackLayout stackLayout = new mxStackLayout(graph);
        stackLayout.execute(parent);

        mxGraphLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
        layout.setUseBoundingBox(false);
        layout.execute(graph.getDefaultParent());

        // Create a Swing component to display the graph
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(1920, 1080));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setVisible(true);

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 2, Color.WHITE, true, null);
        File file = new File("graph.jpg");
        ImageIO.write(image, "jpg", file);
    }

}
