import java.util.Arrays;

public class TestEnvironment {


	//{58.974604845047, 15.037506818771362, -64.0113115310669}
	//{58.701420307159424, 14.014512300491333, -64.46481943130493}

	public static void main(String[] args) {

		double[][] labArray = 
			{{97.790109872818, 0.1550614833831787, 7.055211067199707}, {99.09732460975647, -1.8726885318756104, 7.396590709686279}, {98.89606094360352, -1.0398626327514648, 6.08057975769043},
			{99.63694548606873, -1.634061336517334, -0.5843281745910645}, {98.53279209136963, -1.749664545059204, -2.243173122406006}, {99.34767866134644, -3.148287534713745, 0.5061507225036621},
			{93.38897705078125, -3.7266016006469727, -10.047686100006104}, {94.74215698242188, -4.207342863082886, -8.00325870513916}, {95.45221138000488, -4.281878471374512, -6.930053234100342}, 
			
			{58.788891077041626, 67.40722060203552, 40.376585721969604}, {58.22276830673218, 68.51941347122192, 43.04102063179016}, {58.34369659423828, 68.18437576293945, 43.17700266838074},
			{57.85778474807739, 69.4614052772522, 43.179839849472046}, {57.46259331703186, 70.22103667259216, 45.467495918273926}, {57.32113695144653, 70.42467594146729, 46.925777196884155},
			{57.766974449157715, 69.85905766487122, 41.97472929954529}, {57.48453879356384, 70.293128490448, 44.406479597091675}, {57.23371458053589, 70.79562544822693, 45.760321617126465},
			
			{89.55846810340881, -67.99906492233276, 48.13920259475708}, {89.21723461151123, -71.04882597923279, 51.36675834655762}, {89.42860674858093, -69.3979561328888, 52.13055610656738},
			{88.81511521339417, -74.49987530708313, 52.61523723602295}, {88.73614883422852, -75.39200782775879, 55.1963210105896}, {88.75708484649658, -75.36473870277405, 56.985509395599365},
			{88.72453999519348, -75.13129711151123, 51.11459493637085}, {88.52632546424866, -77.3114562034607, 56.658339500427246}, {88.47119212150574, -78.02900671958923, 59.58452224731445},
			
			{56.34441113471985, 73.36065173149109, 44.25313472747803}, {55.5542426109314, 74.99518990516663, 48.81749749183655}, {56.233943939208984, 73.01554083824158, 50.01518130302429},
			{56.495713233947754, 73.27848672866821, 41.64718985557556}, {56.29888844490051, 73.76313209533691, 41.984689235687256}, {56.322569370269775, 73.29252362251282, 45.3238308429718},
			{57.25648283958435, 71.51240110397339, 39.712172746658325}, {57.37566876411438, 71.25663757324219, 39.28746581077576}, {57.98414754867554, 69.88134980201721, 37.745219469070435}, 
			
			{63.360291481018066, 5.49355149269104, -56.996846199035645}, {61.74910354614258, 7.504850625991821, -59.580159187316895}, {62.37506127357483, 6.616920232772827, -58.57654809951782},
			{60.78528642654419, 8.749514818191528, -61.128997802734375}, {60.13189339637756, 9.546518325805664, -62.181055545806885}, {60.7442786693573, 8.583664894104004, -61.19694709777832},
			{57.03644037246704, 15.941500663757324, -67.16029644012451}, {57.03644037246704, 15.941500663757324, -67.16029644012451}, {58.356688261032104, 14.350950717926025, -65.02279043197632}, 
			
			{97.36097002029419, -19.551485776901245, 75.39525032043457}, {97.40421795845032, -19.416987895965576, 77.30993032455444}, {97.30259394645691, -20.018726587295532, 78.60885858535767},
			{97.11946606636047, -20.860105752944946, 75.8231520652771}, {97.01783514022827, -21.47197723388672, 77.15907096862793}, {96.93012928962708, -21.974295377731323, 77.76134014129639},
			{96.91576862335205, -21.675467491149902, 70.46815156936646}, {96.79538655281067, -22.447675466537476, 72.70334959030151}, {96.84762978553772, -22.229015827178955, 73.94065856933594}};

		k_means(labArray);
	}



