/*
 * Created by 14chanwa
 * on 2017.06.25
 */

package MovingElements;

import Game.Dinosaur;
import processing.core.PApplet;

public class ObstacleFactory extends MovingElementFactory {

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition, -20, 0.0, -20) {

			@Override
			public void drawElement(Dinosaur _dinosaur, double _currentXPosition) {
				int obstacle_X_position = Dinosaur.OFFSET_DISTANCE_TO_BORDER
						+ (int) Math.floor(get_xPosition() - _currentXPosition);
				int obstacle_Y_position = -Dinosaur.HORIZON_LINE_HEIGHT
						+ (int) Math.floor(_dinosaur.getDrawSurface_height() - get_yPosition());
				_dinosaur.arc(obstacle_X_position, obstacle_Y_position, Dinosaur.OBSTACLE_RADIUS,
						Dinosaur.OBSTACLE_HEIGHT, PApplet.PI, PApplet.TWO_PI);
			}

		};
	}

	@Override
	public double getMinimumDistanceBetweenObjects() {
		return Dinosaur.MIN_DISTANCE_BETWEEN_OBSTACLES;
	}

}
