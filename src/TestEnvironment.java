import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestEnvironment {



	
	public static void main(String[] args) {

		
		
		double[][] labArray = 
		
		{{98.39854025840759, 0.8915960788726807, -2.3975372314453125}, {98.15157413482666, 1.398712396621704, -2.759838104248047}, {96.15249609947205, -25.11465549468994, 58.126842975616455},
		{96.72126007080078, 1.4764666557312012, -4.913783073425293}, {96.15246152877808, 2.1722614765167236, -5.760645866394043}, {95.85500836372375, -26.81601047515869, 58.61250162124634},
		{94.78895878791809, -0.6625950336456299, -7.87283182144165}, {94.6804621219635, 0.48664212226867676, -8.017778396606445}, {95.70163893699646, -27.14371681213379, 50.94786882400513},
		
		{64.30682325363159, 57.50557780265808, 20.779776573181152}, {64.91822671890259, 56.57455325126648, 18.766343593597412}, {69.44426345825195, 48.35280776023865, 12.072443962097168},
		{63.708964586257935, 58.436423540115356, 22.821855545043945}, {63.93290662765503, 58.230698108673096, 21.412980556488037}, {65.81089043617249, 54.37511205673218, 19.421732425689697}, 
		{55.26016354560852, 76.71642303466797, 40.81277847290039}, {55.70309019088745, 75.90210437774658, 37.89631724357605}, {56.779531717300415, 73.60312342643738, 33.99665355682373}, 
		
		{88.96834635734558, -72.6819634437561, 46.805477142333984}, {89.3834297657013, -68.77774000167847, 41.65844917297363}, {54.06823515892029, 24.210989475250244, -71.94548845291138},
		{88.78300595283508, -74.29823279380798, 47.478485107421875}, {88.94774222373962, -72.61836528778076, 44.39427852630615}, {50.96644186973572, 30.341744422912598, -76.99650526046753},
		{89.4567334651947, -66.89858436584473, 29.85551357269287}, {50.2381272315979, 30.97975254058838, -78.19303274154663}, {51.09834289550781, 29.34810519218445, -76.78706645965576}, 
		
		{55.79465413093567, 75.7203996181488, 37.42919564247131}, {55.43932271003723, 76.42418146133423, 39.308565855026245}, {55.401633739471436, 76.31230354309082, 40.97289443016052}, 
		{57.8800413608551, 72.88020849227905, 21.9504714012146}, {57.10540199279785, 74.05203580856323, 26.226842403411865}, {56.81194519996643, 74.26393032073975, 29.373663663864136},
		{64.53023982048035, 56.13988637924194, 24.490654468536377}, {63.37857937812805, 58.21490287780762, 27.50089168548584}, {63.89460229873657, 56.96555972099304, 27.611374855041504}, 
		
		{88.92140626907349, -72.88116216659546, 44.83422040939331}, {49.62267208099365, 31.947404146194458, -79.20184135437012}, {49.0891010761261, 33.14089775085449, -80.07450103759766},
		{89.28094840049744, -68.79854202270508, 34.109532833099365}, {51.46850514411926, 27.644962072372437, -76.19080543518066}, {52.02009439468384, 26.472628116607666, -75.29208660125732},
		{88.91234874725342, -72.56370782852173, 40.95982313156128}, {88.87409973144531, -72.97751307487488, 41.87495708465576}, {47.77854681015015, 36.14911437034607, -82.22084045410156},
		
		{98.57276272773743, 0.058263540267944336, -2.1506786346435547}, {96.79230284690857, -20.861536264419556, 47.751688957214355}, {96.62489771842957, -21.824359893798828, 48.47069978713989},
		{96.18172907829285, 0.6909370422363281, -5.742287635803223}, {96.79230284690857, -20.861536264419556, 47.751688957214355}, {96.54176902770996, -22.305041551589966, 48.83002042770386},
		{95.5734646320343, -0.4068613052368164, -6.680810451507568}, {96.69178509712219, -21.282225847244263, 46.14913463592529}, {96.53850555419922, -22.137105464935303, 46.408724784851074}};
				
		//k_means(labArray);
		//rgb(210, 208, 2)
		findCenters(labArray);
	
		
	}

	
	//Lab -> white   (100, 0.00526049995830391, -0.010408184525267927)
	//Lab -> orange  (55.913088044526475,72.56018013827448,66.29200880893285)
	//Lab -> green   (88.04705355618475, -82.46268930918521, 68.73366304048506)
	//Lab -> red     (53.33175496564277,79.84941812993662,66.74846289191817)
	//Lab -> blue    (38.405155022889595, 62.114933081015565, -97.67872290151811)
	//Lab -> yellow  (96.62167769425967, -20.59135185521843, 93.96572954786468)
	
	private static void findCenters(double [][] LabArray){ //this method finds closest color to provided center
		
		ColorAndIndex Ucolor = new ColorAndIndex();
		ColorAndIndex Lcolor = new ColorAndIndex();
		ColorAndIndex Fcolor = new ColorAndIndex();
		ColorAndIndex Rcolor = new ColorAndIndex();
		ColorAndIndex Bcolor = new ColorAndIndex();
		ColorAndIndex Dcolor = new ColorAndIndex();
		
		double[] Ucenter = LabArray[4]; //these 'center' variables are anchor colors.
		double[] Lcenter = LabArray[13];
		double[] Fcenter = LabArray[22];
		double[] Rcenter = LabArray[31];
		double[] Bcenter = LabArray[40];
		double[] Dcenter = LabArray[49];
		
		double [][] crayolaColors = 
		{{100, 0.00526049995830391, -0.010408184525267927},{55.913088044526475,72.56018013827448,66.29200880893285},{88.04705355618475, -82.46268930918521, 68.73366304048506},
		{53.33175496564277,79.84941812993662,66.74846289191817},{38.405155022889595, 62.114933081015565, -97.67872290151811},{96.62167769425967, -20.59135185521843, 93.96572954786468}};
		
		double Udistance = 0;
		double leastDistanceU = Integer.MAX_VALUE;
		
		double Ldistance = 0;
		double leastDistanceL = Integer.MAX_VALUE;
		
		double Fdistance = 0;
		double leastDistanceF = Integer.MAX_VALUE;
		
		double Rdistance = 0;
		double leastDistanceR = Integer.MAX_VALUE;
		
		double Bdistance = 0;
		double leastDistanceB = Integer.MAX_VALUE;
		
		double Ddistance = 0;
		double leastDistanceD = Integer.MAX_VALUE;
		
		for(int i = 0; i < crayolaColors.length; i++){
			Udistance = de_CIE2000(Ucenter, crayolaColors[i]); //calculates distance between two provided colors
			Ldistance = de_CIE2000(Lcenter, crayolaColors[i]);
			Fdistance = de_CIE2000(Fcenter, crayolaColors[i]);
			Rdistance = de_CIE2000(Rcenter, crayolaColors[i]);
			Bdistance = de_CIE2000(Bcenter, crayolaColors[i]);
			Ddistance = de_CIE2000(Dcenter, crayolaColors[i]);
			
			if(Udistance < leastDistanceU){
				leastDistanceU = Udistance;
				Ucolor.distance = Udistance; //update Object distance
				Ucolor.index = i; //update Object index
			}
			
			if(Ldistance < leastDistanceL){
				leastDistanceL = Ldistance;
				Lcolor.distance = Ldistance;
				Lcolor.index = i;
			}
			
			if(Fdistance < leastDistanceF){
				leastDistanceF = Fdistance;
				Fcolor.distance = Udistance;
				Fcolor.index = i;
			}
			
			if(Rdistance <  leastDistanceR){
				leastDistanceR = Rdistance;
				Rcolor.distance = Rdistance;
				Rcolor.index = i;
			}
			
			if(Bdistance < leastDistanceB){
				leastDistanceB = Bdistance;
				Bcolor.distance = Bdistance;
				Bcolor.index = i;
			}
			
			if(Ddistance < leastDistanceD){
				leastDistanceD = Ddistance;
				Dcolor.distance = Ddistance;
				Dcolor.index = i;
			}
			
		}
		
		
		System.out.println(Ucolor.index + ", " + Lcolor.index + ", " + Fcolor.index+ " \n" + Rcolor.index + ", " + Bcolor.index + ", " + Dcolor.index);
		
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
	
	
	
	

	private static void k_means(double[][] laBArray){// ﴾͡๏̯͡๏﴿ O'RLY?

		double[] Ucenter = laBArray[4];
		double[] Lcenter = laBArray[13];
		double[] Fcenter = laBArray[22];
		double[] Rcenter = laBArray[31];
		double[] Bcenter = laBArray[40];
		double[] Dcenter = laBArray[49];
	//	System.out.println("Ucenter: " + Arrays.toString(Ucenter));
	//	System.out.println("Lcenter: " + Arrays.toString(Lcenter));
	//	System.out.println("Fcenter: " + Arrays.toString(Fcenter));
	//	System.out.println("Rcenter: " + Arrays.toString(Rcenter));
	//	System.out.println("Bcenter: " + Arrays.toString(Bcenter));
		System.out.println("Dcenter: " + Arrays.toString(Dcenter));
	
		ColorAndIndex Ucluster = new ColorAndIndex();
		ColorAndIndex Lcluster = new ColorAndIndex();
		ColorAndIndex Fcluster = new ColorAndIndex();
		ColorAndIndex Rcluster = new ColorAndIndex();
		ColorAndIndex Bcluster = new ColorAndIndex();
		ColorAndIndex Dcluster = new ColorAndIndex();
		
		
		List<ColorAndIndex> all_U_Distances = new ArrayList<>(); //I know breaking convention but its so hard to read allUDistances
		List<ColorAndIndex> all_L_Distances = new ArrayList<>();
		List<ColorAndIndex> all_F_Distances = new ArrayList<>();
		List<ColorAndIndex> all_R_Distances = new ArrayList<>();
		List<ColorAndIndex> all_B_Distances = new ArrayList<>();
		List<ColorAndIndex> all_D_Distances = new ArrayList<>();
		Object[] UColorsToSelect = null;
		Object[] LColorsToSelect = null;
		Object[] FColorsToSelect = null;
		Object[] RColorsToSelect = null;
		Object[] BColorsToSelect = null;
		Object[] DColorsToSelect = null;
		
		for(int i = 0; i < laBArray.length; i++){
			if(i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49){
				//to avoid comparing centers
				continue;
			}

			Ucluster.distance = euclideanDistance(Ucenter, laBArray[i]);
			Ucluster.labArray = laBArray[i];
			Ucluster.index = i;
			all_U_Distances.add(Ucluster);
			Ucluster = null;
			Ucluster = new ColorAndIndex();
			
			Lcluster.distance = euclideanDistance(Lcenter, laBArray[i]); //get 8 lowest distances and currLab color
			Lcluster.labArray = laBArray[i];
			Lcluster.index = i;
			all_L_Distances.add(Lcluster);
			Lcluster = null;
			Lcluster = new ColorAndIndex();
			
			Fcluster.distance = euclideanDistance(Fcenter, laBArray[i]);
			Fcluster.labArray = laBArray[i];
			Fcluster.index = i;
			all_F_Distances.add(Fcluster);
			Fcluster = null;
			Fcluster = new ColorAndIndex();
			
			Rcluster.distance = euclideanDistance(Rcenter, laBArray[i]);
			Rcluster.labArray = laBArray[i];
			Rcluster.index = i;
			all_R_Distances.add(Rcluster);
			Rcluster = null;
			Rcluster = new ColorAndIndex();
			
			Bcluster.distance = euclideanDistance(Bcenter, laBArray[i]);
			Bcluster.labArray = laBArray[i];
			Bcluster.index = i;
			all_B_Distances.add(Bcluster);
			Bcluster = null;
			Bcluster = new ColorAndIndex();
				
			Dcluster.distance = euclideanDistance(Dcenter, laBArray[i]);
			Dcluster.labArray = laBArray[i];
			Dcluster.index = i;
			all_D_Distances.add(Dcluster);
			Dcluster = null;
			Dcluster = new ColorAndIndex();
			
		}
		
		Collections.sort(all_U_Distances);
		UColorsToSelect = all_U_Distances.toArray(new Object[0]);
		UColorsToSelect = Arrays.copyOf(UColorsToSelect, 8);
		
		Collections.sort(all_L_Distances);
		LColorsToSelect = all_L_Distances.toArray(new Object[0]);
		LColorsToSelect = Arrays.copyOf(LColorsToSelect, 8);
		
		Collections.sort(all_F_Distances);
		FColorsToSelect = all_F_Distances.toArray(new Object[0]);
		FColorsToSelect = Arrays.copyOf(FColorsToSelect, 8);
		
		Collections.sort(all_R_Distances);
		RColorsToSelect = all_R_Distances.toArray(new Object[0]);
		RColorsToSelect = Arrays.copyOf(RColorsToSelect, 8);
		
		Collections.sort(all_B_Distances);
		BColorsToSelect = all_B_Distances.toArray(new Object[0]);
		BColorsToSelect = Arrays.copyOf(BColorsToSelect, 8);
		
		Collections.sort(all_D_Distances);
		DColorsToSelect = all_D_Distances.toArray(new Object[0]);
		DColorsToSelect = Arrays.copyOf(DColorsToSelect, 8);
	
		
	//	System.out.println(Arrays.toString(UColorsToSelect));
	//	System.out.println(Arrays.toString(LColorsToSelect));
	//	System.out.println(Arrays.toString(FColorsToSelect));
	//	System.out.println(Arrays.toString(RColorsToSelect));
	//	System.out.println(Arrays.toString(BColorsToSelect));
		System.out.println(Arrays.toString(DColorsToSelect));
		
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
	
	
	private static double euclideanDistance(double[] lab , double []lab1){
		double L = lab[0] - lab1[0];
		double A = lab[1] - lab1[1];
		double B = lab[2] - lab1[2];

		return Math.sqrt((L * L) +  (A * A) +  (B * B));	
	}
}
