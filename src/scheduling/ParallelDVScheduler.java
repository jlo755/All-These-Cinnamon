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
        } catch (Exception e) {//####[84]####
            e.printStackTrace();//####[85]####
        }//####[86]####
    }//####[87]####
//####[93]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[93]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[93]####
        try {//####[94]####
            schedulesProcessed++;//####[95]####
            PartialSchedule schedule = scheduleStack.pop();//####[96]####
            _currentSchedule = schedule;//####[97]####
            ArrayList<String> reachable = schedule.getReachable();//####[98]####
            for (String s : reachable) //####[99]####
            {//####[99]####
                if (schedule.startTimeZeroProcessors() > 1) //####[100]####
                {//####[100]####
                    Node n = nodeMap.get(s);//####[101]####
                    boolean discovered = false;//####[102]####
                    for (int i = 1; i <= _numProcessors; i++) //####[103]####
                    {//####[103]####
                        double time = schedule.getProcessorTime(i);//####[104]####
                        if (time == 0.0 && !discovered) //####[105]####
                        {//####[105]####
                            discovered = true;//####[106]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[107]####
                            childSchedule.update(n, i);//####[108]####
                            String id = childSchedule.generateId();//####[109]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[110]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[111]####
                            {//####[111]####
                                _prevSchedules.add(id);//####[112]####
                                scheduleStack.push(childSchedule);//####[113]####
                            }//####[114]####
                        } else if (time != 0.0) //####[115]####
                        {//####[115]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[116]####
                            childSchedule.update(n, i);//####[117]####
                            String id = childSchedule.generateId();//####[118]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[119]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[120]####
                            {//####[120]####
                                _prevSchedules.add(id);//####[121]####
                                scheduleStack.push(childSchedule);//####[122]####
                            }//####[123]####
                        }//####[124]####
                    }//####[125]####
                } else {//####[127]####
                    for (int i = 1; i <= _numProcessors; i++) //####[128]####
                    {//####[128]####
                        Node n = nodeMap.get(s);//####[129]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[131]####
                        childSchedule.update(n, i);//####[133]####
                        String id = childSchedule.generateId();//####[134]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[135]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[136]####
                        {//####[136]####
                            _prevSchedules.add(id);//####[137]####
                            scheduleStack.push(childSchedule);//####[138]####
                        }//####[139]####
                    }//####[140]####
                }//####[141]####
            }//####[142]####
            if (reachable.isEmpty()) //####[143]####
            {//####[143]####
                double[] solution = schedule.getEndTimes();//####[145]####
                double endTime = 0;//####[146]####
                for (int i = 0; i < solution.length; i++) //####[148]####
                {//####[148]####
                    if (solution[i] > endTime) //####[149]####
                    {//####[149]####
                        endTime = solution[i];//####[150]####
                    }//####[151]####
                }//####[152]####
                if (!bestTimes.contains(endTime)) //####[154]####
                {//####[154]####
                    bestTimes.add(endTime);//####[155]####
                    bestTimesCopy.add(endTime);//####[156]####
                    schedulesProcessed++;//####[157]####
                    fireSecondUpdate();//####[158]####
                }//####[159]####
                if (endTime < currentBestSolution) //####[161]####
                {//####[161]####
                    currentBestSolution = endTime;//####[162]####
                    bestSchedule.setCurrentBestState(schedule);//####[163]####
                }//####[164]####
            }//####[165]####
        } catch (EmptyStackException e) {//####[166]####
        }//####[168]####
    }//####[169]####
//####[175]####
    /**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 *///####[175]####
    public void setThreads(int thread) {//####[175]####
        this.threadsToUse = thread;//####[176]####
    }//####[177]####
}//####[177]####
