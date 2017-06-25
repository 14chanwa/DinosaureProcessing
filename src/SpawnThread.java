import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SpawnThread {
	
	// Update period
	public static final int UPDATE_PERIOD = 10; // in milliseconds
	
	// Margins
	public static final double X_MARGIN = 20;
	
	public double m_lambda;
	public ConcurrentLinkedQueue<MovingElement> m_queue;
	public GameHandler m_gameHandler;
	
	public double m_lastXPosition;
	
	private final ScheduledExecutorService scheduler =
		       Executors.newScheduledThreadPool(1);
	
	private final Runnable m_updateQueue = new Runnable() {
		public void run() {
			// Remove objects beyond boundaries
			double _inferior_boundary = getXPosition() - X_MARGIN;
			while(!m_queue.isEmpty() && m_queue.element().get_xPosition() < _inferior_boundary) {
				m_queue.remove();
			}
			
			// Add object according to exponential law
			double _superior_boundary = getXPosition() + getHorizonX() + X_MARGIN;
			double _distance_to_superior_boundary = _superior_boundary - m_lastXPosition;
			double _exponential_law = 1 - Math.exp(- m_lambda * _distance_to_superior_boundary);
			double _rand = Math.random();
			if (_rand < _exponential_law) {
				// Add an object
				m_lastXPosition = _superior_boundary;
				m_queue.add(new MovingElement(_superior_boundary));
				System.out.println("_exponential_law = " + _exponential_law + ", rand = " + _rand + ", x = " + m_lastXPosition + ": added object");
			}
		}
	};
	
	/*
	 * Constructor
	 */
	public SpawnThread(double _lambda, ConcurrentLinkedQueue<MovingElement> _queue, GameHandler _gameHandler) {
		super();
		m_gameHandler = _gameHandler;
		m_lambda = _lambda;
		m_queue = _queue;
		m_lastXPosition = getXPosition() + getHorizonX();
	}
	
	/*
	 * Start routine
	 */
	public void startSpawnRoutine() {
		scheduler.scheduleAtFixedRate(m_updateQueue, UPDATE_PERIOD, UPDATE_PERIOD, TimeUnit.MILLISECONDS);
		
	}
	
	public double getXPosition() {
		return m_gameHandler.m_currentXPosition;
	}
	
	public double getHorizonX() {
		return m_gameHandler.m_horizonX;
	}
	
	public static void main(String[] args) {
		GameHandler gameHandler = new GameHandler();
		ConcurrentLinkedQueue<MovingElement> queue = new ConcurrentLinkedQueue<MovingElement>();
		SpawnThread spawnThread = new SpawnThread(1.0, queue, gameHandler);
		spawnThread.startSpawnRoutine();
	}
}
