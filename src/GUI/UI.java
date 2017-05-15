package GUI; 

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import javax.imageio.ImageIO;
import javafx.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class UI extends JFrame implements Runnable {
     
    // GUI 
    private JPanel panelButton, filterOptions, panelVideoButtons, panelLabels; 
    private JButton buttonPlayStop, buttonPlay, buttonNormal, buttonPluginGray, buttonPluginSepia, buttonPluginInvert, 
                    buttonPluginPixelize, buttonThresholding, buttonPluginHalftone, buttonPluginMinimum, 
                    buttonPluginMaximum, buttonPluginFlip, buttonPluginTelevision, buttonPluginEdgeDetector,
                    buttonPluginDifference, buttonOpenFile;
    private JLabel labelCurrentFilter;
    private Thread  thread; 
    private int vidWidth, vidHeight;
    private boolean playing;
    private File file;
    private BufferedImage pic;
    private Container container;
    private ImageIcon image;
    private VideoCapture vid;
    
    //---@Rain---
    private Media media;
    private MediaPlayer player;
    private MediaView view;
    //---@Rain---
    
    
    
    public UI() {      
        loadGUI();      
        thread = new Thread(this);
        thread.start();
        playing = false; 
        setLayout(new BorderLayout());
 
    }
     
    private void loadGUI() {
        setTitle("Video Editor"); 
        
        // Labels
        labelCurrentFilter = new JLabel("Current filter: None");
         
        // Buttons 
        ButtonHandler l_handler = new ButtonHandler();
        buttonPlay = new JButton("Play");
        buttonPlayStop = new JButton("Stop");
        buttonOpenFile = new JButton("Open file");
        buttonNormal = new JButton("Normal"); 
        buttonPluginGray = new JButton("Gray Scale"); 
        buttonPluginSepia = new JButton("Sepia"); 
        buttonPluginInvert = new JButton("Invert Colors");     
        buttonPluginPixelize = new JButton("Pixelize"); 
        buttonThresholding = new JButton("Thresholding"); 
        buttonPluginHalftone = new JButton("Halftone"); 
        buttonPluginMinimum = new JButton("Minimum"); 
        buttonPluginMaximum = new JButton("Maximum");         
        buttonPluginFlip = new JButton("Flip"); 
        buttonPluginTelevision = new JButton("Television"); 
        buttonPluginEdgeDetector = new JButton("Edge Detector"); 
        buttonPluginDifference = new JButton("Difference"); 
        
        buttonPlay.addActionListener(l_handler); 
        buttonPlayStop.addActionListener(l_handler);
        buttonOpenFile.addActionListener(l_handler);
        buttonPluginGray.addActionListener(l_handler); 
        buttonNormal.addActionListener(l_handler); 
        buttonPluginSepia.addActionListener(l_handler); 
        buttonPluginInvert.addActionListener(l_handler); 
        buttonPluginPixelize.addActionListener(l_handler); 
        buttonThresholding.addActionListener(l_handler); 
        buttonPluginHalftone.addActionListener(l_handler);         
        buttonPluginMinimum.addActionListener(l_handler); 
        buttonPluginMaximum.addActionListener(l_handler);         
        buttonPluginFlip.addActionListener(l_handler); 
        buttonPluginTelevision.addActionListener(l_handler); 
        buttonPluginEdgeDetector.addActionListener(l_handler); 
        buttonPluginDifference.addActionListener(l_handler); 
        
        

        // Panels 
        panelButton = new JPanel(); 
        panelButton.add(buttonPlay);
        panelButton.add(buttonPlayStop);
        panelButton.add(buttonOpenFile);
         
        filterOptions = new JPanel(); 
        filterOptions.setLayout(new GridLayout(15,1)); 
        filterOptions.add(buttonNormal); 
        filterOptions.add(buttonPluginGray); 
        filterOptions.add(buttonPluginSepia); 
        filterOptions.add(buttonPluginInvert);         
        filterOptions.add(buttonPluginPixelize); 
        filterOptions.add(buttonThresholding); 
        filterOptions.add(buttonPluginHalftone);         
        filterOptions.add(buttonPluginMinimum); 
        filterOptions.add(buttonPluginMaximum);         
        filterOptions.add(buttonPluginFlip); 
        filterOptions.add(buttonPluginTelevision); 
        filterOptions.add(buttonPluginEdgeDetector); 
        filterOptions.add(buttonPluginDifference);
        
        pic = null;
        
        panelLabels = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLabels.add(labelCurrentFilter); 
         
        

        panelVideoButtons = new JPanel(new BorderLayout());
        panelVideoButtons.add(panelButton, BorderLayout.SOUTH);
        panelVideoButtons.add(panelLabels, BorderLayout.CENTER);
        panelVideoButtons.setSize(200, 200);
        
        container = getContentPane(); 
        container.setLayout(new BorderLayout()); 
        
        container.add(filterOptions, BorderLayout.WEST);
        container.add(panelVideoButtons, BorderLayout.SOUTH);
        

        vidHeight = 400;
        vidWidth = 720;
        
        setSize(vidWidth+125,vidHeight+100); 
        setResizable(false); 
        setVisible(true); 
        
        
    }
  
    private void openFile(JFXPanel panelPlayer) {      
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog( this );
		  
		if ( result == JFileChooser.CANCEL_OPTION ) {
		// If the user clicks cancel
			file = null;
		} else {
			// If the user chooses a file
			file = fileChooser.getSelectedFile();
			media = new Media(file.toURI().toString());
	        player = new MediaPlayer(media);
	        view = new MediaView(player);
	        BorderPane root = new BorderPane();
	        root.getChildren().add(view);
	        Scene scene = new Scene(root, 500, 500, true);
	        Platform.runLater(new Runnable() {
	            @Override 
	            public void run() {
	            	panelPlayer.setSize(1000,1000);
	            	panelPlayer.setScene(scene);
	                    
	            }
	          });
	       
	        
	        
	        container.add(panelPlayer, BorderLayout.CENTER);

	        
	        
			
			/*
			try {
				
				
				pic = ImageIO.read(file);
				if(pic != null) {
					panelVideoButtons = new JPanel(new BorderLayout());

					image = new ImageIcon(pic);
					JLabel picLabel = new JLabel(image);
					panelVideoButtons.add(picLabel, BorderLayout.NORTH);
					panelVideoButtons.add(panelButton, BorderLayout.SOUTH);
					panelVideoButtons.add(panelLabels, BorderLayout.CENTER);
					container.removeAll();;  
					container.setLayout(new BorderLayout()); 
					container.add(panelVideoButtons, BorderLayout.CENTER);
					container.add(filterOptions, BorderLayout.WEST);
					vidHeight = pic.getHeight();
					vidWidth = pic.getWidth();
					setSize(vidWidth+125,vidHeight+100);
				} else {
					System.out.println("You did not select an image file");
				}
			} catch (IOException e) {
				System.out.println("You did not select an image file");
			}
			*/
			
			
			
			
		}  
    }
     
    public void run() { 
        while(true){             
            if(playing) 
            {
                
            } 
        }  
    }
    
    public void ShowVidFrame() {
    	Mat mat = new Mat();
    	vid = new VideoCapture(file.getAbsolutePath());
        vid.open(file.getAbsolutePath());
    	vid.grab();
    	vid.retrieve(mat);

      //  Highgui.imwrite(System.getProperty("user.dir")+ "temp.jpg", mat);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir")+ "temp.jpg");
        JLabel label = new JLabel("", image, JLabel.CENTER);
    }
     
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent a_event) { 
        	if (a_event.getSource() == buttonPlay) {
        		if(!playing){ 
                    playing = true;  
                    player.play();
                } 
        	} else if(a_event.getSource() == buttonPlayStop) {
                if(playing){ 
                    playing = false;       
                    player.stop();
                } 
            } else if (a_event.getSource() == buttonOpenFile) {
            	System.out.println("opening file");
            	final JFXPanel panelPlayer = new JFXPanel(); 
            	openFile(panelPlayer);
            }
            else if(a_event.getSource() == buttonNormal){ 
                labelCurrentFilter.setText("Current filter: None"); 
            } 
            else if(a_event.getSource() == buttonPluginGray){  
                labelCurrentFilter.setText("Current filter: Gray Scale"); 
            } 
            else if(a_event.getSource() == buttonPluginSepia){ 
                labelCurrentFilter.setText("Current filter: Sepia"); 
            } 
            else if(a_event.getSource() == buttonPluginInvert){ 
                labelCurrentFilter.setText("Current filter: Negative"); 
            } 
            else if(a_event.getSource() == buttonPluginPixelize){  
                labelCurrentFilter.setText("Current filter: Pixelize"); 
            } 
            else if(a_event.getSource() == buttonThresholding){  
                labelCurrentFilter.setText("Current filter: Thresholding"); 
            } 
            else if(a_event.getSource() == buttonPluginHalftone){ 
                labelCurrentFilter.setText("Current filter: Halftone"); 
            } 
            else if(a_event.getSource() == buttonPluginMinimum){ 
                labelCurrentFilter.setText("Current filter: Minimum"); 
            } 
            else if(a_event.getSource() == buttonPluginMaximum){ 
                labelCurrentFilter.setText("Current filter: Maximum"); 
            } 
            else if(a_event.getSource() == buttonPluginFlip){ 
                labelCurrentFilter.setText("Current filter: Flip"); 
            } 
            else if(a_event.getSource() == buttonPluginTelevision){  
                labelCurrentFilter.setText("Current filter: Television"); 
            } 
            else if(a_event.getSource() == buttonPluginEdgeDetector){                  
                labelCurrentFilter.setText("Current filter: Edge Detector"); 
            }     
            else if(a_event.getSource() == buttonPluginDifference){  
                labelCurrentFilter.setText("Current filter: Difference"); 
            } 
        } 
    } 
}
