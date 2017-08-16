import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
	
	
	public Mat captureFrame(Mat capturedFrame, boolean captured){
		
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

        
        
	    //define hierarchy
	    Mat hierarchy = new Mat();
	    //find contours
	    Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
	    
	    //Remove contours that aren't close to a square shape.
	    for(int i = 0; i < contours.size(); i++){
	
		        double area = Imgproc.contourArea(contours.get(i)); 
		        MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
		        
		        double perimeter = Imgproc.arcLength(contour2f, true);
		        //Found squareness equation on wiki... 
		        //https://en.wikipedia.org/wiki/Shape_factor_(image_analysis_and_microscopy)
		        double squareness = 4 * Math.PI * area / Math.pow(perimeter, 2);
		        
		        //add contour to new List if it has a square shape.
		        if(squareness >= 0.7 && squareness <= 0.9 && area >= 2000 && squareContours.size() <= 8){
		           squareContours.add(contours.get(i));
		        }
	    	}
	    
        
      /*  if(captured == true){
        	findRGBs(squareContours);
        }
        
      */
	    //Put overlapping code over here....
       // System.out.println("square contour size: " + squareContours.size());
        /*for(int i = 0; i < squareContours.size(); i++){
            Imgproc.drawContours(newFrame, squareContours, i, new Scalar(255, 255, 255), 3);
        } */  
        
        
    	return newFrame;
	}
	
	List<Mat> foundFrames = new ArrayList<>();
	private Color[] colorArray = new Color[54];
	private int currentIndex = 0;
	
	private void findRGBs(List<MatOfPoint> squareContours){
		Mat mat = new Mat(); //better than reusing passed parameter, there is no point.
		//Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
		System.out.println(mat.type());
		for(int i = 0; i < squareContours.size(); i++){
			squareContours.get(i).convertTo(mat, CvType.CV_8S);
			//System.out.println(i);
			getPixelValues(mat);
			foundFrames.add(mat);
		}
	}
	
	 
	private void getPixelValues(Mat img) {
		System.out.println("iterating");
		int width = img.width();
		int height = img.height();
		int rSum = 0;
		int rAvg = 0;
		int gSum = 0;
		int gAvg = 0;
		int bSum = 0;
		int bAvg = 0;

		
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
		
		rAvg = (int) (rSum / img.total());
		gAvg = (int) (gSum /  img.total());
		bAvg = (int) (bSum / img.total());
		
		
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
	public int unsignedToBytes(byte b) {
		return b & 0xFF;
	}
	
	private void addColorToArray(int red, int green, int blue) {
		colorArray[currentIndex] = new Color(red, green, blue);
		currentIndex += 1;
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
