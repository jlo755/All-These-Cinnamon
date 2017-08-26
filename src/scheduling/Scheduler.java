package scheduling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;

import javax.swing.Timer;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

import dataStructure.Node;
import inputParse.Edge;
import outputParse.OutputParser;
import visualisation.VisualController;


/**
 *
 * This class produces an optimal solution to a multi-processor
 * scheduling problem. This is based on an input graph representing a
 * series of tasks costs and their dependencies, as well as the
 * number of processors specified.
 *
 */
public class Scheduler {

	protected double currentBestSolution; // Current best scheduling time
	protected FinalState bestSchedule; // Best solutions for all of the Nodes.
	protected LinkedHashMap<String, Node> nodeMap; // Input task scheduling graph.
	protected double sumAdding = 0;
	protected double totalTaskTime = 0;
	protected int _numProcessors;
	protected int _numSchedules=0;
	protected ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
	protected ArrayList<Double> bestTimes = new ArrayList<Double>();
	protected ArrayList<Double> bestTimesCopy = new ArrayList<Double>();
	protected double time = 0;
	private VisualController _vc;
	private PartialSchedule _currentSchedule;

	/**
	 * Initialize the best solution so far to infinity on starting.
	 */
	public Scheduler() {
		currentBestSolution = Double.POSITIVE_INFINITY;
	}

