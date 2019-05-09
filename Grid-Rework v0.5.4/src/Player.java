import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Player extends Circle {
	
	int row; //player row index
	int column; //player column index
	int score = 0; //player score
	int lives = 3; //current player lives
	int defaultLives; //default player lives
	int resetCount = 0; //how many times th eplayer has been reset this session
	Label livesLabel; //Label which displays this player's lives
	Label scoreLabel; //Label which displays this player's score
	double x; //player X position
	double y; //player Y position
	double size; //player size
	boolean drowning = false; //is the player drowning?
	boolean carried = false; //is the player being carried?
	boolean moving = false; //is the player moving?
	//Color color; //player color
	ImagePattern pattern;
	
	public Player(double x, double y, double size, int lives, ImagePattern pattern)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		//this.color = color;
		this.pattern = pattern;
		this.lives = lives;
		this.defaultLives = this.lives;
		
		this.setRadius(size);
		//this.setFill(color);
		this.setFill(pattern);
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
	public void carry(double objectMovement, double leftBound, double rightBound)
	{	
		//Check if the player is out of bounds
		if (((this.getTranslateX()+(this.size*2)) < leftBound) || ((this.getTranslateX()-(this.size*2)) > rightBound))
		{
			this.kill(); //kill player if they are carried off screen
		}
		//Set player position to object position
		this.setTranslateX(this.getTranslateX() + objectMovement);
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
			this.lives--;
			this.livesLabel.setText("Lives: " + this.lives);
			
			//Reset player if they reach 0 lives
			if (this.lives <= 0)
			{
				this.reset();
			}
		}
	} //kill
	
	//Reset player location, lives and score
	public void reset()
	{
		this.resetCount++;
		this.resetPos();
		this.resetLives();
		this.resetScore();
	} //reset
	
	//Reset player location without killing them
	public void resetPos()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
	} //resetPos
	
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
				if (scores.get(i).getActivated() == false)
				{
					scores.get(i).collide(this);
					this.resetPos();
				}
			}
		}
	}
	
	//Add one to player score
	public void scorePoint()
	{
		this.score++;
		this.scoreLabel.setText("Score: " + this.score + "/5");
	} //scorePoint
	
	//Get score value
	public int getScore()
	{
		return this.score;
	} //getScore
	
	//Set label which will track this player's lives
	public void setLivesLabel(Label livesLabel)
	{
		this.livesLabel = livesLabel;
	} //setLivesLabel
	
	//Set label which will track this player's score
	public void setScoreLabel(Label scoreLabel)
	{
		this.scoreLabel = scoreLabel;
	} //setScoreLabel
	
	//Get lives
	public int getLives()
	{
		return this.lives;
	} //getLives
	
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
	
	//Get player size
	public double getSize()
	{
		return this.size;
	} //getSize
	
	//Reset player lives
	public void resetLives()
	{
		this.lives = this.defaultLives;
		this.livesLabel.setText("Lives: " + this.defaultLives);
	} //resetLives
	
	//Reset player score
	public void resetScore()
	{
		this.score = 0;
		this.scoreLabel.setText("Score: " + this.score + "/5");
	}
	
	//Get amount of player resets
	public int getResetCount()
	{
		return this.resetCount;
	} //getResetCount
	
} //class
