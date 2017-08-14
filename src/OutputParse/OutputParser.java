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

/**
 * OutputParser produces an output dot file from a given graph data structure.
 *
 */
public class OutputParser {
	private DOTExporter<Node, Edge> _dotExp;
	private VIDProvider _vidProv;
	private VAttributeProvider _vaProv;
	private EAttributeProvider _eaProv;
	private DirectedAcyclicGraph<Node, Edge> graph;
	private String _fileName;

	public OutputParser() {
		_vidProv = new VIDProvider();
		_vaProv = new VAttributeProvider();
		_eaProv = new EAttributeProvider();
		_dotExp = new DOTExporter<Node, Edge>(_vidProv, null, null, _vaProv, _eaProv);
	}

	/**
	 * Sets local DAG object
	 * @param g
	 */
	public void setGraph(DirectedAcyclicGraph<Node, Edge> g){
		graph = g;
	}

	/**
	 * Outputs a dot file from the given DAG.
	 * @throws IOException
	 */
	public void outputDot() throws IOException {
		_dotExp.exportGraph(graph, new FileWriter(_fileName.replaceAll(".dot", "Output")+".dot"));
	}

	/**
	 * Formats the input dot file to be reused for the output dot file in a correct format.
	 * - Adapted from StackOverflow user peeskillet's answer.
	 * @throws IOException
	 */
	public void formatFile() throws IOException{
		
		// Change the input file name to be output.
		File file = new File(_fileName.replaceAll(".dot", "Output")+".dot");
		
		// Append the file name with "output" string for display.
		Scanner scanner = new Scanner(file);      
		StringBuilder sbf = new StringBuilder();
		String line = scanner.nextLine();
		BufferedReader inputFile = new BufferedReader(new FileReader(_fileName));
		String nameOfGraph = inputFile.readLine();
		int indexOfApos = nameOfGraph.indexOf("\"");
		sbf.append(nameOfGraph.substring(0, indexOfApos+1)+"output"+nameOfGraph.substring(indexOfApos+1, nameOfGraph.length())+"\n");
		
		// Recreate file content without quotation marks
		while(scanner.hasNextLine()){  
			line = scanner.nextLine(); 

			String newLine = "";

			for (int i = 0; i < line.length(); i++){
				if (line.charAt(i) != '"') { 
					newLine += line.charAt(i);
				}
			}

			sbf.append(newLine+"\n");
		}
		
		// Write to the output dot file.
		file.delete();
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(_fileName.replaceAll(".dot", "Output")+".dot")));
		bwr.write(sbf.toString());
		bwr.flush();
		bwr.close();
		scanner.close();
		inputFile.close();
	}

	/**
	 * Sets the file name.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		_fileName = fileName;
		
	}
}
