import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IterativeDFS {

	public static void main(String[] args) throws IOException {
		HashMap<String, DependencyNode> nodeMap = new HashMap<String, DependencyNode>();

		InputParser parser = new InputParser();
		parser.parseInput(nodeMap);
		StateSpaceNode bestNode = new StateSpaceNode("test", 0, 0);
		double bestSolution = Double.POSITIVE_INFINITY;
		for (String nodeName : nodeMap.keySet()) {
			HashMap<DependencyNode, Double> parents = nodeMap.get(nodeName).getParents();
			if (parents.keySet().isEmpty()) {
				System.out.println("Hello: "+nodeName);
				StateSpaceNode test = new StateSpaceNode(nodeName, nodeMap.get(nodeName).getCost(), 2);
				test.addStateParents(test);
				test.setProcessor(2);
				StateSpaceNode node = DFS(nodeMap, test);
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
		}

	}

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 * @param nodeMap
	 * @param initialNode
	 */
	public static StateSpaceNode DFS(HashMap<String, DependencyNode> nodeMap, StateSpaceNode initialNode) {
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
	}

	public static double calculateEstimatedCost(StateSpaceNode n, HashMap<String, DependencyNode> graph){
		double cost = 0;
		for(StateSpaceNode node: n.getStateParents()){
			graph.get(node.getID()).setCompleted(true);
		}
		for(DependencyNode node: graph.values()){
			if(!node.getCompleted()){
				cost += node.getCost();
			}
		}
		for(StateSpaceNode node: n.getStateParents()){
			graph.get(node.getID()).setCompleted(false);
		}
		return cost;
	}

	/**
	 * This method expands the current state space for the current node. For
	 * example, if A is the node, and the nodes that are reachable are B and C, it
	 * will instantiate a new node and register B and C as a child to A.
	 * 
	 * @param graph
	 * @param currentNode
	 */

	public static void expandStateSpace(HashMap<String, DependencyNode> graph, StateSpaceNode currentNode) {
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
				StateSpaceNode newChildNode = new StateSpaceNode(s, cost, i);
				newChildNode.setProcessor(i);
				// Stores the previous state parents of the node into the current
				// node to keep track of what nodes have been processed.
				for (StateSpaceNode n : currentNode.getStateParents()) {
					newChildNode.addStateParents(n);
				}
				// Register itself into the state parents.
				newChildNode.addStateParents(newChildNode);
				// Still need to allocate cost when stored.
				currentNode.addChild(newChildNode, 0);
			}
		}
	}

	/**
	 * Get a list of reachable Nodes from the supplied Node in a given graph.
	 * @param graph
	 * @param currentNode
	 * @return
	 */
	public static ArrayList<String> getReachable(HashMap<String, DependencyNode> graph, StateSpaceNode currentNode) {
		ArrayList<StateSpaceNode> completedNodes = currentNode.getStateParents();
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		// System.out.println("Current Node: "+currentNode.getID());
		for (StateSpaceNode n : completedNodes) {
			graph.get(n.getID()).setCompleted(true);
		}
		for (String s : graph.keySet()) {
			boolean dependenciesDone = true;
			for (DependencyNode n : graph.get(s).getParents().keySet()) {
				if (!n.getCompleted()) {
					dependenciesDone = false;
					break;
				}
			}
			if (dependenciesDone && !graph.get(s).getCompleted()) {
				reachableNodeNames.add(s);
			}
		}
		for (StateSpaceNode n : completedNodes) {
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
	public static void calculateTime(HashMap<String, DependencyNode> graph, StateSpaceNode node){
		double maxCost = 0.0, maxProcessorTime = 0.0, longestCommunicationCost = 0.0;
		//check if it has a parent
		ArrayList<StateSpaceNode> parentsInStateTree = node.getStateParents();
		DependencyNode nodeOnDependGraph = graph.get(node.getID());
		maxProcessorTime = calculateMaxCurrentProcessorTime(parentsInStateTree, node.getProcessor());
		if(nodeOnDependGraph.getParents().size() != 0 ){
			//if all the parents are on the same processor
			if (checkListContainsParent(parentsInStateTree,nodeOnDependGraph.getParents(), node.getProcessor())){
				// Calculating the node that has the max end time of the processor in use
				maxCost = getMaxCommunicationCost(node, graph, parentsInStateTree, node.getProcessor());
				longestCommunicationCost = getLongestCommunicationCost(node, graph, parentsInStateTree, node.getProcessor());
				if(maxProcessorTime == maxCost) {
					node.setStartTime(maxCost+longestCommunicationCost);
					node.setEndTime(maxCost+longestCommunicationCost+node.getCost());
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


	public static boolean checkListContainsParent(ArrayList<StateSpaceNode> parentsInStateTree, HashMap<DependencyNode, Double> parents, int processor){
		/*ArrayList<String> completedNodes = new ArrayList<String>();
		for(StateSpaceNode n: parentsInStateTree) {
			if(n.getProcessor() == processor) {
				completedNodes.add(n.getID());
			}
		}*/
		for(Node n : parents.keySet()){
			for(StateSpaceNode parentStateNode: parentsInStateTree) {
				if(n.getID() == parentStateNode.getID()){
					if(parentStateNode.getProcessor() != processor){
						return true;
					}
				}
				/*if(!(completedNodes.contains(n.getID()))){
					return true;
				}*/
			}
		}
		return false;
	}


	public static double calculateMaxCurrentProcessorTime(ArrayList<StateSpaceNode> parentsInStateTree, int processor){
		double max = 0.0;
		for(StateSpaceNode n : parentsInStateTree){
			if(n.getProcessor() == processor) {
				if (n.getEndTime() > max) {
					max = n.getEndTime();
				}
			}
		}
		return max;
	}

	public static double calculateCommunicationCost(Node node, Node parent, HashMap<String, DependencyNode> graph){
		//System.out.println("Hello?");
		for(Node n1: parent.getChildren().keySet()){
			if(n1.equals(node)){
				return parent.getChildren().get(n1);
			}
		}
		return 0.0;
	}

	public static double getMaxCommunicationCost(Node n, HashMap<String, DependencyNode> graph, ArrayList<StateSpaceNode> parentsInStateTree, int processor){
		double ccost=0.0;
		/*ArrayList<StateSpaceNode> listOfNodes = new ArrayList<StateSpaceNode>();
		for(StateSpaceNode node : parentsInStateTree) {
			if(node.getProcessor() != processor) {
				listOfNodes.add(node);
			}
		}*/
		DependencyNode nodeDepenGraph = graph.get(n.getID());
		for(Node n2:nodeDepenGraph.getParents().keySet()){
			for(StateSpaceNode n1:parentsInStateTree){
				//for(StateSpaceNode n1 : listOfNodes) {
				if(n1.getID() == n2.getID() && n1.getProcessor() != processor) {
					if (calculateCommunicationCost(nodeDepenGraph,n2, graph)+n1.getEndTime() > ccost) {
						ccost = calculateCommunicationCost(nodeDepenGraph,n2, graph)+n1.getEndTime();
					}
				}
				//}
			}

		}
		return ccost;
	}

	public static double getLongestCommunicationCost(Node n, HashMap<String, DependencyNode> graph, ArrayList<StateSpaceNode> parentsInStateTree, int processor){
		double ccost=0.0;
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(StateSpaceNode node : parentsInStateTree) {
			if(node.getProcessor() != processor) {
				listOfNodes.add(node);
			}
		}
		DependencyNode nodeDepenGraph = graph.get(n.getID());
		//System.out.println("The current node is: "+n.getID());
		for(Node n2:nodeDepenGraph.getParents().keySet()){
			for(Node n1 : listOfNodes) {
				if(n1.getID() == n2.getID()) {
					if (calculateCommunicationCost(nodeDepenGraph,n2, graph) > ccost) {
						ccost = calculateCommunicationCost(nodeDepenGraph,n2, graph);
					}
				}
			}

		}
		//System.out.println("Longest Communication: "+ccost);
		return ccost;
	}

}
