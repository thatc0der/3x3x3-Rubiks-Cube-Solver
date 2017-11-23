import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SolveCube {

	List<String> firstStageSolutions = new ArrayList<String>();
	List<String> secondStageSolutions = new ArrayList<String>();
	List<String> thirdStageSolutions = new ArrayList<String>();
	List<String> fourthStageSolutions = new ArrayList<String>();
	List<String> allTestMoves = new ArrayList<String>();

	byte cube[][] = 
				{{0,0,0,0,0,0,0,0,0},
				{1,1,1,1,1,1,1,1,1},
				{2,2,2,2,2,2,2,2,2},
				{3,3,3,3,3,3,3,3,3},
				{4,4,4,4,4,4,4,4,4},
				{5,5,5,5,5,5,5,5,5}};




	public SolveCube () throws IOException{
		List<String> firstLookupTable = Files.readAllLines(Paths.get("stage0.txt"));
		for(String i : firstLookupTable)
			firstStageSolutions.add(i);

		List<String> secondLookupTable = Files.readAllLines(Paths.get("stage1.txt"));
		for(String i : secondLookupTable)
			secondStageSolutions.add(i);

		List<String> thirdLookupTable = Files.readAllLines(Paths.get("stage2.txt"));
		for(String i : thirdLookupTable)
			thirdStageSolutions.add(i);

		List<String> fourthLookupTable = Files.readAllLines(Paths.get("stage3.txt"));
		for(String i : fourthLookupTable)
			fourthStageSolutions.add(i);

		List<String> testMoves = Files.readAllLines(Paths.get("testMoves.txt"));
		for(String i : testMoves)
			allTestMoves.add(i);
	}


	public void inputCube(SolveCube s){
		char [][] input = new char[6][9];
		String[] sides = { "TOP", "LEFT", "FRONT", "RIGHT", "BACK", "BOTTOM" };
		int side_index = 0; 
		Scanner in = new Scanner(System.in);


		System.out.println("Welcome to the 3x3x3 cube solver :)\n \n"
				+ "Please enter your cube \nNOTE: READ LEFT TO RIGHT \n");

		for (String side : sides) {
			System.out.println("Enter the colors for the " + side + " side");
			System.out.println(" w = white \n o = orange \n g = green \n r = red\n b = blue\n y = yellow\n");
			int counter = 0;
			while (counter < 9) {
				success: {
				fail: {
				if(!in.hasNext("[wogrby]")){
					System.err.println("Not a valid character please re-enter sticker");
					counter --;
				}
				String currInput = in.next();
				if (currInput.length() != 1) {
					System.err.println("One letter At A Time. Re-enter sticker");
					counter --; //reset to last number for second chance
					break fail;
				}  

				char result = currInput.toCharArray()[0];
				input[side_index][counter] = result;
				break success;
			}
			}
			counter++;
			}
			side_index++;
		}
		in.close();

		charToDigit(input,s);
	}

	private void charToDigit(char [][] input, SolveCube s){

		byte firstCube [][] = new byte[6][9];
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < input[i].length; j++){
				switch(input[i][j]){
				case 'w':
					firstCube[i][j] = 0;
					break;
				case 'o':
					firstCube[i][j] = 1;
					break;
				case 'g':
					firstCube[i][j] = 2;
					break;
				case 'r':
					firstCube[i][j] = 3;
					break;
				case 'b':
					firstCube[i][j] = 4;
					break;
				case 'y':
					firstCube[i][j] = 5;
					break;
				default:
					System.out.println("NOT A CHAR!");
				}
			}
		}
		mapOrientation(firstCube, s);
	}

	public String mapOrientation(byte[][] firstCube, SolveCube c){

		TableGenerator s = new TableGenerator();        

		s.cube = firstCube;
		s.print_cube();
		String solution = null;
		byte[][] mappings= 
				{{0,2,3,4,1,5},{1,2,0,4,5,3},{5,2,1,4,3,0},{3,2,5,4,0,1},
				{3,0,2,5,4,1},{2,0,1,5,3,4},{1,0,4,5,2,3},{4,0,3,5,1,2},
				{4,3,5,1,0,2},{0,3,4,1,2,5},{2,3,0,1,5,4},{5,3,2,1,4,0},
				{5,4,3,2,1,0},{3,4,0,2,5,1},{0,4,1,2,3,5},{1,4,5,2,0,3},
				{1,5,2,0,4,3},{4,5,1,0,3,2},{3,5,4,0,2,1},{2,5,3,0,1,4},
				{2,1,5,3,0,4},{5,1,4,3,2,0},{4,1,0,3,5,2},{0,1,2,3,4,5}};

		byte[] foundMap = null;

		//Orange[217,95,1], 
		//49.3524708607811,59.43803577863738,60.89846723856668
		
		//red[153,18,18]
		//32.36625857894016,52.158645356244676, 38.13044181886191
		
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
				cube[i][j] = foundMap[firstCube[i][j]];
			}
		}

		s.cube = cube;

		try {
			
			solution = c.solver(c,s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return solution ; 
	}

	//Cube input solve final.

	private String readSingleTurn(String wholeSolution) {

		wholeSolution = wholeSolution.replaceAll(" {2,}"," ").trim(); //empty space at the beginning screws it up so this fixes it.
		String []unoptimized = wholeSolution.split("\\s+");


		for(int i = 2; i < unoptimized.length;i++){
			String index = unoptimized[i];
			String lastIndex = unoptimized[i -1];
			String b4lastIndex = unoptimized[i - 2];

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

	//Reads turn 1 by 1
	//FYI first is really second and second is first but whatever.
	private String combineTurns(String firstTurn, String secondTurn) {
		if(firstTurn.endsWith("i") && secondTurn.endsWith("i")) {			
			firstTurn = firstTurn.replace("i", "2");
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
			return firstTurn;
		}
		if(firstTurn.endsWith("2") && secondTurn.endsWith("i")) {
			firstTurn = firstTurn.replace("2", "");
			return firstTurn;
		}

		if(firstTurn.endsWith("") && secondTurn.endsWith("2")) {
			firstTurn = firstTurn + "i";
			return firstTurn;

		}
		if(firstTurn.endsWith("2") && secondTurn.endsWith("")) {
			firstTurn = firstTurn.replace("2", "i");
			return firstTurn;
		}
		return "THIS SHOULDN'T HAPPEN";
	}


	//Sequence Solve final. just call
	public String solver(SolveCube c, TableGenerator g) throws IOException{

		
//		xg.print_cube();
		TableGenerator savedCube = new TableGenerator();
		savedCube.copyCube(g);

		String currTestMoves = ""; 
		long start = System.currentTimeMillis();
		long end = start + 5 * 1000; //x seconds
		int shortestSoFar = 50; 
		
		System.out.println("Calculating...");

		for(int i = 0; i < c.allTestMoves.size() && System.currentTimeMillis() < end;i++){
			String solution = c.solve(g,savedCube, c.allTestMoves.get(i), false);
			int length = solution.split("\\s+").length;
			if(length < shortestSoFar){
				currTestMoves = c.allTestMoves.get(i);
			
				String currShortestSolution = c.solve(g, savedCube, c.allTestMoves.get(i), false);
				shortestSoFar = currShortestSolution.split("\\s+").length;
				if(shortestSoFar <= 25)
					break;
			}
		}
		String shortestSolution = c.solve(g, savedCube,currTestMoves, true);
		return shortestSolution;	
	}

	private String solve(TableGenerator obj , TableGenerator savedCube,  String testMoves, boolean printAll) throws IOException{

		//Apply the turns get the hash and restart.
		obj.copyCube(savedCube);
		String wholeSolution = "";
	 
		obj.apply_turns(testMoves);

		int hash = obj.hash_2x2x2();
		wholeSolution += stageSolution(hash, 6, firstStageSolutions);	
		wholeSolution = readSingleTurn(wholeSolution); 
		obj.apply_turns(wholeSolution);

		int hash2 = obj.hash_2x2x2B();
		wholeSolution += stageSolution(hash2, 5, secondStageSolutions) + " ";
		wholeSolution = readSingleTurn(wholeSolution); 
		obj.copyCube(savedCube);
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);


		int hash3 = obj.hashBowTie();			
		wholeSolution += stageSolution(hash3, 7, thirdStageSolutions) + " ";

		wholeSolution = readSingleTurn(wholeSolution); 
		obj.copyCube(savedCube);
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);

		int hash4 = obj.hash_1x1x3Stacks();
		wholeSolution += stageSolution(hash4, 4, fourthStageSolutions);
		wholeSolution = readSingleTurn(wholeSolution); 
		obj.apply_turns(testMoves);
		obj.apply_turns(wholeSolution);

		
		wholeSolution = testMoves + wholeSolution;
		obj.copyCube(savedCube);
		wholeSolution = readSingleTurn(wholeSolution);
		obj.apply_turns(wholeSolution);

		if(printAll == true){
			obj.print_cube();
			System.out.println("Your solution :) \n" +  wholeSolution.replaceAll("i", "'"));
		}
		String trim = wholeSolution.trim();
		int solutionLength = trim.split("\\s+").length;
		if(printAll == true)
			System.out.println("Number of moves: " + solutionLength);

		return trim;
	}

	//For specific solution
	public String arrangeString(String str , int hashLength){ 
		String reverseLine = new StringBuffer(str.substring(hashLength)).reverse().toString();
		String cleanerLine = reverseLine.replaceAll(",", "").replaceAll(" {2,}"," ").replaceAll("PN ", "");
		return cleanerLine;
	}

	//Solution application
	public String stageSolution(int hash, int hashLength, List<String> stageSolutions){
		String stageOneSolution = "";
		try {
			String turns = stageSolutions.get(hash);
			stageOneSolution = arrangeString(turns, hashLength);

		} catch (Exception e) {
			System.out.println("False cube! Color detection failed. Please retry");
			System.exit(0);
		}
		return stageOneSolution;
	}
}
