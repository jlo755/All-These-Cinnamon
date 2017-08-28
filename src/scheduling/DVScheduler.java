package scheduling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class DVScheduler extends Scheduler {
	protected int _numSchedules=0;
	protected ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();
	protected ArrayList<Double> bestTimes = new ArrayList<Double>();
	protected ArrayList<Double> bestTimesCopy = new ArrayList<Double>();
	protected double time = 0;
	protected VisualController _vc;
	protected PartialSchedule _currentSchedule;
	protected long startTime;
	protected long actualMemUsed;
	protected String status = "Current Status: Processing...";
	protected int schedulesProcessed;
	protected Timer Time2;
	protected Timer time2;
	protected double _overallTimer;

	/**
	 * Initialize the best solution so far to infinity on starting.
	 */
	public DVScheduler() {
		currentBestSolution = Double.POSITIVE_INFINITY;
	}

	/**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 * @throws IOException
	 */
	public void schedule() {
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		initializeNodes();

		// "Nodemap" is the input graph for the algorithm.
		int timeDelay = 100;
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
				fireLabelUpdate();

			}
		};
		Time2 = new Timer(timeDelay, Time);
		startTime = System.nanoTime();
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
		long startTime = System.nanoTime();
		dfs();
		long endTime = System.nanoTime();
		_overallTimer =(endTime-startTime)/1000000000.0;
		Double toBeTruncated = new Double(""+_overallTimer);

		_overallTimer = BigDecimal.valueOf(toBeTruncated)
		    .setScale(4, RoundingMode.HALF_UP)
		    .doubleValue();
		setTimeLabel();
	}

	/**
	 * Fires an event to VisualController to notify the graph to update its state.
	 */
	public synchronized void fire() {
		_vc.setSchedule(_currentSchedule);
		_numSchedules++;
		_vc.updateGraph();
		long afterUsedMem=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024/1024;
		_vc.setStateLabel2("Status: Processing",afterUsedMem+"MB");

	}

	public synchronized void fireLabelUpdate(){
		_vc.setStateLabel(this.schedulesProcessed+"",currentBestSolution);
	}

	public void setTimeLabel(){
		_vc.setTimeLabel(_overallTimer);
	}

	public synchronized void fireBest() {
		_vc.setSchedule(bestSchedule.getCurrentBestSchedule());
		//_numSchedules++;
		_vc.updateGraph();

	}

	public synchronized void fireSecondUpdate() {
		if(bestTimesCopy.size() != 0) {
			_vc.setScatterPlotInput(bestTimesCopy);
			//System.out.println("Hi");
			//System.out.println((System.nanoTime()-startTime)/1000000000.0);
			_vc.updateStats((System.nanoTime()-startTime)/1000000000.0);
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
			schedulesProcessed++;
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
							String id = childSchedule.generateId();
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) {
								_prevSchedules.add(id);
								scheduleStack.push(childSchedule);
							}
						} else if(time != 0.0){
							PartialSchedule childSchedule = schedule.makeChildSchedule();
							childSchedule.update(n, i);
							String id = childSchedule.generateId();
							double maxHeuristic = childSchedule.getMaxHeuristic(n);
							if(maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) {
								_prevSchedules.add(id);
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
						String id = childSchedule.generateId();
						double maxHeuristic = childSchedule.getMaxHeuristic(n);
						if(maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) {
							_prevSchedules.add(id);
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
					//schedulesProcessed++;
					fireSecondUpdate();
				}

				if(endTime < currentBestSolution) {
					currentBestSolution = endTime;
					bestSchedule.setCurrentBestState(schedule);
				}
			}
		}
		time2.stop();
		Time2.stop();
		fireBest();
		fireLabelUpdate();
		fireSecondUpdate();

		long afterUsedMem=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024/1024;
		status="Current Status: Finished";
		//actualMemUsed=afterUsedMem-beforeUsedMem;
		_vc.setStateLabel2(status,afterUsedMem+"MB");
	}

}
