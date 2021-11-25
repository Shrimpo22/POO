package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Fire.FireUtils;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import debug.Debug;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends Movable{

	private boolean inBulldozer;
	private Bulldozer bulldozer;
	private int layer;
	private static boolean planeActivated = false;
	private Plane plane;

	public Fireman(Point2D position) {
		this.position = position;
		this.inBulldozer=false;
		this.layer=3;
	}

	// Move numa direcao aleatoria 
	@Override
	public void move() {
		int key = ImageMatrixGUI.getInstance().keyPressed();
		if(key==KeyEvent.VK_ESCAPE)System.exit(1);
		if(key == KeyEvent.VK_ENTER) {
			if(inBulldozer) {
				inBulldozer = false;
				layer=3;
				return;
			}
		}else if(key == KeyEvent.VK_P) {
			if(!planeActivated) {
				callPlane();
				planeActivated = true;
			}
		}else if(Direction.isDirection(key)) {
			Direction dir = Direction.directionFor(key);

			Fire fire = (Fire) game.findElement(position.plus(dir.asVector()), 1);
			Bulldozer temp = (Bulldozer) game.findElement(position.plus(dir.asVector()), 2);

			if(fire != null) {
				if(!inBulldozer) {
//					Terrain terrain = (Terrain) game.findElement(position.plus(dir.asVector()), 0);
					fire.douse(dir);
				}
			}else {
				if(temp != null) {
					if(!inBulldozer) {
						inBulldozer = true;
						position = GameEngine.clamp(position.plus(dir.asVector()));
						layer=-1;
					}
					bulldozer = temp;
				}else{
					if(inBulldozer){
						bulldozer.move(dir);
					}
					position = GameEngine.clamp(position.plus(dir.asVector()));
				}
			}
		}
	}


	private void callPlane() {
		plane = new Plane(FireUtils.getCollumn());
		game.addElement(plane);
	}

	public void landPlane() {
		if(planeActivated)
			if(plane.scrap()) {
				game.removeElement(plane);
				planeActivated = false;
			}

	}
	// Verifica se a posicao p esta' dentro da grelha de jogo
	//	public boolean canMoveTo(Point2D p) {
	//
	//		if (p.getX() < 0) return false;
	//		if (p.getY() < 0) return false;
	//		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
	//		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
	//		return true;
	//	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	// Metodos de GameElement
	@Override
	public String getName() {
		return "fireman";
	}

	@Override
	public int getLayer() {
		return layer;
	}
}
