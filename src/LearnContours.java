import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LearnContours {

	public static void main(String[] args) {

		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat capturedFrame = Imgcodecs.imread("first.png");
		
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
    	
    	//Dilate image to increase size of lines
        Mat kernel = Imgproc.getStructuringElement(1, new Size(3,3));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);
        
    	
    	List<MatOfPoint> contours = new ArrayList<>();
    	//find contours
    	Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
    	
    	//convert image 
    	Imgproc.cvtColor(capturedFrame, capturedFrame, Imgproc.COLOR_BGR2RGB);

    	
    	//Draw contours on original image
    	for(int n = 0; n < contours.size(); n++){
    		Imgproc.drawContours(capturedFrame, contours, n, new Scalar(255, 0 , 0), 1);
    	}
    	
    	Imgcodecs.imwrite("before.png", capturedFrame);
    	
    	//display image with all contours
    	Imshow showImg = new Imshow("displayImage");
    	showImg.show(capturedFrame);
    	
    	
    	//Remove contours that aren't close to a square shape.
    	for(int i = 0; i < contours.size(); i++){
    		
    		double area = Imgproc.contourArea( contours.get(i)); 
    		MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
    		double perimeter = Imgproc.arcLength(contour2f, true);

    		double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

    		System.out.println("Squareness: " + squareness);


    		if(squareness <= 0.7 && squareness >=  0.8){
    			contours.remove(i);
    		}
    	}
    	
    	
    	for(int i = 0; i < contours.size(); i++){
    		Imgproc.drawContours(capturedFrame, contours, i, new Scalar(0, 255, 0), 1);
    	}
    	
    	    	
    		
    	showImg.show(capturedFrame);
    	Imgcodecs.imwrite("remove.png", capturedFrame);
    	
    	
    	for(int i = 0; i < contours.size(); i++){

    		Imgproc.drawContours(capturedFrame, contours, i, new Scalar(0, 0, 255), 1);
    	}
    	
    	
    	Imgcodecs.imwrite("after.png", capturedFrame);
    	
    	Imshow img = new Imshow("firstImg");
    	
    	img.show(capturedFrame);
     		
    	
	}
	
	
 	
	
	/*    	
    MatOfPoint2f         approxCurve = new MatOfPoint2f();
	for(int n = 0; n < contours.size(); n++){

	    //Convert contours(i) from MatOfPoint to MatOfPoint2f
        MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(n).toArray() );
        //Processing on mMOP2f1 which is in type MatOfPoint2f
        double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
        Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

        //Convert back to MatOfPoint
        MatOfPoint points = new MatOfPoint( approxCurve.toArray() );

        // Get bounding rect of contour
        Rect rect = Imgproc.boundingRect(points);

         // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
        Imgproc.rectangle(capturedFrame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 0, 0, 255), 3); 

        
        
	}
	    	*/
}
