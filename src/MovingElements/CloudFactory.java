package MovingElements;

import Game.Dinosaure;

public class CloudFactory extends MovingElementFactory {

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition) {
			
			@Override
			public void drawElement(Dinosaure _dinosaure, double _currentXPosition) {
				int cloud_X_position = (int) Math.floor(get_xPosition() - _currentXPosition);
				int cloud_Y_position = _dinosaure.getDrawSurface_height() - 100;
				_dinosaure.rect(cloud_X_position - 10, cloud_Y_position - 10, 20, 20);
			}

		};
	}

}
