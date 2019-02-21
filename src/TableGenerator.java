
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TableGenerator{


	public byte [][] cube;
	//                                            RB          FL     |    UL          UB          UR       UF     |     BL          LD          BD    |   FR          RD        FD                  
	private static int [] edgeList =         {3, 5, 4, 3, 2, 3, 1, 5, 0, 3, 1, 1, 0, 1, 4, 1, 0, 5, 3, 1, 0, 7, 2, 1, 4, 5, 1, 3, 1, 7, 5, 3, 4, 7, 5, 7, 2, 5, 3, 3, 3, 7, 5, 5,     2, 7, 5, 1};
	//                                            LF                BR               UR                 UF               UL                UB                FR                RD              FD                 BL                LD                BD             
	private static int [] mappedEdgeList =   {1, 5, 2, 3,       4, 3, 3, 5,      0, 5, 3, 1,       0, 7, 2, 1,       0, 3, 1, 1,       0, 1, 4, 1,       2, 5, 3, 3,       3, 7, 5, 5,      2, 7, 5, 1,       4, 5, 1, 3,       1, 7, 5, 3,       4, 7, 5, 7};

	//												BDR                  BRU                  FDL                	 FLU                    	
	private static int [] cornerList =       {4, 6, 5, 8, 3, 8,    4, 0, 3, 2, 0, 2,    2, 6, 5, 0, 1, 8,      2, 0, 1, 2, 0, 6,      4, 2, 0, 0, 1, 0, 2, 2, 0, 8, 3, 0, 4, 8, 1, 6, 5, 6, 2, 8, 3, 6, 5, 2};
	private static int [] mappedCornerList = {2, 6, 5, 0, 1, 8, 2, 0, 1, 2, 0, 6, 4, 6, 5, 8, 3, 8, 4, 0, 3, 2, 0, 2, 2, 2, 0, 8, 3, 0, 4, 2, 0, 0, 1, 0, 2, 8, 3, 6, 5, 2, 4, 8, 1, 6, 5, 6};


	TableGenerator() {
		cube = new byte[][] {
			{0,0,0,0,0,0,0,0,0}, 
			{1,1,1,1,1,1,1,1,1},
			{2,2,2,2,2,2,2,2,2},
			{3,3,3,3,3,3,3,3,3},
			{4,4,4,4,4,4,4,4,4},
			{5,5,5,5,5,5,5,5,5},
		};
	}

	public static void main(String[] args) throws IOException {


		TableGenerator g = new TableGenerator();
		//g.startSearch(g,16);
		
	}		

	/*
	 *
		STAGE: 0
		0 1 1
		1 9 10
		2 90 100
		3 852 952
		4 7169 8121 
		5 44182 52303
		6 131636 183939
		7 68940 252879
		8 561 253440

		STAGE 1 
		0 1 1
		1 6 7
		2 30 37
		3 158 195
		4 863 1058
		5 4206 5264
		6 16056 21320
		7 36126 57446
		8 25576 83022
		9 1650 84672

		STAGE: 2
		0 1 1
		1 3 4
		3 12 16
		4 76 92
		5 190 282
		6 782 1064
		7 4348 5412
		8 16990 22402
		9 66928 89330
		10 242152 331482
		11 580465 911947
		12 574585 1486532
		13 68653 1555185
		14 15 1555200
		
		
		STAGE: 3
		0 1 1
		8 48 49
		9 16 65
		10 5 70
		11 28 98
		12 153 251
		13 539 790
		14 373 1163
		15 117 1280
		16 16 1296

	 */

	public int hash_2x2x2(){

		int edgeFD = hashEdge(2,5);
		int edgeRD = hashEdge(3,5);
		int edgeFR = hashEdge(2,3);
		int cornerFRD = hashCorner(2,3,5);

		if(edgeRD > edgeFD)
			edgeRD-= 2;
		if(edgeFR > edgeFD)
			edgeFR -= 2;

		if(edgeFR > edgeRD)
			edgeFR -= 2;

		return ((edgeFD * 22 + edgeRD ) * 20 + edgeFR) * 24 + cornerFRD;
	}

	public int hash_2x2x2B(){
		//LF BR UR UF UL UB FR RD FD BL LD BD             
		//RB FL | UL UB UR UF | BL LD BD | FR RD FD                  

		int edgeBD = hashEdge(4,5);
		int edgeLD = hashEdge(1,5);
		int edgeBL = hashEdge(4,1);

		int cornerBLD = hashCorner(4,1,5);

		if(edgeLD > edgeBD)
			edgeLD -= 2;
		if(edgeBL > edgeBD)
			edgeBL -= 2;

		if(edgeBL> edgeLD)
			edgeBL -= 2;

		return ((edgeBD * 16 + edgeLD) * 14 + edgeBL) * 21 + cornerBLD;
	}
	
	public int hashBowTie(){
		int edgeUF = hashEdge(0, 2);
		int edgeUR = hashEdge(0, 3);
		int edgeUB = hashEdge(0, 4);
		int edgeUL = hashEdge(0, 1);
		int cornerFUR = hashCorner(2,0,3);
		int cornerBUL = hashCorner(4,0,1);

		if(edgeUR > edgeUF)
			edgeUR-= 2;
		if(edgeUB > edgeUF)
			edgeUB -= 2;
		if(edgeUL > edgeUF)
			edgeUL -= 2;

		if(edgeUB > edgeUR)
			edgeUB -= 2;
		if(edgeUL > edgeUR)
			edgeUL -= 2;

		if(edgeUL > edgeUB)
			edgeUL -= 2;

		if(cornerBUL > cornerFUR)
			cornerBUL -= 3;
	
		return ((((edgeUF * 10 + edgeUR) * 8 +  edgeUB) * 6 + edgeUL ) * 18 + cornerFUR) * 15 + cornerBUL;

	}
	
	public int hash_1x1x3Stacks(){
		int edgeFL = hashEdge(2,1);
		int cornerFLU = hashCorner(2,1,0);
		int cornerFDL = hashCorner(2,5,1);
		int cornerBRU = hashCorner(4,3,0);
		
		
		if(edgeFL >= 2)
			edgeFL -= 2;
		
		if(cornerFDL > cornerFLU)
			cornerFDL -= 3;
		if(cornerBRU > cornerFLU)
			cornerBRU -= 3;
		
		
		if(cornerBRU > cornerFDL)
			cornerBRU -= 3;
		
		return (((edgeFL)* 12 + cornerFLU) * 9  + cornerFDL) * 6 + cornerBRU;
		
		
	}

	private void hashMappingComponents(){

		// [] edgeList =         {3, 5, 4, 3,   2, 3, 1, 5,

		//this slightly confusing
		System.out.println(Arrays.toString(edgeList));
		for(int i = 2; i < mappedEdgeList.length; i+=4){
			int secondHalf = mappedEdgeList[i];
			int firstHalf = mappedEdgeList[i-2];

			for(int j = 2; j < edgeList.length; j+=4){
				if((edgeList[j-2] == firstHalf && edgeList[j] == secondHalf)){
					System.out.println("first");
					System.out.printf("%s, %s \n", firstHalf, secondHalf);
					System.out.println();
					
					mappedEdgeList[i-1] = edgeList[j-1];
					mappedEdgeList[i+1] = edgeList[j+1];
				}
				else if(edgeList[j-2] == secondHalf && edgeList[j] == firstHalf){
					System.out.println("second");
					System.out.printf("%s, %s \n", firstHalf, secondHalf);
					
					mappedEdgeList[i+1] = edgeList[j-1];
					mappedEdgeList[i-1] = edgeList[j+1];
					
				}
					
			}
		}
		System.out.println(Arrays.toString(cornerList));
		
		for(int i = 4; i < mappedCornerList.length; i+=6){
			//					2, 6, 5, 2, 1, 8,
			int lastHalf = mappedCornerList[i];
			int midHalf = mappedCornerList[i-2];
			int firstHalf = mappedCornerList[i-4];
			
			for(int j = 4; j < cornerList.length; j+=6){
				if((cornerList[j-4] == firstHalf) && (cornerList[j-2] == midHalf) && (cornerList[j] == lastHalf)){
					mappedCornerList[i+1] = cornerList[j+1];
					mappedCornerList[i-1] = cornerList[j-1];
					mappedCornerList[i-3] = cornerList[j-3];
				}
			}
		}
	}
	
	private int mappedHash_2x2x2(){

		int edgeBD = hashMappedEdge(4,5);
		int edgeLD = hashMappedEdge(1,5);
		int edgeBL = hashMappedEdge(4,1);

		int cornerBLD = hashMappedCorner(4,1,5);

		if(edgeLD > edgeBD)
			edgeLD -= 2;
		if(edgeBL > edgeBD)
			edgeBL -= 2;

		if(edgeBL > edgeLD)
			edgeBL -= 2;

		return ((edgeBD * 22 + edgeLD) * 20 + edgeBL) * 24 + cornerBLD;		

	}
	
	private int mappedHash_2x2x3(){
		//LF BR UR UF UL UB FR RD FD BL LD BD  
		//LF BR UR UF UL UB FR RD FD BL LD BD             


		int edgeBD = hashMappedEdge(4,5);
		int edgeLD = hashMappedEdge(1,5);
		int edgeBL = hashMappedEdge(4,1);
		int edgeUB = hashMappedEdge(0,4);
		int edgeUL = hashMappedEdge(0,1);
		
		int cornerBLD = hashMappedCorner(4,1,5);
		int cornerBUL = hashMappedCorner(4,0,1);
		
		if(edgeLD > edgeBD)
			edgeLD -= 2;
		if(edgeBL > edgeBD)
			edgeBL -= 2;
		if(edgeUB > edgeBD)
			edgeUB -= 2;
		if(edgeUL > edgeBD)
			edgeUL -= 2;
		
		if(edgeBL > edgeLD)
			edgeBL -= 2;
		if(edgeUB > edgeLD)
			edgeUB -= 2;
		if(edgeUL > edgeLD)
			edgeUL -= 2;
		
		if(edgeUB > edgeBL)
			edgeUB -=2;
		if(edgeUL > edgeBL)
			edgeUL -= 2;
		
		if(edgeUL > edgeUB)
			edgeUL -= 2;
		
		if(cornerBUL > cornerBLD)
			cornerBUL -= 3;
		//System.out.println("returned: " + (((((edgeBD * 22 + edgeLD) * 20 + edgeBL) * 18 + edgeUB) * 16 + edgeUL) * 24 + cornerBLD) * 21 + cornerBUL + "\n");
		return (((((edgeBD * 22 + edgeLD) * 20 + edgeBL) * 18 + edgeUB) * 16 + edgeUL) * 24 + cornerBLD) * 21 + cornerBUL;
	}
	
	private int hash_2x2x3(){
		//RB FL UL UB UR UF BL LD BD FR RD FD                  

		int edgeFD = hashEdge(2,5);
		int edgeRD = hashEdge(3,5);
		int edgeFR = hashEdge(2,3);
		int edgeUF = hashEdge(0,2);
		int edgeUR = hashEdge(0,3);
		int cornerFRD = hashCorner(2,3,5);
		int cornerFUR = hashCorner(2,0,3);

		if(edgeRD > edgeFD)
			edgeRD -= 2;
		if(edgeFR > edgeFD)
			edgeFR -= 2;
		if(edgeUF > edgeFD)
			edgeUF -= 2;
		if(edgeUR > edgeFD)
			edgeUR -= 2;
		
		if(edgeFR > edgeRD)
			edgeFR -= 2;
		if(edgeUF > edgeRD)
			edgeUF -= 2;
		if(edgeUR > edgeRD)
			edgeUR -= 2;
		
		if(edgeUF > edgeFR)
			edgeUF -= 2;
		if(edgeUR > edgeFR)
			edgeUR -= 2;
		
		if(edgeUR > edgeUF)
			edgeUR -= 2;
		
		if(cornerFUR > cornerFRD)
			cornerFUR -= 3;
		
		return (((((edgeFD * 22 + edgeRD) * 20 + edgeFR) * 18 + edgeUF) * 16 + edgeUR) * 24 + cornerFRD) * 21 + cornerFUR;
		
		
	}
	
	private int hashMappedEdge(int firstColor, int secondColor){
		int value = 0;
		for(int i = 3; i < mappedEdgeList.length; i+= 4){
			if(firstColor == cube[mappedEdgeList[i - 3]][mappedEdgeList[i - 2]] && secondColor == cube[mappedEdgeList[i - 1]][mappedEdgeList[i]])
				break;
			else 
				value++;
			if(firstColor == cube[mappedEdgeList[i - 1]][mappedEdgeList[i]] && secondColor == cube[mappedEdgeList[i - 3]][mappedEdgeList[i - 2]])
				break;
			else
				value++;
		}
		return value;
	}

	private int hashMappedCorner(int firstColor, int secondColor, int thirdColor){
		int value = 0;
		for(int i = 5; i < mappedCornerList.length; i+= 6){
			if(firstColor == cube[mappedCornerList[i - 5]][mappedCornerList[i - 4]] && secondColor == cube[mappedCornerList[i - 3]][mappedCornerList[i - 2]] && thirdColor == cube[mappedCornerList[i - 1]][mappedCornerList[i]])
				break;
			else 
				value++;
			if(firstColor == cube[mappedCornerList[i - 3]][mappedCornerList[i - 2]] && secondColor == cube[mappedCornerList[i - 1]][mappedCornerList[i]] && thirdColor == cube[mappedCornerList[i - 5]][mappedCornerList[i - 4]])
				break;
			else 
				value++;
			if(firstColor ==  cube[mappedCornerList[i - 1]][mappedCornerList[i]] && secondColor == cube[mappedCornerList[i - 5]][mappedCornerList[i - 4]] && thirdColor == cube[mappedCornerList[i - 3]][mappedCornerList[i - 2]])
				break;
			else 
				value++;
		}
		return value;
	}

	private int hashEdge(int firstColor, int secondColor){
		int value = 0;
		for(int i = 3; i < edgeList.length; i+= 4){
			if(firstColor == cube[edgeList[i - 3]][edgeList[i - 2]] && secondColor == cube[edgeList[i - 1]][edgeList[i]])
				break;
			else 
				value++;
			if(firstColor == cube[edgeList[i - 1]][edgeList[i]] && secondColor == cube[edgeList[i - 3]][edgeList[i - 2]])
				break;
			else
				value++;
		}
		return value;
	}

	private int hashCorner(int firstColor, int secondColor, int thirdColor){
		int value = 0;
		for(int i = 5; i < cornerList.length; i+= 6){
			if(firstColor == cube[cornerList[i - 5]][cornerList[i - 4]] && secondColor == cube[cornerList[i - 3]][cornerList[i - 2]] && thirdColor == cube[cornerList[i - 1]][cornerList[i]])
				break;
			else 
				value++;
			if(firstColor == cube[cornerList[i - 3]][cornerList[i - 2]] && secondColor == cube[cornerList[i - 1]][cornerList[i]] && thirdColor == cube[cornerList[i - 5]][cornerList[i - 4]])
				break;
			else 
				value++;
			if(firstColor ==  cube[cornerList[i - 1]][cornerList[i]] && secondColor == cube[cornerList[i - 5]][cornerList[i - 4]] && thirdColor == cube[cornerList[i - 3]][cornerList[i - 2]])
				break;
			else 
				value++;
		}
		return value;
	}

	private void formatTable() throws FileNotFoundException{
		String movements = "";
		String finalString = "";
		String fileName = "stage3.txt";

		PrintWriter outputStream = new PrintWriter(fileName);

		for(int nodes = 0; nodes < foundStates.length; nodes++){
			if(foundStates[nodes] != null){
				movements = formatString(foundStates[nodes]);
				while(movements.length() < 46)
					movements += "NP ";
				movements += "\n";

				finalString = nodes + " " + movements;
				while(finalString.length() < 54)
					finalString = "0" + finalString;

				outputStream.print(finalString);
			}	
		}
		outputStream.close();
	}

	private String formatString(byte[] currentNode){
		String[] turnNames = {"iU ", "2U ", "U  ", "iL ", "2L ", "L  ", "iF ", "2F ", "F  ",
				"iR ", "2R ", "R  ", "iB ", "2B ", "B  ", "iD ", "2D ", "D  "};
		String name = "";
		for (int b : currentNode)
			name += turnNames[b];

		return name;
	}



	byte[] pruneTable = null;	
	byte[][] foundStates;
	int threadIndex;
	byte[] allTurns;
	TableGenerator[] depthTurns;
	List<Integer> firstStageHashes = new ArrayList<Integer>();

	private void startSearch(TableGenerator g , final int maxDepth) {
		System.out.println("Stage 3");

		Path fileName = Paths.get("pruneTable.txt");
		try {
			pruneTable = Files.readAllBytes(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("File Read! ");


		float startTime = System.nanoTime();

		foundStates = new byte[1296][];

		int foundSoFar = 0;
		for(int targetDepth = 0; targetDepth <= maxDepth; targetDepth++){
			Thread [] threads = new Thread[18];
			for(int i = 0; i < threads.length; i++){
				final int currDepth = targetDepth;
				final TableGenerator threadg = new TableGenerator();                
				threadg.threadIndex = i;
				threadg.allTurns = new byte[currDepth];
				threadg.foundStates = foundStates;
				threadg.pruneTable = pruneTable;
				threadg.depthTurns = new TableGenerator[maxDepth];
				for(int x = 0; x < maxDepth; x++)
					threadg.depthTurns[x] = new TableGenerator();
				threads[i] = new Thread(new Runnable() {
					@Override
					public void run() {


						try {
							threadg.DFS(threadg, 0, currDepth);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
				threads[i].start();
			}
			for(int x = 0; x < threads.length; x++){
				if(threads[x] != null)
					try {
						threads[x].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

			int foundThisDepth = 0 ;
			for(int i = 0; i  < foundStates.length; i++){
				if(foundStates[i] != null){
					foundThisDepth++;
				}
			}
			int newThisDepth = foundThisDepth - foundSoFar;
			foundSoFar = foundThisDepth;
			if(newThisDepth >= 2){
				System.out.printf("%d: %d : %d\n", targetDepth, newThisDepth, foundSoFar);
				float stopTime = (System.nanoTime()- startTime)/ 1000000000;
				System.out.println(stopTime/60 + " minutes");    
			}
		}

		System.out.println("Done :)");

		try {
			formatTable();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void copyCube(TableGenerator cube_to_copy)  {
		byte [][] oldCube = cube_to_copy.cube;

		for(int i = 0;i < cube.length; i++){
			for(int x = 0; x < cube[i].length; x++){
				cube[i][x] = oldCube[i][x];
			}		
		}   
	}	
	private byte[] copyTurns(byte[] turns_to_copy){
		byte [] copiedTurns = new byte[turns_to_copy.length];

		for(int i = 0; i < turns_to_copy.length;i++){
			copiedTurns[i] = turns_to_copy[i];
		}
		return copiedTurns;
	}


	/*
	 *--------------------------------------------------------------------------------
	 * Iterative Deepening Depth First Search algorithm that finds positions of the cube
	 *--------------------------------------------------------------------------------
	 */


	private void DFS (TableGenerator turn, int depth, int targetDepth) throws IOException{			

		if(depth == targetDepth) {
			if(turn.checkStage0() == true  && turn.checkStage1() == true && turn.checkStage2() == true){
				if(foundStates[turn.hashCode()] == null){
					foundStates[turn.hashCode()] = copyTurns(allTurns);
				}
			}
		
		} else {

			//Regular hash2x2x3
			int tableStateValue = turn.hash_2x2x3();
			boolean high  = (tableStateValue & 1) != 0;
			tableStateValue = tableStateValue >> 1;
			if(high){
				byte right = (byte)((pruneTable[tableStateValue] >> 4) & 0xF);
				if(right + (byte) depth > (byte) targetDepth)
					return;
				
			} else{
				byte left = (byte)(pruneTable[tableStateValue] & 0xF);
				if(left + (byte) depth > (byte)targetDepth)
					return;
			}
			//hash2x2x3A
			int tableStateValueA = turn.mappedHash_2x2x3();
			boolean highA = (tableStateValueA & 1) != 0;
			tableStateValueA = tableStateValueA >> 1;
			if(highA){
				byte rightA = (byte)((pruneTable[tableStateValueA] >> 4) & 0xF);
				if(rightA + (byte) depth > (byte) targetDepth)
					return;
			} else {
				byte leftA = (byte)(pruneTable[tableStateValueA] & 0xF);
				if(leftA + (byte) depth > (byte) targetDepth)
					return;
			}
						
			
			int newDepth = depth + 1;

			int prevSide;
			if(depth == 0) {
				prevSide = -1;
			} else {
				prevSide = allTurns[depth-1] / 3;
			}


			for (int i = 0; i < 18; i++) {

				if (depth == 0 && i != threadIndex) {
					continue;
				}

				TableGenerator newTurn = null;

				switch (i) {
				case 0:
					if(prevSide == 5 || prevSide == 0){
						i+= 2;
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_U();
					allTurns[depth] = (byte) i;
					break;
				case 1:
					if(prevSide == 5 || prevSide == 0){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_U2();
					allTurns[depth] = (byte) i;
					break;
				case 2:
					if(prevSide == 5 || prevSide == 0){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Ui();
					allTurns[depth] = (byte) i;
					break;
				case 3:
					if(prevSide == 3 || prevSide == 1){
						i+= 2;
						break;
					}

					newTurn = depthTurns[depth];					
					newTurn.copyCube(turn);
					newTurn.turn_L();
					allTurns[depth] = (byte) i;
					break;
				case 4:
					if(prevSide == 3 || prevSide == 1){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_L2();
					allTurns[depth] = (byte) i;
					break;
				case 5:
					if(prevSide == 3 || prevSide == 1){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Li();
					allTurns[depth] = (byte) i;
					break;

				case 6:
					if(prevSide == 2 || prevSide == 4){
						i+= 2;
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_F();
					allTurns[depth] = (byte) i;
					break;
				case 7:
					if(prevSide == 2 || prevSide == 4){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_F2();
					allTurns[depth] = (byte) i;
					break;
				case 8:
					if(prevSide == 2 || prevSide == 4){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Fi();
					allTurns[depth] = (byte) i;
					break;					
				case 9:
					if(prevSide == 3){
						i+= 2;
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_R();
					allTurns[depth] = (byte) i;
					break;
				case 10:
					if(prevSide == 3){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_R2();
					allTurns[depth] = (byte) i;
					break;
				case 11:
					if(prevSide == 3){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Ri();
					allTurns[depth] = (byte) i;
					break;
				case 12:
					if(prevSide == 4){
						i+= 2;
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_B();
					allTurns[depth] = (byte) i;
					break; 
				case 13:
					if(prevSide == 4){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_B2();
					allTurns[depth] = (byte) i;
					break;
				case 14:
					if(prevSide == 4){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Bi();
					allTurns[depth] = (byte) i;
					break;
				case 15:
					if(prevSide == 5){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_D();
					allTurns[depth] = (byte) i;
					break;
				case 16:
					if(prevSide == 5){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_D2();
					allTurns[depth] = (byte) i;
					break;
				case 17:
					if(prevSide == 5){
						break;
					}

					newTurn = depthTurns[depth];
					newTurn.copyCube(turn);
					newTurn.turn_Di();
					allTurns[depth] = (byte) i;
					break;
				}
				if(newTurn != null){
					DFS(newTurn, newDepth, targetDepth);
				}
			}
		} 
	}


	private boolean checkStage0(){
		if(cube[2][7] == 2 && cube[5][1] == 5 &&  
				cube[3][3] == 3 && cube[2][5] == 2 &&
				cube[3][7] == 3 && cube[5][5] == 5 &&
				cube[2][8] == 2 && cube[3][6] == 3 && cube[5][2] == 5)
			return true;
		else 
			return false;
	}

	private boolean checkStage1(){
		if(cube[1][3] == 1 && cube[4][5] == 4 &&  
				cube[4][7] == 4 && cube[5][7] == 5 &&
				cube[1][7] == 1 && cube[5][3] == 5 &&
				cube[4][8] == 4 && cube[1][6] == 1 && cube[5][6] == 5)
			return true;
		else 
			return false;
	}

	private boolean checkStage2(){
		if(cube[0][1] == 0 && cube[4][1] == 4 &&
				cube[0][3] == 0 && cube[1][1] == 1 &&
				cube[0][5] == 0 && cube[3][1] == 3 &&
				cube[0][7] == 0 && cube[2][1] == 2 &&
				cube[0][0] == 0 && cube[1][0] == 1 && cube[4][2] == 4 &&
				cube[0][8] == 0	&& cube[2][2] == 2 && cube[3][0] == 3)
			return true;
		else 
			return false;
	}

	@Override
	public boolean equals(Object other) {
		return hash_1x1x3Stacks() == ((TableGenerator) other).hash_1x1x3Stacks();
	}
	@Override
	public int hashCode() {
		return hash_1x1x3Stacks();
	}
	@Override
	public String toString() {
		String acc = "" + hash_1x1x3Stacks() + "";
		return acc;
	}

	
	
	String scramble_cube() {
		Random rand = new Random();
		String scrambleStr = "";
		for (int scramble = 0; scramble < 28; scramble++) {
			int mixcube = rand.nextInt(18);
			switch (mixcube) {
			case 0:
				if(scrambleStr.endsWith(" U ") || scrambleStr.endsWith(" Ui ") || scrambleStr.endsWith(" U2 ")){
					break;
				}
				scrambleStr += " U ";
				break;
			case 1:
				if(scrambleStr.endsWith(" U ") || scrambleStr.endsWith(" Ui ") || scrambleStr.endsWith(" U2 ")){
					break;
				}
				scrambleStr += " Ui ";
				break;
			case 2:
				if(scrambleStr.endsWith(" U ") || scrambleStr.endsWith(" Ui ") || scrambleStr.endsWith(" U2 ")){
					break;
				}
				scrambleStr += " U2 ";
				break;
			case 3:
				if(scrambleStr.endsWith(" F ") || scrambleStr.endsWith(" Fi ") || scrambleStr.endsWith(" F2 ")){
					break;
				}
				scrambleStr += " F ";
				break;
			case 4:
				if(scrambleStr.endsWith(" F ") || scrambleStr.endsWith(" Fi ") || scrambleStr.endsWith(" F2 ")){
					break;
				}
				scrambleStr += " Fi ";
				break;
			case 5:
				if(scrambleStr.endsWith(" F ") || scrambleStr.endsWith(" Fi ") || scrambleStr.endsWith(" F2 ")){
					break;
				}
				scrambleStr += " F2 ";
				break;
			case 6:
				if(scrambleStr.endsWith(" R ") || scrambleStr.endsWith(" Ri ") || scrambleStr.endsWith(" R2 ")){
					break;
				}
				scrambleStr += " R ";
				break;
			case 7:
				if(scrambleStr.endsWith(" R ") || scrambleStr.endsWith(" Ri ") || scrambleStr.endsWith(" R2 ")){
					break;
				}
				scrambleStr += " Ri ";
				break;
			case 8:
				if(scrambleStr.endsWith(" R ") || scrambleStr.endsWith(" Ri ") || scrambleStr.endsWith(" R2 ")){
					break;
				}
				scrambleStr += " R2 ";
				break;
			case 9:
				if(scrambleStr.endsWith(" L ") || scrambleStr.endsWith(" Li ") || scrambleStr.endsWith(" L2 ")){
					break;
				}
				scrambleStr += " L ";
				break;
			case 10:
				if(scrambleStr.endsWith(" L ") || scrambleStr.endsWith(" Li ") || scrambleStr.endsWith(" L2 ")){
					break;
				}
				scrambleStr += " Li ";
				break;
			case 11:
				if(scrambleStr.endsWith(" L ") || scrambleStr.endsWith(" Li ") || scrambleStr.endsWith(" L2 ")){
					break;
				}
				scrambleStr += " L2 ";
				break;
			case 12:
				if(scrambleStr.endsWith(" B ") || scrambleStr.endsWith(" Bi ") || scrambleStr.endsWith(" B2 ")){
					break;
				}
				scrambleStr += " B ";
				break;
			case 13:
				if(scrambleStr.endsWith(" B ") || scrambleStr.endsWith(" Bi ") || scrambleStr.endsWith(" B2 ")){
					break;
				}
				scrambleStr += " Bi ";
				break;
			case 14:
				if(scrambleStr.endsWith(" B ") || scrambleStr.endsWith(" Bi ") || scrambleStr.endsWith(" B2 ")){
					break;
				}
				scrambleStr += " B2 ";
				break;
			case 15:
				if(scrambleStr.endsWith(" D ") || scrambleStr.endsWith(" Di ") || scrambleStr.endsWith(" D2 ")){
					break;
				}
				scrambleStr += " D ";
				break;
			case 16:
				if(scrambleStr.endsWith(" D ") || scrambleStr.endsWith(" Di ") || scrambleStr.endsWith(" D2 ")){
					break;
				}
				scrambleStr += " Di ";
				break;
			case 17:
				if(scrambleStr.endsWith(" D ") || scrambleStr.endsWith(" Di ") || scrambleStr.endsWith(" D2 ")){
					break;
				}
				scrambleStr += " D2 ";
				break;
			default:
				System.out.println("not an actual turn :/ ");
				break;
			}
		}
		return scrambleStr;
	}
	void apply_turns(String Notation) {
		for (int letter = 0; letter < Notation.length(); letter++) {
			char oneLetter = Notation.charAt(letter);
			if (letter + 1 < Notation.length() && Notation.charAt(letter + 1) == '2') {
				letter++; // consume the '2'
				switch (oneLetter) {
				case 'D':
					turn_D2();
					break;
				case 'R':
					turn_R2();
					break;
				case 'U':
					turn_U2();
					break;
				case 'L':
					turn_L2();
					break;
				case 'B':
					turn_B2();
					break;
				case 'F':
					turn_F2();
					break;
				}
			}

			else if (letter + 1 < Notation.length() && Notation.charAt(letter + 1) == 'i') {
				letter++;
				switch (oneLetter) {
				case 'D':
					turn_Di();
					break;
				case 'R':
					turn_Ri();
					break;
				case 'U':
					turn_Ui();
					break;
				case 'L':
					turn_Li();
					break;
				case 'B':
					turn_Bi();
					break;
				case 'F':
					turn_Fi();
					break;

				}
			} else
				switch (oneLetter) {
				case 'D':
					turn_D();
					break;
				case 'R':
					turn_R();
					break;
				case 'U':
					turn_U();
					break;
				case 'L':
					turn_L();
					break;
				case 'B':
					turn_B();
					break;
				case 'F':
					turn_F();
					break;
				}
		}
	}
	

	private void turn_side(int side_num) { 
		byte cube0_1 =       cube[side_num][1];
		byte cube0_0 =       cube[side_num][0];
		cube[side_num][1] = cube[side_num][3];
		cube[side_num][0] = cube[side_num][6];
		cube[side_num][3] = cube[side_num][7];
		cube[side_num][6] = cube[side_num][8];
		cube[side_num][7] = cube[side_num][5];
		cube[side_num][8] = cube[side_num][2];
		cube[side_num][5] =   cube0_1;
		cube[side_num][2] =   cube0_0; 
	}
	private void turn_U() { 
		turn_side(0);
		byte cube1_0 = cube[1][0];
		byte cube1_1 = cube[1][1];
		byte cube1_2 = cube[1][2];
		cube[1][0] = cube[2][0];
		cube[1][1] = cube[2][1];
		cube[1][2] = cube[2][2];
		//front side
		cube[2][0] = cube[3][0];
		cube[2][1] = cube[3][1];
		cube[2][2] = cube[3][2];
		// right side 
		cube[3][0] = cube[4][0];
		cube[3][1] = cube[4][1];
		cube[3][2] = cube[4][2];
		// back side 
		cube[4][0] = cube1_0;
		cube[4][1] = cube1_1;
		cube[4][2] = cube1_2;
	}
	private void turn_F(){ 
		turn_side(2); 
		byte cube0_6 = cube[0][6];
		byte cube0_7 = cube[0][7];
		byte cube0_8 = cube[0][8];
		cube[0][6] = cube[1][8];
		cube[0][7] = cube[1][5];
		cube[0][8] = cube[1][2];
		// left side 
		cube[1][8] = cube[5][2];
		cube[1][5] = cube[5][1];
		cube[1][2] = cube[5][0];
		// down side
		cube[5][2] = cube[3][0];
		cube[5][1] = cube[3][3];
		cube[5][0] = cube[3][6];
		// right side
		cube[3][0] = cube0_6;
		cube[3][3] = cube0_7;
		cube[3][6] = cube0_8;
	}
	private void turn_R() { 
		turn_side(3);  
		byte cube0_2 = cube[0][2];
		byte cube0_5 = cube[0][5];
		byte cube0_8 = cube[0][8];
		cube[0][2] = cube[2][2];
		cube[0][5] = cube[2][5];
		cube[0][8] = cube[2][8];
		//front side 
		cube[2][2] = cube[5][2];
		cube[2][5] = cube[5][5];
		cube[2][8] = cube[5][8];
		//down side
		cube[5][2] = cube[4][6];
		cube[5][5] = cube[4][3];
		cube[5][8] = cube[4][0];
		//back side
		cube[4][6] = cube0_2;
		cube[4][3] = cube0_5;
		cube[4][0] = cube0_8;

	}
	private void turn_D(){ 
		turn_side(5);	
		byte cube2_6 = cube[2][6];
		byte cube2_7 = cube[2][7];
		byte cube2_8 = cube[2][8];
		cube[2][6] = cube[1][6];
		cube[2][7] = cube[1][7];
		cube[2][8] = cube[1][8];
		// left side
		cube[1][6] = cube[4][6];
		cube[1][7] = cube[4][7];
		cube[1][8] = cube[4][8];
		//back side
		cube[4][6] = cube[3][6];
		cube[4][7] = cube[3][7];
		cube[4][8] = cube[3][8];
		// right side
		cube[3][6] = cube2_6;
		cube[3][7] = cube2_7;
		cube[3][8] = cube2_8; 
	}
	private void turn_L(){
		turn_side(1);
		byte cube0_0 = cube[0][0];
		byte cube0_3 = cube[0][3];
		byte cube0_6 = cube[0][6];
		cube[0][0] = cube[4][8];
		cube[0][3] = cube[4][5];
		cube[0][6] = cube[4][2];
		// back side
		cube[4][8] = cube[5][0];
		cube[4][5] = cube[5][3];
		cube[4][2] = cube[5][6];
		// down side
		cube[5][0] = cube[2][0];
		cube[5][3] = cube[2][3];
		cube[5][6] = cube[2][6];
		// front side
		cube[2][0] = cube0_0;
		cube[2][3] = cube0_3;
		cube[2][6] = cube0_6;
	}
	private void turn_B(){ 
		turn_side(4);	
		byte cube0_0 = cube[0][0];
		byte cube0_1 = cube[0][1];
		byte cube0_2 = cube[0][2];
		cube[0][0] = cube[3][2];
		cube[0][1] = cube[3][5];
		cube[0][2] = cube[3][8];
		// right side
		cube[3][2] = cube[5][8];
		cube[3][5] = cube[5][7];
		cube[3][8] = cube[5][6];
		//down side
		cube[5][8] = cube[1][6];
		cube[5][7] = cube[1][3];
		cube[5][6] = cube[1][0];
		// left side
		cube[1][6] = cube0_0;
		cube[1][3] = cube0_1;
		cube[1][0] = cube0_2;


	}  
	private void turn_sideCC(int side_num) {  

		byte cube0_2 =       cube[side_num][2];
		byte cube0_5 =       cube[side_num][5];
		cube[side_num][2] = cube[side_num][8];
		cube[side_num][5] = cube[side_num][7];
		cube[side_num][8] = cube[side_num][6];
		cube[side_num][7] = cube[side_num][3];
		cube[side_num][6] = cube[side_num][0];
		cube[side_num][3] = cube[side_num][1];
		cube[side_num][0] = cube0_2;
		cube[side_num][1] = cube0_5;

	} 
	private void turn_Ui() { 
		turn_sideCC(0);  
		byte cube2_0 = cube[2][0];
		byte cube2_1 = cube[2][1];
		byte cube2_2 = cube[2][2];
		cube[2][0] = cube[1][0];
		cube[2][1] = cube[1][1];
		cube[2][2] = cube[1][2];
		//Left side
		cube[1][0] = cube[4][0];
		cube[1][1] = cube[4][1];
		cube[1][2] = cube[4][2];
		// back side
		cube[4][0] = cube[3][0];
		cube[4][1] = cube[3][1];
		cube[4][2] = cube[3][2];
		// right side
		cube[3][0] = cube2_0;
		cube[3][1] = cube2_1;
		cube[3][2] = cube2_2;
	} 
	private void turn_Fi() { 
		turn_sideCC(  2);
		byte cube0_6 = cube[0][6];
		byte cube0_7 = cube[0][7];
		byte cube0_8 = cube[0][8];
		cube[0][6] = cube[3][0];
		cube[0][7] = cube[3][3];
		cube[0][8] = cube[3][6]; 
		// right side
		cube[3][0] = cube[5][2];
		cube[3][3] = cube[5][1];
		cube[3][6] = cube[5][0]; 
		// down side
		cube[5][2] = cube[1][8];
		cube[5][1] = cube[1][5];
		cube[5][0] = cube[1][2];
		// left side
		cube[1][8] = cube0_6;
		cube[1][5] = cube0_7;
		cube[1][2] = cube0_8; 
	}
	private void turn_Ri() { 
		turn_sideCC(3);	  
		byte cube0_2 = cube[0][2];
		byte cube0_5 = cube[0][5];
		byte cube0_8 = cube[0][8];
		cube[0][2] = cube[4][6];
		cube[0][5] = cube[4][3];
		cube[0][8] = cube[4][0];
		// back side 
		cube[4][6] = cube[5][2];
		cube[4][3] = cube[5][5];
		cube[4][0] = cube[5][8];
		//down side 
		cube[5][2] = cube[2][2];
		cube[5][5] = cube[2][5];
		cube[5][8] = cube[2][8];
		// right side
		cube[2][2] = cube0_2;
		cube[2][5] = cube0_5;
		cube[2][8] = cube0_8;   
	}
	private void turn_Di() { 
		turn_sideCC(5);  
		byte cube2_6 = cube[2][6];
		byte cube2_7 = cube[2][7];
		byte cube2_8 = cube[2][8];
		cube[2][6] = cube[3][6];
		cube[2][7] = cube[3][7];
		cube[2][8] = cube[3][8];
		//right side
		cube[3][6] = cube[4][6];
		cube[3][7] = cube[4][7];
		cube[3][8] = cube[4][8];
		//back side
		cube[4][6] = cube[1][6];
		cube[4][7] = cube[1][7];
		cube[4][8] = cube[1][8];
		//left side
		cube[1][6] = cube2_6;
		cube[1][7] = cube2_7;
		cube[1][8] = cube2_8;
	}
	private void turn_Li() { 
		turn_sideCC(1);  
		byte cube0_0 = cube[0][0];
		byte cube0_3 = cube[0][3];
		byte cube0_6 = cube[0][6];
		cube[0][0] = cube[2][0];
		cube[0][3] = cube[2][3];
		cube[0][6] = cube[2][6];
		//front side
		cube[2][0] = cube[5][0];
		cube[2][3] = cube[5][3];
		cube[2][6] = cube[5][6];
		//down side
		cube[5][0] = cube[4][8];
		cube[5][3] = cube[4][5];
		cube[5][6] = cube[4][2];
		//back side
		cube[4][8] = cube0_0;
		cube[4][5] = cube0_3;
		cube[4][2] = cube0_6;
	} 
	private void turn_Bi() { 
		turn_sideCC(4);
		byte cube0_0 = cube[0][0];
		byte cube0_1 = cube[0][1];
		byte cube0_2 = cube[0][2];
		cube[0][0] = cube[1][6];
		cube[0][1] = cube[1][3];
		cube[0][2] = cube[1][0];
		//left side
		cube[1][6] = cube[5][8];
		cube[1][3] = cube[5][7];
		cube[1][0] = cube[5][6];
		// down side
		cube[5][8] = cube[3][2];
		cube[5][7] = cube[3][5];
		cube[5][6] = cube[3][8];
		// right side
		cube[3][2] = cube0_0;
		cube[3][5] = cube0_1;
		cube[3][8] = cube0_2;   
	}
	private void turn_side2(int side_num) { 

		byte cube0_0 = cube[side_num][0];
		byte cube0_1 = cube[side_num][1];
		byte cube0_2 = cube[side_num][2];
		byte cube0_3 = cube[side_num][3];
		cube[side_num][0] = cube[side_num][8];
		cube[side_num][1] = cube[side_num][7];
		cube[side_num][2] = cube[side_num][6];
		cube[side_num][3] = cube[side_num][5];
		cube[side_num][8] = cube0_0;
		cube[side_num][7] = cube0_1;
		cube[side_num][6] = cube0_2;
		cube[side_num][5] = cube0_3;

	} 
	private void turn_F2()	{
		turn_side2(2);  
		byte cube0_6 = cube[0][6];
		byte cube0_7 = cube[0][7];
		byte cube0_8 = cube[0][8];
		byte cube3_0 = cube[3][0];
		byte cube3_3 = cube[3][3];
		byte cube3_6 = cube[3][6];
		cube[0][6] = cube[5][2];
		cube[0][7] = cube[5][1];
		cube[0][8] = cube[5][0];
		//right side
		cube[3][0] = cube[1][8];
		cube[3][3] = cube[1][5];
		cube[3][6] = cube[1][2];
		//left side
		cube[1][8] = cube3_0;
		cube[1][5] = cube3_3;
		cube[1][2] = cube3_6;
		//yellow side
		cube[5][2] = cube0_6;
		cube[5][1] = cube0_7;
		cube[5][0] = cube0_8;
	}
	private void turn_U2() { 
		turn_side2(0);  
		byte cube2_0 = cube[2][0];
		byte cube2_1 = cube[2][1];
		byte cube2_2 = cube[2][2];
		byte cube1_0 = cube[1][0];
		byte cube1_1 = cube[1][1];
		byte cube1_2 = cube[1][2];
		cube[2][0] = cube[4][0];
		cube[2][1] = cube[4][1];
		cube[2][2] = cube[4][2];
		//left side
		cube[1][0] = cube[3][0];
		cube[1][1] = cube[3][1];
		cube[1][2] = cube[3][2];
		//right side
		cube[3][0] = cube1_0;
		cube[3][1] = cube1_1;
		cube[3][2] = cube1_2;
		//back side
		cube[4][0] = cube2_0; 
		cube[4][1] = cube2_1;
		cube[4][2] = cube2_2;	 
	}
	private void turn_R2() {
		turn_side2(3);
		byte cube0_2 = cube[0][2];
		byte cube0_5 = cube[0][5];
		byte cube0_8 = cube[0][8];
		byte cube2_2 = cube[2][2];
		byte cube2_5 = cube[2][5];
		byte cube2_8 = cube[2][8];
		cube[0][2] = cube[5][2];
		cube[0][5] = cube[5][5];
		cube[0][8] = cube[5][8];
		//front side
		cube[2][2] = cube[4][6];
		cube[2][5] = cube[4][3];
		cube[2][8] = cube[4][0];
		//back side
		cube[4][6] = cube2_2;
		cube[4][3] = cube2_5;
		cube[4][0] = cube2_8;
		// down side
		cube[5][2] = cube0_2;
		cube[5][5] = cube0_5;
		cube[5][8] = cube0_8;
	}
	private void turn_D2() { 
		turn_side2(5);
		byte cube2_6 = cube[2][6];
		byte cube2_7 = cube[2][7];
		byte cube2_8 = cube[2][8];
		byte cube3_6 = cube[3][6];
		byte cube3_7 = cube[3][7];
		byte cube3_8 = cube[3][8];
		cube[2][6] = cube[4][6];
		cube[2][7] = cube[4][7];
		cube[2][8] = cube[4][8];
		// right side
		cube[3][6] = cube[1][6];
		cube[3][7] = cube[1][7];
		cube[3][8] = cube[1][8];
		// left side
		cube[1][6] = cube3_6; 
		cube[1][7] = cube3_7;
		cube[1][8] = cube3_8;
		//back side
		cube[4][6] = cube2_6;
		cube[4][7] = cube2_7;
		cube[4][8] = cube2_8;
	}
	private void turn_B2()	{
		turn_side2(4);	
		byte cube0_0 = cube[0][0];
		byte cube0_1 = cube[0][1];
		byte cube0_2 = cube[0][2];
		byte cube3_2 = cube[3][2];
		byte cube3_5 = cube[3][5];
		byte cube3_8 = cube[3][8];
		// up side
		cube[0][0] = cube[5][8];
		cube[0][1] = cube[5][7];
		cube[0][2] = cube[5][6];
		//right side
		cube[3][2] = cube[1][6];
		cube[3][5] = cube[1][3];
		cube[3][8] = cube[1][0];
		//left side
		cube[1][6] = cube3_2;
		cube[1][3] = cube3_5;
		cube[1][0] = cube3_8;
		//down side
		cube[5][8] = cube0_0; 
		cube[5][7] = cube0_1;
		cube[5][6] = cube0_2;
	}
	private void turn_L2()	{
		turn_side2(1);
		byte cube0_0 = cube[0][0];
		byte cube0_3 = cube[0][3];
		byte cube0_6 = cube[0][6];
		byte cube2_0 = cube[2][0];
		byte cube2_3 = cube[2][3];
		byte cube2_6 = cube[2][6];
		// up side
		cube[0][0] = cube[5][0];
		cube[0][3] = cube[5][3];
		cube[0][6] = cube[5][6];
		// front side
		cube[2][0] = cube[4][8];
		cube[2][3] = cube[4][5];
		cube[2][6] = cube[4][2];
		// back side
		cube[4][8] = cube2_0;
		cube[4][5] = cube2_3;
		cube[4][2] = cube2_6;
		// Down side
		cube[5][0] = cube0_0;
		cube[5][3] = cube0_3;
		cube[5][6] = cube0_6;
	}
	private void print_sides(int first_side, int side_count) {
		char ColorNames [] = {'W','O','G','R','B','Y'};
		for(int line  = 0; line < 3; line++) { 
			for(int side = first_side; side < side_count + first_side; side++) {
				System.out.print(" ");
				if(side_count == 1 || side_count == 6) {
					System.out.print("    ");
				}
				for(int column = 0; column < 3; column++) {
					int sticker = (3 * line) + column;
					System.out.print(ColorNames [cube[side][sticker]]);
				}
			}
			System.out.println();
		}
	}
	void print_cube() {
		print_sides(0, 1);
		print_sides(1, 4);
		print_sides(5, 1);
	} 
}