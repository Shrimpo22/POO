package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Launcher {
	public static void main(String[] args) {
		
		//Debug.on();
		Debug.off();
		
		// Cria uma instancia de GameEngine e depois inicia o jogo
		// Podera' vir a ficar diferente caso defina GameEngine como solitao 
		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
		gui.setSize(10, 10);
		gui.go();
		
		GameEngine game = new GameEngine();
		gui.registerObserver(game);
		game.readLevel("example.txt");
		game.start();
		
	}
}
