package outputParse;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import inputParse.Edge;

/**
 * Implements ComponentAttributeProvider to provide the weight of an edge
 * within the graph.
 *
 */
public class EAttributeProvider implements ComponentAttributeProvider<Edge>{

	@Override
	public Map<String, String> getComponentAttributes(Edge edge) {
		
		int EdgeCost = (int)edge.getCost();

		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Weight", EdgeCost+"");

		
		return map;
	}


}
