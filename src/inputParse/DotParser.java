package inputParse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.ImportException;

import dataStructure.Node;

/**
 * 
 * Parses an input dot format file.
 *
 */
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

	/**
	 * Parses the input dot file into an equivalent HashMap.
	 * @throws ImportException
	 */
	public void parseInput() throws ImportException{
		//converts .DOT file to a DAG _graph
		_dotImp.importGraph(_graph, _reader);
		//populates the _nodeMap which is a graph made of Node data structures
		for(Node e:_graph.vertexSet()){			
			_nodeMap.put(e.getID(), e);
		}
	}
	
	/**
	 * Returns the HashMap of Nodes.
	 * @return
	 */
	public HashMap<String, Node> getNodeMap(){
		return _nodeMap;
	}
	
	/**
	 * Returns a DAG representative of the input dot file.
	 * @return
	 */
	public DirectedAcyclicGraph<Node, Edge> getGraph(){
		return _graph;
	}
}
