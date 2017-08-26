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
//####[14]####
//-- ParaTask related imports//####[14]####
import pt.runtime.*;//####[14]####
import java.util.concurrent.ExecutionException;//####[14]####
import java.util.concurrent.locks.*;//####[14]####
import java.lang.reflect.*;//####[14]####
import pt.runtime.GuiThread;//####[14]####
import java.util.concurrent.BlockingQueue;//####[14]####
import java.util.ArrayList;//####[14]####
import java.util.List;//####[14]####
//####[14]####
/**
 *
 * This class produces an optimal solution to a multi-processor scheduling
 * problem. This is based on an input graph representing a series of tasks costs
 * and their dependencies, as well as the number of processors specified.
 *
 *///####[22]####
public class ParallelScheduler extends Scheduler {//####[23]####
    static{ParaTask.init();}//####[23]####
    /*  ParaTask helper method to access private/protected slots *///####[23]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[23]####
        if (m.getParameterTypes().length == 0)//####[23]####
            m.invoke(instance);//####[23]####
        else if ((m.getParameterTypes().length == 1))//####[23]####
            m.invoke(instance, arg);//####[23]####
        else //####[23]####
            m.invoke(instance, arg, interResult);//####[23]####
    }//####[23]####
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
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
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
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
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
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[30]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[30]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[30]####
    }//####[30]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
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
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[30]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[30]####
        while (!scheduleStack.isEmpty()) //####[31]####
        {//####[31]####
            processSchedule(scheduleStack);//####[32]####
        }//####[33]####
    }//####[34]####
//####[34]####
//####[47]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 *
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[47]####
    public void dfs() {//####[47]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[50]####
        for (PartialSchedule schedule : schedules) //####[51]####
        {//####[51]####
            scheduleStack.add(schedule);//####[52]####
        }//####[53]####
        TaskIDGroup g = new TaskIDGroup(2);//####[54]####
        for (int i = 0; i < 2; i++) //####[56]####
        {//####[56]####
            TaskID id = processScheduleTask(scheduleStack);//####[57]####
            g.add(id);//####[58]####
        }//####[59]####
        try {//####[61]####
            g.waitTillFinished();//####[62]####
        } catch (Exception e) {//####[63]####
            e.printStackTrace();//####[64]####
        }//####[65]####
    }//####[67]####
//####[73]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[73]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[73]####
        try {//####[74]####
            PartialSchedule schedule = scheduleStack.pop();//####[75]####
            ArrayList<String> reachable = schedule.getReachable();//####[76]####
            for (String s : reachable) //####[77]####
            {//####[77]####
                if (schedule.startTimeZeroProcessors() > 1) //####[78]####
                {//####[78]####
                    Node n = nodeMap.get(s);//####[79]####
                    boolean discovered = false;//####[80]####
                    for (int i = 1; i <= _numProcessors; i++) //####[81]####
                    {//####[81]####
                        double time = schedule.getProcessorTime(i);//####[82]####
                        if (time == 0.0 && !discovered) //####[83]####
                        {//####[83]####
                            discovered = true;//####[84]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[85]####
                            childSchedule.update(n, i);//####[86]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[87]####
                            if (maxHeuristic < currentBestSolution) //####[88]####
                            {//####[88]####
                                scheduleStack.push(childSchedule);//####[89]####
                            }//####[90]####
                        } else if (time != 0.0) //####[91]####
                        {//####[91]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[92]####
                            childSchedule.update(n, i);//####[93]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[94]####
                            if (maxHeuristic < currentBestSolution) //####[95]####
                            {//####[95]####
                                scheduleStack.push(childSchedule);//####[96]####
                            }//####[97]####
                        }//####[98]####
                    }//####[99]####
                } else {//####[101]####
                    for (int i = 1; i <= _numProcessors; i++) //####[102]####
                    {//####[102]####
                        Node n = nodeMap.get(s);//####[104]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[106]####
                        childSchedule.update(n, i);//####[108]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[109]####
                        if (maxHeuristic < currentBestSolution) //####[110]####
                        {//####[110]####
                            scheduleStack.push(childSchedule);//####[111]####
                        }//####[112]####
                    }//####[113]####
                }//####[114]####
            }//####[115]####
            if (reachable.isEmpty()) //####[116]####
            {//####[116]####
                double[] solution = schedule.getEndTimes();//####[118]####
                double endTime = 0;//####[119]####
                for (int i = 0; i < solution.length; i++) //####[120]####
                {//####[120]####
                    if (solution[i] > endTime) //####[121]####
                    {//####[121]####
                        endTime = solution[i];//####[122]####
                    }//####[123]####
                }//####[124]####
                if (endTime < currentBestSolution) //####[125]####
                {//####[125]####
                    bestSchedule.setCurrentBestState(schedule);//####[126]####
                    currentBestSolution = endTime;//####[127]####
                }//####[128]####
            }//####[129]####
        } catch (EmptyStackException e) {//####[130]####
        }//####[132]####
    }//####[133]####
}//####[133]####
