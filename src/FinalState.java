import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import DataStructure.Node;

public class FinalState {
	HashMap<String, Node> _currentBestState;
	
	public FinalState(Set<String> set){
		_currentBestState = new HashMap<String, Node>();
		for(String s: set){
			_currentBestState.put(s, new Node(s,0));
		}

	}
	
	public void setCurrentBestState(HashMap<String, Node> graph){
		for(String nodeID : graph.keySet()){
			Node n = _currentBestState.get(nodeID);
			Node bestNode = graph.get(nodeID);
			n.setStartTime(bestNode.getStartTime());
			n.setEndTime(bestNode.getEndTime());
			n.setProcessor(bestNode.getProcessor());
		}
	}
	
	public HashMap<String, Node> getCurrentBestState(){
		return _currentBestState;
	}
	
	public void printCurrentBestState(){
		for(String s:_currentBestState.keySet()){
			Node n = _currentBestState.get(s);
			System.out.println("Node ID: "+n.getID()+" Start Time: "+n.getStartTime()+" Processor: "+n.getProcessor() +" End Time: "+n.getEndTime());
		}
	}
}
