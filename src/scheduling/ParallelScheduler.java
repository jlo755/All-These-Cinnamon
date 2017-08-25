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
import pt.runtime.TaskIDGroup;//####[15]####
//####[15]####
//-- ParaTask related imports//####[15]####
import pt.runtime.*;//####[15]####
import java.util.concurrent.ExecutionException;//####[15]####
import java.util.concurrent.locks.*;//####[15]####
import java.lang.reflect.*;//####[15]####
import pt.runtime.GuiThread;//####[15]####
import java.util.concurrent.BlockingQueue;//####[15]####
import java.util.ArrayList;//####[15]####
import java.util.List;//####[15]####
//####[15]####
/**
 * 
 * This class produces an optimal solution to a multi-processor scheduling
 * problem. This is based on an input graph representing a series of tasks costs
 * and their dependencies, as well as the number of processors specified.
 *
 *///####[23]####
public class ParallelScheduler extends Scheduler {//####[24]####
    static{ParaTask.init();}//####[24]####
    /*  ParaTask helper method to access private/protected slots *///####[24]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[24]####
        if (m.getParameterTypes().length == 0)//####[24]####
            m.invoke(instance);//####[24]####
        else if ((m.getParameterTypes().length == 1))//####[24]####
            m.invoke(instance, arg);//####[24]####
        else //####[24]####
            m.invoke(instance, arg, interResult);//####[24]####
    }//####[24]####
//####[31]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[31]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[31]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[31]####
            try {//####[31]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[31]####
                    Stack.class//####[31]####
                });//####[31]####
            } catch (Exception e) {//####[31]####
                e.printStackTrace();//####[31]####
            }//####[31]####
        }//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[31]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[31]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[31]####
        // ensure Method variable is set//####[31]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[31]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[31]####
        }//####[31]####
        taskinfo.setParameters(scheduleStack);//####[31]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[31]####
        taskinfo.setInstance(this);//####[31]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[31]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[31]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[31]####
        // ensure Method variable is set//####[31]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[31]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[31]####
        }//####[31]####
        taskinfo.setTaskIdArgIndexes(0);//####[31]####
        taskinfo.addDependsOn(scheduleStack);//####[31]####
        taskinfo.setParameters(scheduleStack);//####[31]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[31]####
        taskinfo.setInstance(this);//####[31]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[31]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[31]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[31]####
        // ensure Method variable is set//####[31]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[31]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[31]####
        }//####[31]####
        taskinfo.setQueueArgIndexes(0);//####[31]####
        taskinfo.setIsPipeline(true);//####[31]####
        taskinfo.setParameters(scheduleStack);//####[31]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[31]####
        taskinfo.setInstance(this);//####[31]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[31]####
    }//####[31]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable 
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[31]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[31]####
        while (!scheduleStack.isEmpty()) //####[32]####
        {//####[32]####
            processSchedule(scheduleStack);//####[33]####
        }//####[34]####
    }//####[35]####
//####[35]####
//####[48]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[48]####
    public void dfs() {//####[48]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[51]####
        for (PartialSchedule schedule : schedules) //####[52]####
        {//####[52]####
            scheduleStack.add(schedule);//####[53]####
        }//####[54]####
        TaskIDGroup g = new TaskIDGroup(2);//####[55]####
        for (int i = 0; i < 2; i++) //####[57]####
        {//####[57]####
            TaskID id = processScheduleTask(scheduleStack);//####[58]####
            g.add(id);//####[59]####
        }//####[60]####
        try {//####[62]####
            g.waitTillFinished();//####[63]####
        } catch (Exception e) {//####[64]####
            e.printStackTrace();//####[65]####
        }//####[66]####
    }//####[68]####
//####[74]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[74]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[74]####
        try {//####[75]####
            PartialSchedule schedule = scheduleStack.pop();//####[76]####
            ArrayList<String> reachable = schedule.getReachable();//####[77]####
            for (String s : reachable) //####[78]####
            {//####[78]####
                if (schedule.startTimeZeroProcessors() > 1) //####[79]####
                {//####[79]####
                    Node n = nodeMap.get(s);//####[80]####
                    boolean discovered = false;//####[81]####
                    for (int i = 1; i <= _numProcessors; i++) //####[82]####
                    {//####[82]####
                        double time = schedule.getProcessorTime(i);//####[83]####
                        if (time == 0.0 && !discovered) //####[84]####
                        {//####[84]####
                            discovered = true;//####[85]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[86]####
                            childSchedule.solve(n, i);//####[87]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[88]####
                            if (maxHeuristic < currentBestSolution) //####[89]####
                            {//####[89]####
                                scheduleStack.push(childSchedule);//####[90]####
                            }//####[91]####
                        } else if (time != 0.0) //####[92]####
                        {//####[92]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[93]####
                            childSchedule.solve(n, i);//####[94]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[95]####
                            if (maxHeuristic < currentBestSolution) //####[96]####
                            {//####[96]####
                                scheduleStack.push(childSchedule);//####[97]####
                            }//####[98]####
                        }//####[99]####
                    }//####[100]####
                } else {//####[102]####
                    for (int i = 1; i <= _numProcessors; i++) //####[103]####
                    {//####[103]####
                        Node n = nodeMap.get(s);//####[105]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[107]####
                        childSchedule.solve(n, i);//####[109]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[110]####
                        if (maxHeuristic < currentBestSolution) //####[111]####
                        {//####[111]####
                            scheduleStack.push(childSchedule);//####[112]####
                        }//####[113]####
                    }//####[114]####
                }//####[115]####
            }//####[116]####
            if (reachable.isEmpty()) //####[117]####
            {//####[117]####
                double[] solution = schedule.getEndTimes();//####[119]####
                double endTime = 0;//####[120]####
                for (int i = 0; i < solution.length; i++) //####[121]####
                {//####[121]####
                    if (solution[i] > endTime) //####[122]####
                    {//####[122]####
                        endTime = solution[i];//####[123]####
                    }//####[124]####
                }//####[125]####
                if (endTime < currentBestSolution) //####[126]####
                {//####[126]####
                    currentBestSolution = endTime;//####[127]####
                }//####[128]####
            }//####[129]####
        } catch (EmptyStackException e) {//####[130]####
        }//####[132]####
    }//####[133]####
}//####[133]####
