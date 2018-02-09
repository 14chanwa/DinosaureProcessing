package MovingElements;

import Game.Dinosaur;

public class Cloud extends MovingElement {

	public Cloud(double _xPosition) {
		super(_xPosition);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawElement(Dinosaur _dinosaure, double _currentXPosition) {
		int cloud_X_position = (int) Math.floor(get_xPosition() - _currentXPosition);
		int cloud_Y_position = _dinosaure.getDrawSurface_height() - 100;
		_dinosaure.rect(cloud_X_position - 10, cloud_Y_position - 10, 20, 20);
	}

}
