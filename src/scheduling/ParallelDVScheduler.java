package scheduling;//####[2]####
//####[2]####
import java.awt.event.ActionEvent;//####[4]####
import java.awt.event.ActionListener;//####[5]####
import java.io.IOException;//####[6]####
import java.util.ArrayList;//####[7]####
import java.util.Comparator;//####[8]####
import java.util.EmptyStackException;//####[9]####
import java.util.HashMap;//####[10]####
import java.util.LinkedHashMap;//####[11]####
import java.util.PriorityQueue;//####[12]####
import java.util.Queue;//####[13]####
import java.util.Stack;//####[14]####
import java.util.concurrent.ExecutorService;//####[15]####
import java.util.concurrent.Executors;//####[16]####
import java.util.concurrent.ForkJoinPool;//####[17]####
import dataStructure.Node;//####[19]####
import pt.runtime.TaskID;//####[20]####
import pt.runtime.TaskIDGroup;//####[21]####
//####[21]####
//-- ParaTask related imports//####[21]####
import pt.runtime.*;//####[21]####
import java.util.concurrent.ExecutionException;//####[21]####
import java.util.concurrent.locks.*;//####[21]####
import java.lang.reflect.*;//####[21]####
import pt.runtime.GuiThread;//####[21]####
import java.util.concurrent.BlockingQueue;//####[21]####
import java.util.ArrayList;//####[21]####
import java.util.List;//####[21]####
//####[21]####
/**
 * Parallel scheduler combined with the data visualization for finding optimal task schedules.
 *
 *///####[26]####
public class ParallelDVScheduler extends DVScheduler {//####[27]####
    static{ParaTask.init();}//####[27]####
    /*  ParaTask helper method to access private/protected slots *///####[27]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[27]####
        if (m.getParameterTypes().length == 0)//####[27]####
            m.invoke(instance);//####[27]####
        else if ((m.getParameterTypes().length == 1))//####[27]####
            m.invoke(instance, arg);//####[27]####
        else //####[27]####
            m.invoke(instance, arg, interResult);//####[27]####
    }//####[27]####
//####[28]####
    int threadsToUse;//####[28]####
//####[30]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[30]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[30]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[30]####
            try {//####[30]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[30]####
                    Stack.class//####[30]####
                });//####[30]####
            } catch (Exception e) {//####[30]####
                e.printStackTrace();//####[30]####
            }//####[30]####
        }//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[30]####
        // ensure Method variable is set//####[30]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[30]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[30]####
        }//####[30]####
        taskinfo.setParameters(scheduleStack);//####[30]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[30]####
        taskinfo.setInstance(this);//####[30]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[30]####
        // ensure Method variable is set//####[30]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[30]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[30]####
        }//####[30]####
        taskinfo.setTaskIdArgIndexes(0);//####[30]####
        taskinfo.addDependsOn(scheduleStack);//####[30]####
        taskinfo.setParameters(scheduleStack);//####[30]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[30]####
        taskinfo.setInstance(this);//####[30]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[30]####
        // ensure Method variable is set//####[30]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[30]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[30]####
        }//####[30]####
        taskinfo.setQueueArgIndexes(0);//####[30]####
        taskinfo.setIsPipeline(true);//####[30]####
        taskinfo.setParameters(scheduleStack);//####[30]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[30]####
        taskinfo.setInstance(this);//####[30]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[30]####
    }//####[30]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[30]####
        while (!scheduleStack.isEmpty()) //####[31]####
        {//####[31]####
            processSchedule(scheduleStack);//####[32]####
        }//####[33]####
    }//####[34]####
