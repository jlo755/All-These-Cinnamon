import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Dijkstra {

	public static void main(String[] args) throws IOException {
		HashMap<String,Node> nodeMap = new HashMap<String,Node>();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String lineToRead = "";
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
				nodeMap.get(nameOfParentNode).addChild(nodeMap.get(nameOfChildrenNode), weight);
				nodeMap.get(nameOfChildrenNode).addParent(nodeMap.get(nameOfParentNode), weight);
			} else if(!lineToRead.equals("}")){ // when there is no arrow in the line
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfNode = lineToRead.substring(0, IndexOfWeight); // everything before the "]" is the name of the node
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2)); // everything after the equals is the weight, - 2 to get rid of the " ] ;"
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight)); // hashmap which has for node a eg: (a, node a) as the input
			}
		}
		Dijkstra(nodeMap, "a");
		/*for(String s:nodeMap.keySet()){
			System.out.println(nodeMap.get(s).getCost() + " " + nodeMap.get(s).getID());
		}*/
		Node toTest = nodeMap.get("a");
		for(Node n : toTest.getDistance().keySet()){
			System.out.println(n.getID() +" "+ toTest.getDistance().get(n));
		}
	}

	public static void Dijkstra(HashMap<String,Node> graph, String s){ // string s is the node you are starting off with
		HashMap<String, Boolean> color = new HashMap<String, Boolean>();
		for(String id:graph.keySet()){
			Node startingMap = graph.get(s);
			if(startingMap.getChildren().containsKey(graph.get(id))){ // starting to build the distance vector for the node
				startingMap.updateDistance(graph.get(id), startingMap.getChildren().get(graph.get(id))+startingMap.getCost()+graph.get(id).getCost()); // adding the costs of all the nodes in the network.
				// for the nodes that are reachable, put in the weight + computation cost +the computation cost of node being reached.
				color.put(id, false); // if this is not the current node we are on. we are not processing this right now
			} else if(id.equals(s)){ // if the node is going to itself, put in the computation cost of that node itself
				color.put(id, true);
				startingMap.updateDistance(graph.get(id), startingMap.getCost());
			} else {
				startingMap.updateDistance(graph.get(id), Integer.MAX_VALUE); // adding infinity to the network if the node cant be reached from the current node being processed
				color.put(id, false);
			}
		}
		String idToDo = s; // current node we are on
		while(color.containsValue(false)){ // while we still have nodes to process, we are going to process them
			int min = Integer.MAX_VALUE;
			for(Node child:graph.get(s).getDistance().keySet()){ // for all the children in the distance vector, 
				System.out.println("Current Node: "+idToDo+" Node:"+child.getID());
				System.out.println("Cost: "+graph.get(s).getDistance().get(child));
				if(graph.get(s).getDistance().get(child) <= min && !color.get(child.getID())){ // s is the current node, get distance gets the distance vector (hashmap) and then select a child and get(child) returns the integer cost for reaching that child (the link cost)
					// <= so we take care of any isolated nodes.
					// the not part is to make sure that we have not previously processed this node
					System.out.println("Yes");
					min = graph.get(s).getDistance().get(child); // for the current node, get the distance vector, and then get the child which has the minimum distance and store this as the minimum
					idToDo = child.getID(); 
				}
			}

			color.put(idToDo, true); // comparing distances
			for(Node child: graph.get(idToDo).getChildren().keySet()){ // only need to process the children
				HashMap<Node, Integer> mapToCompare = graph.get(s).getDistance(); // create a new map for comparing , this will be the distance vector of node a
				if(graph.get(idToDo).getChildren().get(child)+mapToCompare.get(graph.get(idToDo)+mapToCompare.get(child).getCost()) < mapToCompare.get(child)){ // go through the distance vactor of node a and then update according to the nodes which its children can reach. If this is less than what is previously stored, you update the cost in the distance vector
					mapToCompare.put(child, graph.get(idToDo).getChildren().get(child)+mapToCompare.get(graph.get(idToDo))+mapToCompare.get(child).getCost()); // JACKY: add the distance of the node
				}
			}
		}
	}
}


class Node {
	private HashMap<Node, Integer> _parents; // this is the parents hashmap with the parent node and their communication cost
	private HashMap<Node, Integer>_children; // this is the children hashmap with the child node and their comunication cost
	private String _id; // name of the node
	private int _cost; // cost of the node itself
	private HashMap<Node, Integer> _distance;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_parents = new HashMap<Node, Integer>();
		_children = new HashMap<Node, Integer>();
		_distance = new HashMap<Node, Integer>(); // storing the distances in a matrix to all the other nodes in the network (infinitiy and actual reachable costs) 
	}

	protected void addParent(Node node, int cost){
		_parents.put(node, cost);
	}

	protected void addChild(Node node, int cost){
		_children.put(node, cost);
	}

	protected String getID(){
		return _id;
	}

	protected void updateDistance(Node n, int i){
		_distance.put(n, i);
	}

	protected HashMap<Node, Integer> getDistance(){
		return _distance;
	}

	protected int getCost(){
		return _cost;
	}

	protected HashMap<Node, Integer> getParents(){
		return _parents;
	}

	protected HashMap<Node, Integer> getChildren(){
		return _children;
	}
}
