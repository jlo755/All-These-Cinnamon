package visualisation;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.SwingWorker;


/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualGraph{
	SingleGraph graph;
	JPanel _panel;
	HashMap<String, dataStructure.Node> _taskGraph;
	protected String styleSheet =
			"node {" + "size: 40px; shape: circle; fill-color: white; stroke-mode: plain; stroke-color: black; text-alignment: center; text-size: 14;}"+
					"node.processor {fill-color: rgb(127,110,126); }" +
					"node.processor1 {fill-color: rgb(220,40,80); }"+
					"node.processor2 {fill-color: rgb(37,128,57); text-color: rgb(232,219,243); }" +
					"node.processor3 {fill-color: rgb(253,110,41); } " +
					"node.processor4 {fill-color: rgb(72,151,216); text-color: rgb(232,219,243); }" +
					"node.processor5 {fill-color: rgb(139,0,139); text-color: rgb(255,236,92); }" +
					"node.processor6 {fill-color: rgb(255,140,0);} " +
					"node.processor7 {fill-color: rgb(128,128,0); text-color: rgb(72,151,216); }"
					;


	public VisualGraph(HashMap<String, dataStructure.Node> g, JPanel panel){
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		_panel = panel;
		graph = new SingleGraph("MainGraph");
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.setStrict(false);
		graph.setAutoCreate(true);
		_taskGraph = g;
		for(dataStructure.Node node : g.values()) {
			for (dataStructure.Node parent : node.getParents().keySet()) {
				graph.addEdge("" + node.getID() + parent.getID(), "" + parent.getID(), "" + node.getID(), true);
			}
		}

		for (Node node : graph) {
			node.addAttribute("ui.label", node.getId() + "");
		}


	}

	public void setTaskGraph(HashMap<String, dataStructure.Node> graph){
		_taskGraph = graph;
	}

	public void startTraversal(ArrayList<dataStructure.Node> a, int numOfprocessors) {
		for (dataStructure.Node n : _taskGraph.values()) {
			_panel.setVisible(false);
			Node next = graph.getNode(n.getID());
			if(numOfprocessors > 7){
				next.setAttribute("ui.class", "processor");
			}
			else{
				if (n.getProcessor() == 1) {
					next.setAttribute("ui.class", "processor1");

				} else if (n.getProcessor() == 2) {
					next.setAttribute("ui.class", "processor2");

				} else if (n.getProcessor() == 3) {
					next.setAttribute("ui.class", "processor3");

				} else if (n.getProcessor() == 4) {
					next.setAttribute("ui.class", "processor4");

				} else if (n.getProcessor() == 5) {
					next.setAttribute("ui.class", "processor5");

				} else if (n.getProcessor() == 6) {
					next.setAttribute("ui.class", "processor6");

				} else if (n.getProcessor() == 7) {
					next.setAttribute("ui.class", "processor7");
				}
			}
			_panel.setVisible(true);
			//sleep();

		}
	}

		protected void sleep() {
			try { Thread.sleep(4); } catch (Exception e) {
				e.printStackTrace();
			}
	}



}
