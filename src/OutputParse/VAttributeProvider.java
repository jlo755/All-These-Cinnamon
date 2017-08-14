package OutputParse;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import DataStructure.Node;

/**
 * Implements VAttributeProvider to get the weight, start time and processor number for
 * a specific node.
 *
 */
public class VAttributeProvider implements ComponentAttributeProvider<Node>{

	@Override
	public Map<String, String> getComponentAttributes(Node node) {
		int NodeCost = node.getCost();
		int NodeStart = (int)node.getStartTime();
		int NodeProcessor = node.getProcessor();
		
		// LinkedHashMap keeps track of ordering for correct output.
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Weight", NodeCost+",");
		map.put("Start", NodeStart+",");
		map.put("Processor", NodeProcessor+"");	
		
		
		return map;
	}

}
