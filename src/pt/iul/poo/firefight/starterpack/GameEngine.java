package pt.iul.poo.firefight.starterpack;

import debug.*;
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

	// Pontos referentes �s extremidades da grelha de jogo

	private static final Point2D min = new Point2D(0,0);
	private static final Point2D max = new Point2D(GameEngine.GRID_WIDTH - 1, GameEngine.GRID_HEIGHT - 1);


	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> elementList = new ArrayList<>() ;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro


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
	@Override
	public void update(Observed source) {

		Debug.Message("Player Moved", true);
		fireman.move();
		Debug.Check("Before Fire List", false);
//		List<Fire> fires =  getFires();
//		if(!fires.isEmpty()) {
//			for(Fire fire : fires) {
//				Flammable temp = findElement(fire.getPosition());
//				temp.burn();
//				if(temp.burnt()==1)
//					fires.remove(fire);
//				else if(turn > 2){
//					//Propagar Fogo
//				}
//			}
//
//		}
		gui.update();  
		turn ++;
		Debug.Attribute("Turn Number", turn, true);
		// redesenha as imagens na GUI, tendo em conta as novas posicoes
	}


	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		//		createTerrain();      // criar mapa do terreno
		//		createMoreStuff();    // criar mais objetos (bombeiro, fogo,...)
		sendImagesToGUI();    // enviar as imagens para a GUI
	}


	// Criacao do terreno - so' pinheiros neste exemplo 
	//	private void createTerrain() {
	//
	//	}


	// Criacao de mais objetos - neste exemplo e' um bombeiro e dois fogos
	//	private void createMoreStuff() {
	//					fireman = new Fireman( new Point2D(5,5));
	//					tileList.add(fireman);
	//					
	//					tileList.add(new Fire(new Point2D(3,3)));
	//					tileList.add(new Fire(new Point2D(3,2)));
	//	}


	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		Debug.Check("sendImagesGUI", false);
		gui.addImages(elementList);
		Debug.Message("Images Sent to GUI", false);
	}

	// M�todo para ler um n�vel

	public void readLevel(String file){
		try {
			File level = new File(file);
			Scanner lvl = new Scanner(level);
			int numLine = 0;

			while(lvl.hasNextLine()) {

				if(numLine<10) {
					String temp = lvl.nextLine();
					Debug.Attribute("Line Number 1", numLine, false);

					for(int x=0; x<10; x++) {
						char Object = temp.charAt(x);
						Point2D ObjectPosition = new Point2D(x,numLine);
						elementList.add(Terrain.generate(Object, ObjectPosition));
					}

				}else {
					Debug.Attribute("Line Number 2", numLine, false);
					String Object = lvl.next();
					Debug.Attribute("Object is", Object, false);
					int x = lvl.nextInt();
					int y = lvl.nextInt();

					Debug.Attribute("X", x, false);
					Debug.Attribute("Y", y, false);


					if(Object.equals("Fireman")){
						elementList.add(fireman = (Fireman)Movable.generate(Object, new Point2D(x,y)));
						Debug.Message(Object + "Created", false);
					}else {
						elementList.add(Movable.generate(Object, new Point2D(x,y)));
						Debug.Message(Object + "Created", false);
					}
				}
				numLine ++;
			}
			lvl.close();

		}catch(FileNotFoundException e) {
			System.out.println("Ficheiro n�o encontrado");

		}
	}

	// M�todo para os objetos n�o sa�rem da janela

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
		Debug.Check("getFires", false);
		List<Fire> fires = new ArrayList<>();		
		Debug.Check("getFires list created", false);
		for(ImageTile ge: elementList)
			if(ge instanceof Fire) {
				fires.add((Fire)ge);
				Debug.Attribute("FiresList", ge, false);
			}

		return fires;
	}

	public Flammable findElement(Point2D position) {
		for(ImageTile ge : elementList)
			if(ge instanceof Flammable)
				if(ge.getPosition() == position)
					return (Flammable) ge;
		return null;
	}
}
