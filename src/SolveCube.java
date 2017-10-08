import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SolveCube {
	public ByteBuffer byt = ByteBuffer.allocate(68); //For the final table	
	List<String> firstPhaseSolutions = new ArrayList<String>();
	List<String> secondPhaseSolutions = new ArrayList<String>();
	List<String> thirdPhaseSolutions = new ArrayList<String>();
	List<String> allTestMoves = new ArrayList<String>();
	TableGenerator resetCube = new TableGenerator();
	String publicSolution ="";
	
	
	byte cube[][] = 
			   {{0,0,0,0,0,0,0,0,0},
				{1,1,1,1,1,1,1,1,1},
				{2,2,2,2,2,2,2,2,2},
				{3,3,3,3,3,3,3,3,3},
				{4,4,4,4,4,4,4,4,4},
				{5,5,5,5,5,5,5,5,5}
	};
	boolean fetched = false; 
	
	
	//Loads up the 4 small tables into memory under 12MB total.
	//finalPhase.txt is too large to pull into memory so I use NIO libraries to read it 
	//From computer storage.
	private void allocateTables() throws IOException{
		List<String> firstLookupTable = Files.readAllLines(Paths.get("firstPhase.txt"));
		for(String i : firstLookupTable)
			firstPhaseSolutions.add(i);
		
		List<String> secondLookupTable = Files.readAllLines(Paths.get("secondPhase.txt"));
		for(String i : secondLookupTable)
			secondPhaseSolutions.add(i);
		 
		List<String> thirdLookupTable = Files.readAllLines(Paths.get("thirdPhase.txt"));
		for(String i : thirdLookupTable)
			thirdPhaseSolutions.add(i);
		
		List<String> testMoves = Files.readAllLines(Paths.get("testMoves.txt"));
		for(String i : testMoves)
			allTestMoves.add(i);
	}
	
	
	public void inputCube(byte[][] cube){
      mapOrientation(cube);
	}
	
	
	//There are 24 possible orientations for the cube
	//I would have to have 24 different tables for all orientations
	//I only have one so what I am doing here is taking any or the 23 
	//other possible orientations and map them to the one my table has. {0,1,2,3,,4,5}
	private void mapOrientation(byte[][] firstCube){
		
        TableGenerator s = new TableGenerator();        

        s.cube = firstCube;
        System.out.println("your cube: \n");

        s.print_cube();
		
		byte[][] mappings= {{0,2,3,4,1,5},{1,2,0,4,5,3},{5,2,1,4,3,0},{3,2,5,4,0,1},
				   {3,0,2,5,4,1},{2,0,1,5,3,4},{1,0,4,5,2,3},{4,0,3,5,1,2},
				   {4,3,5,1,0,2},{0,3,4,1,2,5},{2,3,0,1,5,4},{5,3,2,1,4,0},
				   {5,4,3,2,1,0},{3,4,0,2,5,1},{0,4,1,2,3,5},{1,4,5,2,0,3},
				   {1,5,2,0,4,3},{4,5,1,0,3,2},{3,5,4,0,2,1},{2,5,3,0,1,4},
				   {2,1,5,3,0,4},{5,1,4,3,2,0},{4,1,0,3,5,2},{0,1,2,3,4,5}};

		byte[] foundMap = null;
		
		int upCenter = firstCube[0][4];
		int frontCenter = firstCube[2][4];

		for(int i = 0; i < mappings.length; i++){
        	for(int j = 0; j < mappings[i].length; j++){
        		if(mappings[i][upCenter] == 0  && mappings[i][frontCenter] == 2){
           			foundMap = mappings[i];
        		}
        	}
        }
		
		 byte[][] cube = new byte [6][9];
	        
        for(int i = 0; i < cube.length; i++){
        	for(int j = 0; j < cube[i].length; j++){
        		if(foundMap == null){
        			foundMap = mappings[4]; //<-- Not always accurate but benefit of doubt ;) 
        			System.out.println("Center mapping failed...");
        		//	System.exit(0);
        		}
        		cube[i][j] = foundMap[firstCube[i][j]];
        	}
        }
        
        
        s.cube = cube;
      
        try {
			stateSolver(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Cube input solve final.
	private void stateSolver(TableGenerator g) throws IOException {
		
		
		TableGenerator savedCube = new TableGenerator(); //This is to save the scramble to apply in iterations 
		savedCube.copyCube(g);
		SolveCube s = new SolveCube();
		s.allocateTables();
		final int MAX_SEARCH_TIME = 5000; // equivalent to 5 seconds
		String currTestMoves = "";
		long start = System.currentTimeMillis();
		long end = start + MAX_SEARCH_TIME;
		int shortestSoFar = 50; //Set at 50 because its the upper-bound solution length 
		// give the program X seconds to find shortest solution to the cube (5 is 
		
		System.out.println("Calculating...");
		
		for(int i = 0; i < s.allTestMoves.size() && System.currentTimeMillis() < end;i++){
			int length = s.solveInput(g,savedCube, s.allTestMoves.get(i), false);
			if(length < shortestSoFar){
				currTestMoves = s.allTestMoves.get(i);
				shortestSoFar = s.solveInput(g,savedCube, s.allTestMoves.get(i), false);
			}
		}
		s.solveInput(g, savedCube, currTestMoves ,true);	
	}
	
	//Reads solution turn by turn and looks if it can be shortened combineTurns returns shortened segment 
	private String readSingleTurn(String wholeSolution) {
		
		wholeSolution = wholeSolution.replaceAll(" {2,}"," ").trim(); //empty space at the beginning screws it up so this fixes it.
		String []unoptimized = wholeSolution.split("\\s+");
		
		//starts at 2 because you can't seek -2 in a string for possible improvements
		for(int i = 2; i < unoptimized.length;i++){
			String index = unoptimized[i];
			String lastIndex = unoptimized[i -1];
			String b4lastIndex = unoptimized[i - 2];
			
			// to look for cases like U2 D2 U to make Ui D2. Length of 2 instead of 3
			if(b4lastIndex.startsWith("R") && lastIndex.startsWith("L") && index.startsWith("R")||
					b4lastIndex.startsWith("U") && lastIndex.startsWith("D") && index.startsWith("U")||
					b4lastIndex.startsWith("L") && lastIndex.startsWith("R") && index.startsWith("L")||
					b4lastIndex.startsWith("D") && lastIndex.startsWith("U") && index.startsWith("D")||
					b4lastIndex.startsWith("B") && lastIndex.startsWith("F") && index.startsWith("B")||
					b4lastIndex.startsWith("F") && lastIndex.startsWith("B") && index.startsWith("F")){
				
					b4lastIndex = combineTurns(index, b4lastIndex);
					unoptimized[i - 2] = b4lastIndex;
					index = "";
					unoptimized[i] = index;
			}			
		}
		
		//Looks for just 2 "redundant" cases like U U can be U2. Reduced from 2 to 1.
		for(int i = 1; i < unoptimized.length; i++){
			 String index = unoptimized[i];
			 String lastIndex = unoptimized[i - 1];
				if(index.startsWith("R") && lastIndex.startsWith("R") || 
				   index.startsWith("U") && lastIndex.startsWith("U") || 
				   index.startsWith("L") && lastIndex.startsWith("L") || 
				   index.startsWith("D") && lastIndex.startsWith("D") || 
				   index.startsWith("B") && lastIndex.startsWith("B") || 
				   index.startsWith("F") && lastIndex.startsWith("F")) {
					   //System.out.println("Whole Solution: " + wholeSolution);
					   lastIndex = combineTurns(index, lastIndex);
					   unoptimized[i - 1] = lastIndex;
					   index = "";
					   unoptimized[i] = index;
				   
				}
	
			}
		
		String optimized = Arrays.toString(unoptimized).replaceAll("\\[|\\]", "").replaceAll(",", "").replaceAll(" {2,}"," ") + " ";
		return optimized;
	}
	
	private String combineTurns(String firstTurn, String secondTurn) {
		if(firstTurn.endsWith("i") && secondTurn.endsWith("i")) {
			/*System.out.println();
			System.out.println("CASE 1");
			System.out.println("FIRST: " + firstTurn);
			System.out.println("SECOND: " + secondTurn);*/
			firstTurn = firstTurn.replace("i", "2");
			//System.out.println(firstTurn);
			return firstTurn;
		}
		if(firstTurn.length() == 1 &&  secondTurn.length() == 1) {
			secondTurn = "";
			firstTurn = firstTurn + "2";
			return firstTurn;
		}
		if (firstTurn.endsWith("2") && secondTurn.endsWith("2")) {
			firstTurn = "";
			return firstTurn;
		}
		if(firstTurn.length() == 1 && secondTurn.endsWith("i") || 
		   firstTurn.endsWith("i") && secondTurn.length() == 1) {
			firstTurn = "";
			return firstTurn;
		}
		if(firstTurn.endsWith("i") && secondTurn.endsWith("2")) { //This one messed up but it work ;) 
			firstTurn = firstTurn.replace("i", "");
			//System.out.println(firstTurn);
			return firstTurn;
		}
		if(firstTurn.endsWith("2") && secondTurn.endsWith("i")) {
			/*System.out.println();
			System.out.println("CASE 6");
			System.out.println("FIRST: " + firstTurn);
			System.out.println("SECOND: " + secondTurn);*/
			firstTurn = firstTurn.replace("2", "");
			//System.out.println(firstTurn);
			return firstTurn;
		}
		
		if(firstTurn.endsWith("") && secondTurn.endsWith("2")) {
		/*	System.out.println();
			System.out.println("CASE 7");
			System.out.println("FIRST: " + firstTurn);
			System.out.println("SECOND: " + secondTurn);*/
			firstTurn = firstTurn + "i";
			//System.out.println(firstTurn);
			return firstTurn;
			
		}
		if(firstTurn.endsWith("2") && secondTurn.endsWith("")) {
		/*	System.out.println();
			System.out.println("CASE 8");
			System.out.println("FIRST: " + firstTurn);
			System.out.println("SECOND: " + secondTurn);*/
			firstTurn = firstTurn.replace("2", "i");
			//System.out.println(firstTurn);
			return firstTurn;
		}
		return "THIS SHOULDN'T HAPPEN";
	}
	
	private int solveInput(TableGenerator obj , TableGenerator savedCube,  String testMoves, boolean printAll) throws IOException{
		
		//applies saved cube (entered cube) to cube that gets solved
		obj.copyCube(savedCube);
		
		String wholeSolution = "";
		//Applies test moves length 1-3 that can get cube
		//into better position for solution 
		obj.apply_turns(testMoves);
		
		//generates the hash that represents the position of the pieces that make the 2x2x block
		int hash = obj.hash_2x2x2();
		//uses hash to get solution of first phase and applies it to the cube also keeps sequence
		wholeSolution += firstPhaseSolution(hash, obj);
		//looks for places where solution can be condensed
		wholeSolution = readSingleTurn(wholeSolution); 
		//applies shorter solution on the  cube if found
		obj.apply_turns(wholeSolution);
		
		//same process is repeated for phase 2 and 3 of solution
		int hash2 = obj.hash_1x2x2();
		wholeSolution += solutionTwoAndThree(hash2, 7, obj, secondPhaseSolutions , wholeSolution) + " ";
		wholeSolution = readSingleTurn(wholeSolution); 
		obj.copyCube(savedCube);
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);
	
		int hash3 = obj.hash_1x2x2b();			
		wholeSolution += solutionTwoAndThree(hash3, 8,  obj, thirdPhaseSolutions, wholeSolution) + " ";

		wholeSolution = readSingleTurn(wholeSolution); 
		obj.copyCube(savedCube);
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);
		
		//looking for last solution from the 500MB lookup table
		int hash4 = obj.hash_SlotandLastLayer();
		wholeSolution += lastSolution(hash4, obj);
		wholeSolution = readSingleTurn(wholeSolution); 
		obj.copyCube(savedCube);
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);

		if(printAll == true)
			obj.print_cube();
	
		//takes whole solution, shortens it and resolves the cube. :) 
		wholeSolution = testMoves + wholeSolution;
		obj.copyCube(savedCube);
		wholeSolution = readSingleTurn(wholeSolution);
		obj.apply_turns(wholeSolution);
		
		//repeated to remove the rare case that duplicate occurs.
		//Might need other fix if problem persists. (looping)
		
		/*obj.copyCube(savedCube);
		wholeSolution = readSingleTurn(wholeSolution);
		obj.apply_turns(wholeSolution);
		*/
		publicSolution = wholeSolution.replaceAll("i", "'");
		if(printAll == true){
			System.out.println("Your solution :) \n" +  publicSolution.replaceAll("i", "'"));
			//publicSolution = wholeSolution.replaceAll("i", "'");
		}
		String trim = wholeSolution.trim();
		
		
		int solutionLength = trim.split("\\s+").length;
		if(printAll == true)
			System.out.println("Number of moves: " + solutionLength);
		
		return solutionLength;
	}
	
	
	public String obtainSolution(){
		return publicSolution;
	}
	
	//formats solution string from table.
	private String getPhase1Solution(String str){ 
		String reverseLine = new StringBuffer(str.substring(7)).reverse().toString();
  		String cleanerLine = reverseLine.replaceAll("PN ,","")
                .replaceAll(",", "")
                .replaceAll(" {2,}"," ").replaceAll("PN ", "");
		return cleanerLine;
	}
	
	//Specifically for firstPhase.txt as table was generated different from the rest
	private String firstPhaseSolution(int hash, TableGenerator obj){
		try{
			String turns = firstPhaseSolutions.get(hash);
			String phaseOneSolution = getPhase1Solution(turns);
			return phaseOneSolution;
		} catch(IndexOutOfBoundsException e){
			//If hash is out-of-range 0-253439 color sorting failed due to lighting
			System.out.println("Lighting problem affected solution. Please retry.");
			System.exit(0);
		}
		return "FAILED";
	}

	//for getting the second phase and third phase solutions
	private String solutionTwoAndThree(int hashVal, int hashLength, TableGenerator obj, List<String>  phaseSolutions, String wholeSolution){
		
		String turns;
		String sucessfulString = "";
		try{
			 turns = phaseSolutions.get(hashVal);	
			 sucessfulString = turns;
		} catch(IndexOutOfBoundsException e){
			//same deal if cube is invalid to lighting and image capture
			//getting solution can fail and not solve it correctly.
			obj.print_cube();
			System.out.println(wholeSolution);
			System.out.println("out of range hash: "+ hashVal);
			System.out.println("Cube was not valid sorry retry. Due to lighting please retry.");
			System.exit(0);
		}
			
		//return solution to apply to cube 
		String turnsToApply = getPhaseSolutions(sucessfulString, hashLength);
		return turnsToApply;
	}
	
	//gets last solution. Handled differently because its a much larger file
	private String lastSolution(int hashVal, TableGenerator obj) throws IOException{
		String lastTurns = getLastSolution(hashVal);
		return lastTurns;
	}
	
	
	//This was hard to write I and isn't so clear either sorry.
	//Basically I had to remove the hash number that came with the string
	//Without destroying the solution and I had to reverse and flip the string
	//So that it could solve the cube correctly.
	//This is used for phase 2 3 and 4 of solution so it caters for a lot.
	private String getPhaseSolutions(String str,int hashLength){
		
		if(str.length() == 0)
			return "";
		
		String rawSolution = str.replaceAll(" {2,}", " ").replaceAll("NP ", "").substring(hashLength);

		//System.out.println("RAW SOLUTION: " + rawSolution);
		if (rawSolution.length() == 0) 
			return "";
		
		
		String[] turnArray = rawSolution.split("\\s+");
		
		//this is to track which indexes don't end with 2 or i for future replacement
		int[] turnIndexes = new int[turnArray.length];
		for(int i = 0; i < turnIndexes.length; i++){
			turnIndexes[i] = -1;
		}
		
		
		for(int i = 0; i < turnArray.length; i++){
			if(turnArray[i].charAt(turnArray[i].length() - 1) != 'i' && turnArray[i].charAt(turnArray[i].length() - 1) != '2'){
				turnIndexes[i] = i; 
				//This condition is to get indexes that are 1 in length 
			}
			if(turnArray[i].charAt(turnArray[i].length() - 1) == 'i'){
				turnArray[i] = turnArray[i].replaceFirst(".$", "");
			}				
		}
		

		for(int j = 0; j < turnIndexes.length ;j++){
			if(turnIndexes[j] != -1){
				turnArray[j] = turnArray[j] + "i";
			}	
		}
		
		if(turnArray.length > 1){
			for(int i = 0; i < turnArray.length / 2; i++){
				String temp = turnArray[i];
				turnArray[i] = turnArray[turnArray.length - i - 1];
				turnArray[turnArray.length - i - 1] = temp;
			}
		} 
		
		String solution = Arrays.toString(turnArray).replaceAll("\\[|\\]", "").replaceAll(",", "");
		return solution;
	}
	
	//This is for the large table where I use the generated hash
	private int indexToLine(int line, FileChannel file){
		byt.clear();
		try {
			//each line is 61 bytes read by each line..
			file.read(byt, line*61);
		} catch (IOException e){
			e.printStackTrace();
		}
		return line;
	}
	
	
	private char byteToChar(byte b){
		if(b == 0x100)  
			throw new NumberFormatException(String.format("Bad Byte Input: %02X", b));
		return (char) b;
	}
	
	private String getLastSolution(int hashVal){
		FileInputStream stream = null;
		String solution = null;
		try {
			stream = new FileInputStream("finalPhase.txt");
			FileChannel file = stream.getChannel();
			
			indexToLine(hashVal, file);
			try {
				//reads the solution without the hash thus +8
				file.read(byt, hashVal * 61 + 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			String turns = "";
			for(int i = 0; i < 61; i++){
				turns += (byteToChar(byt.get(i)));
			}
			 //once solution has been obtained I make it ready for cube application :) 
			 solution = getPhaseSolutions(turns, 9);
		}	
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return solution;
	}
}