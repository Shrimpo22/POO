package pt.iul.poo.firefight.starterpack;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;


public class Window extends Canvas{

	private static final long serialVersionUID = -6357664197674207224L;
	private static JFrame frame;
	
	public Window(int width, int height, String title, Menu menu) {
		
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(menu);
		frame.setVisible(true);
		menu.start();
	}
	
	public void setVisibility(boolean b) {
		frame.setVisible(b);
	}

}

