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
        long startTime = System.nanoTime();//####[44]####
        for (PartialSchedule schedule : schedules) //####[45]####
        {//####[45]####
            scheduleStack.add(schedule);//####[46]####
        }//####[47]####
        TaskIDGroup g = new TaskIDGroup(threadsToUse);//####[50]####
        for (int i = 0; i < threadsToUse; i++) //####[52]####
        {//####[52]####
            TaskID id = processScheduleTask(scheduleStack);//####[53]####
            g.add(id);//####[54]####
        }//####[55]####
        try {//####[57]####
            g.waitTillFinished();//####[58]####
        } catch (Exception e) {//####[59]####
            e.printStackTrace();//####[60]####
        }//####[61]####
    }//####[63]####
//####[69]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[69]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[69]####
        try {//####[70]####
            PartialSchedule schedule = scheduleStack.pop();//####[71]####
            ArrayList<String> reachable = schedule.getReachable();//####[72]####
            for (String s : reachable) //####[73]####
            {//####[73]####
                if (schedule.startTimeZeroProcessors() > 1) //####[74]####
                {//####[74]####
                    Node n = nodeMap.get(s);//####[75]####
                    boolean discovered = false;//####[76]####
                    for (int i = 1; i <= _numProcessors; i++) //####[77]####
                    {//####[77]####
                        double time = schedule.getProcessorTime(i);//####[78]####
                        if (time == 0.0 && !discovered) //####[79]####
                        {//####[79]####
                            discovered = true;//####[80]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[81]####
                            childSchedule.update(n, i);//####[82]####
                            String id = childSchedule.generateId();//####[83]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[84]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[85]####
                            {//####[85]####
                                _prevSchedules.add(id);//####[86]####
                                scheduleStack.push(childSchedule);//####[87]####
                            }//####[88]####
                        }//####[89]####
                    }//####[90]####
                } else {//####[92]####
                    for (int i = 1; i <= _numProcessors; i++) //####[93]####
                    {//####[93]####
                        Node n = nodeMap.get(s);//####[94]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[96]####
                        childSchedule.update(n, i);//####[98]####
                        String id = childSchedule.generateId();//####[99]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[100]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[101]####
                        {//####[101]####
                            _prevSchedules.add(id);//####[102]####
                            scheduleStack.push(childSchedule);//####[103]####
                        }//####[104]####
                    }//####[105]####
                }//####[106]####
            }//####[107]####
            if (reachable.isEmpty()) //####[108]####
            {//####[108]####
                double[] solution = schedule.getEndTimes();//####[109]####
                double endTime = 0;//####[110]####
                for (int i = 0; i < solution.length; i++) //####[111]####
                {//####[111]####
                    if (solution[i] > endTime) //####[112]####
                    {//####[112]####
                        endTime = solution[i];//####[113]####
                    }//####[114]####
                }//####[115]####
                if (endTime < currentBestSolution) //####[116]####
                {//####[116]####
                    bestSchedule.setCurrentBestState(schedule);//####[117]####
                    currentBestSolution = endTime;//####[118]####
                }//####[119]####
            }//####[120]####
        } catch (EmptyStackException e) {//####[121]####
        }//####[123]####
    }//####[124]####
}//####[124]####
