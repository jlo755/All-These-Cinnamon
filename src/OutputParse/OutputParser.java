package OutputParse;

import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.DOTExporter;

import DataStructure.Node;
import InputParse.Edge;

public class OutputParser {
	private DOTExporter<Node, Edge> _dotExp;
	private VIDProvider para1;
	private VAttributeProvider para4;
	private EAttributeProvider para5;
	private DirectedAcyclicGraph graph;
	
	public OutputParser() {
		para1 = new VIDProvider();
		para4 = new VAttributeProvider();
		para5 = new EAttributeProvider();
		_dotExp = new DOTExporter<Node, Edge>(para1, null, null, para4, para5);
	}
	
	public void setGraph(DirectedAcyclicGraph g){
		graph = g;
	}
	
	public void outputDot() throws IOException {
		_dotExp.exportGraph(graph, new FileWriter("output.dot"));
	}

}
