package InputParse;
import org.jgrapht.EdgeFactory;

import DataStructure.Node;


/**
 * 
 * Implements EdgeFactory to return an Edge object to build the graph.
 *
 */

public class NodeEdgeFactory implements EdgeFactory<Node, Edge> {


	@Override
	public Edge createEdge(Node childNode, Node parentNode) {
		return new Edge(childNode, parentNode);
	}

}
