package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Plane extends GameElement implements Tickable{
	private boolean scrap;
	
	Plane(int collumn){
		this.position = new Point2D(collumn, 11);
		scrap = false;
	}

	public String getName() {
		return "plane";
	}

	public int getLayer() {
		return 4;
	}
	
	public boolean scrap() {
		return scrap;
	}

	private void douse() {
		Point2D downTile = position.plus(Direction.DOWN.asVector());
		Point2D down2Tile = downTile.plus(Direction.DOWN.asVector());

		Debug.attribute("DownTile: ", downTile, 1);
		Debug.attribute("Down2Tile: ", down2Tile, 1);
		if(GameEngine.getGUI().isWithinBounds(downTile)) {
			Fire fire1 = (Fire) game.findElement(downTile, 1);
			if(fire1 != null)
				fire1.douse(Direction.DOWN);
		}
		if(GameEngine.getGUI().isWithinBounds(down2Tile)) {
			Fire fire2 = (Fire) game.findElement(down2Tile, 1);
			if(fire2 != null)
				fire2.douse(Direction.DOWN);
		}

	}

	public void move() {
		position = position.plus(Direction.UP.asVector()).plus(Direction.UP.asVector());
		douse();
		Debug.attribute("Plane Position", position, 1);
		if(!GameEngine.getGUI().isWithinBounds(position))
			scrap = true;
	}

	@Override
	public void tick() {
		move();
	}
}
