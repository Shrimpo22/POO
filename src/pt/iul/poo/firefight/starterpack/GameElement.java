package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	Point2D position;
	
	public String getName() {
		return null;
	}

	public Point2D getPosition() {
		return position;
	}
	
	public int getLayer() {
		return 0;
	}

	public void tick() {
		
	}
	
	@Override
	public String toString() {
		return "GameElement ["+getName()+"] | Position: ("+getPosition().getX()+","+getPosition().getY()+") | Layer:"+getLayer();
	}
	

}
