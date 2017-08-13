package OutputParse;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.ext.ComponentAttributeProvider;

import DataStructure.Node;
import InputParse.Edge;


public class EAttributeProvider implements ComponentAttributeProvider{

	@Override
	public Map getComponentAttributes(Object edge) {
		Edge theEdge = (Edge)edge;
		
		double EdgeCost = theEdge.getCost();

		
		Map map = new HashMap<String, String>();
		map.put("Weight", EdgeCost+"");

		
		return map;
	}


}
