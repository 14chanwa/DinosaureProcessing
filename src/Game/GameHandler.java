package Game;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import MovingElements.CloudFactory;
import MovingElements.MovingElement;
import MovingElements.ObstacleFactory;


public class GameHandler {
	
	public static class ElementQueue extends ConcurrentLinkedQueue<MovingElement> {};
	
	public double m_currentXPosition;	// Pixels
	public double m_currentXVelocity;	// Pixels per second
	
	public double m_horizonX;
	
	private ElementQueue m_cloudQueue;
	private ElementQueue m_obstacleQueue;
	
	public ArrayList<ElementQueue> m_queues;
	
	// The threads handling the spawn rate of the elements
	public SpawnThread m_cloudSpawnThread;
	public SpawnThread m_obstacleSpawnThread;
	
	private final ScheduledExecutorService scheduler =
		       Executors.newScheduledThreadPool(1);
	
	/**
	 * Updates the player's X position
	 */
	private final Runnable m_updateXPosition = new Runnable() {
		public void run() {
			m_currentXPosition += Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS * m_currentXVelocity / 1000.0;
		}
	};
	
	public GameHandler() {
		m_currentXPosition = 0.0;
		m_currentXVelocity = 100.0;
		
		m_horizonX = 100.0;
		
		m_cloudQueue = new ElementQueue();
		m_obstacleQueue = new ElementQueue();
		
		m_queues = new ArrayList<ElementQueue>();
		m_queues.add(m_cloudQueue);
		m_queues.add(m_obstacleQueue);
		
		m_cloudSpawnThread = new SpawnThread(0.0001, m_cloudQueue, this, new CloudFactory());
		m_obstacleSpawnThread = new SpawnThread(0.0001, m_obstacleQueue, this, new ObstacleFactory());
	}
	
	public void startRoutine() {
		scheduler.scheduleAtFixedRate(m_updateXPosition, Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS, Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
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
