package outputGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import java.util.HashMap;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualGraph {

    public VisualGraph(HashMap<String, dataStructure.Node> g){
        Graph graph = new SingleGraph("MainGraph");
        SpriteManager sm = new SpriteManager(graph);
        Sprite s = sm.addSprite("S1");
        graph.setStrict(false);
        graph.setAutoCreate( true );
        for(dataStructure.Node node : g.values()) {
            s.attachToNode(""+node.getID());
            for (dataStructure.Node parent : node.getParents().keySet()) {
                graph.addEdge("" + node.getID() + parent.getID(), "" + node.getID(), "" + parent.getID());
                s.attachToNode(""+parent.getID());
            }
        }
        graph.display();

    }
}
