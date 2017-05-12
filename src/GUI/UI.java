package GUI; 

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import javax.media.*;
import javax.imageio.ImageIO;
import javafx.*;


public class UI extends JFrame implements Runnable {
     
    // GUI 
    private JPanel panelButton, filterOptions, panelCenter, panelLabels; 
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
    
    public UI() { 
         
        loadGUI(); 
         
        thread = new Thread(this);
        thread.start();
        playing = false;
        
        setLayout(new BorderLayout());
        
        // COULD TRY JAVA FX INSTEAD OF SWING
    	
    	/*Media media = new Media(new File("AHS.mp4").toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mediaView);
        borderPane.setBottom(addToolBar());

        borderPane.setStyle("-fx-background-color: Black");

        Scene scene = new Scene(borderPane, 600, 600);
        scene.setFill(Color.BLACK);*/

        //primaryStage.setTitle("Media Player!");
        //primaryStage.setScene(scene);
       // primaryStage.show();

      
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
         
        panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelButton, BorderLayout.SOUTH);
        panelCenter.add(panelLabels, BorderLayout.CENTER);
        
        container = getContentPane(); 
        container.setLayout(new BorderLayout()); 
        container.add(panelCenter, BorderLayout.CENTER);
        container.add(filterOptions, BorderLayout.WEST);
        
        vidHeight = 480;
        vidWidth = 480;
        
        setSize(vidWidth+125,vidHeight+100); 
        setResizable(false); 
        setVisible(true); 
    }
  
    private void openFile() {      
		JFileChooser fileChooser = new JFileChooser();
		  
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog( this );
		  
		if ( result == JFileChooser.CANCEL_OPTION ) {
		// If the user clicks cancel
		file = null;
		} else {
			// If the user chooses a file
			file = fileChooser.getSelectedFile();
			try {
				pic = ImageIO.read(file);
				if(pic != null) {
					panelCenter = new JPanel(new BorderLayout());
					image = new ImageIcon(pic);
					JLabel picLabel = new JLabel(image);
					panelCenter.add(picLabel, BorderLayout.NORTH);
					panelCenter.add(panelButton, BorderLayout.SOUTH);
					panelCenter.add(panelLabels, BorderLayout.CENTER);
					 
					container.removeAll();;  
					container.setLayout(new BorderLayout()); 
					container.add(panelCenter, BorderLayout.CENTER);
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
                } 
        	} else if(a_event.getSource() == buttonPlayStop) {
                if(playing){ 
                    playing = false;                      
                } 
            } else if (a_event.getSource() == buttonOpenFile) {
            	openFile();
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
