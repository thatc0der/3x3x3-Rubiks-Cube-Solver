import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;



public class VideoCap {
	
	GetContours takeFrame = new GetContours();
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();
    Mat copy = new Mat();
    VideoCap(){
        cap = new VideoCapture();
        cap.open(0);
    } 
 
    int counter = 0;
    BufferedImage getOneFrame() {
    	
    	counter++;
    	
    	//Imgproc.cvtColor(mat2Img.mat, mat2Img.mat, Imgproc.COLOR_RGB2BGR);
    	//mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);
    		
    		
    	cap.read(mat2Img.mat);
    	
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);

    	/*Imshow me = new Imshow("debug");
		me.show(mat2Img.mat);
    */
    	
		return mat2Img.getImage(mat2Img.mat);
    }   
}