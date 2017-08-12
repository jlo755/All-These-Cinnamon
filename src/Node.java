import java.util.HashMap;

public abstract class Node {
	protected HashMap<Node, Double>_children; // this is the children hashmap with the child node and their comunication cost
	private String _id; // name of the node
	private int _cost; // cost of the node itself
	private boolean _completed;

	protected Node(String id, int cost){
		_id = id;
		_cost = cost;
		_children = new HashMap<Node, Double>();
		_completed = false;
		// stores all the nodes we visited in the state space before this node was visited
	}

	protected void addChild(Node node, double cost){
		_children.put(node, cost);
	}

	public boolean getCompleted() {
		return _completed;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	protected String getID(){
		return _id;
	}

	protected int getCost(){
		return _cost;
	}

	protected HashMap<Node, Double> getChildren(){
		return _children;
	}
}