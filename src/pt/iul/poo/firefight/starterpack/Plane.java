package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Plane extends Mobile implements Movable, Tickable{
	private boolean scrap;
	private Fireman fireman;
	private static int douseCounter = 0;

	Plane(int collumn, Fireman fireman){
		super(new Point2D(collumn, 11), "plane", 4);
		scrap = false;
		this.fireman = fireman;
	}

	public boolean scrap() {
		return scrap;
	}

	private void douse() {
		Point2D downTile = getPosition().plus(Direction.DOWN.asVector());
		Point2D down2Tile = downTile.plus(Direction.DOWN.asVector());
		
		if(game.getGUI().isWithinBounds(downTile)) {
			Fire fire1 = (Fire) game.findElement(downTile, o->o instanceof Fire);
			if(fire1 != null) {
				fireman.calculateReward(50);
				fire1.douse(Direction.DOWN);
				douseCounter ++;
			}
		}
		if(game.getGUI().isWithinBounds(down2Tile)) {
			Fire fire2 = (Fire) game.findElement(down2Tile, o->o instanceof Fire);
			if(fire2 != null) {
				fireman.calculateReward(50);
				fire2.douse(Direction.DOWN);
				douseCounter ++;
			}
		}
		
	}

	public static int getDouses() {
		return douseCounter;
	}

	public void move() {
		setPosition(getPosition().plus(Direction.UP.asVector()).plus(Direction.UP.asVector()));
		douse();
		if(!game.getGUI().isWithinBounds(getPosition()))
			scrap = true;
	}

	@Override
	public void tick() {
		move();
	}

}
