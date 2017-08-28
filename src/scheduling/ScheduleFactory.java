package scheduling;

/**
 * 
 * Factory class to instantiate a singleton instance of a Schedule object.
 *
 */
public class ScheduleFactory {
	private static ScheduleFactory _factory;
	private int _parallelize;
	private int _processors;
	private boolean _visualise;

	private ScheduleFactory() {
	}

	/**
	 * Returns the single instance of the Scheduler.
	 * @return
	 */
	public static ScheduleFactory getInstance() {
		if(_factory == null) {
			_factory = new ScheduleFactory();
			_factory.setParallelize(1);
			_factory.setProcessorNumber(1);
			_factory.setVisualise(false);
		}
		return _factory;
	}

	/**
	 * Sets the number of cores to run the scheduling on.
	 * @param parallelize
	 */
	public void setParallelize(int parallelize) {
		_parallelize = parallelize;
	}
	
	/**
	 * Set the number of processors the tasks can be allocated to.
	 * @param processors
	 */
	public void setProcessorNumber(int processors) {
		_processors = processors;
	}
	
	/**
	 * Turn visualization on or off.
	 * @param visualise
	 */
	public void setVisualise(boolean visualise) {
		_visualise = visualise;
	}

	/**
	 * Produces a non-visualization scheduler object.
	 * @return
	 */
	public Scheduler produceNonVisualScheduler() {
		if(_parallelize > 1) {
			ParallelScheduler scheduler = new ParallelScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			scheduler.setThreads(_parallelize);
			return scheduler;
		} else {
			Scheduler scheduler = new Scheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		}
	}
	
	/**
	 * Produces a visualization scheduler object.
	 * @return
	 */
	public DVScheduler produceVisualScheduler() {
		if(_parallelize > 1) {
			DVScheduler scheduler = new ParallelDVScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			scheduler.setThreads(_parallelize);
			return scheduler;
		} else {
			DVScheduler scheduler = new DVScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		}
	}

	/**
	 * Get number of cores the scheduling is running on.
	 * @return
	 */
	public int getParallelise() {
		return _parallelize;
	}
}
