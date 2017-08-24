package scheduling;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import dataStructure.Node;
import outputGraph.GraphController;

/**
 * 
 * This class stores and manages the currently found "best solution" for a Node object,
 * used while traversing state space. A separate Node object is used to track this within
 * a HashMap. 
 *
 */

public class FinalState {
	// Current best solution for a Node mapped to an ID in String.
	HashMap<String, Node> _currentBestState;

	public FinalState(){

	}
	/**
	 * Instantiate a set of Nodes' best solution to be 0.
	 * @param taskGraph
	 */
	public FinalState(HashMap<String, Node> taskGraph){
		_currentBestState = new HashMap<String, Node>();
		_currentBestState = new HashMap<String, Node>();
		for(Node n: taskGraph.values()){
			String name = n.getID();
			_currentBestState.put(n.getID(), new Node(name,n.getCost()));
		}
		for(Node n: taskGraph.values()){
			Node parent = _currentBestState.get(n.getID());
			for(Node child:n.getChildren().keySet()){
				Node node = _currentBestState.get(child.getID());
				parent.addChild(node, n.getChildren().get(child));
				node.addParent(parent, n.getChildren().get(child));
			}
		}

	}
	}
	
	/**
	 * Set the current best state for all the Nodes in a graph.
	 * @param graph
	 */
	public void setCurrentBestState(HashMap<String, Node> graph){
		for(String nodeID : graph.keySet()){
			Node n = _currentBestState.get(nodeID);
			Node bestNode = graph.get(nodeID);
			n.setStartTime(bestNode.getStartTime());
			n.setEndTime(bestNode.getEndTime());
			n.setProcessor(bestNode.getProcessor());
		}
	}
	
	/**
	 * Get HashMap for current best state of Nodes.
	 * @return
	 */
	public HashMap<String, Node> getCurrentBestState(){
		return _currentBestState;
	}
	
	/**
	 * Print current best state of all Nodes.
	 */
	public void printCurrentBestState(){
		for(String s:_currentBestState.keySet()){
			Node n = _currentBestState.get(s);
			System.out.println("Node ID: "+n.getID()+
					" Start Time: "+n.getStartTime()+
					" Processor: "+n.getProcessor() +
					" End Time: "+n.getEndTime());
		}
	}

	public void finalStateToGraph(HashMap<String, dataStructure.Node> g){
		GraphController gc = new GraphController();
		ArrayList<dataStructure.Node> nodes = new ArrayList<dataStructure.Node>();
		for(Node n : g.values()){
			nodes.add(n);
		}
		Collections.sort(nodes, Node.startTimes());
		gc.createGraph(g, nodes);
		gc.updateGraph();
	}
}
