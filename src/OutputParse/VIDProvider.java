package OutputParse;

import org.jgrapht.ext.ComponentNameProvider;

import DataStructure.Node;

public class VIDProvider implements ComponentNameProvider{

	@Override
	public String getName(Object node) {
		Node theNode = (Node)(node);
		return theNode.getID();
	}

}
