/* TO-DO:
 * Prevent diagonal movement?
 * Movement of player on moving objects
 * Smoother animations
 */


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Grid
{
	
	//player score
	static int p1= 3;
	//Class constants & variables
	
	//SCALING ONLY WORKS WHEN GRID_WIDTH IS ODD & GRID_HEIGHT IS EVEN!
	private final int GRID_WIDTH = 19; //Number of tiles in each row (13 default)			
	private final int GRID_HEIGHT = 14; //Number of tiles in each column (14 default)
	private final double PADDING = 16; //Width of spacing around the grid
	
	private double TILE_SIZE; //Size of the tile, calculated on launch
	//private double GRID_Y; //Grid height measurement
	private double PLAYER_SIZE; //Size of player object
	private double OBJECT_SPAWN_DISTANCE; //distance moving objects will spawn away from the grid
	private double BOTTOM_ROW, TOP_ROW, RIGHT_COLUMN, LEFT_COLUMN, MIDDLE_COLUMN; //Layout positions
	private double RESPAWN_X, RESPAWN_Y; //Respawn position
	boolean hitOrRun;
	Label score;
	public TranslateTransition mover;
	Player player;
	double leftSpawn;
	double rightSpawn;
	GridGen backgroundGrid;
	
	//Start method
	public Pane start(double d) throws MalformedURLException 
	{
		Pane gamePane = new Pane();
		gamePane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		int gameSceneWidth = (int) d; // All blocks are square so height and width are equal
		
		TILE_SIZE = (double)((int) ((gameSceneWidth - PADDING) / GRID_WIDTH)) ; //Calculate grid tile size
		//GRID_Y = TILE_SIZE * GRID_HEIGHT; //Calculate grid height measurement
		PLAYER_SIZE = (TILE_SIZE - 5)/2; //Calculate size of the player sprite
		OBJECT_SPAWN_DISTANCE = TILE_SIZE*1.5; //Calculate object spawn distance
		
		//Calculate layout positions of centers of tiles RELATIVE TO CENTER OF THE GRID
		BOTTOM_ROW = (TILE_SIZE * ((GRID_HEIGHT - 1)/2)) + (TILE_SIZE/2);
		TOP_ROW = (row(GRID_HEIGHT));
		RIGHT_COLUMN = (TILE_SIZE * (GRID_WIDTH/2));
		LEFT_COLUMN = -RIGHT_COLUMN;
		MIDDLE_COLUMN = 0;
		
		//Set player respawn location
		RESPAWN_X = MIDDLE_COLUMN; //respawn column
		RESPAWN_Y = row(1); //respawn row

		//generate game grid
		backgroundGrid = new GridGen();
		ArrayList<ArrayList<Rectangle>> grid = backgroundGrid.gridGen(TILE_SIZE, GRID_WIDTH, GRID_HEIGHT);
		
		
		
		
		
		

		//Create a VBox to hold the grid
		VBox vbxGrid = new VBox();
		vbxGrid.setAlignment(Pos.CENTER);
		
		//Draw the squares from the grid array list on to the scene
		for (int n = 0; n < GRID_HEIGHT; n++)  //1 iteration = 1 row
		{
			HBox row = new HBox(); //Create an HBox to hold a row
			
			for (int i = 0; i < GRID_WIDTH; i++) //Fill the row with squares (1 iteration = 1 square)
			{
				row.getChildren().add(grid.get(n).get(i)); //Take square from grid arraylist and add it to the row
			}
			
			row.setAlignment(Pos.CENTER); //Align the row to the center
			vbxGrid.getChildren().add(row); //Add the row to the grid VBox
		}

		
		File jeffsBeautifulFaceFile = new File(System.getProperty("user.dir") + "/images/fisherPlayer.jpg");
		String jeffsBeautifulFaceLocalUrl = jeffsBeautifulFaceFile.toURI().toURL().toString();
		Image jeffsBeautifulFaceLocalImage = new Image(jeffsBeautifulFaceLocalUrl);
		ImagePattern jeffsBeautifulFacePattern = new ImagePattern(jeffsBeautifulFaceLocalImage);
		
		//Draw the player
		//Player(x, y, size, color)
		player = new Player(RESPAWN_X, RESPAWN_Y, PLAYER_SIZE, jeffsBeautifulFacePattern);
		
		//Animation to move the player
		mover = new TranslateTransition(Duration.millis(250), player);
		mover.setFromX(RESPAWN_X);
		mover.setFromY(RESPAWN_Y);
		
		System.out.println("Mover X = " + mover.getFromX());
		System.out.println("Mover Y = " + mover.getFromX());
		System.out.println("Player X = " + player.getLayoutX());
		System.out.println("Player Y = " + player.getLayoutY());
		
		/* OBJECTS WITH COLLISION */
		
		rightSpawn = RIGHT_COLUMN + OBJECT_SPAWN_DISTANCE; //right spawn point for moving objects
		leftSpawn = LEFT_COLUMN - OBJECT_SPAWN_DISTANCE; //left spawn point for moving objects
		double defaultSize = TILE_SIZE - 10; //default object size
		double logHeight = TILE_SIZE - 15; //default log height
		//Color logColor = Color.SADDLEBROWN; //default log color
		
		
		//all these images are cars or logs or busses
		/*
		 * Scool bus
		 */
		File file9 = new File(System.getProperty("user.dir") + "/images/schoolbus.png");
		String localUrl9 = file9.toURI().toURL().toString();
		Image localImage9 = new Image(localUrl9);
		ImagePattern pattern9 = new ImagePattern(localImage9);
		/*
		 * Car 1
		 */
		File file11 = new File(System.getProperty("user.dir") + "/images/car1.png");
		String localUrl11 = file11.toURI().toURL().toString();
		Image localImage11 = new Image(localUrl11);
		ImagePattern pattern11 = new ImagePattern(localImage11);
		/*
		 * Car 2
		 */
		File file12 = new File(System.getProperty("user.dir") + "/images/car2.png");
		String localUrl12 = file12.toURI().toURL().toString();
		Image localImage12 = new Image(localUrl12);
		ImagePattern pattern12 = new ImagePattern(localImage12);
		/*
		 * Car 3
		 */
		File file13 = new File(System.getProperty("user.dir") + "/images/car3.png");
		String localUrl13 = file13.toURI().toURL().toString();
		Image localImage13 = new Image(localUrl13);
		ImagePattern pattern13 = new ImagePattern(localImage13);
		/*
		 * Car 4
		 */
		File file14 = new File(System.getProperty("user.dir") + "/images/car4.png");
		String localUrl14 = file14.toURI().toURL().toString();
		Image localImage14 = new Image(localUrl14);
		ImagePattern pattern14 = new ImagePattern(localImage14);	 

		/*
		 * Logs
		 *    	      	      
		 */
		File file15 = new File(System.getProperty("user.dir") + "/images/log.png");
		String localUrl15 = file15.toURI().toURL().toString();
		Image localImage15 = new Image(localUrl15);
		ImagePattern pattern15 = new ImagePattern(localImage15);
		
		//MovingObject(count, space, speed, velocity, width, height, startX, startY, color, animation type, carry player?)
		
		//Cars 1 (row 2)
		MovingObject car1 = new MovingObject(3, 50, 60, 5, defaultSize, defaultSize, rightSpawn, row(2), pattern11, "LEFT", false);
		//Cars 2 (row 3)
		MovingObject car2 = new MovingObject(3, 50, 55, 5, defaultSize, defaultSize, leftSpawn, row(3), pattern12, "RIGHT", false);
		//Cars 3 (row 4)
		MovingObject car3 = new MovingObject(3, 60, 35, 5, defaultSize + 15, defaultSize, rightSpawn, row(4), pattern13, "LEFT", false);
		//Cars 4 (row 5)
		MovingObject car4 = new MovingObject(2, 125, 30, 5, defaultSize + 30, defaultSize - 5, leftSpawn, row(5), pattern14, "RIGHT", false);
		//Trucks (row 6)
		MovingObject truck = new MovingObject(2, 75, 45, 5, (TILE_SIZE*2) - 10, defaultSize, rightSpawn, row(6), pattern9, "LEFT", false);
		//Logs 1 (row 8)
		MovingObject log1 = new MovingObject(3, 85, 55, 5, (TILE_SIZE*2), logHeight, leftSpawn, row(8), pattern15, "RIGHT", true);
		//Logs 2 (row 9)
		MovingObject log2 = new MovingObject(3, 85, 55, 5, (TILE_SIZE*2), logHeight, rightSpawn, row(9), pattern15, "LEFT", true);
		//Logs 3 (row 10)
		MovingObject log3 = new MovingObject(2, 125, 30, 5, (TILE_SIZE*4), logHeight, rightSpawn+75, row(10), pattern15, "LEFT", true);
		//Logs 4 (row 11)
		MovingObject log4 = new MovingObject(2, 65, 55, 5, (TILE_SIZE*3), logHeight, leftSpawn-75, row(11), pattern15, "RIGHT", true);
		//Logs 5 (row 12)
		MovingObject log5 = new MovingObject(3, 85, 55, 5, (TILE_SIZE*2), logHeight, rightSpawn, row(12), pattern15, "LEFT", true);
		
		// displays lives 
		score = new Label();
		score.setText("LIVES: " + p1);
		score.setStyle("-fx-font-size:36px;");
		
		//Stack pane to put objects on top of each other
		StackPane stack = new StackPane();
		stack.getChildren().add(vbxGrid); //Add game grid to stack pane
		
		
		//Add the arrays of moving objects on to the stack pane
		car1.toPane(stack); //Cars 1 (row 2)
		car2.toPane(stack); //Cars 2 (row 3)
		car3.toPane(stack); //Cars 3 (row 4)
		car4.toPane(stack); //Cars 4 (row 5)
		truck.toPane(stack); //Trucks (row 6)
		log1.toPane(stack); //Logs 1 (row 8)
		log2.toPane(stack); //Logs 2 (row 9)
		log3.toPane(stack); //Logs 3 (row 10)
		log4.toPane(stack); //Logs 4 (row 11)
		log5.toPane(stack); //Logs 5 (row 12)
		
		stack.getChildren().addAll(score); // adds player score to pane
		stack.getChildren().addAll(player); //Add player to stack pane
		//Stack pane layout
		stack.setAlignment(Pos.CENTER);
		stack.setMaxHeight(GRID_HEIGHT);
		stack.setMaxWidth(GRID_WIDTH);
		
		//Border pane to hold the stack pane
		BorderPane pane = new BorderPane(stack);

		/* OBJECT ANIMATIONS */
		
		//Create and start animations
		animateRectangles(car1, player, stack); //Cars 1 (row 2)
		animateRectangles(car2, player, stack); //Cars 2 (row 3)
		animateRectangles(car3, player, stack); //Cars 3 (row 4)
		animateRectangles(car4, player, stack); //Cars 4 (row 5)
		animateRectangles(truck, player, stack); //Trucks (row 6)
		animateRectangles(log1, player, stack); //Logs 1 (row 8)
		animateRectangles(log2, player, stack); //Logs 2 (row 9)
		animateRectangles(log3, player, stack); //Logs 3 (row 10)
		animateRectangles(log4, player, stack); //Logs 4 (row 11)
		animateRectangles(log5, player, stack); //Logs 5 (row 12)
		
		mover.setOnFinished(event -> { //When the player movement animation finishes
			player.setMoving(false); //Set player moving to false
			
			//!! NOT WORKING YET
			/*
			if (player.getTranslateY() < row(7) && player.getTranslateY() > row(12)) //check if the player is drowning
			{
				player.kill();
			}
			*/
		});
		
		gamePane.getChildren().addAll(pane);
		return gamePane;
	} //start
	
	
	
	
	//Return the coordinates of a row
	public double row(int rowNumber)
	{
		rowNumber--;
		double row = BOTTOM_ROW - (TILE_SIZE*rowNumber);
		return row;
	} //row
	
	//Create an animation for an array list of rectangles
	public void animateRectangles(MovingObject obj, Player player, StackPane stack)
	{
		try
		{
			for (int i = 0; i < obj.array.size(); i++)
			{	
				Timeline objAnimation = createTimeline(obj.animType, obj.array.get(i), player, stack.getBoundsInLocal(),
															(i * obj.space), obj.speed, obj.velocity, i, obj.infiniteAnim);
				objAnimation.play();
			}
		}
		catch (NullPointerException ex)
		{
			System.out.println("ERROR! Invalid animation type.");
			ex.printStackTrace();
		}
	} //animateRectangles
	
	//Create a timeline animation
	public Timeline createTimeline(String animationType, MovingObject object, Player player, Bounds paneBounds,
									int delay, int speedMillis, int xVelocity, int startDelay, boolean indefinite)
	{
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(speedMillis), new EventHandler<ActionEvent>()
		{
			
			
			double xv = xVelocity; //X velocity of animation
			double playerMovement;
			double objectStartPosition = object.getTranslateX(); //Starting position of the object
			int frame = 0;
			
			Bounds bounds = paneBounds; //Bounds of the layout pane
			
			@Override
			public void handle(ActionEvent e) 
			{	
				
				
				double objectX = object.getTranslateX();
				
				//Pause for the start delay
				if (frame <= startDelay)
				{
					frame++;
				}
				//Pause for spacing
				else if (frame <= delay)
				{
					frame++;
				}
				//Move object
				else
				{
					if (animationType.equalsIgnoreCase("LEFT")) //move left
					{
						object.setTranslateX(objectX - xv); //Move object left
						playerMovement = -1*xv;
					}
					else if (animationType.equalsIgnoreCase("RIGHT")) //move right
					{
						object.setTranslateX(objectX + xv); //Move object right
						playerMovement = xv;
					}
					
					//If the object reaches the edge of the pane
					if (animationType.equalsIgnoreCase("LEFT")) //left side
					{
						if (objectX <= (bounds.getMinX() - (bounds.getMaxX()/2))) //If an object moves out of bounds on the left side
						{
							object.setTranslateX(objectStartPosition); //reset object position
						}
					}
					else if (animationType.equalsIgnoreCase("RIGHT")) //right side
					{
						if (objectX >= bounds.getMaxX()) //If an object moves out of bounds on the right side
						{
							object.setTranslateX(objectStartPosition); //reset object position
						}
					}
					
					//Check for collision
					if(object.getBoundsInParent().intersects(player.getBoundsInParent()))
					{
						if (object.getCarry() == false ) //if the object does NOT carry player
						{
							
						
							player.kill();
							
							p1--; // after ever kill 1 life is taken
							score.setText("LIVES: "+p1); // displays new lives
							
						}
						if(p1 == 0) {  //resets lives after all 3 lives are used
							p1=3;	//sets to 3 lives
						}
						
						else
						{
							player.setCarried(true);
							player.carry(playerMovement);
							
							
						}
						
						
					}
					
					else
					{
						player.setCarried(false);
					}
					
					
					frame++;
				}
			}
		}));
	
		//Determine whether the animation is indefinite
		if (object.infiniteAnim == true)
		{
			animation.setCycleCount(Timeline.INDEFINITE);
		}
		
		return animation;
	} //createTimeline
	
	//Key pressed
	public void sendKeyEvent(KeyEvent e)
	{
		//Player movement
		
		//Get player location
		double currentX = player.getTranslateX();
		double currentY = player.getTranslateY();
		
		//Arrow key is pressed
		if ((e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) && player.getMoving() == false)
		{
			//Store location in animation before moving
			mover.setFromX(currentX);
			mover.setFromY(currentY);
			
			//move up
			if (e.getCode() == KeyCode.UP)
			{
				//Determine spot to move to
				double newY = currentY - TILE_SIZE;
				double newX = currentX;
				
				if (newY <= TOP_ROW) //Prevent moving out of upper bound & on top row
				{}
				else //move player
				{
					player.move(newX, newY, mover);
				}
			}
			
			//move down
			if (e.getCode() == KeyCode.DOWN)
			{	
				//Determine spot to move to
				double newY = currentY + TILE_SIZE;
				double newX = currentX;
				
				if (newY > BOTTOM_ROW) //Prevent moving out of lower bound
				{}
				else //move player
				{
					player.move(newX, newY, mover);
				}
			}
			
			//move left
			if (e.getCode() == KeyCode.LEFT)
			{	
				//Determine spot to move to
				double newX = currentX - TILE_SIZE;
				double newY = currentY;
				
				if (newX < LEFT_COLUMN) //Prevent moving out of left bound
				{}
				else
				{
					player.move(newX, newY, mover); //move player
				}
			}
			
			//move right
			if (e.getCode() == KeyCode.RIGHT)
			{	
				//Determine spot to move to
				double newX = currentX + TILE_SIZE;
				double newY = currentY;
				
				if (newX > RIGHT_COLUMN) //Prevent moving out of left bound
				{}
				else
				{
					player.move(newX, newY, mover); //move player
				}
			}
			
		} //player movement
		
	}
	
	// returns the game window size for proper sizing in Main
	public int getGameSize()
	{
		return (int) (GRID_WIDTH * TILE_SIZE);
	}

} //class
