package GUI;//####[1]####
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
//####[28]####
    private FilterProcessor processor;//####[28]####
//####[30]####
    private ArrayList<String> a = new ArrayList<String>();//####[30]####
//####[31]####
    private Iterator<String> subVideoNames;//####[31]####
//####[33]####
    public SimpleGUI() {//####[33]####
        super("Simple GUI example");//####[34]####
        setSize(400, 300);//####[35]####
        btnGo.addActionListener(this);//####[36]####
        btnCheckResponsiveness.addActionListener(this);//####[37]####
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//####[38]####
        add(btnGo, BorderLayout.SOUTH);//####[39]####
        add(btnCheckResponsiveness, BorderLayout.CENTER);//####[40]####
    }//####[43]####
//####[45]####
    public void done() {//####[45]####
        JOptionPane.showMessageDialog(SimpleGUI.this, "Finished!");//####[46]####
    }//####[47]####
//####[49]####
    public void addFilter(FilterProcessor processor, String filter) {//####[49]####
        processor.initializeFilter(filter);//####[51]####
        processor.start();//####[52]####
    }//####[54]####
//####[56]####
    public void actionPerformed(ActionEvent e) {//####[56]####
        if (e.getSource() == btnCheckResponsiveness) //####[59]####
        {//####[60]####
            btnCheckResponsiveness.setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));//####[61]####
        } else {//####[63]####
            try {//####[65]####
                splitVideo("mv.mp4").hasCompleted();//####[66]####
                getVideoFiles();//####[67]####
            } catch (Exception ee) {//####[72]####
            }//####[73]####
        }//####[74]####
    }//####[75]####
//####[77]####
    public static void main(String[] agrs) {//####[77]####
        SwingUtilities.invokeLater(new Runnable() {//####[77]####
//####[79]####
            public void run() {//####[79]####
                SimpleGUI frame = new SimpleGUI();//####[80]####
                frame.setVisible(true);//####[81]####
            }//####[82]####
        });//####[82]####
    }//####[84]####
//####[88]####
    private static volatile Method __pt__addFilterToSubVideos__method = null;//####[88]####
    private synchronized static void __pt__addFilterToSubVideos__ensureMethodVarSet() {//####[88]####
        if (__pt__addFilterToSubVideos__method == null) {//####[88]####
            try {//####[88]####
                __pt__addFilterToSubVideos__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__addFilterToSubVideos", new Class[] {//####[88]####
                    //####[88]####
                });//####[88]####
            } catch (Exception e) {//####[88]####
                e.printStackTrace();//####[88]####
            }//####[88]####
        }//####[88]####
    }//####[88]####
    public TaskIDGroup<Void> addFilterToSubVideos() {//####[89]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[89]####
        return addFilterToSubVideos(new TaskInfo());//####[89]####
    }//####[89]####
    public TaskIDGroup<Void> addFilterToSubVideos(TaskInfo taskinfo) {//####[89]####
        // ensure Method variable is set//####[89]####
        if (__pt__addFilterToSubVideos__method == null) {//####[89]####
            __pt__addFilterToSubVideos__ensureMethodVarSet();//####[89]####
        }//####[89]####
        taskinfo.setParameters();//####[89]####
        taskinfo.setMethod(__pt__addFilterToSubVideos__method);//####[89]####
        taskinfo.setInstance(this);//####[89]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[89]####
    }//####[89]####
    public void __pt__addFilterToSubVideos() {//####[89]####
        long startTime = System.currentTimeMillis();//####[90]####
        String filter = "colorchannelmixer=.3:.4:.3:0:.3:.4:.3:0:.3:.4:.3";//####[91]####
        while (subVideoNames.hasNext()) //####[92]####
        addFilter(new FilterProcessor("SubVideos/" + subVideoNames.next()), filter);//####[93]####
        long endTime = System.currentTimeMillis();//####[94]####
        long totalTime = endTime - startTime;//####[95]####
        System.out.println("Duration: " + totalTime + " ms");//####[96]####
    }//####[97]####
