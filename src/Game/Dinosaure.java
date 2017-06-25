package Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import MovingElements.MovingElement;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PSurface;

public class Dinosaure extends PApplet {

	public static final int WINDOW_HEIGHT = 300;
	public static final int WINDOW_WIDTH = 800;
	
	public static final int PIXELS_FROM_PLAYER_TO_BORDER = 100;
	
	// Update period
	public static final int PLAYER_REFRESH_PERIOD_MILLISECONDS = 2;
	public static final int OBJECT_SPAWN_PERIOD_MILLISECONDS = 50; // in milliseconds
		
	// Margins outside window
	public static final double X_MARGIN = 20;
	
	public static final int HORIZON_LINE_HEIGHT = 80;
	
	public static final double PLAYER_INITIAL_VELOCITY = 100.0;
	public static final double PLAYER_WEIGHT = 90.0;
	public static final double PLAYER_JUMP_VELOCITY = 350.0;
	
	
	// Game
	GameHandler m_gameHandler;
	
//	// Containers
//	JFrame frame;
//	JPanel drawPanel;
//	PSurface ps;

	public Dinosaure() {
		
		// Create GameHandler
		m_gameHandler = new GameHandler();
		m_gameHandler.set_horizonX(WINDOW_WIDTH);
		
//		// Setup frame
//		frame = new JFrame("JFrame Test");
//		
//		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//		//frame.setMinimumSize(new Dimension(200,200));
//		frame.setLayout(new BorderLayout());
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		drawPanel = new JPanel();
//		frame.add(drawPanel, BorderLayout.CENTER);
//		
//		JPanel bottomPanel = new JPanel(new BorderLayout());
//		bottomPanel.add(new JLabel("salut"), BorderLayout.CENTER);
//		
//		JButton stopButton = new JButton("STOP");
//		stopButton.addActionListener(new ActionListener() {
//			boolean isStopped = false;
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if (isStopped) {
//					ps.resumeThread();
//					isStopped = false;
//					return;
//				}
//				ps.pauseThread();
//				isStopped = true;
//			}
//			
//		});
//		//bottomPanel.add(stopButton, BorderLayout.EAST);	
//		frame.add(bottomPanel,BorderLayout.SOUTH);
//		
//		ps = initSurface();
//		ps.setSize(200, 200);
//		SmoothCanvas smoothCanvas = (SmoothCanvas)ps.getNative();
//		drawPanel.add(smoothCanvas);
//		ps.startThread();
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		drawPanel.setFocusable(false);
		
		// Run game
		m_gameHandler.startRoutine();
	}
	
	public void setup() {
		
	}
	
    public void settings(){
        //size(drawPanel.getSize().width, frame.getContentPane().getSize().height);
        size(getDrawSurface_width(), getDrawSurface_height());
        //surface.setResizable(true);
    }
    
    public int getDrawSurface_width() {
    	return WINDOW_WIDTH; //frame.getContentPane().getSize().width;
    }
    
    public int getDrawSurface_height() {
    	return WINDOW_HEIGHT; //
    }

    public void draw(){	
    	
    	//surface.setSize(getDrawSurface_width(), getDrawSurface_height());
        //ellipse(mouseX, mouseY, 20, 20);
        background(100);
                
        
        // Draw horizon
        int horizon_line = getDrawSurface_height() - HORIZON_LINE_HEIGHT;
        line(-10, horizon_line, getDrawSurface_width() + 10, horizon_line);
        
        // Draw queues
        for (GameHandler.ElementQueue queue : m_gameHandler.m_queues) {
	        for (MovingElement element : queue) {
				element.drawElement(this, m_gameHandler.get_currentXPosition());
			}
        }
        
        // Draw player
        m_gameHandler.m_player.drawElement(this, m_gameHandler.get_currentXPosition());

    }
    
    public void keyPressed() {
    	//System.out.println("Key pressed: " + key);
    	if (key == ' ' && m_gameHandler.m_player.get_yPosition() == 0) {
    		m_gameHandler.m_player.set_yVelocity(PLAYER_JUMP_VELOCITY);
    	}
    }
    
    public static void main(String[] args){
    	PApplet.main("Game.Dinosaure");
        //new Dinosaure();
    }

}