package user;

import javafx.application.Application;
import javafx.stage.Stage;
import tankbrickgame.SplashScreen;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setSplashScreen(stage);
	}

}


// If error, try installing ArcadeClassic font
// link: https://www.1001fonts.com/arcadeclassic-font.html
//install by "Install for all user"
