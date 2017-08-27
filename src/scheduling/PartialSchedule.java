package scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import dataStructure.Node;

/**
 * This class represents a partially completed solution for multi-processor task scheduling.
 *
 */
public class PartialSchedule {
	
	private double _totalIdleTime;
	private double _totalTaskTime;
	private double[] _processorTimes;
	private int _numProcessors;
	private boolean[] _completed;
	private double[] _startTimes;
	private double[] _endTimes;
	private double[] _bottomLevels;
	private int[] _processors;
	private LinkedHashMap<String, Node> _taskGraph;
	private HashMap<String, Integer> _nodeOrdering;
	
	public PartialSchedule(LinkedHashMap<String, Node> taskGraph, int numProcessors, double totalTaskTime) {
		int size = taskGraph.values().size();
		_totalIdleTime = 0.0;
		_totalTaskTime = totalTaskTime;
		_numProcessors = numProcessors;
		_processorTimes = new double[_numProcessors];
		_completed = new boolean[size];
		_startTimes = new double[size];
		_endTimes = new double[size];
		_processors = new int[size];
		_taskGraph = taskGraph;
		_nodeOrdering = new HashMap<String, Integer>();
		_bottomLevels = new double[size];
	}
	
	/**
	 * Sets the idle time.
	 * @param idleTime
	 */
	public void setIdleTime(double idleTime) {
		_totalIdleTime = idleTime;
	}
	
	/**
	 * Sets the processor times.
	 * @param processorTimes
	 */
	public void setProcessorTimes(double[] processorTimes) {
		for(int i = 0; i<processorTimes.length; i++) {
			_processorTimes[i] = processorTimes[i];
		}
	}

	/**
	 * Sets the completed nodes' status.
	 * @param completed
	 */
	public void setCompleted(boolean[] completed) {
		for(int i = 0; i<completed.length; i++) {
			_completed[i] = completed[i];
		}
	}
	
	/**
	 * Sets the end times of the nodes.
	 * @param endTimes
	 */
	public void setEndTimes(double[] endTimes) {
		for(int i = 0; i<endTimes.length; i++) {
			_endTimes[i] = endTimes[i];
		}
	}
	
	/**
	 * Sets the start times of the nodes.
	 * @param startTimes
	 */
	public void setStartTimes(double[] startTimes) {
		for(int i = 0; i<startTimes.length; i++) {
			_startTimes[i] = startTimes[i];
		}
	}
	
	/**
	 * Sets what processor the node is on.
	 * @param processors
	 */
	public void setProcessors(int[] processors) {
		for(int i = 0; i<processors.length; i++) {
			_processors[i] = processors[i];
		}
	}
	
	/**
	 * Set the bottom level of the node.
	 * @param bottomLevels
	 */
	public void setBottomLevel(double[] bottomLevels) {
		_bottomLevels = bottomLevels;
	}
	
	/**
	 * Sets the order for the new child schedule.
	 * @param nodeOrdering
	 */
	public void setOrder(HashMap<String, Integer> nodeOrdering) {
		_nodeOrdering = nodeOrdering;
	}
	
	/**
	 * Decides the index of all the node for arrays.
	 */
	public void decideIndex() {
		int index = -1;
		for(Node n:_taskGraph.values()) {
			index++;
			_nodeOrdering.put(n.getID(), index);
			_bottomLevels[index] = n.getBottomLevel();
		}
	}
	
	/**
	 * Updates all of the states of this partial schedule and calculates the earliest start time of the node on the processor.
	 * @param node
	 * @param processor
	 */
	public void update(Node node, int processor) {
		int index = _nodeOrdering.get(node.getID());
		_completed[index] = true;
		_processors[index] = processor;
		_totalIdleTime += updateIdleTime(node);
		double earliestStartTime = calculateTime(node);
		_processorTimes[processor-1] = earliestStartTime + node.getCost();
		_startTimes[index] = earliestStartTime;
		_endTimes[index] = earliestStartTime + node.getCost();
	}
	
