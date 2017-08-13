package InputParse;

import DataStructure.Node;

public class Edge {
	private Node _childNode;
	private Node _parentNode;
	private double _cost;
	
	public Edge(Object childNode, Object parentNode){
		_childNode = (Node) childNode;
		_parentNode = (Node) parentNode;
	}
	
	public Edge(Object childNode, Object parentNode, double cost){
		_childNode = (Node) childNode;
		_parentNode = (Node) parentNode;
		_childNode.addParent(_parentNode, cost);
		_parentNode.addChild(_childNode, cost);
		_cost = cost;
	}
}
