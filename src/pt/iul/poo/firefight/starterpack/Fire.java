package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import debug.Debug;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends Movable {

	private boolean doused;
	private String name = "fire";
	private final Terrain terrain;
	
	public Fire(Point2D position) {
		this.position = position;
		this.doused = false;
		terrain = ((Terrain) game.findElement(position, 0));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLayer() {
		return 1;
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



	public static class FireUtils {
		private static List<Fire> fires;
		private static List<Fire> firesToRemove = new ArrayList<>();
		private static List<Fire> firesToAdd = new ArrayList<>();

		public static void propagate() {
			fires.forEach(n->{Debug.attribute(n, 4); FireUtils.spread(n);});
			organizeFires();
		}

		private static void spread(Fire fire) {
			
			fire.terrain.burn();
			if(fire.terrain.burnt()) {
				firesToRemove.add(fire);
				game.removeElement(fire);
			}else
				if(game.getTurn() > 2){
					List<Point2D> neighbours = fire.getPosition().getNeighbourhoodPoints();
					for(Point2D position : neighbours) {
						position = GameEngine.clamp(position);
						if(game.findElement(position, 3) != null) {
							continue;
						}
//						Flammable neighbour = (Flammable)game.findElement(position, 0);
						Fire toAdd = new Fire(position);
						Random random = new Random();
						if(!toAdd.terrain.burnt() && toAdd.terrain.getImmunity() == 0 && random.nextInt(20) < toAdd.terrain.getProbability()*20) {
//							Fire propagate = new Fire(position);
							if(fires.contains(toAdd) || firesToRemove.contains(toAdd) || firesToAdd.contains(toAdd)) {	
								continue;
							}
							firesToAdd.add(toAdd);
						}
					}
				}
		}

		private static void organizeFires() {
			firesToRemove.forEach(n-> {fires.remove(n); game.removeElement(n);});
			firesToRemove.clear();

			firesToAdd.forEach(n -> {fires.add(n); game.addElement(n);});
			firesToAdd.clear();
		}

		public static void removeAllDoused() {
			if(fires == null) {
				initializeFires();
			}
			fires.forEach(n->{if(n.doused()) game.removeElement(n);});
			fires.removeIf(n->n.doused());
		}

		private static void initializeFires() {
			fires=game.getFires();
		}

		public static void PutOut(Point2D first, Point2D second) {
			fires.forEach(n->{if(n.getPosition().equals(first) || n.getPosition().equals(second)) {Debug.check("OK", 1); game.removeElement(n);}});
			fires.removeIf(n->n.getPosition().equals(first) || n.getPosition().equals(second));
		}

		public static int getCollumn() {
			int mostFiresCollumn = 0;
			int maxFiresCollumn = 0;
			for(int x = 0; x<GameEngine.GRID_WIDTH; x++) {
				Debug.message("Collumn "+x, 1);
				int currentCollumnCount = 0;
				for(int y = 0; y<GameEngine.GRID_HEIGHT; y++) {
					Fire fire = (Fire) game.findElement(new Point2D(x,y), 1);
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
			return mostFiresCollumn;
		}




	}
}
