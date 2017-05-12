package Editor;

import javax.swing.JFrame;

import GUI.UI;
import Processing.VideoProcessor;

public class VideoEditor {
	
	public static void main(String[] args) { 
		
		UI gui = new UI(); 
		
		VideoProcessor processor = new VideoProcessor("IMG_0101.mov");
		// This contains a sample filter. Will add a list of filters with proper names
		processor.initializeFilter("colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131");
		//processor.start();
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    }
}
