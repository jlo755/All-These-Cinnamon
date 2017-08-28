package scheduling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import javax.swing.Timer;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

import dataStructure.Node;
import inputParse.Edge;
import outputParse.OutputParser;
import pt.runtime.TaskID;
import pt.runtime.TaskIDGroup;
import visualisation.VisualController;

/**
 * Parallel scheduler combined with the data visualization for finding optimal task schedules.
 *
 */
public class ParallelDVScheduler extends DVScheduler{
	int threadsToUse;

	private void processScheduleTask(Stack<PartialSchedule> scheduleStack) {
		while (!scheduleStack.isEmpty()) {
			processSchedule(scheduleStack);
		}
	}

	/**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization. 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 */

	public void dfs() {
		threadsToUse = ScheduleFactory.getInstance().getParallelise();
		for (Node n : nodeMap.values()) {
			// If a node doesn't have parents, it is a starting node
			if (n.getParents().isEmpty()) {

				PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);
				schedule.decideIndex();
				schedule.update(n, 1);
				schedules.add(schedule);

			}
		}
		Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();
		for (PartialSchedule schedule : schedules) {
			scheduleStack.add(schedule);
		}
		ExecutorService e = Executors.newFixedThreadPool(threadsToUse);
		e.submit(new Runnable() {
			public void run() {
				startTime = System.nanoTime();

				processScheduleTask(scheduleStack);

				long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				
				status="Current Status: Finished";
				_vc.setStateLabel2(status,actualMemUsed+"MB");

				long endTime = System.nanoTime();
				System.out.println("Hello");
				System.out.println((endTime-startTime)/1000000000.0);
				time2.stop();
				Time2.stop();
				fireBest();
				fireLabelUpdate();
				fireSecondUpdate();
			  }
		});
		

	}

	/**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 */
	private void processSchedule(Stack<PartialSchedule> scheduleStack) {
		try{
			schedulesProcessed++;
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
					schedulesProcessed++;
					fireSecondUpdate();
				}

				if(endTime < currentBestSolution) {
					currentBestSolution = endTime;
					bestSchedule.setCurrentBestState(schedule);
					System.out.println(currentBestSolution);
				}
			}
		}   catch (EmptyStackException e){
			e.printStackTrace();
			// catch exception here and carry on execution
		}
	}

	/**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 */
	public void setThreads(int thread) {
		this.threadsToUse = thread;
	}
}
