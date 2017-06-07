package Processing;//####[1]####
//####[1]####
import java.awt.BorderLayout;//####[3]####
import java.awt.Color;//####[4]####
import java.awt.event.ActionEvent;//####[5]####
import java.awt.event.ActionListener;//####[6]####
import java.io.IOException;//####[7]####
import java.io.File;//####[8]####
import javax.swing.JButton;//####[9]####
import javax.swing.JFrame;//####[10]####
import javax.swing.JOptionPane;//####[11]####
import javax.swing.SwingUtilities;//####[12]####
import java.util.ArrayList;//####[13]####
import javax.swing.SwingWorker;//####[14]####
import controllers.*;//####[16]####
import pt.runtime.TaskID;//####[17]####
import pt.runtime.TaskIDGroup;//####[18]####
import java.nio.file.Files;//####[20]####
import java.nio.file.Paths;//####[21]####
import GUI.UI;//####[22]####
import java.util.*;//####[24]####
//####[24]####
//-- ParaTask related imports//####[24]####
import pt.runtime.*;//####[24]####
import java.util.concurrent.ExecutionException;//####[24]####
import java.util.concurrent.locks.*;//####[24]####
import java.lang.reflect.*;//####[24]####
import pt.runtime.GuiThread;//####[24]####
import java.util.concurrent.BlockingQueue;//####[24]####
import java.util.ArrayList;//####[24]####
import java.util.List;//####[24]####
//####[24]####
public class ParallelProcessor extends SwingWorker<Void, Integer> {//####[26]####
    static{ParaTask.init();}//####[26]####
    /*  ParaTask helper method to access private/protected slots *///####[26]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[26]####
        if (m.getParameterTypes().length == 0)//####[26]####
            m.invoke(instance);//####[26]####
        else if ((m.getParameterTypes().length == 1))//####[26]####
            m.invoke(instance, arg);//####[26]####
        else //####[26]####
            m.invoke(instance, arg, interResult);//####[26]####
    }//####[26]####
//####[28]####
    private ArrayList<String> a = new ArrayList<String>();//####[28]####
//####[29]####
    private Iterator<String> subVideoNames;//####[29]####
//####[30]####
    public long startTime;//####[30]####
//####[31]####
    public long time;//####[31]####
//####[32]####
    private String filter;//####[32]####
//####[33]####
    private UI ui;//####[33]####
//####[34]####
    private int id;//####[34]####
//####[35]####
    private String file;//####[35]####
//####[36]####
    private String outputFile;//####[36]####
//####[38]####
    public ParallelProcessor(String file, String filter, UI ui, int id) {//####[38]####
        this.file = file;//####[39]####
        String ext = "";//####[40]####
        int i = file.lastIndexOf('.');//####[41]####
        if (i > 0) //####[42]####
        {//####[42]####
            ext += file.substring(i + 1);//####[43]####
        }//####[44]####
        outputFile = ui.outputVideo + "." + ext;//####[45]####
        this.filter = filter;//####[46]####
        this.ui = ui;//####[47]####
        this.id = id;//####[48]####
    }//####[49]####
//####[52]####
    @Override//####[52]####
    public void done() {//####[52]####
        ui.Paraprocessors.remove(this);//####[53]####
        ui.progressBars.get(id).setValue(100);//####[54]####
        time = System.currentTimeMillis() - startTime;//####[55]####
        System.out.println("Video filtering for video " + id + " took " + (time / 1000) + " seconds.");//####[56]####
        JOptionPane.showMessageDialog(ui, "Finished Saving Video: " + id + "\nTime taken: " + (time / 1000) + " seconds.");//####[57]####
        System.out.println(id);//####[58]####
        System.out.println("done!!!!!");//####[59]####
        ui.progressBars.remove(id);//####[60]####
        ui.processingInfo.remove(id);//####[61]####
    }//####[62]####
