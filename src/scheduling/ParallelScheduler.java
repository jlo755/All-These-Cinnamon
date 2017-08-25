package scheduling;//####[1]####
//####[1]####
import java.util.ArrayList;//####[2]####
import java.util.Comparator;//####[3]####
import java.util.HashMap;//####[4]####
import java.util.LinkedHashMap;//####[5]####
import java.util.PriorityQueue;//####[6]####
import java.util.Queue;//####[7]####
import java.util.Stack;//####[8]####
import java.util.concurrent.ForkJoinPool;//####[9]####
import dataStructure.Node;//####[11]####
import pt.runtime.TaskIDGroup;//####[12]####
//####[12]####
//-- ParaTask related imports//####[12]####
import pt.runtime.*;//####[12]####
import java.util.concurrent.ExecutionException;//####[12]####
import java.util.concurrent.locks.*;//####[12]####
import java.lang.reflect.*;//####[12]####
import pt.runtime.GuiThread;//####[12]####
import java.util.concurrent.BlockingQueue;//####[12]####
import java.util.ArrayList;//####[12]####
import java.util.List;//####[12]####
//####[12]####
/**
 * 
 * This class produces an optimal solution to a multi-processor 
 * scheduling problem. This is based on an input graph representing a 
 * series of tasks costs and their dependencies, as well as the 
 * number of processors specified. 
 *
 *///####[21]####
public class ParallelScheduler {//####[22]####
    static{ParaTask.init();}//####[22]####
    /*  ParaTask helper method to access private/protected slots *///####[22]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[22]####
        if (m.getParameterTypes().length == 0)//####[22]####
            m.invoke(instance);//####[22]####
        else if ((m.getParameterTypes().length == 1))//####[22]####
            m.invoke(instance, arg);//####[22]####
        else //####[22]####
            m.invoke(instance, arg, interResult);//####[22]####
    }//####[22]####
//####[24]####
    private double currentBestSolution;//####[24]####
//####[25]####
    private FinalState bestState;//####[25]####
//####[26]####
    private LinkedHashMap<String, Node> nodeMap;//####[26]####
//####[27]####
    private double sumAdding = 0;//####[27]####
//####[28]####
    private double totalTaskTime = 0;//####[28]####
//####[29]####
    private int _numProcessors;//####[29]####
//####[30]####
    private ArrayList<PartialSchedule> schedules = new ArrayList<PartialSchedule>();//####[30]####
//####[31]####
    private double time = 0;//####[31]####
//####[33]####
    private static final ForkJoinPool pool = new ForkJoinPool();//####[33]####
//####[38]####
    /**
	 * Initialize the best solution so far to infinity on starting.
	 *///####[38]####
    public ParallelScheduler() {//####[38]####
        currentBestSolution = Double.POSITIVE_INFINITY;//####[39]####
    }//####[40]####
