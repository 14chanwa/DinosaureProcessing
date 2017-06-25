import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameHandler {
	
	public static final int X_REFRESH_PERIOD_MILLISECONDS = 5;
	
	public double m_currentXPosition;	// Pixels
	public double m_currentXVelocity;	// Pixels per second
	
	public double m_horizonX;
	
	public ConcurrentLinkedQueue<MovingElement> m_cloudQueue;
	public ConcurrentLinkedQueue<MovingElement> m_obstacleQueue;
	
	// The threads handling the spawn rate of the elements
	public SpawnThread m_cloudSpawnThread;
	public SpawnThread m_obstacleSpawnThread;
	
	private final ScheduledExecutorService scheduler =
		       Executors.newScheduledThreadPool(1);
	
	private final Runnable m_updateXPosition = new Runnable() {
		public void run() {
			m_currentXPosition += X_REFRESH_PERIOD_MILLISECONDS * m_currentXVelocity / 1000.0;
		}
	};
	
	public GameHandler() {
		m_currentXPosition = 0.0;
		m_currentXVelocity = 100.0;
		
		m_horizonX = 100.0;
		
		m_cloudQueue = new ConcurrentLinkedQueue<MovingElement>();
		m_obstacleQueue = new ConcurrentLinkedQueue<MovingElement>();
		
		m_cloudSpawnThread = new SpawnThread(0.0001, m_cloudQueue, this);
		m_obstacleSpawnThread = new SpawnThread(0.0001, m_obstacleQueue, this);
	}
	
	public void startRoutine() {
		scheduler.scheduleAtFixedRate(m_updateXPosition, X_REFRESH_PERIOD_MILLISECONDS, X_REFRESH_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
		m_cloudSpawnThread.startSpawnRoutine();
		//m_obstacleSpawnThread.startSpawnRoutine();
	}
	
	public void set_horizonX(double _horizonX) {
		m_horizonX = _horizonX;
	}
	
	public static void main(String[] args) {
		GameHandler gameHandler = new GameHandler();
		gameHandler.startRoutine();
	}
	
}
