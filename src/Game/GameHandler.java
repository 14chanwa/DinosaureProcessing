package Game;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import MovingElements.CloudFactory;
import MovingElements.MovingElement;
import MovingElements.ObstacleFactory;
import MovingElements.Player;

public class GameHandler {

	public static class ElementQueue extends ConcurrentLinkedQueue<MovingElement> {
		private static final long serialVersionUID = 4422699335821522673L;
	};

	public Player m_player;

	public double m_horizonX;

	private ElementQueue m_cloudQueue;
	private ElementQueue m_obstacleQueue;

	public ArrayList<ElementQueue> m_queues;

	// The threads handling the spawn rate of the elements
	public SpawnThread m_cloudSpawnThread;
	public SpawnThread m_obstacleSpawnThread;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public GameHandler() {
		m_player = new Player(0.0);

		m_horizonX = 100.0;

		m_cloudQueue = new ElementQueue();
		m_obstacleQueue = new ElementQueue();

		m_queues = new ArrayList<ElementQueue>();
		m_queues.add(m_cloudQueue);
		m_queues.add(m_obstacleQueue);

		m_cloudSpawnThread = new SpawnThread(Dinosaure.CLOUD_SPAWN_RATE, m_cloudQueue, this, new CloudFactory());
		m_obstacleSpawnThread = new SpawnThread(Dinosaure.OBSTACLE_SPAWN_RATE, m_obstacleQueue, this,
				new ObstacleFactory());
	}

	/**
	 * Updates the player's X position.
	 */
	private final Runnable m_updateXPosition = new Runnable() {
		public void run() {
			m_player.moveObject(Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS / 1000.0);
		}
	};

	/**
	 * Starts the game's threads.
	 */
	public void startRoutine() {
		scheduler.scheduleAtFixedRate(m_updateXPosition, Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS,
				Dinosaure.PLAYER_REFRESH_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
		m_cloudSpawnThread.startSpawnRoutine();
		try {
			TimeUnit.MILLISECONDS.sleep(37);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m_obstacleSpawnThread.startSpawnRoutine();
	}

	public void set_horizonX(double _horizonX) {
		m_horizonX = _horizonX;
	}

	public double get_currentXPosition() {
		return m_player.get_xPosition();
	}

	/**
	 * Unit test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameHandler gameHandler = new GameHandler();
		gameHandler.startRoutine();
	}

}
