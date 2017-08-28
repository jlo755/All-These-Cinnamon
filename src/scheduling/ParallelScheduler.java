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
            System.out.println((System.nanoTime() - startTime) / 1000000000.0);//####[59]####
        } catch (Exception e) {//####[60]####
            e.printStackTrace();//####[61]####
        }//####[62]####
    }//####[64]####
//####[70]####
    /**
	 * Processes a partial schedule from the stack through DFS. To be used
	 * in parallel computing.
	 *///####[70]####
    private void processSchedule(Stack<PartialSchedule> scheduleStack) {//####[70]####
        try {//####[71]####
            PartialSchedule schedule = scheduleStack.pop();//####[72]####
            ArrayList<String> reachable = schedule.getReachable();//####[73]####
            for (String s : reachable) //####[74]####
            {//####[74]####
                if (schedule.startTimeZeroProcessors() > 1) //####[75]####
                {//####[75]####
                    Node n = nodeMap.get(s);//####[76]####
                    boolean discovered = false;//####[77]####
                    for (int i = 1; i <= _numProcessors; i++) //####[78]####
                    {//####[78]####
                        double time = schedule.getProcessorTime(i);//####[79]####
                        if (time == 0.0 && !discovered) //####[80]####
                        {//####[80]####
                            discovered = true;//####[81]####
                            PartialSchedule childSchedule = schedule.makeChildSchedule();//####[82]####
                            childSchedule.update(n, i);//####[83]####
                            String id = childSchedule.generateId();//####[84]####
                            double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[85]####
                            if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[86]####
                            {//####[86]####
                                _prevSchedules.add(id);//####[87]####
                                scheduleStack.push(childSchedule);//####[88]####
                            }//####[89]####
                        }//####[90]####
                    }//####[91]####
                } else {//####[93]####
                    for (int i = 1; i <= _numProcessors; i++) //####[94]####
                    {//####[94]####
                        Node n = nodeMap.get(s);//####[95]####
                        PartialSchedule childSchedule = schedule.makeChildSchedule();//####[97]####
                        childSchedule.update(n, i);//####[99]####
                        String id = childSchedule.generateId();//####[100]####
                        double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[101]####
                        if (maxHeuristic < currentBestSolution && !_prevSchedules.contains(id)) //####[102]####
                        {//####[102]####
                            _prevSchedules.add(id);//####[103]####
                            scheduleStack.push(childSchedule);//####[104]####
                        }//####[105]####
                    }//####[106]####
                }//####[107]####
            }//####[108]####
            if (reachable.isEmpty()) //####[109]####
            {//####[109]####
                double[] solution = schedule.getEndTimes();//####[110]####
                double endTime = 0;//####[111]####
                for (int i = 0; i < solution.length; i++) //####[112]####
                {//####[112]####
                    if (solution[i] > endTime) //####[113]####
                    {//####[113]####
                        endTime = solution[i];//####[114]####
                    }//####[115]####
                }//####[116]####
                if (endTime < currentBestSolution) //####[117]####
                {//####[117]####
                    bestSchedule.setCurrentBestState(schedule);//####[118]####
                    currentBestSolution = endTime;//####[119]####
                }//####[120]####
            }//####[121]####
        } catch (EmptyStackException e) {//####[122]####
        }//####[124]####
    }//####[125]####
}//####[125]####
