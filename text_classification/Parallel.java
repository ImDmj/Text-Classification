package cluster;//####[1]####
//####[1]####
import java.io.File;//####[2]####
//####[2]####
//-- ParaTask related imports//####[2]####
import pt.runtime.*;//####[2]####
import java.util.concurrent.ExecutionException;//####[2]####
import java.util.concurrent.locks.*;//####[2]####
import java.lang.reflect.*;//####[2]####
import pt.runtime.GuiThread;//####[2]####
import java.util.concurrent.BlockingQueue;//####[2]####
import java.util.ArrayList;//####[2]####
import java.util.List;//####[2]####
//####[2]####
public class Parallel {//####[3]####
    static{ParaTask.init();}//####[3]####
    /*  ParaTask helper method to access private/protected slots *///####[3]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[3]####
        if (m.getParameterTypes().length == 0)//####[3]####
            m.invoke(instance);//####[3]####
        else if ((m.getParameterTypes().length == 1))//####[3]####
            m.invoke(instance, arg);//####[3]####
        else //####[3]####
            m.invoke(instance, arg, interResult);//####[3]####
    }//####[3]####
//####[4]####
    File file;//####[4]####
//####[5]####
    long start, end;//####[5]####
//####[6]####
    KMeansSMTP kalgo;//####[6]####
//####[7]####
    public Parallel(File file) {//####[7]####
        this.file = file;//####[8]####
    }//####[9]####
//####[10]####
    public void startRunning() {//####[10]####
        TaskInfo __pt__id = new TaskInfo();//####[11]####
//####[11]####
        boolean isEDT = GuiThread.isEventDispatchThread();//####[11]####
//####[11]####
//####[11]####
        /*  -- ParaTask notify clause for 'id' -- *///####[11]####
        try {//####[11]####
            Method __pt__id_slot_0 = null;//####[11]####
            __pt__id_slot_0 = ParaTaskHelper.getDeclaredMethod(getClass(), "finishedBuilding", new Class[] {});//####[11]####
            if (false) finishedBuilding(); //-- ParaTask uses this dummy statement to ensure the slot exists (otherwise Java compiler will complain)//####[11]####
            __pt__id.addSlotToNotify(new Slot(__pt__id_slot_0, this, false));//####[11]####
//####[11]####
        } catch(Exception __pt__e) { //####[11]####
            System.err.println("Problem registering method in clause:");//####[11]####
            __pt__e.printStackTrace();//####[11]####
        }//####[11]####
        TaskID id = runPSNM(__pt__id);//####[11]####
    }//####[12]####
//####[13]####
    private static volatile Method __pt__runPSNM__method = null;//####[13]####
    private synchronized static void __pt__runPSNM__ensureMethodVarSet() {//####[13]####
        if (__pt__runPSNM__method == null) {//####[13]####
            try {//####[13]####
                __pt__runPSNM__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__runPSNM", new Class[] {//####[13]####
                    //####[13]####
                });//####[13]####
            } catch (Exception e) {//####[13]####
                e.printStackTrace();//####[13]####
            }//####[13]####
        }//####[13]####
    }//####[13]####
    public TaskID<Void> runPSNM() {//####[13]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[13]####
        return runPSNM(new TaskInfo());//####[13]####
    }//####[13]####
    public TaskID<Void> runPSNM(TaskInfo taskinfo) {//####[13]####
        // ensure Method variable is set//####[13]####
        if (__pt__runPSNM__method == null) {//####[13]####
            __pt__runPSNM__ensureMethodVarSet();//####[13]####
        }//####[13]####
        taskinfo.setParameters();//####[13]####
        taskinfo.setMethod(__pt__runPSNM__method);//####[13]####
        taskinfo.setInstance(this);//####[13]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[13]####
    }//####[13]####
    public void __pt__runPSNM() {//####[13]####
        TaskID kmean = kmeans();//####[14]####
        TaskInfo __pt__view = new TaskInfo();//####[15]####
//####[15]####
        /*  -- ParaTask dependsOn clause for 'view' -- *///####[15]####
        __pt__view.addDependsOn(kmean);//####[15]####
//####[15]####
        TaskID view = showResult(__pt__view);//####[15]####
        try {//####[16]####
            view.waitTillFinished();//####[17]####
        } catch (Exception e) {//####[19]####
            e.printStackTrace();//####[20]####
        }//####[21]####
    }//####[22]####
