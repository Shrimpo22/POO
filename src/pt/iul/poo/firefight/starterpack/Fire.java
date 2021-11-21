package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends Movable {
	
	private int doused = 0;
	private String name = "fire";

	public Fire(Point2D position) {
		this.position = position;
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
		doused = 1;
		Terrain terrain = (Terrain) GameEngine.findElement(position, 0);
		terrain.douse();
	}
	
	public int doused() {
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


}
