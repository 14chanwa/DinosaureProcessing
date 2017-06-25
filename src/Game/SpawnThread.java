/*
 * Created by 14chanwa
 * on 2017.06.24
 */

package Game;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import MovingElements.CloudFactory;
import MovingElements.MovingElement;
import MovingElements.MovingElementFactory;

public class SpawnThread {

	private MovingElementFactory m_factory;

	public double m_lambda;
	public ConcurrentLinkedQueue<MovingElement> m_queue;
	public GameHandler m_gameHandler;

	public double m_lastXPosition;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private final Runnable m_updateQueue = new Runnable() {
		public void run() {
			// Remove objects beyond boundaries
			double _inferior_boundary = getXPosition() - Dinosaur.X_MARGIN - Dinosaur.OFFSET_DISTANCE_TO_BORDER;
			while (!m_queue.isEmpty() && m_queue.element().get_xPosition() < _inferior_boundary) {
				m_queue.remove();
			}

			// Add object according to exponential law
			double _superior_boundary = getXPosition() + Dinosaur.WINDOW_WIDTH + Dinosaur.X_MARGIN;
			double _distance_to_superior_boundary = _superior_boundary - m_lastXPosition;
			double _exponential_law = 1 - Math
					.exp(-m_lambda * (_distance_to_superior_boundary - m_factory.getMinimumDistanceBetweenObjects()));
			double _rand = Math.random();
			if (_rand < _exponential_law) {
				// Add an object
				m_lastXPosition = _superior_boundary;
				m_queue.add(m_factory.getNewInstance(_superior_boundary));
				// System.out.println("_exponential_law = " + _exponential_law +
				// ", rand = " + _rand + ", x = "
				// + m_lastXPosition + ": added object");
			}
		}
	};

	/*
	 * Constructor
	 */
	public SpawnThread(double _lambda, ConcurrentLinkedQueue<MovingElement> _queue, GameHandler _gameHandler,
			MovingElementFactory _factory) {
		super();
		m_gameHandler = _gameHandler;
		m_lambda = _lambda;
		m_queue = _queue;
		m_lastXPosition = getXPosition() + Dinosaur.WINDOW_WIDTH;
		m_factory = _factory;
	}

	/*
	 * Start routine
	 */
	public void startSpawnRoutine() {
		scheduler.scheduleAtFixedRate(m_updateQueue, Dinosaur.OBJECT_SPAWN_PERIOD_MILLISECONDS,
				Dinosaur.OBJECT_SPAWN_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);

	}

	public double getXPosition() {
		return m_gameHandler.get_currentXPosition();
	}

	public static void main(String[] args) {
		GameHandler gameHandler = new GameHandler();
		ConcurrentLinkedQueue<MovingElement> queue = new ConcurrentLinkedQueue<MovingElement>();

		CloudFactory cloudFactory = new CloudFactory();

		SpawnThread spawnThread = new SpawnThread(1.0, queue, gameHandler, cloudFactory);
		spawnThread.startSpawnRoutine();
	}
}
