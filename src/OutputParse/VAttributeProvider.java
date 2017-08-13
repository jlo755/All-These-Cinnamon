package OutputParse;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import DataStructure.Node;

public class VAttributeProvider implements ComponentAttributeProvider{

	@Override
	public Map getComponentAttributes(Object node) {
		Node theNode = (Node)node;
		
		double NodeCost = theNode.getCost();
		double NodeStart = theNode.getStartTime();
		double NodeProcessor = theNode.getProcessor();
		
		Map map = new HashMap<String, String>();
		map.put("Weight", NodeCost+"");
		map.put("Start", NodeStart+"");
		map.put("Processor", NodeProcessor+"");
		
		
		return map;
	}

}
