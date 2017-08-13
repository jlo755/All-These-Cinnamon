import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.jgrapht.ext.ImportException;

import DataStructure.Node;
import InputParse.DotParser;

/**
* This class recursively calls the recursive method to get the children nodes of a particular node. As this occurs recursively,
* we traverse through the entire graph. When a node is visited, we check all possible time costs for that particular node being
* placed on the different processors. We then check which of these is the best time. This process continues until all the nodes
* in the network are visited and we have obtained the best scheduling times.
*/
public class RecursiveDFS {

	static double currentBestSolution = Double.POSITIVE_INFINITY;
	static FinalState bestState;

	public static void main(String[] args) throws IOException, ImportException {
		HashMap<String, Node> nodeMap;

		//InputParser parser = new InputParser();
		//parser.parseInput(nodeMap);
		DotParser dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		nodeMap = dotParser.getNodeMap();
		bestState = new FinalState(nodeMap.keySet());
		// "Nodemap" is the input graph for the algorithm.
		for(int i = 1; i<=1; i++) {
			for(Node n:nodeMap.values()) {
				// If a node doesn't not have parents, it is a starting node
				if(n.getParents().isEmpty()) {
					n.setProcessor(i);
					long startTime = System.nanoTime();
					// call the recursive DFS method for that node to obtain and process all of the children
					recursiveDFS(nodeMap, n.getID());
					long endTime = System.nanoTime();
					System.out.println((endTime-startTime)/ 1000000000.0);
					
				}
			}
		}
		bestState.printCurrentBestState();
		System.out.println(currentBestSolution);
	}

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph This is the input graph
	 * @param nodeName This is the node which we are starting off from
	 */

	public static void recursiveDFS(HashMap<String, Node> graph, String nodeName) {
		// set the node as being completed
		graph.get(nodeName).setCompleted(true);
		ArrayList<String> test = getReachable(graph);
		// this calculates the time at which the node task can be placed in that particular processor
		calculateTime(graph, graph.get(nodeName));
		// loop through the different available processors to check for and compare between the final times when the node
		// is placed on each of the processors
		
		for(int i = 1; i<=2; i++) {
			// Repeat these processes until all of the processor and node possibilities have been covered
			for(String node1:test) {
				graph.get(node1).setProcessor(i);
				// call the recursive DFS method to check for the processing times of the child nodes
				recursiveDFS(graph, node1);
				// set the times of the node to 0
				graph.get(node1).setStartTime(0.0);
				graph.get(node1).setEndTime(0.0);
				// set the node as not finished
				graph.get(node1).setCompleted(false);
			}
			if(test.isEmpty()) {
				double max = 0;
				for(Node n:graph.values()){
					if(n.getEndTime() > max) {
						max = n.getEndTime();
					}
				}
				// reset the best solution if a lower scheduling time has been found
				if(max < currentBestSolution) {
					currentBestSolution = max;
					bestState.setCurrentBestState(graph);
				}
			}
		}
	}


	/**
	 * Get a list of reachable Nodes from the supplied Node in a given graph.
	 * @param graph This is the input node graph
	 * @return reachableNodeNames the reachable nodes list
	 */
	public static ArrayList<String> getReachable(HashMap<String, Node> graph) {
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		for(String nodes : graph.keySet()){
			if(graph.get(nodes).getCompleted()){
				continue;
			}
			boolean dependenciesDone = true;
			// if the parent nodes have not been completed, then the dependencies are not complete
			for(Node parentNode:graph.get(nodes).getParents().keySet()){
				if(!parentNode.getCompleted()){
					dependenciesDone = false;
					break;
				}
			}
			// add the node into the list as reachable if the dependencies are done
			if(dependenciesDone){
				reachableNodeNames.add(nodes);
			}
		}
		return reachableNodeNames;
	}

	/**
	 * This calculates the time at which the node can be placed in the scheduler in that particular processor
	 * @param graph - is the original dependency graph, NOT the state space. In our
	 *        case, it stores HashMap<String, Node> - name of the node, to the Node
	 *        itself.
	 * 
	 * @param node - this is the current node we’re calculating time for.
	 * 
	 **/

