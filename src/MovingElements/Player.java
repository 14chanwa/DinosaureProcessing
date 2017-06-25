package MovingElements;

import Game.Dinosaure;

public class Player extends MovingElement {

	public Player(double _xPosition) {
		super(_xPosition, 0.0, 10.0, 0.0);
		set_xVelocity(Dinosaure.INITIAL_PLAYER_VELOCITY);
	}

	@Override
	public void drawElement(Dinosaure _dinosaure, double _currentXPosition) {
		// TODO Auto-generated method stub
		int player_X_position = Dinosaure.PIXELS_FROM_PLAYER_TO_BORDER;
        int player_Y_position = (int) Math.floor(_dinosaure.getDrawSurface_height() - Dinosaure.HORIZON_LINE_HEIGHT - get_yPosition());
        
        // Draw player
        _dinosaure.ellipse(player_X_position, player_Y_position, 20, 60);
	}

}
