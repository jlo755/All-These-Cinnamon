package inputParse;
import java.util.Map;

import org.jgrapht.ext.EdgeProvider;

import dataStructure.Node;

/**
 * Implements EdgeProvider to produce an Edge object and returns it.
 *
 */
public class eProvider implements EdgeProvider<Node, Edge>{


	@Override
	public Edge buildEdge(Node parentNode, Node childNode, String arg2, Map<String, String> arg3) {
		return new Edge(childNode, parentNode, Double.parseDouble((String) arg3.get("Weight")));
	}
	

}
