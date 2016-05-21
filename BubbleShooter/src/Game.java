import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game implements Runnable {

	@Override
	public void run() {
		
				// Top-level frame in which game components live
				// Be sure to change "TOP LEVEL FRAME" to the name of your game
				final JFrame frame = new JFrame("BubbleShooter");
				frame.setLocation(300, 200);

				// Status panel
				final JPanel status_panel = new JPanel();
				frame.add(status_panel, BorderLayout.SOUTH);
				final JLabel status = new JLabel("Shoot!");
				status_panel.add(status);
				
				//score label
				final JPanel score_keeper = new JPanel();
				frame.add(score_keeper, BorderLayout.EAST);
				final JLabel score = new JLabel ("Score: 0");
				score_keeper.add(score);
				
				// Main playing area
				final GameCourt court = new GameCourt(status, score);
				frame.add(court, BorderLayout.CENTER);

				// Reset button
				final JPanel control_panel = new JPanel();
				frame.add(control_panel, BorderLayout.NORTH);
				
				

				// Note here that when we add an action listener to the reset
				// button, we define it as an anonymous inner class that is
				// an instance of ActionListener with its actionPerformed()
				// method overridden. When the button is pressed,
				// actionPerformed() will be called.
				final JButton reset = new JButton("Reset");
				reset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						court.reset();
					}
				});
				control_panel.add(reset);

				// Put the frame on the screen
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

				// Start game
				court.reset();
			}

			/*
			 * Main method run to start and run the game Initializes the GUI elements
			 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
			 * this in the final submission of your game.
			 */
			public static void main(String[] args) {
				SwingUtilities.invokeLater(new Game());
			}
		
	}


