import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IterativeDFS {

	public static void main(String[] args) throws IOException {
		HashMap<String,Node> nodeMap = new HashMap<String,Node>();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String lineToRead = "";
		System.out.println("hello");
		// get rid of the first line in the input
		bfr.readLine();
		// if we havent reached the end of the input which ends with "}"
		while(!lineToRead.equals("}")){
			// get the line
			lineToRead = bfr.readLine();
			// remove all the spaces
			lineToRead=lineToRead.replaceAll("\\s", "");
			int i = 0;
			// when there is an arrow in the line
			if(lineToRead.contains("->")){
				// getting position of the left-side of arrow to get the parent name of node
				int IndexOfArrowHead = lineToRead.indexOf("-");
				// getting position of the point of arrow to appropriately get the child name of node
				int IndexOfArrowEnd = lineToRead.indexOf(">");
				// determining the end position of the child node's name
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfParentNode = lineToRead.substring(0, IndexOfArrowHead);
				String nameOfChildrenNode = lineToRead.substring(IndexOfArrowEnd+1, IndexOfWeight);
				// determining position of when the weight begins
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2));
				Node parentNode = nodeMap.get(nameOfParentNode);
				Node childNode = nodeMap.get(nameOfChildrenNode);
				nodeMap.get(nameOfParentNode).addChild(childNode, weight);
				nodeMap.get(nameOfChildrenNode).addParent(parentNode, weight);
				// when there is no arrow in the line
			} else if(!lineToRead.equals("}")){
				int IndexOfWeight = lineToRead.indexOf("[");
				// everything before the "]" is the name of the node
				String nameOfNode = lineToRead.substring(0, IndexOfWeight);
				int indexOfStartCost = lineToRead.indexOf("=");
				// everything after the equals is the weight, - 2 to get rid of the " ] ;"
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2));
				// hashmap which has for node a eg: (a, node a) as the input
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight));
			}
		}
		for(int i =  1; i<3; i++){
			for(String s:nodeMap.keySet()){
				if(nodeMap.get(s).getParents().keySet().isEmpty()){
					Node test = new Node(s, nodeMap.get(s).getCost());
					test.addStateParents(test);
					test.setProcessor(i);
					DFS(nodeMap, test);
				}
			}
		}
	}

	public static void DFS(HashMap<String, Node> graph, Node initialNode){
		Stack<Node> s = new Stack<Node>();
		s.push(initialNode);
		while(!s.isEmpty()){
			Node node = s.pop();
			expandStateSpace(graph, node);
			System.out.println("ID: "+node.getID()+" Processor: "+node.getProcessor());
			if(!node.getCompleted()){
				node.setCompleted(true);
				for(Node n:node.getChildren().keySet()){
					s.push(n);
				}
			}
		}
	}

	/**
	 * This method expands the current state space for the current node.
	 * For example, if A is the node, and the nodes that are reachable are
	 * B and C, it will instantiate a new node and register B and C as a 
	 * child to A.
	 * 
	 * @param graph
	 * @param currentNode
	 */

	public static void expandStateSpace(HashMap<String, Node> graph, Node currentNode){
		ArrayList<String> reachableNodes = new ArrayList<String>();
		// This returns an ArrayList of all the node names that are currently
		// reachable from where the current node.
		// Reachable is whatever node can be processed.
		reachableNodes = getReachable(graph, currentNode);
		int processor = 2;
		for(int i = 1; i<=processor; i++){
			for(String s:reachableNodes){
				// Task cost of the current node.
				int cost = graph.get(s).getCost();
				// Instantiate a new node to generate the state space.
				Node newChildNode = new Node(s, cost);
				newChildNode.setProcessor(i);
				// Stores the previous state parents of the node into the current
				// node to keep track of what nodes have been processed.
				for(Node n: currentNode.getStateParents()){
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

	public static ArrayList<String> getReachable(HashMap<String, Node> graph, Node currentNode){
		ArrayList<Node> completedNodes = currentNode.getStateParents();
		ArrayList<String> reachableNodeNames = new ArrayList<String>();
		//System.out.println("Current Node: "+currentNode.getID());
		for(Node n:completedNodes){
			graph.get(n.getID()).setCompleted(true);
		}
		for(String s:graph.keySet()){
			boolean dependenciesDone = true;
			for(Node n:graph.get(s).getParents().keySet()){
				if(!n.getCompleted()){
					dependenciesDone = false;
					break;
				}
			}
			if(dependenciesDone && !graph.get(s).getCompleted()){
				reachableNodeNames.add(s);
			}
		}
		for(Node n:completedNodes){
			graph.get(n.getID()).setCompleted(false);
		}
		return reachableNodeNames;
	}

	/**@Param g - is the original dependency graph, NOT the state space. In our case, it stores HashMap<String, Node> - name of the node, to the Node itself.

	 @Param node - this is the current node we’re calculating time for.

	 @Param list - stores the name of the processor, ie processor1 and the nodes on that processor.
	 */
	public static void calculateTime(HashMap<String, Node> g, Node node, HashMap<String, Node> list){

	}

}


