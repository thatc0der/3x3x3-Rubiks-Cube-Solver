import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestEnvironment {
	
	static double[][] labArray = 
		{{67.37970852851868, -1.5761256217956543, -1.1435151100158691}, {66.60223436355591, -1.775294542312622, -0.6283044815063477}, {66.25926542282104, -16.419321298599243, 31.725049018859863},
		{66.71317172050476, -0.9859204292297363, -2.6965856552124023}, {66.559885263443, -1.6837716102600098, -2.9396772384643555}, {66.46986317634583, -17.33112335205078, 31.98106288909912},
		{68.28126382827759, -2.366960048675537, -3.1632184982299805}, {67.90755462646484, -2.3691654205322266, -3.166484832763672}, {67.32474112510681, -18.86516809463501, 33.07682275772095},
		
		{57.1456561088562, 29.626578092575073, 20.513999462127686}, {56.921976804733276, 30.16766905784607, 20.21235227584839}, {58.41226410865784, 29.981493949890137, 20.022642612457275},
		{58.14974117279053, 30.31855821609497, 20.811176300048828}, {58.52139687538147, 30.27176856994629, 20.76319456100464}, {59.2638578414917, 30.17902374267578, 20.668745040893555},
		{28.165188550949097, 31.463727355003357, 18.946152925491333}, {30.4399436712265, 30.079126358032227, 16.20226502418518}, {31.695589303970337, 29.066503047943115, 15.347212553024292},
		
		{55.20941376686096, -35.45331954956055, 25.107139348983765}, {54.90727972984314, -34.828901290893555, 24.164175987243652}, {42.51266264915466, -0.12156367301940918, -30.84850311279297},
		{55.91131639480591, -38.65727782249451, 26.517480611801147}, {56.077587604522705, -37.31134533882141, 25.12984275817871}, {42.651533126831055, 1.3931095600128174, -34.80794429779053},
		{41.736101150512695, 4.020869731903076, -39.83616828918457}, {42.86864399909973, 3.2646656036376953, -38.618290424346924}, {56.56504821777344, -39.27388787269592, 25.73826313018799}, 
		
		{32.44839382171631, 27.55551040172577, 18.272560834884644}, {32.44839382171631, 27.55551040172577, 18.272560834884644}, {33.228702783584595, 27.35307812690735, 18.713784217834473},
		{31.710551500320435, 28.80816161632538, 20.47944664955139}, {32.539663910865784, 28.84708344936371, 19.085532426834106}, {32.93643867969513, 28.78907322883606, 18.99811625480652},
		{60.719000577926636, 32.1306586265564, 23.369157314300537}, {61.968350887298584, 32.471030950546265, 22.874343395233154}, {62.1162166595459, 32.954007387161255, 22.531509399414062}, 
		
		{55.12525486946106, -36.039113998413086, 24.98113512992859}, {40.587973833084106, 0.9076595306396484, -32.70132541656494}, {41.426689982414246, 0.9435266256332397, -32.56804347038269},
		{54.23116683959961, -37.09086775779724, 25.412291288375854}, {40.120782017707825, 3.7093013525009155, -37.643200159072876}, {41.91556799411774, 1.924261450767517, -35.38152575492859}, 
		{55.83640170097351, -39.19646143913269, 26.405727863311768}, {55.783660650253296, -39.63226079940796, 26.86995267868042}, {42.10581398010254, 3.685861825942993, -39.24446105957031},
		
		{70.33210110664368, 0.8348524570465088, -7.258331775665283}, {69.12196779251099, -14.498025178909302, 26.012516021728516}, {69.21389126777649, -13.970434665679932, 25.61239004135132},
		{70.98227214813232, 0.5737841129302979, -8.4969162940979}, {69.31087589263916, -15.541136264801025, 26.78593397140503}, {69.23802161216736, -15.926659107208252, 26.675963401794434},
		{72.07740950584412, 0.6550252437591553, -9.59845781326294}, {70.48867177963257, -17.34483242034912, 28.890085220336914}, {69.42983365058899, -16.964733600616455, 27.45109796524048}};

	
	
	public static void main(String[] args) {
		
		
		
		//k_means(labArray);
		//rgb(210, 208, 2)
		findCenters(labArray);
	
		
	} 
	
	private static void findCenters(double [][] LabArray){ //this method finds closest color to provided center
		
		final int SIZE = 6;
        ColorAndIndex[] colors = new ColorAndIndex[SIZE]; //b14:
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new ColorAndIndex();
        }
		
        double[][] centers = {LabArray[4],LabArray[13],LabArray[22],LabArray[31],LabArray[40],LabArray[49]}; 
		
        double [][] crayolaColors = 
        {{100, 0.00526049995830391, -0.010408184525267927},{35.71689493804023, 38.18518746791636, 43.982516784310114},{39.14982168015123,-32.450520997738295,10.605199206744654},
		{20.18063311070288, 40.48184409611946 , 29.94034624098952},{23.921448197848527, 5.28400492805528, -30.63998357385018},{81.19132678332146, -17.614271251146395, 81.03415848709281}};

		double[][] distances = new double[SIZE][2];
        for (int i = 0; i < distances.length; i++) {
            distances[i][0] = 0;
            distances[i][1] = Integer.MAX_VALUE; //b14: Maybe even just have a seperate array for the max value, so you have 2 arrays instead.
        }
        
        
        for(int i = 0; i < crayolaColors.length; i++){
            //calculates distance between two provided color
           for (int j = 0; j < distances.length; j++) { //b14 simplified all the IFs
               distances[j][0] = de_CIE2000(centers[j], crayolaColors[i]);
               if(distances[j][0] < distances[j][1]){
                   distances[j][1] = distances[j][0];
                   colors[j].distance = distances[j][0]; //update Object distance
                   colors[j].index = i; //update Object index
                   colors[j].labArray = crayolaColors[i];
               }
           	}
         }
        
        k_means(LabArray, colors);
	}
	
	@SuppressWarnings("unchecked") //<-Stop annoying errors
	private static void k_means(double[][] labArray , ColorAndIndex[] colors){// ﴾͡๏̯͡๏﴿ O'RLY?

		final int SIZE = 6;
        double[][] centers = {colors[0].labArray,colors[1].labArray,colors[2].labArray,colors[3].labArray,colors[4].labArray,colors[5].labArray}; 
		
        ColorAndIndex[] clusters = new ColorAndIndex[SIZE];
        for(int i = 0; i < clusters.length; i++){
        	clusters[i] = new ColorAndIndex();
        }
         
	
		
		
	
		double distance = 0;
		List<ColorAndIndex> orangeCluster = new ArrayList<>();
		ColorAndIndex currColor = new ColorAndIndex();
		double highestDistance = Integer.MAX_VALUE;
		final int STICKERSIDEAMOUNT = 8;
		
		List<double[]> allColors =new ArrayList<>(Arrays.asList(labArray));
		for(int currCenter = 0; currCenter < SIZE; currCenter++){
			for(int sticker = 0; sticker < STICKERSIDEAMOUNT; sticker++){
				for(int i = 0; i < allColors.size(); i++){
					if(i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49)
						continue;
	
					distance = euclideanDistance(centers[currCenter], allColors.get(i));
					if(distance < highestDistance){
						highestDistance = distance;
						currColor.distance = distance;
						currColor.labArray = allColors.get(i);
						currColor.index = i;
					}
				}
	
				System.out.println(currColor.toString());
				orangeCluster.add(currColor);
				allColors.get(currColor.index = (Integer) null);
				
				
				highestDistance = Integer.MAX_VALUE;
				currColor = null;
				currColor = new ColorAndIndex();
			}
		}
	
		
		System.exit(0);
			
	//	System.out.println(Arrays.toString(colors[0].labArray));
	
		
		/*
		for(int x = 0; x < SIZE; x++){
			index[x] = new ArrayList<Integer>();
			for(ColorAndIndex c : colorsToSelect[x]){
				index[x].add(c.getIndex());
			}
			
		}
		
		System.out.println("orange cluster: " + index[1].toString());
		System.out.println("red cluster: " + index[3].toString());*/
	}	
	

	//Low standard conversation
	//nothing great ever happened with low standards
	//no team ever won a championship with low standards
	
	
	/*
	 [0.6843132521104128, 
	  1.0636890842521098,
	  1.4792103105162868,
	  2.4426597006304482,
	  3.0596028909523287,
	  3.1891519207804384,
	  3.2793789559283097,
	  3.5246411630873773]
	*/
	
	
	public static void findColor(){
		
	}
	
	
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


	
	private static double euclideanDistance(double[] lab , double []lab1){
		double L = lab[0] - lab1[0];
		double A = lab[1] - lab1[1];
		double B = lab[2] - lab1[2];

		return Math.sqrt((L * L) +  (A * A) +  (B * B));	
	}
}