//####[64]####
    public void addFilter(VideoFilter processor, String filter) {//####[64]####
        processor.initializeFilter(filter);//####[65]####
        processor.start();//####[66]####
    }//####[67]####
//####[69]####
    private static volatile Method __pt__startSpliting_String_method = null;//####[69]####
    private synchronized static void __pt__startSpliting_String_ensureMethodVarSet() {//####[69]####
        if (__pt__startSpliting_String_method == null) {//####[69]####
            try {//####[69]####
                __pt__startSpliting_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startSpliting", new Class[] {//####[69]####
                    String.class//####[69]####
                });//####[69]####
            } catch (Exception e) {//####[69]####
                e.printStackTrace();//####[69]####
            }//####[69]####
        }//####[69]####
    }//####[69]####
    public TaskID<Void> startSpliting(String file) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return startSpliting(file, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskID<Void> startSpliting(String file, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__startSpliting_String_method == null) {//####[70]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setParameters(file);//####[70]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[70]####
    }//####[70]####
    public TaskID<Void> startSpliting(TaskID<String> file) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return startSpliting(file, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskID<Void> startSpliting(TaskID<String> file, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__startSpliting_String_method == null) {//####[70]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setTaskIdArgIndexes(0);//####[70]####
        taskinfo.addDependsOn(file);//####[70]####
        taskinfo.setParameters(file);//####[70]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[70]####
    }//####[70]####
    public TaskID<Void> startSpliting(BlockingQueue<String> file) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return startSpliting(file, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskID<Void> startSpliting(BlockingQueue<String> file, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__startSpliting_String_method == null) {//####[70]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setQueueArgIndexes(0);//####[70]####
        taskinfo.setIsPipeline(true);//####[70]####
        taskinfo.setParameters(file);//####[70]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[70]####
    }//####[70]####
    public void __pt__startSpliting(String file) {//####[70]####
        try {//####[71]####
            System.out.println("** Going to wait for the tasks...");//####[72]####
            TaskID id1 = splitVideo(file);//####[73]####
            TaskIDGroup g = new TaskIDGroup(1);//####[74]####
            g.add(id1);//####[75]####
            g.waitTillFinished();//####[77]####
            System.out.println("Done");//####[79]####
        } catch (Exception ee) {//####[81]####
        }//####[82]####
    }//####[83]####
//####[83]####
//####[85]####
    private static volatile Method __pt__splitVideo_String_method = null;//####[85]####
    private synchronized static void __pt__splitVideo_String_ensureMethodVarSet() {//####[85]####
        if (__pt__splitVideo_String_method == null) {//####[85]####
            try {//####[85]####
                __pt__splitVideo_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__splitVideo", new Class[] {//####[85]####
                    String.class//####[85]####
                });//####[85]####
            } catch (Exception e) {//####[85]####
                e.printStackTrace();//####[85]####
            }//####[85]####
        }//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> splitVideo(String fileName) throws IOException {//####[86]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[86]####
        return splitVideo(fileName, new TaskInfo());//####[86]####
    }//####[86]####
    public TaskIDGroup<Void> splitVideo(String fileName, TaskInfo taskinfo) throws IOException {//####[86]####
        // ensure Method variable is set//####[86]####
        if (__pt__splitVideo_String_method == null) {//####[86]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[86]####
        }//####[86]####
        taskinfo.setParameters(fileName);//####[86]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[86]####
        taskinfo.setInstance(this);//####[86]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[86]####
    }//####[86]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName) throws IOException {//####[86]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[86]####
        return splitVideo(fileName, new TaskInfo());//####[86]####
    }//####[86]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName, TaskInfo taskinfo) throws IOException {//####[86]####
        // ensure Method variable is set//####[86]####
        if (__pt__splitVideo_String_method == null) {//####[86]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[86]####
        }//####[86]####
        taskinfo.setTaskIdArgIndexes(0);//####[86]####
        taskinfo.addDependsOn(fileName);//####[86]####
        taskinfo.setParameters(fileName);//####[86]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[86]####
        taskinfo.setInstance(this);//####[86]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[86]####
    }//####[86]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName) throws IOException {//####[86]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[86]####
        return splitVideo(fileName, new TaskInfo());//####[86]####
    }//####[86]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName, TaskInfo taskinfo) throws IOException {//####[86]####
        // ensure Method variable is set//####[86]####
        if (__pt__splitVideo_String_method == null) {//####[86]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[86]####
        }//####[86]####
        taskinfo.setQueueArgIndexes(0);//####[86]####
        taskinfo.setIsPipeline(true);//####[86]####
        taskinfo.setParameters(fileName);//####[86]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[86]####
        taskinfo.setInstance(this);//####[86]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[86]####
    }//####[86]####
    public void __pt__splitVideo(String fileName) throws IOException {//####[86]####
        VideoSpliter vs = new VideoSpliter(fileName, id);//####[87]####
        String duration = vs.getVideoDuration(fileName);//####[90]####
        int durationInMs = vs.transferDuration(duration);//####[91]####
        int partitionedInMs = vs.partition(durationInMs, Runtime.getRuntime().availableProcessors());//####[92]####
        String partitionedDur = vs.transferMsToDuration(partitionedInMs);//####[93]####
        Iterator<String> it = vs.generateCommandLines(Runtime.getRuntime().availableProcessors(), partitionedInMs, partitionedDur).iterator();//####[95]####
        vs.doRealSplittingWork(it);//####[96]####
    }//####[98]####