//####[22]####
//####[23]####
    private static volatile Method __pt__kmeans__method = null;//####[23]####
    private synchronized static void __pt__kmeans__ensureMethodVarSet() {//####[23]####
        if (__pt__kmeans__method == null) {//####[23]####
            try {//####[23]####
                __pt__kmeans__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__kmeans", new Class[] {//####[23]####
                    //####[23]####
                });//####[23]####
            } catch (Exception e) {//####[23]####
                e.printStackTrace();//####[23]####
            }//####[23]####
        }//####[23]####
    }//####[23]####
    public TaskID<Void> kmeans() {//####[23]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[23]####
        return kmeans(new TaskInfo());//####[23]####
    }//####[23]####
    public TaskID<Void> kmeans(TaskInfo taskinfo) {//####[23]####
        // ensure Method variable is set//####[23]####
        if (__pt__kmeans__method == null) {//####[23]####
            __pt__kmeans__ensureMethodVarSet();//####[23]####
        }//####[23]####
        taskinfo.setParameters();//####[23]####
        taskinfo.setMethod(__pt__kmeans__method);//####[23]####
        taskinfo.setInstance(this);//####[23]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[23]####
    }//####[23]####
    public void __pt__kmeans() {//####[23]####
	try{
        kalgo = new KMeansSMTP();//####[25]####
        kalgo.clear();//####[26]####
        kalgo.readDocuments(file);//####[27]####
        System.out.println("file read");//####[28]####
        kalgo.generateVector();//####[29]####
		start = System.currentTimeMillis();//####[24]####
        System.out.println("vector");//####[30]####
        kalgo.kmeans();//####[31]####
        end = System.currentTimeMillis();//####[32]####
	}catch(Exception e){
		e.printStackTrace();
	}
    }//####[33]####
//####[33]####
//####[34]####
    private static volatile Method __pt__showResult__method = null;//####[34]####
    private synchronized static void __pt__showResult__ensureMethodVarSet() {//####[34]####
        if (__pt__showResult__method == null) {//####[34]####
            try {//####[34]####
                __pt__showResult__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__showResult", new Class[] {//####[34]####
                    //####[34]####
                });//####[34]####
            } catch (Exception e) {//####[34]####
                e.printStackTrace();//####[34]####
            }//####[34]####
        }//####[34]####
    }//####[34]####
    public TaskID<Void> showResult() {//####[34]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[34]####
        return showResult(new TaskInfo());//####[34]####
    }//####[34]####
    public TaskID<Void> showResult(TaskInfo taskinfo) {//####[34]####
        // ensure Method variable is set//####[34]####
        if (__pt__showResult__method == null) {//####[34]####
            __pt__showResult__ensureMethodVarSet();//####[34]####
        }//####[34]####
        taskinfo.setParameters();//####[34]####
        taskinfo.setMethod(__pt__showResult__method);//####[34]####
        taskinfo.setInstance(this);//####[34]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[34]####
    }//####[34]####
    public void __pt__showResult() {//####[34]####
        ViewCluster vc = new ViewCluster(kalgo.cls);//####[35]####
        vc.setSize(600, 400);//####[36]####
        vc.setVisible(true);//####[37]####
    }//####[38]####
//####[38]####
//####[39]####
    private void finishedBuilding() {//####[39]####
        System.out.println("over");//####[40]####
        System.out.println("Parallel " + (end - start));//####[41]####
    }//####[42]####
}//####[42]####
