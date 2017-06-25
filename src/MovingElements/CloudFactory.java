package MovingElements;

import Game.Dinosaure;

public class CloudFactory extends MovingElementFactory {
	
	// Boundaries for cloud altitude
	private final double MIN_CLOUD_ALTITUDE = 100.0;
	private final double MAX_CLOUD_ALTITUDE = 150.0;

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition, MIN_CLOUD_ALTITUDE + Math.random() * (MAX_CLOUD_ALTITUDE - MIN_CLOUD_ALTITUDE)) {
			
			@Override
			public void drawElement(Dinosaure _dinosaure, double _currentXPosition) {
				int cloud_X_position = (int) Math.floor(get_xPosition() - _currentXPosition);
				int cloud_Y_position = (int) Math.floor(_dinosaure.getDrawSurface_height() - Dinosaure.HORIZON_LINE_HEIGHT - get_yPosition());
				_dinosaure.rect(cloud_X_position - 10, cloud_Y_position - 10, 20, 20);
			}

		};
	}

}
