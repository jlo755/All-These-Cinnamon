package jUnit;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;

import org.jgrapht.ext.ImportException;
import org.junit.Before;
import org.junit.Test;

import dataStructure.Node;
import inputParse.DotParser;
import scheduling.Scheduler;

public class TestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
	//Test for 7 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test7Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(28,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=18000000)
	public void test7Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(22,(int)scheduler.getCurrentBestSolution());
	}
	
	
	@Test(timeout=18000000)
	public void test7Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(22,(int)scheduler.getCurrentBestSolution());
	}

	
	//Test for 8 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test8Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=18000000)
	public void test8Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=18000000)
	public void test8Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}
	
	
	//Test for 9 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test9Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=18000000)
	public void test9Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=18000000)
	public void test9Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}

	
	//Test for 10 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test10Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=18000000)
	public void test10Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=18000000)
	public void test10Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}

	
	//Test for 11 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test11Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(350,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=18000000)
	public void test11Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(227,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=18000000)
	public void test11Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(227,(int)scheduler.getCurrentBestSolution());
	}

	//Test for 13 nodes and processors of 2 4 and 8
	@Test(timeout=18000000)
	public void test13Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=18000000)
	public void test13Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		//change the integer to the bestFinalState
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=18000000)
	public void test13Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		//change the integer to the bestFinalState
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}
	
	//Tests for methods in Node class
	@Test
	public void test7NodesID() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		//check if all node IDs are correct
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		Object[] nodes = nodeMap.values().toArray();
		String firstID = ((Node) nodes[0]).getID();
		assertEquals("0", firstID); 
	}
	
	@Test
	public void test7NodesCost() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		//check if all node costs are correct
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		Object[] nodes = nodeMap.values().toArray();
		int cost = ((Node) nodes[0]).getCost();
		assertEquals("5", cost); 
	}
	
	@Test
	public void test7NodesChildren() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		
		//check the node "0" children
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		HashMap<Node, Double> children = nodeMap.get("0").getChildren();
		assertEquals(3, children.size()); 
	}
	
	@Test
	public void test7NodesParents() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		dotParser.parseInput();
		
		//check the node "6" parents
		HashMap<String, Node> nodeMap = dotParser.getNodeMap();
		HashMap<Node, Double> parents = nodeMap.get("6").getParents();
		assertEquals(1, parents.size());
	}
}
