# DinosaureProcessing
A repo containing a dinosaur game inspired from the one implemented in Google Chrome.

This application uses Processing for drawing. The main method is located in Game.Dinosaure. In order to compile the program, you will need to add Processing to the build path, as described [here](https://processing.org/tutorials/eclipse/).

The camera follows the player, who always at the left of the screen. Objects (clouds and obstacles) spawn at the right of the screen ; this process is handled by the `SpawnThread` instances created in the `GameHandler` class. Every object has a dynamic, dimensions and drawing methods, that are implemented in their respective `MovingElementFactory` classes. Collisions are detected by approximating objects to simple polygons such as triangles and diamonds.

### TODO
Still have to decide what to do after a collision ; I intend to mark the obstacle as collided and count the number of objects collided as a score: +1 for every non-collided obstacle that exits the screen at the left, -1 for every collided obstacle. A timer can be added in order to finish the game.
