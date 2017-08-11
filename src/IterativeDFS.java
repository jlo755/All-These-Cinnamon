import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IterativeDFS {

	public static void main(String[] args) throws IOException {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();

		InputParser parser = new InputParser();
		parser.parseInput(nodeMap);

		for (int i = 1; i < 3; i++) {
			for (String nodeName : nodeMap.keySet()) {
				HashMap<Node, Double> parents = nodeMap.get(nodeName).getParents();
				if (parents.keySet().isEmpty()) {
					Node test = new Node(nodeName, nodeMap.get(nodeName).getCost());
					test.addStateParents(test);
					test.setProcessor(i);
					DFS(nodeMap, test);
				}
			}
		}

	}

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 * @param graph
	 * @param initialNode
	 */
	public static void DFS(HashMap<String, Node> graph, Node initialNode) {
		//HashMap<Integer, ArrayList<Node>> processorList = new HashMap<Integer, ArrayList<Node>>();
		Stack<Node> s = new Stack<Node>();
		s.push(initialNode);
		while (!s.isEmpty()) {
			Node node = s.pop();
			expandStateSpace(graph, node);
			//processorList.get(node.getProcessor()).add(node);
			calculateTime(graph, node);
			//System.out.println("ID: " + node.getID() + " Processor: " + node.getProcessor());
			if (!node.getCompleted()) {
				node.setCompleted(true);
				for (Node n : node.getChildren().keySet()) {
					s.push(n);
				}
			}
			if(node.getStateParents().size() == graph.values().size()) {
				for(Node n : node.getStateParents()) {
					System.out.println("Node ID: "+n.getID() + " Processor: "+n.getProcessor());
					if(n.getID().equals("d")) {
						System.out.println(node.getEndTime());
					}
				}
			}
		}
	}

	/**
	 * This method expands the current state space for the current node. For
	 * example, if A is the node, and the nodes that are reachable are B and C, it
	 * will instantiate a new node and register B and C as a child to A.
	 * 
	 * @param graph
	 * @param currentNode
	 */

	public static void expandStateSpace(HashMap<String, Node> graph, Node currentNode) {
		ArrayList<String> reachableNodes = new ArrayList<String>();
		// This returns an ArrayList of all the node names that are currently
		// reachable from where the current node.
		// Reachable is whatever node can be processed.
		reachableNodes = getReachable(graph, currentNode);
		int processor = 2;
		for (int i = 1; i <= processor; i++) {
			for (String s : reachableNodes) {
				// Task cost of the current node.
				int cost = graph.get(s).getCost();
				// Instantiate a new node to generate the state space.
				Node newChildNode = new Node(s, cost);
				newChildNode.setProcessor(i);
				// Stores the previous state parents of the node into the current
				// node to keep track of what nodes have been processed.
				for (Node n : currentNode.getStateParents()) {
					newChildNode.addStateParents(n);
				}
				// Register itself into the state parents.
				newChildNode.addStateParents(newChildNode);
				// Still need to allocate cost when stored.
				currentNode.addChild(newChildNode, 0);
				newChildNode.addParent(currentNode, 0);
			}
		}
	}

	/**
	 * Get a list of reachable Nodes from the supplied Node in a given graph.
	 * @param graph
	 * @param currentNode
	 * @return
	 */
	public static ArrayList<String> getReachable(HashMap<String, Node> graph, Node currentNode) {
		ArrayList<Node> completedNodes = currentNode.getStateParents();
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		// System.out.println("Current Node: "+currentNode.getID());
		for (Node n : completedNodes) {
			graph.get(n.getID()).setCompleted(true);
		}
		for (String s : graph.keySet()) {
			boolean dependenciesDone = true;
			for (Node n : graph.get(s).getParents().keySet()) {
				if (!n.getCompleted()) {
					dependenciesDone = false;
					break;
				}
			}
			if (dependenciesDone && !graph.get(s).getCompleted()) {
				reachableNodeNames.add(s);
			}
		}
		for (Node n : completedNodes) {
			graph.get(n.getID()).setCompleted(false);
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
	 */
	public static void calculateTime(HashMap<String, Node> graph, Node node){
		Double maxCost = 0.0, maxProcessorTime = 0.0;
		//check if it has a parent
		ArrayList<Node> parentsInStateTree = node.getStateParents();
		Node nodeOnDependGraph = graph.get(node.getID());
		if(nodeOnDependGraph.getParents().size() != 0 ){
			//if all the parents are on the same processor
			if (checkListContainsParent(parentsInStateTree,nodeOnDependGraph.getParents(), node.getProcessor())){
				// Calculating the node that has the max end time of the processor in use
				maxProcessorTime = calculateMaxCurrentProcessorTime(parentsInStateTree, node.getProcessor());
				maxCost = getMaxCommunicationCost(node, graph, parentsInStateTree, node.getProcessor());
				if (maxProcessorTime < maxCost) {
					node.setStartTime(maxCost);
					node.setEndTime(maxCost + node.getCost());
				} else {
					node.setStartTime(maxProcessorTime);
					node.setEndTime(node.getCost()+maxProcessorTime);
				}
			} else{
				maxProcessorTime = calculateMaxCurrentProcessorTime(parentsInStateTree, node.getProcessor());
				node.setStartTime(maxProcessorTime);
				node.setEndTime(node.getCost()+maxProcessorTime);
			}
		} else{
			maxProcessorTime = calculateMaxCurrentProcessorTime(parentsInStateTree, node.getProcessor());
			node.setStartTime(maxProcessorTime);
			node.setEndTime(node.getCost()+maxProcessorTime);
		}
		/*if(node.getID().equals("d")) {
			for(Node n: node.getStateParents()) {
				System.out.println("ID: "+n.getID() + " Processor: "+n.getProcessor());
				System.out.println("Start Time: "+n.getStartTime() + " End Time: "+n.getEndTime());
			}
			System.out.println(maxProcessorTime);
		}*/
	}


	public static boolean checkListContainsParent(ArrayList<Node> parentsInStateTree, HashMap<Node, Double> parents, int processor){
		ArrayList<String> completedNodes = new ArrayList<String>();
		for(Node n: parentsInStateTree) {
			if(n.getProcessor() == processor) {
				completedNodes.add(n.getID());
			}
		}
		for(Node n : parents.keySet()){
			if(!(completedNodes.contains(n.getID()))){
				return true;
			}
		}
		return false;
	}


	public static Double calculateMaxCurrentProcessorTime(ArrayList<Node> parentsInStateTree, int processor){
		Double max = 0.0;
		for(Node n : parentsInStateTree){
			if(n.getProcessor() == processor) {
				if (n.getEndTime() > max) {
					max = n.getEndTime();
				}
			}
		}
		return max;
	}

	public static Double calculateCommunicationCost(Node node, Node parent, HashMap<String, Node> graph){
		//System.out.println("Hello?");
		for(Node n1: parent.getChildren().keySet()){
			if(n1.equals(node)){
				return parent.getChildren().get(n1);
			}
		}
		return 0.0;
	}

	public static Double getMaxCommunicationCost(Node n, HashMap<String, Node> graph, ArrayList<Node> parentsInStateTree, int processor){
		Double ccost=0.0;
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(Node node : parentsInStateTree) {
			if(node.getProcessor() != processor) {
				listOfNodes.add(node);
			}
		}
		Node nodeDepenGraph = graph.get(n.getID());
		//System.out.println("The current node is: "+n.getID());
		for(Node n2:nodeDepenGraph.getParents().keySet()){
			for(Node n1 : listOfNodes) {
				if(n1.getID() == n2.getID()) {
					if (calculateCommunicationCost(nodeDepenGraph,n2, graph)+n1.getEndTime() > ccost) {
						ccost = calculateCommunicationCost(nodeDepenGraph,n2, graph)+n1.getEndTime();
					}
				}
			}

		}
		return ccost;
	}

}
