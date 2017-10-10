import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	private JPanel contentPane;

	DisplayWindow updateButtons = new DisplayWindow();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainFrame frame = new MainFrame();
					frame.runMainTasks();
					frame.callDisplayWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */    

	public void callDisplayWindow(){
		updateButtons.displayCube();
	//	updateButtons.showSolution.setForeground(Color.BLACK);
	}

	
	public void runMainTasks(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 150, 650, 490);
		contentPane = new JPanel();
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();

		contentPane.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				//To capture side press SPACE
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					videoCap.captured = true;
					videoCap.getOneFrame();
					if(videoCap.takeFrame.successfulDetection == true){
						updateButtons.photoStatus.setForeground(Color.BLUE);  
						updateButtons.photoStatus.setText(videoCap.takeFrame.updateWindowText);
						updateButtons.photoStatus.setFont(new Font("Monaco", Font.BOLD,14));
					} 
					else if(videoCap.takeFrame.successfulDetection == false){
						updateButtons.photoStatus.setForeground(Color.RED);
						updateButtons.photoStatus.setText(videoCap.takeFrame.updateWindowText);
						updateButtons.photoStatus.setFont(new Font("Monaco", Font.BOLD,14));
					}
					for(int i = 0; i < videoCap.takeFrame.allColors.length;  i++){
						updateButtons.setColor(videoCap.takeFrame.allColors[i], updateButtons.buttons[i]);
					}
					
					if(videoCap.takeFrame.completed == true){
						updateButtons.showSolution.setForeground(Color.GREEN);
						updateButtons.showSolution.setText("solution: "+videoCap.takeFrame.fetchedSolution);
						updateButtons.showSolution.setFont(new Font("Monaco", Font.BOLD,16));
					}
				}
				
				//PRESS "R" to reset!
				else if(e.getKeyCode() == KeyEvent.VK_R){
					AnalyzeFrame g = new AnalyzeFrame();
					g.colorArray = new Color[54];
					g.currentIndex = 0;
					for(int i = 0; i < 300; i++){
						System.out.println();
					}
					System.out.println("RESET");
					videoCap.takeFrame = new AnalyzeFrame();
					//LOOK at the new object declaration
				} else if(e.getKeyCode() == KeyEvent.VK_X){
					System.out.println("quit application");
					System.exit(0);
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		new MyThread().start();
	}


	VideoCap videoCap = new VideoCap();

	//repaint the whole window for latest frames
	public void paint(Graphics g){
		g = contentPane.getGraphics();
		g.drawImage(videoCap.getOneFrame(), 0, 0, this);
	}


	class MyThread extends Thread{
		@Override
		public void run(){
			for (;;){
				repaint(); 
				try { Thread.sleep(30);
				} catch (InterruptedException e) {    }
			}  
		} 
	}
}