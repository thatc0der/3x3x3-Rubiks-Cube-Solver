import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DisplayWindow {

	final int SQUARE_SIZE = 50;
	JButton[] buttons = new JButton[54];
	Color[] updateColors = new Color[54];
	JTextField photoStatus = new JTextField(){
	    @Override public void setBorder(Border border) {
	        //Remove ugly background
	    }
	};
	
	JTextField showSolution = new JTextField(){
		@Override public void setBorder(Border border) {
        // Remove ugly background
		}
	};

	private void layDownButtons(JButton [] allButtons, int column , int startingButton, int endingButton, int yLevel){
		int [] allXCoordinates = {20,80,140,200,260,320,380,440,500,560,620,680};	
		int [] allYCoordinates = {50,110,170,230,290,350,410,470,530};
		int currButton = column;
		for(int i = startingButton; i < endingButton;i++){
			allButtons[i].setBounds(allXCoordinates[currButton], allYCoordinates[yLevel], SQUARE_SIZE, SQUARE_SIZE);
			currButton++;
			if(i == startingButton + 2 || i == endingButton - 4){
				yLevel++;
				currButton = column;
			}
		}	
	}
	
	public void allocateButtons(){
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new JButton();
		}
	}
	
	public void displayCube(){
		JFrame frame = new JFrame("cube display frame");
		frame.setTitle("Rubiks cube window");
		frame.setBounds(750,150,800,700);
    	
    	JPanel panel = new JPanel();
		panel.setLayout(null);
				
		
		allocateButtons();
		
		String[] sideLabels = {"0","1","2","3","4","5"};
		
		buttons[4].setText(sideLabels[0]);
		buttons[13].setText(sideLabels[1]);
		buttons[22].setText(sideLabels[2]);
		buttons[31].setText(sideLabels[3]);
		buttons[40].setText(sideLabels[4]);
		buttons[49].setText(sideLabels[5]);
		
		
		//top
		layDownButtons(buttons,3,0,9,0);
		//left
		layDownButtons(buttons, 0, 9, 18, 3);
		//front
		layDownButtons(buttons, 3, 18, 27, 3);
		//right
		layDownButtons(buttons, 6, 27, 36, 3);
		//back
		layDownButtons(buttons, 9, 36, 45, 3);
		//bottom
		layDownButtons(buttons, 3, 45, 54, 6);

		for(int i = 0; i < buttons.length; i++){
			panel.add(buttons[i]);
		}
		
		showSolution.setBackground(Color.BLACK);
		showSolution.setFont(new Font("Monaco", Font.BOLD, 16));
		showSolution.setText("No solution:");
		showSolution.setBounds(50, 600, 750, 20);
		showSolution.setOpaque(false);
		
		photoStatus.setBackground(Color.GREEN);
		photoStatus.setFont(new Font("Monaco", Font.BOLD, 18));
		photoStatus.setText("Welcome to the cube solver! Please start capturing you cube.");
		photoStatus.setBounds(100, 10, 700, 20);
		photoStatus.setOpaque(false);
		
		
		frame.add(showSolution);
		frame.add(photoStatus);
		
		frame.add(panel);
    	frame.setVisible(true);
	}	
	
	
	public void setColor(Color color, JButton currButton){
		currButton.setBackground(color);
	}
}