package inputParse;

import dataStructure.Node;

/**
 * 
 * Class representing an edge between a parent and a child
 * node within a graph, and its cost.
 *
 */
public class Edge {
	private Node _childNode;
	private Node _parentNode;
	private double _cost;
	
	/**
	 * Initialize an edge between two nodes.
	 * @param childNode
	 * @param parentNode
	 */
	public Edge(Object childNode, Object parentNode){
		_childNode = (Node) childNode;
		_parentNode = (Node) parentNode;
	}
	
	/**
	 * Initialize an edge with cost between two nodes.
	 * @param childNode
	 * @param parentNode
	 * @param cost
	 */
	public Edge(Object childNode, Object parentNode, double cost){
		_childNode = (Node) childNode;
		_parentNode = (Node) parentNode;
		_childNode.addParent(_parentNode, cost);
		_parentNode.addChild(_childNode, cost);
		_cost = cost;
	}
	
	/**
	 * Return the cost of the edge.
	 * @return
	 */
	public double getCost() {
		return _cost;
	}
}
