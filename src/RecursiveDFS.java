import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RecursiveDFS {

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
				Node parentNode = nodeMap.get(nameOfParentNode);
				Node childNode = nodeMap.get(nameOfChildrenNode);
				nodeMap.get(nameOfParentNode).addChild(childNode, weight);
				nodeMap.get(nameOfChildrenNode).addParent(parentNode, weight);
			} else if(!lineToRead.equals("}")){ // when there is no arrow in the line
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfNode = lineToRead.substring(0, IndexOfWeight); // everything before the "]" is the name of the node
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost+1, lineToRead.length()-2)); // everything after the equals is the weight, - 2 to get rid of the " ] ;"
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight)); // hashmap which has for node a eg: (a, node a) as the input
			}
		}
		DFS(nodeMap, "a", 1);
	}
	
	public static void DFS(HashMap<String, Node> graph, String initialNode, int processor) {
		System.out.println(initialNode+" Processor "+processor);
		graph.get(initialNode).setCompleted(true);
		for(int i = 1; i<3; i++){
			for(String nodes : graph.keySet()){
				if(graph.get(nodes).getCompleted()){
					continue;
				}
				boolean dependenciesDone = true;
				for(Node parentNode:graph.get(nodes).getParents().keySet()){
					if(!parentNode.getCompleted()){
						dependenciesDone = false;
						break;
					}
				}
				if(dependenciesDone){
					DFS(graph, nodes, i);
					graph.get(nodes).setCompleted(false);
				}
			}
		}
	}
}


