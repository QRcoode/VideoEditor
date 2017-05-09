package Processing;

import java.io.File;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.*;

public class VideoProcessor {
	
	private FFmpegFrameFilter filter;
	private FFmpegFrameRecorder videoRecorder;
	private FFmpegFrameGrabber videoGrab;
	private File Directory;
    
	public VideoProcessor(String filename) {
		File file = new File(filename);
		videoGrab = new FFmpegFrameGrabber(file);
		getDirectory();
	}
	
	private void getDirectory() {
		Directory = new File(System.getProperty("user.dir") + "/Edited Video/");
        if (!Directory.exists()) {
            Directory.mkdirs();
        }
	}
	
	public void initializeFilter(String Filter) {
		// Set the FFmpeg effect/filter that will be applied
		filter = new FFmpegFrameFilter(Filter, videoGrab.getImageWidth(), videoGrab.getImageHeight());
	}
	
	private void initVideoRecorder(String path) {
		try {  
            videoRecorder = FFmpegFrameRecorder.createDefault(path, videoGrab.getImageWidth(), videoGrab.getImageHeight());
            videoRecorder.setFrameRate(videoGrab.getFrameRate());
            videoRecorder.setSampleFormat(videoGrab.getSampleFormat());
            videoRecorder.start();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }
	}
	
	public void start() {
		Frame frame;
		try {
        	
            String path = Directory + "/video" + System.currentTimeMillis() + ".mov";
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
}