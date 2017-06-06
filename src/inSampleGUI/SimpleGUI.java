package inSampleGUI;//####[1]####
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
import controllers.*;//####[15]####
import pt.runtime.TaskIDGroup;//####[16]####
import java.nio.file.Files;//####[18]####
import java.nio.file.Paths;//####[19]####
import java.util.*;//####[21]####
//####[21]####
//-- ParaTask related imports//####[21]####
import pt.runtime.*;//####[21]####
import java.util.concurrent.ExecutionException;//####[21]####
import java.util.concurrent.locks.*;//####[21]####
import java.lang.reflect.*;//####[21]####
import pt.runtime.GuiThread;//####[21]####
import java.util.concurrent.BlockingQueue;//####[21]####
import java.util.ArrayList;//####[21]####
import java.util.List;//####[21]####
//####[21]####
public class SimpleGUI extends JFrame implements ActionListener {//####[23]####
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
//####[25]####
    private JButton btnGo = new JButton("Go!");//####[25]####
//####[26]####
    private JButton btnCheckResponsiveness = new JButton("Responsive?");//####[26]####
//####[27]####
    private ArrayList<String> a = new ArrayList<String>();//####[27]####
//####[28]####
    private Iterator<String> subVideoNames;//####[28]####
//####[29]####
    public long startTime;//####[29]####
//####[31]####
    public SimpleGUI() {//####[31]####
        super("Simple GUI example");//####[32]####
        setSize(400, 300);//####[33]####
        btnGo.addActionListener(this);//####[34]####
        btnCheckResponsiveness.addActionListener(this);//####[35]####
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//####[36]####
        add(btnGo, BorderLayout.SOUTH);//####[37]####
        add(btnCheckResponsiveness, BorderLayout.CENTER);//####[38]####
    }//####[39]####
//####[41]####
    public void done() {//####[41]####
        JOptionPane.showMessageDialog(SimpleGUI.this, "Done!");//####[42]####
    }//####[43]####
//####[45]####
    public void addFilter(FilterProcessor processor, String filter) {//####[45]####
        processor.initializeFilter(filter);//####[47]####
        processor.start();//####[48]####
    }//####[50]####
//####[52]####
    public void actionPerformed(ActionEvent e) {//####[52]####
        String filter = "colorchannelmixer=.3:.4:.3:0:.3:.4:.3:0:.3:.4:.3";//####[54]####
        if (e.getSource() == btnCheckResponsiveness) //####[55]####
        {//####[56]####
            btnCheckResponsiveness.setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));//####[57]####
        } else {//####[59]####
            startTime = System.currentTimeMillis();//####[60]####
            TaskID splited = startSpliting("mv.mp4");//####[61]####
            TaskInfo __pt__recordNames = new TaskInfo();//####[62]####
//####[62]####
            /*  -- ParaTask dependsOn clause for 'recordNames' -- *///####[62]####
            __pt__recordNames.addDependsOn(splited);//####[62]####
//####[62]####
            TaskID recordNames = recordSubVideoNames(__pt__recordNames);//####[62]####
            TaskInfo __pt__filtered = new TaskInfo();//####[63]####
//####[63]####
            /*  -- ParaTask dependsOn clause for 'filtered' -- *///####[63]####
            __pt__filtered.addDependsOn(recordNames);//####[63]####
//####[63]####
            TaskID filtered = startFiltering(filter, __pt__filtered);//####[63]####
            TaskInfo __pt__combined = new TaskInfo();//####[64]####
//####[64]####
            /*  -- ParaTask dependsOn clause for 'combined' -- *///####[64]####
            __pt__combined.addDependsOn(filtered);//####[64]####
//####[64]####
            TaskID combined = startCombine(__pt__combined);//####[64]####
        }//####[68]####
    }//####[69]####
