import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RecursiveDFS {

	static double currentBestSolution = Double.POSITIVE_INFINITY;

	public static void main(String[] args) throws IOException {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();

		InputParser parser = new InputParser();
		parser.parseInput(nodeMap);
		double bestSolution = Double.POSITIVE_INFINITY;
		/*System.out.println("Hello: "+nodeName);
				StateSpaceNode test = new StateSpaceNode(nodeName, nodeMap.get(nodeName).getCost(), 2);
				test.addStateParents(test);
				test.setProcessor(2);*/
		for(int i = 1; i<=2; i++) {
			for(Node n:nodeMap.values()) {
				if(n.getParents().isEmpty()) {
					n.setProcessor(i);
					recursiveDFS(nodeMap, n.getID());
					System.out.println(currentBestSolution);
				}
			}
		}

		System.out.println(currentBestSolution);
	}
	//StateSpaceNode node = DFS(nodeMap, test);
	/*
				double nodeTime = 0;
				for(StateSpaceNode n: node.getStateParents()){
					if(nodeTime < n.getEndTime()){
						nodeTime = n.getEndTime();
					}
				}
				System.out.println("End Time: "+nodeTime);
				//System.out.println("Node: "+node.getEndTime()+ " Size: "+node.getStateParents().size());
				if(node.getEndTime() < bestSolution) {
					bestSolution = node.getEndTime();
					bestNode = node;
				}
			}
		}
		for(StateSpaceNode n:bestNode.getStateParents()) {
			System.out.println("ID: "+n.getID() + " Processor: "+n.getProcessor() + " Start Time: "+n.getStartTime() + " End Time: "+n.getEndTime());
		}*/

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 * @param nodeMap
	 * @param initialNode
	 */
	/*public static StateSpaceNode DFS(HashMap<String, DependencyNode> nodeMap, StateSpaceNode initialNode) {
		StateSpaceNode bestNode = initialNode;
		//HashMap<Integer, ArrayList<Node>> processorList = new HashMap<Integer, ArrayList<Node>>();
		Stack<StateSpaceNode> s = new Stack<StateSpaceNode>();
		s.push(initialNode);
		double bestSolution = Double.POSITIVE_INFINITY;
		while (!s.isEmpty()) {
			StateSpaceNode node = s.pop();
			int perfectLoadBalance = 0;
			for(int i = 1; i<=2; i++){
				perfectLoadBalance += calculateMaxCurrentProcessorTime(node.getStateParents(), i);
			}
			double idealProcessorTime = (perfectLoadBalance + calculateEstimatedCost(node, nodeMap))/2;
			if(bestSolution >= idealProcessorTime){
				//System.out.println("Estimated cost: "+ ( calculateEstimatedCost(node, graph)));
				expandStateSpace(nodeMap, node);
				//processorList.get(node.getProcessor()).add(node);
				calculateTime(nodeMap, node);
				//System.out.println("ID: " + node.getID() + " Processor: " + node.getProcessor());
				if (!node.getCompleted()) {
					node.setCompleted(true);
					for (Node n : node.getChildren().keySet()) {
						s.push((StateSpaceNode)n);
					}
				}
				if(node.getStateParents().size() == nodeMap.values().size()) {
					double maxTime = 0;
					for(StateSpaceNode n: node.getStateParents()){
						if(maxTime < n.getEndTime()){
							maxTime = n.getEndTime();
						}
					}
					if(maxTime < bestSolution) {
						bestSolution = maxTime;
						bestNode = node;
					}
					//System.out.println("Calculated Time: "+maxTime);
					//System.out.println("Current Solution: "+bestSolution);
				}
			} else {
				node = null;
			}
		}
		return bestNode;
	}*/

	public static void recursiveDFS(HashMap<String, Node> graph, String nodeName) {
		graph.get(nodeName).setCompleted(true);
		ArrayList<String> test = getReachable(graph);
		calculateTime(graph, graph.get(nodeName));
		/*System.out.println("Node name: "+graph.get(nodeName).getID() + " Processor: "+graph.get(nodeName).getProcessor());
		System.out.println("Start Time: "+graph.get(nodeName).getStartTime());
		System.out.println("End Time: "+graph.get(nodeName).getEndTime());*/
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
				//System.out.println(graph.get(nodeName).getEndTime());
				if(max < currentBestSolution) {
					currentBestSolution = max;
				}
			}
		}
	}

	/*public static void DFS(HashMap<String, Node> graph, String initialNode, int processor) {
		System.out.println(initialNode+" Processor "+processor);
		graph.get(initialNode).setCompleted(true);
		for(int i = 1; i<3; i++){
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
					DFS(graph, nodes, i);
					graph.get(nodes).setCompleted(false);
				}
			}
		}
	}

	/*public static double calculateEstimatedCost(Node n, HashMap<String, Node> graph){
		double cost = 0;
		for(Node node: n.getStateParents()){
			graph.get(node.getID()).setCompleted(true);
		}
		for(Node node: graph.values()){
			if(!node.getCompleted()){
				cost += node.getCost();
			}
		}
		for(Node node: n.getStateParents()){
			graph.get(node.getID()).setCompleted(false);
		}
		return cost;
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
		double maxCost = 0.0, maxProcessorTime = 0.0, longestCommunicationCost = 0.0;
		//check if it has a parent
		maxProcessorTime = calculateMaxCurrentProcessorTime(graph, node.getProcessor());
		//System.out.println("Hello: "+maxProcessorTime);
		if(node.getParents().size() != 0 ){
			//if all the parents are on the same processor
			if (checkListContainsParent(graph, node)){
				//System.out.println("Hello do you?");
				// Calculating the node that has the max end time of the processor in use
				maxCost = getMaxCommunicationCost(node, graph);
				//System.out.println("Max Cost: "+maxCost);
				longestCommunicationCost = getLongestCommunicationCost(node, graph);
				if(maxProcessorTime == maxCost) {
					node.setStartTime(maxCost);
					node.setEndTime(maxCost+node.getCost());
				}
				else if (maxProcessorTime < maxCost) {
					node.setStartTime(maxCost);
					node.setEndTime(maxCost + node.getCost());
				} else {
					node.setStartTime(maxProcessorTime);
					node.setEndTime(node.getCost()+maxProcessorTime);
				}
			} else{
				node.setStartTime(maxProcessorTime);
				node.setEndTime(node.getCost()+maxProcessorTime);
			}
		} else{
			node.setStartTime(maxProcessorTime);
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

	public static double calculateCommunicationCost(Node node, Node parent, HashMap<String, Node> graph){
		//System.out.println("Hello?");
		for(Node n1: parent.getChildren().keySet()){
			if(n1.equals(node)){
				return parent.getChildren().get(n1);
			}
		}
		return 0.0;
	}

	public static double getMaxCommunicationCost(Node n, HashMap<String, Node> graph){
		double ccost=0.0;
		for(Node n1:n.getParents().keySet()){
			if(n1.getProcessor() != n.getProcessor()) {
				if (calculateCommunicationCost(n,n1, graph)+n1.getEndTime() > ccost) {
					ccost = calculateCommunicationCost(n,n1, graph)+n1.getEndTime();
				}
			}
			//}
		}

		return ccost;
	}

	public static double getLongestCommunicationCost(Node node, HashMap<String, Node> graph){
		double ccost=0.0;
		//System.out.println("The current node is: "+n.getID());
		for(Node parent:node.getParents().keySet()){
			if (calculateCommunicationCost(node,parent, graph) > ccost) {
				ccost = calculateCommunicationCost(node,parent, graph);
			}
		}

		//System.out.println("Longest Communication: "+ccost);
		return ccost;
	}

}
