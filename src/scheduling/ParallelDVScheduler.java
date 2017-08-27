package scheduling;//####[1]####
//####[1]####
import java.awt.event.ActionEvent;//####[3]####
import java.awt.event.ActionListener;//####[4]####
import java.io.IOException;//####[5]####
import java.util.ArrayList;//####[6]####
import java.util.Comparator;//####[7]####
import java.util.EmptyStackException;//####[8]####
import java.util.HashMap;//####[9]####
import java.util.LinkedHashMap;//####[10]####
import java.util.PriorityQueue;//####[11]####
import java.util.Queue;//####[12]####
import java.util.Stack;//####[13]####
import java.util.concurrent.ExecutorService;//####[14]####
import java.util.concurrent.Executors;//####[15]####
import java.util.concurrent.ForkJoinPool;//####[16]####
import javax.swing.Timer;//####[18]####
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;//####[20]####
import dataStructure.Node;//####[22]####
import inputParse.Edge;//####[23]####
import outputParse.OutputParser;//####[24]####
import pt.runtime.TaskID;//####[25]####
import pt.runtime.TaskIDGroup;//####[26]####
import visualisation.VisualController;//####[27]####
//####[27]####
//-- ParaTask related imports//####[27]####
import pt.runtime.*;//####[27]####
import java.util.concurrent.ExecutionException;//####[27]####
import java.util.concurrent.locks.*;//####[27]####
import java.lang.reflect.*;//####[27]####
import pt.runtime.GuiThread;//####[27]####
import java.util.concurrent.BlockingQueue;//####[27]####
import java.util.ArrayList;//####[27]####
import java.util.List;//####[27]####
//####[27]####
public class ParallelDVScheduler extends DVScheduler {//####[29]####
    static{ParaTask.init();}//####[29]####
    /*  ParaTask helper method to access private/protected slots *///####[29]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[29]####
        if (m.getParameterTypes().length == 0)//####[29]####
            m.invoke(instance);//####[29]####
        else if ((m.getParameterTypes().length == 1))//####[29]####
            m.invoke(instance, arg);//####[29]####
        else //####[29]####
            m.invoke(instance, arg, interResult);//####[29]####
    }//####[29]####
//####[30]####
    int threadsToUse;//####[30]####
//####[32]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[32]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[32]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[32]####
            try {//####[32]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[32]####
                    Stack.class//####[32]####
                });//####[32]####
            } catch (Exception e) {//####[32]####
                e.printStackTrace();//####[32]####
            }//####[32]####
        }//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[32]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[32]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[32]####
        // ensure Method variable is set//####[32]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[32]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[32]####
        }//####[32]####
        taskinfo.setParameters(scheduleStack);//####[32]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[32]####
        taskinfo.setInstance(this);//####[32]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[32]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[32]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[32]####
        // ensure Method variable is set//####[32]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[32]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[32]####
        }//####[32]####
        taskinfo.setTaskIdArgIndexes(0);//####[32]####
        taskinfo.addDependsOn(scheduleStack);//####[32]####
        taskinfo.setParameters(scheduleStack);//####[32]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[32]####
        taskinfo.setInstance(this);//####[32]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[32]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[32]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[32]####
    }//####[32]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[32]####
        // ensure Method variable is set//####[32]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[32]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[32]####
        }//####[32]####
        taskinfo.setQueueArgIndexes(0);//####[32]####
        taskinfo.setIsPipeline(true);//####[32]####
        taskinfo.setParameters(scheduleStack);//####[32]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[32]####
        taskinfo.setInstance(this);//####[32]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[32]####
    }//####[32]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[32]####
        while (!scheduleStack.isEmpty()) //####[33]####
        {//####[33]####
            processSchedule(scheduleStack);//####[34]####
        }//####[35]####
    }//####[36]####