//####[98]####
//####[100]####
    private static volatile Method __pt__getVideoFiles__method = null;//####[100]####
    private synchronized static void __pt__getVideoFiles__ensureMethodVarSet() {//####[100]####
        if (__pt__getVideoFiles__method == null) {//####[100]####
            try {//####[100]####
                __pt__getVideoFiles__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__getVideoFiles", new Class[] {//####[100]####
                    //####[100]####
                });//####[100]####
            } catch (Exception e) {//####[100]####
                e.printStackTrace();//####[100]####
            }//####[100]####
        }//####[100]####
    }//####[100]####
    public TaskID<Void> getVideoFiles() throws IOException {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return getVideoFiles(new TaskInfo());//####[101]####
    }//####[101]####
    public TaskID<Void> getVideoFiles(TaskInfo taskinfo) throws IOException {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__getVideoFiles__method == null) {//####[101]####
            __pt__getVideoFiles__ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setParameters();//####[101]####
        taskinfo.setMethod(__pt__getVideoFiles__method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    public void __pt__getVideoFiles() throws IOException {//####[101]####
        System.out.println("start");//####[103]####
        File[] listOfFiles = new File("SubVideos").listFiles();//####[104]####
        ArrayList<String> videoNames = new ArrayList<String>();//####[105]####
        for (File listOfFile : listOfFiles) //####[106]####
        {//####[106]####
            if (listOfFile.isFile()) //####[107]####
            {//####[108]####
                videoNames.add(listOfFile.getName());//####[109]####
                System.out.println(listOfFile.getName());//####[110]####
            }//####[111]####
        }//####[112]####
        subVideoNames = videoNames.iterator();//####[113]####
    }//####[114]####
//####[114]####
//####[116]####
    private static volatile Method __pt__startFiltering_String_method = null;//####[116]####
    private synchronized static void __pt__startFiltering_String_ensureMethodVarSet() {//####[116]####
        if (__pt__startFiltering_String_method == null) {//####[116]####
            try {//####[116]####
                __pt__startFiltering_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startFiltering", new Class[] {//####[116]####
                    String.class//####[116]####
                });//####[116]####
            } catch (Exception e) {//####[116]####
                e.printStackTrace();//####[116]####
            }//####[116]####
        }//####[116]####
    }//####[116]####
    public TaskID<Void> startFiltering(String filter) {//####[117]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[117]####
        return startFiltering(filter, new TaskInfo());//####[117]####
    }//####[117]####
    public TaskID<Void> startFiltering(String filter, TaskInfo taskinfo) {//####[117]####
        // ensure Method variable is set//####[117]####
        if (__pt__startFiltering_String_method == null) {//####[117]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[117]####
        }//####[117]####
        taskinfo.setParameters(filter);//####[117]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[117]####
        taskinfo.setInstance(this);//####[117]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[117]####
    }//####[117]####
    public TaskID<Void> startFiltering(TaskID<String> filter) {//####[117]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[117]####
        return startFiltering(filter, new TaskInfo());//####[117]####
    }//####[117]####
    public TaskID<Void> startFiltering(TaskID<String> filter, TaskInfo taskinfo) {//####[117]####
        // ensure Method variable is set//####[117]####
        if (__pt__startFiltering_String_method == null) {//####[117]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[117]####
        }//####[117]####
        taskinfo.setTaskIdArgIndexes(0);//####[117]####
        taskinfo.addDependsOn(filter);//####[117]####
        taskinfo.setParameters(filter);//####[117]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[117]####
        taskinfo.setInstance(this);//####[117]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[117]####
    }//####[117]####
    public TaskID<Void> startFiltering(BlockingQueue<String> filter) {//####[117]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[117]####
        return startFiltering(filter, new TaskInfo());//####[117]####
    }//####[117]####
    public TaskID<Void> startFiltering(BlockingQueue<String> filter, TaskInfo taskinfo) {//####[117]####
        // ensure Method variable is set//####[117]####
        if (__pt__startFiltering_String_method == null) {//####[117]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[117]####
        }//####[117]####
        taskinfo.setQueueArgIndexes(0);//####[117]####
        taskinfo.setIsPipeline(true);//####[117]####
        taskinfo.setParameters(filter);//####[117]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[117]####
        taskinfo.setInstance(this);//####[117]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[117]####
    }//####[117]####
    public void __pt__startFiltering(String filter) {//####[117]####
        try {//####[118]####
            TaskID id2 = addFilterToSubVideos(filter);//####[119]####
            TaskIDGroup gg = new TaskIDGroup(1);//####[120]####
            gg.add(id2);//####[121]####
            gg.waitTillFinished();//####[122]####
            System.out.println("** Finished...");//####[123]####
        } catch (Exception ee) {//####[125]####
        }//####[126]####
    }//####[128]####
