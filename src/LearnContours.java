import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
        Mat newFrame = new Mat();
        capturedFrame.copyTo(newFrame);
       
        Imshow init = new Imshow("");
        init.show(capturedFrame);
        
        //Gray
        Mat gray = new Mat();
        Imgproc.cvtColor(capturedFrame, gray, Imgproc.COLOR_BGR2GRAY);
        
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
        Mat hierarchy = new Mat();
        //find contours
        Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        
   
        //Draw contours on original image
        for(int n = 0; n < contours.size(); n++){
            Imgproc.drawContours(capturedFrame, contours, n, new Scalar(255, 0 , 0), 1);
        }

        
        //Remove contours that aren't close to a square shape.
        for(int i = 0; i < contours.size(); i++){

            double area = Imgproc.contourArea(contours.get(i)); 
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
            double perimeter = Imgproc.arcLength(contour2f, true);
            

            //Found squareness equation on wiki... 
            //https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
            double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

            if(squareness >= 0.7 && squareness <= 0.9 && area >= 2000 && squareContours.size() < 9){
               squareContours.add(contours.get(i));
            }
        }
        
        System.out.println("square contours size: "+  squareContours.size());

            MatOfPoint2f approxCurve = new MatOfPoint2f();
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
                Imgproc.rectangle(newFrame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 255, 255), 3); 

        	}	
     
        Imshow withRects= new Imshow("");
        withRects.show(newFrame );
	
        findRGBs(squareContours);
	} 
	
	
	
	
	static List<Mat> foundFrames = new ArrayList<>();
	private static Color[] colorArray = new Color[54];
	private static int currentIndex = 0;
	
	private static void findRGBs(List<MatOfPoint> squareContours){

		
		for(int i = 0; i < squareContours.size(); i++){
			squareContours.get(i).convertTo(squareContours.get(i), CvType.CV_8U);
			
			//Imgproc.cvtColor(squareContours.get(i), squareContours.get(i), Imgproc.COLOR_BGR2RGB);
			
			getPixelValues(squareContours.get(i));
			foundFrames.add(squareContours.get(i));
		}
	}
	
	 
	private static void getPixelValues(Mat img) {
		
		Imgproc.cvtColor(img, img, Imgproc.COLOR_GRAY2RGB, 2);
		int width = img.width();
		int height = img.height();
		int rSum = 0;
		int rAvg = 0;
		int gSum = 0;
		int gAvg = 0;
		int bSum = 0;
		int bAvg = 0;
		
	
		Imshow wallo = new Imshow("");
		wallo.show(img, "Contour", true);
		
		
		System.out.println();
		int channels = img.channels();
		int totalBytes = (int) (img.total() * img.channels());
		byte buff[] = new byte[totalBytes];
		img.get(0, 0, buff);

		for (int i = 0; i < height; i++) {
			// stride is the number of bytes in a row of smallImg
			int stride = channels * width;
			for (int j = 0; j < stride; j += channels) {

				int r = unsignedToBytes(buff[(i * stride) + j]);
				int g = unsignedToBytes(buff[(i * stride) + j + 1]);
				int b = unsignedToBytes(buff[(i * stride) + j + 2]);

				rSum += r;
				gSum += g;
			    bSum += b;
				

			}
		}
		

		float[] hsv = new float[3];
		
		rAvg = rSum / (img.width() * img.height());
		gAvg = gSum / (img.width() * img.height());
		bAvg = bSum / (img.width() * img.height());
		
		
		Color.RGBtoHSB(rAvg, gAvg, bAvg, hsv);
		
		hsv[2] = 1; //Set to max value
		
		int rgb = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);

		Color color = new Color(rgb);
		System.out.println("R: " + color.getRed());
		System.out.println("G: " + color.getGreen());
		System.out.println("B: " + color.getBlue());
		
		System.out.println("\n"+"\n"+"\n"+"\n");
		addColorToArray(color.getRed(), color.getGreen(), color.getBlue());

	}
	public static int unsignedToBytes(byte b) {
		return b & 0xFF;
	}
	
	private static void addColorToArray(int red, int green, int blue) {
		colorArray[currentIndex] = new Color(red, green, blue);
		currentIndex += 1;
	}
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
    
    Imgproc.rectangle(newFrame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 0, 0, 255), 3); 

	}
	System.out.println(counter);*/

