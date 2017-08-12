import java.util.HashMap;

public class DependencyNode extends Node {
	private HashMap<DependencyNode, Double> _parents; // this is the parents hashmap with the parent node and their communication cost

	protected DependencyNode(String id, int cost){
		super(id, cost);
		_parents = new HashMap<DependencyNode, Double>();
	}

	protected void addParent(DependencyNode node, double cost){
		_parents.put(node, cost);
	}

	protected void addChild(DependencyNode node, double cost){
		_children.put(node, cost);
	}

	protected HashMap<DependencyNode, Double> getParents(){
		return _parents;
	}
}