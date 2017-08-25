package scheduling;//####[1]####
//####[1]####
import java.util.ArrayList;//####[3]####
import java.util.Comparator;//####[4]####
import java.util.EmptyStackException;//####[5]####
import java.util.HashMap;//####[6]####
import java.util.LinkedHashMap;//####[7]####
import java.util.PriorityQueue;//####[8]####
import java.util.Queue;//####[9]####
import java.util.Stack;//####[10]####
import java.util.Set;//####[11]####
import java.util.HashSet;//####[12]####
import dataStructure.Node;//####[14]####
import pt.runtime.TaskIDGroup;//####[15]####
//####[15]####
//-- ParaTask related imports//####[15]####
import pt.runtime.*;//####[15]####
import java.util.concurrent.ExecutionException;//####[15]####
import java.util.concurrent.locks.*;//####[15]####
import java.lang.reflect.*;//####[15]####
import pt.runtime.GuiThread;//####[15]####
import java.util.concurrent.BlockingQueue;//####[15]####
import java.util.ArrayList;//####[15]####
import java.util.List;//####[15]####
//####[15]####
/**
 * 
 * This class produces an optimal solution to a multi-processor scheduling
 * problem. This is based on an input graph representing a series of tasks costs
 * and their dependencies, as well as the number of processors specified.
 *
 *///####[23]####
public class ParallelScheduler {//####[24]####
    static{ParaTask.init();}//####[24]####
    /*  ParaTask helper method to access private/protected slots *///####[24]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[24]####
        if (m.getParameterTypes().length == 0)//####[24]####
            m.invoke(instance);//####[24]####
        else if ((m.getParameterTypes().length == 1))//####[24]####
            m.invoke(instance, arg);//####[24]####
        else //####[24]####
            m.invoke(instance, arg, interResult);//####[24]####
    }//####[24]####
//####[26]####
    private double currentBestSolution;//####[26]####
//####[27]####
    private FinalState bestState;//####[27]####
//####[28]####
    private LinkedHashMap<String, Node> nodeMap;//####[28]####
//####[29]####
    private double sumAdding = 0;//####[29]####
//####[30]####
    private double totalTaskTime = 0;//####[30]####
//####[31]####
    private int _numProcessors;//####[31]####
//####[32]####
    private ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();//####[32]####
//####[33]####
    private double time = 0;//####[33]####
//####[34]####
    private Set<PartialSchedule> prevPartialSchedules;//####[34]####
//####[39]####
    /**
	 * Initialize the best solution so far to infinity on starting.
	 *///####[39]####
    public ParallelScheduler() {//####[39]####
        currentBestSolution = Double.POSITIVE_INFINITY;//####[40]####
        prevPartialSchedules = new HashSet<PartialSchedule>();//####[41]####
    }//####[42]####
//####[47]####
    /**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 *///####[47]####
    public void schedule() {//####[47]####
        for (Node n : nodeMap.values()) //####[48]####
        {//####[48]####
            totalTaskTime += n.getCost();//####[49]####
            n.setBottomLevel(findMaxBottomLevel(nodeMap, n.getID()));//####[50]####
        }//####[51]####
        for (Node n : nodeMap.values()) //####[56]####
        {//####[56]####
            if (n.getParents().isEmpty()) //####[59]####
            {//####[59]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[61]####
                schedule.decideIndex();//####[62]####
                schedule.solve(n, 1);//####[63]####
                schedules.add(schedule);//####[64]####
            }//####[69]####
        }//####[70]####
        dfs();//####[71]####
        System.out.println(time);//####[73]####
        System.out.println("Best Solution: " + currentBestSolution);//####[74]####
    }//####[75]####
