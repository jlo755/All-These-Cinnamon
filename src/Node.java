import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private HashMap<Node, Double> _parents; // this is the parents hashmap with the parent node and their communication cost
	private HashMap<Node, Double>_children; // this is the children hashmap with the child node and their comunication cost
	private String _id; // name of the node
	private int _cost; // cost of the node itself
	private HashMap<String, Double> _distances;
	private boolean _completed;
	private int _processor;
	private double _startTime;
	private double _endTime;
	private ArrayList<Node> _stateParents;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_parents = new HashMap<Node, Double>();
		_children = new HashMap<Node, Double>();
		_distances = new HashMap<String, Double>(); // storing the distances in a matrix to all the other nodes in the network (infinitiy and actual reachable costs) 
		_completed = false;
		_stateParents = new ArrayList<Node>();
	}

	protected void addParent(Node node, double cost){
		_parents.put(node, cost);
	}
	
	protected void addStateParents(Node node){
		_stateParents.add(node);
	}
	
	protected ArrayList<Node> getStateParents(){
		return _stateParents;
	}

	protected void addChild(Node node, double cost){
		_children.put(node, cost);
	}

	public boolean getCompleted() {
		return _completed;
	}

	public void setProcessor(int i){
		_processor = i;
	}

	public int getProcessor(){
		return _processor;
	}

	public void setStartTime(Double i){
		_startTime = i;
	}

	public double getStartTime(){
		return _startTime;
	}

	public void setEndTime(Double i){
		_endTime = i;
	}

	public double getEndTime(){
		return _endTime;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}


	protected String getID(){
		return _id;
	}

	protected void updateDistance(String n, double i){
		_distances.put(n, i);
	}

	protected HashMap<String, Double> getDistances(){
		return _distances;
	}

	protected int getCost(){
		return _cost;
	}

	protected HashMap<Node, Double> getParents(){
		return _parents;
	}

	protected HashMap<Node, Double> getChildren(){
		return _children;
	}
}