	/**
	 * Calculates the earliest time a node can be placed on a processor.
	 * @param node
	 * @return
	 */
	public double calculateTime(Node node) {
		double maxCost = 0.0, maxProcessorTime = 0.0;
		int index = _nodeOrdering.get(node.getID());
		// check if it has a parent
		maxProcessorTime = calculateMaxCurrentProcessorTime(_processors[index]);
		if (node.getParents().size() != 0) {

			// if any of the parents are on a different processor
			if (checkListContainsParent(node)) {

				// Calculating the node that has the max end time of the processor in use
				maxCost = getMaxCommunicationCost(node);
				if (maxProcessorTime <= maxCost) {
					return maxCost;
				} else {
					return maxProcessorTime;
				}
			} else {
				return maxProcessorTime;
			}
		} else {
			return maxProcessorTime;
		}
	}

	/**
	 * Checks if a node has parents in a processor and returns true or false
	 * 
	 * @param node
	 *            this is the current node we’re checking the parent for
	 * @return returns whether the node has a parent in a processor
	 */

	public boolean checkListContainsParent(Node node) {
		// get all the parents of that node and check if they are present in the
		// processor
		int indexChild = _nodeOrdering.get(node.getID());
		for (Node n : node.getParents().keySet()) {
			int indexParent = _nodeOrdering.get(n.getID());
			if (_processors[indexChild] != _processors[indexParent]) {
				return true;
			} else if (!_completed[indexParent]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method returns the max time for a processor
	 * 
	 * @param graph
	 *            This is the graph containing all the nodes in the network
	 * @param processor
	 *            This is the processor we are obtaining the max time for
	 * @return This returns the mac time for that particular processor
	 */
	public double calculateMaxCurrentProcessorTime(int processor) {
		return _processorTimes[processor-1];
	}

	/**
	 * This method calculates the commucation cost between a node and its parent
	 * 
	 * @param node
	 *            This is the node we are trying to get the communication cost for
	 * @param parent
	 *            This is the parent node
	 * @return This returns the cost between the node and its parent. If the link
	 *         doesn't exist, this returns 0.0
	 */
	public double calculateCommunicationCost(Node node, Node parent) {
		// get children list of parent
		if (parent.getChildren().containsKey(node)) {
			// get link cost (value) of parent with the node
			return parent.getChildren().get(node);
		} else {
			return 0.0;
		}
	}

	/**
	 * This method gets the communication cost between this node and its parents and
	 * then adds it to the task processing cost of the node and then returns this
	 * total cost
	 * 
	 * @param node
	 *            This is the node we are trying to get the maximum communication
	 *            cost for
	 * @return This returns the cost which includes the communication cost and the
	 *         task cost
	 */
	public double getMaxCommunicationCost(Node n) {

		double ccost = 0.0;
		int index = _nodeOrdering.get(n.getID());
		// get parent list of a node, where parent belongs to a different processor
		for (Node n1 : n.getParents().keySet()) {
			int parentIndex = _nodeOrdering.get(n1.getID());
			if (_processors[index] != _processors[parentIndex]) {

				// get cost of the current task that needs to be processed after its parent
				// in the other processor i.e communication cost + task cost
				if (calculateCommunicationCost(n, n1) + _endTimes[parentIndex] > ccost) {
					ccost = calculateCommunicationCost(n, n1) + _endTimes[parentIndex];
				}
			}
		}

		return ccost;
	}
	
	/**
	 * Updates the new idle time of the node.
	 * @param node
	 * @return
	 */
	public double updateIdleTime(Node node){
		int index = _nodeOrdering.get(node.getID());
		double processorTime = this.calculateMaxCurrentProcessorTime(_processors[index]);
		double earliestStartTime = calculateTime(node);
		double idleTime = earliestStartTime - processorTime;
		return idleTime;
	}
	
	/**
	 * Retrieves the end time of a node.
	 * @param node
	 * @return
	 */
	public double getNodeEndTime(Node node) {
		int index = _nodeOrdering.get(node.getID());
		return _endTimes[index];
	}
	
	/**
	 * Get all of the reachable nodes from the current partial schedule.
	 * @return
	 */
	public ArrayList<String> getReachable() {
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		for (String nodeName : _taskGraph.keySet()) {
			int index = _nodeOrdering.get(nodeName);
			if (_completed[index]) {
				continue;
			}
			boolean dependenciesDone = true;

			// if the parent nodes have not been completed, then the dependencies are not
			// complete
			for (Node parentNode : _taskGraph.get(nodeName).getParents().keySet()) {
				int parentIndex = _nodeOrdering.get(parentNode.getID());
				if (!_completed[parentIndex]) {
					dependenciesDone = false;
					break;
				}
			}

			// add the node into the list as reachable if the dependencies are done
			if (dependenciesDone) {
				reachableNodeNames.add(nodeName);
			}
		}
		return reachableNodeNames;
	}
	
	/**
	 * Create a new child schedule from this partial schedule.
	 * @return
	 */
	public PartialSchedule makeChildSchedule() {
		PartialSchedule newSchedule = new PartialSchedule(_taskGraph, _numProcessors, _totalTaskTime);
		newSchedule.setStartTimes(_startTimes);
		newSchedule.setProcessorTimes(_processorTimes);
		newSchedule.setProcessors(_processors);
		newSchedule.setIdleTime(_totalIdleTime);
		newSchedule.setEndTimes(_endTimes);
		newSchedule.setCompleted(_completed);
		newSchedule.setOrder(_nodeOrdering);
		newSchedule.setBottomLevel(_bottomLevels);
		return newSchedule;
	}
	
	/**
	 * Use the more dominating heuristic function between critical path and perfect load balancing.
	 * @param n
	 * @return
	 */
	public double getMaxHeuristic(Node n) {
		double totalIdleTime = (_totalIdleTime+_totalTaskTime)/this._numProcessors;
		double max = 0;
		for(int i = 0; i<_startTimes.length; i++) {
			double criticalPath = _startTimes[i] + _bottomLevels[i];
			if(criticalPath>max) {
				max = criticalPath;
			}
		}
		return Math.max(totalIdleTime, max);
	}

	/**
	 * Gets all processors' times from this partial schedule.
	 * @return
	 */
	public double[] getProcessorTimes() {
		return _processorTimes;
	}
	
	/**
	 * Gets all end times of nodes from this partial schedule.
	 * @return
	 */
	public double[] getEndTimes() {
		return _endTimes;
	}
	
	/**
	 * Gets all start times of nodes from this partial schedule.
	 * @return
	 */
	public double[] getStartTimes() {
		return _startTimes;
	}
	
	/**
	 * Returns the indexing used for the nodes.
	 * @return
	 */
	public HashMap<String, Integer> getNodeOrdering(){
		return _nodeOrdering;	
	}
	
	/**
	 * Returns the processor numbers for the nodes.
	 * @return
	 */
	public int[] getNodeProcessors() {
		return _processors;
	}
	
	/**
	 * Finding out which processors have a current start time of zero.
	 * @return
	 */
	public int startTimeZeroProcessors() {
		int count = 0;
		for(int i = 0; i<_processorTimes.length; i++) {
			if(_processorTimes[i] == 0.0) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Gets the current time for the specified processor.
	 * @param processor
	 * @return
	 */
	public double getProcessorTime(int processor) {
		return _processorTimes[processor-1];
		
	}

	/**
	 * Gets the starting time of the specified node.
	 * @param node
	 * @return
	 */
	public double getNodeStartTime(Node node) {
		int index = _nodeOrdering.get(node.getID());
		return _startTimes[index];
	}
	
	public String generateId() {
		String id = "";
		for(int i = 0; i<_endTimes.length; i++) {
			id=id+_endTimes[i];
		}
		return id;
	}
}