//####[36]####
//####[49]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 *
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[49]####
    public void dfs() {//####[49]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[50]####
        for (Node n : nodeMap.values()) //####[51]####
        {//####[51]####
            if (n.getParents().isEmpty()) //####[53]####
            {//####[53]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[55]####
                schedule.decideIndex();//####[56]####
                schedule.update(n, 1);//####[57]####
                schedules.add(schedule);//####[58]####
            }//####[60]####
        }//####[61]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[62]####
        for (PartialSchedule schedule : schedules) //####[63]####
        {//####[63]####
            scheduleStack.add(schedule);//####[64]####
        }//####[65]####
        Timer time2;//####[66]####
        int timeDelay = 100;//####[67]####
        ActionListener time;//####[68]####
        time = new ActionListener() {//####[68]####
//####[72]####
            @Override//####[72]####
            public void actionPerformed(ActionEvent e) {//####[72]####
                fire();//####[73]####
            }//####[75]####
        };//####[75]####
        ActionListener Time;//####[78]####
        Time = new ActionListener() {//####[78]####
//####[82]####
            @Override//####[82]####
            public void actionPerformed(ActionEvent e) {//####[82]####
                fireLabelUpdate();//####[84]####
            }//####[86]####
        };//####[86]####
        Timer Time2;//####[88]####
        Time2 = new Timer(timeDelay, Time);//####[89]####
        time2 = new Timer(timeDelay, time);//####[90]####
        time2.start();//####[91]####
        Time2.start();//####[92]####
        startTime = System.nanoTime();//####[93]####
        TaskIDGroup g = new TaskIDGroup(2);//####[94]####
        for (int i = 0; i < 2; i++) //####[96]####
        {//####[96]####
            TaskID id = processScheduleTask(scheduleStack);//####[97]####
            g.add(id);//####[98]####
        }//####[99]####
        try {//####[101]####
            g.waitTillFinished();//####[102]####
            time2.stop();//####[103]####
            Time2.stop();//####[104]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[105]####
            status = "Current Status: Finished";//####[106]####
            _vc.setStateLabel2(status, actualMemUsed);//####[107]####
        } catch (Exception e) {//####[108]####
            e.printStackTrace();//####[109]####
        }//####[110]####
        long endTime = System.nanoTime();//####[111]####
        System.out.println("Hello");//####[112]####
        System.out.println((endTime - startTime) / 1000000000.0);//####[113]####
    }//####[115]####
//####[121]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[121]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[121]####
        try {//####[122]####
            schedulesProcessed++;//####[123]####
            PartialSchedule schedule = scheduleStack.pop();//####[124]####
            _currentSchedule = schedule;//####[125]####
            ArrayList<String> reachable = schedule.getReachable();//####[126]####
            for (String s : reachable) //####[127]####
            {//####[127]####
                if (schedule.startTimeZeroProcessors() > 1) //####[128]####
                {//####[128]####
                    Node n = nodeMap.get(s);//####[129]####
                    boolean discovered = false;//####[130]####
                    for (int i = 1; i <= _numProcessors; i++) //####[131]####
                    {//####[131]####
                        double time = schedule.getProcessorTime(i);//####[132]####
                        if (time == 0.0 && !discovered) //####[133]####
                        {//####[133]####
                            discovered = true;//####[134]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[135]####
                            childSchedule.update(n, i);//####[136]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[137]####
                            if (maxHeuristic < currentBestSolution) //####[138]####
                            {//####[138]####
                                scheduleStack.push(childSchedule);//####[139]####
                            }//####[140]####
                        } else if (time != 0.0) //####[141]####
                        {//####[141]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[142]####
                            childSchedule.update(n, i);//####[143]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[144]####
                            if (maxHeuristic < currentBestSolution) //####[145]####
                            {//####[145]####
                                scheduleStack.push(childSchedule);//####[146]####
                            }//####[147]####
                        }//####[148]####
                    }//####[149]####
                } else {//####[151]####
                    for (int i = 1; i <= _numProcessors; i++) //####[152]####
                    {//####[152]####
                        Node n = nodeMap.get(s);//####[153]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[155]####
                        childSchedule.update(n, i);//####[157]####
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
                for (int i = 0; i < solution.length; i++) //####[170]####
                {//####[170]####
                    if (solution[i] > endTime) //####[171]####
                    {//####[171]####
                        endTime = solution[i];//####[172]####
                    }//####[173]####
                }//####[174]####
                if (!bestTimes.contains(endTime)) //####[176]####
                {//####[176]####
                    bestTimes.add(endTime);//####[177]####
                    bestTimesCopy.add(endTime);//####[178]####
                    schedulesProcessed++;//####[179]####
                    fireSecondUpdate();//####[180]####
                }//####[181]####
                if (endTime < currentBestSolution) //####[183]####
                {//####[183]####
                    currentBestSolution = endTime;//####[184]####
                    bestSchedule.setCurrentBestState(schedule);//####[185]####
                    System.out.println(currentBestSolution);//####[186]####
                }//####[187]####
            }//####[188]####
        } catch (EmptyStackException e) {//####[189]####
            e.printStackTrace();//####[190]####
        }//####[192]####
    }//####[193]####
//####[195]####
    public void setThreads(int thread) {//####[195]####
        this.threadsToUse = thread;//####[196]####
    }//####[197]####
}//####[197]####
