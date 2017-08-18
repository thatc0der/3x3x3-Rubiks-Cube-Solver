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
    Mat2Image freshImg = new Mat2Image();//copy that has no wierd drawings.
    VideoCap(){
        cap = new VideoCapture();
        cap.open(0);
    } 
 
    int frames = 0;
    /*int savedCount = 0; //keep track of saved frames
    String[] photos = {"first.png","second.png","third.png","forth.png",
    "fifth.png","sixth.png","seventh.png","eigth.png","ninth.png"};
    */
    boolean captured;
    BufferedImage getOneFrame() {
    	
    	frames++;
    		
    	cap.read(mat2Img.mat);
    	cap.read(freshImg.mat);
    	
    	//freshImg.getImage(freshImg.mat);
    	
		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat , false);
		
    	if(captured == true){
    		cap.read(freshImg.mat);
    		
    		
    		freshImg.getImage(freshImg.mat);
    		
    		freshImg.mat = takeFrame.captureFrame(freshImg.mat, true);
    		
    		Imgcodecs.imwrite("greenSample.png", freshImg.mat);
    		captured = false;
    	}
		
		
		
		
		/*if(captured == true){
    		System.out.println(captured);
    		Mat saveClean = new Mat();
    		mat2Img.mat.copyTo(saveClean);
    		Imgcodecs.imwrite("greenSample.png", saveClean);
    		Imshow m8 = new Imshow("wallo");
    		m8.show(saveClean);
    		mat2Img.mat = takeFrame.captureFrame(mat2Img.mat);
    		System.out.println("written!");
        	captured = false;

        	return mat2Img.getImage(mat2Img.mat);
    	}
*/		return mat2Img.getImage(mat2Img.mat);
    }  
}