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
/**
 * Parallel scheduler combined with the data visualization for finding optimal task schedules.
 *
 *///####[32]####
public class ParallelDVScheduler extends DVScheduler {//####[33]####
    static{ParaTask.init();}//####[33]####
    /*  ParaTask helper method to access private/protected slots *///####[33]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[33]####
        if (m.getParameterTypes().length == 0)//####[33]####
            m.invoke(instance);//####[33]####
        else if ((m.getParameterTypes().length == 1))//####[33]####
            m.invoke(instance, arg);//####[33]####
        else //####[33]####
            m.invoke(instance, arg, interResult);//####[33]####
    }//####[33]####
//####[34]####
    int threadsToUse;//####[34]####
//####[36]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[36]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[36]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[36]####
            try {//####[36]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[36]####
                    Stack.class//####[36]####
                });//####[36]####
            } catch (Exception e) {//####[36]####
                e.printStackTrace();//####[36]####
            }//####[36]####
        }//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[36]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setParameters(scheduleStack);//####[36]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[36]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(0);//####[36]####
        taskinfo.addDependsOn(scheduleStack);//####[36]####
        taskinfo.setParameters(scheduleStack);//####[36]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[36]####
    }//####[36]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[36]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(scheduleStack);//####[36]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[36]####
    }//####[36]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[36]####
        while (!scheduleStack.isEmpty()) //####[37]####
        {//####[37]####
            processSchedule(scheduleStack);//####[38]####
        }//####[39]####
    }//####[40]####
//####[40]####
//####[53]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization. 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[53]####
    public void dfs() {//####[53]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[54]####
        for (Node n : nodeMap.values()) //####[55]####
        {//####[55]####
            if (n.getParents().isEmpty()) //####[57]####
            {//####[57]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[59]####
                schedule.decideIndex();//####[60]####
                schedule.update(n, 1);//####[61]####
                schedules.add(schedule);//####[62]####
            }//####[64]####
        }//####[65]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[66]####
        for (PartialSchedule schedule : schedules) //####[67]####
        {//####[67]####
            scheduleStack.add(schedule);//####[68]####
        }//####[69]####
        TaskIDGroup g = new TaskIDGroup(2);//####[70]####
        for (int i = 0; i < 2; i++) //####[72]####
        {//####[72]####
            TaskID id = processScheduleTask(scheduleStack);//####[73]####
            g.add(id);//####[74]####
        }//####[75]####
        try {//####[77]####
            time2.start();//####[78]####
            Time2.start();//####[79]####
            g.waitTillFinished();//####[80]####
            time2.stop();//####[81]####
            Time2.stop();//####[82]####
            fireBest();//####[83]####
            fireLabelUpdate();//####[84]####
            fireSecondUpdate();//####[85]####
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[86]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[87]####
            status = "Current Status: Finished";//####[89]####
            _vc.setStateLabel2(status, afterUsedMem / 1024 / 1024 + "MB");//####[90]####
        } catch (Exception e) {//####[91]####
            e.printStackTrace();//####[92]####
        }//####[93]####
    }//####[113]####
//####[119]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[119]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[119]####
        try {//####[120]####
            schedulesProcessed++;//####[121]####
            PartialSchedule schedule = scheduleStack.pop();//####[122]####
            _currentSchedule = schedule;//####[123]####
            ArrayList<String> reachable = schedule.getReachable();//####[124]####
            for (String s : reachable) //####[125]####
            {//####[125]####
                if (schedule.startTimeZeroProcessors() > 1) //####[126]####
                {//####[126]####
                    Node n = nodeMap.get(s);//####[127]####
                    boolean discovered = false;//####[128]####
                    for (int i = 1; i <= _numProcessors; i++) //####[129]####
                    {//####[129]####
                        double time = schedule.getProcessorTime(i);//####[130]####
                        if (time == 0.0 && !discovered) //####[131]####
                        {//####[131]####
                            discovered = true;//####[132]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[133]####
                            childSchedule.update(n, i);//####[134]####
                            String id = childSchedule.generateId();//####[135]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[136]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[137]####
                            {//####[137]####
                                _prevSchedules.add(id);//####[138]####
                                scheduleStack.push(childSchedule);//####[139]####
                            }//####[140]####
                        } else if (time != 0.0) //####[141]####
                        {//####[141]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[142]####
                            childSchedule.update(n, i);//####[143]####
                            String id = childSchedule.generateId();//####[144]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[145]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[146]####
                            {//####[146]####
                                _prevSchedules.add(id);//####[147]####
                                scheduleStack.push(childSchedule);//####[148]####
                            }//####[149]####
                        }//####[150]####
                    }//####[151]####
                } else {//####[153]####
                    for (int i = 1; i <= _numProcessors; i++) //####[154]####
                    {//####[154]####
                        Node n = nodeMap.get(s);//####[155]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[157]####
                        childSchedule.update(n, i);//####[159]####
                        String id = childSchedule.generateId();//####[160]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[161]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[162]####
                        {//####[162]####
                            _prevSchedules.add(id);//####[163]####
                            scheduleStack.push(childSchedule);//####[164]####
                        }//####[165]####
                    }//####[166]####
                }//####[167]####
            }//####[168]####
            if (reachable.isEmpty()) //####[169]####
            {//####[169]####
                double[] solution = schedule.getEndTimes();//####[171]####
                double endTime = 0;//####[172]####
                for (int i = 0; i < solution.length; i++) //####[174]####
                {//####[174]####
                    if (solution[i] > endTime) //####[175]####
                    {//####[175]####
                        endTime = solution[i];//####[176]####
                    }//####[177]####
                }//####[178]####
                if (!bestTimes.contains(endTime)) //####[180]####
                {//####[180]####
                    bestTimes.add(endTime);//####[181]####
                    bestTimesCopy.add(endTime);//####[182]####
                    schedulesProcessed++;//####[183]####
                    fireSecondUpdate();//####[184]####
                }//####[185]####
                if (endTime < currentBestSolution) //####[187]####
                {//####[187]####
                    currentBestSolution = endTime;//####[188]####
                    bestSchedule.setCurrentBestState(schedule);//####[189]####
                }//####[190]####
            }//####[191]####
        } catch (EmptyStackException e) {//####[192]####
        }//####[194]####
    }//####[195]####
//####[201]####
    /**
	 * Set the number of threads to use for parallel executions.
	 * @param thread
	 *///####[201]####
    public void setThreads(int thread) {//####[201]####
        this.threadsToUse = thread;//####[202]####
    }//####[203]####
}//####[203]####
