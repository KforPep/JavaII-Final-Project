/**
 * This class is the main window. It handles key press events
 * and send them to the Grid.
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Optional;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Main extends Application{
	
	/***
	 * This class opens and controls the window.
	 * It shows the scene from Grid inside of it
	 */
	private MenuItem quit;
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage mainStage) {
		try {
			
			// Set the window to the screen dimensions
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			mainStage.setWidth(screenSize.getWidth()); 		//Set the width of the window to fill screen
			mainStage.setHeight(screenSize.getHeight());	//Set the height of the window to fill screen
			
			// Add the gameplay Grid to the stage
			Grid gameGrid = new Grid();
			Pane returnedGamePane = gameGrid.start(mainStage.getHeight());	// Starts new Grid using mainStage's height for sizing
			
			//Option Menu Stuff
			Menu menuOp = new Menu("Options");
			quit = new MenuItem("Quit");
			
			//Sound Menu Stuff
			Menu soundMenu = new Menu("Sound");
			//Character Noise Set up
			CheckBox charCheck = new CheckBox();
			Labeled charLabel = new Label("Character Noise");
			charCheck.setSelected(true);
			charLabel.setGraphic(charCheck);
			CustomMenuItem charNoise = new CustomMenuItem(charLabel);
			charNoise.setHideOnClick(false);
			
			//Ambient Noise Set up
			Label ambLabel = new Label("Ambient Noise");
			CheckBox ambCheck = new CheckBox();
			ambCheck.setSelected(true);
			ambLabel.setGraphic(ambCheck);
			CustomMenuItem ambNoise = new CustomMenuItem(ambLabel);
			ambNoise.setHideOnClick(false);
			
			//Add Menu Items
			menuOp.getItems().addAll(quit);
			soundMenu.getItems().addAll(charNoise, ambNoise);
			

			
			MenuBar menuBar = new MenuBar();
			menuBar.getMenus().addAll(menuOp, soundMenu);
			
			
			// These Panes cover the left and right side of the screen
			// to hide vehicles entering and exiting the scene
			Pane leftSideCover = new Pane();
			leftSideCover.setMaxSize(((mainStage.getWidth() - mainStage.getHeight())/2), mainStage.getHeight());
			leftSideCover.setMinWidth((mainStage.getWidth() - mainStage.getHeight())/2);
			leftSideCover.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
			Pane rightSideCover = new Pane();
			rightSideCover.setMaxSize((mainStage.getWidth() - mainStage.getHeight()/2), mainStage.getHeight());
			rightSideCover.setMinWidth((mainStage.getWidth() - mainStage.getHeight())/2);
			rightSideCover.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
			
			// Set left, gameplay, and right on a BorderPane
			BorderPane totalScreen = new BorderPane();
			totalScreen.setTop(menuBar);
			totalScreen.setCenter(returnedGamePane);
			totalScreen.setRight(rightSideCover);
			totalScreen.setLeft(leftSideCover);
			
			// Set up scene and send all key presses to the Grid
			Scene scene = new Scene(totalScreen);
			scene.setOnKeyPressed(e -> 
			{
				gameGrid.sendKeyEvent(e);
			});
			
			File file = new File(System.getProperty("user.dir") + "/images/Icon.jpg"); 
			String localUrl = file.toURI().toURL().toString();
			Image iconImage = new Image(localUrl);
			mainStage.getIcons().add(iconImage);
			
			mainStage.setTitle("Class Simulator");
			mainStage.setScene(scene);
			mainStage.show();
			
			// Handles window size changes and resizes the leftSideCover and rightSideCover
			// without changing the game play region
			ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
			{
				leftSideCover.setMaxSize(((mainStage.getWidth() - gameGrid.getGameSize())/2), mainStage.getHeight());
				leftSideCover.setMinSize(((mainStage.getWidth() - gameGrid.getGameSize())/2), mainStage.getHeight());
				rightSideCover.setMaxSize(((mainStage.getWidth() - gameGrid.getGameSize())/2), mainStage.getHeight());
				rightSideCover.setMinSize(((mainStage.getWidth() - gameGrid.getGameSize())/2), mainStage.getHeight());
			};

		    mainStage.widthProperty().addListener(stageSizeListener);
		    mainStage.heightProperty().addListener(stageSizeListener); 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Menu Events
		quit.setOnAction(event ->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Quit?");
			alert.setHeaderText("");
			alert.setContentText("Are you sure you want to quit?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				mainStage.close(); // closes the game
			} else {
			    // goes back to the game
			}
			
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
