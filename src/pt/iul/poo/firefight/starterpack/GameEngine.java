package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Fire.FireUtils;

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

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador e' feito no construtor 
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	//	public void start() {
	//		 
	//		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
	//		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
	//		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
	//		gui.go();                              // 4. lancar a GUI
	//		
	//		tileList = new ArrayList<>();   
	//	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado (neste caso seria a GUI)

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}


	@Override
	public void update(Observed source) {

		Debug.line(true);
		removeRubble();
		fireman.move();
		tick();
		Debug.message("Player Moved", true);

		FireUtils.propagate();

		//		fires.forEach(n -> {Debug.line2(1); Debug.attribute("Fire!", 1);});

		gui.update();  
		turn ++;
		Debug.attribute("Turn Number", turn, true);
		// redesenha as imagens na GUI, tendo em conta as novas posicoes
	}


	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		//		createTerrain();      // criar mapa do terreno
		//		createMoreStuff();    // criar mais objetos (bombeiro, fogo,...)
		sendImagesToGUI();    // enviar as imagens para a GUI
		getFires();
	}



	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(elementList);
	}

	// Mï¿½todo para ler um nï¿½vel

	public void readLevel(String file){
		try {
			File level = new File(file);
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
						elementList.add(fireman = (Fireman)Movable.generate(Object, new Point2D(x,y)));
					}else {
						elementList.add(Movable.generate(Object, new Point2D(x,y)));
					}
				}
				numLine ++;
			}
			lvl.close();

		}catch(FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");

		}
	}

	// Mï¿½todo para os objetos nï¿½o saï¿½rem da janela

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

	public List<Fire> getFires(){
		List<Fire> fires = new ArrayList<>();
		for(ImageTile ge: elementList)
			if(ge instanceof Fire) {
				fires.add((Fire)ge);
				Debug.attribute("FiresList", ge, false);
			}
		return fires;
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

	public static ImageMatrixGUI getGUI() {
		return gui;
	}

	public GameElement findElement(Point2D position, int layer) {
		//				Debug.check("findElement", 1);
		for(ImageTile ge : elementList) {
			//			Debug.check("For", 1);
			if(ge.getLayer() == layer) {
				//				Debug.check("Flammable", 1);
				//				Debug.equals(ge.getPosition(), position, 1);
				if(ge.getPosition().equals(position)) {
					//					Debug.attribute("Element found", ge, 1);
					return (GameElement) ge;
				}
			}
		}
		return null;
	}

	private void removeRubble() {
		FireUtils.removeAllDoused();
		fireman.landPlane();

	}

	public void tick() {
		for(ImageTile ge : elementList)
			((GameElement) ge).tick();	
	}
}
