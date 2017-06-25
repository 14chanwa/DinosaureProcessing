package MovingElements;

import Game.Dinosaure;
import processing.core.PApplet;

public class ObstacleFactory extends MovingElementFactory {

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition) {

			@Override
			public void drawElement(Dinosaure _dinosaure, double _currentXPosition) {
				int obstacle_X_position = (int) Math.floor(get_xPosition() - _currentXPosition);
				int obstacle_Y_position = (int) Math
						.floor(_dinosaure.getDrawSurface_height() - Dinosaure.HORIZON_LINE_HEIGHT - get_yPosition());
				_dinosaure.arc(obstacle_X_position, obstacle_Y_position + 20, 20, 30, PApplet.PI, PApplet.TWO_PI);
			}

		};
	}

}
