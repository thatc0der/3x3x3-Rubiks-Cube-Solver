import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyFrame extends JFrame {
	private JPanel contentPane;

	  /**
	  * Launch the application.
	  */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    MyFrame frame = new MyFrame();
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
	    public MyFrame() {
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(550, 250, 650, 600);
	        contentPane = new JPanel();
	        contentPane.setFocusable(true);
	        contentPane.requestFocusInWindow();
	        contentPane.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_SPACE){
						System.out.println("Pressed");
						videoCap.captured = true;
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_A){
						System.out.println("Pressed");
						videoCap.captured = true;
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