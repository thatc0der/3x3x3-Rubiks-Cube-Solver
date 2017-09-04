import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestEnvironment {
	
		
	 Color [] RGBarray = {
			 new Color(152,154,167), new Color(152,154,165), new Color(150,162,111),
			 new Color(148,151,165), new Color(146,149,165), new Color(145,159,105),
			 new Color(149,154,173), new Color(148,153,173), new Color(146,162,104),
			 
			 new Color(201,121,104), new Color(202,122,105), new Color(209,129,115), 
			 new Color(198,114,97), new Color(199,114,97), new Color(206,122,106),
			 new Color(118,48,38), new Color(120,48,39), new Color(125,57,48),
			 
			 new Color(71,127,72), new Color(68,124,71), new Color(58,88,129),
			 new Color(59,127,66), new Color(53,121,63), new Color(45,80,133),
			 new Color(28,72,138), new Color(55,128,66), new Color(37,77,142),
			 
			 new Color(122,59,64), new Color(121,60,65), new Color(128,71,76),
			 new Color(116,44,49), new Color(117,45,53), new Color(123,57,64),
			 new Color(205,118,117), new Color(205,115,115), new Color(209,121,121),
			 
			 new Color(77,141,81), new Color(52,93,135), new Color(58,97,138),
			 new Color(66,138,73), new Color(35,82,138), new Color(41,88,142), 
			 new Color(62,142,75), new Color(61,139,77), new Color(40,90,154), 
			 
			 new Color(148,150,166), new Color(140,154,100), new Color(144,158,107), 
			 new Color(146,151,170), new Color(137,154,94), new Color(141,158,104), 
			 new Color(149,155,182), new Color(143,162,104), new Color(142,162,106)};

	
	 
	/*
	  
	 */
	 
	 
	public static void main(String[] args) {
		
		 Color [] RGBarray = {new Color(140,67,87), new Color(21,74,130), new Color(141,72,92), new Color(133,99,85), new Color(158,170,176), new Color(12,65,46), new Color(125,49,64), new Color(124,50,67), new Color(130,93,81), new Color(147,146,116), new Color(144,143,113), new Color(157,167,170), new Color(122,90,81), new Color(121,90,80), new Color(123,89,82), new Color(5,46,90), new Color(8,45,85), new Color(132,130,99), new Color(24,81,65), new Color(179,189,193), new Color(31,86,70), new Color(171,182,186), new Color(15,71,56), new Color(20,75,60), new Color(140,99,83), new Color(164,174,176), new Color(13,69,124), new Color(172,183,189), new Color(160,159,128), new Color(163,162,131), new Color(132,67,85), new Color(131,65,84), new Color(20,66,49), new Color(156,167,169), new Color(153,163,166), new Color(125,59,77), new Color(29,76,128), new Color(160,159,128), new Color(31,75,63), new Color(135,102,92), new Color(25,68,114), new Color(25,69,116), new Color(160,169,173), new Color(126,55,74), new Color(146,144,114), new Color(12,70,56), new Color(16,71,58), new Color(147,111,104), new Color(133,57,80), new Color(147,146,114), new Color(16,67,123), new Color(129,89,80), new Color(141,141,104), new Color(11,62,115)};
		 
		 double [] eleventhSticker = {62.07782180195946, 29.325498290275252, 20.657613652327434};
		 double [] orangeCenter = {57.11418761401045, 31.70038511089801, 23.99605809091703};
		 
		 //double distance = euclideanDistance(eleventhSticker, orangeCenter);
		 //System.out.println("Distance: " + distance);
		 
		 changeThemColors(RGBarray);
	
	} 
	
	private static void changeThemColors(Color [] colorArrayToChange){

		double [][] labArray = new double[54][];
		for(int i = 0; i < colorArrayToChange.length;i++){
			labArray[i] = RGB2Lab(colorArrayToChange[i]);
		}
		
		/*String toClean = Arrays.toString(colorArrayToChange);
		String cleaner = "";
		cleaner = cleanColorString(toClean);
		
		System.out.println(cleaner);*/
		
		findCenters(labArray);
	}
	
	private static String cleanColorString(String toClean){
		
		System.out.println("BEFORE: "+ toClean);
		toClean = toClean.replaceAll("java.awt.Color", "").replaceAll("[a-z]=", "").replaceAll("\\[", "(").replaceAll("\\]", ")");
		return toClean;
	}
	
	private static void findCenters(double[][] LabArray){ //this method finds closest color to provided center
		
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
        String[] colorNames  = {"WHITE","ORANGE","GREEN","RED","BLUE","YELLOW"};
        
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
        			colors.colorString = colorNames[colors.index];
        		}
        	}
        	foundColorsToSkip.add(colors.index);
        	centerHolder[currCenter] = colors;
        	colors = null;
        	colors = new ColorAndIndex();
        	distance = 0;
        	highestDistance = Integer.MAX_VALUE;
        }

        /*
        System.out.println("rawOrange: " + Arrays.toString(LabArray[13]));
        System.out.println(centerHolder[1].toString());
        
        System.out.println("rawRed: " + Arrays.toString(LabArray[31]));
        System.out.println(centerHolder[3].toString());
        
        System.out.println("rawWhite: " + Arrays.toString(LabArray[4]));
        System.out.println(centerHolder[0]);
      */
        //  System.exit(0);
		System.out.println(centerHolder[0].colorString+ ", " + centerHolder[1].colorString+", "+ centerHolder[2].colorString+ "\n" + centerHolder[3].colorString + ", " + centerHolder[4].colorString + ", " + centerHolder[5].colorString);
        k_means(LabArray, centerHolder);
	}
	
	@SuppressWarnings("unchecked") //<-Stop annoying errors
	private static void k_means(double[][] labArray , ColorAndIndex[] colors){// ﴾͡๏̯͡๏﴿ O'RLY?

		final int SIZE = 6;
		double distance = 0;
		double highestDistance = Integer.MAX_VALUE;
		final int STICKER_SIDE_AMOUNT = 9;
		
		double[][] centers = {colors[0].labArray,colors[1].labArray,colors[2].labArray,colors[3].labArray,colors[4].labArray,colors[5].labArray}; 
		
		List<Integer> indexesToSkip = new ArrayList<Integer>();
	
		ArrayList[] allClusters = new ArrayList[SIZE];
		for(int cluster = 0; cluster < SIZE; cluster++){
			allClusters[cluster] = new ArrayList<ColorAndIndex>();
		}
		ColorAndIndex currColor = new ColorAndIndex();
		
		
		List<double[]> allColors =new ArrayList<double[]>(Arrays.asList(labArray));
	//	for(int currCenter = 0; currCenter < SIZE; currCenter++){
		for(int x = 0; x < 6; x++){
			for(int sticker = 0; sticker < STICKER_SIDE_AMOUNT; sticker++){
				for(int i = 0; i < allColors.size(); i++){
					if(indexesToSkip.contains(i))
						continue;
	
					distance = euclideanDistance(centers[x], allColors.get(i));
					if(distance < highestDistance){
						highestDistance = distance;
						currColor.distance = distance;
						currColor.labArray = allColors.get(i);
						currColor.index = i;
						currColor.colorString = colors[x].colorString;
						currColor.numberRepresentation = colors[x].index;
					}
				}
				//System.out.println("distance: " + currColor.distance + ": index: " + currColor.index);
				allClusters[x].add(currColor);
				
				indexesToSkip.add(currColor.index);
				highestDistance = Integer.MAX_VALUE;
				currColor = null;
				currColor = new ColorAndIndex();
			}
		}
		
		System.out.println("White cluster: \n"+ allClusters[0]);
		System.out.println("Orange cluster: \n"+ allClusters[1]);	
		System.out.println("Green cluster: \n"+ allClusters[2]);
		System.out.println("Red cluster: \n"+allClusters[3]);
		System.out.println("Blue cluster: \n"+allClusters[4]);
		System.out.println("Yellow cluster: \n" + allClusters[5]);
		//System.exit(0);
		
		int [] rawCube = new int [54];
		final int MAX_CLUSTER_AMOUNT = 6;
		for(int currCluster = 0; currCluster < MAX_CLUSTER_AMOUNT; currCluster++){
			convertToArray(allClusters[currCluster], rawCube);
		}
		
		System.out.println("RawCube: " +Arrays.toString(rawCube));

	}	
	
	private static void convertToArray(ArrayList<ColorAndIndex> currCluster, int[] rawCube){
		
		for(ColorAndIndex c : currCluster){
			rawCube[c.getIndex()] = c.numberRepresentation;
		}
		
	}

	//Low standard conversation
	//nothing great ever happened with low standards
	//no team ever won a championship with low standards
	

	private static double dL_CIE2000(double[] x, double[] y) {
		return y[0] - x[0];
	}
	
	final static double pow_25_7 = Math.pow(25, 7);

	
	private static double de_CIE2000(double[] x, double[] y) {
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

	
	private static double[] RGB2Lab(Color RGBColor){

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

	
	private static double euclideanDistance(double[] lab , double []lab1){
		double L = lab[0] - lab1[0];
		double A = lab[1] - lab1[1];
		double B = lab[2] - lab1[2];

		return Math.sqrt((L * L) +  (A * A) +  (B * B));	
	}
}
