import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LearnContours {

	public static void main(String[] args) {

		
		Mat image = Imgcodecs.imread("/home/chris/Downloads/squares.png");
		if(image.empty() == true) {
		    System.out.println("Error: no image found!");
		}

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat image32S = new Mat();
		image.convertTo(image32S, CvType.CV_32SC1);

		Imgproc.findContours(image32S, contours, new Mat(), Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);

		// Draw all the contours such that they are filled in.
		Mat contourImg = new Mat(image32S.size(), image32S.type());
		for (int i = 0; i < contours.size(); i++) {
		    Imgproc.drawContours(contourImg, contours, i, new Scalar(255, 255, 255), -1);
		}

		Imgcodecs.imwrite("debug_image.jpg", contourImg); // DEBUG
	}

}
