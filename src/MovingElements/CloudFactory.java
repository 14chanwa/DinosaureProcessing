/*
 * Created by 14chanwa
 * on 2017.06.25
 */

package MovingElements;

import Game.Dinosaur;

public class CloudFactory extends MovingElementFactory {

	// Boundaries for cloud altitude
	private final double MIN_CLOUD_ALTITUDE = 150.0;
	private final double MAX_CLOUD_ALTITUDE = 200.0;

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition,
				MIN_CLOUD_ALTITUDE + Math.random() * (MAX_CLOUD_ALTITUDE - MIN_CLOUD_ALTITUDE)) {

			@Override
			public void drawElement(Dinosaur _dinosaur, double _currentXPosition) {
				int cloud_X_position = Dinosaur.OFFSET_DISTANCE_TO_BORDER
						+ (int) Math.floor(get_xPosition() - _currentXPosition);
				int cloud_Y_position = -Dinosaur.HORIZON_LINE_HEIGHT
						+ (int) Math.floor(_dinosaur.getDrawSurface_height() - get_yPosition());
				_dinosaur.rect(cloud_X_position - 10, cloud_Y_position - 10, 30, 20, 200);
			}

		};
	}

	@Override
	public double getMinimumDistanceBetweenObjects() {
		return 0;
	}

}
