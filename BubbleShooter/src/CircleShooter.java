import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CircleShooter extends GameObject {
	
	private Color color;
	public static final int SIZE = 20;
	public static final int INIT_POS_X = 210;
	public static final int INIT_POS_Y = 360;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	
	//constructor
	public CircleShooter(int courtWidth, int courtHeight, Color color) {
		
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, 
			  INIT_VEL_X, INIT_VEL_Y);
		this.color = color;
	}
	
	//return the color
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
