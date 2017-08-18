import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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

        Mat capturedFrame = Imgcodecs.imread("greenSample.png"); //Loaded in RGB
        Mat copyToScan = Imgcodecs.imread("greenSample.png");
        Imgproc.cvtColor(copyToScan, copyToScan, Imgproc.COLOR_RGB2BGR);
        Imgproc.cvtColor(capturedFrame, capturedFrame, Imgproc.COLOR_RGB2BGR);
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

        //Dilate image to increase size of lines
        Mat kernel = Imgproc.getStructuringElement(2, new Size(7,7));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);

        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours
        Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        //Remove contours that aren't close to a square shape.
        List<MatOfPoint> finalContours = new ArrayList<>();
        int[] current_hierarchy = new int[4];
        for(int i = 0; i < contours.size(); i++){
            double area = Imgproc.contourArea(contours.get(i)); 
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
            double perimeter = Imgproc.arcLength(contour2f, true);
            //Found squareness equation on wiki... 
            //https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
            double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);

            if(squareness >= 0.7 && squareness <= 0.9 && area >= 2000){
                hierarchy.get(0, i, current_hierarchy);
                System.out.println(current_hierarchy[3]);
                if (current_hierarchy[3] != -1) {
                    finalContours.add(contours.get(i));
                }
            }
        }
        
        System.out.println("final size: "+  finalContours.size());

            MatOfPoint2f approxCurve = new MatOfPoint2f(); 
        	for(int n = 0; n < finalContours.size(); n++){

        	    //Convert contours(i) from MatOfPoint to MatOfPoint2f
                MatOfPoint2f contour2f = new MatOfPoint2f( finalContours.get(n).toArray() );
                //Epsilon (size of rectangle)
                double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
                Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
                //Convert back to MatOfPoint
                MatOfPoint points = new MatOfPoint( approxCurve.toArray());

                // Get bounding rect of contour
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(newFrame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 255, 255), 3); 
                Rect currRect = new Rect(new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height));
                
            	scanBlackCopy(copyToScan, currRect);

        	}	
        	Imshow withRects= new Imshow("");
            withRects.show(newFrame);    
        	
	} 
	
	
	
	
	private static Color[] colorArray = new Color[54];
	private static int currentIndex = 0;
	
	static List<String> allColors = new ArrayList<>();
	private static void scanBlackCopy(Mat img2read , Rect roi){
		int rAvg = 0 , bAvg = 0, gAvg = 0;
		int rSum = 0, bSum = 0, gSum = 0;
		
		img2read = new Mat(img2read, roi);
		img2read.convertTo(img2read, CvType.CV_8UC1);
		int totalBytes = (int) (img2read.total() * img2read.channels());
		byte buff[] = new byte[totalBytes];
		int channels = img2read.channels();
		img2read.get(0,0,buff);
		int stride = channels * img2read.width();
		for(int i = 0; i < img2read.height();i++){
			for(int x = 0; x < stride; x+= channels){
				int r = unsignedToBytes(buff[(i * stride) + x]);
				int g = unsignedToBytes(buff[(i * stride)+ x + 1]);
				int b = unsignedToBytes(buff[(i * stride)+ x + 2]);

				rSum += r;
				gSum += g;
				bSum += b;
				
			}
		}
		//maybe use a while statement.. while true, and pass as param to next method.
		float[] hsv = new float[3];
		
		rAvg = (int) (rSum / img2read.total());
		gAvg = (int) (gSum /  img2read.total());
		bAvg = (int) (bSum / img2read.total());
		
		
		Color.RGBtoHSB(rAvg, gAvg, bAvg, hsv);
		
		hsv[2] = 1; //Set to max value
		
		int rgb = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);

		Color brightenedColor = new Color(rgb);	
		System.out.printf("R: %s G: %s B: %s \n", brightenedColor.getRed(), brightenedColor.getGreen(), brightenedColor.getBlue());		
	}
	
		
	public static int unsignedToBytes(byte b) {
		return b & 0xFF;
	}
	
	private static void addColorToArray(int red, int green, int blue) {
		colorArray[currentIndex] = new Color(red, green, blue);
		currentIndex += 1;
	}
}
