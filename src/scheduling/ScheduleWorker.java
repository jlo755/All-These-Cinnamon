package scheduling;
import javax.swing.SwingWorker;

import inputParse.DotParser;
import visualisation.VisualController;

/**
 * SwingWorker implementation that handles the updating of GUI representations of schedule solution calculation.
 */
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

	/**
	 * Calculates optimal schedules in the background while GUI updates.
	 */
	@Override
	protected Object doInBackground() throws Exception {
		_scheduler.schedule();
		return null;
	}

}
