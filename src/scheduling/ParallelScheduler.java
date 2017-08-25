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
import dataStructure.Node;//####[12]####
import pt.runtime.TaskIDGroup;//####[13]####
//####[13]####
//-- ParaTask related imports//####[13]####
import pt.runtime.*;//####[13]####
import java.util.concurrent.ExecutionException;//####[13]####
import java.util.concurrent.locks.*;//####[13]####
import java.lang.reflect.*;//####[13]####
import pt.runtime.GuiThread;//####[13]####
import java.util.concurrent.BlockingQueue;//####[13]####
import java.util.ArrayList;//####[13]####
import java.util.List;//####[13]####
//####[13]####
/**
 * 
 * This class produces an optimal solution to a multi-processor scheduling
 * problem. This is based on an input graph representing a series of tasks costs
 * and their dependencies, as well as the number of processors specified.
 *
 *///####[21]####
public class ParallelScheduler {//####[22]####
    static{ParaTask.init();}//####[22]####
    /*  ParaTask helper method to access private/protected slots *///####[22]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[22]####
        if (m.getParameterTypes().length == 0)//####[22]####
            m.invoke(instance);//####[22]####
        else if ((m.getParameterTypes().length == 1))//####[22]####
            m.invoke(instance, arg);//####[22]####
        else //####[22]####
            m.invoke(instance, arg, interResult);//####[22]####
    }//####[22]####
//####[24]####
    private double currentBestSolution;//####[24]####
//####[25]####
    private FinalState bestState;//####[25]####
//####[26]####
    private LinkedHashMap<String, Node> nodeMap;//####[26]####
//####[27]####
    private double sumAdding = 0;//####[27]####
//####[28]####
    private double totalTaskTime = 0;//####[28]####
//####[29]####
    private int _numProcessors;//####[29]####
//####[30]####
    private ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();//####[30]####
//####[31]####
    private double time = 0;//####[31]####
//####[36]####
    /**
	 * Initialize the best solution so far to infinity on starting.
	 *///####[36]####
    public ParallelScheduler() {//####[36]####
        currentBestSolution = Double.POSITIVE_INFINITY;//####[37]####
    }//####[38]####
//####[43]####
    /**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 *///####[43]####
    public void schedule() {//####[43]####
        for (Node n : nodeMap.values()) //####[44]####
        {//####[44]####
            totalTaskTime += n.getCost();//####[45]####
            n.setBottomLevel(findMaxBottomLevel(nodeMap, n.getID()));//####[46]####
        }//####[47]####
        for (Node n : nodeMap.values()) //####[52]####
        {//####[52]####
            if (n.getParents().isEmpty()) //####[55]####
            {//####[55]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[57]####
                schedule.decideIndex();//####[58]####
                schedule.solve(n, 1);//####[59]####
                schedules.add(schedule);//####[60]####
            }//####[65]####
        }//####[66]####
        dfs();//####[67]####
        System.out.println(time);//####[69]####
        System.out.println("Best Solution: " + currentBestSolution);//####[70]####
    }//####[71]####
//####[75]####
    private static volatile Method __pt__fuckTask_StackPartialSchedule_method = null;//####[75]####
    private synchronized static void __pt__fuckTask_StackPartialSchedule_ensureMethodVarSet() {//####[75]####
        if (__pt__fuckTask_StackPartialSchedule_method == null) {//####[75]####
            try {//####[75]####
                __pt__fuckTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__fuckTask", new Class[] {//####[75]####
                    Stack.class//####[75]####
                });//####[75]####
            } catch (Exception e) {//####[75]####
                e.printStackTrace();//####[75]####
            }//####[75]####
        }//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(Stack<PartialSchedule> scheduleStack) {//####[75]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[75]####
        return fuckTask(scheduleStack, new TaskInfo());//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[75]####
        // ensure Method variable is set//####[75]####
        if (__pt__fuckTask_StackPartialSchedule_method == null) {//####[75]####
            __pt__fuckTask_StackPartialSchedule_ensureMethodVarSet();//####[75]####
        }//####[75]####
        taskinfo.setParameters(scheduleStack);//####[75]####
        taskinfo.setMethod(__pt__fuckTask_StackPartialSchedule_method);//####[75]####
        taskinfo.setInstance(this);//####[75]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[75]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[75]####
        return fuckTask(scheduleStack, new TaskInfo());//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[75]####
        // ensure Method variable is set//####[75]####
        if (__pt__fuckTask_StackPartialSchedule_method == null) {//####[75]####
            __pt__fuckTask_StackPartialSchedule_ensureMethodVarSet();//####[75]####
        }//####[75]####
        taskinfo.setTaskIdArgIndexes(0);//####[75]####
        taskinfo.addDependsOn(scheduleStack);//####[75]####
        taskinfo.setParameters(scheduleStack);//####[75]####
        taskinfo.setMethod(__pt__fuckTask_StackPartialSchedule_method);//####[75]####
        taskinfo.setInstance(this);//####[75]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[75]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[75]####
        return fuckTask(scheduleStack, new TaskInfo());//####[75]####
    }//####[75]####
    private TaskIDGroup<Void> fuckTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[75]####
        // ensure Method variable is set//####[75]####
        if (__pt__fuckTask_StackPartialSchedule_method == null) {//####[75]####
            __pt__fuckTask_StackPartialSchedule_ensureMethodVarSet();//####[75]####
        }//####[75]####
        taskinfo.setQueueArgIndexes(0);//####[75]####
        taskinfo.setIsPipeline(true);//####[75]####
        taskinfo.setParameters(scheduleStack);//####[75]####
        taskinfo.setMethod(__pt__fuckTask_StackPartialSchedule_method);//####[75]####
        taskinfo.setInstance(this);//####[75]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[75]####
    }//####[75]####
    public void __pt__fuckTask(Stack<PartialSchedule> scheduleStack) {//####[75]####
        while (!scheduleStack.isEmpty()) //####[76]####
        {//####[76]####
            fuck(scheduleStack);//####[77]####
        }//####[78]####
    }//####[79]####
