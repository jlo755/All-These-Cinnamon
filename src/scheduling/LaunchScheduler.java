package scheduling;
import java.io.IOException;
import org.jgrapht.ext.ImportException;
import inputParse.DotParser;
import visualisation.ProcessorScreen;

/**
 * This class launches the scheduler to calculate an optimal multi-processor task scheduling solution.
 */
public class LaunchScheduler {

	public static DotParser dotParser;
	public static int _noOfProcessors;
	public static String _fileName;
	public static DVScheduler _scheduler;

	public static void main(String[] args) throws IOException, ImportException, NumberFormatException {

		_fileName=args[0];
		_noOfProcessors = Integer.parseInt(args[1]);
		boolean visualisation = false;
		int threads = 0;
		dotParser = new DotParser(_fileName);
		dotParser.parseInput();
		
		// Process user input arguments
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
		
		// Choose the relevant scheduler to run based on user input
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
	 * Set the number of processors the tasks can run on.
	 * @param processorCount
	 */
	public void setProcessor(int processorCount) {
		_noOfProcessors = processorCount;

	}
}