//####[71]####
    public static void main(String[] agrs) {//####[71]####
        SwingUtilities.invokeLater(new Runnable() {//####[71]####
//####[73]####
            public void run() {//####[73]####
                SimpleGUI frame = new SimpleGUI();//####[74]####
                frame.setVisible(true);//####[75]####
            }//####[76]####
        });//####[76]####
    }//####[78]####
//####[80]####
    private static volatile Method __pt__addFilterToSubVideos_String_method = null;//####[80]####
    private synchronized static void __pt__addFilterToSubVideos_String_ensureMethodVarSet() {//####[80]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[80]####
            try {//####[80]####
                __pt__addFilterToSubVideos_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__addFilterToSubVideos", new Class[] {//####[80]####
                    String.class//####[80]####
                });//####[80]####
            } catch (Exception e) {//####[80]####
                e.printStackTrace();//####[80]####
            }//####[80]####
        }//####[80]####
    }//####[80]####
    public TaskIDGroup<Void> addFilterToSubVideos(String filter) {//####[81]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[81]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[81]####
    }//####[81]####
    public TaskIDGroup<Void> addFilterToSubVideos(String filter, TaskInfo taskinfo) {//####[81]####
        // ensure Method variable is set//####[81]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[81]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[81]####
        }//####[81]####
        taskinfo.setParameters(filter);//####[81]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[81]####
        taskinfo.setInstance(this);//####[81]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[81]####
    }//####[81]####
    public TaskIDGroup<Void> addFilterToSubVideos(TaskID<String> filter) {//####[81]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[81]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[81]####
    }//####[81]####
    public TaskIDGroup<Void> addFilterToSubVideos(TaskID<String> filter, TaskInfo taskinfo) {//####[81]####
        // ensure Method variable is set//####[81]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[81]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[81]####
        }//####[81]####
        taskinfo.setTaskIdArgIndexes(0);//####[81]####
        taskinfo.addDependsOn(filter);//####[81]####
        taskinfo.setParameters(filter);//####[81]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[81]####
        taskinfo.setInstance(this);//####[81]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[81]####
    }//####[81]####
    public TaskIDGroup<Void> addFilterToSubVideos(BlockingQueue<String> filter) {//####[81]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[81]####
        return addFilterToSubVideos(filter, new TaskInfo());//####[81]####
    }//####[81]####
    public TaskIDGroup<Void> addFilterToSubVideos(BlockingQueue<String> filter, TaskInfo taskinfo) {//####[81]####
        // ensure Method variable is set//####[81]####
        if (__pt__addFilterToSubVideos_String_method == null) {//####[81]####
            __pt__addFilterToSubVideos_String_ensureMethodVarSet();//####[81]####
        }//####[81]####
        taskinfo.setQueueArgIndexes(0);//####[81]####
        taskinfo.setIsPipeline(true);//####[81]####
        taskinfo.setParameters(filter);//####[81]####
        taskinfo.setMethod(__pt__addFilterToSubVideos_String_method);//####[81]####
        taskinfo.setInstance(this);//####[81]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[81]####
    }//####[81]####
    public void __pt__addFilterToSubVideos(String filter) {//####[81]####
        while (subVideoNames.hasNext()) //####[83]####
        addFilter(new FilterProcessor("SubVideos/" + subVideoNames.next()), filter);//####[84]####
    }//####[85]####
