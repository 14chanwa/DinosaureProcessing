/*
 * Created by 14chanwa
 * on 2017.06.25
 */

package MovingElements;

public abstract class MovingElementFactory {
	public abstract MovingElement getNewInstance(double _xPosition);

	public abstract double getMinimumDistanceBetweenObjects();
}
