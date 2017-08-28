package visualisation;


import dataStructure.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import scheduling.PartialSchedule;
import scheduling.ScheduleListener;
import scheduling.Scheduler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * This class acts as the controller between the model and the view in MVC
 */
public class VisualController implements ScheduleListener {

	private VisualGraph vg;
	private VisualStatistics vs;
	private HashMap<String, dataStructure.Node> _graph;
	ArrayList<Double> bestTimes;
	private SingleGraph graph;
	private ArrayList<dataStructure.Node> _nodes = new ArrayList<>();
	private JPanel graphPanel;
	private JPanel statsPanel;
	private Scheduler _model;
	private PartialSchedule _schedule;
	private JFreeChart ScatterPlotChart;
	private int count;
	private ProcessorScreen _ps;

	public  VisualController(HashMap<String, Node> g, JPanel Panel1, JPanel Panel2, ProcessorScreen ps){
		graphPanel = Panel1;
		statsPanel = Panel2;
		_graph = g;
		vg = new VisualGraph(g, graphPanel);
		graph = vg.graph;
		_ps = ps;
		vs = new VisualStatistics();
		ScatterPlotChart = vs.createStateSpaceGraph();
		graphPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		viewer.enableAutoLayout();
		ViewPanel viewPanel = viewer.addDefaultView(false);
		graphPanel.add(viewPanel);
		ChartPanel CP = new ChartPanel(ScatterPlotChart);
		CP.setBorder(new LineBorder(new Color(0, 0, 0)));
		CP.setBounds(0, 0, 572, 434);
		statsPanel.add(CP);

	}
	/**
	 * This method sets the schedule
	 * @param schedule
	 */
	public synchronized void setSchedule(PartialSchedule schedule){
		_schedule = schedule;
	}
	/**
	 * This method sets the best times calculated in the scatter plot
	 * @param bestTimes
	 */
	public synchronized void setScatterPlotInput(ArrayList<Double> bestTimes){
		this.bestTimes = bestTimes;

	}
	/**
	 * This method updates fields of the nodes and their characteristics and then calls the update GUI method to show these changes
	 */
	public synchronized void updateGraph(){
		double[] endTimes = _schedule.getEndTimes();
		int[] processors = _schedule.getNodeProcessors();
		double[] startTimes = _schedule.getStartTimes();
		for(Node node:_graph.values()){
			int index = _schedule.getNodeOrdering().get(node.getID());
			node.setStartTime(startTimes[index]);
			node.setEndTime(endTimes[index]);
			node.setProcessor(processors[index]);
		}
		updateGUI();
	}

	public synchronized void updateGUI(){
		_nodes = sortStartTimes();
		this.update();
	}
	/**
	 * This method returns the graph of the graph stream
	 * @return
	 */

	public synchronized HashMap<String, dataStructure.Node> getGraph(){
		return _graph;
	}
	/**
	 * This method gets the panel of the graph stream
	 * @return
	 */
	public synchronized JPanel getGraphPanel(){
		return graphPanel;
	}
	/**
	 * This method returns the panel in which the scatter plot is in
	 * @return
	 */
	public synchronized JPanel getStatsPanel(){
		return statsPanel;
	}
	/**
	 * This method sets the labels in the processor screen
	 * @param schedulesProduced
	 * @param currentBestSolution
	 */
	public synchronized void setStateLabel(String schedulesProduced,Double currentBestSolution){
		_ps.setDynamicLabel(schedulesProduced, currentBestSolution);
	}
	/**
	 * This method sets the labels in the processor screen
	 * @param overallTime
	 */
	public synchronized void setTimeLabel(Double overallTime){
		_ps.setTotalTimeLabel( overallTime);
	}
	/**
	 * This method sets the labels in the processor screen
	 * @param status
	 * @param memoryUsage
	 */
	public synchronized void setLabelProcessorScreen(String status,String memoryUsage){
		_ps.setDynamicLabelForMoreFrequentFire(status, memoryUsage);
	}
	/**
	 * This method sorts the start times of the node arraylist so that they are ordered
	 * @return
	 */
	public synchronized ArrayList<dataStructure.Node> sortStartTimes(){
		if(_nodes.size()>0) {
			_nodes.clear();
		}
		for(dataStructure.Node n : _graph.values()){
			_nodes.add(n);
		}
		Collections.sort(_nodes, Node.startTimes());
		return _nodes;
	}
	/**
	 * This method sets the visual model
	 * @param model
	 */
	public synchronized void setVisualModel(Scheduler model){
		_model = model;
	}

	/**
	 * This method sets the count of the schedule
	 * @param count
	 */
	public synchronized void setScheduleCount(int count){
		this.count = count;
	}

	/**
	 * This method updates the graph traversal in the graph stream
	 */
	@Override
	public synchronized void update() {

		vg.startTraversal( _nodes, _ps.getProcessorCount());
	}

	/**
	 * This updates the scatter plot points
	 * @param time
	 */
	public synchronized void updateStats(double time) {

		if(bestTimes.size() != 0) {
			for(double d:bestTimes){
				vs.updatePlot(d, time);

			}
		}
	}

}
