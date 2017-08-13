package DataStructure;
import java.util.HashMap;
/**
 * This class is responsible for storing all the nodes along
 * with their different attributes The attributes include the children of the node,
 * the name of the node, the task cost of that node itself and whether the node has
 * already been completed in the traversal.
 */
public class Node {
	// this is the parents hashmap with the parent node and their communication cost
	private HashMap<Node, Double> _parents; 
	// this is the children hashmap with the child node and their comunication cost
	private HashMap<Node, Double>_children;
	// name of the node
	private String _id;
	// cost of the node itself
	private int _cost; 
	private HashMap<String, Double> _distances;
	// boolean of whether the node has already been visited in the traversal
	private boolean _completed;
	// the processor on which to set the node on
	private int _processor;
	// start and end times of the nodes which depend on the communication as well as the task costs
	private double _startTime;
	private double _endTime;
	// stores all the nodes we visited in the state space before this node was visited


	public Node(String id, int cost){
		_id = id;
		_cost = cost;
		// children and parents hashmaps with the child and parent nodes and their comunication costs
		_parents = new HashMap<Node, Double>();
		_children = new HashMap<Node, Double>();
		// storing the distances in a matrix to all the other nodes in the network (infinitiy and actual reachable costs)
		_distances = new HashMap<String, Double>();  
		_completed = false;
		// stores all the nodes we visited in the state space before this node was visited
	}

	/**
	* This method adds a parent node along with its cost to the parents hashmap
 	* @param node This is the node to be added
 	* @param cost This is the task processing cost of the node
 	*/
	public void addParent(Node node, double cost){
		_parents.put(node, cost);
	}


	/**
	* This method adds a child node along with its cost to the children hashmap
 	* @param node This is the node to be added
 	* @param cost This is the task processing cost of the node
 	*/
	public void addChild(Node node, double cost){
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
	* This method sets the processor for which the node is to be put on.
 	* It is set according to the interger input
 	* @param i This integer number shows which processor number the node is
 	* to be put on
 	*/
	public void setProcessor(int i){
		_processor = i;
	}

	/**
	* This method returns the processor number of the node
 	* @return This returns the processor of the node
 	*/
	public int getProcessor(){
		return _processor;
	}

	/**
	* This method sets the start time of the node. This start time indicates
 	* the time when the processing of the node begins in the schedule
 	* @param i This is what the start time will be set to
 	*/
	public void setStartTime(Double i){
		_startTime = i;
	}

	/**
	* This method returns the start time of the node. This start time indicates
 	* the time when the processing of the node begins in the schedule
 	* @return The start time of the node is returned
 	*/
	public double getStartTime(){
		return _startTime;
	}

	/**	
	* This method sets the end time of the node. This end time indicates
 	* the time when the processing of the node ends in the schedule
 	* @param i This is what the end time will be set to
 	*/
	public void setEndTime(Double i){
		_endTime = i;
	}

	/**
	* This method returns the end time of the node. This end time indicates
 	* the time when the processing of the node ends in the schedule
 	* @return The end time of the node is returned
 	*/
	public double getEndTime(){
		return _endTime;
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
	public String getID(){
		return _id;
	}

	/**
	* This method returns the cost of a node
 	* @return the cost for a node
 	*/
	public int getCost(){
		return _cost;
	}

	/**
	* This method returns a list of the parents of a node
 	* @return a hashmap consisting of the parents
 	*/
	public HashMap<Node, Double> getParents(){
		return _parents;
	}

	/**
	* This method returns a list of the children of a node
 	* @return a hashmap consisting of the children
 	*/
	public HashMap<Node, Double> getChildren(){
		return _children;
	}
}
