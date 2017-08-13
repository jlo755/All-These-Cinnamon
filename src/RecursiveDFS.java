import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.jgrapht.ext.ImportException;

import DataStructure.Node;
import InputParse.DotParser;

/**
* This class recursively calls the recursive method to get the children nodes of a particular node. As this occurs recursively,
* we traverse through the entire graph. When a node is visited, we check all possible time costs for that particular node being
* placed on the different processors. We then check which of these is the best time. This process continues until all the nodes
* in the network are visited and we have obtained the best scheduling times.
*/
public class RecursiveDFS {

	public static void main(String[] args) throws IOException, ImportException {
		
		Scheduler scheduler = new Scheduler();

		//InputParser parser = new InputParser();
		//parser.parseInput(nodeMap);
		DotParser dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		//scheduler.setBestState(new FinalState(nodeMap.keySet()));
		scheduler.schedule();
	}
}
