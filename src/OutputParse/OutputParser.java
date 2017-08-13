package OutputParse;

import java.io.FileWriter;

import org.jgrapht.ext.DOTExporter;

import DataStructure.Node;
import InputParse.Edge;

public class OutputParser {
	private DOTExporter<Node, Edge> _dotExp;
	
	public OutputParser() {
		_dotExp = new DOTExporter<Node, Edge>(null, null, null);
	}
	
	public void outputDot() {
		//_dotExp.exportGraph(graph, new FileWriter("output.dot"));
	}

}
