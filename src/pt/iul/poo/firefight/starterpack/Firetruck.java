package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Firetruck extends Mobile implements Drivable{
	
	public Firetruck(Point2D position) {
		super(position, "firetruck", 2);
	}
	
	public void drive() {
		int key = game.getGUI().keyPressed();
		if(!Direction.isDirection(key))
			return;
		Direction dir = Direction.directionFor(key);
		
		
		switch(dir) {
		case LEFT : this.setName("firetruck_left"); break;
		case RIGHT : this.setName("firetruck_right"); break;
		default:
			break;
		}
		
		setPosition(GameEngine.clamp(getPosition().plus(dir.asVector())));

		
	}


}
