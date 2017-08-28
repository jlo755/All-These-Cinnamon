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
import javax.swing.Timer;//####[19]####
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;//####[21]####
import dataStructure.Node;//####[23]####
import inputParse.Edge;//####[24]####
import outputParse.OutputParser;//####[25]####
import pt.runtime.TaskID;//####[26]####
import pt.runtime.TaskIDGroup;//####[27]####
import visualisation.VisualController;//####[28]####
//####[28]####
//-- ParaTask related imports//####[28]####
import pt.runtime.*;//####[28]####
import java.util.concurrent.ExecutionException;//####[28]####
import java.util.concurrent.locks.*;//####[28]####
import java.lang.reflect.*;//####[28]####
import pt.runtime.GuiThread;//####[28]####
import java.util.concurrent.BlockingQueue;//####[28]####
import java.util.ArrayList;//####[28]####
import java.util.List;//####[28]####
//####[28]####
/**
 * Parallel scheduler combined with the data visualization for finding optimal task schedules.
 *
 *///####[33]####
public class ParallelDVScheduler extends DVScheduler {//####[34]####
    static{ParaTask.init();}//####[34]####
    /*  ParaTask helper method to access private/protected slots *///####[34]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[34]####
        if (m.getParameterTypes().length == 0)//####[34]####
            m.invoke(instance);//####[34]####
        else if ((m.getParameterTypes().length == 1))//####[34]####
            m.invoke(instance, arg);//####[34]####
        else //####[34]####
            m.invoke(instance, arg, interResult);//####[34]####
    }//####[34]####
//####[35]####
    int threadsToUse;//####[35]####
//####[37]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[37]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[37]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[37]####
            try {//####[37]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[37]####
                    Stack.class//####[37]####
                });//####[37]####
            } catch (Exception e) {//####[37]####
                e.printStackTrace();//####[37]####
            }//####[37]####
        }//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[37]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[37]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[37]####
        // ensure Method variable is set//####[37]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[37]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[37]####
        }//####[37]####
        taskinfo.setParameters(scheduleStack);//####[37]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[37]####
        taskinfo.setInstance(this);//####[37]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[37]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[37]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[37]####
        // ensure Method variable is set//####[37]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[37]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[37]####
        }//####[37]####
        taskinfo.setTaskIdArgIndexes(0);//####[37]####
        taskinfo.addDependsOn(scheduleStack);//####[37]####
        taskinfo.setParameters(scheduleStack);//####[37]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[37]####
        taskinfo.setInstance(this);//####[37]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[37]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[37]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[37]####
    }//####[37]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[37]####
        // ensure Method variable is set//####[37]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[37]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[37]####
        }//####[37]####
        taskinfo.setQueueArgIndexes(0);//####[37]####
        taskinfo.setIsPipeline(true);//####[37]####
        taskinfo.setParameters(scheduleStack);//####[37]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[37]####
        taskinfo.setInstance(this);//####[37]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[37]####
    }//####[37]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[37]####
        while (!scheduleStack.isEmpty()) //####[38]####
        {//####[38]####
            processSchedule(scheduleStack);//####[39]####
        }//####[40]####
    }//####[41]####
//####[41]####
//####[54]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization.
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[54]####
    public void dfs() {//####[54]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[55]####
        for (Node n : nodeMap.values()) //####[56]####
        {//####[56]####
            if (n.getParents().isEmpty()) //####[58]####
            {//####[58]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[60]####
                schedule.decideIndex();//####[61]####
                schedule.update(n, 1);//####[62]####
                schedules.add(schedule);//####[63]####
            }//####[65]####
        }//####[66]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[67]####
        for (PartialSchedule schedule : schedules) //####[68]####
        {//####[68]####
            scheduleStack.add(schedule);//####[69]####
        }//####[70]####
        TaskIDGroup g = new TaskIDGroup(2);//####[71]####
        for (int i = 0; i < 2; i++) //####[73]####
        {//####[73]####
            TaskID id = processScheduleTask(scheduleStack);//####[74]####
            g.add(id);//####[75]####
        }//####[76]####
        try {//####[78]####
            time2.start();//####[79]####
            Time2.start();//####[80]####
            g.waitTillFinished();//####[81]####
            time2.stop();//####[82]####
            Time2.stop();//####[83]####
            fireBest();//####[84]####
            fireLabelUpdate();//####[85]####
            fireSecondUpdate();//####[86]####
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[87]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[88]####
            status = "Current Status: Finished";//####[90]####
            _vc.setStateLabel2(status, afterUsedMem / 1024 / 1024 + "MB");//####[91]####
        } catch (Exception e) {//####[92]####
            e.printStackTrace();//####[93]####
        }//####[94]####
    }//####[114]####
