package InputParse;
import org.jgrapht.EdgeFactory;

/**
 * 
 * Implements EdgeFactory to return an Edge object to build the graph.
 *
 */
public class NodeEdgeFactory implements EdgeFactory {

	@Override
	public Object createEdge(Object childNode, Object parentNode) {
		return new Edge(childNode, parentNode);
	}

}
