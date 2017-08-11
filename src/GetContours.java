import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GetContours {


static {
	 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
}
	
	
	public Mat captureFrame(Mat capturedFrame){
		
        Mat newFrame = new Mat();
        capturedFrame.copyTo(newFrame); 
        
        //Gray
        Mat gray = new Mat();
        Imgproc.cvtColor(capturedFrame, gray, Imgproc.COLOR_RGB2GRAY);

        //Blur
        Mat blur = new Mat();
        Imgproc.blur(gray, blur, new Size(3,3));
        //Canny image
        Mat canny = new Mat();
        Imgproc.Canny(blur, canny, 20, 40, 3, true);

        //Dilate image to increase size of lines
        Mat kernel = Imgproc.getStructuringElement(1, new Size(3,3));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);


        List<MatOfPoint> contours = new ArrayList<>();
        List<MatOfPoint> squareContours = new ArrayList<>();

        //find contours
        Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);


        //Remove contours that aren't close to a square shape.
        for(int i = 0; i < contours.size(); i++){

            double area = Imgproc.contourArea( contours.get(i)); 
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
            double perimeter = Imgproc.arcLength(contour2f, true);

            //Found squareness equation on wiki... 
            //https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
            double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

            //add contour to new List if it has a square shape.
            if(squareness >= 0.7 && squareness <= 0.9 && area >= 4000){
               squareContours.add(contours.get(i));
            }
        }
        
        //Mat for getting RGB of contours
        List<Mat> recieveRGBs = new ArrayList<>();
        
        //Draw square contours on saved frame in beginning of method
        System.out.println("square contour size: " + squareContours.size());
        for(int i = 0; i < squareContours.size(); i++){
        	
        //	squareContours.get(i).copyTo(recieveRGBs.get(i));
        	
            Imgproc.drawContours(newFrame, squareContours, i, new Scalar(255, 255, 255), 3);
        }     
        
        
        
        
        
	    return newFrame;
	}
	
	
}
