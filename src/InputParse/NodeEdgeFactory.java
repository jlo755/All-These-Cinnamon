package InputParse;
import org.jgrapht.EdgeFactory;


public class NodeEdgeFactory implements EdgeFactory {

	@Override
	public Object createEdge(Object childNode, Object parentNode) {
		return new Edge(childNode, parentNode);
	}

}
