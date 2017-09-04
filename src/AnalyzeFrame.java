import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.opencv.imgproc.Imgproc;
public class AnalyzeFrame {


	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	boolean capturesCompleted;
	int counter = 0;
	List<SortColors> colorsToSort = new ArrayList<SortColors>();
	Color[] colorArray = new Color[54];
	int currentIndex = 0;


	public Mat captureFrame(Mat capturedFrame , boolean captured){
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

		List <MatOfPoint> finalContours = new ArrayList<MatOfPoint>();
		Rect currRect = new Rect();
		MatOfPoint2f approxCurve = new MatOfPoint2f();


		findContours(dilated , finalContours);

		drawRectangles(finalContours, newFrame, captured, currRect , approxCurve);

		determineToCaptureOrPass(finalContours, captured, currRect, approxCurve, newFrame);

		return newFrame;
	}


	private void findContours(Mat dilated , List<MatOfPoint> finalContours){
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();    

		//define hierarchy
		Mat hierarchy = new Mat();
		//find contours
		Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		//Remove contours that aren't close to a square shape.
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
				if (current_hierarchy[3] != -1 && current_hierarchy[2] == -1) {
					finalContours.add(contours.get(i));
				}
			}
		}
	}

	private void drawRectangles(List <MatOfPoint> finalContours , Mat frameToDrawOn, boolean captured, Rect currRect, MatOfPoint2f approxCurve){

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
			if(captured == false){
				Imgproc.rectangle(frameToDrawOn, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar (255, 255, 255), 3); 
			}	
			currRect = new Rect(new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height));
		}	
	}

	private void determineToCaptureOrPass(List<MatOfPoint> finalContours , boolean captured , Rect currRect , MatOfPoint2f approxCurve, Mat newFrame){

		if((captured == true && finalContours.size() == 9 )|| (captured == true && finalContours.size() == 16)){
			System.out.println("size: " + finalContours.size());
			for(int i = 0; i < finalContours.size();i++){
				MatOfPoint2f contour2f = new MatOfPoint2f( finalContours.get(i).toArray() );
				//Epsilon (size of rectangle)
				double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
				Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
				//Convert back to MatOfPoint
				MatOfPoint points = new MatOfPoint( approxCurve.toArray());
				Rect rect = Imgproc.boundingRect(points);
				currRect = new Rect(new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height));

				getColors(newFrame, currRect);
			}	
		} else if((captured == true && finalContours.size() != 9) || (captured == true && finalContours.size() != 16)){
			System.err.println("You didn't capture 9 stickers! Take another picture.");
		}    	
		int finalCheck = 0;
		if(colorArray[53] != null && finalCheck == 0){
			finalCheck = -1;
			capturesCompleted = true;
			System.out.println(Arrays.toString(colorArray));
			//all have been fulfilled now color math.
			changeThemColors(colorArray);
			System.exit(0);
		}
	}




	private void getColors(Mat img2read , Rect roi){ //This method gets called in a loop of how many rectangles I have
		int rAvg = 0 , bAvg = 0, gAvg = 0;    //I pass the current rectangle in the loop
		int rSum = 0, bSum = 0, gSum = 0;

		img2read = new Mat(img2read, roi); //image size of rect (saved contour size and coordinates)
		img2read.convertTo(img2read, CvType.CV_8UC1); //convert to workable type for getting RGB data

		int totalBytes = (int) (img2read.total() * img2read.channels());
		byte buff[] = new byte[totalBytes];
		int channels = img2read.channels();

		img2read.get(0,0,buff);
		int stride = channels * img2read.width();
		//Color calculation below avg color
		for(int i = 0; i < img2read.height();i++){
			for(int j = 0; j < stride; j+= channels){
				int r = unsignedToBytes(buff[(i * stride) + j]);
				int g = unsignedToBytes(buff[(i * stride)+ j + 1]);
				int b = unsignedToBytes(buff[(i * stride)+ j + 2]);

				rSum += r;
				gSum += g;
				bSum += b;
			}
		}

		rAvg = (int) (rSum / img2read.total());
		gAvg = (int) (gSum /  img2read.total());
		bAvg = (int) (bSum / img2read.total());

		Color colorToSort = new Color(rAvg, gAvg, bAvg);
		SortColors s = new SortColors();

		s.x = roi.x;
		s.y = roi.y;
		s.color = colorToSort;

		colorsToSort.add(s);

		counter++;
		if(counter == 9){ // after I have 9 colors I then sort them
			sortColors();
			counter = 0;// reset counter
		}
	}


	public int unsignedToBytes(byte b) {
		return b & 0xFF;
	}

	private void addColorToArray(int red, int green, int blue) {
		colorArray[currentIndex] = new Color(red, green, blue);
		currentIndex += 1;
	}




	private void sortColors(){	
		Collections.sort(colorsToSort);				
		List<Color> getColors = new ArrayList<Color>();

		for(SortColors c : colorsToSort){
			getColors.add(c.getColor());
		}

		colorsToSort = new ArrayList<SortColors>(); //reset after pics have been processed and ordered
		Color[] colorsToAdd = getColors.toArray(new Color[9]);

		String toClean = Arrays.toString(colorsToAdd);
		toClean = cleanColorString(toClean);
		
		System.out.println(toClean);
		for(int i = 0; i < colorsToAdd.length;i++){
			addColorToArray(colorsToAdd[i].getRed(), colorsToAdd[i].getGreen(), colorsToAdd[i].getBlue());
		}
	}


	//indexes to track 4,13, 22,31,40,49

	//https://github.com/dwalton76/rubiks-color-resolver/blob/master/rubikscolorresolver/__init__.py#L1425


	private void changeThemColors(Color [] colorArrayToChange){

		double [][] LaBArray = new double[54][];
		for(int i = 0; i < colorArrayToChange.length;i++){
			LaBArray[i] = RGB2Lab(colorArrayToChange[i]);
		}

		System.out.println(Arrays.deepToString(LaBArray));

		String toClean = Arrays.toString(colorArrayToChange);
		toClean = cleanColorString(toClean);
		
		System.out.println(toClean);
		//findCenters(LaBArray);
	}
	
	private static String cleanColorString(String toClean){
		
		toClean = toClean.replaceAll("java.awt.Color", "").replaceAll("[a-z]=", "");
		return toClean;
	}

	private  void findCenters(double[][] LabArray){ //this method finds closest color to provided center

		final int SIZE = 6;
		ColorAndIndex colors = new ColorAndIndex();

		double[][] centers = {LabArray[4],LabArray[13],LabArray[22],LabArray[31],LabArray[40],LabArray[49]}; 

		double [][] crayolaColors = 
			{{100, 0.00526049995830391, -0.010408184525267927},{35.71689493804023, 38.18518746791636, 43.982516784310114},{39.14982168015123,-32.450520997738295,10.605199206744654},
			{20.18063311070288, 40.48184409611946 , 29.94034624098952},{23.921448197848527, 5.28400492805528, -30.63998357385018},{81.19132678332146, -17.614271251146395, 81.03415848709281}};


		double  distance = 0;
		double highestDistance = Integer.MAX_VALUE;
		List<Integer> foundColorsToSkip = new ArrayList<Integer>();

		ColorAndIndex[] centerHolder = new ColorAndIndex[6];

		for(int currCenter = 0; currCenter < SIZE; currCenter++){
			for(int curColor = 0; curColor < SIZE; curColor++){
				if(foundColorsToSkip.contains(curColor))
					continue;

				distance = de_CIE2000(centers[currCenter], crayolaColors[curColor]);

				if(distance < highestDistance){
					highestDistance = distance;
					colors.distance = 0; //this don't matter
					colors.index = curColor; //update Object index
					colors.labArray = centers[currCenter];
				}
			}
			foundColorsToSkip.add(colors.index);
			centerHolder[currCenter] = colors;
			colors = null;
			colors = new ColorAndIndex();
			distance = 0;
			highestDistance = Integer.MAX_VALUE;
		}
		
		System.out.println(centerHolder[0].index + ", " + centerHolder[1].index +", "+ centerHolder[2].index+ "\n" + centerHolder[3].index + ", " + centerHolder[4].index + ", " + centerHolder[5].index );
		System.out.println();
		System.out.printf("%s, %s, \n %s, %s \n %s, %s \n %s, %s \n %s, %s \n %s, %s \n",
				centerHolder[0].index, Arrays.toString(centerHolder[0].labArray),
				centerHolder[1].index, Arrays.toString(centerHolder[1].labArray),
				centerHolder[2].index, Arrays.toString(centerHolder[2].labArray),
				centerHolder[3].index, Arrays.toString(centerHolder[3].labArray),
				centerHolder[4].index, Arrays.toString(centerHolder[4].labArray),
				centerHolder[5].index, Arrays.toString(centerHolder[5].labArray));
		//System.out.println(centerHolder[0].);
	}

	/*@Override
	public String toString(){
		
	}*/
	
	@SuppressWarnings("unchecked") //<-Stop annoying errors
	private void k_means(double[][] laBArray , ColorAndIndex[] colors){// ﴾͡๏̯͡๏﴿ O'RLY?
		
		final int SIZE = 6;
        double[][] centers = {colors[0].labArray,colors[1].labArray,colors[2].labArray,colors[3].labArray,colors[4].labArray,colors[5].labArray}; 
		
        ColorAndIndex[] clusters = new ColorAndIndex[SIZE];
        for(int i = 0; i < clusters.length; i++){
        	clusters[i] = new ColorAndIndex();
        }
         
        Object[][] colorsToSelect = new Object[SIZE][]; 
        //Objects of all colors to select first sorted 8.
        
		@SuppressWarnings("rawtypes")
		ArrayList[] clusterDistances = new ArrayList[SIZE];
		for(int i = 0; i < clusterDistances.length;i++){
			clusterDistances[i] = new ArrayList<Object>();
		}
		
		for(int j = 0; j < SIZE; j++){
			for(int i = 0; i < laBArray.length; i++){
				if(i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49){
					//to avoid comparing centers
					continue;
				}
				
				clusters[j].distance = euclideanDistance(centers[j], laBArray[i]);
				clusters[j].labArray = laBArray[i];
				clusters[j].index = i;
				clusterDistances[j].add(clusters[j]);
				clusters[j] = null;
				clusters[j] = new ColorAndIndex();	
				
			}	
			Collections.sort(clusterDistances[j]);
			colorsToSelect[j] = clusterDistances[j].toArray(new Object[0]);
			colorsToSelect[j] = Arrays.copyOf(colorsToSelect[j], 8);
	    }
			
		System.out.println(Arrays.toString(colors[0].labArray));
		System.out.println(Arrays.toString(colorsToSelect[0]));
	
	}	
	private  double euclideanDistance(double[] lab , double []lab1){
		double L = lab[0] - lab1[0];
		double A = lab[1] - lab1[1];
		double B = lab[2] - lab1[2];

		return Math.sqrt((L * L) +  (A * A) +  (B * B));	
	}



	private double[] RGB2Lab(Color RGBColor){

		int R = RGBColor.getRed();
		int G = RGBColor.getGreen();
		int B = RGBColor.getBlue();
		double r, g, b, X, Y, Z, xr, yr, zr;

		// D65/2ï¿½
		double Xr = 95.047;  
		double Yr = 100.0;
		double Zr = 108.883;


		// --------- RGB to XYZ ---------//

		r = R/255.0;
		g = G/255.0;
		b = B/255.0;

		if (r > 0.04045)
			r = Math.pow((r+0.055)/1.055,2.4);
		else
			r = r/12.92;

		if (g > 0.04045)
			g = Math.pow((g+0.055)/1.055,2.4);
		else
			g = g/12.92;

		if (b > 0.04045)
			b = Math.pow((b+0.055)/1.055,2.4);
		else
			b = b/12.92 ;

		r*=100;
		g*=100;
		b*=100;

		X =  0.4124*r + 0.3576*g + 0.1805*b;
		Y =  0.2126*r + 0.7152*g + 0.0722*b;
		Z =  0.0193*r + 0.1192*g + 0.9505*b;


		// --------- XYZ to Lab --------- //

		xr = X/Xr;
		yr = Y/Yr;
		zr = Z/Zr;

		if ( xr > 0.008856 )
			xr =  (double) Math.pow(xr, 1/3.);
		else
			xr = (double) ((7.787 * xr) + 16 / 116.0);

		if ( yr > 0.008856 )
			yr =  (double) Math.pow(yr, 1/3.);
		else
			yr = (double) ((7.787 * yr) + 16 / 116.0);

		if ( zr > 0.008856 )
			zr =  (double) Math.pow(zr, 1/3.);
		else
			zr = (double) ((7.787 * zr) + 16 / 116.0);


		double[] lab = new double[3];

		lab[0] = (116*yr)-16;
		lab[1] = 500*(xr-yr); 
		lab[2] = 200*(yr-zr); 

		return lab;


	}

	private double dL_CIE2000(double[] x, double[] y) {
		return y[0] - x[0];
	}

	final double pow_25_7 = Math.pow(25, 7);


	private double de_CIE2000(double[] x, double[] y) {
		// Implementation of the DE2000 color difference defined in "The
		// CIEDE2000 Color-Difference Formula: Implementation Notes,
		// Supplementary Test Data, and Mathematical Observations" from Gaurav
		// Sharma, Wencheng Wu and Edul N. Dalal
		// Pdf available at :
		// http://www.ece.rochester.edu/~gsharma/ciede2000/ciede2000noteCRNA.pdf
		// (last checked 17/07/2016)
		double L1 = x[0];
		double a1 = x[1];
		double b1 = x[2];

		double L2 = y[0];
		double a2 = y[1];
		double b2 = y[2];

		double kl = 1, kc = 1, kh = 1;

		double C1 = Math.sqrt(a1 * a1 + b1 * b1);
		double C2 = Math.sqrt(a2 * a2 + b2 * b2);

		double Lpbar = (L1 + L2) / 2;

		double Cbar = 0.5 * (C1 + C2);
		double Cbarpow7 = Math.pow(Cbar, 7);

		double G = 0.5 * (1 - Math.sqrt(Cbarpow7 / (Cbarpow7 + pow_25_7)));

		double a1p = (1 + G) * a1;
		double a2p = (1 + G) * a2;

		double C1p = Math.sqrt(a1p * a1p + b1 * b1);
		double C2p = Math.sqrt(a2p * a2p + b2 * b2);

		double Cpbar = (C1p + C2p) / 2;

		double h1p = Math.atan2(b1, a1p);
		h1p += (h1p < 0) ? 2 * Math.PI : 0;
		double h2p = Math.atan2(b2, a2p);
		h2p += (h2p < 0) ? 2 * Math.PI : 0;

		double dhp = h2p - h1p;
		dhp += (dhp > Math.PI) ? -2 * Math.PI : (dhp < -Math.PI) ? 2 * Math.PI : 0;
		dhp = (C1p * C2p) == 0 ? 0 : dhp;

		double dLp = dL_CIE2000(x, y);
		double dCp = C2p - C1p;

		double dHp = 2 * Math.sqrt(C1p * C2p) * Math.sin(dhp / 2);

		double Hp = 0.5 * (h1p + h2p);
		Hp += Math.abs(h1p - h2p) > Math.PI ? Math.PI : 0;
		if (C1p * C2p == 0) {
			Hp = h1p + h2p;
		}
		double T = 1 - 0.17 * Math.cos(Hp - 0.523599) + 0.24 * Math.cos(2 * Hp) + 0.32 * Math.cos(3 * Hp + 0.10472)
		- 0.20 * Math.cos(4 * Hp - 1.09956);
		double Lpbarpow502 = (Lpbar - 50) * (Lpbar - 50);
		double Sl = 1 + 0.015 * Lpbarpow502 / Math.sqrt(20 + Lpbarpow502);
		double Sc = 1 + 0.045 * Cpbar;
		double Sh = 1 + 0.015 * Cpbar * T;
		double f = (Math.toDegrees(Hp) - 275) / 25;
		double dOmega = (30 * Math.PI / 180) * Math.exp(-(f * f));
		double Rc = 2 * Math.sqrt(Math.pow(Cpbar, 7) / (Math.pow(Cpbar, 7) + Math.pow(25, 7)));
		double RT = -1 * Math.sin(2 * dOmega) * Rc;

		dLp = dLp / (kl * Sl);
		dCp = dCp / (kc * Sc);
		dHp = dHp / (kh * Sh);

		return Math.sqrt(dLp * dLp + dCp * dCp + dHp * dHp + RT * dCp * dHp);
	}


}

