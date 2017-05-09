package Editor;

import Processing.VideoProcessor;

public class VideoEditor {
	
	public static void main(String[] args) { 
		
		VideoProcessor processor = new VideoProcessor("");
		// This contains a sample filter. Will add a list of filters with proper names
		processor.initializeFilter("colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131");
		//processor.start();
		
    }
}
