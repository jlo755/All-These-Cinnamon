package scheduling;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import dataStructure.Node;

/**
 *
 * This class produces an optimal solution to a multi-processor
 * scheduling problem. This is based on an input graph representing a
 * series of tasks costs and their dependencies, as well as the
 * number of processors specified.
 *
 */
public class Scheduler {

	private double currentBestSolution; // Current best scheduling time
	private FinalState bestState; // Best solutions for all of the Nodes.
	private HashMap<String, Node> nodeMap; // Input task scheduling graph.
	private int _numProcessors; // Number of processors assigned for scheduling.

	/**
	 * Initialize the best solution so far to infinity on starting.
	 */
	public Scheduler() {
		currentBestSolution = Double.POSITIVE_INFINITY;
	}

	/**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 */
	public void schedule() {
		double hello = this.findStartingOptimalBranch(nodeMap);
		for (Node n : nodeMap.values()) {
			n.setCompleted(false);
			n.setEndTime(0.0);
			n.setStartTime(0.0);
		}
		System.out.println(currentBestSolution);
		// "Nodemap" is the input graph for the algorithm.
		//for (int i = 1; i <= _numProcessors; i++) {
		for (Node n : nodeMap.values()) {

			// If a node doesn't have parents, it is a starting node
			if (n.getParents().isEmpty()) {
				n.setProcessor(1);

				// call the recursive DFS method for that node to obtain and process all of the
				// children
				dfs(nodeMap, n.getID());
				//bestState.finalStateToGraph(nodeMap);
			}
		}
		//}
		System.out.println(currentBestSolution);
	}

	/**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 *
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 */

	public void dfs(HashMap<String, Node> graph, String nodeName) {

		// set the node as being completed
		graph.get(nodeName).setCompleted(true);
		ArrayList<String> reachableNodes = getReachable(graph);

		// this calculates the time at which the node task can be placed in that
		// particular processor
		calculateTime(graph, graph.get(nodeName));

		// loop through the different available processors to check for and compare
		// between the final times when the node
		// is placed on each of the processors
		for (int i = 1; i <= _numProcessors; i++) {

			// Repeat these processes until all of the processor and node possibilities have
			// been covered
			for (String currentNode : reachableNodes) {

				// set the node on a processor
				graph.get(currentNode).setProcessor(i);

				// call the recursive DFS method to check for the processing times of the child
				// nodes
				int loadBalancedTime = this.getPerfectLoadBalance(graph)/_numProcessors;
				double max = 0;
				double bottomLevelNode = 0;
				for(Node n:graph.values()){
					if(n.isCompleted()){
						bottomLevelNode = findMaxBottomLevel(graph, n.getID());
						/*System.out.println(n.getID());
						System.out.println("End Time: "+n.getEndTime());
						System.out.println("Max: "+bottomLevelNode);*/
						if(bottomLevelNode > max){
							max = bottomLevelNode;
						}
					}
				}
				double totalTime = Math.max(loadBalancedTime, max);
				if(totalTime < currentBestSolution){
					dfs(graph, currentNode);
					// set the times of the node to 0
					graph.get(currentNode).setStartTime(0.0);
					graph.get(currentNode).setEndTime(0.0);

					// set the node as not finished
					graph.get(currentNode).setCompleted(false);
				} else {
					//System.out.println("Is this bounding?");
					// set the times of the node to 0
					graph.get(currentNode).setStartTime(0.0);
					graph.get(currentNode).setEndTime(0.0);

					// set the node as not finished
					graph.get(currentNode).setCompleted(false);
				}
			}

			// If there are no reachable nodes, it is an exit node.
			if (reachableNodes.isEmpty()) {

				// Get the max endTime value for this processor branch.
				double max = 0;
				for (Node n : graph.values()) {
					if (n.getEndTime() > max) {
						max = n.getEndTime();
					}
				}

				// reset the best solution if a lower scheduling time has been found
				if (max < currentBestSolution) {

					currentBestSolution = max;
					bestState.setCurrentBestState(graph);
				}
			}
		}
	}

