package Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MovingElements.MovingElement;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PSurface;

public class Dinosaure extends PApplet{

	public static final int WINDOW_HEIGHT = 300;
	public static final int WINDOW_WIDTH = 800;
	
	public static final int PIXELS_FROM_PLAYER_TO_BORDER = 100;
	
	// Update period
	public static final int PLAYER_REFRESH_PERIOD_MILLISECONDS = 5;
	public static final int OBJECT_SPAWN_PERIOD_MILLISECONDS = 10; // in milliseconds
		
	// Margins outside window
	public static final double X_MARGIN = 20;
	
	
	// Game
	GameHandler m_gameHandler;
	
	// Containers
	JFrame frame;
	JPanel drawPanel;
	PSurface ps;

	public Dinosaure() {
		
		// Create GameHandler
		m_gameHandler = new GameHandler();
		m_gameHandler.set_horizonX(WINDOW_WIDTH);
		
		// Setup frame
		frame = new JFrame("JFrame Test");
		
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//frame.setMinimumSize(new Dimension(200,200));
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawPanel = new JPanel();
		frame.add(drawPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(new JLabel("salut"), BorderLayout.CENTER);
		
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {
			boolean isStopped = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isStopped) {
					ps.resumeThread();
					isStopped = false;
					return;
				}
				ps.pauseThread();
				isStopped = true;
			}
			
		});
		bottomPanel.add(stopButton, BorderLayout.EAST);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		
		ps = initSurface();
		ps.setSize(200, 200);
		SmoothCanvas smoothCanvas = (SmoothCanvas)ps.getNative();
		drawPanel.add(smoothCanvas);
		ps.startThread();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// Run game
		m_gameHandler.startRoutine();
		
	}
	
	public void setup() {
		
	}
	
    public void settings(){
        size(drawPanel.getSize().width, frame.getContentPane().getSize().height);
        //surface.setResizable(true);
    }
    
    public int getDrawSurface_width() {
    	return frame.getContentPane().getSize().width;
    }
    
    public int getDrawSurface_height() {
    	return frame.getContentPane().getSize().height;
    }

    public void draw(){	
    	
    	surface.setSize(getDrawSurface_width(), getDrawSurface_height());
        //background(0);
        //ellipse(mouseX, mouseY, 20, 20);
        background(100);
        
        // Drawing parameters
        int horizon_line = getDrawSurface_height() - 80;
        int player_X_position = 50;
        int player_Y_position = getDrawSurface_height() - 80;
        
        // Draw horizon
        line(-10, horizon_line, getDrawSurface_width() + 10, horizon_line);
        
        // Draw queues
        for (GameHandler.ElementQueue queue : m_gameHandler.m_queues) {
	        for (MovingElement element : queue) {
				element.drawElement(this, m_gameHandler.m_currentXPosition);
			}
        }
        
        // Draw player
        ellipse(player_X_position, player_Y_position, 20, 60);

    }
    
    public static void main(String[] args){
        new Dinosaure();
    }

}