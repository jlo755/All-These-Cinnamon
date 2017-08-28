package testSuites;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.jgrapht.ext.ImportException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import dataStructure.Node;
import inputParse.DotParser;
import scheduling.Scheduler;

public class NodeTestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
	@Test
	public void testEmptyInputFile() throws FileNotFoundException {
		try {
			dotParser = new DotParser("EmptyFile.dot");
			dotParser.parseInput();
			fail("Should have failed: empty file");
		} catch (ImportException e){
			//this should fail so the exception will not be caught
		}
	}
	
	@Test
	public void testNoInputFile() throws ImportException {
		try {
			dotParser = new DotParser("NotaFile.dot");
			dotParser.parseInput();
			fail("Should have failed: no file");
		} catch (FileNotFoundException e){
			//this should fail so the exception will not be caught
		}
	}
	

	@Test
	public void test7NodesID() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		//check if all node IDs are correct
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		Object[] nodes = nodeMap.values().toArray();
		String firstID = ((Node) nodes[0]).getID();
		String secondID = ((Node) nodes[1]).getID();
		String lastID = ((Node) nodes[6]).getID();
		assertEquals(firstID, "0");
		assertEquals(secondID, "1");
		assertEquals(lastID, "6");
	}
	
	@Test
	public void test7NodesCost() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		//check if all node costs are correct
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		Object[] nodes = nodeMap.values().toArray();
		int firstCost = ((Node) nodes[0]).getCost();
		int secondCost = ((Node) nodes[1]).getCost();
		int lastCost = ((Node) nodes[6]).getCost();
		assertEquals(firstCost, 5);
		assertEquals(secondCost, 6);
		assertEquals(lastCost, 7);
	}
	
	@Test
	public void test7NodesChildren() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		
		//check the node "0" children
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		HashMap<Node, Double> children = nodeMap.get("0").getChildren();
		assertEquals(children.size(), 3); 
	}
	
	@Test
	public void test7NodesParents() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		
		//check the node "6" parents
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		HashMap<Node, Double> parents = nodeMap.get("6").getParents();
		assertEquals(parents.size(), 1);
	}
}
