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
//####[35]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[35]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[35]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[35]####
            try {//####[35]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[35]####
                    Stack.class//####[35]####
                });//####[35]####
            } catch (Exception e) {//####[35]####
                e.printStackTrace();//####[35]####
            }//####[35]####
        }//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[35]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[35]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[35]####
        // ensure Method variable is set//####[35]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[35]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[35]####
        }//####[35]####
        taskinfo.setParameters(scheduleStack);//####[35]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[35]####
        taskinfo.setInstance(this);//####[35]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[35]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[35]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[35]####
        // ensure Method variable is set//####[35]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[35]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[35]####
        }//####[35]####
        taskinfo.setTaskIdArgIndexes(0);//####[35]####
        taskinfo.addDependsOn(scheduleStack);//####[35]####
        taskinfo.setParameters(scheduleStack);//####[35]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[35]####
        taskinfo.setInstance(this);//####[35]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[35]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[35]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[35]####
    }//####[35]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[35]####
        // ensure Method variable is set//####[35]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[35]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[35]####
        }//####[35]####
        taskinfo.setQueueArgIndexes(0);//####[35]####
        taskinfo.setIsPipeline(true);//####[35]####
        taskinfo.setParameters(scheduleStack);//####[35]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[35]####
        taskinfo.setInstance(this);//####[35]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[35]####
    }//####[35]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[35]####
        while (!scheduleStack.isEmpty()) //####[36]####
        {//####[36]####
            processSchedule(scheduleStack);//####[37]####
        }//####[38]####
    }//####[39]####
//####[39]####
//####[52]####
    /**
	 * Iterative approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem. This algorithm has been incorporated
	 * with data visualization. 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[52]####
    public void dfs() {//####[52]####
        threadsToUse = ScheduleFactory.getInstance().getParallelise();//####[53]####
        for (Node n : nodeMap.values()) //####[54]####
        {//####[54]####
            if (n.getParents().isEmpty()) //####[56]####
            {//####[56]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[58]####
                schedule.decideIndex();//####[59]####
                schedule.update(n, 1);//####[60]####
                schedules.add(schedule);//####[61]####
            }//####[63]####
        }//####[64]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[65]####
        for (PartialSchedule schedule : schedules) //####[66]####
        {//####[66]####
            scheduleStack.add(schedule);//####[67]####
        }//####[68]####
        TaskIDGroup g = new TaskIDGroup(threadsToUse);//####[69]####
        for (int i = 0; i < threadsToUse; i++) //####[71]####
        {//####[71]####
            TaskID id = processScheduleTask(scheduleStack);//####[72]####
            g.add(id);//####[73]####
        }//####[74]####
        try {//####[76]####
            time2.start();//####[77]####
            Time2.start();//####[78]####
            g.waitTillFinished();//####[79]####
            time2.stop();//####[80]####
            Time2.stop();//####[81]####
            fireBest();//####[82]####
            fireLabelUpdate();//####[83]####
            fireSecondUpdate();//####[84]####
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[85]####
            long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//####[86]####
            status = "Current Status: Finished";//####[88]####
            _vc.setStateLabel2(status, afterUsedMem / 1024 / 1024 + "MB");//####[89]####
        } catch (Exception e) {//####[90]####
            e.printStackTrace();//####[91]####
        }//####[92]####
    }//####[93]####
//####[99]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[99]####
    private synchronized void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[99]####
        try {//####[100]####
            schedulesProcessed++;//####[101]####
            PartialSchedule schedule = scheduleStack.pop();//####[102]####
            _currentSchedule = schedule;//####[103]####
            ArrayList<String> reachable = schedule.getReachable();//####[104]####
            for (String s : reachable) //####[105]####
            {//####[105]####
                if (schedule.startTimeZeroProcessors() > 1) //####[106]####
                {//####[106]####
                    Node n = nodeMap.get(s);//####[107]####
                    boolean discovered = false;//####[108]####
                    for (int i = 1; i <= _numProcessors; i++) //####[109]####
                    {//####[109]####
                        double time = schedule.getProcessorTime(i);//####[110]####
                        if (time == 0.0 && !discovered) //####[111]####
                        {//####[111]####
                            discovered = true;//####[112]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[113]####
                            childSchedule.update(n, i);//####[114]####
                            String id = childSchedule.generateId();//####[115]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[116]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[117]####
                            {//####[117]####
                                _prevSchedules.add(id);//####[118]####
                                scheduleStack.push(childSchedule);//####[119]####
                            }//####[120]####
                        } else if (time != 0.0) //####[121]####
                        {//####[121]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[122]####
                            childSchedule.update(n, i);//####[123]####
                            String id = childSchedule.generateId();//####[124]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[125]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[126]####
                            {//####[126]####
                                _prevSchedules.add(id);//####[127]####
                                scheduleStack.push(childSchedule);//####[128]####
                            }//####[129]####
                        }//####[130]####
                    }//####[131]####
                } else {//####[133]####
                    for (int i = 1; i <= _numProcessors; i++) //####[134]####
                    {//####[134]####
                        Node n = nodeMap.get(s);//####[135]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[137]####
                        childSchedule.update(n, i);//####[139]####
                        String id = childSchedule.generateId();//####[140]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[141]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[142]####
                        {//####[142]####
                            _prevSchedules.add(id);//####[143]####
                            scheduleStack.push(childSchedule);//####[144]####
                        }//####[145]####
                    }//####[146]####
                }//####[147]####
            }//####[148]####
            if (reachable.isEmpty()) //####[149]####
            {//####[149]####
                double[] solution = schedule.getEndTimes();//####[151]####
                double endTime = 0;//####[152]####
                for (int i = 0; i < solution.length; i++) //####[154]####
                {//####[154]####
                    if (solution[i] > endTime) //####[155]####
                    {//####[155]####
                        endTime = solution[i];//####[156]####
                    }//####[157]####
                }//####[158]####
                if (!bestTimes.contains(endTime)) //####[160]####
                {//####[160]####
                    bestTimes.add(endTime);//####[161]####
                    bestTimesCopy.add(endTime);//####[162]####
                    schedulesProcessed++;//####[163]####
                    fireSecondUpdate();//####[164]####
                }//####[165]####
                if (endTime < currentBestSolution) //####[167]####
                {//####[167]####
                    currentBestSolution = endTime;//####[168]####
                    bestSchedule.setCurrentBestState(schedule);//####[169]####
                }//####[170]####
            }//####[171]####
        } catch (EmptyStackException e) {//####[172]####
        }//####[174]####
    }//####[175]####
}//####[175]####
