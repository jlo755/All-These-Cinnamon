package graphOutput;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashMap;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualGraph {

    public VisualGraph(HashMap<String, dataStructure.Node> g){
        Graph graph = new SingleGraph("MainGraph");
        graph.setStrict(false);
        graph.setAutoCreate( true );
        for(dataStructure.Node node : g.values()) {
            for (dataStructure.Node parent : node.getParents().keySet()) {
                graph.addEdge("" + node.getID() + parent.getID(), "" + node.getID(), "" + parent.getID());
            }
        }
        graph.display();
    }
}
