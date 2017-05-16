package GUI; 

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;

import Processing.VideoProcessor;

import javax.media.*;
import javax.imageio.ImageIO;
import javafx.*;


public class UI extends JFrame implements Runnable {
     
    // GUI 
    private JPanel panelButton, filterOptions, panelCenter, panelLabels; 
    private JButton buttonPlayStop, buttonPlay, buttonNormal, buttonPluginGray, buttonPluginSepia, buttonPluginInvert, 
                    buttonPluginPixelize, buttonThresholding, buttonPluginHalftone, buttonPluginMinimum, 
                    buttonPluginMaximum, buttonPluginFlip, buttonPluginTelevision, buttonPluginEdgeDetector,
                    buttonPluginDifference, buttonOpenFile, buttonSave;
    private JLabel labelCurrentFilter, labelProcessing;
    private Thread  thread; 
    private int vidWidth, vidHeight;
    private boolean playing;
    private File file;
    private BufferedImage pic;
    private Container container;
    private ImageIcon image;
    private VideoCapture vid;
    private VideoProcessor processor;
    private String filter;
    private UI ui;
    
    public UI() { 
        ui = this;
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
        labelProcessing = new JLabel("");
         
        // Buttons 
        ButtonHandler l_handler = new ButtonHandler();
        buttonPlay = new JButton("Play");
        buttonPlayStop = new JButton("Stop");
        buttonOpenFile = new JButton("Open file");
        buttonSave = new JButton("Save");
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
        buttonSave.addActionListener(l_handler);
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
        panelButton.add(buttonSave);
         
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
        panelLabels.add(labelProcessing);
         
        panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelButton, BorderLayout.SOUTH);
        panelCenter.add(panelLabels, BorderLayout.CENTER);
        
        container = getContentPane(); 
        container.setLayout(new BorderLayout()); 
        container.add(panelCenter, BorderLayout.CENTER);
        container.add(filterOptions, BorderLayout.WEST);
        
        vidHeight = 640;
        vidWidth = 640;
        
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
		} else {
			// If the user chooses a file
			file = fileChooser.getSelectedFile();
			processor = new VideoProcessor(file.getName(), ui);
			pic = processor.getFirstFrame(file);
			if(pic != null) {
				BufferedImage resized = scale(pic,640,640);
				image = new ImageIcon(resized);
				JLabel picLabel = new JLabel(image);
				panelCenter.add(picLabel, BorderLayout.NORTH);
				panelCenter.add(panelButton, BorderLayout.SOUTH);
				panelCenter.add(panelLabels, BorderLayout.CENTER);
				 
				container.removeAll();;  
				container.setLayout(new BorderLayout()); 
				container.add(panelCenter, BorderLayout.CENTER);
				container.add(filterOptions, BorderLayout.WEST);
				vidHeight = resized.getHeight();
				vidWidth = resized.getWidth();
				setSize(vidWidth+125,vidHeight+100);
			} else {
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
    
    public void updateLabel(String text) {
    	labelProcessing.setText(text);
		panelLabels.validate();
        panelLabels.repaint();
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
            } else if (a_event.getSource() == buttonSave) {
            	if (file == null) {
            		JOptionPane.showMessageDialog(ui, "You have not selected a file yet.");
            	} else {
            		if (filter == null) {
            			JOptionPane.showMessageDialog(ui, "You have not selected a filter.");
            		} else {
            			updateLabel("Saving Video......");
            			processor = new VideoProcessor(file.getName(), ui);
            			// Need to add shared filter that changes when you push the filter buttons
            			processor.initializeFilter(filter);
            			processor.execute();
            		}
            	}
            }
            else if(a_event.getSource() == buttonNormal){ 
                labelCurrentFilter.setText("Current filter: None");
                filter = null;
            } 
            else if(a_event.getSource() == buttonPluginGray){  
                labelCurrentFilter.setText("Current filter: Gray Scale");
                filter = "colorchannelmixer=.3:.4:.3:0:.3:.4:.3:0:.3:.4:.3";
            } 
            else if(a_event.getSource() == buttonPluginSepia){ 
                labelCurrentFilter.setText("Current filter: Sepia");
                filter = "colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131";
            } 
            else if(a_event.getSource() == buttonPluginInvert){ 
                labelCurrentFilter.setText("Current filter: Negative");
                filter = "lutrgb='r=negval:g=negval:b=negval'lutyuv='y=negval:u=negval:v=negval'";
            } 
            else if(a_event.getSource() == buttonPluginPixelize){  
                labelCurrentFilter.setText("Current filter: Pixelize");
                filter = "boxblur=5:1";
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
    public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;

        int w = img.getWidth();
        int h = img.getHeight();

        int prevW = w;
        int prevH = h;

        do {
            if (w > targetWidth) {
                w /= 2;
                w = (w < targetWidth) ? targetWidth : w;
            }

            if (h > targetHeight) {
                h /= 2;
                h = (h < targetHeight) ? targetHeight : h;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }

        return ret;

    }
}
