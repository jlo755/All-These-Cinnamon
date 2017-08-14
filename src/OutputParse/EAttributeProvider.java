package OutputParse;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import InputParse.Edge;


public class EAttributeProvider implements ComponentAttributeProvider<Edge>{

	@Override
	public Map<String, String> getComponentAttributes(Edge edge) {
		
		int EdgeCost = (int)edge.getCost();

		
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(EdgeCost);
		map.put("Weight", EdgeCost+"");

		
		return map;
	}


}
