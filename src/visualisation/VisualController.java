package visualisation;


import dataStructure.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
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

/**
 * Created by DarthPenguin on 22/08/17.
 */
public class VisualController implements ScheduleListener {

   private VisualGraph vg;
   private HashMap<String, dataStructure.Node> _graph;
   private SingleGraph graph;
   private ArrayList<dataStructure.Node> _nodes = new ArrayList<>();
   private JPanel panel;
   private Scheduler _model;

   public VisualController(HashMap<String, dataStructure.Node> g, JPanel Panel){
      panel = Panel;
      _graph = g;
      vg = new VisualGraph(g, panel);
      graph = vg.graph;
      panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
      Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
      viewer.enableAutoLayout();
      ViewPanel viewPanel = viewer.addDefaultView(false);
      panel.add(viewPanel);
      _nodes = this.sortStartTimes();
   }


   public void setGraph(HashMap<String, dataStructure.Node> g){
      _graph = g;
      //vg = new VisualGraph(g, panel);
      ArrayList<Node> test = new ArrayList<Node>();
      Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
      viewer.enableAutoLayout();
      //panel.removeAll();
      ViewPanel viewPanel = viewer.addDefaultView(false);
      //panel.add(viewPanel);
      _nodes = sortStartTimes();
      this.update();
   }

   public HashMap<String, dataStructure.Node> getGraph(){
      return _graph;
   }

   public JPanel getPanel(){
      return panel;
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

   @Override
   public void update() {
      vg.startTraversal( _nodes);
   }
}
