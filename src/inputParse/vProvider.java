package inputParse;
import java.util.Map;

import org.jgrapht.ext.VertexProvider;

import dataStructure.Node;

/**
 * 
 * Implements VertexProvider to create a Vertex of type Node.
 *
 */
public class vProvider implements VertexProvider<Node> {

	@Override
	public Node buildVertex(String nodeName, Map<String, String> attr) {
		// reads and parses the weight attribute from the .DOT file
		// ascribes that attribute to the current Node/Vertex being processed
		return new Node(nodeName, Integer.parseInt(attr.get("Weight")));
	}

}
