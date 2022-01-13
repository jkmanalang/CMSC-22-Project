package tankbrickgame;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	public static final int GAMESTATUSBAR_HEIGHT = 50;

	private Stage stage;
	private Scene gameStageScene;
	private Group gameStageRoot;
	private Canvas gameStageCanvas;
	private GraphicsContext gameStageGc;
	private GameTimer gameTimer;


	public GameStage(){
		this.gameStageCanvas = new Canvas(SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);
		this.gameStageGc = gameStageCanvas.getGraphicsContext2D();
		this.gameStageRoot = new Group(gameStageCanvas);
		this.gameStageScene = new Scene(gameStageRoot, SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);

	}

	public void setGameScreen(Stage stage){
		this.stage = stage;
		this.stage.setTitle("Game Stage");
		this.stage.setScene(gameStageScene);

		gameTimer = new GameTimer(gameStageGc, gameStageScene, this);
		gameTimer.start();
	}

	// method to show game over stage screen
	void drawGameOver(int i, int score){

		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {

				GameOverStage gameover = new GameOverStage(i, stage, score);

				stage.setScene(gameover.getScene());
			}
		});
	}
}
