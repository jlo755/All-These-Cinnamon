package InputParse;
import java.util.Map;

import org.jgrapht.ext.EdgeProvider;

import DataStructure.Node;


public class eProvider implements EdgeProvider<Node, Edge>{

	@Override
	public Edge buildEdge(Node parentNode, Node childNode, String arg2, Map<String, String> arg3) {
		return new Edge(childNode, parentNode, Double.parseDouble((String) arg3.get("Weight")));
	}
	

}
