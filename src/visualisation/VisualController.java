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

	public void setSchedule(PartialSchedule schedule){
		_schedule = schedule;
	}

	public void setScatterPlotInput(ArrayList<Double> bestTimes){
		this.bestTimes = bestTimes;
		//updateStats();
	}

	public void updateGraph(){
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

	public void updateGUI(){
		_nodes = sortStartTimes();
		this.update();
	}

	public HashMap<String, dataStructure.Node> getGraph(){
		return _graph;
	}

	public JPanel getGraphPanel(){
		return panel1;
	}

	public JPanel getStatsPanel(){
		return panel2;
	}
	
	public void setStateLabel(String text){
		_ps.setNewLabel(text);
	}

	public ArrayList<dataStructure.Node> sortStartTimes(){
		if(_nodes.size()>0) {
			_nodes.clear();
		}
		for(dataStructure.Node n : _graph.values()){
			_nodes.add(n);
		}
		Collections.sort(_nodes, Node.startTimes());
		return _nodes;
	}

	public void setVisualModel(Scheduler model){
		_model = model;
	}

	public void setScheduleCount(int count){
		this.count = count;
	}

	@Override
	public void update() {

		vg.startTraversal( _nodes);
	}

	public void updateStats(double time) {

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