	/**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 * @throws IOException 
	 */
	public void schedule() {

		initializeNodes();
		
		// "Nodemap" is the input graph for the algorithm.
		Timer time2;
		int timeDelay = 100;
		int timeDelay2 = 50;
		ActionListener time;
		time = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fire();

			}
		};

		ActionListener Time;
		Time = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("FIRE");
				fireSecondUpdate();

			}
		};
		Timer Time2;
		Time2 = new Timer(timeDelay2, Time);

		time2 = new Timer(timeDelay, time);
		for (Node n : nodeMap.values()) {
			// If a node doesn't have parents, it is a starting node
			if (n.getParents().isEmpty()) {

				PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);
				schedule.decideIndex();
				schedule.update(n, 1);
				schedules.add(schedule);

			}
		}
		time2.start();
		Time2.start();
		dfs();
		Time2.start();
		time2.stop();
		fire();

		try {
			outputSolution();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Fires an event to VisualController to notify the graph to update its state.
	 */
	private void fire() {
		_vc.setSchedule(_currentSchedule);
		_numSchedules++;
		_vc.updateGraph();

	}

	private void fireSecondUpdate() {
		if(!(bestTimesCopy.size() == 0)) {
			_vc.setScatterPlotInput(bestTimesCopy);
			for(Double d: bestTimesCopy){
				System.out.println("Hi");
			}
			//System.out.println("Hi");
			bestTimesCopy.clear();
		}
	}


	/**
	 * Sets the visual controller.
	 * @param vc
	 */
	public void setVisualController(VisualController vc){
		_vc = vc;
	}

	/**
	 * Initializes the nodes final time and bottom level.
	 */
	protected void initializeNodes() {
		for (Node n : nodeMap.values()) {
			totalTaskTime += n.getCost();
			n.setBottomLevel(findMaxBottomLevel(nodeMap, n.getID()));
		}
	}
	
	/**
	 * Outputs the solution in a dot file format.
	 * @throws IOException
	 */
	public void outputSolution() throws IOException{
		OutputParser outputParse = new OutputParser();
		outputParse.setFileName(LaunchScheduler._fileName);
		DirectedAcyclicGraph<Node, Edge> graph = LaunchScheduler.dotParser.getGraph();
		PartialSchedule graphSolution = bestSchedule.getCurrentBestSchedule();
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
		outputParse.setGraph(LaunchScheduler.dotParser.getGraph());
		outputParse.outputDot();
		outputParse.formatFile();
		System.out.println("Output file is " +LaunchScheduler._fileName.replaceAll(".dot", "Output")+".dot");
	}

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 *
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 **/
	public void dfs() {

		// set the node as being completed
		Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();
		for(PartialSchedule schedule:schedules) {
			scheduleStack.add(schedule);
		}
		while(!scheduleStack.isEmpty()) {
			PartialSchedule schedule = scheduleStack.pop();
			_currentSchedule = schedule;
			ArrayList<String> reachable = schedule.getReachable();
			for(String s:reachable) {
				if(schedule.startTimeZeroProcessors() > 1) {
					Node n = nodeMap.get(s);
					boolean discovered = false;
					for(int i = 1; i<=_numProcessors; i++) {
						double time = schedule.getProcessorTime(i);
						if(time == 0.0 && !discovered) {
							discovered = true;
							PartialSchedule childSchedule = schedule.makeChildSchedule();
							childSchedule.update(n, i);
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution) {
								scheduleStack.push(childSchedule);
							}
						} else if(time != 0.0){
							PartialSchedule childSchedule = schedule.makeChildSchedule();
							childSchedule.update(n, i);
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution) {
								scheduleStack.push(childSchedule);
							}
						}
					}
				}
				else {
					for(int i = 1; i<=_numProcessors; i++) {
						Node n = nodeMap.get(s);

						PartialSchedule childSchedule = schedule.makeChildSchedule();

						childSchedule.update(n, i);
						double maxHeuristic = childSchedule.getMaxHeuristic(n);
						if(maxHeuristic < currentBestSolution) {
							scheduleStack.push(childSchedule);
						}
					}
				}
			}
			if(reachable.isEmpty()) {

				double[] solution = schedule.getEndTimes();
				double endTime = 0;

				for(int i = 0; i<solution.length; i++) {
					if(solution[i] > endTime) {
						endTime = solution[i];
					}
				}

				if(!bestTimes.contains(endTime)) {
					bestTimes.add(endTime);
					bestTimesCopy.add(endTime);
					sumAdding++;
				}

				if(endTime < currentBestSolution) {
					currentBestSolution = endTime;
					bestSchedule.setCurrentBestState(schedule);
				}
			}
		}
	}

	/**
	 * Heuristic for critical path by finding the maximum bottom level of a node within a graph.
	 * @param graph
	 * @param initialNode
	 * @return
	 */
	public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode){
		Stack<String> s = new Stack<String>();
		s.add(initialNode);
		double startTime = graph.get(initialNode).getStartTime();
		graph.get(initialNode).setOptimalBottomLevel(startTime+graph.get(initialNode).getCost());
		double max = 0;
		while(!s.isEmpty()){
			Node node = graph.get(s.pop());
			if(node.getParents().keySet().contains(graph.get(initialNode))){
				graph.get(initialNode).setOptimalBottomLevel(startTime+graph.get(initialNode).getCost());
			}
			for(Node child: node.getChildren().keySet()){
				s.push(child.getID());
			}
			if(node.getID() != initialNode) {
				double test = 0;
				for(Node parent:node.getParents().keySet()) {
					if(test<parent.getOptimalBottomLevel()) {
						test = parent.getOptimalBottomLevel();
					}
				}
				node.setOptimalBottomLevel(test+node.getCost());
			}
		}
		for(Node n:graph.values()) {
			if(n.getOptimalBottomLevel() > max && n.getChildren().isEmpty()) {
				max = n.getOptimalBottomLevel();
			}
			n.setOptimalBottomLevel(0.0);
		}

		return max;
	}

	/**
	 * Returns the currentBestSolution field.
	 *
	 * @return
	 */
	public double getCurrentBestSolution() {
		return currentBestSolution;
	}

	/**
	 * Returns the bestState field.
	 *
	 * @return
	 */
	public FinalState getBestState() {
		return bestSchedule;
	}

	public void setProcessorNumber(int processors) {
		_numProcessors = processors;
	}

	/**
	 * Sets the bestState field.
	 *
	 * @param newBestState
	 */
	public void setBestState(FinalState newBestState) {
		bestSchedule = newBestState;
	}

	/**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 *
	 * @param taskGraph
	 */
	public void provideTaskGraph(LinkedHashMap<String, Node> taskGraph) {
		nodeMap = taskGraph;
		bestSchedule = new FinalState();

	}

}
