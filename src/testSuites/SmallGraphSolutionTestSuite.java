package testSuites;

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

public class SmallGraphSolutionTestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
	//independent task of 5 nodes
	@Test(timeout=1800000)
	public void testIndependentTasksSmall() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Independent_Nodes_5_WeightType_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(16,(int)scheduler.getCurrentBestSolution());
	}

	//Test for 7 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test7Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int)scheduler.getCurrentBestSolution(), 28);
	}
	
	@Test(timeout=1800000)
	public void test7Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int)scheduler.getCurrentBestSolution(), 22);
	}
	
	
	@Test(timeout=1800000)
	public void test7Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int)scheduler.getCurrentBestSolution(), 22);
	}

	
	//Test for 8 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test8Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void test8Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=1800000)
	public void test8Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581,(int)scheduler.getCurrentBestSolution());
	}
	
	
	//Test for 9 nodes and processors of 2 4 and 8
	@Test(timeout=1800000)
	public void test9Nodes2Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void test9Nodes4Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}


	@Test(timeout=1800000)
	public void test9Nodes8Processors() throws FileNotFoundException, ImportException{
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55,(int)scheduler.getCurrentBestSolution());
	}
	
	//this checks the correct solution for one processor
	@Test(timeout=1800000)
	public void testForkTree1Processor() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(1);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(218,(int)scheduler.getCurrentBestSolution());
	}

	//this will have high task cost and low communication cost between nodes with 2 4 8 processors respectively
	@Test(timeout=1800000)
	public void testForkTreeSmallHighTaskLowCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(128,(int)scheduler.getCurrentBestSolution());
	}
	
	@Test(timeout=1800000)
	public void testForkTreeSmallHighTaskLowCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(89,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeSmallHighTaskLowCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(89,(int)scheduler.getCurrentBestSolution());
	}

	//this will have low task cost and high communication cost between nodes with 2 4 8 processors respectively
	@Test(timeout=1800000)
	public void testForkTreeSmallLowTaskHighCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeSmallLowTaskHighCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18,(int)scheduler.getCurrentBestSolution());
	}

	@Test(timeout=1800000)
	public void testForkTreeSmallLowTaskHighCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18,(int)scheduler.getCurrentBestSolution());
	}

	//this will have high task cost and low communication cost between nodes with 2 4 8 processors respectively
		//contains 6 nodes
		@Test(timeout=1800000)
		public void testStencilSmallHighTaskLowCC2Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
			scheduler.setProcessorNumber(2);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(347,(int)scheduler.getCurrentBestSolution());
		}

		@Test(timeout=1800000)
		public void testStencilSmallHighTaskLowCC4Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
			scheduler.setProcessorNumber(4);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(347,(int)scheduler.getCurrentBestSolution());
		}

		@Test(timeout=1800000)
		public void testStencilSmallHighTaskLowCC8Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
			scheduler.setProcessorNumber(8);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(347,(int)scheduler.getCurrentBestSolution());
		}

		//this will have low task cost and high communication cost between nodes with 2 4 8 processors respectively
		@Test(timeout=1800000)
		public void testStencilSmallLowTaskHighCC2Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
			scheduler.setProcessorNumber(2);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(31,(int)scheduler.getCurrentBestSolution());
		}

		@Test(timeout=1800000)
		public void testStencilSmallLowTaskHighCC4Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
			scheduler.setProcessorNumber(4);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(31,(int)scheduler.getCurrentBestSolution());
		}

		@Test(timeout=1800000)
		public void testStencilSmallLowTaskHighCC8Processors() throws FileNotFoundException, ImportException {
			dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
			scheduler.setProcessorNumber(8);
			dotParser.parseInput();
			scheduler.provideTaskGraph(dotParser.getNodeMap());
			scheduler.schedule();
			assertEquals(31,(int)scheduler.getCurrentBestSolution());
		}



}
