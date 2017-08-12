import java.util.HashMap;
/**
* This class is responsible for storing all the nodes along
* with their different attributes The attributes include the children of the node,
* the name of the node, the task cost of that node itself and whether the node has
* already been completed in the traversal.
*/
public abstract class Node {
	// this is the children hashmap with the child node and their comunication cost
	protected HashMap<Node, Double>_children; 
	// name of the node
	private String _id;
	// cost of the node itself
	private int _cost; 
	// boolean of whether the node has already been visited in the traversal
	private boolean _completed;

	// stores all the nodes we visited in the state space before this node was visited
	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_children = new HashMap<Node, Double>();
		_completed = false;
		
	}

	/**
	* This method adds a child node along with its cost to the children hashmap
	* @param node This is the node to be added
	* @param cost This is the task processing cost of the node
	*/
	protected void addChild(Node node, double cost){
		_children.put(node, cost);
	}

	/**
	* This method returns the status of a node through a boolean and this shows 
	* whether it is completed or not
	* @return a boolean of whether the node is complete or not
	*/
	public boolean getCompleted() {
		return _completed;
	}

	/**
	* This method sets the completed field for a node using the provided boolean completed input
	* @param completed this is the completed boolean of the node
	*/
	public void setCompleted(boolean completed) {
		_completed = completed;
	}
	
	/**
	* This method returns the id of a node
	* @return an id for a node
	*/
	protected String getID(){
		return _id;
	}
	
	/**
	* This method returns the cost of a node
	* @return the cost for a node
	*/
	protected int getCost(){
		return _cost;
	}

	/**
	* This method returns a list of the children of a node
	* @return a hashmap consisting of the children
	*/
	protected HashMap<Node, Double> getChildren(){
		return _children;
	}
}
