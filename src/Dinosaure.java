import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PSurface;

public class Dinosaure extends PApplet{

	public static final int WINDOW_HEIGHT = 300;
	public static final int WINDOW_WIDTH = 800;
	
	public static final int PIXELS_FROM_PLAYER_TO_BORDER = 100;
	
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

    public void draw(){	
    	
    	int drawSurface_width = frame.getContentPane().getSize().width;
    	int drawSurface_height = frame.getContentPane().getSize().height;
    	
    	
    	surface.setSize(drawSurface_width, drawSurface_height);
        //background(0);
        //ellipse(mouseX, mouseY, 20, 20);
        background(100);
        
        // Drawing parameters
        int horizon_line = drawSurface_height - 80;
        int player_X_position = 50;
        int player_Y_position = drawSurface_height - 80;
        
        // Draw horizon
        line(-10, horizon_line, drawSurface_width + 10, horizon_line);
        
        // Draw clouds
        for (MovingElement element : m_gameHandler.m_cloudQueue) {
        	int cloud_X_position = (int) Math.floor(element.get_xPosition() - m_gameHandler.m_currentXPosition);
        	int cloud_Y_position = drawSurface_height - 100;
        	rect(cloud_X_position - 10, cloud_Y_position - 10, 20, 20);
        }
        
        // Draw obstacles
        // TODO
        
        // Draw player
        ellipse(player_X_position, player_Y_position, 20, 60);

    }
    
    public static void main(String[] args){
        new Dinosaure();
    }

}