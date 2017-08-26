package scheduling;
import javax.swing.SwingWorker;

import inputParse.DotParser;
import visualisation.VisualController;

public class ScheduleWorker extends SwingWorker{
	private Scheduler _scheduler;
	private int _noOfProcessors;
	private VisualController _vc;

	public ScheduleWorker(int noOfProcessors, VisualController vc){
		_noOfProcessors = noOfProcessors;
		_vc = vc;
		_scheduler = new Scheduler();
		_scheduler.setProcessorNumber(_noOfProcessors);
		_scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
		_scheduler.setVisualController(vc);
		vc.setVisualModel(_scheduler);
	}

	@Override
	protected Object doInBackground() throws Exception {
		//Thread.sleep(2000);
		// Parse the dot graph input and schedule an optimal solution.
		long startTime = System.nanoTime();
		//scheduler.setPanel(contentPane);
		_scheduler.schedule();
		// Output the solution in a dot format file.
		//outputSolution(_fileName);
		return null;
	}

}
