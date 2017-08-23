package outputGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualGraph {
    Graph graph;

    protected String styleSheet =
            "node {" + "size: 40px; shape: circle; fill-color: white; stroke-mode: plain; stroke-color: black; text-alignment: center;}"+
                    "node.marked {	fill-color: red; }";
    public VisualGraph(HashMap<String, dataStructure.Node> g){
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph = new SingleGraph("MainGraph");
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setStrict(false);
        graph.setAutoCreate( true );
        graph.display();
        for(dataStructure.Node node : g.values()) {
            for (dataStructure.Node parent : node.getParents().keySet()) {
                graph.addEdge("" + node.getID() + parent.getID(), "" + node.getID(), "" + parent.getID());
            }
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", "  "+node.getId()+"  ");
        }


    }

    public void startTraversal(String ID){
        org.graphstream.graph.Node n = graph.getNode(ID);
        explore(graph.getNode(ID));
    }

    public void explore(Node source) {
        Iterator<? extends Node> k = source.getDepthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep();
        }
    }

    protected void sleep() {
        try { Thread.sleep(1500); } catch (Exception e) {}
    }

}
