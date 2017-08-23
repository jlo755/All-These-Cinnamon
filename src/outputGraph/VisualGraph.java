package outputGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualGraph {
    Graph graph;

    protected String styleSheet =
            "node {" + "size: 40px; shape: circle; fill-color: white; stroke-mode: plain; stroke-color: black; text-alignment: center;}"+
                    "node.processor1 {	fill-color: red; }"+
                    "node.processor2 {	fill-color: green; }";
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

    public void startTraversal(String ID, ArrayList<dataStructure.Node> a){
        explore(a);
    }

    public void explore(ArrayList<dataStructure.Node> a) {

        for(dataStructure.Node n : a) {
            Node next = graph.getNode(n.getID());
            if(n.getProcessor() == 1) {
                next.setAttribute("ui.class", "processor1");
                sleep();
            }
            else if(n.getProcessor() == 2){
                next.setAttribute("ui.class", "processor2");
                sleep();
            }
        }
    }

    protected void sleep() {
        try { Thread.sleep(1500); } catch (Exception e) {}
    }

}
