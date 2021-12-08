package pt.iul.poo.firefight.starterpack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.image.BufferStrategy;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import java.awt.event.MouseEvent;

public class Menu extends Canvas implements Runnable{
	private static final long serialVersionUID = -7071532049979466544L;

	private Thread thread;
	private boolean running = false;
	public static final int WIDTH=640, HEIGHT=WIDTH/12*9;
	
	
	private Color colorbg = Color.decode("#360701");
	private Color colorst = Color.decode("#FFC100");

	private MenuListener menuListener;
	private Window w;
	private BGLine line;


	public static void main(String[] args) {
		new Menu();

	}

	public Menu() {
		w =new Window(WIDTH,HEIGHT,"Firefight!",this);
		menuListener = new MenuListener();
		this.addMouseListener(menuListener);
		line = new BGLine();
	}


	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta =0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime =now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();

			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
			}
		}
		stop();
	}

	private void tick() {
		menuListener.tick();
		line.tick();
	}


	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		g.setColor(colorbg);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		line.render(g);
		menuListener.render(g);
		g.dispose();
		bs.show();
	}

	private class MenuListener extends MouseAdapter{

		public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();
			System.out.println(mx);
			System.out.println(my);

			if(mouseOver(mx, my, 210, 150, 200, 64)) {
				w.setVisibility(false);
				ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
				gui.setSize(10, 10);

				GameEngine game = GameEngine.getInstance();
				gui.registerObserver(game);
				game.readLevel("level1.txt");
			}
		}

		public void mouseReleased(MouseEvent e) {

		}

		private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
			if(mx > x && mx < x+ width) {
				if(my > y && my < y+height) {
					return true;
				} else return false;
			}else return false;
		}

		public void tick() {

		}

		public void render(Graphics g) {
			
			g.setColor(colorst);
			Font fnt = new Font("arial", 1, 50);
			g.setFont(fnt);
			g.drawString("Menu", 245, 70);
			g.drawString("Play", 260, 200);
		}
	}

}