//####[85]####
//####[88]####
    private static volatile Method __pt__splitVideo_String_method = null;//####[88]####
    private synchronized static void __pt__splitVideo_String_ensureMethodVarSet() {//####[88]####
        if (__pt__splitVideo_String_method == null) {//####[88]####
            try {//####[88]####
                __pt__splitVideo_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__splitVideo", new Class[] {//####[88]####
                    String.class//####[88]####
                });//####[88]####
            } catch (Exception e) {//####[88]####
                e.printStackTrace();//####[88]####
            }//####[88]####
        }//####[88]####
    }//####[88]####
    public TaskIDGroup<Void> splitVideo(String fileName) throws IOException {//####[89]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[89]####
        return splitVideo(fileName, new TaskInfo());//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> splitVideo(String fileName, TaskInfo taskinfo) throws IOException {//####[89]####
        // ensure Method variable is set//####[89]####
        if (__pt__splitVideo_String_method == null) {//####[89]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[89]####
        }//####[89]####
        taskinfo.setParameters(fileName);//####[89]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[89]####
        taskinfo.setInstance(this);//####[89]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName) throws IOException {//####[89]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[89]####
        return splitVideo(fileName, new TaskInfo());//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName, TaskInfo taskinfo) throws IOException {//####[89]####
        // ensure Method variable is set//####[89]####
        if (__pt__splitVideo_String_method == null) {//####[89]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[89]####
        }//####[89]####
        taskinfo.setTaskIdArgIndexes(0);//####[89]####
        taskinfo.addDependsOn(fileName);//####[89]####
        taskinfo.setParameters(fileName);//####[89]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[89]####
        taskinfo.setInstance(this);//####[89]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName) throws IOException {//####[89]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[89]####
        return splitVideo(fileName, new TaskInfo());//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName, TaskInfo taskinfo) throws IOException {//####[89]####
        // ensure Method variable is set//####[89]####
        if (__pt__splitVideo_String_method == null) {//####[89]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[89]####
        }//####[89]####
        taskinfo.setQueueArgIndexes(0);//####[89]####
        taskinfo.setIsPipeline(true);//####[89]####
        taskinfo.setParameters(fileName);//####[89]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[89]####
        taskinfo.setInstance(this);//####[89]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[89]####
    }//####[89]####
    public void __pt__splitVideo(String fileName) throws IOException {//####[89]####
        VideoSpliter vs = new VideoSpliter(fileName);//####[90]####
        String duration = vs.getVideoDuration(fileName);//####[93]####
        int durationInMs = vs.transferDuration(duration);//####[94]####
        int partitionedInMs = vs.partition(durationInMs, 4);//####[95]####
        String partitionedDur = vs.transferMsToDuration(partitionedInMs);//####[96]####
        Iterator<String> it = vs.generateCommandLines(4, partitionedInMs, partitionedDur).iterator();//####[98]####
        vs.doRealSplittingWork(it);//####[99]####
    }//####[101]####
//####[101]####
//####[103]####
    private static volatile Method __pt__getVideoFiles__method = null;//####[103]####
    private synchronized static void __pt__getVideoFiles__ensureMethodVarSet() {//####[103]####
        if (__pt__getVideoFiles__method == null) {//####[103]####
            try {//####[103]####
                __pt__getVideoFiles__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__getVideoFiles", new Class[] {//####[103]####
                    //####[103]####
                });//####[103]####
            } catch (Exception e) {//####[103]####
                e.printStackTrace();//####[103]####
            }//####[103]####
        }//####[103]####
    }//####[103]####
    public TaskID<Void> getVideoFiles() throws IOException {//####[104]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[104]####
        return getVideoFiles(new TaskInfo());//####[104]####
    }//####[104]####
    public TaskID<Void> getVideoFiles(TaskInfo taskinfo) throws IOException {//####[104]####
        // ensure Method variable is set//####[104]####
        if (__pt__getVideoFiles__method == null) {//####[104]####
            __pt__getVideoFiles__ensureMethodVarSet();//####[104]####
        }//####[104]####
        taskinfo.setParameters();//####[104]####
        taskinfo.setMethod(__pt__getVideoFiles__method);//####[104]####
        taskinfo.setInstance(this);//####[104]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[104]####
    }//####[104]####
    public void __pt__getVideoFiles() throws IOException {//####[104]####
        System.out.println("start");//####[106]####
        File[] listOfFiles = new File("SubVideos").listFiles();//####[107]####
        ArrayList<String> videoNames = new ArrayList<String>();//####[108]####
        for (File listOfFile : listOfFiles) //####[109]####
        {//####[109]####
            if (listOfFile.isFile()) //####[110]####
            {//####[111]####
                videoNames.add(listOfFile.getName());//####[112]####
                System.out.println(listOfFile.getName());//####[113]####
            }//####[114]####
        }//####[115]####
        subVideoNames = videoNames.iterator();//####[116]####
    }//####[117]####
