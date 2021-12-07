package pt.iul.poo.firefight.starterpack;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.*;

public class Trail{
	
	private float alpha = 1;
	private Color color;
	private int width, height;
	private float life;
	private int x, y;
	

	public Trail(int x, int y,Color color, int width, int height,float life) {
		this.x = x;
		this.y = y;
		this.color=color;
		this.width = width;
		this.height = height;
		this.life=life;
	}


	public void tick() {
		if(alpha > life) {
			alpha-=life-0.001;
		}
		
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public float getLife() {
		return life;
	}


	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
	
		g.setColor(color);
		g.fillRect(x,y,width,height);
	
		g2d.setComposite(makeTransparent(1));

		
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public Rectangle getBounds() {
	
		return null;
	}

}