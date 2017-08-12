import java.util.ArrayList;

public class StateSpaceNode extends Node {
	private boolean _completed;
	private int _processor;
	private double _startTime;
	private double _endTime;
	private ArrayList<StateSpaceNode> _stateParents;

	protected StateSpaceNode(String id, int cost, int processor){
		super(id, cost);
		_startTime = 0.0;
		_endTime = 0.0;
		// stores all the nodes we visited in the state space before this node was visited
		_stateParents = new ArrayList<StateSpaceNode>();
	}

	protected void addStateParents(StateSpaceNode node){
		_stateParents.add(node);
	}
	
	protected ArrayList<StateSpaceNode> getStateParents(){
		return _stateParents;
	}

	public boolean getCompleted() {
		return _completed;
	}

	public void setProcessor(int i){
		_processor = i;
	}

	public int getProcessor(){
		return _processor;
	}

	public void setStartTime(Double i){
		_startTime = i;
	}

	public double getStartTime(){
		return _startTime;
	}

	public void setEndTime(Double i){
		_endTime = i;
	}

	public double getEndTime(){
		return _endTime;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}
}