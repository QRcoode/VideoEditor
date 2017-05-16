package Editor;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.UI;

public class VideoEditor {
	
	public static void main(String[] args) { 
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UI gui = new UI();
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});	
    }
}
