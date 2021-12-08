package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	private int turn = 0;
	private int LevelPoints;
	private int PersonalPoints;
	private int TreesLeft = 0;

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	// Pontos referentes ï¿½s extremidades da grelha de jogo

	private static final Point2D min = new Point2D(0,0);
	private static final Point2D max = new Point2D(GameEngine.GRID_WIDTH - 1, GameEngine.GRID_HEIGHT - 1);


	private static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();  // Referencia para ImageMatrixGUI (janela de interface com o utilizador) 



	private List<ImageTile> elementList = new ArrayList<>() ;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private static GameEngine INSTANCE;
	private String username; 
	private int lvl;

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	@Override
	public void update(Observed source) {




		int key = ImageMatrixGUI.getInstance().keyPressed();
		if(key==KeyEvent.VK_ESCAPE)System.exit(1);
		if(key==KeyEvent.VK_J)getElements(o->o instanceof Fire).forEach(o->removeElement((Fire)o));
		if(key==KeyEvent.VK_F)
			for(int i = 0; i<GRID_WIDTH; i++) {
				for( int j=0; j<GRID_HEIGHT; j++){
					addElement(new Fire(new Point2D(i,j)));
				}
			}

		removeRubble();
		fireman.move();
		tick();
		Fire.propagate();
		gameOver();


		PersonalPoints = fireman.reward();
		gui.setStatusMessage("Score: "+PersonalPoints);

		gui.update(); 



		turn ++;
		// redesenha as imagens na GUI, tendo em conta as novas posicoes
	}

	public void start() {
		//		createTerrain();      // criar mapa do terreno
		//		createMoreStuff();    // criar mais objetos (bombeiro, fogo,...)
		sendImagesToGUI();    // enviar as imagens para a GUI
		//		getFires();

	}

	private void sendImagesToGUI() {
		gui.addImages(elementList);
	}

	private void gameOver() {
		if(getElements(o->o instanceof Fire).isEmpty()) {
			username = JOptionPane.showInputDialog("Input username");
			List<Terrain> terrains_left = getElements(o->o instanceof Terrain && !((Terrain) o).burnt() && !(((Terrain) o) instanceof Land));
			terrains_left.forEach(o->{LevelPoints += o.reward(); TreesLeft ++;});

			getStats();
			Scoreboard levelScore = new Scoreboard(lvl);
			levelScore.add(username+"|"+LevelPoints+"|"+PersonalPoints);

			gui.clearImages();
			elementList.clear();
			lvl++;
			if(lvl <= 6)
				readLevel("levels/level"+lvl+".txt");
			else {
				
				gui.dispose();
				System.exit(-1);
			}
		}
	}

	private void getStats() {
		int[] stats = fireman.getStats();
		System.out.println("You've put out: "+ stats[0] + " fires!		Score:"+stats[0]*50);
		System.out.println("You've demolished: "+ stats[1] + " tiles of land!		Score:"+stats[1]*-25);
		System.out.println("You've called: "+ stats[2] + " planes!");
		System.out.println("Your planes have doused a total of: "+ stats[3] + " fires!		Score:"+stats[3]*50);
		System.out.println("A total of: "+ TreesLeft + " trees survived!		Score:"+LevelPoints);
	}

	public void readLevel(String file){
		try {
			File level = new File(file);
			lvl = file.charAt(12)-48;
			LevelPoints = 0;
			PersonalPoints = 0;
			gui.setStatusMessage("Score: 0");
			turn = 0;
			Scanner lvl = new Scanner(level);
			int numLine = 0;

			while(lvl.hasNextLine()) {

				if(numLine<10) {
					String temp = lvl.nextLine();

					for(int x=0; x<10; x++) {
						char Object = temp.charAt(x);
						Point2D ObjectPosition = new Point2D(x,numLine);
						elementList.add(Terrain.generate(Object, ObjectPosition));
					}

				}else {
					String Object = lvl.next();
					int x = lvl.nextInt();
					int y = lvl.nextInt();

					if(Object.equals("Fireman")){
						elementList.add(fireman = (Fireman)Mobile.generate(Object, new Point2D(x,y)));
					}else {
						elementList.add(Mobile.generate(Object, new Point2D(x,y)));
					}
				}
				numLine ++;
			}
			LevelPoints = 0;
			lvl.close();

			gui.go();
			start();

		}catch(FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");

		}
	}

	public static Point2D clamp(Point2D p) {

		Point2D result;
		int x = p.getX();
		int y = p.getY();

		if(p.getX() >= max.getX())
			x = max.getX();
		else if(p.getX() <= min.getX())
			x = min.getX();

		if(p.getY() >= max.getY())
			y = max.getY();
		else if(p.getY() <= min.getY())
			y = min.getY();

		result = new Point2D(x, y);

		return result;
	}


	@SuppressWarnings("unchecked")
	public <T> List<T> getElements(Predicate<GameElement> pred){
		List<T> elements = new ArrayList<>();
		for(ImageTile ge: elementList) {
			if(pred.test((GameElement) ge)) 
				elements.add((T) ge);
		}
		return elements;
	}


	public int getTurn() {
		return turn;
	}


	public void addElement(GameElement object) {
		elementList.add(object);
		gui.addImage(object);
	}


	public void removeElement(GameElement object) {
		elementList.remove(object);
		gui.removeImage(object);
	}


	public <T> void removeElements(List<T> toRemove) {
		elementList.removeAll(toRemove);
		toRemove.forEach(o-> gui.removeImage((ImageTile)o));
	}


	public ImageMatrixGUI getGUI() {
		return gui;
	}


	public GameElement findElement(Point2D position, Predicate<GameElement> pred) {
		for(ImageTile ge : elementList) {
			if(pred.test((GameElement)ge)) {
				if(ge.getPosition().equals(position)) {
					return (GameElement) ge;
				}
			}
		}
		return null;
	}


	private void removeRubble() {
		removeElements(getElements(o->o instanceof FuelBarrel && ((FuelBarrel) o).burnt()));
		removeElements(getElements(o -> o instanceof Fire && ((Fire) o).doused()));
		removeElements(getElements(o -> o instanceof Plane && ((Plane) o).scrap()));
	}


	public void tick() {
		for(ImageTile ge : elementList)
			if(ge instanceof Tickable)
				((Tickable) ge).tick();	
	}


	public Fireman getFireman() {
		return fireman;
	}


	public void setFireman(Fireman fireman) {
		this.fireman = fireman;
	}

}
