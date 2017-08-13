import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RecursiveDFS {

	static double currentBestSolution = Double.POSITIVE_INFINITY;
	static FinalState bestState;

	public static void main(String[] args) throws IOException {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();

		InputParser parser = new InputParser();
		parser.parseInput(nodeMap);
		bestState = new FinalState(nodeMap.keySet());
		for(int i = 1; i<=1; i++) {
			for(Node n:nodeMap.values()) {
				if(n.getParents().isEmpty()) {
					n.setProcessor(i);
					long startTime = System.nanoTime();
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
	 * @param nodeMap
	 * @param initialNode
	 */

	public static void recursiveDFS(HashMap<String, Node> graph, String nodeName) {
		graph.get(nodeName).setCompleted(true);
		ArrayList<String> test = getReachable(graph);
		calculateTime(graph, graph.get(nodeName));
		for(int i = 1; i<=2; i++) {
			for(String node1:test) {
				graph.get(node1).setProcessor(i);
				recursiveDFS(graph, node1);
				graph.get(node1).setStartTime(0.0);
				graph.get(node1).setEndTime(0.0);
				graph.get(node1).setCompleted(false);
			}
			if(test.isEmpty()) {
				double max = 0;
				for(Node n:graph.values()){
					if(n.getEndTime() > max) {
						max = n.getEndTime();
					}
				}
				if(max < currentBestSolution) {
					currentBestSolution = max;
					bestState.setCurrentBestState(graph);
				}
			}
		}
	}


	/**
	 * Get a list of reachable Nodes from the supplied Node in a given graph.
	 * @param graph
	 * @param currentNode
	 * @return
	 */
	public static ArrayList<String> getReachable(HashMap<String, Node> graph) {
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		for(String nodes : graph.keySet()){
			if(graph.get(nodes).getCompleted()){
				continue;
			}
			boolean dependenciesDone = true;
			for(Node parentNode:graph.get(nodes).getParents().keySet()){
				if(!parentNode.getCompleted()){
					dependenciesDone = false;
					break;
				}
			}
			if(dependenciesDone){
				reachableNodeNames.add(nodes);
			}
		}
		return reachableNodeNames;
	}

	/**
	 * @Param g - is the original dependency graph, NOT the state space. In our
	 *        case, it stores HashMap<String, Node> - name of the node, to the Node
	 *        itself.
	 * 
	 * @Param node - this is the current node we’re calculating time for.
	 * 
	 * @Param list - stores the name of the processor, ie processor1 and the nodes
	 *        on that processor.
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


	public static boolean checkListContainsParent(HashMap<String, Node> graph, Node node){
		ArrayList<String> completedNodes = new ArrayList<String>();
		for(Node n: graph.values()) {
			if(n.getProcessor() == node.getProcessor() && n.getCompleted()) {
				completedNodes.add(n.getID());
			}
		}
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

//gets last node in the same processor
	public static double calculateMaxCurrentProcessorTime(HashMap<String, Node> graph, int processor){
		double max = 0.0;
		for(Node n : graph.values()){
			if(n.getProcessor() == processor) {
				if (n.getEndTime() > max) {
					max = n.getEndTime();
				}
			}
		}
		return max;
	}


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
