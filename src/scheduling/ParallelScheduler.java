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
        TaskIDGroup g = new TaskIDGroup(threadsToUse);//####[48]####
        for (int i = 0; i < threadsToUse; i++) //####[50]####
        {//####[50]####
            TaskID id = processScheduleTask(scheduleStack);//####[51]####
            g.add(id);//####[52]####
        }//####[53]####
        try {//####[55]####
            g.waitTillFinished();//####[56]####
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[57]####
        } catch (Exception e) {//####[58]####
            e.printStackTrace();//####[59]####
        }//####[60]####
    }//####[62]####
//####[68]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[68]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[68]####
        try {//####[69]####
            PartialSchedule schedule = scheduleStack.pop();//####[70]####
            ArrayList<String> reachable = schedule.getReachable();//####[71]####
            for (String s : reachable) //####[72]####
            {//####[72]####
                if (schedule.startTimeZeroProcessors() > 1) //####[73]####
                {//####[73]####
                    Node n = nodeMap.get(s);//####[74]####
                    boolean discovered = false;//####[75]####
                    for (int i = 1; i <= _numProcessors; i++) //####[76]####
                    {//####[76]####
                        double time = schedule.getProcessorTime(i);//####[77]####
                        if (time == 0.0 && !discovered) //####[78]####
                        {//####[78]####
                            discovered = true;//####[79]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[80]####
                            childSchedule.update(n, i);//####[81]####
                            String id = childSchedule.generateId();//####[82]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[83]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[84]####
                            {//####[84]####
                                _prevSchedules.add(id);//####[85]####
                                scheduleStack.push(childSchedule);//####[86]####
                            }//####[87]####
                        }//####[88]####
                    }//####[89]####
                } else {//####[91]####
                    for (int i = 1; i <= _numProcessors; i++) //####[92]####
                    {//####[92]####
                        Node n = nodeMap.get(s);//####[93]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[95]####
                        childSchedule.update(n, i);//####[97]####
                        String id = childSchedule.generateId();//####[98]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[99]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[100]####
                        {//####[100]####
                            _prevSchedules.add(id);//####[101]####
                            scheduleStack.push(childSchedule);//####[102]####
                        }//####[103]####
                    }//####[104]####
                }//####[105]####
            }//####[106]####
            if (reachable.isEmpty()) //####[107]####
            {//####[107]####
                double[] solution = schedule.getEndTimes();//####[108]####
                double endTime = 0;//####[109]####
                for (int i = 0; i < solution.length; i++) //####[110]####
                {//####[110]####
                    if (solution[i] > endTime) //####[111]####
                    {//####[111]####
                        endTime = solution[i];//####[112]####
                    }//####[113]####
                }//####[114]####
                if (endTime < currentBestSolution) //####[115]####
                {//####[115]####
                    bestSchedule.setCurrentBestState(schedule);//####[116]####
                    currentBestSolution = endTime;//####[117]####
                }//####[118]####
            }//####[119]####
        } catch (EmptyStackException e) {//####[120]####
        }//####[122]####
    }//####[123]####
}//####[123]####
