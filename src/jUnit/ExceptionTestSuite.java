package jUnit;

import static org.junit.Assert.fail;

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
	public void TestNoFile() throws ImportException {
		try {
			dotParser = new DotParser("NotAFile.dot");
			dotParser.parseInput();
			fail("Should have failed: no file found");
		} catch (Exception e) {
			// Nothing here - just ignore the fact that the
			// exception occurred (since that’s the expected
			// behaviour in this case).
		}
	}
	
	@Test
	public void TestEmptyFile() throws ImportException {
		try {
			dotParser = new DotParser("EmptyFile.dot");
			dotParser.parseInput();
			fail("Should have failed: empty found");
		} catch (Exception e) {
			// Nothing here - just ignore the fact that the
			// exception occurred (since that’s the expected
			// behaviour in this case).
		}
	}
}
