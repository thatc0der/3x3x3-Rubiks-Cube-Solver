import java.awt.EventQueue;
import java.awt.Graphics;

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
	        setBounds(300, 250, 650, 490);
	        contentPane = new JPanel();
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
	        public void run() {
	            for (;;){
	                repaint();
	                try { Thread.sleep(30);
	                } catch (InterruptedException e) {    }
	            }  
	        } 
	    }
}