//####[117]####
//####[119]####
    private static volatile Method __pt__startCombine__method = null;//####[119]####
    private synchronized static void __pt__startCombine__ensureMethodVarSet() {//####[119]####
        if (__pt__startCombine__method == null) {//####[119]####
            try {//####[119]####
                __pt__startCombine__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startCombine", new Class[] {//####[119]####
                    //####[119]####
                });//####[119]####
            } catch (Exception e) {//####[119]####
                e.printStackTrace();//####[119]####
            }//####[119]####
        }//####[119]####
    }//####[119]####
    public TaskID<Void> startCombine() {//####[120]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[120]####
        return startCombine(new TaskInfo());//####[120]####
    }//####[120]####
    public TaskID<Void> startCombine(TaskInfo taskinfo) {//####[120]####
        // ensure Method variable is set//####[120]####
        if (__pt__startCombine__method == null) {//####[120]####
            __pt__startCombine__ensureMethodVarSet();//####[120]####
        }//####[120]####
        taskinfo.setParameters();//####[120]####
        taskinfo.setMethod(__pt__startCombine__method);//####[120]####
        taskinfo.setInstance(this);//####[120]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[120]####
    }//####[120]####
    public void __pt__startCombine() {//####[120]####
        try {//####[121]####
            System.out.println("Start Combining.......");//####[122]####
            TaskID id10 = combine();//####[123]####
            TaskIDGroup gggggggg = new TaskIDGroup(1);//####[124]####
            gggggggg.add(id10);//####[125]####
            gggggggg.waitTillFinished();//####[127]####
            System.out.println("Combine Finished.......");//####[129]####
        } catch (Exception ee) {//####[131]####
        }//####[132]####
        long endTime = System.currentTimeMillis();//####[134]####
        long totalTime = endTime - startTime;//####[135]####
        System.out.println("Duration: " + totalTime + " ms");//####[136]####
    }//####[137]####
//####[137]####
//####[139]####
    private static volatile Method __pt__combine__method = null;//####[139]####
    private synchronized static void __pt__combine__ensureMethodVarSet() {//####[139]####
        if (__pt__combine__method == null) {//####[139]####
            try {//####[139]####
                __pt__combine__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__combine", new Class[] {//####[139]####
                    //####[139]####
                });//####[139]####
            } catch (Exception e) {//####[139]####
                e.printStackTrace();//####[139]####
            }//####[139]####
        }//####[139]####
    }//####[139]####
    public TaskID<Void> combine() {//####[140]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[140]####
        return combine(new TaskInfo());//####[140]####
    }//####[140]####
    public TaskID<Void> combine(TaskInfo taskinfo) {//####[140]####
        // ensure Method variable is set//####[140]####
        if (__pt__combine__method == null) {//####[140]####
            __pt__combine__ensureMethodVarSet();//####[140]####
        }//####[140]####
        taskinfo.setParameters();//####[140]####
        taskinfo.setMethod(__pt__combine__method);//####[140]####
        taskinfo.setInstance(this);//####[140]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[140]####
    }//####[140]####
    public void __pt__combine() {//####[140]####
        VideoCombiner vc = new VideoCombiner();//####[141]####
        vc.combine();//####[142]####
    }//####[143]####
