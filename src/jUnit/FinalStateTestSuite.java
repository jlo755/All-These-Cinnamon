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

public class FinalStateTestSuite {
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
		assertEquals((int)scheduler.getCurrentBestSolution(), 28);
	}
	
	@Test(timeout=18000000)
	public void test7Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int)scheduler.getCurrentBestSolution(), 22);
	}
	
	
	@Test(timeout=18000000)
	public void test7Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int)scheduler.getCurrentBestSolution(), 22);
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
	
	
}
