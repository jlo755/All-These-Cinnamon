package InputParse;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;


public class Test {
	public static void main(String[] args) throws IOException, ImportException {
		DOTImporter dotImp = new DOTImporter(new vProvider(), new eProvider());
		DirectedAcyclicGraph<Node,Edge> graph = new  DirectedAcyclicGraph<Node, Edge>(new NodeEdgeFactory());
		FileReader reader =	new FileReader("Nodes_7_OutTree.dot");
		dotImp.importGraph(graph, reader);
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		for(Node e:graph.vertexSet()){			
			nodeMap.put(e.getID(), e);
		}

	}
}