//####[143]####
//####[147]####
    private static volatile Method __pt__startSpliting_String_method = null;//####[147]####
    private synchronized static void __pt__startSpliting_String_ensureMethodVarSet() {//####[147]####
        if (__pt__startSpliting_String_method == null) {//####[147]####
            try {//####[147]####
                __pt__startSpliting_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startSpliting", new Class[] {//####[147]####
                    String.class//####[147]####
                });//####[147]####
            } catch (Exception e) {//####[147]####
                e.printStackTrace();//####[147]####
            }//####[147]####
        }//####[147]####
    }//####[147]####
    public TaskID<Void> startSpliting(String file) {//####[148]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[148]####
        return startSpliting(file, new TaskInfo());//####[148]####
    }//####[148]####
    public TaskID<Void> startSpliting(String file, TaskInfo taskinfo) {//####[148]####
        // ensure Method variable is set//####[148]####
        if (__pt__startSpliting_String_method == null) {//####[148]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[148]####
        }//####[148]####
        taskinfo.setParameters(file);//####[148]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[148]####
        taskinfo.setInstance(this);//####[148]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[148]####
    }//####[148]####
    public TaskID<Void> startSpliting(TaskID<String> file) {//####[148]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[148]####
        return startSpliting(file, new TaskInfo());//####[148]####
    }//####[148]####
    public TaskID<Void> startSpliting(TaskID<String> file, TaskInfo taskinfo) {//####[148]####
        // ensure Method variable is set//####[148]####
        if (__pt__startSpliting_String_method == null) {//####[148]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[148]####
        }//####[148]####
        taskinfo.setTaskIdArgIndexes(0);//####[148]####
        taskinfo.addDependsOn(file);//####[148]####
        taskinfo.setParameters(file);//####[148]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[148]####
        taskinfo.setInstance(this);//####[148]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[148]####
    }//####[148]####
    public TaskID<Void> startSpliting(BlockingQueue<String> file) {//####[148]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[148]####
        return startSpliting(file, new TaskInfo());//####[148]####
    }//####[148]####
    public TaskID<Void> startSpliting(BlockingQueue<String> file, TaskInfo taskinfo) {//####[148]####
        // ensure Method variable is set//####[148]####
        if (__pt__startSpliting_String_method == null) {//####[148]####
            __pt__startSpliting_String_ensureMethodVarSet();//####[148]####
        }//####[148]####
        taskinfo.setQueueArgIndexes(0);//####[148]####
        taskinfo.setIsPipeline(true);//####[148]####
        taskinfo.setParameters(file);//####[148]####
        taskinfo.setMethod(__pt__startSpliting_String_method);//####[148]####
        taskinfo.setInstance(this);//####[148]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[148]####
    }//####[148]####
    public void __pt__startSpliting(String file) {//####[148]####
        try {//####[149]####
            TaskID id1 = splitVideo(file);//####[150]####
            TaskIDGroup g = new TaskIDGroup(1);//####[151]####
            g.add(id1);//####[152]####
            System.out.println("** Going to wait for the tasks...");//####[153]####
            g.waitTillFinished();//####[154]####
            System.out.println("Done");//####[156]####
        } catch (Exception ee) {//####[158]####
        }//####[159]####
    }//####[161]####