//####[128]####
//####[130]####
    private static volatile Method __pt__addFilterToSubVideos_String_method = null;//####[130]####
    private synchronized static void __pt__addFilterToSubVideos_String_ensureMethodVarSet() {//####[130]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[130]####
            try {//####[130]####
                __pt__addFilterToSubVideos_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__addFilterToSubVideos", new Class[] {//####[130]####
                    String.class//####[130]####
                });//####[130]####
            } catch (Exception e) {//####[130]####
                e.printStackTrace();//####[130]####
            }//####[130]####
        }//####[130]####
    }//####[130]####
    public TaskIDGroup<Void> addFilterToSubVideos(String filter) {//####[131]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[131]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[131]####
    }//####[131]####
    public TaskIDGroup<Void> addFilterToSubVideos(String filter, TaskInfo taskinfo) {//####[131]####
        // ensure Method variable is set//####[131]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[131]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[131]####
        }//####[131]####
        taskinfo.setParameters(filter);//####[131]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[131]####
        taskinfo.setInstance(this);//####[131]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[131]####
    }//####[131]####
    public TaskIDGroup<Void> addFilterToSubVideos(TaskID<String> filter) {//####[131]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[131]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[131]####
    }//####[131]####
    public TaskIDGroup<Void> addFilterToSubVideos(TaskID<String> filter, TaskInfo taskinfo) {//####[131]####
        // ensure Method variable is set//####[131]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[131]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[131]####
        }//####[131]####
        taskinfo.setTaskIdArgIndexes(0);//####[131]####
        taskinfo.addDependsOn(filter);//####[131]####
        taskinfo.setParameters(filter);//####[131]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[131]####
        taskinfo.setInstance(this);//####[131]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[131]####
    }//####[131]####
    public TaskIDGroup<Void> addFilterToSubVideos(BlockingQueue<String> filter) {//####[131]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[131]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[131]####
    }//####[131]####
    public TaskIDGroup<Void> addFilterToSubVideos(BlockingQueue<String> filter, TaskInfo taskinfo) {//####[131]####
        // ensure Method variable is set//####[131]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[131]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[131]####
        }//####[131]####
        taskinfo.setQueueArgIndexes(0);//####[131]####
        taskinfo.setIsPipeline(true);//####[131]####
        taskinfo.setParameters(filter);//####[131]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[131]####
        taskinfo.setInstance(this);//####[131]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Runtime.getRuntime().availableProcessors());//####[131]####
    }//####[131]####
    public void __pt__addFilterToSubVideos(String filter) {//####[131]####
        while (subVideoNames.hasNext()) //####[132]####
        addFilter(new VideoFilter("SubVideos/" + subVideoNames.next(), ui, id), filter);//####[133]####
    }//####[134]####
