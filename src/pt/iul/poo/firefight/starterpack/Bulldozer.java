package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends Mobile implements Drivable {
	private static int demolishes;

	public Bulldozer(Point2D position) {
		super(position, "bulldozer_down", 2);
	}

	@Override
	public void drive() {
		int key = game.getGUI().keyPressed();
		if(!Direction.isDirection(key))
			return;
		Direction dir = Direction.directionFor(key);
		
		switch(dir) {
		case UP : this.setName("bulldozer_up"); break;
		case DOWN : this.setName("bulldozer_down"); break;
		case LEFT : this.setName("bulldozer_left"); break;
		case RIGHT : this.setName("bulldozer_right"); break;

		default: throw new IllegalArgumentException();
		}
		
		setPosition(GameEngine.clamp(getPosition().plus(dir.asVector())));
		Terrain terrain = (Terrain) game.findElement(getPosition(), o->o instanceof Terrain);
		if(!(terrain instanceof Land)) {
			game.removeElement(terrain);
			terrain = new Land(this.getPosition());
			game.addElement(terrain);
			game.getFireman().calculateReward(-25);
			demolishes ++;
		}
		
	}

	public static int getDemolishes() {
		return demolishes;
	}

}
