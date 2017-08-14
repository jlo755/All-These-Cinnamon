package OutputParse;

import org.jgrapht.ext.ComponentNameProvider;

import DataStructure.Node;

/**
 * Implements ComponentNameProvider to return a Node's name.
 *
 */
public class VIDProvider implements ComponentNameProvider<Node>{

	@Override
	public String getName(Node node) {
		return node.getID();
	}

}
