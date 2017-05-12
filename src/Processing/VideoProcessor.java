package Processing;

import java.io.File;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameFilter.Exception;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

public class VideoProcessor {
	
	private FFmpegFrameFilter filter;
	private FFmpegFrameRecorder videoRecorder;
	private FFmpegFrameGrabber videoGrab;
	private File Directory;
	private File video;
	private String ext;
    
	public VideoProcessor(String filename) {
		video = new File(filename);
		videoGrab = new FFmpegFrameGrabber(video.getAbsolutePath());
		ext = getFileExtension(filename);
		try {
			videoGrab.start();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
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
		try {
			filter.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initVideoRecorder(String path) {
		try {  
            videoRecorder = new FFmpegFrameRecorder(path, videoGrab.getImageWidth(), videoGrab.getImageHeight(), videoGrab.getAudioChannels());
            videoRecorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            videoRecorder.setAudioCodec(videoGrab.getAudioCodec());
            videoRecorder.setAudioBitrate(videoGrab.getAudioBitrate());
            videoRecorder.setVideoBitrate(videoGrab.getVideoBitrate());
            videoRecorder.setFrameRate(videoGrab.getFrameRate());
            videoRecorder.setSampleFormat(videoGrab.getSampleFormat());
            videoRecorder.setSampleRate(videoGrab.getSampleRate());
            videoRecorder.start();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }
	}
	
	private String getFileExtension(String filename) {
		String extension = "";
		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    extension += filename.substring(i+1);
		}
		return extension;
	}
	
	public void start() {
		Frame frame;
		try {
			System.out.println("Starting to process video: " + video.getName() + ".....");
            String path = Directory + "/video" + System.currentTimeMillis() + "." + ext;
            initVideoRecorder(path);    
            
            Long startTime = System.currentTimeMillis();
            System.out.println("There is " + videoGrab.getAudioChannels() + " audio channel");
            
            while (videoGrab.grab() != null) {
                frame = videoGrab.grabFrame();
              
                if (frame != null) {
                    //filter.push(frame);
                    //Frame filterFrame;
                    //filterFrame = filter.pull();
                    videoRecorder.setTimestamp(videoGrab.getTimestamp());
                    videoRecorder.record(frame, videoGrab.getPixelFormat());
                }
            }
            filter.stop();
            videoRecorder.stop();
            videoRecorder.release();
            videoGrab.stop();
            videoGrab.release();
            System.out.println("Finished processing video: " + video.getName() + ".....");
            Long endTime = System.currentTimeMillis();
            System.out.println("Video filtering took " + ((endTime.doubleValue()-startTime.doubleValue())/1000) + " seconds.");
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (FrameFilter.Exception e) {
            e.printStackTrace();
        }
	}
}