import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;



public class VideoCap{
	

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();
    Mat2Image freshImg = new Mat2Image();//fresh Mat that has no contours.
    VideoCap(){
        cap = new VideoCapture();
        //Change the number if from 0-4 if you have different cameras on your machine
        //0 is default webcam on your machine
        cap.open(0);
    } 
    
    
    boolean captured;
	AnalyzeFrame takeFrame = new AnalyzeFrame();

    BufferedImage getOneFrame() {

    	//reads the current mat
    	cap.read(mat2Img.mat);
    	
    	//returns images with contours drawn on them but captured isn't true
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat, false);
		
    	if(captured == true){
    		//read captured image
    		cap.read(freshImg.mat);
    		freshImg.getImage(freshImg.mat);
    		
    		//take current frame save it
    		freshImg.mat = takeFrame.captureFrame(freshImg.mat, true);
    		
    		if(takeFrame.completed == true){
    			takeFrame = null;
    			takeFrame = new AnalyzeFrame();
    		}
    		captured = false;
    	}
		
		return mat2Img.getImage(mat2Img.mat);
    }  
}