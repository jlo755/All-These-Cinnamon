import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Class that is responsible for parsing input for 
 * multi-processor task scheduling problem.
 * @author victor
 *
 */
public class InputParser {

	/**
	 * This method reads a dot format input in the terminal describing a series of tasks
	 * and their dependencies and places the information in a HashMap of the Node class
	 * representing a task scheduling graph.
	 * @param nodeMap
	 * @throws IOException
	 */
	public void parseInput(HashMap<String, Node> nodeMap) throws IOException {

		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String lineToRead = "";
		System.out.println("hello");
		bfr.readLine(); // get rid of the first line in the input


		// if we havent reached the end of the input which ends with "}"
		while (!lineToRead.equals("}")) {

			// get the line
			lineToRead = bfr.readLine();
			// remove all the spaces
			lineToRead = lineToRead.replaceAll("\\s", "");

			// when there is an arrow in the line, it describes dependencies between two tasks
			if (lineToRead.contains("->")) {

				// getting position of the left-side of arrow to get the parent name of node
				int IndexOfArrowHead = lineToRead.indexOf("-");

				// getting position of the point of arrow to appropriately get the child name of
				// node
				int IndexOfArrowEnd = lineToRead.indexOf(">");

				// determining the end position of the child node's name
				int IndexOfWeight = lineToRead.indexOf("[");
				String nameOfParentNode = lineToRead.substring(0, IndexOfArrowHead);
				String nameOfChildrenNode = lineToRead.substring(IndexOfArrowEnd + 1, IndexOfWeight);

				// determining position of when the weight begins
				int indexOfStartCost = lineToRead.indexOf("=");
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost + 1, lineToRead.length() - 2));

				// 
				Node parentNode = nodeMap.get(nameOfParentNode);
				Node childNode = nodeMap.get(nameOfChildrenNode);
				nodeMap.get(nameOfParentNode).addChild(childNode, weight);
				nodeMap.get(nameOfChildrenNode).addParent(parentNode, weight);

				// when there is no arrow in the line, it indicates a new task being introduced
			} else if (!lineToRead.equals("}")) {

				// get start of the node name
				int IndexOfWeight = lineToRead.indexOf("[");

				// everything before the "]" is the name of the node
				String nameOfNode = lineToRead.substring(0, IndexOfWeight);
				int indexOfStartCost = lineToRead.indexOf("=");

				// everything after the equals is the weight, - 2 to get rid of the " ] ;"
				int weight = Integer.parseInt(lineToRead.substring(indexOfStartCost + 1, lineToRead.length() - 2));

				// hashmap which has for node a eg: (a, node a) as the input
				nodeMap.put(nameOfNode, new Node(nameOfNode, weight));
			}
		}
	}

}
