import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	private JPanel contentPane;

	  /**
	  * Launch the application.
	  */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    MainFrame frame = new MainFrame();
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
	    public MainFrame() {
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(330, 150, 650, 490);
	        contentPane = new JPanel();
	        contentPane.setFocusable(true);
	        contentPane.requestFocusInWindow();
	        
	        contentPane.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_SPACE){
						System.out.println("captured!");
						videoCap.captured = true;
					}
					else if(e.getKeyCode() == KeyEvent.VK_R){
						AnalyzeFrame g = new AnalyzeFrame();
						g.colorArray = new Color[54];
						g.currentIndex = 0;
						System.out.println("RESET");
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