import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LearnContours {

	public static void main(String[] args) {


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat capturedFrame = Imgcodecs.imread("first.png");
        Mat newFrame = new Mat();
        capturedFrame.copyTo(newFrame);
       
        
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
        Mat kernel = Imgproc.getStructuringElement(2, new Size(7,7));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);

        Imshow m8 = new Imshow("");
        m8.show(dilated);

        List<MatOfPoint> contours = new ArrayList<>();
        List<MatOfPoint> squareContours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours
        Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        
        
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
            

            //Found squareness equation on wiki... 
            // https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
            double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

           // System.out.println("Squareness: " + squareness);


            if(squareness >= 0.7 && squareness <= 0.9 && area >= 2000 && squareContours.size() < 9){
            	
               squareContours.add(contours.get(i));
            }
        }
        
        System.out.println("square contours size: "+  squareContours.size());

        for(int i = 0; i < squareContours.size(); i++){
            Imgproc.drawContours(newFrame, squareContours, i, new Scalar(0, 0, 255), 2);
        }

        
	
	
 	/*
	List<Point> overlappingArea = new ArrayList<>();
	    	
    MatOfPoint2f approxCurve = new MatOfPoint2f();
    int counter = 0;
	for(int n = 0; n < squareContours.size(); n++){

	
		
	    //Convert contours(i) from MatOfPoint to MatOfPoint2f
        MatOfPoint2f contour2f = new MatOfPoint2f( squareContours.get(n).toArray() );
        //Epsilon (size of rectangle)
        double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
        Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

        //Convert back to MatOfPoint
        MatOfPoint points = new MatOfPoint( approxCurve.toArray());

        // Get bounding rect of contour
        Rect rect = Imgproc.boundingRect(points);
        
        Point cooridnates = new Point(rect.x , rect.y);
        
       // System.out.println(rect.x + "," + rect.y);
       
        
        overlappingArea.add(cooridnates);
        System.out.println("size " + overlappingArea.size());
        for(int i = 0; i < overlappingArea.size(); i++){
        	//System.out.println("cur: " + rect.x +"," + rect.y);
        	//System.out.println("src: " + overlappingArea.get(i).x + "," + overlappingArea.get(i).y);
        	if (Math.abs(rect.x - overlappingArea.get(i).x) < 5 && Math.abs(rect.y - overlappingArea.get(i).y) < 5) {
        		counter++;
        		Imgproc.rectangle(newFrame, cooridnates, new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(255,255,255), 3);
        	}
        }
        
        overlappingArea.add(new Point(rect.x + rect.width, rect.y+rect.height));
        
        if(rect.x){
        	System.out.println("INSIDE");
        }
        
       
        
        
     //  Point rectangle = new Point(rect.x, rect.y);
        
        overlappingArea.add(newCoordinatesXY);
        if (overlappingArea.contains(Point (rect.x, rect.y)) {
			
		}

         // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
        
       // Imgproc.rectangle(newFrame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 0, 0, 255), 3); 

		}
		System.out.println(counter);*/
	
	showImg.show(newFrame);
    Imgcodecs.imwrite("removed.png", newFrame);


	}    	
}