//####[79]####
    private static volatile Method __pt__dfsTask_StackPartialSchedule_method = null;//####[79]####
    private synchronized static void __pt__dfsTask_StackPartialSchedule_ensureMethodVarSet() {//####[79]####
        if (__pt__dfsTask_StackPartialSchedule_method == null) {//####[79]####
            try {//####[79]####
                __pt__dfsTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__dfsTask", new Class[] {//####[79]####
                    Stack.class//####[79]####
                });//####[79]####
            } catch (Exception e) {//####[79]####
                e.printStackTrace();//####[79]####
            }//####[79]####
        }//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(Stack<PartialSchedule> scheduleStack) {//####[79]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[79]####
        return dfsTask(scheduleStack, new TaskInfo());//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[79]####
        // ensure Method variable is set//####[79]####
        if (__pt__dfsTask_StackPartialSchedule_method == null) {//####[79]####
            __pt__dfsTask_StackPartialSchedule_ensureMethodVarSet();//####[79]####
        }//####[79]####
        taskinfo.setParameters(scheduleStack);//####[79]####
        taskinfo.setMethod(__pt__dfsTask_StackPartialSchedule_method);//####[79]####
        taskinfo.setInstance(this);//####[79]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[79]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[79]####
        return dfsTask(scheduleStack, new TaskInfo());//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[79]####
        // ensure Method variable is set//####[79]####
        if (__pt__dfsTask_StackPartialSchedule_method == null) {//####[79]####
            __pt__dfsTask_StackPartialSchedule_ensureMethodVarSet();//####[79]####
        }//####[79]####
        taskinfo.setTaskIdArgIndexes(0);//####[79]####
        taskinfo.addDependsOn(scheduleStack);//####[79]####
        taskinfo.setParameters(scheduleStack);//####[79]####
        taskinfo.setMethod(__pt__dfsTask_StackPartialSchedule_method);//####[79]####
        taskinfo.setInstance(this);//####[79]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[79]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[79]####
        return dfsTask(scheduleStack, new TaskInfo());//####[79]####
    }//####[79]####
    private TaskIDGroup<Void> dfsTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[79]####
        // ensure Method variable is set//####[79]####
        if (__pt__dfsTask_StackPartialSchedule_method == null) {//####[79]####
            __pt__dfsTask_StackPartialSchedule_ensureMethodVarSet();//####[79]####
        }//####[79]####
        taskinfo.setQueueArgIndexes(0);//####[79]####
        taskinfo.setIsPipeline(true);//####[79]####
        taskinfo.setParameters(scheduleStack);//####[79]####
        taskinfo.setMethod(__pt__dfsTask_StackPartialSchedule_method);//####[79]####
        taskinfo.setInstance(this);//####[79]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[79]####
    }//####[79]####
    public void __pt__dfsTask(Stack<PartialSchedule> scheduleStack) {//####[79]####
        while (!scheduleStack.isEmpty()) //####[80]####
        {//####[80]####
            dfs(scheduleStack);//####[81]####
        }//####[82]####
    }//####[83]####
//####[83]####
//####[96]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[96]####
    public void dfs() {//####[96]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[99]####
        for (PartialSchedule schedule : schedules) //####[100]####
        {//####[100]####
            scheduleStack.add(schedule);//####[101]####
        }//####[102]####
        TaskIDGroup g = new TaskIDGroup(2);//####[103]####
        for (int i = 0; i < 2; i++) //####[105]####
        {//####[105]####
            TaskID id = dfsTask(scheduleStack);//####[106]####
            g.add(id);//####[107]####
        }//####[108]####
        try {//####[110]####
            g.waitTillFinished();//####[111]####
        } catch (Exception e) {//####[112]####
            e.printStackTrace();//####[113]####
        }//####[114]####
    }//####[116]####
//####[118]####
    private void dfs(Stack<PartialSchedule> scheduleStack) {//####[118]####
        try {//####[119]####
            PartialSchedule schedule = scheduleStack.pop();//####[120]####
            ArrayList<String> reachable = schedule.getReachable();//####[121]####
            for (String s : reachable) //####[122]####
            {//####[122]####
                if (schedule.startTimeZeroProcessors() > 1) //####[123]####
                {//####[123]####
                    Node n = nodeMap.get(s);//####[124]####
                    boolean discovered = false;//####[125]####
                    for (int i = 1; i <= _numProcessors; i++) //####[126]####
                    {//####[126]####
                        double time = schedule.getProcessorTime(i);//####[127]####
                        if (time == 0.0 && !discovered) //####[128]####
                        {//####[128]####
                            discovered = true;//####[129]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[130]####
                            childSchedule.solve(n, i);//####[131]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[132]####
                            if (maxHeuristic < currentBestSolution) //####[133]####
                            {//####[133]####
                                scheduleStack.push(childSchedule);//####[134]####
                            }//####[135]####
                        } else if (time != 0.0) //####[136]####
                        {//####[136]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[137]####
                            childSchedule.solve(n, i);//####[138]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[139]####
                            if (maxHeuristic < currentBestSolution) //####[140]####
                            {//####[140]####
                                scheduleStack.push(childSchedule);//####[141]####
                            }//####[142]####
                        } else {//####[143]####
                        }//####[145]####
                    }//####[146]####
                } else {//####[148]####
                    for (int i = 1; i <= _numProcessors; i++) //####[149]####
                    {//####[149]####
                        Node n = nodeMap.get(s);//####[150]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[152]####
                        childSchedule.solve(n, i);//####[155]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[156]####
                        if (maxHeuristic < currentBestSolution) //####[157]####
                        {//####[157]####
                            scheduleStack.push(childSchedule);//####[158]####
                        }//####[159]####
                    }//####[160]####
                }//####[161]####
            }//####[162]####
            if (reachable.isEmpty()) //####[163]####
            {//####[163]####
                double[] solution = schedule.getEndTimes();//####[165]####
                double endTime = 0;//####[166]####
                for (int i = 0; i < solution.length; i++) //####[167]####
                {//####[167]####
                    if (solution[i] > endTime) //####[168]####
                    {//####[168]####
                        endTime = solution[i];//####[169]####
                    }//####[170]####
                }//####[171]####
                if (endTime < currentBestSolution) //####[172]####
                {//####[172]####
                    currentBestSolution = endTime;//####[173]####
                }//####[174]####
            }//####[175]####
        } catch (EmptyStackException e) {//####[176]####
        }//####[178]####
    }//####[179]####
