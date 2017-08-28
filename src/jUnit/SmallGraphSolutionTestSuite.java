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

/**
 * Test class for testing correct output of optimal schedule for relatively
 * small graphs, produced under 30 minutes.
 *
 */
public class SmallGraphSolutionTestSuite {
	Scheduler scheduler;
	DotParser dotParser;

	@Before
	public void initialise() {
		scheduler = new Scheduler();
	}

	/**
	 * Tests a graph with 5 independent nodes.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testIndependentTasksSmall() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Independent_Nodes_5_WeightType_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(16, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 7 nodes on 2 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test7Nodes2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int) scheduler.getCurrentBestSolution(), 28);
	}

	/**
	 * Tests a graph with 7 nodes on 4 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test7Nodes4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int) scheduler.getCurrentBestSolution(), 22);
	}

	/**
	 * Tests a graph with 7 nodes on 8 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test7Nodes8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_7_OutTree.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals((int) scheduler.getCurrentBestSolution(), 22);
	}

	/**
	 * Tests a graph with 8 nodes on 2 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test8Nodes2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 8 nodes on 4 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test8Nodes4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 8 nodes on 8 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test8Nodes8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_8_Random.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(581, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 9 nodes on 2 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test9Nodes2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 9 nodes on 4 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test9Nodes4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests a graph with 9 nodes on 8 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void test9Nodes8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("Nodes_9_SeriesParallel.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(55, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with high task cost and low
	 * communication costs.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTree1Processor() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(1);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(218, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with high task cost and low
	 * communication costs on 2 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallHighTaskLowCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(128, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with high task cost and low
	 * communication costs on 4 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallHighTaskLowCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(89, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with high task cost and low
	 * communication costs on 8 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallHighTaskLowCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(89, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with low task cost and high
	 * communication costs on 2 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallLowTaskHighCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with low task cost and high
	 * communication costs on 4 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallLowTaskHighCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small fork tree with low task cost and high
	 * communication costs on 8 processors.
	 * 
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testForkTreeSmallLowTaskHighCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("ForkTreeSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(18, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with high task cost and small communication costs on 2 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallHighTaskLowCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(347, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with high task cost and small communication costs on 4 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallHighTaskLowCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(347, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with high task cost and small communication costs on 8 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallHighTaskLowCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallHighTaskLowCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(347, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with small task cost and high communication costs on 2 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallLowTaskHighCC2Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(2);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(31, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with high task cost and small communication costs on 4 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallLowTaskHighCC4Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(4);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(31, (int) scheduler.getCurrentBestSolution());
	}

	/**
	 * Tests correct output for a small 6-node fork tree with high task cost and small communication costs on 8 processors.
	 * @throws FileNotFoundException
	 * @throws ImportException
	 */
	@Test(timeout = 1800000)
	public void testStencilSmallLowTaskHighCC8Processors() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("StencilSmallLowTaskHighCC.dot");
		scheduler.setProcessorNumber(8);
		dotParser.parseInput();
		scheduler.provideTaskGraph(dotParser.getNodeMap());
		scheduler.schedule();
		assertEquals(31, (int) scheduler.getCurrentBestSolution());
	}

}
