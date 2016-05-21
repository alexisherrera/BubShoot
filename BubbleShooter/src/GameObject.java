import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;


public class GameObject {
	
	//position of the objects
	public int pos_x; 
	public int pos_y;
	
	//size of the objects
	public int width;
	public int height;
	
	//velocity of the objects, if applicable (aka is the shooting ball)
	public int v_x;
	public int v_y;
	
	//maximum values 
	public int max_x;
	public int max_y;
	
	//coordinates of the mouse
	public int xMouseCord;
	public int yMouseCord;
	
	private int xMovement;
	private int yMovement;

	//constructor
	public GameObject(int pos_x, int pos_y, 
			int width, int height, int court_width, int court_height, int v_x, int v_y){
		
		//just initialize the instance variables 
		this.v_x = v_x;
		this.v_y = v_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		
		this.max_x = court_width - width;
		this.max_y = court_height - height;
	}
	
	//alternate constructor for objects not moving (aka no constructor)
	public GameObject(int pos_x, int pos_y, 
			int width, int height, int court_width, int court_height){
		
		//just initialize the instance variables
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		
		this.max_x = court_width - width;
		this.max_y = court_height - height;
	}
	
	public void movementCalc() {
		// Get the vector between the player and the target
	    double pathX = xMouseCord - pos_x;
	    double pathY = yMouseCord - pos_y;

	    // Calculate the unit vector of the path
	    double magnitude = Math.sqrt(pathX * pathX + pathY * pathY);
	    int x = (int) (pathX * v_y / magnitude);
	    int y = (int) (pathY * v_y / magnitude);
	    
	    this.xMovement = x;
	    this.yMovement = y;
	    
	}
	
	
	//update coordinates based on their velocity, if applicable
	public void move(){
		

		pos_x += this.xMovement;
		pos_y += this.yMovement;

		clip();
	}
	
	//clip the position of the objects based on their size
	public void clip(){
		if (pos_x < 0) pos_x = 0;
		else if (pos_x > max_x) pos_x = max_x;

		if (pos_y < 0) pos_y = 0;
		else if (pos_y > max_y) pos_y = max_y;
	}
	
	//checks whether two objects will intersect
	public boolean intersects(GameObject obj){
		return (pos_x + width >= obj.pos_x
				&& pos_y + height >= obj.pos_y
				&& obj.pos_x + obj.width >= pos_x 
				&& obj.pos_y + obj.height >= pos_y);
	}
	
	
	//determines the direction in which the object will go considering
	//it bounces off of something
	public void bounce(Direction d) {
		if (d == null) return;
		switch (d) {
		case UP:    v_y = Math.abs(v_y); break;  
		case DOWN:  v_y = -Math.abs(v_y); break;
		case LEFT:  v_x = Math.abs(v_x); break;
		case RIGHT: v_x = -Math.abs(v_x); break;
		}
	}
	
	//direction where to go if object hits the wall
	public Direction hitWall() {
		if (pos_x + v_x < 0)
			return Direction.LEFT;
		else if (pos_x + v_x > max_x)
			return Direction.RIGHT;
		if (pos_y + v_y < 0)
			return Direction.UP;
		else if (pos_y + v_y > max_y)
			return Direction.DOWN;
		else return null;
	}
	
	//default method
	public void draw(Graphics g) {
	}
	
}
