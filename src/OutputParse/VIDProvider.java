package OutputParse;

import org.jgrapht.ext.ComponentNameProvider;

import DataStructure.Node;

public class VIDProvider implements ComponentNameProvider<Node>{

	@Override
	public String getName(Node node) {
		return node.getID();
	}

}
