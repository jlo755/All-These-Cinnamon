package InputParse;
import java.util.Map;

import org.jgrapht.ext.EdgeProvider;

/**
 * Implements EdgeProvider to produce an Edge object and returns it.
 *
 */
public class eProvider implements EdgeProvider{

	@Override
	public Edge buildEdge(Object parentNode, Object childNode, String arg2, Map arg3) {
		return new Edge(childNode, parentNode, Double.parseDouble((String) arg3.get("Weight")));
	}
	

}