//####[34]####
//####[47]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization.
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[47]####
    public void dfs() {//####[47]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[48]####
        for (Node n : nodeMap.values()) //####[49]####
        {//####[49]####
            if (n.getParents().isEmpty()) //####[51]####
            {//####[51]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[53]####
                schedule.decideIndex();//####[54]####
                schedule.update(n, 1);//####[55]####
                schedules.add(schedule);//####[56]####
            }//####[58]####
        }//####[59]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[60]####
        for (PartialSchedule schedule : schedules) //####[61]####
        {//####[61]####
            scheduleStack.add(schedule);//####[62]####
        }//####[63]####
        TaskIDGroup g = new TaskIDGroup(2);//####[64]####
        for (int i = 0; i < 2; i++) //####[66]####
        {//####[66]####
            TaskID id = processScheduleTask(scheduleStack);//####[67]####
            g.add(id);//####[68]####
        }//####[69]####
        try {//####[71]####
            time2.start();//####[72]####
            Time2.start();//####[73]####
            g.waitTillFinished();//####[74]####
            time2.stop();//####[75]####
            Time2.stop();//####[76]####
            fireBest();//####[77]####
            fireLabelUpdate();//####[78]####
            fireSecondUpdate();//####[79]####
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[80]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[81]####
            status = "Current Status: Finished";//####[83]####
            _vc.setStateLabel2(status, afterUsedMem / 1024 / 1024 + "MB");//####[84]####
        } catch (Exception e) {//####[85]####
            e.printStackTrace();//####[86]####
        }//####[87]####
    }//####[88]####
//####[94]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[94]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[94]####
        try {//####[95]####
            schedulesProcessed++;//####[96]####
            PartialSchedule schedule = scheduleStack.pop();//####[97]####
            _currentSchedule = schedule;//####[98]####
            ArrayList<String> reachable = schedule.getReachable();//####[99]####
            for (String s : reachable) //####[100]####
            {//####[100]####
                if (schedule.startTimeZeroProcessors() > 1) //####[101]####
                {//####[101]####
                    Node n = nodeMap.get(s);//####[102]####
                    boolean discovered = false;//####[103]####
                    for (int i = 1; i <= _numProcessors; i++) //####[104]####
                    {//####[104]####
                        double time = schedule.getProcessorTime(i);//####[105]####
                        if (time == 0.0 && !discovered) //####[106]####
                        {//####[106]####
                            discovered = true;//####[107]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[108]####
                            childSchedule.update(n, i);//####[109]####
                            String id = childSchedule.generateId();//####[110]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[111]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[112]####
                            {//####[112]####
                                _prevSchedules.add(id);//####[113]####
                                scheduleStack.push(childSchedule);//####[114]####
                            }//####[115]####
                        } else if (time != 0.0) //####[116]####
                        {//####[116]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[117]####
                            childSchedule.update(n, i);//####[118]####
                            String id = childSchedule.generateId();//####[119]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[120]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[121]####
                            {//####[121]####
                                _prevSchedules.add(id);//####[122]####
                                scheduleStack.push(childSchedule);//####[123]####
                            }//####[124]####
                        }//####[125]####
                    }//####[126]####
                } else {//####[128]####
                    for (int i = 1; i <= _numProcessors; i++) //####[129]####
                    {//####[129]####
                        Node n = nodeMap.get(s);//####[130]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[132]####
                        childSchedule.update(n, i);//####[134]####
                        String id = childSchedule.generateId();//####[135]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[136]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[137]####
                        {//####[137]####
                            _prevSchedules.add(id);//####[138]####
                            scheduleStack.push(childSchedule);//####[139]####
                        }//####[140]####
                    }//####[141]####
                }//####[142]####
            }//####[143]####
            if (reachable.isEmpty()) //####[144]####
            {//####[144]####
                double[] solution = schedule.getEndTimes();//####[146]####
                double endTime = 0;//####[147]####
                for (int i = 0; i < solution.length; i++) //####[149]####
                {//####[149]####
                    if (solution[i] > endTime) //####[150]####
                    {//####[150]####
                        endTime = solution[i];//####[151]####
                    }//####[152]####
                }//####[153]####
                if (!bestTimes.contains(endTime)) //####[155]####
                {//####[155]####
                    bestTimes.add(endTime);//####[156]####
                    bestTimesCopy.add(endTime);//####[157]####
                    schedulesProcessed++;//####[158]####
                    fireSecondUpdate();//####[159]####
                }//####[160]####
                if (endTime < currentBestSolution) //####[162]####
                {//####[162]####
                    currentBestSolution = endTime;//####[163]####
                    bestSchedule.setCurrentBestState(schedule);//####[164]####
                }//####[165]####
            }//####[166]####
        } catch (EmptyStackException e) {//####[167]####
        }//####[169]####
    }//####[170]####
//####[176]####
    /**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 *///####[176]####
    public void setThreads(int thread) {//####[176]####
        this.threadsToUse = thread;//####[177]####
    }//####[178]####
}//####[178]####
