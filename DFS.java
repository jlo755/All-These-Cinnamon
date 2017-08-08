import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

public class DFS {

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
		iterative(nodeMap, "a");
	}

	public static void iterative(HashMap<String, Node> graph, String initialNode){
		Stack<Node> s = new Stack<Node>();
		Node newInitialNode = new Node("a", 2);
		newInitialNode.setProcessor(1);
		s.push(newInitialNode);
		for(String a:graph.get("a").getChildren().keySet()){
			for(int i = 1; i<3; i++){
				if(i==newInitialNode.getProcessor()){
					newInitialNode.addChild("P"+i+a, 0);
				} else {
					newInitialNode.addChild("P"+i+a, graph.get("a").getChildren().get(a));
				}
			}
		}
		while(!s.isEmpty()){
			Node node = s.pop();
			if(!node.getCompleted()){
				node.setCompleted(true);
				for(int i = 0; i<2; i++){
					for(Node nodes : node.getChildren().keySet()){
						if(nodes.getCompleted()){
							continue;
						}
						boolean dependenciesDone = true;
						for(String parentNode:node.getParents().keySet()){
							if(!graph.get(parentNode).getCompleted()){
								dependenciesDone = false;
								break;
							}
						}
						if(dependenciesDone){
							s.push();
						}
					}
				}
			}
		}

	}
}

/*procedure DFS-iterative(G,v):
2      let S be a stack
3      S.push(v)
4      while S is not empty
5          v = S.pop()
6          if v is not labeled as discovered:
7              label v as discovered
				for all processors
8              for all nodes in G.adjacentEdges(v) do 
9                  if(reachable any parent completed but not yet discovered)
					S.push(w)*/


class Node {
	private HashMap<String, Double> _parents; // this is the parents hashmap with the parent node and their communication cost
	private HashMap<String, Double>_children; // this is the children hashmap with the child node and their comunication cost
	private String _id; // name of the node
	private int _cost; // cost of the node itself
	private HashMap<String, Double> _distances;
	private boolean _completed;
	private int _processor;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_parents = new HashMap<String, Double>();
		_children = new HashMap<String, Double>();
		_distances = new HashMap<String, Double>(); // storing the distances in a matrix to all the other nodes in the network (infinitiy and actual reachable costs) 
		_completed = false;
	}

	protected void addParent(String node, double cost){
		_parents.put(node, cost);
	}

	protected void addChild(String node, double cost){
		_children.put(node, cost);
	}

	public boolean getCompleted() {
		return _completed;
	}
	
	public void setProcessor(int i){
		_processor = i;
	}
	
	public int getProcessor(){
		return _processor;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
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


