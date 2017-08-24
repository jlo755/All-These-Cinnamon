package jUnit;

import java.io.FileNotFoundException;

import org.jgrapht.ext.ImportException;
import org.junit.Before;
import org.junit.Test;

import inputParse.DotParser;
import scheduling.Scheduler;

public class ExceptionTestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
	@Test
	public void TestEmptyFile() throws FileNotFoundException, ImportException {
		dotParser = new DotParser("EmptyFile.dot");
		dotParser.parseInput();
		
	}
}
