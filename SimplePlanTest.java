package junit;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import metier.DefaultUserPreferences;
import metier.Home;
import metier.Wall;
import swing.PlanComponent;

/**
 * @author spooky
 *
 */
public class SimplePlanTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Wall wall1 = new Wall(-100, 0, 200, 0, 25);
		Wall wall2 = new Wall(200, 0, 500, 300, 25);
		Wall wall3 = new Wall(-100, 0, -100, 300, 10);
		Home home = new Home();
		home.addWall(wall1);
		home.addWall(wall2);
		home.addWall(wall3); 
		home.setWallAtEnd(wall1, wall2);
		home.setWallAtStart(wall2, wall1);
		PlanComponent planComponent = new PlanComponent(home, new DefaultUserPreferences(), null);
		planComponent.setBorder(
		BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
		home.setSelectedItems(
		Arrays.asList(new Wall [] {wall2, wall3}));
		planComponent.setRectangleFeedback(-125, 100, 700, 225);
		JFrame frame = new JFrame("Plan Test");
		frame.add(planComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
