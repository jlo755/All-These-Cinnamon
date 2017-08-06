import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Dijkstra {

	public static void main(String[] args) throws IOException {
		HashMap<String,Node> nodeMap = new HashMap<String,Node>();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String lineToRead = "";
		System.out.println("hello");
		bfr.readLine(); // get rid of the first line in the input
		while(!lineToRead.equals("}")){ // if we havent reached the end of the input which ends with "}"
			lineToRead = bfr.readLine(); // get the line
			lineToRead=lineToRead.replaceAll("\\s", ""); // remove all the spaces
			int i = 0;
			int indexOfWeight = lineToRead.indexOf("[");
			if(lineToRead.contains("->")){ // when there is an arrow in the line
				int IndexOfArrowHead = lineToRead.indexOf("-"); // getting position of the left-side of arrow to get the parent name of node
				int IndexOfArrowEnd = lineToRead.indexOf(">"); // getting position of the point of arrow to appropriately get the child name of node
				int IndexOfWeight = lineToRead.indexOf("["); // determining the end position of the child node's name
				String nameOfParentNode = lineToRead.substring(0, IndexOfArrowHead);
				String nameOfChildrenNode = lineToRead.substring(IndexOfArrowEnd+1, IndexOfWeight);
				int indexOfStartCost = lineToRead.indexOf("="); // determining position of when the weight begins
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2));
				nodeMap.get(nameOfParentNode).addChild(nameOfChildrenNode, weight);
				nodeMap.get(nameOfChildrenNode).addParent(nameOfParentNode, weight);
			} else if(!lineToRead.equals("}")){ // when there is no arrow in the line
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfNode = lineToRead.substring(0, IndexOfWeight); // everything before the "]" is the name of the node
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2)); // everything after the equals is the weight, - 2 to get rid of the " ] ;"
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight)); // hashmap which has for node a eg: (a, node a) as the input
			}
		}
		for(String nodeName:nodeMap.keySet()) {
			Dijkstra(nodeMap, nodeName);
			Node toTest = nodeMap.get(nodeName);
			System.out.println(toTest.getID());
			for(String n : toTest.getDistances().keySet()){
				System.out.println(n +" "+ toTest.getDistances().get(n));
			}
		}
	}

	public static void Dijkstra(HashMap<String,Node> graph, String startingNodeName){ // string s is the node you are starting off with
		HashMap<String, Boolean> nodesProcessed = new HashMap<String, Boolean>();
		for(String nodeName:graph.keySet()){
			Node initialNode = graph.get(startingNodeName);
			if(initialNode.getChildren().containsKey(nodeName)){ // starting to build the distance vector for the node
				initialNode.updateDistance(nodeName, initialNode.getChildren().get(nodeName)+initialNode.getCost()+graph.get(nodeName).getCost()); // adding the costs of all the nodes in the network.
				// for the nodes that are reachable, put in the weight + computation cost +the computation cost of node being reached.
				nodesProcessed.put(nodeName, false); // if this is not the current node we are on. we are not processing this right now
			} else if(nodeName.equals(startingNodeName)){ // if the node is going to itself, put in the computation cost of that node itself
				nodesProcessed.put(nodeName, true);
				initialNode.updateDistance(nodeName, initialNode.getCost());
			} else {
				initialNode.updateDistance(nodeName, Double.POSITIVE_INFINITY); // adding infinity to the network if the node cant be reached from the current node being processed
				nodesProcessed.put(nodeName, false);
			}
		}
		System.out.println("Current Node: "+startingNodeName);
		for(String nodeName:graph.get(startingNodeName).getDistances().keySet()) {
			//System.out.println("Name: "+nodeName);
			//System.out.println("Cost: "+graph.get(startingNodeName).getDistances().get(nodeName));
		}
		String nodetoProcessed = startingNodeName; // current node we are on
		while(nodesProcessed.containsValue(false)){ // while we still have nodes to process, we are going to process them
			Double min = Double.POSITIVE_INFINITY;
			for(String child:graph.get(startingNodeName).getDistances().keySet()){ // for all the children in the distance vector, 
				//System.out.println("Current Node: "+nodetoProcessed+" Node:"+child);
				//System.out.println("Cost: "+graph.get(startingNodeName).getDistances().get(child));
				if(graph.get(startingNodeName).getDistances().get(child) <= min && !nodesProcessed.get(child)){ // s is the current node, get distance gets the distance vector (hashmap) and then select a child and get(child) returns the integer cost for reaching that child (the link cost)
					// <= so we take care of any isolated nodes.
					// the not part is to make sure that we have not previously processed this node
					min = graph.get(startingNodeName).getDistances().get(child); // for the current node, get the distance vector, and then get the child which has the minimum distance and store this as the minimum
					nodetoProcessed = child; 
				}
			}

			nodesProcessed.put(nodetoProcessed, true); // when a node is set to true, it means we are processing that node
			Node nodeToProcess = graph.get(nodetoProcessed);
			HashMap<String, Double> nodeToProcessChildrenDistances = nodeToProcess.getChildren();
			double costToProcessNode = graph.get(startingNodeName).getDistances().get(nodetoProcessed); // the cost associated with getting from the starting node to the current processing node
			for(String child: graph.get(nodetoProcessed).getChildren().keySet()){ // only need to process the children
				// create a new map for comparing , this will be the distance vector of node a
				double childsDistance = nodeToProcessChildrenDistances.get(child); // this is the communication cost from the current processing node to its child
				int childTaskCost = graph.get(child).getCost(); // cost associated with processing the current child node
				double currentCostToChild = graph.get(startingNodeName).getDistances().get(child); // current cost from the starting node to the intended child cost
				double totalCost = childsDistance+childTaskCost+costToProcessNode;
				if( totalCost < currentCostToChild){ // go through the distance vector of node a and then update according to the nodes which its children can reach. If this is less than what is previously stored, you update the cost in the distance vector
					graph.get(startingNodeName).getDistances().put(child, totalCost); // JACKY: add the distance of the node
				}
			}
		}
	}
}


class Node {
	private HashMap<String, Double> _parents; // this is the parents hashmap with the parent node and their communication cost
	private HashMap<String, Double>_children; // this is the children hashmap with the child node and their comunication cost
	private String _id; // name of the node
	private int _cost; // cost of the node itself
	private HashMap<String, Double> _distances;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_parents = new HashMap<String, Double>();
		_children = new HashMap<String, Double>();
		_distances = new HashMap<String, Double>(); // storing the distances in a matrix to all the other nodes in the network (infinitiy and actual reachable costs) 
	}

	protected void addParent(String node, double cost){
		_parents.put(node, cost);
	}

	protected void addChild(String node, double cost){
		_children.put(node, cost);
	}

	protected String getID(){
		return _id;
	}

	protected void updateDistance(String n, double i){
		_distances.put(n, i);
	}

	protected HashMap<String, Double> getDistances(){
		return _distances;
	}

	protected int getCost(){
		return _cost;
	}

	protected HashMap<String, Double> getParents(){
		return _parents;
	}

	protected HashMap<String, Double> getChildren(){
		return _children;
	}
}
