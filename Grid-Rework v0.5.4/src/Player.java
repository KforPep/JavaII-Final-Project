import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.ImagePattern;
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
	//Color color; //player color
	ImagePattern pattern;
	
	public Player(double x, double y, double size, ImagePattern playerPattern)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		//this.color = color;
		this.pattern = playerPattern;
		
		this.setRadius(size);
		//this.setFill(color);
		this.setFill(playerPattern);
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
	public void carry(double objectMovement)
	{	
		//Set player position to object position
		this.setTranslateX(this.getTranslateX() + objectMovement);
		//this.setTranslateX(object.getTranslateX());
		//this.setTranslateY(object.getTranslateY());
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
	
	//Reset player location without killing them
	public void reset()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
	//Determine if the player is drowning
	public boolean collidesWithLogs(ArrayList<ArrayList<MovingObject>> logs)
	{
		boolean drowning = true;

		//Check each log and see if it is colliding with the player
		for (int i = 0; i < logs.size(); i++) //check each row of logs
		{
			for (int j = 0; j < logs.get(i).size(); j++) //check each log in the row
			{
				if (logs.get(i).get(j).getCarry() == true) //check if the log is set to carry the player
				{
					if (logs.get(i).get(j).getBoundsInParent().intersects(this.getBoundsInParent())) //check if the log intersects with the player
					{
						drowning = false; //Set drowning to false
					}
				}

			}
		}
		
		return drowning;
	} //collidesWithLogs
	
	//Determine if player is touching a score object
	public void collidesWithScore(ArrayList<ScoreObject> scores)
	{
		//Check each score item and see if it is colliding with the player
		for (int i = 0; i < scores.size(); i++)
		{
			if (scores.get(i).getBoundsInParent().intersects(this.getBoundsInParent()))
			{
				scores.get(i).collide(this);
				this.reset();
			}
		}
	}
	
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
