package InputParse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import DataStructure.Node;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.ImportException;


public class DotParser {
	private DOTImporter<Node, Edge> _dotImp;
	private DirectedAcyclicGraph<Node,Edge> _graph;
	private FileReader _reader;
	private HashMap<String, Node> _nodeMap;
	
	public DotParser(String fileName) throws FileNotFoundException{
		_dotImp = new DOTImporter<Node, Edge>(new vProvider(), new eProvider());
		_graph = new DirectedAcyclicGraph<Node, Edge>(new NodeEdgeFactory());
		_reader = new FileReader(fileName);
		_nodeMap = new HashMap<String, Node>();
	}

	public void parseInput() throws ImportException{
		_dotImp.importGraph(_graph, _reader);
		for(Node e:_graph.vertexSet()){			
			_nodeMap.put(e.getID(), e);
		}
	}
	
	public HashMap<String, Node> getNodeMap(){
		return _nodeMap;
	}
}