//####[120]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[120]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[120]####
        try {//####[121]####
            schedulesProcessed++;//####[122]####
            PartialSchedule schedule = scheduleStack.pop();//####[123]####
            _currentSchedule = schedule;//####[124]####
            ArrayList<String> reachable = schedule.getReachable();//####[125]####
            for (String s : reachable) //####[126]####
            {//####[126]####
                if (schedule.startTimeZeroProcessors() > 1) //####[127]####
                {//####[127]####
                    Node n = nodeMap.get(s);//####[128]####
                    boolean discovered = false;//####[129]####
                    for (int i = 1; i <= _numProcessors; i++) //####[130]####
                    {//####[130]####
                        double time = schedule.getProcessorTime(i);//####[131]####
                        if (time == 0.0 && !discovered) //####[132]####
                        {//####[132]####
                            discovered = true;//####[133]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[134]####
                            childSchedule.update(n, i);//####[135]####
                            String id = childSchedule.generateId();//####[136]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[137]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[138]####
                            {//####[138]####
                                _prevSchedules.add(id);//####[139]####
                                scheduleStack.push(childSchedule);//####[140]####
                            }//####[141]####
                        } else if (time != 0.0) //####[142]####
                        {//####[142]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[143]####
                            childSchedule.update(n, i);//####[144]####
                            String id = childSchedule.generateId();//####[145]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[146]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[147]####
                            {//####[147]####
                                _prevSchedules.add(id);//####[148]####
                                scheduleStack.push(childSchedule);//####[149]####
                            }//####[150]####
                        }//####[151]####
                    }//####[152]####
                } else {//####[154]####
                    for (int i = 1; i <= _numProcessors; i++) //####[155]####
                    {//####[155]####
                        Node n = nodeMap.get(s);//####[156]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[158]####
                        childSchedule.update(n, i);//####[160]####
                        String id = childSchedule.generateId();//####[161]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[162]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[163]####
                        {//####[163]####
                            _prevSchedules.add(id);//####[164]####
                            scheduleStack.push(childSchedule);//####[165]####
                        }//####[166]####
                    }//####[167]####
                }//####[168]####
            }//####[169]####
            if (reachable.isEmpty()) //####[170]####
            {//####[170]####
                double[] solution = schedule.getEndTimes();//####[172]####
                double endTime = 0;//####[173]####
                for (int i = 0; i < solution.length; i++) //####[175]####
                {//####[175]####
                    if (solution[i] > endTime) //####[176]####
                    {//####[176]####
                        endTime = solution[i];//####[177]####
                    }//####[178]####
                }//####[179]####
                if (!bestTimes.contains(endTime)) //####[181]####
                {//####[181]####
                    bestTimes.add(endTime);//####[182]####
                    bestTimesCopy.add(endTime);//####[183]####
                    schedulesProcessed++;//####[184]####
                    fireSecondUpdate();//####[185]####
                }//####[186]####
                if (endTime < currentBestSolution) //####[188]####
                {//####[188]####
                    currentBestSolution = endTime;//####[189]####
                    bestSchedule.setCurrentBestState(schedule);//####[190]####
                }//####[191]####
            }//####[192]####
        } catch (EmptyStackException e) {//####[193]####
        }//####[195]####
    }//####[196]####
//####[202]####
    /**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 *///####[202]####
    public void setThreads(int thread) {//####[202]####
        this.threadsToUse = thread;//####[203]####
    }//####[204]####
}//####[204]####
