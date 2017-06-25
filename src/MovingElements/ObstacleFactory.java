package MovingElements;

import Game.Dinosaure;

public class ObstacleFactory extends MovingElementFactory {

	@Override
	public MovingElement getNewInstance(double _xPosition) {
		return new MovingElement(_xPosition) {
			
			@Override
			public void drawElement(Dinosaure _dinosaure, double _currentXPosition) {
				// TODO
			}

		};
	}

}
