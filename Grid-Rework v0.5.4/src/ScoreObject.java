import javafx.scene.shape.Circle;

public class ScoreObject extends Circle {
	
	private boolean activated = false;
	
	public ScoreObject(double size, double x, double y)
	{
		this.setVisible(false);
		this.setRadius(size);
		this.setTranslateX(x);
		this.setTranslateY(y);
	} //constructor
	
	//What to do when a player collides with a score object
	public void collide(Player player)
	{
		this.setVisible(true);
		this.setFill(player.pattern);
		this.activated = true;
		player.scorePoint();
	} //collide
	
	//Check if this object has been activated
	public boolean getActivated()
	{
		return this.activated;
	}
}
