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

public class LargeGraphSolutionTestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
	//Test for 10 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test10Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void test10Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=1800000)
	public void test10Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_10_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(50,(int)scheduler.getCurrentBestSolution());
	}

	
	//Test for 11 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test11Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(350,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=1800000)
	public void test11Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(227,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=1800000)
	public void test11Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_11_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(227,(int)scheduler.getCurrentBestSolution());
	}

	//Test for 13 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test13Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void test13Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		//change the integer to the bestFinalState
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void test13Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("OutTree-Balanced-MaxBf-3_Nodes_10_CCR_10.00_WeightType_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		//change the integer to the bestFinalState
		assertEquals(41,(int)scheduler.getCurrentBestSolution());
	}



	//this will have high task cost and low communication cost between nodes with 2 4 8 processors respectively
	@Test(timeout=1800000)
	public void testForkTreeLargeHighTaskLowCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeHighTaskLowCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(351,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeLargeHighTaskLowCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeHighTaskLowCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(214,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeLargeHighTaskLowCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeHighTaskLowCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(174,(int)scheduler.getCurrentBestSolution());
	}

	//this will have low task cost and high communication cost between nodes with 2 4 8 processors respectively
	@Test(timeout=1800000)
	public void testForkTreeLargeLowTaskHighCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeLowTaskHighCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(52,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeLargeLowTaskHighCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeLowTaskHighCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(52,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeLargeLowTaskHighCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeLargeLowTaskHighCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(52,(int)scheduler.getCurrentBestSolution());
	}

	//independent task of 7 nodes
	@Test(timeout=18000000)
	public void testIndependentTasksLarge() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Independent_Nodes_7_WeightType_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(238,(int)scheduler.getCurrentBestSolution());
	}
}
