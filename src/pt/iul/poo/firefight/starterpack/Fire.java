package pt.iul.poo.firefight.starterpack;

import java.util.List;
import java.util.Random;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends Mobile {

	private boolean doused;
	private Terrain terrain;

	public Fire(Point2D position) {
		super(position, "fire", 1);
		this.doused = false;
		terrain = ((Terrain) game.findElement(position, o->o instanceof Terrain));
	}

	public void douse(Direction dir) {
		switch(dir) {
		case UP : setName("water_up"); break;
		case DOWN : setName("water_down"); break;
		case LEFT : setName("water_left"); break;
		case RIGHT : setName("water_right"); break;

		default: throw new IllegalArgumentException();
		}
		doused = true;
		terrain.douse();
	}

	public Terrain getTerrain() {
		return terrain;
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

	public void spread(List<Point2D> neighbours) {

		if(doused)
			return;
		terrain.burn();
		if(terrain.burnt()) {
			game.removeElement(this);
		}else if(game.getTurn() > 1){
			for(Point2D position : neighbours) {
				position = GameEngine.clamp(position);
				if(game.findElement(position, o-> o instanceof Fireman || o instanceof FiremanBot) != null) {
					continue;
				}
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

		fires.forEach(n-> n.spread(n.getPosition().getNeighbourhoodPoints()));
	}

	public static int getCollumn() {
		int mostFiresCollumn = 0;
		int maxFiresCollumn = 0;
		for(int x = 0; x<GameEngine.GRID_WIDTH; x++) {
			int currentCollumnCount = 0;
			for(int y = 0; y<GameEngine.GRID_HEIGHT; y++) {
				Fire fire = (Fire) game.findElement(new Point2D(x,y), o->o instanceof Fire);
				if( fire != null && !fire.doused) {
					currentCollumnCount ++;
				}
			}
			if(currentCollumnCount >= maxFiresCollumn) {
				mostFiresCollumn = x;
				maxFiresCollumn = currentCollumnCount;
			}
		}		
		return mostFiresCollumn;
	}
}