	public static void calculateTime(HashMap<String, Node> graph, Node node){
		double maxCost = 0.0, maxProcessorTime = 0.0;
		//check if it has a parent
		maxProcessorTime = calculateMaxCurrentProcessorTime(graph, node.getProcessor());
		if(node.getParents().size() != 0 ){
			//if all the parents are on the same processor
			if (checkListContainsParent(graph, node)){
				// Calculating the node that has the max end time of the processor in use
				maxCost = getMaxCommunicationCost(node);
				// if communication cost of a parent in the other processor is more than
				// the processing cost of the parent in the same processor
				if (maxProcessorTime <= maxCost) {
					//start time of task after the parent in other processor has finished
					node.setStartTime(maxCost);
					//current task finishes when it has processed ( in "cost" time )
					node.setEndTime(maxCost + node.getCost());
				} else {
					//current task starts as soon as parent in the same processor ends
					node.setStartTime(maxProcessorTime);
					//current task finishes when it has processed ( in "cost" time )
					node.setEndTime(node.getCost()+maxProcessorTime);
				}
			} else{
				//first node will start immediately
				node.setStartTime(maxProcessorTime);
				//current task finishes when it has processed ( in "cost" time )
				node.setEndTime(node.getCost()+maxProcessorTime);
			}
		} else{
			//first node will start immediately
			node.setStartTime(maxProcessorTime);
			//current task finishes when it has processed ( in "cost" time )
			node.setEndTime(node.getCost()+maxProcessorTime);
		}
	}

	/**
	 * Checks if a node has parents in a processor and returns true or false
	 * @param graph  is the original dependency graph, NOT the state space. In our
	 *              case, it stores HashMap<String, Node> - name of the node, to the Node
	 *              itself.
	 * @param node this is the current node we’re checking the parent for
	 * @return returns whether the node has a parent in a processor
	*/

	public static boolean checkListContainsParent(HashMap<String, Node> graph, Node node){
		ArrayList<String> completedNodes = new ArrayList<String>();
		// populate the completed nodes list according to whether it is set as completed
		for(Node n: graph.values()) {
			if(n.getProcessor() == node.getProcessor() && n.getCompleted()) {
				completedNodes.add(n.getID());
			}
		}
		// get all the parents of that node and check if they are present in the processor
		for(Node n : node.getParents().keySet()){
			if(n.getProcessor() != node.getProcessor()){
				return true;
			}
			if(!(completedNodes.contains(n.getID()))){
				return true;
			}
		}
		return false;
	}

	/**
	* This method returns the max time for a processor
	* @param graph This is the graph containing all the nodes in the network
	* @param processor This is the processor we are obtaining the max time for
	* @return This returns the mac time for that particular processor
	*/
	public static double calculateMaxCurrentProcessorTime(HashMap<String, Node> graph, int processor){
		double max = 0.0;
		// go through the graph and then obtain the maximum time in the schedule for that particular processor
		for(Node n : graph.values()){
			// check if the node exists in the processor we are trying to get the max time for
			if(n.getProcessor() == processor) {
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
	* @param node This is the node we are trying to get the communication cost for
	* @param parent This is the parent node
	* @return This returns the cost between the node and its parent. If the link doesn't exist, this returns 0.0
	*/
	public static double calculateCommunicationCost(Node node, Node parent){
		// get children list of parent
		for(Node n1: parent.getChildren().keySet()){
			// child node in the list
			if(n1.equals(node)){
				//get link cost (value) of parent with the node
				return parent.getChildren().get(n1);
			}
		}
		return 0.0;
	}

	/**
	* This method gets the communication cost between this node and its parents and then adds it to the
	* task processing cost of the node and then returns this total cost
	* @param node This is the node we are trying to get the maximum communication cost for
	* @return This returns the cost which includes the communication cost and the task cost
	*/
	public static double getMaxCommunicationCost(Node n){
		double ccost=0.0;
		//get parent list of a node, where parent belongs to a different processor
		for(Node n1:n.getParents().keySet()){
			if(n1.getProcessor() != n.getProcessor()) {
				// get cost of the current task that needs to be processed after its parent
				// in the other processor i.e communication cost + task cost
				if (calculateCommunicationCost(n,n1)+n1.getEndTime() > ccost) {
					ccost = calculateCommunicationCost(n,n1)+n1.getEndTime();
				}
			}
		}

		return ccost;
	}


}
