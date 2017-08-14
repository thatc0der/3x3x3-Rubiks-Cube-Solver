import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;



public class VideoCap{
	
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
    /*int savedCount = 0; //keep track of saved frames
    String[] photos = {"first.png","second.png","third.png","forth.png",
    "fifth.png","sixth.png","seventh.png","eigth.png","ninth.png"};
    */
    boolean captured = false;
    BufferedImage getOneFrame() {
    	
    	frames++;
    		
    	cap.read(mat2Img.mat);
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);

    	if(captured == true){
    		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);
    		System.out.println("Written!");
    		Imgcodecs.imwrite("first.png", mat2Img.mat);
    		System.exit(0);
    	}
		return mat2Img.getImage(mat2Img.mat);
    }  
}