	/**
	 * Get a list of reachable Nodes from the supplied Node in a given graph.
	 *
	 * @param graph
	 *            This is the input node graph
	 * @return reachableNodeNames the reachable nodes list
	 */
	public ArrayList<String> getReachable(HashMap<String, Node> graph) {

		ArrayList<String> reachableNodeNames = new ArrayList<String>();

		for (String nodeName : graph.keySet()) {

			// skip already completed nodes
			if (graph.get(nodeName).isCompleted()) {
				continue;
			}
			boolean dependenciesDone = true;

			// if the parent nodes have not been completed, then the dependencies are not
			// complete
			for (Node parentNode : graph.get(nodeName).getParents().keySet()) {
				if (!parentNode.isCompleted()) {
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
	 * This calculates the time at which the node can be placed in the scheduler in
	 * that particular processor
	 *
	 * @param graph
	 *            - is the original dependency graph, NOT the state space. In our
	 *            case, it stores HashMap<String, Node> - name of the node, to the
	 *            Node itself.
	 *
	 * @param node
	 *            - this is the current node we're calculating time for.
	 *
	 **/
	public void calculateTime(HashMap<String, Node> graph, Node node) {

		double maxCost = 0.0, maxProcessorTime = 0.0;

		// check if it has a parent
		maxProcessorTime = calculateMaxCurrentProcessorTime(graph, node.getProcessor());
		if (node.getParents().size() != 0) {

			// if any of the parents are on a different processor
			if (checkListContainsParent(graph, node)) {

				// Calculating the node that has the max end time of the processor in use
				maxCost = getMaxCommunicationCost(node);

				// if communication cost of a parent in the other processor is more than
				// the processing cost of the parent in the same processor
				if (maxProcessorTime <= maxCost) {

					// start time of task after the parent in other processor has finished
					node.setStartTime(maxCost);

					// current task finishes when it has processed ( in "cost" time )
					node.setEndTime(maxCost + node.getCost());
				} else {

					// current task starts as soon as parent in the same processor ends
					node.setStartTime(maxProcessorTime);

					// current task finishes when it has processed ( in "cost" time )
					node.setEndTime(node.getCost() + maxProcessorTime);
				}
			} else {

				// first node will start immediately
				node.setStartTime(maxProcessorTime);

				// current task finishes when it has processed ( in "cost" time )
				node.setEndTime(node.getCost() + maxProcessorTime);
			}
		} else {

			// first node will start immediately
			node.setStartTime(maxProcessorTime);

			// current task finishes when it has processed ( in "cost" time )
			node.setEndTime(node.getCost() + maxProcessorTime);
		}
	}

	/**
	 * Checks if a node has parents in a processor and returns true or false
	 *
	 * @param graph
	 *            is the original dependency graph, NOT the state space. In our
	 *            case, it stores HashMap<String, Node> - name of the node, to the
	 *            Node itself.
	 * @param node
	 *            this is the current node we�re checking the parent for
	 * @return returns whether the node has a parent in a processor
	 */

	public boolean checkListContainsParent(HashMap<String, Node> graph, Node node) {

		ArrayList<String> completedNodes = new ArrayList<String>();

		// populate the completed nodes list according to whether it is set as completed
		for (Node n : graph.values()) {
			if (n.getProcessor() == node.getProcessor() && n.isCompleted()) {
				completedNodes.add(n.getID());
			}
		}

		// get all the parents of that node and check if they are present in the
		// processor
		for (Node n : node.getParents().keySet()) {
			if (n.getProcessor() != node.getProcessor()) {
				return true;
			}
			if (!(completedNodes.contains(n.getID()))) {
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
	public double calculateMaxCurrentProcessorTime(HashMap<String, Node> graph, int processor) {
		double max = 0.0;

		// go through the graph and then obtain the maximum time in the schedule for
		// that particular processor
		for (Node n : graph.values()) {

			// check if the node exists in the processor we are trying to get the max time
			// for
			if (n.getProcessor() == processor) {

				// update the max time
				if (n.getEndTime() > max) {
					max = n.getEndTime();
				}
			}
		}
		return max;
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
		for (Node n1 : parent.getChildren().keySet()) {

			// child node in the list
			if (n1.equals(node)) {

				// get link cost (value) of parent with the node
				return parent.getChildren().get(n1);
			}
		}
		return 0.0;
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

		// get parent list of a node, where parent belongs to a different processor
		for (Node n1 : n.getParents().keySet()) {
			if (n1.getProcessor() != n.getProcessor()) {

				// get cost of the current task that needs to be processed after its parent
				// in the other processor i.e communication cost + task cost
				if (calculateCommunicationCost(n, n1) + n1.getEndTime() > ccost) {
					ccost = calculateCommunicationCost(n, n1) + n1.getEndTime();
				}
			}
		}

		return ccost;
	}

	public int getPerfectLoadBalance(HashMap<String, Node> graph){
		int totalTime = 0;
		for(int j = 1; j<= _numProcessors; j++){
			totalTime += calculateMaxCurrentProcessorTime(graph, j);
		}
		for(Node node: graph.values()){
			if(!node.isCompleted()){
				totalTime += node.getCost();
			}
		}
		return totalTime;
	}

	public double findStartingOptimalBranch(HashMap<String, Node> graph){
		Queue<Node> optimalBranch = new PriorityQueue<Node>();
		/*Comparator<Node> nodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node left, Node right) {
				if(left.getBottomLevel() < right.getBottomLevel()){
					return 1;
				} else if (left.getBottomLevel() > right.getBottomLevel()){
					return -1;
				} else {
					return 0;
				}
			}
		};*/
		for(Node n:graph.values()){
			//System.out.println("N: "+n.getID());
			n.setBottomLevel(findMaxBottomLevel(graph, n.getID()));
			//System.out.println(n.getBottomLevel());
			optimalBranch.add(n);
		}
		while(!optimalBranch.isEmpty()){
			Node n = optimalBranch.poll();
			n.setCompleted(true);
			double currentBestTime = Double.POSITIVE_INFINITY;
			double currentStartTime = Double.POSITIVE_INFINITY;
			int previousProcessor = n.getProcessor();
			for(int i = 1; i<=_numProcessors; i++){
				n.setProcessor(i);
				calculateTime(graph, n);
				//System.out.println("Node: "+n.getID());
				//System.out.println(n.getEndTime());
				if(currentBestTime > n.getEndTime()){
					currentBestTime = n.getEndTime();
					currentStartTime = n.getStartTime();
					previousProcessor = n.getProcessor();
				}
				n.setEndTime(0.0);
				n.setStartTime(0.0);
			}
			n.setStartTime(currentStartTime);
			n.setEndTime(currentBestTime);
			n.setProcessor(previousProcessor);
			if(optimalBranch.isEmpty()){
				double max = 0;
				for (Node n2 : graph.values()) {
					//System.out.println("Node: "+n2.getID()+" End Time: "+n2.getEndTime());
					if (n2.getEndTime() > max) {
						max = n2.getEndTime();
					}
				}
				if (max < currentBestSolution) {
					currentBestSolution = max;
					//System.out.println("Hello: "+currentBestSolution);
					bestState.setCurrentBestState(graph);
					for(Node n1:graph.values()){
						//System.out.println("Node name: "+n1.getID()+" Processor: "+n1.getProcessor());
						//System.out.println("Node end: "+n1.getEndTime());
					}
				}
			}
		}
		return 0.0;
	}

	public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode){
		Stack<String> s = new Stack<String>();
		s.add(initialNode);
		double startTime = graph.get(initialNode).getStartTime();
		graph.get(initialNode).setOptimalBottomLevel(startTime+graph.get(initialNode).getCost());
		//System.out.println("Initial Node: "+initialNode);
		//double totalTime = startTime;
		double max = 0;
		/*for(Node n:graph.values()){
			if(n.isCompleted()){
				System.out.println("Node name: "+n.getID());
				System.out.println("Node endTime: "+n.getEndTime());
				System.out.println("Node processor: "+n.getProcessor());
			}
		}*/
		//graph.get(initialNode).setBottomLevel(0);
		//System.out.println("\nNode Started: "+initialNode);
		while(!s.isEmpty()){
			Node node = graph.get(s.pop());
			//System.out.println("Node popped: "+node.getID());
			if(node.getParents().keySet().contains(graph.get(initialNode))){
				//System.out.println("Reset");
				graph.get(initialNode).setOptimalBottomLevel(startTime+graph.get(initialNode).getCost());
				//totalTime = startTime+graph.get(initialNode).getCost();
			}
			for(Node child: node.getChildren().keySet()){
				s.push(child.getID());
			}
			if(node.getID() != initialNode) {
				double test = 0;
				for(Node parent:node.getParents().keySet()) {
					//System.out.println("Parent: "+parent.getID());
					//System.out.println("Cost: "+parent.getOptimalBottomLevel());
					if(test<parent.getOptimalBottomLevel()) {
						test = parent.getOptimalBottomLevel();
					}
				}
				//System.out.println(node.getCost());
				node.setOptimalBottomLevel(test+node.getCost());
			}
			/*totalTime += node.getCost();
			if(totalTime > max){
				max = totalTime;
			}*/
		}
		for(Node n:graph.values()) {
			if(n.getOptimalBottomLevel() > max && n.getChildren().isEmpty()) {
				max = n.getOptimalBottomLevel();
			}
			n.setOptimalBottomLevel(0.0);
		}
		//System.out.println(max);
		return max;
	}

	/*public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode){
		Node node = graph.get(initialNode);
		node.setBottomLevelFlag(true);
		for(Node n:node.getChildren().keySet()) {
			if(!n.getBottomLevelFlag()) {
				findMaxBottomLevel(graph, n.getID());
			}
		}
	}*/

	/**
	 * Returns the currentBestSolution field.
	 *
	 * @return
	 */
	public double getCurrentBestSolution() {
		return currentBestSolution;
	}

	/**
	 * Returns the bestState field.
	 *
	 * @return
	 */
	public FinalState getBestState() {
		return bestState;
	}

	public void setProcessorNumber(int processors) {
		_numProcessors = processors;
	}

	/**
	 * Sets the bestState field.
	 *
	 * @param newBestState
	 */
	public void setBestState(FinalState newBestState) {
		bestState = newBestState;
	}

	/**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 *
	 * @param taskGraph
	 */
	public void provideTaskGraph(HashMap<String, Node> taskGraph) {
		nodeMap = taskGraph;
		bestState = new FinalState(taskGraph);

	}

}
