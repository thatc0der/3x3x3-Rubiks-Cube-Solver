import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;


public class TableGenerator{
		
	public byte [][] cube;

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

		//Avoid running this as it's missing for generating the final phase table
		//Which could take 8 days to finish...
		
		
		/*
		TableGenerator g = new TableGenerator();
		//g.scramble_cube();
		*/
	}		
	
	public int hash_2x2x2(){
		int edgeUF = hash_edge(0, 2);
		int edgeFR = hash_edge(2, 3);
		int edgeUR = hash_edge(0, 3);
		int cornerUFR = hash_corner(0, 3, 2);
		
		if(edgeFR > edgeUF)
			edgeFR -= 2;

		if(edgeUR > edgeUF)
		    edgeUR -= 2;
		
		if(edgeUR > edgeFR)
			edgeUR -= 2;
		
		return ((edgeUF * 22 + edgeFR ) * 20 + edgeUR) * 24 + cornerUFR;
	}
	
	public int hash_1x2x2(){
		int edgeUB = hash_edge(0, 4);
		int edgeRB = hash_edge(3, 4);
		int cornerUBR = hash_corner(0, 4, 3);
		
		if(edgeRB > edgeUB)
			edgeRB -= 2;
		
		return (edgeUB * 16 + edgeRB) * 21 + cornerUBR;
	}
	public int hash_1x2x2b() {
		int edgeBL = hash_edge(1,4);
		int edgeUL = hash_edge(0,1);
		int cornerUBL = hash_corner(0, 1, 4);
		
		if(edgeBL > edgeUL)
			edgeBL -= 2;
		return (edgeUL * 12 + edgeBL) * 18 + cornerUBL;
	}
	

	 
	
	public int hash_2x2x3(){
		int edgeUF = hash_edge(0,2);
		int edgeFR = hash_edge(2,3);
		int edgeUR = hash_edge(0,3);
		int edgeUB = hash_edge(0,4);
		int edgeRB = hash_edge(3,4);
		int cornerURF = hash_corner(0,3,2);
		int cornerUBR = hash_corner(0,4,3);
		
		if(edgeFR > edgeUF)
			edgeFR -= 2;
		if(edgeUR > edgeUF)
			edgeUR -= 2;
		if(edgeUB > edgeUF)
			edgeUB -= 2;
		if(edgeRB > edgeUF)
			edgeRB -= 2;
		
		if(edgeUR > edgeFR)
			edgeUR -= 2;
		if(edgeUB > edgeFR)
			edgeUB -= 2;
		if(edgeRB > edgeFR)
			edgeRB -= 2;
		
		if(edgeUB > edgeUR)
			edgeUB -= 2;
		if(edgeRB > edgeUR)
			edgeRB -= 2;
		
		if(edgeRB > edgeUB)
			edgeRB -= 2;

		if(cornerUBR > cornerURF)
			cornerUBR -= 3;
		
		return (((((edgeUF * 22 + edgeFR) * 20 + edgeUR) * 18 + edgeUB) * 16 + edgeRB) * 24 + cornerURF) * 21 + cornerUBR;
	}
	
	//This is irrelevant if table is generated
	private int hash_2x2x3A(){
		int edgeUR = hash_edgeA(0,3);
		int edgeRB = hash_edgeA(3,4);
		int edgeUB = hash_edgeA(0,4);
		int edgeUL = hash_edgeA(0,1);
		int edgeBL = hash_edgeA(4,1);
		int cornerUBR = hash_cornerA(0,4,3);
		int cornerULB = hash_cornerA(0,1,4);
		
		
		if(edgeRB > edgeUR)
			edgeRB -= 2;
		if(edgeUB > edgeUR)
			edgeUB -= 2;
		if(edgeUL > edgeUR)
			edgeUL -= 2;
		if(edgeBL > edgeUR)
			edgeBL -= 2;
		
		if(edgeUB > edgeRB)
			edgeUB -= 2;
		if(edgeUL > edgeRB)
			edgeUL -= 2;
		if(edgeBL > edgeRB)
			edgeBL -= 2;
		
		if(edgeUL > edgeUB)
			edgeUL -= 2;
		if(edgeBL > edgeUB)
			edgeBL -= 2;
		
		if(edgeBL > edgeUL)
			edgeBL -= 2;
		
		if(cornerULB > cornerUBR)
			cornerULB -= 3;
		
		return (((((edgeUR * 22 + edgeRB) * 20 + edgeUB) * 18 + edgeUL) * 16 + edgeBL) * 24 + cornerUBR) * 21 + cornerULB;

	}
	public int hash_SlotandLastLayer(){
		
		int edgeBD = hash_edge(4,5);
		int edgeRD = hash_edge(3,5);
		int edgeFD = hash_edge(2,5);
		int edgeLD = hash_edge(1,5);
		
		
		int cornerBLD = hash_corner(1,5,4);
		int cornerRDB = hash_corner(3,4,5);
		int cornerRFD = hash_corner(2,3,5);
		int cornerFLD = hash_corner(2,5,1);
		

		if(edgeRD > edgeBD)
			edgeRD -= 2;
		if(edgeFD > edgeBD)
			edgeFD -= 2;
		if(edgeLD > edgeBD)
			edgeLD -= 2;
		
		if(edgeFD > edgeRD)
			edgeFD -= 2;
		if(edgeLD > edgeRD)
			edgeLD -= 2;
		
		
		if(edgeLD > edgeFD)
			edgeLD -= 2;
		//special case for last edge
		if(edgeLD >= 2) 
			edgeLD -= 2;
		
	
		if(cornerRDB > cornerBLD)
			cornerRDB -= 3;
		if(cornerRFD > cornerBLD)
			cornerRFD -= 3;
		if(cornerFLD > cornerBLD)
			cornerFLD -= 3;
		
		
		if(cornerRFD > cornerRDB)
			cornerRFD -= 3;
		if(cornerFLD > cornerRDB)
			cornerFLD -= 3;
		
		
		if(cornerFLD > cornerRFD)
			cornerFLD -= 3;
		
		
		return ((((((edgeBD * 8 + edgeRD) * 6  + edgeFD) *  2  + edgeLD) *15  + cornerBLD) * 12 + cornerRDB) * 9 + cornerRFD )* 6 + cornerFLD;
		
	}

	/*
	 * STAGE: 4
		1 3 4
		3 6 10
		4 38 48
		5 76 124
		6 166 290
		7 900 1190
		8 3142 4332
		9 9917 14249
		10 37469 51718
		11 143449 195167
		12 505521 700688
		13 1610728 2311416
		14 3632982 5944398
		15 3093237 9037635
		16 293435 9331070
		17 130 9331200
	 */
	
	private void formatTable() throws FileNotFoundException{
		String movements = "";
		String finalString = "";
		String fileName = "finalPhase.txt";
		
		PrintWriter outputStream = new PrintWriter(fileName);

		for(int nodes = 0; nodes < foundStates.length; nodes++){
			if(foundStates[nodes] != null){
					movements = formatString(foundStates[nodes]);
					while(movements.length() < 49)
						movements += "NP ";
					movements += "\n";
					
					finalString = nodes + " " + movements;
					while(finalString.length() < 61)
						finalString = "0" + finalString;
					
					outputStream.print(finalString);
				}	
			}
		outputStream.close();
	}
	
	private String formatString(byte[] currentNode){
		String[] turnNames = {"U  ", "U2 ", "Ui ", "L  ", "L2 ", "Li ", "F  ", "F2 ", "Fi ",
                "R  ", "R2 ", "Ri ", "B  ", "B2 ", "Bi ", "D  ", "D2 ", "Di "};
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

	
	
	private void startSearch(TableGenerator g , final int maxDepth) {
		System.out.println("Phase 4");
		
		
		Path fileName = Paths.get("pruneTable.txt");
		try {
			pruneTable = Files.readAllBytes(fileName);
			System.out.println("File Read! ");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		float startTime = System.nanoTime();

	 
		foundStates = new byte[93312000][];
	    
		int foundSoFar = 0;
		for(int targetDepth = 0; targetDepth <= maxDepth; targetDepth++){
            Thread [] threads = new Thread[18];
            for(int i = 0; i < threads.length; i++){
                final int currDepth = targetDepth;
                final TableGenerator threadg = new TableGenerator();                
                threadg.threadIndex = i;
                threadg.allTurns = new byte[currDepth];
                threadg.pruneTable = pruneTable;
                threadg.foundStates = foundStates;
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
		    System.out.printf("%d: %d : %d\n", targetDepth, newThisDepth, foundSoFar);
		    float stopTime = (System.nanoTime()- startTime)/ 1000000000;
			System.out.println(stopTime/60 + " minutes");    
		}
		
		System.out.println("Done m8 :) ");
		
		
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
			if(turn.checkPhase1() == true && turn.checkPhase2() == true && turn.checkPhase3() == true){
				if(foundStates[turn.hashCode()] == null) {
		            foundStates[turn.hashCode()] = copyTurns(allTurns);
		        }
			}
	   		
 			} else {
				int newDepth = depth + 1;
				
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
				int tableStateValueA = turn.hash_2x2x3A();
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
	
	
	private boolean checkPhase1(){
		if(cube[0][5] == 0 && cube[0][7] == 0 && cube[0][8] == 0 && 
		   cube[2][1] == 2 && cube[2][2] == 2 && cube[2][5] == 2 && 
		   cube[3][0] == 3 && cube[3][1] == 3 && cube[3][3] == 3)
			return true;
		else 
		   return false;
	}
	private boolean checkPhase2(){
		if(cube[4][0] == 4 && cube[4][1] == 4 && cube[4][3] == 4 &&
		   cube[0][1] == 0 && cube[0][2] == 0 && 
		   cube[3][2] == 3 && cube[3][5] == 3)
			return true;
		else 
			return false;
	}
	private boolean checkPhase3(){
		if(cube[1][0] == 1 && cube[1][1] == 1 && cube[1][3] == 1 &&
		   cube[4][2] == 4 && cube[4][5] == 4 &&
		   cube[0][0] == 0 && cube[0][3] == 0)
			return true;
		else 
			return false;
	}

	public boolean equals(Object other) {
		return hash_SlotandLastLayer() == ((TableGenerator) other).hash_SlotandLastLayer();
	}
	@Override
	public int hashCode() {
		return hash_SlotandLastLayer();
	}
	@Override
	public String toString() {
		String acc = "" + hash_SlotandLastLayer() + "";
		
		while(acc.length() < 8 )
			acc = "0" + acc;
		
		return acc;
	}
	
	
	private int hash_edgeA(int firstColor, int secondColor){
		int counter = 0;
		//first edge FR
		if(firstColor != cube[2][5] || secondColor != cube[3][3]){
			counter++;
			if(firstColor != cube[3][3] || secondColor != cube[2][5]){
				counter++;
				//second edge FD
				if(firstColor != cube[2][7] || secondColor != cube[5][1]){
					counter++;
					if(firstColor != cube[5][1] || secondColor != cube[2][7]){
						counter++;
						//third edge RD
						if(firstColor != cube[3][7] || secondColor != cube[5][5]){
							counter++;
							if(firstColor != cube[5][5] || secondColor != cube[3][7]){
								counter++;
								//fourth edge BD
								if(firstColor != cube[4][7] || secondColor != cube[5][7]){
									counter++;
									if(firstColor != cube[5][7] || secondColor != cube[4][7]){
										counter++;
										//fifth edge LD
										if(firstColor != cube[1][7] || secondColor != cube[5][3]){
											counter++;
											if(firstColor != cube[5][3] || secondColor != cube[1][7]){
												counter++;
												//sixth edge FL
												if(firstColor != cube[2][3] || secondColor != cube[1][5]){
													counter++;
													if(firstColor != cube[1][5] || secondColor != cube[2][3]){
														counter++;
														//seventh edge UF
														if(firstColor != cube[0][7] || secondColor != cube[2][1]){
															counter++;
															if(firstColor != cube[2][1] || secondColor != cube[0][7]){
																counter++;
																//eighth edge BL
																if(firstColor != cube[4][5] || secondColor != cube[1][3]){
																	counter++;
																	if(firstColor != cube[1][3] || secondColor != cube[4][5]){
																		counter++;
																		//ninth edge UL
																		if(firstColor != cube[0][3] || secondColor != cube[1][1]){
																				counter++;
																		    if(firstColor != cube[1][1] || secondColor != cube[0][3]){
																			    counter++;
																				//tenth edge UR
																				if(firstColor != cube[0][5] || secondColor != cube[3][1]){
																					counter++;
																					if(firstColor != cube[3][1] || secondColor != cube[0][5]){
																						counter++;
																						//eleventh edge RB
																						if(firstColor != cube[4][3] || secondColor != cube[3][5]){
																							counter++;
																							if(firstColor != cube[3][5] || secondColor != cube[4][3]){
																								counter++;
																								//twelfth edge UB
																								if(firstColor != cube[0][1] || secondColor != cube[4][1]){
																									counter++;
																									if(firstColor != cube[4][1] || secondColor != cube[0][1]){
																										counter++;
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return counter; 
	}
	private int hash_cornerA(int firstColor, int secondColor, int thirdColor){
		int counter = 0;
		// First Corner URF
		if (firstColor != cube[0][8] || secondColor != cube[3][0] || thirdColor != cube[2][2]) {
			counter++;
			if (firstColor != cube[3][0] || secondColor != cube[2][2] || thirdColor != cube[0][8]) {
				counter++;
				if (firstColor != cube[2][2] || secondColor != cube[0][8] || thirdColor != cube[3][0]) {
					counter++;
	    				//Second Corner FRD
		    			if(firstColor != cube[5][2]  ||  secondColor != cube[2][8] || thirdColor != cube[3][6]){
		    				counter++;
		    				if(firstColor != cube[2][8]  ||  secondColor != cube[3][6] || thirdColor != cube[5][2]){
		    					counter++;
		    					if(firstColor != cube[3][6]  ||  secondColor != cube[5][2] || thirdColor != cube[2][8]){
		    						counter++;
		    						//Third Corner BRD
		    						if(firstColor != cube[3][8]  ||  secondColor != cube[4][6] || thirdColor != cube[5][8]){
		    							counter++;
		    							if(firstColor != cube[4][6]  ||  secondColor != cube[5][8] || thirdColor != cube[3][8]){
		    								counter++;
		    								if(firstColor != cube[5][8]  ||  secondColor != cube[3][8] || thirdColor != cube[4][6]){
		    									counter++;
	    										//Fourth Corner BLD
		    									if(firstColor != cube[5][6]  ||  secondColor != cube[4][8] || thirdColor != cube[1][6]){
									        		counter++;
									        		if(firstColor != cube[4][8]  ||  secondColor != cube[1][6] || thirdColor != cube[5][6]){
									        			counter++;
									        			if(firstColor != cube[1][6]  ||  secondColor != cube[5][6] || thirdColor != cube[4][8]){
									        				counter++;
									        				//Fifth Corner FLD
									        				if(firstColor != cube[5][0]  ||  secondColor != cube[1][8] || thirdColor != cube[2][6]){
									        					counter++;
	    														if(firstColor != cube[1][8]  ||  secondColor != cube[2][6] || thirdColor != cube[5][0]){
	    															counter++;
	    															if(firstColor != cube[2][6]  ||  secondColor != cube[5][0] || thirdColor != cube[1][8]){
    																	counter++;
    																	//Sixth Corner ULF
    																	if(firstColor != cube[0][6]  ||  secondColor != cube[2][0] || thirdColor != cube[1][2]){
    																		counter++;
    																		if(firstColor != cube[2][0]  ||  secondColor != cube[1][2] || thirdColor != cube[0][6]){
    																			counter++;
    																			if(firstColor != cube[1][2]  ||  secondColor != cube[0][6] || thirdColor != cube[2][0]){
    																				counter++;
    																				//Seventh Corner ULB
    																				if(firstColor != cube[0][0] ||  secondColor !=  cube[1][0] || thirdColor != cube[4][2]){
    																					counter++;
    																					if( firstColor != cube[1][0]  ||  secondColor != cube[4][2] || thirdColor != cube[0][0]){
    																						counter++;
    																						if(firstColor != cube[4][2]  ||  secondColor != cube[0][0] || thirdColor != cube[1][0]){ 
    																							counter++;
    																							//Eigith Corner URB
    																							if(firstColor != cube[0][2]  ||  secondColor != cube[4][0] || thirdColor != cube[3][2]){
    																								counter++;
    																								if(firstColor != cube[4][0]  ||  secondColor != cube[3][2] || thirdColor != cube[0][2]){
    																									counter++;
    																									if(firstColor != cube[3][2]  ||  secondColor != cube[0][2] || thirdColor != cube[4][0]){
    																										counter++;
    																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return counter;
	}
	private int hash_edge(int firstColor, int secondColor){
		int counter = 0;
		//first edge LF
		if(firstColor != cube[1][5] || secondColor != cube[2][3]){
			counter++;
			if(firstColor != cube[2][3] || secondColor != cube[1][5]){
				counter++;
				//second edge  LD
				if(firstColor != cube[1][7] || secondColor != cube[5][3]){
					counter++;
					if(firstColor != cube[5][3] || secondColor != cube[1][7]){
						counter++;
						//third edge  FD
						if(firstColor != cube[2][7] || secondColor != cube[5][1]){
							counter++;
							if(firstColor != cube[5][1] || secondColor != cube[2][7]){
								counter++;
								//fourth edge  RD
								if(firstColor != cube[3][7] || secondColor != cube[5][5]){
									counter++;
									if(firstColor != cube[5][5] || secondColor != cube[3][7]){
										counter++;
										//fifth edge  BD
										if(firstColor != cube[4][7] || secondColor != cube[5][7]){
											counter++;
											if(firstColor != cube[5][7] || secondColor != cube[4][7]){
												counter++;
												//sixth edge  LB  PHASE 3 BELOW
												if(firstColor != cube[1][3] || secondColor != cube[4][5]){
													counter++;
													if(firstColor != cube[4][5] || secondColor != cube[1][3]){
														counter++;
														//seventh edge UL
														if(firstColor != cube[0][3] || secondColor != cube[1][1]){
															counter++;
															if(firstColor != cube[1][1] || secondColor != cube[0][3]){
																counter++;
																//eighth edge RB  PHASE 2 BELOW
																if(firstColor != cube[3][5] || secondColor != cube[4][3]){
																	counter++;
																	if(firstColor != cube[4][3] || secondColor != cube[3][5]){
																		counter++;
																		//ninth edge UB  
																		if(firstColor != cube[0][1] || secondColor != cube[4][1]){
																			counter++;
																			if(firstColor != cube[4][1] || secondColor != cube[0][1]){
																				counter++;
																				//tenth edge UF   FIRST PHASE EDGES BELOW
																				if(firstColor != cube[0][7] || secondColor != cube[2][1]){
																					counter++;
																					if(firstColor != cube[2][1] || secondColor != cube[0][7]){
																						counter++;
																						//eleventh edge RF   
																						if(firstColor != cube[3][3] || secondColor != cube[2][5]){
																							counter++;
																							if(firstColor != cube[2][5] || secondColor != cube[3][3]){
																								counter++;
																								//twelfth edge UR
																								if(firstColor != cube[0][5] || secondColor != cube[3][1]){
																									counter++;
																									if(firstColor != cube[3][1] || secondColor != cube[0][5]){
																										counter++;
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return counter;	
	}
	private int hash_corner(int firstColor, int secondColor, int thirdColor){
		int counter = 0;
		//First Corner ULF
		if(firstColor != cube[0][6]  ||  secondColor != cube[2][0] || thirdColor != cube[1][2]){
			counter++;
			if(firstColor != cube[2][0]  ||  secondColor != cube[1][2] || thirdColor != cube[0][6]){
				counter++;
				if(firstColor != cube[1][2]  ||  secondColor != cube[0][6] || thirdColor != cube[2][0]){
					counter++;
					//Second Corner  FLD
					if(firstColor != cube[5][0]  ||  secondColor != cube[1][8] || thirdColor != cube[2][6]){
						counter++;
						if(firstColor != cube[1][8]  ||  secondColor != cube[2][6] || thirdColor != cube[5][0]){
							counter++;
							if(firstColor != cube[2][6]  ||  secondColor != cube[5][0] || thirdColor != cube[1][8]){
								counter++;
								//Third Corner  FRD
								if(firstColor != cube[2][8]  ||  secondColor != cube[3][6] || thirdColor != cube[5][2]){
									counter++;
									if(firstColor != cube[3][6]  ||  secondColor != cube[5][2] || thirdColor != cube[2][8]){
										counter++;
										if(firstColor != cube[5][2]  ||  secondColor != cube[2][8] || thirdColor != cube[3][6]){
											counter++;
											//Fourth Corner   BRD
											if(firstColor != cube[5][8]  ||  secondColor != cube[3][8] || thirdColor != cube[4][6]){
												counter++;
												if(firstColor != cube[3][8]  ||  secondColor != cube[4][6] || thirdColor != cube[5][8]){
													counter++;
													if(firstColor != cube[4][6]  ||  secondColor != cube[5][8] || thirdColor != cube[3][8]){
														counter++;
														//Fifth Corner  BLD
														if(firstColor != cube[5][6]  ||  secondColor != cube[4][8] || thirdColor != cube[1][6]){
															counter++;
															if(firstColor != cube[4][8]  ||  secondColor != cube[1][6] || thirdColor != cube[5][6]){
																counter++;
																if(firstColor != cube[1][6]  ||  secondColor != cube[5][6] || thirdColor != cube[4][8]){
																	counter++;
																	//Sixth Corner  ULB
																	if(firstColor != cube[0][0] ||  secondColor !=  cube[1][0] || thirdColor != cube[4][2]){
																		counter++;
																		if( firstColor != cube[1][0]  ||  secondColor != cube[4][2] || thirdColor != cube[0][0]){
																			counter++;
																			if(firstColor != cube[4][2]  ||  secondColor != cube[0][0] || thirdColor != cube[1][0]){ 
																				counter++;
																				//Seventh Corner  URB
																				if(firstColor != cube[0][2]  ||  secondColor != cube[4][0] || thirdColor != cube[3][2]){
																					counter++;
																					if(firstColor != cube[4][0]  ||  secondColor != cube[3][2] || thirdColor != cube[0][2]){
																						counter++;
																						if(firstColor != cube[3][2]  ||  secondColor != cube[0][2] || thirdColor != cube[4][0]){
																							counter++;
																							//Eighth Corner   UFR
																							if(firstColor != cube[0][8]  ||  secondColor != cube[3][0] || thirdColor != cube[2][2]){
																								counter++;
																								if(firstColor != cube[3][0]  ||  secondColor != cube[2][2] || thirdColor != cube[0][8]){
																									counter++;
																									if(firstColor != cube[2][2]  ||  secondColor != cube[0][8] || thirdColor != cube[3][0]){
																										counter++;
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return counter ;
	}
	
	
	 String scramble_cube() {
		Random rand = new Random();
		String scrambleStr = "";
		for (int scramble = 0; scramble < 30; scramble++) {
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
