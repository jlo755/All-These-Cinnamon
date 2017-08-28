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
        while (!scheduleStack.isEmpty()) //####[30]####
        {//####[30]####
            processSchedule(scheduleStack);//####[31]####
        }//####[32]####
    }//####[33]####
//####[33]####
//####[46]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization.
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[46]####
    public void dfs() {//####[46]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[47]####
        for (Node n : nodeMap.values()) //####[48]####
        {//####[48]####
            if (n.getParents().isEmpty()) //####[50]####
            {//####[50]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[52]####
                schedule.decideIndex();//####[53]####
                schedule.update(n, 1);//####[54]####
                schedules.add(schedule);//####[55]####
            }//####[57]####
        }//####[58]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[59]####
        for (PartialSchedule schedule : schedules) //####[60]####
        {//####[60]####
            scheduleStack.add(schedule);//####[61]####
        }//####[62]####
        TaskIDGroup g = new TaskIDGroup(2);//####[63]####
        for (int i = 0; i < 2; i++) //####[65]####
        {//####[65]####
            TaskID id = processScheduleTask(scheduleStack);//####[66]####
            g.add(id);//####[67]####
        }//####[68]####
        try {//####[70]####
            time2.start();//####[71]####
            Time2.start();//####[72]####
            g.waitTillFinished();//####[73]####
            time2.stop();//####[74]####
            Time2.stop();//####[75]####
            fireBest();//####[76]####
            fireLabelUpdate();//####[77]####
            fireSecondUpdate();//####[78]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[79]####
            status = "Current Status: Finished";//####[81]####
            _vc.setLabelProcessorScreen(status, afterUsedMem / 1024 / 1024 + "MB");//####[82]####
        } catch (Exception e) {//####[83]####
            e.printStackTrace();//####[84]####
        }//####[85]####
    }//####[86]####
//####[92]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[92]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[92]####
        try {//####[93]####
            schedulesProcessed++;//####[94]####
            PartialSchedule schedule = scheduleStack.pop();//####[95]####
            _currentSchedule = schedule;//####[96]####
            ArrayList<String> reachable = schedule.getReachable();//####[97]####
            for (String s : reachable) //####[98]####
            {//####[98]####
                if (schedule.startTimeZeroProcessors() > 1) //####[99]####
                {//####[99]####
                    Node n = nodeMap.get(s);//####[100]####
                    boolean discovered = false;//####[101]####
                    for (int i = 1; i <= _numProcessors; i++) //####[102]####
                    {//####[102]####
                        double time = schedule.getProcessorTime(i);//####[103]####
                        if (time == 0.0 && !discovered) //####[104]####
                        {//####[104]####
                            discovered = true;//####[105]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[106]####
                            childSchedule.update(n, i);//####[107]####
                            String id = childSchedule.generateId();//####[108]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[109]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[110]####
                            {//####[110]####
                                _prevSchedules.add(id);//####[111]####
                                scheduleStack.push(childSchedule);//####[112]####
                            }//####[113]####
                        } else if (time != 0.0) //####[114]####
                        {//####[114]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[115]####
                            childSchedule.update(n, i);//####[116]####
                            String id = childSchedule.generateId();//####[117]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[118]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[119]####
                            {//####[119]####
                                _prevSchedules.add(id);//####[120]####
                                scheduleStack.push(childSchedule);//####[121]####
                            }//####[122]####
                        }//####[123]####
                    }//####[124]####
                } else {//####[126]####
                    for (int i = 1; i <= _numProcessors; i++) //####[127]####
                    {//####[127]####
                        Node n = nodeMap.get(s);//####[128]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[130]####
                        childSchedule.update(n, i);//####[132]####
                        String id = childSchedule.generateId();//####[133]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[134]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[135]####
                        {//####[135]####
                            _prevSchedules.add(id);//####[136]####
                            scheduleStack.push(childSchedule);//####[137]####
                        }//####[138]####
                    }//####[139]####
                }//####[140]####
            }//####[141]####
            if (reachable.isEmpty()) //####[142]####
            {//####[142]####
                double[] solution = schedule.getEndTimes();//####[144]####
                double endTime = 0;//####[145]####
                for (int i = 0; i < solution.length; i++) //####[147]####
                {//####[147]####
                    if (solution[i] > endTime) //####[148]####
                    {//####[148]####
                        endTime = solution[i];//####[149]####
                    }//####[150]####
                }//####[151]####
                if (!bestTimes.contains(endTime)) //####[153]####
                {//####[153]####
                    bestTimes.add(endTime);//####[154]####
                    bestTimesCopy.add(endTime);//####[155]####
                    schedulesProcessed++;//####[156]####
                    fireSecondUpdate();//####[157]####
                }//####[158]####
                if (endTime < currentBestSolution) //####[160]####
                {//####[160]####
                    currentBestSolution = endTime;//####[161]####
                    bestSchedule.setCurrentBestState(schedule);//####[162]####
                }//####[163]####
            }//####[164]####
        } catch (EmptyStackException e) {//####[165]####
        }//####[167]####
    }//####[168]####
//####[174]####
    /**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 *///####[174]####
    public void setThreads(int thread) {//####[174]####
        this.threadsToUse = thread;//####[175]####
    }//####[176]####
}//####[176]####
