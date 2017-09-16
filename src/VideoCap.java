import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;



public class VideoCap{
	
	AnalyzeFrame takeFrame = new AnalyzeFrame();
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();
    Mat2Image freshImg = new Mat2Image();//copy that has no weird drawings.
    VideoCap(){
        cap = new VideoCapture();
        //Change the number if from 0-4 if you have different cameras on your machine
        //0 is default webcam on your machine
        cap.open(0);
    } 
 
    
    boolean captured;
    int successfulCaptures = 0;
    BufferedImage getOneFrame() {
    	
    	
    	//reads the current mat
    	cap.read(mat2Img.mat);
    	
    	//returns images with contours drawn on them but captured isn't true
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat , false);
		
    	if(captured == true){
    		//read captured image
    		cap.read(freshImg.mat);
    		freshImg.getImage(freshImg.mat);
    		
    		//take current frame save it
    		freshImg.mat = takeFrame.captureFrame(freshImg.mat, true);
    		
    		while(takeFrame.capturesCompleted == true){
    	        cap.read(mat2Img.mat);
    			return mat2Img.getImage(mat2Img.mat);
    		}
    		//Imgcodecs.imwrite("greenSample.png", freshImg.mat);
    		captured = false;
    	}
		
		return mat2Img.getImage(mat2Img.mat);
    }  
}