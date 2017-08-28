package visualisation;


import dataStructure.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import scheduling.PartialSchedule;
import scheduling.ScheduleListener;
import scheduling.ScheduleWorker;
import scheduling.Scheduler;
import visualisation.VisualGraph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualController implements ScheduleListener {

	private VisualGraph vg;
	private VisualStatistics vs;
	private HashMap<String, dataStructure.Node> _graph;
	ArrayList<Double> bestTimes;
	private SingleGraph graph;
	private ArrayList<dataStructure.Node> _nodes = new ArrayList<>();
	private JPanel panel1;
	private JPanel panel2;
	private Scheduler _model;
	private PartialSchedule _schedule;
	private JFreeChart chart;
	private int count;
	private ProcessorScreen _ps;

	public VisualController(HashMap<String, Node> g, JPanel Panel1, JPanel Panel2, ProcessorScreen ps){
		panel1 = Panel1;
		panel2 = Panel2;
		_graph = g;
		vg = new VisualGraph(g, panel1);
		graph = vg.graph;
		_ps = ps;
		vs = new VisualStatistics();
		chart = vs.createStateSpaceGraph();
		panel1.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		viewer.enableAutoLayout();
		ViewPanel viewPanel = viewer.addDefaultView(false);
		panel1.add(viewPanel);
		ChartPanel CP = new ChartPanel(chart);
		CP.setBorder(new LineBorder(new Color(0, 0, 0)));
		CP.setBounds(0, 0, 572, 434);
		panel2.add(CP);
		//_nodes = this.sortStartTimes();
	}

	public synchronized void setSchedule(PartialSchedule schedule){
		_schedule = schedule;
	}

	public synchronized void setScatterPlotInput(ArrayList<Double> bestTimes){
		this.bestTimes = bestTimes;
		//updateStats();
	}

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

	public synchronized HashMap<String, dataStructure.Node> getGraph(){
		return _graph;
	}

	public synchronized JPanel getGraphPanel(){
		return panel1;
	}

	public synchronized JPanel getStatsPanel(){
		return panel2;
	}

	public synchronized void setStateLabel(String text,Double input3){
		_ps.setNewLabel(text, input3);
	}
	public void setTimeLabel(Double overallTime){
		_ps.setLabelTime( overallTime);
	}

	public synchronized void setStateLabel2(String text,String string){
		_ps.setNewLabel2(text, string);
	}

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

	public synchronized void setVisualModel(Scheduler model){
		_model = model;
	}

	public synchronized void setScheduleCount(int count){
		this.count = count;
	}

	@Override
	public synchronized void update() {

		vg.startTraversal( _nodes, _ps.getProcessorCount());
	}

	public synchronized void updateStats(double time) {

		if(bestTimes.size() != 0) {
			for(double d:bestTimes){
				//panel2.setVisible(false);
				vs.updatePlot(d, time);
				//chart = vs.createStateSpaceGraph();
				//panel2.setVisible(true);
			}
		}
	}

}
