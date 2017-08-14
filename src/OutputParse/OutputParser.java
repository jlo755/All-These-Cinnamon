package OutputParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.DOTExporter;

import DataStructure.Node;
import InputParse.Edge;

public class OutputParser {
	private DOTExporter<Node, Edge> _dotExp;
	private VIDProvider para1;
	private VAttributeProvider para4;
	private EAttributeProvider para5;
	private DirectedAcyclicGraph<Node, Edge> graph;
	private String _fileName;

	public OutputParser() {
		para1 = new VIDProvider();
		para4 = new VAttributeProvider();
		para5 = new EAttributeProvider();
		_dotExp = new DOTExporter<Node, Edge>(para1, null, null, para4, para5);
	}

	public void setGraph(DirectedAcyclicGraph<Node, Edge> g){
		graph = g;
	}

	public void outputDot() throws IOException {
		_dotExp.exportGraph(graph, new FileWriter(_fileName.replaceAll(".dot", "Output")+".dot"));
	}

	public void formatFile() throws IOException{
		File file = new File(_fileName.replaceAll(".dot", "Output")+".dot");
		Scanner scanner = new Scanner(file);       // create scanner to read
		StringBuilder sbf = new StringBuilder();
		String line = scanner.nextLine();
		BufferedReader inputFile = new BufferedReader(new FileReader(_fileName));
		String nameOfGraph = inputFile.readLine();
		int indexOfApos = nameOfGraph.indexOf("\"");
		sbf.append(nameOfGraph.substring(0, indexOfApos+1)+"output"+nameOfGraph.substring(indexOfApos+1, nameOfGraph.length())+"\n");
		while(scanner.hasNextLine()){  // while there is a next line
			line = scanner.nextLine();  // line = that next line

			// do something with that line
			String newLine = "";

			// replace a character
			for (int i = 0; i < line.length(); i++){
				if (line.charAt(i) != '"') {  // or anything other character you chose
					newLine += line.charAt(i);
				}
			}

			// print to another file.
			sbf.append(newLine+"\n");
		}
		file.delete();
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(_fileName.replaceAll(".dot", "Output")+".dot")));
		bwr.write(sbf.toString());
		bwr.flush();
		bwr.close();
		scanner.close();
		inputFile.close();

	}

	public void setFileName(String fileName) {
		_fileName = fileName;
		
	}
}
