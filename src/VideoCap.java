import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;



public class VideoCap {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();

    VideoCap(){
        cap = new VideoCapture();
        cap.open(0);
    } 
 
    int counter = 0;
    BufferedImage getOneFrame() {
    	
    	counter++;
    	
    	
    	if(counter == 60){
    		getThisStuff(mat2Img.mat);
    	}
    	cap.read(mat2Img.mat);
    	
    	
    
    	return mat2Img.getImage(mat2Img.mat);
    }
    
    
    private void getThisStuff(Mat capturedFrame){
    	
    	Imgproc.cvtColor(capturedFrame, capturedFrame, Imgproc.COLOR_BGR2RGB);
    	Imgcodecs.imwrite("first.png", capturedFrame);
    	//Gray
    	Mat gray = new Mat();
    	Imgproc.cvtColor(capturedFrame, gray, Imgproc.COLOR_BGR2GRAY);

    	//Blur
    	Mat blur = new Mat();
    	Imgproc.blur(gray, blur, new Size(3,3));
    	//Canny image
    	Mat canny = new Mat();
    	Imgproc.Canny(blur, canny, 20, 40, 3, true);
    	
    	
    	Imgcodecs.imwrite("test.png", canny);
    	
    	//System.exit(0);
        Mat kernel = Imgproc.getStructuringElement(1, new Size(3,3));
        Mat dilated = new Mat();
        
        Imgproc.dilate(canny,dilated, kernel);
        
        //Imgproc.cvtColor(captImg, dst, Imgproc.COLOR_GRAY2BGR);
    	
        /*Imgcodecs.imwrite("after.png", dilated);
       System.exit(0);*/
        
    	
    	List<MatOfPoint> contours = new ArrayList<>();
    	//find contours
    	Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
    	//draw contours

    	Imgproc.cvtColor(capturedFrame, capturedFrame, Imgproc.COLOR_BGR2RGB);
    	
    	for(int i = 0; i < contours.size(); i++){
    		Imgproc.drawContours(capturedFrame, contours, i, new Scalar(0, 0, 255), -1);
    	}
    	
    	
    	
    	
    	
    	Imgcodecs.imwrite("after.png", capturedFrame);
    	
    	System.exit(0);
    	}
    
    
    private void isItASquare(MatOfPoint contours){
    	
    	
    	
    }
    }