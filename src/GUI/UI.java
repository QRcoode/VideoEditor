package GUI; 

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container; 
import java.awt.FlowLayout; 
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 

public class UI extends JFrame implements Runnable {
     
    // GUI 
    private JPanel panelButton, filterOptions, panelCenter, panelLabels; 
    private JButton buttonPlayStop, buttonPlay, buttonNormal, buttonPluginGray, buttonPluginSepia, buttonPluginInvert, 
                    buttonPluginPixelize, buttonThresholding, buttonPluginHalftone, buttonPluginMinimum, 
                    buttonPluginMaximum, buttonPluginFlip, buttonPluginTelevision, buttonPluginEdgeDetector,
                    buttonPluginDifference;
    private JLabel labelCurrentFilter;
    private Thread  thread; 
    private int vidWidth, vidHeight;
    private boolean playing;
    
    public UI(){ 
         
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
     
    private void loadGUI(){
        setTitle("Video Editor"); 
        
        // Labels
        labelCurrentFilter = new JLabel("Current filter: None");
         
        // Buttons 
        ButtonHandler l_handler = new ButtonHandler();
        buttonPlay = new JButton("Play");
        buttonPlayStop = new JButton("Stop"); 
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
        
        // Adding a picture for now instead of an video
        BufferedImage pic = null;
		try {
			pic = ImageIO.read(new File("blank.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        JLabel picLabel = new JLabel(new ImageIcon(pic));
        
        
        panelLabels = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLabels.add(labelCurrentFilter); 
         
        panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(picLabel, BorderLayout.NORTH);
        panelCenter.add(panelButton, BorderLayout.SOUTH);
        panelCenter.add(panelLabels, BorderLayout.CENTER);
        
        
        Container container = getContentPane(); 
        container.setLayout(new BorderLayout()); 
        container.add(panelCenter, BorderLayout.CENTER);
        container.add(filterOptions, BorderLayout.WEST);
        
        vidHeight = pic.getHeight();
        vidWidth = pic.getWidth();
        
        setSize(vidWidth+125,vidHeight+100); 
        setResizable(true); 
        setVisible(true); 
    } 
     
    public void run(){ 
        while(true){             
                if(playing) 
                {
                    /*imageIn = videoInterface.getFrame(); 
                    MarvinImage.copyColorArray(imageIn, imageOut); 
                     
                    if(pluginImage == null || rect){ 
                        MarvinImage.copyColorArray(imageIn, imageOut); 
                    } 
                 
                    if(pluginImage != null){                     
                        pluginImage.process(imageIn, imageOut, null, imageMask, false);
                    } 
                    imageOut.update(); 
                    videoPanel.setImage(imageOut); */
                } 
            }  
    } 
     
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent a_event){ 
        	if (a_event.getSource() == buttonPlay){
        		if(!playing){ 
                    playing = true;                      
                } 
        	} else if(a_event.getSource() == buttonPlayStop){ 
                if(playing){ 
                    playing = false;                      
                } 
            } 
            else if(a_event.getSource() == buttonNormal){ 
                //pluginImage = null; 
                labelCurrentFilter.setText("Current filter: None"); 
            } 
            else if(a_event.getSource() == buttonPluginGray){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar"); 
                labelCurrentFilter.setText("Current filter: Gray Scale"); 
            } 
            else if(a_event.getSource() == buttonPluginSepia){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.sepia.jar"); 
                labelCurrentFilter.setText("Current filter: Sepia"); 
            } 
            else if(a_event.getSource() == buttonPluginInvert){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar"); 
                labelCurrentFilter.setText("Current filter: Negative"); 
            } 
            else if(a_event.getSource() == buttonPluginPixelize){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.blur.pixelize.jar"); 
                labelCurrentFilter.setText("Current filter: Pixelize"); 
            } 
            else if(a_event.getSource() == buttonThresholding){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.thresholding.jar"); 
                labelCurrentFilter.setText("Current filter: Thresholding"); 
            } 
            else if(a_event.getSource() == buttonPluginHalftone){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.halftone.dithering.jar"); 
                labelCurrentFilter.setText("Current filter: Halftone"); 
            } 
            else if(a_event.getSource() == buttonPluginMinimum){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.statistical.minimum.jar"); 
                //pluginImage.setAttribute("size", 2); 
                labelCurrentFilter.setText("Current filter: Minimum"); 
            } 
            else if(a_event.getSource() == buttonPluginMaximum){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.statistical.maximum.jar"); 
                //pluginImage.setAttribute("size", 2); 
                labelCurrentFilter.setText("Current filter: Maximum"); 
            } 
            else if(a_event.getSource() == buttonPluginFlip){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.flip.jar"); 
                labelCurrentFilter.setText("Current filter: Flip"); 
            } 
            else if(a_event.getSource() == buttonPluginTelevision){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.artistic.television.jar"); 
                labelCurrentFilter.setText("Current filter: Television"); 
            } 
            else if(a_event.getSource() == buttonPluginEdgeDetector){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");                 
                labelCurrentFilter.setText("Current filter: Edge Detector"); 
            }     
            else if(a_event.getSource() == buttonPluginDifference){ 
                //pluginImage = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.difference.differenceColor.jar"); 
                //pluginImage.setAttribute("comparisonImage", imageLastFrame); 
                labelCurrentFilter.setText("Current filter: Difference"); 
            } 
        } 
    } 
}
