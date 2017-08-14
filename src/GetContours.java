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
        
        //Grays
        Mat gray = new Mat();
        Imgproc.cvtColor(capturedFrame, gray, Imgproc.COLOR_RGB2GRAY);
        
        //Blur
        Mat blur = new Mat();
        Imgproc.blur(gray, blur, new Size(3,3));
        //Canny image
        Mat canny = new Mat();
        Imgproc.Canny(blur, canny, 20, 40, 3, true);

        //Dilate image to increase size of lines
        Mat kernel = Imgproc.getStructuringElement(2, new Size(7,7));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);


        List<MatOfPoint> contours = new ArrayList<>();
        List<MatOfPoint> squareContours = new ArrayList<>();

        
        
        //find contours
        Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        
        //Remove contours that aren't close to a square shape.
        //Wondering if there is a way I can improve this?
        for(int i = 0; i < contours.size(); i++){

            double area = Imgproc.contourArea(contours.get(i)); 
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
            
            double perimeter = Imgproc.arcLength(contour2f, true);
            //Found squareness equation on wiki... 
            //https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
            double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

            //add contour to new List if it has a square shape.
            if(squareness >= 0.7 && squareness <= 0.9 && area >= 2000 && squareContours.size() < 9){
               squareContours.add(contours.get(i));
            }
        }
        
        //Put overlapping code over here....
       // System.out.println("square contour size: " + squareContours.size());
        for(int i = 0; i < squareContours.size(); i++){
        	
        //	squareContours.get(i).copyTo(recieveRGBs.get(i));
        	
            Imgproc.drawContours(newFrame, squareContours, i, new Scalar(255, 255, 255), 3);
        }   
	 
    	return newFrame;
	}
	
	List<Mat> foundFrames = new ArrayList<>(); 
	private void findRGBs(Mat currFrame,List<MatOfPoint> squareContours){
		
		for(int i = 0; i < squareContours.size(); i++){
			squareContours.get(i).convertTo(foundFrames.get(i), i);
			
		}
	}
	
	 
	
	
	
	/*List<Point> overlappingArea = new ArrayList<>();
    MatOfPoint2f approxCurve = new MatOfPoint2f();

    for(int n = 0; n < squareContours.size(); n++){
		
	    //Convert contours(n) from MatOfPoint to MatOfPoint2f
        MatOfPoint2f contour2f = new MatOfPoint2f( squareContours.get(n).toArray());
        //Processing on mMOP2f1 which is in type MatOfPoint2f
        double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
        Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

        //Convert back to MatOfPoint
        MatOfPoint points = new MatOfPoint( approxCurve.toArray());

        
        // Get bounding rect of contour
        Rect rect = Imgproc.boundingRect(points);
        MatOfRect rects = new MatOfRect(rect);
        MatOfInt matofint = new MatOfInt(n);
        
      
        Point cooridnates = new Point(rect.x , rect.y);
        
        //System.out.println(rect.x + "," + rect.y);
    	Objdetect.groupRectangles(rects, matofint, 9);
        overlappingArea.add(cooridnates);
        
        for(int i = 0; i < overlappingArea.size(); i++){
        	System.out.println("Difference: " + Math.abs(rect.x - overlappingArea.get(i).x));
        	if (Math.abs(rect.x - overlappingArea.get(i).x) >= 5 && Math.abs(rect.y - overlappingArea.get(i).y) >= 5) {
        		Imgproc.rectangle(newFrame, cooridnates, new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(255,255,255), 3);
        	}
        }
	}  */
}
