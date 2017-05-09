import java.io.File;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;

public class VideoProcessing {
	static FFmpegFrameFilter filter;
	static FFmpegFrameRecorder videoRecorder;
	static FFmpegFrameGrabber videoGrab;
    
	public static void main(String[] args) { 
	    
		File file = new File("IMG_0101.mov");
		videoGrab = new FFmpegFrameGrabber(file);
        File myDirectory = new File(System.getProperty("user.dir") + "/Edited Video/");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        
        Frame frame;
        try {
        	videoGrab.start();
            String path = myDirectory + "/video" + System.currentTimeMillis() + ".mov";
            initVideoRecorder(path);
            filter.start();
            
            System.out.println("There is " +videoGrab.getAudioChannels() + " audio channel");
            
            while (videoGrab.grab() != null) {
                frame = videoGrab.grabImage();
                if (frame != null) {
                    filter.push(frame);
                    frame = filter.pull();
                    videoRecorder.setTimestamp(videoGrab.getTimestamp());
                    videoRecorder.record(frame);
                }
            }
            filter.stop();
            videoRecorder.stop();
            videoRecorder.release();
            videoGrab.stop();
            videoGrab.release();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (FrameFilter.Exception e) {
            e.printStackTrace();
        }
    	
    }

	private static void initVideoRecorder(String path) {
		try {
            //FFmpeg effect/filter that will be applied
            filter = new FFmpegFrameFilter("colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131", videoGrab.getImageWidth(), videoGrab.getImageHeight());
            videoRecorder = FFmpegFrameRecorder.createDefault(path, videoGrab.getImageWidth(), videoGrab.getImageHeight());
            videoRecorder.setFrameRate(videoGrab.getFrameRate());
            videoRecorder.setSampleFormat(videoGrab.getSampleFormat());
            videoRecorder.start();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }
	}
}