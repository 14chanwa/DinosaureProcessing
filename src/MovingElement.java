
public class MovingElement {
	private double m_xPosition;
	private double m_xVelocity;
	
	public MovingElement(double _xPosition) {
		set_xPosition(_xPosition);
		set_xVelocity(0.0);
	}
	
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
	
	public void moveObject(double _dt) {
		m_xPosition += _dt * m_xVelocity;
	}
}
