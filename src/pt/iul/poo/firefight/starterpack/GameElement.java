package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile, Tickable{
	protected String name;
	protected Point2D position;
	protected int layer;
	
	static final GameEngine game = GameEngine.getInstance();
	
	
	
	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;
	}
	
	public String getName() {
		return name;
	}

	public Point2D getPosition() {
		return position;
	}
	
	public int getLayer() {
		return layer;
	}

	public void tick() {
		
	}
	
	@Override
	public String toString() {
		return "GameElement ["+getName()+"] | Position: ("+getPosition().getX()+","+getPosition().getY()+") | Layer:"+getLayer();
	}
	
}
