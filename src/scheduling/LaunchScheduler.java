package scheduling;
import java.io.IOException;
import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.ImportException;

import application.Main;
import application.SwingFx;
import application.mainMenuController;
import dataStructure.Node;
import inputParse.DotParser;
import inputParse.Edge;
import outputGraph.GraphController;
import outputGraph.VisualGraph;
import outputParse.OutputParser;
import statistics.compareSchedules;

/**
* This class recursively calls the recursive method to get the children nodes of a particular node. As this occurs recursively,
* we traverse through the entire graph. When a node is visited, we check all possible time costs for that particular node being
* placed on the different processors. We then check which of these is the best time. This process continues until all the nodes
* in the network are visited and we have obtained the best scheduling times.
*/
public class LaunchScheduler {

	private static Scheduler scheduler;
	private static DotParser dotParser;
	private static int _noOfProcessors;
	private static String _fileName;

	public static void main(String[] args) throws IOException, ImportException {

		// Parse the dot graph input and schedule an optimal solution.
		long startTime = System.nanoTime();
		//Main m = new Main();
		//m.launchMenu();
		scheduler = new Scheduler();
		dotParser = new DotParser(args[0]);
		scheduler.setProcessorNumber(Integer.parseInt(args[1]));
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		GraphController gc = new GraphController();
		//gc.createGraph(dotParser.getNodeMap());
		compareSchedules chart = new compareSchedules(
				"School Vs Years" ,
				"Numer of Schools vs years");

		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
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
		System.out.println("Output file is " + fileName.replaceAll(".dot", "Output")+".dot");
	}

	public static void setProcessor(int test) {
		_noOfProcessors = test;

	}
	public static void setFileName(String fileName) {
		_fileName = fileName;

	}
}
