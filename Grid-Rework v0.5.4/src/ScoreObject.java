import javafx.scene.shape.Circle;

public class ScoreObject extends Circle {
	
	public ScoreObject(double size, double x, double y)
	{
		this.setVisible(false);
		this.setRadius(size);
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
	//What to do when a player collides with a score object
	public void collide(Player player)
	{
		this.setVisible(true);
		this.setFill(player.pattern);
	}

}
