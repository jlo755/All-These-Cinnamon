import java.util.ArrayList;
/**
* This class is responsible for extending out the state space of the input graph
* It stores information about the relationships between the different nodes and their
* attributes as well as the possible proccessors they could be placed on
*/
public class StateSpaceNode extends Node {
	// boolean of whether the node has already been visited in the traversal
	private boolean _completed;
	// the processor the node could be placed on out of all the available processors
	private int _processor;
	// start and end times of the nodes which depend on the communication as well as the task costs
	private double _startTime;
	private double _endTime;
	// stores all the nodes we visited in the state space before this node was visited
	private ArrayList<StateSpaceNode> _stateParents;

	protected StateSpaceNode(String id, int cost, int processor){
		super(id, cost);
		_startTime = 0.0;
		_endTime = 0.0;
		// stores all the nodes we visited in the state space before this node was visited
		_stateParents = new ArrayList<StateSpaceNode>();
	}

	/**
	* This method adds a node as a parent into a list which contains all the nodes we visited
	* in the state space before this node
	* @param node The node to be added into the list
	*/
	protected void addStateParents(StateSpaceNode node){
		_stateParents.add(node);
	}
	
	/*
	* This method returns the list of parents
	* @return This returns a list consisting of all the parents
	*/
	protected ArrayList<StateSpaceNode> getStateParents(){
		return _stateParents;
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
}