//####[42]####
    private static volatile Method __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method = null;//####[42]####
    private synchronized static void __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet() {//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            try {//####[42]####
                __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processReachableTask", new Class[] {//####[42]####
                    Stack.class, PartialSchedule.class, String.class//####[42]####
                });//####[42]####
            } catch (Exception e) {//####[42]####
                e.printStackTrace();//####[42]####
            }//####[42]####
        }//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(0);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(1);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(1);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(0);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, String s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0, 1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(2);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(2);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setTaskIdArgIndexes(0, 1, 2);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(2);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, TaskID<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0, 1);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(2);//####[42]####
        taskinfo.addDependsOn(s);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(0);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, PartialSchedule schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0, 2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(1);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, TaskID<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0, 2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(1);//####[42]####
        taskinfo.addDependsOn(schedule);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(Stack<PartialSchedule> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1, 2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(TaskID<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(1, 2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setTaskIdArgIndexes(0);//####[42]####
        taskinfo.addDependsOn(scheduleStack);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s) {//####[42]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[42]####
        return processReachableTask(scheduleStack, schedule, s, new TaskInfo());//####[42]####
    }//####[42]####
    public TaskID<Void> processReachableTask(BlockingQueue<Stack<PartialSchedule>> scheduleStack, BlockingQueue<PartialSchedule> schedule, BlockingQueue<String> s, TaskInfo taskinfo) {//####[42]####
        // ensure Method variable is set//####[42]####
        if (__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method == null) {//####[42]####
            __pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_ensureMethodVarSet();//####[42]####
        }//####[42]####
        taskinfo.setQueueArgIndexes(0, 1, 2);//####[42]####
        taskinfo.setIsPipeline(true);//####[42]####
        taskinfo.setParameters(scheduleStack, schedule, s);//####[42]####
        taskinfo.setMethod(__pt__processReachableTask_StackPartialSchedule_PartialSchedule_String_method);//####[42]####
        taskinfo.setInstance(this);//####[42]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[42]####
    }//####[42]####
    public void __pt__processReachableTask(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, String s) {//####[42]####
        processReachable(scheduleStack, schedule, s);//####[43]####
    }//####[44]####
//####[44]####
//####[49]####
    /**
	 * Processes the input graph with DFS to calculate an optimal schedule.
	 *///####[49]####
    public void schedule() {//####[49]####
        for (Node n : nodeMap.values()) //####[50]####
        {//####[50]####
            totalTaskTime += n.getCost();//####[51]####
            n.setBottomLevel(findMaxBottomLevel(nodeMap, n.getID()));//####[52]####
        }//####[53]####
        System.out.println(currentBestSolution);//####[55]####
        for (Node n : nodeMap.values()) //####[58]####
        {//####[58]####
            if (n.getParents().isEmpty()) //####[61]####
            {//####[61]####
                PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[63]####
                schedule.decideIndex();//####[64]####
                schedule.solve(n, 1);//####[65]####
                schedules.add(schedule);//####[66]####
            }//####[70]####
        }//####[71]####
        dfs();//####[72]####
        System.out.println(time);//####[74]####
        System.out.println("Best Solution: " + currentBestSolution);//####[75]####
    }//####[76]####
//####[89]####
    /**
	 * Recursive approach to DFS for a given graph, used in search in the state
	 * space of the scheduling problem.
	 * 
	 *
	 * @param graph
	 *            This is the input graph
	 * @param nodeName
	 *            This is the node which we are starting off from
	 *///####[89]####
    public void dfs() {//####[89]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[92]####
        for (PartialSchedule schedule : schedules) //####[93]####
        {//####[93]####
            scheduleStack.add(schedule);//####[94]####
        }//####[95]####
        while (!scheduleStack.isEmpty()) //####[96]####
        {//####[96]####
            PartialSchedule schedule = scheduleStack.pop();//####[97]####
            ArrayList<String> reachable = schedule.getReachable();//####[98]####
            TaskIDGroup g = new TaskIDGroup(reachable.size());//####[100]####
            for (String s : reachable) //####[101]####
            {//####[101]####
                TaskID id = processReachableTask(scheduleStack, schedule, s);//####[102]####
                g.add(id);//####[103]####
            }//####[104]####
            try {//####[105]####
                g.waitTillFinished();//####[106]####
            } catch (Exception e) {//####[107]####
                e.printStackTrace();//####[108]####
            }//####[109]####
            if (reachable.isEmpty()) //####[111]####
            {//####[111]####
                double[] solution = schedule.getEndTimes();//####[112]####
                double endTime = 0;//####[113]####
                for (int i = 0; i < solution.length; i++) //####[114]####
                {//####[114]####
                    if (solution[i] > endTime) //####[115]####
                    {//####[115]####
                        endTime = solution[i];//####[116]####
                    }//####[117]####
                }//####[118]####
                if (endTime < currentBestSolution) //####[119]####
                {//####[119]####
                    currentBestSolution = endTime;//####[120]####
                }//####[121]####
            }//####[122]####
        }//####[123]####
    }//####[125]####
//####[127]####
    private void processReachable(Stack<PartialSchedule> scheduleStack, PartialSchedule schedule, String s) {//####[127]####
        if (schedule.startTimeZeroProcessors() > 1) //####[128]####
        {//####[128]####
            Node n = nodeMap.get(s);//####[129]####
            boolean discovered = false;//####[130]####
            for (int i = 1; i <= _numProcessors; i++) //####[131]####
            {//####[131]####
                double time = schedule.getProcessorTime(i);//####[132]####
                if (time == 0.0 && !discovered) //####[133]####
                {//####[133]####
                    discovered = true;//####[134]####
                    PartialSchedule childSchedule = schedule.makeChildSchedule();//####[135]####
                    childSchedule.solve(n, i);//####[136]####
                    double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[137]####
                    if (maxHeuristic < currentBestSolution) //####[138]####
                    {//####[138]####
                        scheduleStack.push(childSchedule);//####[139]####
                    }//####[140]####
                } else if (time != 0.0) //####[141]####
                {//####[141]####
                    PartialSchedule childSchedule = schedule.makeChildSchedule();//####[142]####
                    childSchedule.solve(n, i);//####[143]####
                    double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[144]####
                    if (maxHeuristic < currentBestSolution) //####[145]####
                    {//####[145]####
                        scheduleStack.push(childSchedule);//####[146]####
                    }//####[147]####
                } else {//####[148]####
                }//####[150]####
            }//####[151]####
        } else {//####[153]####
            for (int i = 1; i <= _numProcessors; i++) //####[154]####
            {//####[154]####
                Node n = nodeMap.get(s);//####[155]####
                PartialSchedule childSchedule = schedule.makeChildSchedule();//####[157]####
                childSchedule.solve(n, i);//####[160]####
                double maxHeuristic = childSchedule.getMaxHeuristic(n);//####[161]####
                if (maxHeuristic < currentBestSolution) //####[162]####
                {//####[162]####
                    scheduleStack.push(childSchedule);//####[163]####
                }//####[164]####
            }//####[165]####
        }//####[166]####
    }//####[167]####
//####[169]####
    public double findMaxBottomLevel(HashMap<String, Node> graph, String initialNode) {//####[169]####
        Stack<String> s = new Stack<String>();//####[170]####
        s.add(initialNode);//####[171]####
        double startTime = graph.get(initialNode).getStartTime();//####[172]####
        graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[173]####
        double max = 0;//####[174]####
        while (!s.isEmpty()) //####[175]####
        {//####[175]####
            Node node = graph.get(s.pop());//####[176]####
            if (node.getParents().keySet().contains(graph.get(initialNode))) //####[177]####
            {//####[177]####
                graph.get(initialNode).setOptimalBottomLevel(startTime + graph.get(initialNode).getCost());//####[178]####
            }//####[179]####
            for (Node child : node.getChildren().keySet()) //####[180]####
            {//####[180]####
                s.push(child.getID());//####[181]####
            }//####[182]####
            if (node.getID() != initialNode) //####[183]####
            {//####[183]####
                double test = 0;//####[184]####
                for (Node parent : node.getParents().keySet()) //####[185]####
                {//####[185]####
                    if (test < parent.getOptimalBottomLevel()) //####[186]####
                    {//####[186]####
                        test = parent.getOptimalBottomLevel();//####[187]####
                    }//####[188]####
                }//####[189]####
                node.setOptimalBottomLevel(test + node.getCost());//####[190]####
            }//####[191]####
        }//####[192]####
        for (Node n : graph.values()) //####[193]####
        {//####[193]####
            if (n.getOptimalBottomLevel() > max && n.getChildren().isEmpty()) //####[194]####
            {//####[194]####
                max = n.getOptimalBottomLevel();//####[195]####
            }//####[196]####
            n.setOptimalBottomLevel(0.0);//####[197]####
        }//####[198]####
        return max;//####[200]####
    }//####[201]####
//####[203]####
    public double findStartingOptimalBranch(HashMap<String, Node> graph) {//####[203]####
        Queue<Node> optimalBranch = new PriorityQueue<Node>();//####[204]####
        for (Node n : graph.values()) //####[205]####
        {//####[205]####
            optimalBranch.add(n);//####[206]####
        }//####[207]####
        Stack<PartialSchedule> scheduleStack = new Stack<PartialSchedule>();//####[208]####
        PartialSchedule schedule = new PartialSchedule(nodeMap, _numProcessors, totalTaskTime);//####[209]####
        schedule.decideIndex();//####[210]####
        scheduleStack.add(schedule);//####[212]####
        while (!optimalBranch.isEmpty()) //####[213]####
        {//####[213]####
            Node n = optimalBranch.poll();//####[215]####
            PartialSchedule scheduleOnStack = scheduleStack.pop();//####[216]####
            PartialSchedule[] partialSchedules = new PartialSchedule[_numProcessors];//####[217]####
            for (int i = 1; i <= _numProcessors; i++) //####[218]####
            {//####[218]####
                PartialSchedule childSchedule = scheduleOnStack.makeChildSchedule();//####[219]####
                childSchedule.solve(n, i);//####[220]####
                partialSchedules[i - 1] = childSchedule;//####[221]####
            }//####[222]####
            int minIndex = 0;//####[223]####
            Double min = Double.POSITIVE_INFINITY;//####[224]####
            for (int i = 1; i <= _numProcessors; i++) //####[225]####
            {//####[225]####
                PartialSchedule scheduleToCompare = partialSchedules[i - 1];//####[226]####
                if (min > scheduleToCompare.getNodeEndTime(n)) //####[227]####
                {//####[227]####
                    min = scheduleToCompare.getNodeEndTime(n);//####[228]####
                    minIndex = i;//####[229]####
                }//####[230]####
            }//####[231]####
            scheduleStack.push(partialSchedules[minIndex - 1]);//####[232]####
            if (optimalBranch.isEmpty()) //####[233]####
            {//####[233]####
                currentBestSolution = min;//####[234]####
                break;//####[235]####
            }//####[236]####
        }//####[237]####
        return 0.0;//####[238]####
    }//####[239]####
//####[246]####
    /**
	 * Returns the currentBestSolution field.
	 * 
	 * @return
	 *///####[246]####
    public double getCurrentBestSolution() {//####[246]####
        return currentBestSolution;//####[247]####
    }//####[248]####
//####[255]####
    /**
	 * Returns the bestState field.
	 * 
	 * @return
	 *///####[255]####
    public FinalState getBestState() {//####[255]####
        return bestState;//####[256]####
    }//####[257]####
//####[259]####
    public void setProcessorNumber(int processors) {//####[259]####
        _numProcessors = processors;//####[260]####
    }//####[261]####
//####[268]####
    /**
	 * Sets the bestState field.
	 * 
	 * @param newBestState
	 *///####[268]####
    public void setBestState(FinalState newBestState) {//####[268]####
        bestState = newBestState;//####[269]####
    }//####[270]####
//####[278]####
    /**
	 * Pass a task dependency graph in the form of a HashMap to the Scheduler to
	 * process.
	 * 
	 * @param taskGraph
	 *///####[278]####
    public void provideTaskGraph(LinkedHashMap<String, Node> taskGraph) {//####[278]####
        nodeMap = taskGraph;//####[279]####
        bestState = new FinalState(taskGraph);//####[280]####
    }//####[282]####
}//####[282]####
