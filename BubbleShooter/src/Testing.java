import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.JLabel;

public class Testing {


	//get shift balls when no balls are shot!
	@Test
    public void getShiftBalls(){
		JLabel jTest1 = new JLabel("Shoot!");
		JLabel jTest2 = new JLabel("Score: 0");
		GameCourt testing = new GameCourt(jTest1, jTest2);
		
		testing.resetTesting();
		for (int i = 0; i < 3; i++) {
			
			CircleShooter x = testing.getShooter();
			x.xMouseCord = 10;
			x.yMouseCord = 40;
			x.v_y = 10;
			x.movementCalc();
			
			testing.tick();
		}
		
		assertEquals(0, testing.getUpdater());
    }

	//test wheather balls are shifted after they do indeed hit something!
	@Test
    public void getShiftBalls2(){
		JLabel jTest1 = new JLabel("Shoot!");
		JLabel jTest2 = new JLabel("Score: 0");
		GameCourt testing = new GameCourt(jTest1, jTest2);
		
		testing.resetTesting();
		for (int i = 0; i < 3; i++) {
			
			CircleShooter x = testing.getShooter();
			x.xMouseCord = 10;
			x.yMouseCord = 40;
			x.v_y = 10;
			x.movementCalc();
			
			for (int j = 0; j <100; j++) {
				x.move();
			}
			testing.tick();
		}
		
		assertEquals(3, testing.getUpdater());
    }
	
	//test wheather balls are shifted after they do indeed hit something 
	//after 5 times!
		@Test
	    public void getShiftBalls3(){
			JLabel jTest1 = new JLabel("Shoot!");
			JLabel jTest2 = new JLabel("Score: 0");
			GameCourt testing = new GameCourt(jTest1, jTest2);
			
			testing.resetTesting();
			for (int i = 0; i < 5; i++) {
				
				CircleShooter x = testing.getShooter();
				x.xMouseCord = 10;
				x.yMouseCord = 40;
				x.v_y = 10;
				x.movementCalc();
				
				for (int j = 0; j <100; j++) {
					x.move();
				}
				testing.tick();
			}
			
			assertEquals(5, testing.getUpdater());
	    }
		
		//checks weather the game is over
			@Test
		    public void gameOverCheck(){
				JLabel jTest1 = new JLabel("Shoot!");
				JLabel jTest2 = new JLabel("Score: 0");
				GameCourt testing = new GameCourt(jTest1, jTest2);
				
				testing.resetTesting();
				CircleShooter x = testing.getShooter();
				
				for (int i = 0; i < 70; i++) {
					
					
					x.xMouseCord = 10;
					x.yMouseCord = 60;
					x.v_y = 10;
					x.movementCalc();
					
					for (int j = 0; j <500; j++) {
						x.move();
					}
					testing.tickTesting();
				}
				
				assertEquals(true, testing.playing);
		    }
			
			//check whether the balls pop correctly
			@Test
			public void popCorrectly(){
				JLabel jTest1 = new JLabel("Shoot!");
				JLabel jTest2 = new JLabel("Score: 0");
				GameCourt testing = new GameCourt(jTest1, jTest2);
				testing.resetTesting();
				
				CircleInPlace holder[][] = new CircleInPlace [17][15];
				testing.ballHolder =holder;
				boolean checker = false;
				testing.shooter = new CircleShooter(testing.
						COURT_WIDTH, 
						testing.COURT_HEIGHT, Color.blue);
				//fill in a line with all blue balls
				for (int i = 0; i < 16; i++) {
					testing.ballHolder[i][3] = new CircleInPlace(testing.
							COURT_WIDTH, 
							testing.COURT_HEIGHT, Color.blue, 
							0 + (i * 25), 5);
				}
				
				CircleShooter x = testing.getShooter();

					x.xMouseCord = 0;
					x.yMouseCord = 20;
					x.v_y = 10;
					x.movementCalc();
					
					for (int j = 0; j <500; j++) {
						x.move();
					}
					testing.tickTesting();

				
			assertEquals(false, (testing.ballHolder[0][4] == null 
					&& testing.ballHolder[0][3] == null &&
					testing.ballHolder[1][3] == null));
		    }
			
			
	
}
