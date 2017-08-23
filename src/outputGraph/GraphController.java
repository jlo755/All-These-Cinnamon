package outputGraph;


import dataStructure.Node;

import java.util.HashMap;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class GraphController {

	private VisualGraph vg;
	private HashMap<String, dataStructure.Node> _graph;

	public GraphController(){ }

	public void createGraph(HashMap<String, dataStructure.Node> g){
		vg = new VisualGraph(g);
		_graph = g;
	}

	public void updateGraph(){

	}


}
