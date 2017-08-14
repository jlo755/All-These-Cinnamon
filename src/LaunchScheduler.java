import java.io.IOException;
import java.util.HashMap;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.ImportException;

import DataStructure.Node;
import InputParse.DotParser;
import InputParse.Edge;
import OutputParse.OutputParser;

/**
* This class recursively calls the recursive method to get the children nodes of a particular node. As this occurs recursively,
* we traverse through the entire graph. When a node is visited, we check all possible time costs for that particular node being
* placed on the different processors. We then check which of these is the best time. This process continues until all the nodes
* in the network are visited and we have obtained the best scheduling times.
*/
public class LaunchScheduler {
	
	private static Scheduler scheduler;
	private static DotParser dotParser;

	public static void main(String[] args) throws IOException, ImportException {
		
		// Parse the dot graph input and schedule an optimal solution.
		scheduler = new Scheduler();
		dotParser = new DotParser(args[0]);
		scheduler.setProcessorNumber(Integer.parseInt(args[1]));
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		
		// Output the solution in a dot format file.
		outputSolution(args[0]);
	}
	
	/**
	 * This method uses the solution found by the Scheduler to output the solution
	 * in a dot format file.
	 * @throws IOException
	 */
	private static void outputSolution(String fileName) throws IOException {
		OutputParser outputParse = new OutputParser();
		outputParse.setFileName(fileName);
		DirectedAcyclicGraph<Node, Edge> graph = dotParser.getGraph();
		HashMap<String, Node> graphSolution = scheduler.getBestState().getCurrentBestState();
		for(Object n:graph.vertexSet()){
			Node node = (Node) n;
			for(Node n1:graphSolution.values()){
				if(node.getID() == n1.getID()){
					node.setStartTime(n1.getStartTime());
					node.setEndTime(n1.getEndTime());
					node.setProcessor(n1.getProcessor());
				}
			}
		}
		outputParse.setGraph(dotParser.getGraph());
		outputParse.outputDot();
		outputParse.formatFile();
		scheduler.getBestState().printCurrentBestState();
	}
}