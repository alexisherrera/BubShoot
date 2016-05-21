import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CircleInPlace extends GameObject{

	private Color color;
	public static final int SIZE = 20;
	
	//constructor
	public CircleInPlace(int courtWidth, int courtHeight, Color color, 
						 int INIT_POS_X, int INIT_POS_Y) {
		
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	//draw method to color this little balls
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(pos_x, pos_y, width, height);
	}
}
