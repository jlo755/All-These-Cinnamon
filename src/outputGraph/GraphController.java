package outputGraph;


import dataStructure.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class GraphController {

	private VisualGraph vg;
	private HashMap<String, dataStructure.Node> _graph;
	private SingleGraph graph;
	private ArrayList<dataStructure.Node> _nodes;

	public GraphController(){ }

	public void createGraph(HashMap<String, dataStructure.Node> g, ArrayList<dataStructure.Node> a){
		vg = new VisualGraph(g);
		graph = vg.graph;
		_graph = g;
		_nodes = a;
	}

	public void updateGraph(){

		vg.startTraversal(_nodes.get(0).getID(), _nodes,graph);

	}


}
