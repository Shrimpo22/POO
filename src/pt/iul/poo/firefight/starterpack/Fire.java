package pt.iul.poo.firefight.starterpack;

import java.util.List;
import java.util.Random;

import debug.Debug;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends Mobile {

	private boolean doused;
	private final Terrain terrain;

	public Fire(Point2D position) {
		super(position, "fire", 1);
		this.doused = false;
		terrain = ((Terrain) game.findElement(position, o->o instanceof Terrain));
	}

	public void douse(Direction dir) {
		switch(dir) {
		case UP : name = "water_up"; break;
		case DOWN : name = "water_down"; break;
		case LEFT : name = "water_left"; break;
		case RIGHT : name = "water_right"; break;

		default: throw new IllegalArgumentException();
		}
		doused = true;
		terrain.douse();
	}

	public boolean doused() {
		return doused;
	}

	@Override
	public boolean equals(Object obj) {

		if(obj == this) {
			return true;

		}
		if(!(obj instanceof Fire)) {
			return false;

		}
		Fire o = (Fire) obj;
		return o.getPosition().equals(this.getPosition());

	}
	
	private void spread() {
		if(doused)
			return;
		terrain.burn();
		if(terrain.burnt()) {
			game.removeElement(this);
		}else
			if(game.getTurn() > 2){
				List<Point2D> neighbours = position.getNeighbourhoodPoints();
				for(Point2D position : neighbours) {
					position = GameEngine.clamp(position);
					if(game.getFireman().getPosition().equals(position)) {
						continue;
					}
					//					Flammable neighbour = (Flammable)game.findElement(position, 0);
					Fire toAdd = new Fire(position);
					Random random = new Random();
					if(!toAdd.terrain.burnt() && toAdd.terrain.getImmunity() == 0 && random.nextInt(20) < toAdd.terrain.getProbability()*20) {
						if(game.getElements(o-> o instanceof Fire).contains(toAdd)) {	
							continue;
						}
						game.addElement(toAdd);
					}
				}
			}
	}

	public static void propagate() {
		List<Fire> fires = game.getElements(o->o instanceof Fire);		
		fires.forEach(n->{Debug.attribute(n, 4); n.spread();});
	}

	public static int getCollumn() {
		int mostFiresCollumn = 0;
		int maxFiresCollumn = 0;
		for(int x = 0; x<GameEngine.GRID_WIDTH; x++) {
			Debug.message("Collumn "+x, 1);
			int currentCollumnCount = 0;
			for(int y = 0; y<GameEngine.GRID_HEIGHT; y++) {
				Fire fire = (Fire) game.findElement(new Point2D(x,y), o->o instanceof Fire);
				if( fire != null && !fire.doused) {
					currentCollumnCount ++;
					Debug.line2(1);
					Debug.attribute(fire, 1);
				}
				//					Debug.line2(1);
			}
			if(currentCollumnCount >= maxFiresCollumn) {
				mostFiresCollumn = x;
				maxFiresCollumn = currentCollumnCount;
			}
		}		
		Debug.attribute("Collumn with most fires: ",mostFiresCollumn, 1);
		return mostFiresCollumn;
	}
}

