/*
 * Created by 14chanwa
 * on 2017.06.24
 */

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
import processing.core.PApplet;

public class GameHandler {

	public static class ElementQueue extends ConcurrentLinkedQueue<MovingElement> {
		private static final long serialVersionUID = 4422699335821522673L;
	};

	/**
	 * Player instance
	 */
	public Player m_player;

	/**
	 * Score
	 */
	public int m_score;

	/**
	 * List of queues of objects to be drawn
	 */
	public ArrayList<ElementQueue> m_queues;

	private ElementQueue m_cloudQueue;
	private ElementQueue m_obstacleQueue;

	// The threads handling the spawn rate of the elements
	private SpawnThread m_cloudSpawnThread;
	private SpawnThread m_obstacleSpawnThread;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/**
	 * Constructor
	 */
	public GameHandler() {
		m_player = new Player(0.0);
		m_score = 0;

		m_cloudQueue = new ElementQueue();
		m_obstacleQueue = new ElementQueue();

		m_queues = new ArrayList<ElementQueue>();
		m_queues.add(m_cloudQueue);
		m_queues.add(m_obstacleQueue);

		m_cloudSpawnThread = new SpawnThread(Dinosaur.CLOUD_SPAWN_RATE, m_cloudQueue, this, new CloudFactory());
		m_obstacleSpawnThread = new SpawnThread(Dinosaur.OBSTACLE_SPAWN_RATE, m_obstacleQueue, this,
				new ObstacleFactory());
	}