//####[134]####
//####[136]####
    private static volatile Method __pt__combine__method = null;//####[136]####
    private synchronized static void __pt__combine__ensureMethodVarSet() {//####[136]####
        if (__pt__combine__method == null) {//####[136]####
            try {//####[136]####
                __pt__combine__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__combine", new Class[] {//####[136]####
                    //####[136]####
                });//####[136]####
            } catch (Exception e) {//####[136]####
                e.printStackTrace();//####[136]####
            }//####[136]####
        }//####[136]####
    }//####[136]####
    public TaskID<Void> combine() {//####[137]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[137]####
        return combine(new TaskInfo());//####[137]####
    }//####[137]####
    public TaskID<Void> combine(TaskInfo taskinfo) {//####[137]####
        // ensure Method variable is set//####[137]####
        if (__pt__combine__method == null) {//####[137]####
            __pt__combine__ensureMethodVarSet();//####[137]####
        }//####[137]####
        taskinfo.setParameters();//####[137]####
        taskinfo.setMethod(__pt__combine__method);//####[137]####
        taskinfo.setInstance(this);//####[137]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[137]####
    }//####[137]####
    public void __pt__combine() {//####[137]####
        VideoCombiner vc = new VideoCombiner(outputFile, id);//####[138]####
        vc.combine();//####[139]####
    }//####[141]####
//####[141]####
//####[143]####
    private static volatile Method __pt__startCombine__method = null;//####[143]####
    private synchronized static void __pt__startCombine__ensureMethodVarSet() {//####[143]####
        if (__pt__startCombine__method == null) {//####[143]####
            try {//####[143]####
                __pt__startCombine__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startCombine", new Class[] {//####[143]####
                    //####[143]####
                });//####[143]####
            } catch (Exception e) {//####[143]####
                e.printStackTrace();//####[143]####
            }//####[143]####
        }//####[143]####
    }//####[143]####
    public TaskID<Void> startCombine() {//####[144]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[144]####
        return startCombine(new TaskInfo());//####[144]####
    }//####[144]####
    public TaskID<Void> startCombine(TaskInfo taskinfo) {//####[144]####
        // ensure Method variable is set//####[144]####
        if (__pt__startCombine__method == null) {//####[144]####
            __pt__startCombine__ensureMethodVarSet();//####[144]####
        }//####[144]####
        taskinfo.setParameters();//####[144]####
        taskinfo.setMethod(__pt__startCombine__method);//####[144]####
        taskinfo.setInstance(this);//####[144]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[144]####
    }//####[144]####
    public void __pt__startCombine() {//####[144]####
        try {//####[145]####
            System.out.println("Start Combining.......");//####[146]####
            TaskID id10 = combine();//####[147]####
            TaskIDGroup gggggggg = new TaskIDGroup(1);//####[148]####
            gggggggg.add(id10);//####[149]####
            gggggggg.waitTillFinished();//####[151]####
            System.out.println("Combine Finished.......");//####[153]####
        } catch (Exception ee) {//####[155]####
        }//####[156]####
    }//####[159]####
