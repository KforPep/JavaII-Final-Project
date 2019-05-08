import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Circle {
	
	int row; //player row index
	int column; //player column index
	double x; //player X position
	double y; //player Y position
	double size; //player size
	boolean drowning = false; //is the player drowning?
	boolean carried = false; //is the player being carried?
	boolean moving = false; //is the player moving?
	Color color; //player color
	
	public Player(double x, double y, double size, Color color)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
		
		this.setRadius(size);
		this.setFill(color);
		this.setTranslateX(x);
		this.setTranslateY(y);
	} //constructor

	//Moves the player with key press
	public void move(double x, double y, TranslateTransition moveAnimation)
	{	
		
		this.setMoving(true); //flag player as moving
		
		//Set movement animation's end point
		moveAnimation.setToY(y);
		moveAnimation.setToX(x);
		
		moveAnimation.play(); //Play movement animation
		
		//move player
		this.setTranslateY(y);
		this.setTranslateX(x);
		
		moveAnimation.setFromY(y); //Set movement animation's start point Y
		moveAnimation.setFromX(x); //Set movement animation's start point X
		//System.out.println(currentY);
	} //move
	
	//Carries the player
	public void carry(MovingObject object)
	{	
		//Set player position to object position
		this.setTranslateX(object.getTranslateX());
		this.setTranslateY(object.getTranslateY());
	} //carry
	
	//Kill the player
	public void kill()
	{
		if (this.getDrowning() == true && this.getCarried() == true) //save the player if they are being carried by a log
		{}
		else
		{
			this.setTranslateX(x);
			this.setTranslateY(y);
		}
	} //kill
	
	//Set drowning status
	public void setDrowning(boolean drowningStatus)
	{
		this.drowning = drowningStatus;
	} //setDrowning
	
	//Check drowning status
	public boolean getDrowning()
	{
		return this.drowning;
	} //getDrowning
	
	//Set carried status
	public void setCarried(boolean carriedStatus)
	{
		this.carried = carriedStatus;
	} //setCarried
	
	//Check carried status
	public boolean getCarried()
	{
		return this.carried;
	} //getCarried
	
	//Set moving status
	public void setMoving(boolean isMoving)
	{
		this.moving = isMoving;
	} //setMoving
	
	//Check moving status
	public boolean getMoving()
	{
		return this.moving;
	} //getMoving
	
} //class
