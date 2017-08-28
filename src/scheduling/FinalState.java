package scheduling;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JPanel;

import dataStructure.Node;
import visualisation.VisualController;

/**
 *
 * This class stores and manages the currently found "best solution" for a Node object,
 * used while traversing state space. A separate Node object is used to track this within
 * a HashMap.
 *
 */

public class FinalState {
	// Current best solution for a Node mapped to an ID in String.
	PartialSchedule _currentBestSchedule;

	/**
	 * Instantiate a set of Nodes' best solution to be 0.
	 * @param taskGraph
	 */
	public FinalState(){
	}

	/**
	 * Set the current best state for all the Nodes in a graph.
	 * @param graph
	 */
	public void setCurrentBestState(PartialSchedule schedule){
		_currentBestSchedule = schedule;
	}

	/**
	 * Get HashMap for current best state of Nodes.
	 * @return
	 */
	public PartialSchedule getCurrentBestSchedule(){
		return _currentBestSchedule;
	}

	/**
	 * Print current best state of all Nodes.
	 */
	public void printCurrentBestSchedule(){
		double[] endTimes = _currentBestSchedule.getEndTimes();
		int[] processors = _currentBestSchedule.getNodeProcessors();
		Set<String> s = _currentBestSchedule.getNodeOrdering().keySet();
		for(String nodeName:s) {
			System.out.println(nodeName);
			int index = _currentBestSchedule.getNodeOrdering().get(nodeName);
			System.out.println("End Time: "+endTimes[index]);
			System.out.println("Processor: "+processors[index]);
		}
	}
}
