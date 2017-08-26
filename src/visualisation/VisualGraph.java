package visualisation;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

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
			"node {" + "size: 40px; shape: circle; fill-color: white; stroke-mode: plain; stroke-color: black; text-alignment: center;}"+
					"node.processor1 { fill-color: red; }"+
					"node.processor2 { fill-color: green; }" +
					"node.processor3 { fill-color: yellow; } " +
					"node.processor4 {fill-color: blue; }" +
					"node.processor5 {fill-color: rgb(139,0,139); }" +
					"node.processor6 {fill-color: rgb(255,140,0); }" +
					"node.processor7 {fill-color: rgb(128,128,0); }"
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
				graph.addEdge("" + node.getID() + parent.getID(), "" + node.getID(), "" + parent.getID());
			}
		}

		for (Node node : graph) {
			node.addAttribute("ui.label", "  " + node.getId() + "  ");
		}


	}

	public void setTaskGraph(HashMap<String, dataStructure.Node> graph){
		_taskGraph = graph;
	}

	public void startTraversal(ArrayList<dataStructure.Node> a){
		for(dataStructure.Node n : _taskGraph.values()) {
			Node next = graph.getNode(n.getID());
			if (n.getProcessor() == 1) {
				next.setAttribute("ui.class", "processor1");

			} else if (n.getProcessor() == 2) {
				next.setAttribute("ui.class", "processor2");

			} else if (n.getProcessor() == 3) {
				next.setAttribute("ui.class", "processor3");

			} else if(n.getProcessor() == 4) {
				next.setAttribute("ui.class", "processor4");

			}else if(n.getProcessor() == 5) {
				next.setAttribute("ui.class", "processor5");

			} else if(n.getProcessor() == 6) {
				next.setAttribute("ui.class", "processor6");

			} else if(n.getProcessor() == 7) {
				next.setAttribute("ui.class", "processor7");
			}

		}
	}



}
