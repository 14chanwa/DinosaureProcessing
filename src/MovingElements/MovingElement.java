/*
 * Created by 14chanwa
 * on 2017.06.25
 */

package MovingElements;

import Game.Dinosaur;

public abstract class MovingElement {

	private double m_xPosition;
	private double m_yPosition;

	private double m_xVelocity;
	private double m_yVelocity;

	private double m_mass;

	private double m_minYPosition;

	// Constructor
	public MovingElement(double _xPosition, double _yPosition, double _mass, double _minYPosition) {
		set_xPosition(_xPosition);
		set_xVelocity(0.0);
		set_yPosition(_yPosition);
		set_yVelocity(0.0);
		m_mass = _mass;
		m_minYPosition = _minYPosition;
	}

	public MovingElement(double _xPosition, double _yPosition) {
		this(_xPosition, _yPosition, 0.0, 0.0);
	}

	public MovingElement(double _xPosition) {
		this(_xPosition, 0.0, 0.0, 0.0);
	}

	/**
	 * Move object given instantaneous velocities/accelerations.
	 * 
	 * @param _dt
	 */
	public void moveObject(double _dt) {
		// Move object on X axis
		m_xPosition += _dt * m_xVelocity;

		// Move object on Y axis
		m_yPosition = Math.max(m_minYPosition, m_yPosition + _dt * m_yVelocity);
		if (m_yPosition == m_minYPosition) {
			m_yVelocity = 0;
		} else {
			m_yVelocity -= _dt * m_mass * 9.81;
		}
	}

	public abstract void drawElement(Dinosaur _dinosaur, double _currentXPosition);

	public double get_xPosition() {
		return m_xPosition;
	}

	public void set_xPosition(double _xPosition) {
		m_xPosition = _xPosition;
	}

	public double get_xVelocity() {
		return m_xVelocity;
	}

	public void set_xVelocity(double _xVelocity) {
		m_xVelocity = _xVelocity;
	}

	public double get_yPosition() {
		return m_yPosition;
	}

	public void set_yPosition(double _yPosition) {
		m_yPosition = _yPosition;
	}

	public double get_yVelocity() {
		return m_yVelocity;
	}

	public void set_yVelocity(double _yVelocity) {
		m_yVelocity = _yVelocity;
	}
}
