package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import constants.ConfigConstants;

public class VideoCombiner {
	
	private String combinedVideoNames = "concat:";

	
	public void combine()
	{
		try {
			convertVideoToTsFormat();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	
	public void convertVideoToTsFormat() throws IOException
	{
		File[] filteredVideos = new File("EditedVideo").listFiles();		
		for(File filteredVideo: filteredVideos) 
		{	
			try {
				String command = "ffmpeg -i " + "EditedVideo\\" + filteredVideo.getName()
				+ " -c copy -bsf:v h264_mp4toannexb -f mpegts "+ "EditedTsVideo\\" + filteredVideo.getName() + ".ts";
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
			combinedVideoNames += ConfigConstants.EDITED_TS_VIDEOS_PATH + "\\" +filteredVideo.getName() + ".ts|";	
		}
		System.out.println("combinedVideoNames are: " + combinedVideoNames);
		String names = "\""+combinedVideoNames+"\"";
		String command = "ffmpeg.exe" + " -i " + names + " -c" + " copy" + " -bsf:a" +
				" aac_adtstoasc " + ConfigConstants.COMBINED_VIDEO_PATH + "\\filteredVideo.mp4";
		
		
		
	
		ProcessBuilder pb = new ProcessBuilder("ffmpeg.exe", "-i", names, "-c" , "copy", "-bsf:a",
				"aac_adtstoasc", ConfigConstants.COMBINED_VIDEO_PATH + "\\filteredVideo.mp4");
		try {
			
			pb.redirectErrorStream(true);
			pb.start();
			
			System.out.println(names);
			System.out.println(command);
			
			Runtime.getRuntime().exec(command);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	
	public void runCombineProcessBuilder()
	{
		
		String command = "ffmpeg.exe" + "-i" + combinedVideoNames + "-c" + "copy" + "-bsf:a" +
				"aac_adtstoasc" + ConfigConstants.COMBINED_VIDEO_PATH + "\\filteredVideo.mp4";
	
		String combinedVideoNames = "concat:EditedTsVideo\\video1496737311548.mp4.ts|EditedTsVideo\\video1496737311549.mp4.ts|EditedTsVideo\\video1496737311550.mp4.ts|EditedTsVideo\\video1496737311551.mp4.ts|";
		
		ProcessBuilder pb = new ProcessBuilder("ffmpeg.exe", "-i", combinedVideoNames, "-c" , "copy", "-bsf:a",
				"aac_adtstoasc", ConfigConstants.COMBINED_VIDEO_PATH + "\\filteredVideo.mp4");
		try {
			System.out.println(command);
			pb.redirectErrorStream(true);
			pb.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

	
}
