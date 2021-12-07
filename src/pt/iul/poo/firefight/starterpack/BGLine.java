package pt.iul.poo.firefight.starterpack;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

public class BGLine {
	private Color linecolor = Color.decode("#DA0000");//#DA0000
	private Color trailcolor = Color.decode("#DA0000");//#F66806
	
	
	
	WavePrint sw = new WavePrint();
	private int x, y;
	private int velY;
	private List<Trail> trails = new ArrayList<>();
	private Random random = new Random();
	private int count = 10;


	public BGLine() {
		x=0;
		y=-4;
		velY=4;
		sw.setCycles(10);
	}


	public void tick() {
		if(count == 0) {
			sw.setCycles(random.nextInt(6)+2);
		count = 10;
		}
		count--;
		
		y += velY;

		if(y < -4 || y >= Menu.HEIGHT-40) velY*=-1;

		trails.add(new Trail(x,y,trailcolor,Menu.WIDTH, 4, 0.008f));
		trails.forEach(o->o.tick());
		trails.removeIf(o->o.getAlpha()<o.getLife());
//		sw.setCycles(4);
		//		sw.setCycles(6);
		sw.tick();



	}



	public void render(Graphics g) {

		sw.paintComponent(g);
		g.setColor(linecolor);

		trails.forEach(o->o.render(g));

	}


	public class WavePrint {
		int SCALEFACTOR = 200;
		int cycles;
		int points;
		double[] sines;
		int[] pts;
		private int y = 0;
		private int velY = 4;

		public  void setCycles(int cycles) {
			this.cycles = cycles;
			this.points = SCALEFACTOR * cycles * 2;
			this.sines = new double[points];
			for (int i = 0; i < points; i++) {
				double radians = (Math.PI / SCALEFACTOR) * i;
				this.sines[i] = Math.sin(radians);
			}
		}

		public void tick() {
			y += velY;

			if(y < -4 || y >= Menu.HEIGHT-40) velY*=-1;
		}


		public void paintComponent(Graphics g) {
			int maxWidth = Menu.WIDTH;
			double hstep = (double) maxWidth / (double) points;
			int maxHeight = 6;
			pts = new int[points];
			for (int i = 0; i < points; i++){
				pts[i] = (int) (sines[i] * maxHeight / 2 * .95 + maxHeight / 2);
			}
			g.setColor(linecolor);
			for (int i = 1; i < points; i++) {
				int x1 = (int) ((i - 1) * hstep);
				int x2 = (int) (i * hstep);
				int y1 = pts[i - 1]+y;
				int y2 = pts[i]+y;
				g.drawLine(x1, y1-10, x2, y2+10);
			}
		}

	}
}
