package jUnit;

import org.junit.Before;

import inputParse.DotParser;
import scheduling.Scheduler;

public class OtherTestSuite {
	Scheduler scheduler;
	DotParser dotParser;
	
	@Before
	public void initialise(){
		scheduler = new Scheduler();
	}
	
}
