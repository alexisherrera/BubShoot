import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	public CircleShooter shooter;
	public CircleInPlace [][] ballHolder;
	private JLabel status;
	private int iCoordinate;
	private int jCoordniate;
	private int iNew;
	private int jNew;
	private int updater;
	private int score;
	private int overkeeper;
	private String username;
	private JLabel scoreLabel;
	private Map<Integer, String> highScores;
	public boolean playing = false;
	public static final int COURT_WIDTH = 430;
	public static final int COURT_HEIGHT = 400;
	public static final int INTERVAL = 35;
	
	
	public GameCourt(JLabel status, JLabel score) {
		
		
		 this.scoreLabel = score; 
		 this.status = status;

		//put a border around the game
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//set the timer
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		
		//start the timer
		timer.start();
		
		//focus clicks on the Gamecourt
		setFocusable(true);
		
		//add click listeners to the Game
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				
				
				shooter.xMouseCord = e.getX();
				shooter.yMouseCord = e.getY();
				shooter.v_y = 10;
				shooter.movementCalc();	
	
		
			}
		});
		
		this.status = status;
	}
	
	//reset function for testing purposes
	public void resetTesting() {

		this.ballHolder = new CircleInPlace [17][15];
		
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 8; j++) {
				
				double colorChooser = Math.random();
				Color color;
				
				if (colorChooser <= 0.2) {
					color = Color.BLUE;
				}
				
				else if (colorChooser > 0.2 && colorChooser <= 0.4) {
					color = Color.RED;
				}
				
				else if (colorChooser > 0.4 && colorChooser <= 0.6) {
					color = Color.ORANGE;
				}
				
				else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
					color = Color.magenta;
				}
				
				else { color = Color.PINK; }
				
				
				this.ballHolder[i][j] = new CircleInPlace(this.COURT_WIDTH, 
										this.COURT_HEIGHT, color, 
										0 + (i * 25), 0 + (j * 25));
			}
		}
		
		double colorChooser = Math.random();
		Color color;
		
		if (colorChooser <= 0.2) {
			color = Color.BLUE;
		}
		
		else if (colorChooser > 0.2 && colorChooser <= 0.4) {
			color = Color.RED;
		}
		
		else if (colorChooser > 0.4 && colorChooser <= 0.6) {
			color = Color.ORANGE;
		}
		
		else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
			color = Color.magenta;
		}
		
		else { color = Color.PINK; }
		
		this.shooter = new CircleShooter(this.COURT_WIDTH, this.COURT_HEIGHT, 
										 color);
		
		
		this.updater = 0;
		this.score   = 0;
		this.scoreLabel.setText("Score: 0");
		playing = true;
		status.setText("Shoot!");
	}
	
	//reset function
	public void reset() {
	 
		this.ballHolder = new CircleInPlace [17][15];
		
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 8; j++) {
				
				double colorChooser = Math.random();
				Color color;
				
				if (colorChooser <= 0.2) {
					color = Color.BLUE;
				}
				
				else if (colorChooser > 0.2 && colorChooser <= 0.4) {
					color = Color.RED;
				}
				
				else if (colorChooser > 0.4 && colorChooser <= 0.6) {
					color = Color.ORANGE;
				}
				
				else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
					color = Color.magenta;
				}
				
				else { color = Color.PINK; }
				
				
				this.ballHolder[i][j] = new CircleInPlace(this.COURT_WIDTH, 
										this.COURT_HEIGHT, color, 
										0 + (i * 25), 0 + (j * 25));
			}
		}
		
		double colorChooser = Math.random();
		Color color;
		
		if (colorChooser <= 0.2) {
			color = Color.BLUE;
		}
		
		else if (colorChooser > 0.2 && colorChooser <= 0.4) {
			color = Color.RED;
		}
		
		else if (colorChooser > 0.4 && colorChooser <= 0.6) {
			color = Color.ORANGE;
		}
		
		else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
			color = Color.magenta;
		}
		
		else { color = Color.PINK; }
		
		this.shooter = new CircleShooter(this.COURT_WIDTH, this.COURT_HEIGHT, 
										 color);
		
		
		this.updater = 0;
		this.score   = 0;
		this.overkeeper = 0;
		this.scoreLabel.setText("Score: 0");
		this.playing = true;
		this.highScores = new TreeMap<>();
		status.setText("Shoot!");
		
		JTextField user1 = new JTextField();
		
		JOptionPane.showMessageDialog(null, user1, "Username", 
									  JOptionPane.PLAIN_MESSAGE);
		
		this.username = user1.getText();
		
		JOptionPane.showMessageDialog(user1, "This is BubbleShooter! Shoot the "
				+ "bubble by"
				+ " clicking on a position for the ball" + "\n" + "in the bottom "
				+ "to go." + 
				"If there are three other balls"
				+ " adjacent (not diagonally, " + "\n" + "however) to where the" 
						+ "balls land you will win points! 10 points for "
						+ "each pop!");	
		
		requestFocusInWindow();
	 
	}
	
	//add the ball to the double array
	void addBallToArray() {
		
		int x_val = this.ballHolder[this.iCoordinate][this.jCoordniate].pos_x;

		if (shooter.pos_x >= (x_val - 7) && shooter.pos_x <= (x_val + 7)) {
			
			
			this.ballHolder[this.iCoordinate][this.jCoordniate + 1] = 
					new CircleInPlace(this.COURT_WIDTH, this.COURT_HEIGHT, 
							this.shooter.getColor(), 
							this.ballHolder[this.iCoordinate]
									[this.jCoordniate].pos_x,
							this.ballHolder[this.iCoordinate]
									[this.jCoordniate].pos_y + 25);
			
			this.iNew = this.iCoordinate;
			this.jNew = this.jCoordniate + 1;
		}
		
		else if (shooter.pos_x < (x_val - 7) && shooter.pos_x >= (x_val - 20)) {
			
			if ((this.iCoordinate - 1 >= 0) && 
					this.ballHolder[this.iCoordinate - 1][this.jCoordniate] 
				== null ) {
				
				this.ballHolder[this.iCoordinate - 1][this.jCoordniate] = 
						new CircleInPlace(this.COURT_WIDTH, this.COURT_HEIGHT, 
								this.shooter.getColor(), 
								(this.ballHolder[this.iCoordinate]
										[this.jCoordniate].
								pos_x - 25),
								this.ballHolder[this.iCoordinate]
								[this.jCoordniate].pos_y);
				
				this.iNew = this.iCoordinate - 1;
				this.jNew = this.jCoordniate;
			}
			
			else {
				
				this.ballHolder[this.iCoordinate][this.jCoordniate + 1] = 
						new CircleInPlace(this.COURT_WIDTH, this.COURT_HEIGHT, 
								this.shooter.getColor(), 
								this.ballHolder[this.iCoordinate]
										[this.jCoordniate].pos_x,
								this.ballHolder[this.iCoordinate]
								[this.jCoordniate].pos_y + 25);
				
				this.iNew = this.iCoordinate;
				this.jNew = this.jCoordniate + 1;
			}
		}
		
		else if (shooter.pos_x > (x_val + 7) && shooter.pos_x <= (x_val + 20)) {
			
			
			if ((this.iCoordinate + 1 <= 16 && 
					this.ballHolder[this.iCoordinate + 1][this.jCoordniate] 
					== null )) {
					
					this.ballHolder[this.iCoordinate + 1][this.jCoordniate] = 
							new CircleInPlace(this.COURT_WIDTH, 
									this.COURT_HEIGHT, 
									this.shooter.getColor(), 
									(this.ballHolder[this.iCoordinate]
											[0].pos_x + 25),
									this.ballHolder[this.iCoordinate]
									[this.jCoordniate].pos_y);	
					
					this.iNew = this.iCoordinate + 1;
					this.jNew = this.jCoordniate;
				}
			
			
			else {
				
				this.ballHolder[this.iCoordinate][this.jCoordniate + 1] = 
						new CircleInPlace(this.COURT_WIDTH, this.COURT_HEIGHT, 
								this.shooter.getColor(), 
								this.ballHolder[this.iCoordinate]
										[this.jCoordniate].pos_x,
								this.ballHolder[this.iCoordinate]
								[this.jCoordniate].pos_y + 25);
				
				this.iNew = this.iCoordinate;
				this.jNew = this.jCoordniate + 1;
			}
		}
		
		else {
			this.ballHolder[this.iCoordinate][this.jCoordniate + 1] = 
					new CircleInPlace(this.COURT_WIDTH, this.COURT_HEIGHT, 
							this.shooter.getColor(), 
							this.ballHolder[this.iCoordinate]
									[this.jCoordniate].pos_x,
							this.ballHolder[this.iCoordinate]
							[this.jCoordniate].pos_y + 25);
			
			this.iNew = this.iCoordinate;
			this.jNew = this.jCoordniate + 1;
		}
		
		
		double colorChooser = Math.random();
		Color color;
		
		if (colorChooser <= 0.2) {
			color = Color.BLUE;
		}
		
		else if (colorChooser > 0.2 && colorChooser <= 0.4) {
			color = Color.RED;
		}
		
		else if (colorChooser > 0.4 && colorChooser <= 0.6) {
			color = Color.ORANGE;
		}
		
		else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
			color = Color.magenta;
		}
		
		else { color = Color.PINK; }
		
		this.shooter = new CircleShooter(this.COURT_WIDTH, this.COURT_HEIGHT, 
										 color);
		this.status.setText("Shoot!");
	}
	
	//check if the double array has any new combinations
	public void checkForScores() {
		
		CircleInPlace home = this.ballHolder[this.iNew]
							 [this.jNew];
		
		
		
		//if the ball above is not null!
		if (this.ballHolder[this.iNew][this.jNew - 1] != null) {

			//if color above is the same
			if (home.getColor().equals(this.ballHolder[this.iNew]
					[this.jNew - 1].getColor())) {
				
				//if the ball two balls above the shooter is not null!
				if (this.jNew - 2 >= 0 && 
						this.ballHolder[this.iNew][this.jNew - 2] != null) {
				
				//if a line that is just straight is empty
					if (this.ballHolder[this.iNew]
						[this.jNew - 1].getColor().
						equals(this.ballHolder[this.iNew]
								[this.jNew - 2].getColor())) {
						
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew][this.jNew - 1] = null;
						this.ballHolder[this.iNew][this.jNew - 2] = null;
						this.score = this.score + 30;
						return;
					}
				}
			
				//check if the ball to the right above is not null				
				if (this.iNew + 1 <= 16 && this.iNew + 1 >= 0 &&
						this.ballHolder[this.iNew + 1][this.jNew - 1] != null) {

					//if makes a reflected L shap
					if (this.ballHolder[this.iNew]
						[this.jNew - 1].getColor().equals(this.ballHolder
								[this.iNew + 1][this.jNew - 1].getColor())) {
					
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew][this.jNew - 1] = null;
						this.ballHolder[this.iNew + 1][this.jNew - 1] = null;
						this.score = this.score + 30;
						return;
					}
				}
				
				
				//check if the ball above the left is not null
				if (this.iNew - 1 <= 16 && this.iNew - 1 >= 0 && 
						this.ballHolder[this.iNew - 1][this.jNew- 1] != null) {
													
					//if makes an L shape in the other direction
					if (this.ballHolder[this.iNew]
							[this.jNew - 1].getColor().equals(this.ballHolder
									[this.iNew - 1][this.jNew - 1].
									getColor())) {
						
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew][this.jNew - 1] = null;
						this.ballHolder[this.iNew - 1][this.jNew - 1] = null;
						this.score = this.score + 30;
						return;
						
						}
					}
				
				//if lands in the middle of two balls of the same color
				//LEFT
				if (this.iNew - 1 >= 0 && 
						this.ballHolder[this.iNew - 1][this.jNew] != null) {
					
					//if makes an L shape in the other direction
					if (this.ballHolder[this.iNew - 1]
							[this.jNew].getColor().equals(this.ballHolder
									[this.iNew][this.jNew - 1].getColor())) {
						
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew][this.jNew - 1] = null;
						this.ballHolder[this.iNew - 1][this.jNew] = null;
						this.score = this.score + 30;
						return;
						
						}
				}
				
				
				//if lands in the middle of two balls of the same color
				//Right
				if (this.iNew + 1 <= 16 && 
						this.ballHolder[this.iNew + 1][this.jNew] != null) {
					
					//if makes an L shape in the other direction
					if (this.ballHolder[this.iNew + 1]
							[this.jNew].getColor().equals(this.ballHolder
									[this.iNew][this.jNew - 1].getColor())) {
						
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew][this.jNew - 1] = null;
						this.ballHolder[this.iNew + 1][this.jNew] = null;
						this.score = this.score + 30;
						return;
						
						}
				}
				
				
				else { }
				
				}
			}
		
		//check if the ball on the left is non-null!
		if (this.iNew - 1 >= 0 && 
				this.ballHolder[this.iNew - 1][this.jNew] != null) {
			
			//if color on the left is the same
			if (home.getColor().equals(this.ballHolder[this.iNew - 1]
					[this.jNew].getColor())) { 
				
				//if the ball to the right is not null!
				if (this.iNew + 1 <= 16 && 
						this.ballHolder[this.iNew + 1][this.jNew] != null) {
										
				//if color on the right is the same
				if (this.iNew + 1 <= 16 && this.ballHolder[this.iNew + 1]
						[this.jNew] != null &&
						home.getColor().equals(this.ballHolder[this.iNew + 1]
						[this.jNew].getColor())) {
					
					this.ballHolder[this.iNew][this.jNew] = null;
					this.ballHolder[this.iNew - 1][this.jNew] = null;
					this.ballHolder[this.iNew + 1][this.jNew] = null;
					this.score = this.score + 30;
					return;
				}
			}
				
				//if the ball on top is not null!
				if (this.ballHolder[this.iNew][this.jNew - 1] != null) {
					
				//if color on the top is the same
				if (this.jNew - 1 >= 0 && this.ballHolder[this.iNew - 1]
						[this.jNew - 1] != null &&
						home.getColor().equals(this.ballHolder[this.iNew - 1]
						[this.jNew - 1].getColor())) {
					
					this.ballHolder[this.iNew][this.jNew] = null;
					this.ballHolder[this.iNew - 1][this.jNew] = null;
					this.ballHolder[this.iNew - 1][this.jNew - 1] = null;
					this.score = this.score + 30;
					return;
					}
				}
			}
		}
		
		//if item on the right is non-null
		if (this.iNew + 1 <= 16 && 
				this.ballHolder[this.iNew + 1][this.jNew] != null) {
			
			//if color on the right is the same
			if (home.getColor().equals(this.ballHolder[this.iNew + 1]
					[this.jNew].getColor())) { 
				
				//if the ball on top is not null!
				if (this.ballHolder[this.iNew][this.jNew - 1] != null) {
					
					//if color on the top is the same
					if (this.jNew - 1 >= 0 && this.iNew - 1 >= 0 &&
							this.ballHolder[this.iNew - 1]
							[this.jNew - 1] != null &&
							home.getColor().equals(this.ballHolder
									[this.iNew - 1]
							[this.jNew - 1].getColor())) {
						
						this.ballHolder[this.iNew][this.jNew] = null;
						this.ballHolder[this.iNew - 1][this.jNew] = null;
						this.ballHolder[this.iNew - 1][this.jNew - 1] = null;
						this.score = this.score + 30;
						return;
						}
				}
			}	
		}	
	}
	
	public void updateArray() {
	
		CircleInPlace[][] x = new CircleInPlace[17][15];
		
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 14; j++) {
											
				x[i][j + 1] = this.ballHolder[i][j];
				
				if (x[i][j + 1] != null) {
				x[i][j + 1].pos_y = x[i][j + 1].pos_y + 25;
				
				}
			}
		}
		
		this.ballHolder = x;
		
		for (int i = 0; i < 17; i++) {
			
			double colorChooser = Math.random();
			Color color;
			
			if (colorChooser <= 0.2) {
				color = Color.BLUE;
			}
			
			else if (colorChooser > 0.2 && colorChooser <= 0.4) {
				color = Color.RED;
			}
			
			else if (colorChooser > 0.4 && colorChooser <= 0.6) {
				color = Color.ORANGE;
			}
			
			else if (colorChooser > 0.6 && colorChooser <= 0.8 ) {
				color = Color.magenta;
			}
			
			else { color = Color.PINK; }
			
			
			this.ballHolder[i][0] = new CircleInPlace(this.COURT_WIDTH, 
									this.COURT_HEIGHT, color, 
									0 + (i * 25), 0);
			
		}
		
	}
	
	//method for testing purposes
	public int getUpdater() {
		return this.updater;
	}
	
	//method for testing purposes
	public CircleShooter getShooter () {
		return this.shooter;
	}
	
	void writerMethod() {
		
		BufferedWriter writer;
		try {
			
			writer = new BufferedWriter(new FileWriter
					("High Scores.txt", true));
			writer.write(this.username + ": " + this.score + "\n");
			
			writer.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//reading the file
		BufferedReader reader;
		

		
		try {
			
			reader = new BufferedReader(new FileReader("High Scores.txt"));
			
			String user = reader.readLine();
			while (user != null) {

				user.trim();
				int space = user.indexOf(" ");
				
				String name = user.substring(0, space);
				
				String g = user.substring(space + 1, user.length());	
				System.out.print(g);
				
				int parser = Integer.parseInt(g);
				
				this.highScores.put(parser, name);
				
				
				user = reader.readLine();
				
			}
			
			reader.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Collection<Integer> keeperofInts = this.highScores.keySet();
		
		ArrayList listFINAL = new ArrayList(keeperofInts);
		Collections.sort(listFINAL);
		
		
		Integer[] ret = new Integer[listFINAL.size()];
		 for(int i = 0;i < ret.length;i++) {
	     
			 ret[i] = (Integer) listFINAL.get(i);
		 }
			
		
		Arrays.sort(ret);
		
		String temp = "High Scores:" + "\n" + "\n";
		
		for (int i = 6; i >= 0; i--) {
			
			int x = ret[i];
			Integer z = new Integer(x);
			
			String y = this.highScores.get(z);
			
			temp = temp + y + " " + x + "\n";
			
		}
		
		JOptionPane.showMessageDialog(null, temp);
		
		
	
	}
	
	//tick function for every 35 milliseconds
	void tick() {
		
		int counter = 0;
		int counter2 = 0;
		for (int i = 0; i < 17; i++) {
			
			if (counter == 9) {
				this.playing = false;
				this.status.setText("Game Over!");
				break;
			}
			
			if (this.ballHolder[i][13] != null) {
				counter = counter + 1;
				
			}
			
			if (this.ballHolder[i][13] != null) {
				this.playing = false;
				this.status.setText("Game Over!");
				break;
				
			}
			
			
			if (this.ballHolder[i][0] == null) {
				counter2 = counter2 + 1;
			}
			
			//if the entire top is swiped then you won!
			if (counter2 == 16) {
				this.playing = false;
				this.status.setText("You won!");
				break;
			}
			
		}
		
		
		
		if (this.playing) {
			shooter.move();
			shooter.bounce(shooter.hitWall());
			
			for (int i = 0; i < 17; i++) {
				for (int j = 0; j < 15 ; j++) {
				
					if (this.ballHolder[i][j] == null) { continue; }
					
					else if (this.ballHolder[i][j].intersects(shooter)) {
						
						this.iCoordinate = i;
						this.jCoordniate = j;
						
						this.updater += 1;
						addBallToArray();

						checkForScores();
						this.scoreLabel.setText("Score: " +  this.score);
						if (this.updater % 5 == 0) {
							updateArray();
						}
						break;
					}
				}
			}

			repaint();
		}
		
		else {this.overkeeper = this.overkeeper + 1;
			
			if (this.overkeeper == 1) {
			writerMethod();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 15; j++) {
				
				if (this.ballHolder[i][j] == null) { continue; }
				this.ballHolder[i][j].draw(g);
				
			}
		}
		this.shooter.draw(g);
	}
	
	//get the dimensions right when running it
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	
void tickTesting() {
		
		int counter = 0;
		int counter2 = 0;
		for (int i = 0; i < 17; i++) {
			
			if (counter == 9) {
				this.playing = false;
				this.status.setText("Game Over!");
				break;
			}
			
			if (this.ballHolder[i][13] != null) {
				counter = counter + 1;
				
			}
			
			if (this.ballHolder[i][13] != null) {
				this.playing = false;
				this.status.setText("Game Over!");
				break;
				
			}
			
			
			if (this.ballHolder[i][0] == null) {
				counter2 = counter2 + 1;
			}
			
			//if the entire top is swiped then you won!
			if (counter2 == 16) {
				this.playing = false;
				this.status.setText("You won!");
				break;
			}
			
		}
		
		
		
		if (this.playing) {
			shooter.move();
			shooter.bounce(shooter.hitWall());
			
			for (int i = 0; i < 17; i++) {
				for (int j = 0; j < 15 ; j++) {
				
					if (this.ballHolder[i][j] == null) { continue; }
					
					else if (this.ballHolder[i][j].intersects(shooter)) {
						
						this.iCoordinate = i;
						this.jCoordniate = j;
						
						this.updater += 1;
						addBallToArray();

						checkForScores();
						this.scoreLabel.setText("Score: " +  this.score);
						if (this.updater % 5 == 0) {
							updateArray();
						}
						break;
					}
				}
			}

			repaint();
		}
		
		else {this.overkeeper = this.overkeeper + 1;
			
			
		}
	}

}