//####[161]####
//####[163]####
    private static volatile Method __pt__startFiltering_String_method = null;//####[163]####
    private synchronized static void __pt__startFiltering_String_ensureMethodVarSet() {//####[163]####
        if (__pt__startFiltering_String_method == null) {//####[163]####
            try {//####[163]####
                __pt__startFiltering_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__startFiltering", new Class[] {//####[163]####
                    String.class//####[163]####
                });//####[163]####
            } catch (Exception e) {//####[163]####
                e.printStackTrace();//####[163]####
            }//####[163]####
        }//####[163]####
    }//####[163]####
    public TaskID<Void> startFiltering(String filter) {//####[164]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[164]####
        return startFiltering(filter, new TaskInfo());//####[164]####
    }//####[164]####
    public TaskID<Void> startFiltering(String filter, TaskInfo taskinfo) {//####[164]####
        // ensure Method variable is set//####[164]####
        if (__pt__startFiltering_String_method == null) {//####[164]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[164]####
        }//####[164]####
        taskinfo.setParameters(filter);//####[164]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[164]####
        taskinfo.setInstance(this);//####[164]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[164]####
    }//####[164]####
    public TaskID<Void> startFiltering(TaskID<String> filter) {//####[164]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[164]####
        return startFiltering(filter, new TaskInfo());//####[164]####
    }//####[164]####
    public TaskID<Void> startFiltering(TaskID<String> filter, TaskInfo taskinfo) {//####[164]####
        // ensure Method variable is set//####[164]####
        if (__pt__startFiltering_String_method == null) {//####[164]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[164]####
        }//####[164]####
        taskinfo.setTaskIdArgIndexes(0);//####[164]####
        taskinfo.addDependsOn(filter);//####[164]####
        taskinfo.setParameters(filter);//####[164]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[164]####
        taskinfo.setInstance(this);//####[164]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[164]####
    }//####[164]####
    public TaskID<Void> startFiltering(BlockingQueue<String> filter) {//####[164]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[164]####
        return startFiltering(filter, new TaskInfo());//####[164]####
    }//####[164]####
    public TaskID<Void> startFiltering(BlockingQueue<String> filter, TaskInfo taskinfo) {//####[164]####
        // ensure Method variable is set//####[164]####
        if (__pt__startFiltering_String_method == null) {//####[164]####
            __pt__startFiltering_String_ensureMethodVarSet();//####[164]####
        }//####[164]####
        taskinfo.setQueueArgIndexes(0);//####[164]####
        taskinfo.setIsPipeline(true);//####[164]####
        taskinfo.setParameters(filter);//####[164]####
        taskinfo.setMethod(__pt__startFiltering_String_method);//####[164]####
        taskinfo.setInstance(this);//####[164]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[164]####
    }//####[164]####
    public void __pt__startFiltering(String filter) {//####[164]####
        try {//####[165]####
            TaskID id2 = addFilterToSubVideos(filter);//####[166]####
            TaskIDGroup gg = new TaskIDGroup(1);//####[167]####
            gg.add(id2);//####[168]####
            gg.waitTillFinished();//####[169]####
            System.out.println("** Finished...");//####[170]####
        } catch (Exception ee) {//####[172]####
        }//####[173]####
    }//####[175]####
//####[175]####
//####[177]####
    private static volatile Method __pt__recordSubVideoNames__method = null;//####[177]####
    private synchronized static void __pt__recordSubVideoNames__ensureMethodVarSet() {//####[177]####
        if (__pt__recordSubVideoNames__method == null) {//####[177]####
            try {//####[177]####
                __pt__recordSubVideoNames__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__recordSubVideoNames", new Class[] {//####[177]####
                    //####[177]####
                });//####[177]####
            } catch (Exception e) {//####[177]####
                e.printStackTrace();//####[177]####
            }//####[177]####
        }//####[177]####
    }//####[177]####
    public TaskID<Void> recordSubVideoNames() {//####[178]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[178]####
        return recordSubVideoNames(new TaskInfo());//####[178]####
    }//####[178]####
    public TaskID<Void> recordSubVideoNames(TaskInfo taskinfo) {//####[178]####
        // ensure Method variable is set//####[178]####
        if (__pt__recordSubVideoNames__method == null) {//####[178]####
            __pt__recordSubVideoNames__ensureMethodVarSet();//####[178]####
        }//####[178]####
        taskinfo.setParameters();//####[178]####
        taskinfo.setMethod(__pt__recordSubVideoNames__method);//####[178]####
        taskinfo.setInstance(this);//####[178]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[178]####
    }//####[178]####
    public void __pt__recordSubVideoNames() {//####[178]####
        try {//####[179]####
            TaskID id4 = getVideoFiles();//####[180]####
            TaskIDGroup gg = new TaskIDGroup(1);//####[181]####
            gg.add(id4);//####[182]####
            gg.waitTillFinished();//####[183]####
            System.out.println("** Finished saving files ...");//####[184]####
        } catch (Exception ee) {//####[186]####
        }//####[187]####
    }//####[188]####
//####[188]####
}//####[188]####
