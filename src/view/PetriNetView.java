package view;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.util.mxCellRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PetriNetView {
    public void displayPetriNet(Graph<String, DefaultEdge> petriNet, String title) {
        JFrame frame = new JFrame();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        frame.setSize(width, height);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            for (DefaultEdge edge : petriNet.edgeSet()) {
                String source = petriNet.getEdgeSource(edge);
                String target = petriNet.getEdgeTarget(edge);
                graph.insertEdge(parent, null, "", graph.insertVertex(parent, null, source, 0, 0, 0, 0),
                        graph.insertVertex(parent, null, target, 0, 0, 0, 0));
            }
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        frame.add(graphComponent, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void savePetriNetImage(Graph<String, DefaultEdge> petriNet, String fileName) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            for (DefaultEdge edge : petriNet.edgeSet()) {
                String source = petriNet.getEdgeSource(edge);
                String target = petriNet.getEdgeTarget(edge);
                graph.insertEdge(parent, null, "", graph.insertVertex(parent, null, source, 0, 0, 0, 0),
                        graph.insertVertex(parent, null, target, 0, 0, 0, 0));
            }
        } finally {
            graph.getModel().endUpdate();
        }

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);
        try {
            ImageIO.write(image, "PNG", new File(fileName));
            System.out.println("Petri Net image saved as: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
