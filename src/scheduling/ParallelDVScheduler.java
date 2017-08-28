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
import dataStructure.Node;//####[18]####
import pt.runtime.TaskID;//####[19]####
import pt.runtime.TaskIDGroup;//####[20]####
//####[20]####
//-- ParaTask related imports//####[20]####
import pt.runtime.*;//####[20]####
import java.util.concurrent.ExecutionException;//####[20]####
import java.util.concurrent.locks.*;//####[20]####
import java.lang.reflect.*;//####[20]####
import pt.runtime.GuiThread;//####[20]####
import java.util.concurrent.BlockingQueue;//####[20]####
import java.util.ArrayList;//####[20]####
import java.util.List;//####[20]####
//####[20]####
/**
 * Parallel scheduler combined with the data visualization for finding optimal task schedules.
 *
 *///####[25]####
public class ParallelDVScheduler extends DVScheduler {//####[26]####
    static{ParaTask.init();}//####[26]####
    /*  ParaTask helper method to access private/protected slots *///####[26]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[26]####
        if (m.getParameterTypes().length == 0)//####[26]####
            m.invoke(instance);//####[26]####
        else if ((m.getParameterTypes().length == 1))//####[26]####
            m.invoke(instance, arg);//####[26]####
        else //####[26]####
            m.invoke(instance, arg, interResult);//####[26]####
    }//####[26]####
//####[27]####
    int threadsToUse;//####[27]####
//####[29]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[29]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[29]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[29]####
            try {//####[29]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[29]####
                    Stack.class//####[29]####
                });//####[29]####
            } catch (Exception e) {//####[29]####
                e.printStackTrace();//####[29]####
            }//####[29]####
        }//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[29]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[29]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[29]####
        // ensure Method variable is set//####[29]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[29]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[29]####
        }//####[29]####
        taskinfo.setParameters(scheduleStack);//####[29]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[29]####
        taskinfo.setInstance(this);//####[29]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[29]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[29]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[29]####
        // ensure Method variable is set//####[29]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[29]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[29]####
        }//####[29]####
        taskinfo.setTaskIdArgIndexes(0);//####[29]####
        taskinfo.addDependsOn(scheduleStack);//####[29]####
        taskinfo.setParameters(scheduleStack);//####[29]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[29]####
        taskinfo.setInstance(this);//####[29]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[29]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[29]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[29]####
    }//####[29]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[29]####
        // ensure Method variable is set//####[29]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[29]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[29]####
        }//####[29]####
        taskinfo.setQueueArgIndexes(0);//####[29]####
        taskinfo.setIsPipeline(true);//####[29]####
        taskinfo.setParameters(scheduleStack);//####[29]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[29]####
        taskinfo.setInstance(this);//####[29]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[29]####
    }//####[29]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[29]####
        time2.start();//####[30]####
        Time2.start();//####[31]####
        while (!scheduleStack.isEmpty()) //####[32]####
        {//####[32]####
            processSchedule(scheduleStack);//####[33]####
        }//####[34]####
    }//####[35]####
//####[35]####
//####[48]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization.
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[48]####
    public void dfs() {//####[48]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[49]####
        for (Node n : nodeMap.values()) //####[50]####
        {//####[50]####
            if (n.getParents().isEmpty()) //####[52]####
            {//####[52]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[54]####
                schedule.decideIndex();//####[55]####
                schedule.update(n, 1);//####[56]####
                schedules.add(schedule);//####[57]####
            }//####[59]####
        }//####[60]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[61]####
        for (PartialSchedule schedule : schedules) //####[62]####
        {//####[62]####
            scheduleStack.add(schedule);//####[63]####
        }//####[64]####
        TaskIDGroup g = new TaskIDGroup(2);//####[65]####
        for (int i = 0; i < 2; i++) //####[67]####
        {//####[67]####
            TaskID id = processScheduleTask(scheduleStack);//####[68]####
            g.add(id);//####[69]####
        }//####[70]####
        try {//####[72]####
            g.waitTillFinished();//####[74]####
            time2.stop();//####[75]####
            Time2.stop();//####[76]####
            fireBest();//####[77]####
            fireLabelUpdate();//####[78]####
            fireSecondUpdate();//####[79]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[80]####
            status = "Current Status: Finished";//####[82]####
            _vc.setLabelProcessorScreen(status, afterUsedMem / 1024 / 1024 + "MB");//####[83]####
            outputSolution();//####[84]####
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