//####[79]####
//####[92]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[92]####
    public void dfs() {//####[92]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[95]####
        for (PartialSchedule schedule : schedules) //####[96]####
        {//####[96]####
            scheduleStack.add(schedule);//####[97]####
        }//####[98]####
        TaskIDGroup g = new TaskIDGroup(2);//####[99]####
        if (scheduleStack.size() >= 2) //####[100]####
        {//####[100]####
            for (int i = 0; i < 2; i++) //####[102]####
            {//####[102]####
                TaskID id = fuckTask(scheduleStack);//####[103]####
                g.add(id);//####[104]####
            }//####[105]####
        } else {//####[107]####
            TaskID id = fuckTask(scheduleStack);//####[108]####
            g.add(id);//####[109]####
        }//####[110]####
        try {//####[112]####
            g.waitTillFinished();//####[113]####
        } catch (Exception e) {//####[114]####
            e.printStackTrace();//####[115]####
        }//####[116]####
    }//####[118]####
//####[120]####
    private void fuck(Stack<PartialSchedule> scheduleStack) {//####[120]####
        try {//####[121]####
            PartialSchedule schedule = scheduleStack.pop();//####[122]####
            ArrayList<String> reachable = schedule.getReachable();//####[123]####
            for (String s : reachable) //####[124]####
            {//####[124]####
                if (schedule.startTimeZeroProcessors() > 1) //####[125]####
                {//####[125]####
                    Node n = nodeMap.get(s);//####[126]####
                    boolean discovered = false;//####[127]####
                    for (int i = 1; i <= _numProcessors; i++) //####[128]####
                    {//####[128]####
                        double time = schedule.getProcessorTime(i);//####[129]####
                        if (time == 0.0 && !discovered) //####[130]####
                        {//####[130]####
                            discovered = true;//####[131]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[132]####
                            childSchedule.solve(n, i);//####[133]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[134]####
                            if (maxHeuristic < currentBestSolution) //####[135]####
                            {//####[135]####
                                scheduleStack.push(childSchedule);//####[136]####
                            }//####[137]####
                        } else if (time != 0.0) //####[138]####
                        {//####[138]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[139]####
                            childSchedule.solve(n, i);//####[140]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[141]####
                            if (maxHeuristic < currentBestSolution) //####[142]####
                            {//####[142]####
                                scheduleStack.push(childSchedule);//####[143]####
                            }//####[144]####
                        } else {//####[145]####
                        }//####[147]####
                    }//####[148]####
                } else {//####[150]####
                    for (int i = 1; i <= _numProcessors; i++) //####[151]####
                    {//####[151]####
                        Node n = nodeMap.get(s);//####[152]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[154]####
                        childSchedule.solve(n, i);//####[157]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[158]####
                        if (maxHeuristic < currentBestSolution) //####[159]####
                        {//####[159]####
                            scheduleStack.push(childSchedule);//####[160]####
                        }//####[161]####
                    }//####[162]####
                }//####[163]####
            }//####[164]####
            if (reachable.isEmpty()) //####[165]####
            {//####[165]####
                double[] solution = schedule.getEndTimes();//####[167]####
                double endTime = 0;//####[168]####
                for (int i = 0; i < solution.length; i++) //####[169]####
                {//####[169]####
                    if (solution[i] > endTime) //####[170]####
                    {//####[170]####
                        endTime = solution[i];//####[171]####
                    }//####[172]####
                }//####[173]####
                if (endTime < currentBestSolution) //####[174]####
                {//####[174]####
                    currentBestSolution = endTime;//####[175]####
                }//####[176]####
            }//####[177]####
        } catch (EmptyStackException e) {//####[178]####
            e.printStackTrace();//####[179]####
        }//####[180]####
    }//####[181]####
