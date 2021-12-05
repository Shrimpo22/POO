package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.awt.event.KeyEvent;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends Mobile implements Rewardable{

	private int reward;
	private boolean inBulldozer;
	private Bulldozer bulldozer;
	private boolean planeActivated;
	private Plane plane;


	private int douseCounter = 0;
	private int planesCounter = 0;

	public Fireman(Point2D position) {
		super(position, "fireman", 3);
		this.inBulldozer=false;
		this.planeActivated = false;
		this.reward = 0;
	}

	// Move numa direcao aleatoria 
	@Override
	public void move() {
		int key = ImageMatrixGUI.getInstance().keyPressed();
		if(plane != null && plane.scrap()) {
			planeActivated = false;
			plane =null;
		}
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
				planesCounter ++;
			}
		}else if(Direction.isDirection(key)) {
			Direction dir = Direction.directionFor(key);

			Fire fire = (Fire) game.findElement(position.plus(dir.asVector()), o->o instanceof Fire);
			Bulldozer temp = (Bulldozer) game.findElement(position.plus(dir.asVector()), o->o instanceof Bulldozer);

			if(fire != null) {
				if(!inBulldozer) {
					//					Terrain terrain = (Terrain) game.findElement(position.plus(dir.asVector()), 0);
					fire.douse(dir);
					douseCounter ++;
					reward += 50;
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
						reward -= 25;
					}
					position = GameEngine.clamp(position.plus(dir.asVector()));
				}
			}
		}
	}

	private void callPlane() {
		plane = new Plane(Fire.getCollumn(), this);
		game.addElement(plane);
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void calculateReward(int number) {
		reward += number;
	}

	public int reward() {
		return reward;
	}

	public int[] getStats() {
		int[] stats = {douseCounter, Bulldozer.getDemolishes(), planesCounter, Plane.getDouses()};
		return stats;
	}


}