//####[181]####
    public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode) {//####[181]####
        Stack<String> s = new Stack<String>();//####[182]####
        s.add(initialNode);//####[183]####
        double startTime = graph.get(initialNode).getStartTime();//####[184]####
        graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[185]####
        double max = 0;//####[186]####
        while (!s.isEmpty()) //####[187]####
        {//####[187]####
            Node node = graph.get(s.pop());//####[188]####
            if (node.getParents().keySet().contains(graph.get(initialNode))) //####[189]####
            {//####[189]####
                graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[190]####
            }//####[191]####
            for (Node child : node.getChildren().keySet()) //####[192]####
            {//####[192]####
                s.push(child.getID());//####[193]####
            }//####[194]####
            if (node.getID() != initialNode) //####[195]####
            {//####[195]####
                double test = 0;//####[196]####
                for (Node parent : node.getParents().keySet()) //####[197]####
                {//####[197]####
                    if (test < parent.getOptimalBottomLevel()) //####[198]####
                    {//####[198]####
                        test = parent.getOptimalBottomLevel();//####[199]####
                    }//####[200]####
                }//####[201]####
                node.setOptimalBottomLevel(test + node.getCost());//####[202]####
            }//####[203]####
        }//####[204]####
        for (Node n : graph.values()) //####[205]####
        {//####[205]####
            if (n.getOptimalBottomLevel() > max && n.getChildren().isEmpty()) //####[206]####
            {//####[206]####
                max = n.getOptimalBottomLevel();//####[207]####
            }//####[208]####
            n.setOptimalBottomLevel(0.0);//####[209]####
        }//####[210]####
        return max;//####[212]####
    }//####[213]####
//####[215]####
    public double findStartingOptimalBranch(HashMap<String, Node> graph) {//####[215]####
        Queue<Node> optimalBranch = new PriorityQueue<Node>();//####[216]####
        for (Node n : graph.values()) //####[217]####
        {//####[217]####
            optimalBranch.add(n);//####[218]####
        }//####[219]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[220]####
        PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[221]####
        schedule.decideIndex();//####[222]####
        scheduleStack.add(schedule);//####[224]####
        while (!optimalBranch.isEmpty()) //####[225]####
        {//####[225]####
            Node n = optimalBranch.poll();//####[227]####
            PartialSchedule scheduleOnStack = scheduleStack.pop();//####[228]####
            PartialSchedule[] partialSchedules = new PartialSchedule[_numProcessors];//####[229]####
            for (int i = 1; i <= _numProcessors; i++) //####[230]####
            {//####[230]####
                PartialSchedule childSchedule = scheduleOnStack.makeChildSchedule();//####[231]####
                childSchedule.solve(n, i);//####[232]####
                partialSchedules[i - 1] = childSchedule;//####[233]####
            }//####[234]####
            int minIndex = 0;//####[235]####
            Double min = Double.POSITIVE_INFINITY;//####[236]####
            for (int i = 1; i <= _numProcessors; i++) //####[237]####
            {//####[237]####
                PartialSchedule scheduleToCompare = partialSchedules[i - 1];//####[238]####
                if (min > scheduleToCompare.getNodeEndTime(n)) //####[239]####
                {//####[239]####
                    min = scheduleToCompare.getNodeEndTime(n);//####[240]####
                    minIndex = i;//####[241]####
                }//####[242]####
            }//####[243]####
            scheduleStack.push(partialSchedules[minIndex - 1]);//####[244]####
            if (optimalBranch.isEmpty()) //####[245]####
            {//####[245]####
                currentBestSolution = min;//####[246]####
                break;//####[247]####
            }//####[248]####
        }//####[249]####
        return 0.0;//####[250]####
    }//####[251]####
//####[258]####
    /**
	 * Returns the currentBestSolution field.
	 * 
	 * @return
	 *///####[258]####
    public double getCurrentBestSolution() {//####[258]####
        return currentBestSolution;//####[259]####
    }//####[260]####
//####[267]####
    /**
	 * Returns the bestState field.
	 * 
	 * @return
	 *///####[267]####
    public FinalState getBestState() {//####[267]####
        return bestState;//####[268]####
    }//####[269]####
//####[271]####
    public void setProcessorNumber(int processors) {//####[271]####
        _numProcessors = processors;//####[272]####
    }//####[273]####
//####[280]####
    /**
	 * Sets the bestState field.
	 * 
	 * @param newBestState
	 *///####[280]####
    public void setBestState(FinalState newBestState) {//####[280]####
        bestState = newBestState;//####[281]####
    }//####[282]####
//####[290]####
    /**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 * 
	 * @param taskGraph
	 *///####[290]####
    public void provideTaskGraph(LinkedHashMap<String, Node> taskGraph) {//####[290]####
        nodeMap = taskGraph;//####[291]####
        bestState = new FinalState(taskGraph);//####[292]####
    }//####[294]####
}//####[294]####
