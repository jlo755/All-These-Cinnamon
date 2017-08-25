package scheduling;
import java.io.IOException;
import java.util.HashMap;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.ImportException;

import dataStructure.Node;
import inputParse.DotParser;
import inputParse.Edge;
import outputParse.OutputParser;

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
		long startTime = System.nanoTime();
		scheduler = new Scheduler();
		dotParser = new DotParser(args[0]);
		scheduler.setProcessorNumber(Integer.parseInt(args[1]));
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		// Output the solution in a dot format file.
		outputSolution(args[0]);
		long endTime = System.nanoTime();
		System.out.println("The program took: "+(endTime - startTime)/1000000000.0);
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
		PartialSchedule graphSolution = scheduler.getBestState().getCurrentBestSchedule();
		int[] processors = graphSolution.getNodeProcessors();
		double[] endTimes = graphSolution.getEndTimes();
		double[] startTimes = graphSolution.getStartTimes();
		for(Object n:graph.vertexSet()){
			Node node = (Node) n;
			int index = graphSolution.getNodeOrdering().get(node.getID());
			node.setStartTime(startTimes[index]);
			node.setEndTime(endTimes[index]);
			node.setProcessor(processors[index]);
		}
		outputParse.setGraph(dotParser.getGraph());
		outputParse.outputDot();
		outputParse.formatFile();
		System.out.println("Output file is " + fileName.replaceAll(".dot", "Output")+".dot");
	}
}
