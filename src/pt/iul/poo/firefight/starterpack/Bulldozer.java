package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends Mobile {
	
	public Bulldozer(Point2D position) {
		super(position, "bulldozer_down", 2);
	}

	@Override
	public void move(Direction dir) {
		switch(dir) {
		case UP : name = "bulldozer_up"; break;
		case DOWN : name = "bulldozer_down"; break;
		case LEFT : name = "bulldozer_left"; break;
		case RIGHT : name = "bulldozer_right"; break;

		default: throw new IllegalArgumentException();
		}
		position = GameEngine.clamp(position.plus(dir.asVector()));
		Terrain terrain = (Terrain) game.findElement(position, 0);

		game.removeElement(terrain);
		terrain = new Land(position);
		game.addElement(terrain);
	}

}