//####[159]####
//####[161]####
    private static volatile Method __pt__recordSubVideoNames__method = null;//####[161]####
    private synchronized static void __pt__recordSubVideoNames__ensureMethodVarSet() {//####[161]####
        if (__pt__recordSubVideoNames__method == null) {//####[161]####
            try {//####[161]####
                __pt__recordSubVideoNames__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__recordSubVideoNames", new Class[] {//####[161]####
                    //####[161]####
                });//####[161]####
            } catch (Exception e) {//####[161]####
                e.printStackTrace();//####[161]####
            }//####[161]####
        }//####[161]####
    }//####[161]####
    public TaskID<Void> recordSubVideoNames() {//####[162]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[162]####
        return recordSubVideoNames(new TaskInfo());//####[162]####
    }//####[162]####
    public TaskID<Void> recordSubVideoNames(TaskInfo taskinfo) {//####[162]####
        // ensure Method variable is set//####[162]####
        if (__pt__recordSubVideoNames__method == null) {//####[162]####
            __pt__recordSubVideoNames__ensureMethodVarSet();//####[162]####
        }//####[162]####
        taskinfo.setParameters();//####[162]####
        taskinfo.setMethod(__pt__recordSubVideoNames__method);//####[162]####
        taskinfo.setInstance(this);//####[162]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[162]####
    }//####[162]####
    public void __pt__recordSubVideoNames() {//####[162]####
        try {//####[163]####
            TaskID id4 = getVideoFiles();//####[164]####
            TaskIDGroup gg = new TaskIDGroup(1);//####[165]####
            gg.add(id4);//####[166]####
            gg.waitTillFinished();//####[167]####
            System.out.println("** Finished saving files ...");//####[168]####
        } catch (Exception ee) {//####[170]####
        }//####[171]####
    }//####[172]####
//####[172]####
//####[175]####
    @Override//####[175]####
    protected Void doInBackground() throws Exception {//####[175]####
        startTime = System.currentTimeMillis();//####[176]####
        TaskID split = startSpliting(file);//####[177]####
        split.waitTillFinished();//####[178]####
        TaskInfo __pt__recordNames = new TaskInfo();//####[180]####
//####[180]####
        /*  -- ParaTask dependsOn clause for 'recordNames' -- *///####[180]####
        __pt__recordNames.addDependsOn(split);//####[180]####
//####[180]####
        TaskID recordNames = recordSubVideoNames(__pt__recordNames);//####[180]####
        recordNames.waitTillFinished();//####[181]####
        TaskInfo __pt__filtered = new TaskInfo();//####[182]####
//####[182]####
        /*  -- ParaTask dependsOn clause for 'filtered' -- *///####[182]####
        __pt__filtered.addDependsOn(recordNames);//####[182]####
//####[182]####
        TaskID filtered = startFiltering(filter, __pt__filtered);//####[182]####
        filtered.waitTillFinished();//####[183]####
        TaskInfo __pt__combined = new TaskInfo();//####[184]####
//####[184]####
        /*  -- ParaTask dependsOn clause for 'combined' -- *///####[184]####
        __pt__combined.addDependsOn(filtered);//####[184]####
//####[184]####
        TaskID combined = startCombine(__pt__combined);//####[184]####
        combined.waitTillFinished();//####[185]####
        long endTime = System.currentTimeMillis();//####[187]####
        long totalTime = endTime - startTime;//####[188]####
        System.out.println("Duration: " + totalTime + " ms");//####[189]####
        return null;//####[191]####
    }//####[192]####
}//####[192]####
