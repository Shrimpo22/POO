package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	private String name;
	private Point2D position;
	private int layer;

	static final GameEngine game = GameEngine.getInstance();



	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void tick() {

	}

	@Override
	public String toString() {
		return "GameElement ["+getName()+"] | Position: ("+getPosition().getX()+","+getPosition().getY()+") | Layer:"+getLayer();
	}

}
