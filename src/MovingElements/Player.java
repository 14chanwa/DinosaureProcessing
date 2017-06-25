/*
 * Created by 14chanwa
 * on 2017.06.25
 */

package MovingElements;

import Game.Dinosaur;

public class Player extends MovingElement {

	public Player(double _xPosition) {
		super(_xPosition, 0.0, Dinosaur.PLAYER_WEIGHT, 0.0);
		set_xVelocity(Dinosaur.PLAYER_INITIAL_VELOCITY);
	}

	@Override
	public void drawElement(Dinosaur _dinosaur, double _currentXPosition) {
		int player_X_position = Dinosaur.OFFSET_DISTANCE_TO_BORDER;
		int player_Y_position = (int) Math
				.floor(_dinosaur.getDrawSurface_height() - Dinosaur.HORIZON_LINE_HEIGHT - get_yPosition());

		// Draw player
		_dinosaur.ellipse(player_X_position, player_Y_position, Dinosaur.PLAYER_WIDTH, Dinosaur.PLAYER_HEIGHT);
	}

}
