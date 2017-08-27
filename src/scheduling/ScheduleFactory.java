package scheduling;

public class ScheduleFactory {
	private static ScheduleFactory _factory;
	private int _parallelize;
	private int _processors;
	private boolean _visualise;

	private ScheduleFactory() {
	}

	public static ScheduleFactory getInstance() {
		if(_factory == null) {
			_factory = new ScheduleFactory();
			_factory.setParallelize(1);
			_factory.setProcessorNumber(1);
			_factory.setVisualise(false);
		}
		return _factory;
	}

	public void setParallelize(int parallelize) {
		_parallelize = parallelize;
	}
	
	public void setProcessorNumber(int processors) {
		_processors = processors;
	}
	
	public void setVisualise(boolean visualise) {
		_visualise = visualise;
	}

	public Scheduler produceNonVisualScheduler() {
		if(_parallelize > 1) {
			ParallelScheduler scheduler = new ParallelScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		} else {
			Scheduler scheduler = new Scheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		}
	}
	
	public DVScheduler produceVisualScheduler() {
		if(_parallelize > 1) {
			DVScheduler scheduler = new ParallelDVScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		} else {
			DVScheduler scheduler = new DVScheduler();
			scheduler.setProcessorNumber(_processors);
			scheduler.provideTaskGraph(LaunchScheduler.dotParser.getNodeMap());
			return scheduler;
		}
	}

	public int getParallelise() {
		return _parallelize;
	}
}