//####[97]####
//####[100]####
    private static volatile Method __pt__splitVideo_String_method = null;//####[100]####
    private synchronized static void __pt__splitVideo_String_ensureMethodVarSet() {//####[100]####
        if (__pt__splitVideo_String_method == null) {//####[100]####
            try {//####[100]####
                __pt__splitVideo_String_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__splitVideo", new Class[] {//####[100]####
                    String.class//####[100]####
                });//####[100]####
            } catch (Exception e) {//####[100]####
                e.printStackTrace();//####[100]####
            }//####[100]####
        }//####[100]####
    }//####[100]####
    public TaskIDGroup<Void> splitVideo(String fileName) throws IOException {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return splitVideo(fileName, new TaskInfo());//####[101]####
    }//####[101]####
    public TaskIDGroup<Void> splitVideo(String fileName, TaskInfo taskinfo) throws IOException {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__splitVideo_String_method == null) {//####[101]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setParameters(fileName);//####[101]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[101]####
    }//####[101]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName) throws IOException {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return splitVideo(fileName, new TaskInfo());//####[101]####
    }//####[101]####
    public TaskIDGroup<Void> splitVideo(TaskID<String> fileName, TaskInfo taskinfo) throws IOException {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__splitVideo_String_method == null) {//####[101]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setTaskIdArgIndexes(0);//####[101]####
        taskinfo.addDependsOn(fileName);//####[101]####
        taskinfo.setParameters(fileName);//####[101]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[101]####
    }//####[101]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName) throws IOException {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return splitVideo(fileName, new TaskInfo());//####[101]####
    }//####[101]####
    public TaskIDGroup<Void> splitVideo(BlockingQueue<String> fileName, TaskInfo taskinfo) throws IOException {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__splitVideo_String_method == null) {//####[101]####
            __pt__splitVideo_String_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(0);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setParameters(fileName);//####[101]####
        taskinfo.setMethod(__pt__splitVideo_String_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 4);//####[101]####
    }//####[101]####
    public void __pt__splitVideo(String fileName) throws IOException {//####[101]####
        VideoSpliter vs = new VideoSpliter(fileName);//####[102]####
        String duration = vs.getVideoDuration("mv.mp4");//####[105]####
        int durationInMs = vs.transferDuration(duration);//####[106]####
        int partitionedInMs = vs.partition(durationInMs, 4);//####[107]####
        String partitionedDur = vs.transferMsToDuration(partitionedInMs);//####[108]####
        Iterator<String> it = vs.generateCommandLines(4, partitionedInMs, partitionedDur).iterator();//####[110]####
        vs.doRealSplittingWork(it);//####[111]####
    }//####[113]####
//####[113]####
//####[115]####
    private static volatile Method __pt__getVideoFiles__method = null;//####[115]####
    private synchronized static void __pt__getVideoFiles__ensureMethodVarSet() {//####[115]####
        if (__pt__getVideoFiles__method == null) {//####[115]####
            try {//####[115]####
                __pt__getVideoFiles__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__getVideoFiles", new Class[] {//####[115]####
                    //####[115]####
                });//####[115]####
            } catch (Exception e) {//####[115]####
                e.printStackTrace();//####[115]####
            }//####[115]####
        }//####[115]####
    }//####[115]####
    public TaskID<Void> getVideoFiles() throws IOException {//####[116]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[116]####
        return getVideoFiles(new TaskInfo());//####[116]####
    }//####[116]####
    public TaskID<Void> getVideoFiles(TaskInfo taskinfo) throws IOException {//####[116]####
        // ensure Method variable is set//####[116]####
        if (__pt__getVideoFiles__method == null) {//####[116]####
            __pt__getVideoFiles__ensureMethodVarSet();//####[116]####
        }//####[116]####
        taskinfo.setParameters();//####[116]####
        taskinfo.setMethod(__pt__getVideoFiles__method);//####[116]####
        taskinfo.setInstance(this);//####[116]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[116]####
    }//####[116]####
    public void __pt__getVideoFiles() throws IOException {//####[116]####
        System.out.println("start");//####[118]####
        File[] listOfFiles = new File("SubVideos").listFiles();//####[132]####
        ArrayList<String> videoNames = new ArrayList<String>();//####[133]####
        for (File listOfFile : listOfFiles) //####[134]####
        {//####[134]####
            if (listOfFile.isFile()) //####[135]####
            {//####[136]####
                videoNames.add(listOfFile.getName());//####[137]####
                System.out.println(listOfFile.getName());//####[138]####
            }//####[139]####
        }//####[142]####
        subVideoNames = videoNames.iterator();//####[143]####
    }//####[146]####
//####[146]####
//####[149]####
    public void donee() {//####[149]####
        System.out.println("isEDT?");//####[151]####
    }//####[152]####
//####[154]####
    private static volatile Method __pt__doWork__method = null;//####[154]####
    private synchronized static void __pt__doWork__ensureMethodVarSet() {//####[154]####
        if (__pt__doWork__method == null) {//####[154]####
            try {//####[154]####
                __pt__doWork__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__doWork", new Class[] {//####[154]####
                    //####[154]####
                });//####[154]####
            } catch (Exception e) {//####[154]####
                e.printStackTrace();//####[154]####
            }//####[154]####
        }//####[154]####
    }//####[154]####
    public TaskID<Void> doWork() {//####[154]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[154]####
        return doWork(new TaskInfo());//####[154]####
    }//####[154]####
    public TaskID<Void> doWork(TaskInfo taskinfo) {//####[154]####
        // ensure Method variable is set//####[154]####
        if (__pt__doWork__method == null) {//####[154]####
            __pt__doWork__ensureMethodVarSet();//####[154]####
        }//####[154]####
        taskinfo.setParameters();//####[154]####
        taskinfo.setMethod(__pt__doWork__method);//####[154]####
        taskinfo.setInstance(this);//####[154]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[154]####
    }//####[154]####
    public void __pt__doWork() {//####[154]####
        System.out.println("asfdfasdfasdf");//####[155]####
    }//####[156]####
//####[156]####
}//####[156]####
