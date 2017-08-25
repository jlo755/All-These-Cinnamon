package scheduling;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;

import dataStructure.Node;

/**
 * 
 * This class produces an optimal solution to a multi-processor 
 * scheduling problem. This is based on an input graph representing a 
 * series of tasks costs and their dependencies, as well as the 
 * number of processors specified. 
 *
 */
public class Scheduler2 {

	private double currentBestSolution; // Current best scheduling time
	private FinalState bestState; // Best solutions for all of the Nodes.
	private LinkedHashMap<String, Node> nodeMap; // Input task scheduling graph.
	private double sumAdding = 0;
	private double totalTaskTime = 0;
	private int _numProcessors;
	private ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
	private double time = 0;
	
	private static final ForkJoinPool pool = new ForkJoinPool();
	
	/**
	 * Initialize the best solution so far to infinity on starting.
	 */
	public Scheduler2() {
		currentBestSolution = Double.POSITIVE_INFINITY;
	}

	/**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 */
	public void schedule() {
		for (Node n : nodeMap.values()) {
			totalTaskTime += n.getCost();
			n.setBottomLevel(findMaxBottomLevel(nodeMap, n.getID()));
		}
		//findStartingOptimalBranch(nodeMap);
		System.out.println(currentBestSolution);
		// "Nodemap" is the input graph for the algorithm.
		//for (int i = 1; i <= _numProcessors; i++) {
		for (Node n : nodeMap.values()) {

			// If a node doesn't have parents, it is a starting node
			if (n.getParents().isEmpty()) {
				//n.setProcessor(1);
				PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);
				schedule.decideIndex();
				schedule.solve(n, 1);
				schedules.add(schedule);
				// call the recursive DFS method for that node to obtain and process all of the
				// children
				//dfs(nodeMap, n.getID());
			}
		}
		dfs();
		//}
		System.out.println(time);
		System.out.println("Best Solution: "+currentBestSolution);
	}

	/**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 */

	public void dfs() {

		// set the node as being completed
		Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();
		for(PartialSchedule schedule:schedules) {
			scheduleStack.add(schedule);
		}
		while(!scheduleStack.isEmpty()) {
			PartialSchedule schedule = scheduleStack.pop();
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
							childSchedule.solve(n, i);
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution) {
								scheduleStack.push(childSchedule);
							}
						} else if(time != 0.0){
							PartialSchedule childSchedule = schedule.makeChildSchedule();
							childSchedule.solve(n, i);
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution) {
								scheduleStack.push(childSchedule);
							}
						} else {
							
						}
					}
				}
				else {
					for(int i = 1; i<=_numProcessors; i++) {
						Node n = nodeMap.get(s);
						//long startTime = System.nanoTime();
						PartialSchedule childSchedule = schedule.makeChildSchedule();
						//long endTime = System.nanoTime();
						//time = time+((endTime-startTime)/1000000000.0);
						childSchedule.solve(n, i);
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
				if(endTime < currentBestSolution) {
					currentBestSolution = endTime;
				}
			}
		}

	}

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
		//System.out.println(max);
		return max;
	}

	public double findStartingOptimalBranch(HashMap<String, Node> graph){
		Queue<Node> optimalBranch = new PriorityQueue<Node>();
		for(Node n:graph.values()){
			optimalBranch.add(n);
		}
		Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();
		PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);
		schedule.decideIndex();
		//schedule.solve(n1, 1);
		scheduleStack.add(schedule);
		while(!optimalBranch.isEmpty()){
			//PartialSchedule schedule2 = scheduleStack.pop();
			Node n = optimalBranch.poll();
			PartialSchedule scheduleOnStack = scheduleStack.pop();
			PartialSchedule[] partialSchedules = new PartialSchedule[_numProcessors];
			for(int i = 1; i<=_numProcessors; i++){
				PartialSchedule childSchedule = scheduleOnStack.makeChildSchedule();
				childSchedule.solve(n, i);
				partialSchedules[i-1] = childSchedule;
			}
			int minIndex = 0;
			Double min = Double.POSITIVE_INFINITY;
			for(int i = 1; i<=_numProcessors; i++) {
				PartialSchedule scheduleToCompare = partialSchedules[i-1];
				if(min>scheduleToCompare.getNodeEndTime(n)) {
					min = scheduleToCompare.getNodeEndTime(n);
					minIndex = i;
				}
			}
			scheduleStack.push(partialSchedules[minIndex-1]);
			if(optimalBranch.isEmpty()){
				currentBestSolution = min;
				break;
			}
		}
		return 0.0;
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
		return bestState;
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
		bestState = newBestState;
	}

	/**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 * 
	 * @param taskGraph
	 */
	public void provideTaskGraph(LinkedHashMap<String, Node> taskGraph) {
		nodeMap = taskGraph;
		bestState = new FinalState(taskGraph);

	}

}
