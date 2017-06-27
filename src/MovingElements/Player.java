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
		_dinosaur.translate(player_X_position, player_Y_position - (int) (0.2 * Dinosaur.PLAYER_HEIGHT));
		_dinosaur.rotate((float) (Math.PI/6.0));
		
		// Draw main ellipse
		_dinosaur.ellipse(0, 0, (int)(0.7 * Dinosaur.PLAYER_WIDTH), (int)(0.9 * Dinosaur.PLAYER_HEIGHT));
		
		_dinosaur.translate((int)(0.1 * Dinosaur.PLAYER_WIDTH), (int)(- 0.6 * Dinosaur.PLAYER_HEIGHT));
		_dinosaur.rotate((float) (2 * Math.PI/6.0));
		
		// Draw head
		if (get_yPosition() > 0) {	
			_dinosaur.rotate((float) (Math.PI/5.0));
			_dinosaur.translate(0, (int)(- 0.1 * Dinosaur.PLAYER_HEIGHT));
		}
		_dinosaur.ellipse(0, 0, (int)(0.5 * Dinosaur.PLAYER_WIDTH), (int)(0.4 * Dinosaur.PLAYER_HEIGHT));
		if (get_yPosition() > 0) {
			_dinosaur.translate(0, (int)( 0.1 * Dinosaur.PLAYER_HEIGHT));
			_dinosaur.rotate(-(float) (Math.PI/5.0));
		}
		
		_dinosaur.rotate(-(float) (2 * Math.PI/6.0));
		_dinosaur.translate((int)(-0.1 * Dinosaur.PLAYER_WIDTH), (int)(0.5 * Dinosaur.PLAYER_HEIGHT));
		
		
		_dinosaur.translate((int)(-0.1 * Dinosaur.PLAYER_WIDTH), (int)(0.55 * Dinosaur.PLAYER_HEIGHT));
		_dinosaur.rotate(-(float) (Math.PI/6.0));
		
		
		_dinosaur.translate((int)(0.5 * Dinosaur.PLAYER_WIDTH), 0);
		
		// Draw feet
		if (get_yPosition() > 0) {
			_dinosaur.rotate(-(float) (Math.PI/4.0));
		}
		_dinosaur.ellipse(0, 0, (int)(0.2 * Dinosaur.PLAYER_WIDTH), (int)(0.2 * Dinosaur.PLAYER_HEIGHT));
		if (get_yPosition() > 0) {
			_dinosaur.rotate((float) (Math.PI/4.0));
		}
		
		_dinosaur.translate(-(int)(0.5 * Dinosaur.PLAYER_WIDTH), 0);
		
		_dinosaur.rotate((float) (Math.PI/6.0));
		_dinosaur.translate((int)(0.1 * Dinosaur.PLAYER_WIDTH), (int)(- 0.55 * Dinosaur.PLAYER_HEIGHT));
		
		_dinosaur.rotate(-(float) (Math.PI/6.0));
		
		// Draw tail
		_dinosaur.translate(-(int)(0.6 * Dinosaur.PLAYER_WIDTH), (int)(0.2 * Dinosaur.PLAYER_HEIGHT));
		_dinosaur.ellipse(0, 0, (int)(0.5 * Dinosaur.PLAYER_WIDTH), (int)(0.1 * Dinosaur.PLAYER_HEIGHT));
		_dinosaur.translate((int)(0.6 * Dinosaur.PLAYER_WIDTH), -(int)(0.2 * Dinosaur.PLAYER_HEIGHT));
		
		_dinosaur.translate(-player_X_position, -player_Y_position + (int) (0.2 * Dinosaur.PLAYER_HEIGHT));
	}

}