	private static void k_means(double[][] laBArray){// ﴾͡๏̯͡๏﴿ O'RLY?

		double[] Ucenter = laBArray[4];
		System.out.println(Arrays.toString(Ucenter));
		double[] Lcenter = laBArray[13];
		double[] Fcenter = laBArray[22];
		double[] Rcenter = laBArray[31];
		double[] Bcenter = laBArray[40];
		double[] Dcenter = laBArray[49];

		double[] Udistances = new double[8]; //8 lowest distances go in here 
		double[] Ldistances = new double[8];
		double[] Fdistances = new double[8];
		double[] Rdistances = new double[8];
		double[] Bdistances = new double[8];
		double[] Ddistances = new double[8];

		Arrays.fill(Udistances, Integer.MAX_VALUE);
		Arrays.fill(Ldistances, Integer.MAX_VALUE);
		Arrays.fill(Fdistances, Integer.MAX_VALUE);
		Arrays.fill(Rdistances, Integer.MAX_VALUE);
		Arrays.fill(Bdistances, Integer.MAX_VALUE);
		Arrays.fill(Ddistances, Integer.MAX_VALUE);

		double[][] colorsToStore = new double[8][];
		double distance = 0;
		for(int i = 0; i < laBArray.length; i++){
			if(i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49)
				continue; //to avoid comparing centers
					distance = euclideanDistance(Ucenter, laBArray[i]);
					//System.out.println(distance);
					//System.out.println(i);
					/*if(Udistances[j] < Udistances[7]){
						Udistances[7] = Udistances[j];
						colorsToStore[j] = laBArray[i];
						Arrays.sort(Udistances);
						System.out.println("sorted: "+Arrays.toString(Udistances));
					}*/
			}
		//System.out.println("U:" + Arrays.deepToString(colorsToStore));
		
		/*
		U:[[99.63694548606873, -1.634061336517334, -0.5843281745910645],
		  
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645],
		  
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645], 
		  
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645],
		 
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645],
		  
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645], 
		  
		  [99.63694548606873, -1.634061336517334, -0.5843281745910645], null]

		*/
		
		//System.out.println("U: " + Arrays.toString(Udistances));
		//System.out.println("L: " + Arrays.toString(Ldistances));
		//pass color store answer in double and change values

	}

	/*Ldistances[j] = euclideanDistance(Lcenter, laBArray[i]);
	Fdistances[j] = euclideanDistance(Fcenter, laBArray[i]);
	Rdistances[j] = euclideanDistance(Rcenter, laBArray[i]);
	Bdistances[j] = euclideanDistance(Bcenter, laBArray[i]);
	Ddistances[j] = euclideanDistance(Dcenter, laBArray[i]);

*/

	/*if(Ldistances[j] < Ldistances[7]){
	Ldistances[7] = Ldistances[j];
	Arrays.sort(Ldistances);
}
if(Fdistances[j] < Fdistances[7]){
	Fdistances[7] = Fdistances[j];
	Arrays.sort(Fdistances);
}
if(Rdistances[j] < Rdistances[7]){
	Rdistances[7] = Rdistances[j];
	Arrays.sort(Rdistances);
}
if(Bdistances[j] < Bdistances[7]){
	Bdistances[7] = Bdistances[j];
	Arrays.sort(Bdistances);
}
if(Ddistances[j] < Ddistances[7]){
	Ddistances[7] = Ddistances[j];
	Arrays.sort(Ddistances);
}*/

	
	private static double euclideanDistance(double[] lab , double []lab1){
		double L = lab[0] - lab1[0];
		double A = lab[1] - lab1[1];
		double B = lab[2] - lab1[2];

		return Math.sqrt((L * L) +  (A * A) +  (B * B));	
	}
}
