import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
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
 
    int frames = 0;
    BufferedImage getOneFrame() {
    	
    	frames++;
    		
    	cap.read(mat2Img.mat);
    	
    	if(frames == 100){
			Imgcodecs.imwrite("first.png", mat2Img.mat);
			System.out.println("WRITTEN!!!");
		}
    	
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);
    	
		return mat2Img.getImage(mat2Img.mat);
    }   
}