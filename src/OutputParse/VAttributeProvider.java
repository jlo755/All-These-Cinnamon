package OutputParse;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import DataStructure.Node;

public class VAttributeProvider implements ComponentAttributeProvider<Node>{

	@Override
	public Map<String, String> getComponentAttributes(Node node) {
		int NodeCost = node.getCost();
		int NodeStart = (int)node.getStartTime();
		int NodeProcessor = node.getProcessor();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Weight", NodeCost+",");
		map.put("Start", NodeStart+",");
		map.put("Processor", NodeProcessor+"");	
		
		
		return map;
	}

}
