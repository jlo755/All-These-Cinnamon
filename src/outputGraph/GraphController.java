package outputGraph;


import dataStructure.Node;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class GraphController {

	private VisualGraph vg;
	private HashMap<String, dataStructure.Node> _graph;
	private SingleGraph graph;
	private ArrayList<dataStructure.Node> _nodes;

	public GraphController(){

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createGraph(HashMap<String, dataStructure.Node> g, ArrayList<dataStructure.Node> a1, JPanel panel){

		vg = new VisualGraph(g);
		graph = vg.graph;
		_graph = g;
		_nodes = a1;
		JPanel contentPane = (JPanel) panel.getParent();
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(contentPane);
        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = viewer.addDefaultView(false);
        //panel.add(viewPanel);
        //frame.add(panel);

	}

	public void updateGraph(){

		vg.startTraversal(_nodes.get(0).getID(), _nodes,graph);

	}


}
