/*
 * Created by 14chanwa
 * on 2017.06.24
 */

package Game;

import MovingElements.MovingElement;
import processing.core.PApplet;

public class Dinosaur extends PApplet {

	// Window dimensions
	public static final int WINDOW_HEIGHT = 300;
	public static final int WINDOW_WIDTH = 800;

	// Update periods (in milliseconds)
	public static final int PLAYER_REFRESH_PERIOD_MILLISECONDS = 1;
	public static final int OBJECT_SPAWN_PERIOD_MILLISECONDS = 67; 
	public static final int COLLISION_DETECTION_PERIOD_MILLISECONDS = 5; 

	// Object spawn rate (lambda parameter in exponential law)
	// Note that the effective spawn rate depends on
	// OBJECT_SPAWN_PERIOD_MILLISECONDS (that is the rate of spawn attempts)
	public static final double CLOUD_SPAWN_RATE = 0.0001;
	public static final double OBSTACLE_SPAWN_RATE = 0.001;
	public static final double MIN_DISTANCE_BETWEEN_OBSTACLES = 75.0;
	
	// Max altitude from which the player can jump
	public static final int MAX_ALTITUDE_TO_JUMP = 15;

	// Drawing parameters
	public static final double X_MARGIN = 20; 					// margins outside window
	public static final int OFFSET_DISTANCE_TO_BORDER = 100;	// distance from left edge to player
	public static final int HORIZON_LINE_HEIGHT = 80;			// height of the horizon on the screen

	public static final double PLAYER_INITIAL_VELOCITY = 100.0;
	public static final double PLAYER_WEIGHT = 120.0;
	public static final double PLAYER_JUMP_VELOCITY = 350.0;
	
	public static final double MIN_CLOUD_ALTITUDE = 125.0;
	public static final double MAX_CLOUD_ALTITUDE = 175.0;
	
	public static final double CLOUD_VELOCITY_MIN = 75;
	public static final double CLOUD_VELOCITY_MAX = 75;
	
	// Player & obstacle dimensions
	public static final int OBSTACLE_RADIUS = 20;	// x radius of the half-ellipsoid
	public static final int OBSTACLE_HEIGHT = 30;	// y-radius of the half-ellipsoid
	public static final int PLAYER_WIDTH = 30;		// x-radius of the ellipsoid
	public static final int PLAYER_HEIGHT = 60;		// y-radius of the ellipsoid
	
	// Score toggles
	public static final int POINTS_EARNED_FOR_SUCCESS = 1;
	public static final int POINTS_EARNED_FOR_COLLISION = -5;

	// Game
	private GameHandler m_gameHandler;

	// // Containers
	// JFrame frame;
	// JPanel drawPanel;
	// PSurface ps;

	public Dinosaur() {

		// Create GameHandler
		m_gameHandler = new GameHandler();

		// // Setup frame
		// frame = new JFrame("JFrame Test");
		//
		// frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// //frame.setMinimumSize(new Dimension(200,200));
		// frame.setLayout(new BorderLayout());
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		// drawPanel = new JPanel();
		// frame.add(drawPanel, BorderLayout.CENTER);
		//
		// JPanel bottomPanel = new JPanel(new BorderLayout());
		// bottomPanel.add(new JLabel("salut"), BorderLayout.CENTER);
		//
		// JButton stopButton = new JButton("STOP");
		// stopButton.addActionListener(new ActionListener() {
		// boolean isStopped = false;
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// if (isStopped) {
		// ps.resumeThread();
		// isStopped = false;
		// return;
		// }
		// ps.pauseThread();
		// isStopped = true;
		// }
		//
		// });
		// //bottomPanel.add(stopButton, BorderLayout.EAST);
		// frame.add(bottomPanel,BorderLayout.SOUTH);
		//
		// ps = initSurface();
		// ps.setSize(200, 200);
		// SmoothCanvas smoothCanvas = (SmoothCanvas)ps.getNative();
		// drawPanel.add(smoothCanvas);
		// ps.startThread();
		// frame.setResizable(false);
		// frame.setLocationRelativeTo(null);
		// frame.setVisible(true);
		// drawPanel.setFocusable(false);

		// Run game
		m_gameHandler.startRoutine();
	}
	
	/**
	 * Gets the width of the drawing surface.
	 * @return Width
	 */
	public int getDrawSurface_width() {
		return WINDOW_WIDTH; // frame.getContentPane().getSize().width;
	}

	/**
	 * Gets the height of the drawing surface.
	 * @return Height
	 */
	public int getDrawSurface_height() {
		return WINDOW_HEIGHT; //
	}

	
	/*
	 * Processing methods
	 */
	
	public void setup() {
		noStroke();
	}

	public void settings() {
		// size(drawPanel.getSize().width,
		// frame.getContentPane().getSize().height);
		size(getDrawSurface_width(), getDrawSurface_height());
		// surface.setResizable(true);
	}

	public void draw() {

		// surface.setSize(getDrawSurface_width(), getDrawSurface_height());
		// ellipse(mouseX, mouseY, 20, 20);
		background(160);

		// Draw horizon
		int horizon_line = getDrawSurface_height() - HORIZON_LINE_HEIGHT;
		fill(100);
		rect(0, horizon_line, WINDOW_WIDTH, horizon_line + WINDOW_HEIGHT);

		fill(255);
		// Draw element queues
		for (GameHandler.ElementQueue queue : m_gameHandler.m_queues) {
			for (MovingElement element : queue) {
				element.drawElement(this, m_gameHandler.get_currentXPosition());
			}
		}

		// Draw player
		m_gameHandler.m_player.drawElement(this, m_gameHandler.get_currentXPosition());

		// Draw score
		textSize(20);
		text("Score: " + (int) Math.floor(m_gameHandler.m_score), 10, 30);

	}

	public void keyPressed() {
		// System.out.println("Key pressed: " + key);
		if (key == ' ') {
			m_gameHandler.attemptJump();
		}
	}

	/**
	 * Launch game!
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main("Game.Dinosaur");
	}

}