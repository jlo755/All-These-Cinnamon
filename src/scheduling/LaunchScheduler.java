package scheduling;
import java.io.IOException;
import org.jgrapht.ext.ImportException;

import inputParse.DotParser;
import visualisation.ProcessorScreen;

/**
 *  Launches the scheduler with the user's input graph and configurations.
 *
 */
public class LaunchScheduler {

	public static DotParser dotParser;
	public static int _noOfProcessors;
	public static String _fileName;
	public static DVScheduler _scheduler;
	
	public static void main(String[] args) throws IOException, ImportException {

		long startTime = System.nanoTime();
		_fileName=args[0];
		_noOfProcessors = Integer.parseInt(args[1]);
		boolean visualisation = false;
		int threads = 0;
		dotParser = new DotParser(_fileName);
		dotParser.parseInput();
		
		// Processes user input to do parallel computing/produce data visualization.
		if(args.length > 2) {
			for(int i = 2; i<args.length; i++) {
				if(args[i].equals("-v")) {
					visualisation = true;
				} else if(args[i].equals("-p")) {
					i++;
					threads = Integer.parseInt(args[i]);
				}
			}
		}
		
		// Retrieves a ScheduleFactory instance to process the scheduling according to the user's needs.
		ScheduleFactory factory = ScheduleFactory.getInstance();
		factory.setParallelize(threads);
		factory.setProcessorNumber(_noOfProcessors);
		if(visualisation && threads > 1) {
			_scheduler = factory.produceVisualScheduler();
			ProcessorScreen processor = new ProcessorScreen();
			processor.beginProcessing();
		}
		else if(visualisation) {
			ProcessorScreen processor = new ProcessorScreen();
			processor.beginProcessing();
		} else {
			Scheduler scheduler = factory.produceNonVisualScheduler();
			scheduler.schedule();
			scheduler.outputSolution();
		}
	}
	
	/**
	 * Sets the number of processors to use in the optimal task schedulng.
	 * @param processorCount
	 */
	public void setProcessor(int processorCount) {
		_noOfProcessors = processorCount;

	}
	
	/**
	 * Set the file name of the input graph.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		_fileName = fileName;

	}

}
