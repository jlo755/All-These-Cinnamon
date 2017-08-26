package scheduling;//####[1]####
//####[1]####
import java.util.ArrayList;//####[3]####
import java.util.EmptyStackException;//####[4]####
import java.util.Stack;//####[5]####
import dataStructure.Node;//####[7]####
//####[7]####
//-- ParaTask related imports//####[7]####
import pt.runtime.*;//####[7]####
import java.util.concurrent.ExecutionException;//####[7]####
import java.util.concurrent.locks.*;//####[7]####
import java.lang.reflect.*;//####[7]####
import pt.runtime.GuiThread;//####[7]####
import java.util.concurrent.BlockingQueue;//####[7]####
import java.util.ArrayList;//####[7]####
import java.util.List;//####[7]####
//####[7]####
/**
 *
 * This class produces an optimal solution to a multi-processor scheduling
 * problem. This is based on an input graph representing a series of tasks costs
 * and their dependencies, as well as the number of processors specified.
 *
 *///####[15]####
public class ParallelScheduler extends Scheduler {//####[16]####
    static{ParaTask.init();}//####[16]####
    /*  ParaTask helper method to access private/protected slots *///####[16]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[16]####
        if (m.getParameterTypes().length == 0)//####[16]####
            m.invoke(instance);//####[16]####
        else if ((m.getParameterTypes().length == 1))//####[16]####
            m.invoke(instance, arg);//####[16]####
        else //####[16]####
            m.invoke(instance, arg, interResult);//####[16]####
    }//####[16]####
//####[23]####
    private static volatile Method __pt__processScheduleTask_StackPartialSchedule_method = null;//####[23]####
    private synchronized static void __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet() {//####[23]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[23]####
            try {//####[23]####
                __pt__processScheduleTask_StackPartialSchedule_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processScheduleTask", new Class[] {//####[23]####
                    Stack.class//####[23]####
                });//####[23]####
            } catch (Exception e) {//####[23]####
                e.printStackTrace();//####[23]####
            }//####[23]####
        }//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[23]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[23]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(Stack<PartialSchedule> scheduleStack, TaskInfo taskinfo) {//####[23]####
        // ensure Method variable is set//####[23]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[23]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[23]####
        }//####[23]####
        taskinfo.setParameters(scheduleStack);//####[23]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[23]####
        taskinfo.setInstance(this);//####[23]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack) {//####[23]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[23]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[23]####
        // ensure Method variable is set//####[23]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[23]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[23]####
        }//####[23]####
        taskinfo.setTaskIdArgIndexes(0);//####[23]####
        taskinfo.addDependsOn(scheduleStack);//####[23]####
        taskinfo.setParameters(scheduleStack);//####[23]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[23]####
        taskinfo.setInstance(this);//####[23]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack) {//####[23]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[23]####
        return processScheduleTask(scheduleStack, new TaskInfo());//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    private TaskIDGroup<Void> processScheduleTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskInfo taskinfo) {//####[23]####
        // ensure Method variable is set//####[23]####
        if (__pt__processScheduleTask_StackPartialSchedule_method == null) {//####[23]####
            __pt__processScheduleTask_StackPartialSchedule_ensureMethodVarSet();//####[23]####
        }//####[23]####
        taskinfo.setQueueArgIndexes(0);//####[23]####
        taskinfo.setIsPipeline(true);//####[23]####
        taskinfo.setParameters(scheduleStack);//####[23]####
        taskinfo.setMethod(__pt__processScheduleTask_StackPartialSchedule_method);//####[23]####
        taskinfo.setInstance(this);//####[23]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[23]####
    }//####[23]####
    /**
	 * Parallel multi-task version of processSchedule(). This should enable
	 * parallel running of the processSchedule() method when calculating
	 * an optimal scheduling solution.
	 *///####[23]####
    public void __pt__processScheduleTask(Stack<PartialSchedule> scheduleStack) {//####[23]####
        while (!scheduleStack.isEmpty()) //####[24]####
        {//####[24]####
            processSchedule(scheduleStack);//####[25]####
        }//####[26]####
    }//####[27]####
//####[27]####
//####[40]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 *
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[40]####
    public void dfs() {//####[40]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[43]####
        for (PartialSchedule schedule : schedules) //####[44]####
        {//####[44]####
            scheduleStack.add(schedule);//####[45]####
        }//####[46]####
        TaskIDGroup g = new TaskIDGroup(2);//####[47]####
        for (int i = 0; i < 2; i++) //####[49]####
        {//####[49]####
            TaskID id = processScheduleTask(scheduleStack);//####[50]####
            g.add(id);//####[51]####
        }//####[52]####
        try {//####[54]####
            g.waitTillFinished();//####[55]####
        } catch (Exception e) {//####[56]####
            e.printStackTrace();//####[57]####
        }//####[58]####
    }//####[60]####
//####[66]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[66]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[66]####
        try {//####[67]####
            PartialSchedule schedule = scheduleStack.pop();//####[68]####
            ArrayList<String> reachable = schedule.getReachable();//####[69]####
            for (String s : reachable) //####[70]####
            {//####[70]####
                if (schedule.startTimeZeroProcessors() > 1) //####[71]####
                {//####[71]####
                    Node n = nodeMap.get(s);//####[72]####
                    boolean discovered = false;//####[73]####
                    for (int i = 1; i <= _numProcessors; i++) //####[74]####
                    {//####[74]####
                        double time = schedule.getProcessorTime(i);//####[75]####
                        if (time == 0.0 && !discovered) //####[76]####
                        {//####[76]####
                            discovered = true;//####[77]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[78]####
                            childSchedule.update(n, i);//####[79]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[80]####
                            if (maxHeuristic < currentBestSolution) //####[81]####
                            {//####[81]####
                                scheduleStack.push(childSchedule);//####[82]####
                            }//####[83]####
                        } else if (time != 0.0) //####[84]####
                        {//####[84]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[85]####
                            childSchedule.update(n, i);//####[86]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[87]####
                            if (maxHeuristic < currentBestSolution) //####[88]####
                            {//####[88]####
                                scheduleStack.push(childSchedule);//####[89]####
                            }//####[90]####
                        }//####[91]####
                    }//####[92]####
                } else {//####[94]####
                    for (int i = 1; i <= _numProcessors; i++) //####[95]####
                    {//####[95]####
                        Node n = nodeMap.get(s);//####[97]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[99]####
                        childSchedule.update(n, i);//####[101]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[102]####
                        if (maxHeuristic < currentBestSolution) //####[103]####
                        {//####[103]####
                            scheduleStack.push(childSchedule);//####[104]####
                        }//####[105]####
                    }//####[106]####
                }//####[107]####
            }//####[108]####
            if (reachable.isEmpty()) //####[109]####
            {//####[109]####
                double[] solution = schedule.getEndTimes();//####[111]####
                double endTime = 0;//####[112]####
                for (int i = 0; i < solution.length; i++) //####[113]####
                {//####[113]####
                    if (solution[i] > endTime) //####[114]####
                    {//####[114]####
                        endTime = solution[i];//####[115]####
                    }//####[116]####
                }//####[117]####
                if (endTime < currentBestSolution) //####[118]####
                {//####[118]####
                    bestSchedule.setCurrentBestState(schedule);//####[119]####
                    currentBestSolution = endTime;//####[120]####
                }//####[121]####
            }//####[122]####
        } catch (EmptyStackException e) {//####[123]####
        }//####[125]####
    }//####[126]####
}//####[126]####
