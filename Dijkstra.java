import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Dijkstra {

	public static void main(String[] args) throws IOException {
		HashMap<String,Node> nodeMap = new HashMap<String,Node>();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String lineToRead = "";
		bfr.readLine();
		while(!lineToRead.equals("}")){
			lineToRead = bfr.readLine();
			lineToRead=lineToRead.replaceAll("\\s", "");
			int i = 0;
			int indexOfWeight = lineToRead.indexOf("[");
			if(lineToRead.contains("->")){
				int IndexOfArrowHead = lineToRead.indexOf("-"); // getting position of the left-side of arrow to get the parent name of node
				int IndexOfArrowEnd = lineToRead.indexOf(">"); // getting position of the point of arrow to appropriately get the child name of node
				int IndexOfWeight = lineToRead.indexOf("["); // determining the end position of the child node's name
				String nameOfParentNode = lineToRead.substring(0, IndexOfArrowHead);
				String nameOfChildrenNode = lineToRead.substring(IndexOfArrowEnd+1, IndexOfWeight);
				int indexOfStartCost = lineToRead.indexOf("="); // determining position of when the weight begins
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2));
				nodeMap.get(nameOfParentNode).addChild(nodeMap.get(nameOfChildrenNode), weight);
				nodeMap.get(nameOfChildrenNode).addParent(nodeMap.get(nameOfParentNode), weight);
			} else if(!lineToRead.equals("}")){
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfNode = lineToRead.substring(0, IndexOfWeight);
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2));
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight));
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

	public static void Dijkstra(HashMap<String,Node> graph, String s){
		HashMap<String, Boolean> color = new HashMap<String, Boolean>();
		for(String id:graph.keySet()){
			Node startingMap = graph.get(s);
			if(startingMap.getChildren().containsKey(graph.get(id))){
				startingMap.updateDistance(graph.get(id), startingMap.getChildren().get(graph.get(id))+startingMap.getCost());
				color.put(id, false);
			} else if(id.equals(s)){
				color.put(id, true);
				startingMap.updateDistance(graph.get(id), startingMap.getCost());
			} else {
				startingMap.updateDistance(graph.get(id), Integer.MAX_VALUE);
				color.put(id, false);
			}
		}
		String idToDo = s;
		while(color.containsValue(false)){
			int min = Integer.MAX_VALUE;
			for(Node child:graph.get(s).getDistance().keySet()){
				System.out.println("Current Node: "+idToDo+" Node:"+child.getID());
				System.out.println("Cost: "+graph.get(s).getDistance().get(child));
				if(graph.get(s).getDistance().get(child) <= min && !color.get(child.getID())){
					System.out.println("Yes");
					min = graph.get(s).getDistance().get(child);
					idToDo = child.getID();
				}
			}

			color.put(idToDo, true);
			for(Node child: graph.get(idToDo).getChildren().keySet()){
				HashMap<Node, Integer> mapToCompare = graph.get(s).getDistance();
				if(graph.get(idToDo).getChildren().get(child)+mapToCompare.get(graph.get(idToDo)) < mapToCompare.get(child)){
					mapToCompare.put(child, graph.get(idToDo).getChildren().get(child)+mapToCompare.get(graph.get(idToDo)));
				}
			}
		}
	}
}


class Node {
	private HashMap<Node, Integer> _parents;
	private HashMap<Node, Integer>_children;
	private String _id;
	private int _cost;
	private HashMap<Node, Integer> _distance;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_parents = new HashMap<Node, Integer>();
		_children = new HashMap<Node, Integer>();
		_distance = new HashMap<Node, Integer>();
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