//####[183]####
    public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode) {//####[183]####
        Stack<String> s = new Stack<String>();//####[184]####
        s.add(initialNode);//####[185]####
        double startTime = graph.get(initialNode).getStartTime();//####[186]####
        graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[187]####
        double max = 0;//####[188]####
        while (!s.isEmpty()) //####[189]####
        {//####[189]####
            Node node = graph.get(s.pop());//####[190]####
            if (node.getParents().keySet().contains(graph.get(initialNode))) //####[191]####
            {//####[191]####
                graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[192]####
            }//####[193]####
            for (Node child : node.getChildren().keySet()) //####[194]####
            {//####[194]####
                s.push(child.getID());//####[195]####
            }//####[196]####
            if (node.getID() != initialNode) //####[197]####
            {//####[197]####
                double test = 0;//####[198]####
                for (Node parent : node.getParents().keySet()) //####[199]####
                {//####[199]####
                    if (test < parent.getOptimalBottomLevel()) //####[200]####
                    {//####[200]####
                        test = parent.getOptimalBottomLevel();//####[201]####
                    }//####[202]####
                }//####[203]####
                node.setOptimalBottomLevel(test + node.getCost());//####[204]####
            }//####[205]####
        }//####[206]####
        for (Node n : graph.values()) //####[207]####
        {//####[207]####
            if (n.getOptimalBottomLevel() > max && n.getChildren().isEmpty()) //####[208]####
            {//####[208]####
                max = n.getOptimalBottomLevel();//####[209]####
            }//####[210]####
            n.setOptimalBottomLevel(0.0);//####[211]####
        }//####[212]####
        return max;//####[214]####
    }//####[215]####
//####[217]####
    public double findStartingOptimalBranch(HashMap<String, Node> graph) {//####[217]####
        Queue<Node> optimalBranch = new PriorityQueue<Node>();//####[218]####
        for (Node n : graph.values()) //####[219]####
        {//####[219]####
            optimalBranch.add(n);//####[220]####
        }//####[221]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[222]####
        PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[223]####
        schedule.decideIndex();//####[224]####
        scheduleStack.add(schedule);//####[226]####
        while (!optimalBranch.isEmpty()) //####[227]####
        {//####[227]####
            Node n = optimalBranch.poll();//####[229]####
            PartialSchedule scheduleOnStack = scheduleStack.pop();//####[230]####
            PartialSchedule[] partialSchedules = new PartialSchedule[_numProcessors];//####[231]####
            for (int i = 1; i <= _numProcessors; i++) //####[232]####
            {//####[232]####
                PartialSchedule childSchedule = scheduleOnStack.makeChildSchedule();//####[233]####
                childSchedule.solve(n, i);//####[234]####
                partialSchedules[i - 1] = childSchedule;//####[235]####
            }//####[236]####
            int minIndex = 0;//####[237]####
            Double min = Double.POSITIVE_INFINITY;//####[238]####
            for (int i = 1; i <= _numProcessors; i++) //####[239]####
            {//####[239]####
                PartialSchedule scheduleToCompare = partialSchedules[i - 1];//####[240]####
                if (min > scheduleToCompare.getNodeEndTime(n)) //####[241]####
                {//####[241]####
                    min = scheduleToCompare.getNodeEndTime(n);//####[242]####
                    minIndex = i;//####[243]####
                }//####[244]####
            }//####[245]####
            scheduleStack.push(partialSchedules[minIndex - 1]);//####[246]####
            if (optimalBranch.isEmpty()) //####[247]####
            {//####[247]####
                currentBestSolution = min;//####[248]####
                break;//####[249]####
            }//####[250]####
        }//####[251]####
        return 0.0;//####[252]####
    }//####[253]####
//####[260]####
    /**
	 * Returns the currentBestSolution field.
	 * 
	 * @return
	 *///####[260]####
    public double getCurrentBestSolution() {//####[260]####
        return currentBestSolution;//####[261]####
    }//####[262]####
//####[269]####
    /**
	 * Returns the bestState field.
	 * 
	 * @return
	 *///####[269]####
    public FinalState getBestState() {//####[269]####
        return bestState;//####[270]####
    }//####[271]####
//####[273]####
    public void setProcessorNumber(int processors) {//####[273]####
        _numProcessors = processors;//####[274]####
    }//####[275]####
//####[282]####
    /**
	 * Sets the bestState field.
	 * 
	 * @param newBestState
	 *///####[282]####
    public void setBestState(FinalState newBestState) {//####[282]####
        bestState = newBestState;//####[283]####
    }//####[284]####
//####[292]####
    /**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 * 
	 * @param taskGraph
	 *///####[292]####
    public void provideTaskGraph(LinkedHashMap<String, Node> taskGraph) {//####[292]####
        nodeMap = taskGraph;//####[293]####
        bestState = new FinalState(taskGraph);//####[294]####
    }//####[296]####
}//####[296]####