	/**
	 * Starts the game's threads.
	 */
	public void startRoutine() {
		scheduler.scheduleAtFixedRate(m_updateXPosition, Dinosaur.PLAYER_REFRESH_PERIOD_MILLISECONDS,
				Dinosaur.PLAYER_REFRESH_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
		scheduler.scheduleAtFixedRate(m_checkObstacleCollisions, Dinosaur.COLLISION_DETECTION_PERIOD_MILLISECONDS,
				Dinosaur.COLLISION_DETECTION_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
		m_cloudSpawnThread.startSpawnRoutine();
		try {
			TimeUnit.MILLISECONDS.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m_obstacleSpawnThread.startSpawnRoutine();
	}

	/**
	 * Attempts to jump.
	 * 
	 * @return
	 */
	public void attemptJump() {
		if (m_player.get_yPosition() <= Dinosaur.MAX_ALTITUDE_TO_JUMP) {
			m_player.set_yVelocity(Dinosaur.PLAYER_JUMP_VELOCITY);
		}
	}

	public double get_currentXPosition() {
		return m_player.get_xPosition();
	}

	/**
	 * Updates the player's X position.
	 */
	private final Runnable m_updateXPosition = new Runnable() {
		public void run() {
			m_player.moveObject(Dinosaur.PLAYER_REFRESH_PERIOD_MILLISECONDS / 1000.0);
			// m_player.set_xVelocity(Dinosaur.PLAYER_INITIAL_VELOCITY +
			// m_player.get_xPosition() / 100);
			m_player.set_xVelocity(
					Dinosaur.PLAYER_INITIAL_VELOCITY + 50 * Math.max(0, -1 + Math.log10(m_player.get_xPosition())));
		}
	};

	/**
	 * Checks if the player is colliding with an obstacle. Say the player is a
	 * diamond and the obstacle is a triangle, then check if the summit of the
	 * triangle is in the diamond or the inferior summit of the diamond is in
	 * the triangle.
	 */
	private final Runnable m_checkObstacleCollisions = new Runnable() {

		class Vertex {
			double x;
			double y;
			double z;

			Vertex(double _x, double _y, double _z) {
				x = _x;
				y = _y;
				z = _z;
			}

			Vertex(double _x, double _y) {
				this(_x, _y, 0);
			}

			// Vertex plus(Vertex _v) {
			// return new Vertex(x + _v.x, y + _v.y);
			// }

			Vertex minus(Vertex _v) {
				return new Vertex(x - _v.x, y - _v.y);
			}

			double dot(Vertex _v) {
				return x * _v.x + y * _v.y + z * _v.z;
			}

			Vertex cross(Vertex _v) {
				return new Vertex(y * _v.z - z * _v.y, z * _v.x - x * _v.z, x * _v.y - y * _v.x);
			}
		}

		public void run() {
			if (checkObstacleCollisions()) {
				//System.out.println("Collision!");
			}
		}

		public boolean checkObstacleCollisions() {

			// Player vertexes
			Vertex A = new Vertex(m_player.get_xPosition(), m_player.get_yPosition() - Dinosaur.PLAYER_HEIGHT / 2.0);
			Vertex B = new Vertex(m_player.get_xPosition() - Dinosaur.PLAYER_WIDTH / 2.0, m_player.get_yPosition());
			Vertex C = new Vertex(m_player.get_xPosition(), m_player.get_yPosition() + Dinosaur.PLAYER_HEIGHT / 2.0);
			Vertex D = new Vertex(m_player.get_xPosition() + Dinosaur.PLAYER_WIDTH / 2.0, m_player.get_yPosition());
			Vertex[] playerVertexes = { A, B, C, D, A, B };

			for (MovingElement _obstacle : m_obstacleQueue) {
				if (!_obstacle.get_collided() && Math.abs(_obstacle.get_xPosition() - m_player.get_xPosition()) < Math
						.max(Dinosaur.PLAYER_WIDTH / 2.0, Dinosaur.OBSTACLE_RADIUS / 2.0)) {
					// Obstacle vertexes
					Vertex E = new Vertex(_obstacle.get_xPosition() + Dinosaur.OBSTACLE_RADIUS / 2.0,
							_obstacle.get_yPosition());
					Vertex F = new Vertex(_obstacle.get_xPosition() - Dinosaur.OBSTACLE_RADIUS / 2.0,
							_obstacle.get_yPosition());
					Vertex G = new Vertex(_obstacle.get_xPosition(),
							_obstacle.get_yPosition() + Dinosaur.OBSTACLE_HEIGHT / 2.0);
					Vertex[] obstacleVertexes = { E, F, G, E, F };

					/*
					 * Detecting collision
					 */
					// Check if the top vertex of the triangle representing the
					// obstacle is in the diamond
					// representing the player
					boolean collided = true;
					for (int i = 0; i < 4; i++) {
						// For all possible positions, check if E is between the
						// edges
						Vertex edge_1 = playerVertexes[i].minus(playerVertexes[i + 1]);
						Vertex edge_2 = G.minus(playerVertexes[i + 1]);
						Vertex edge_3 = playerVertexes[i + 2].minus(playerVertexes[i + 1]);
						if ((edge_1.cross(edge_2)).dot(edge_2.cross(edge_3)) < 0) {
							collided = false;
							break;
						}
					}
					if (collided) {
						_obstacle.toggle_collided();
						return true;
					}
					// Check if the bottom vertex of the diamond is in the
					// triangle
					collided = true;
					for (int i = 0; i < 3; i++) {
						// For all possible positions, check if A is between the
						// edges
						Vertex edge_1 = obstacleVertexes[i].minus(obstacleVertexes[i + 1]);
						Vertex edge_2 = A.minus(obstacleVertexes[i + 1]);
						Vertex edge_3 = obstacleVertexes[i + 2].minus(obstacleVertexes[i + 1]);
						if ((edge_1.cross(edge_2)).dot(edge_2.cross(edge_3)) < 0) {
							collided = false;
							break;
						}
					}
					if (collided) {
						_obstacle.toggle_collided();
						return true;
					}

					/*
					 * Box shaped detection
					 * Should give a lot of false positives
					 */
					// if (G.x < m_player.get_xPosition() +
					// Dinosaur.PLAYER_WIDTH / 2.0
					// && G.x > m_player.get_xPosition() -
					// Dinosaur.PLAYER_WIDTH / 2.0
					// && G.y < m_player.get_yPosition() +
					// Dinosaur.PLAYER_HEIGHT /2.0
					// && G.y > m_player.get_yPosition() -
					// Dinosaur.PLAYER_HEIGHT / 2.0) {
					// return true;
					// }
				}
			}

			return false;
		}
	};

	/**
	 * Unit test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// GameHandler gameHandler = new GameHandler();
		// gameHandler.startRoutine();
		PApplet.main("Game.Dinosaur");
	}

}
