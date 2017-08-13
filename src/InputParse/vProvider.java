package InputParse;
import java.util.Map;

import org.jgrapht.ext.VertexProvider;

import DataStructure.Node;


public class vProvider implements VertexProvider<Node> {

	@Override
	public Node buildVertex(String nodeName, Map<String, String> attr) {
		return new Node(nodeName, Integer.parseInt(attr.get("Weight")));
	}

}
