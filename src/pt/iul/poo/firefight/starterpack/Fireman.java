package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends GameElement{
	
	public Fireman(Point2D position) {
		super(position);
	}
	
	// Move numa direcao aleatoria 
	public void move() {
		
		boolean hasMoved = false;
		
		while (hasMoved == false)  {
		
			Direction randDir = Direction.random();
			Point2D newPosition = position.plus(randDir.asVector());
			
			if (canMoveTo(newPosition) ) {
				setPosition(newPosition);
				hasMoved = true;
			}
		}
	}

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
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
		return 3;
	}
}
