package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import debug.Debug;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends Movable {

	private boolean doused;
	private String name = "fire";

	public Fire(Point2D position) {
		this.position = position;
		this.doused = false;
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
		Terrain terrain = (Terrain) GameEngine.findElement(position, 0);
		terrain.douse();
	}

	public boolean doused() {
		return doused;
	}

	public static void spread() {
		Spread.propagate();
	}
	
	public static void putOut() {
		Spread.removeAllDoused();
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

	

	private static class Spread {
		private static List<Fire> fires;
		private static List<Fire> firesToRemove = new ArrayList<>();
		private static List<Fire> firesToAdd = new ArrayList<>();

		private static void propagate() {
			fires.forEach(n->{Debug.attribute(n, 4); Spread.spread(n);});
			organizeFires();
		}
		
		private static void spread(Fire fire) {
			Flammable temp = (Flammable)GameEngine.findElement(fire.getPosition(), 0);
			temp.burn();
			if(temp.burnt()) {
				firesToRemove.add(fire);
				GameEngine.removeElement(fire);
			}else if(GameEngine.getTurn() > 2){
						List<Point2D> neighbours = fire.getPosition().getNeighbourhoodPoints();
						for(Point2D position : neighbours) {
							position = GameEngine.clamp(position);
							if(GameEngine.findElement(position, 3) != null) {
								continue;
							}
							Terrain neighbour = (Terrain)GameEngine.findElement(position, 0);
							Random random = new Random();
							if(!neighbour.burnt() && neighbour.getImmunity() == 0 && random.nextInt(20) < neighbour.getProbability()*20) {
								Fire propagate = new Fire(position);
								if(fires.contains(propagate) || firesToRemove.contains(propagate) || firesToAdd.contains(propagate)) {	
									continue;
								}
								firesToAdd.add(propagate);
							}
						}
					}
		}
		
		private static void organizeFires() {
			firesToRemove.forEach(n-> {fires.remove(n); GameEngine.removeElement(n);});
			firesToRemove.clear();

			firesToAdd.forEach(n -> {fires.add(n); GameEngine.addElement(n);});
			firesToAdd.clear();
		}
	
		private static void removeAllDoused() {
			if(fires == null) {
				initializeFires();
			}
			fires.forEach(n->{if(n.doused()) GameEngine.removeElement(n);});
			fires.removeIf(n->n.doused());
		}
		
		private static void initializeFires() {
			fires=GameEngine.getFires();
		}
	}
}
