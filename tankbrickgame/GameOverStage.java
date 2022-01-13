package tankbrickgame;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameOverStage {
	public final static Image CONGRATS = new Image("images/congrats.gif");
	public final static Image YOULOSE = new Image("images/youlose.gif");

	private StackPane gameOverRoot;
	private VBox vbox;
	private Scene gameOverScene;
	private GraphicsContext gameOverGc;
	private Canvas gameOverCanvas;
	private Stage stage;

	private int score;
	private int win_lose;

	GameOverStage(int i, Stage stage, int score){
		this.gameOverRoot = new StackPane();
		this.vbox = new VBox();
		this.gameOverScene = new Scene(gameOverRoot, SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);
		this.gameOverCanvas = new Canvas(SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);
		this.gameOverGc = gameOverCanvas.getGraphicsContext2D();
		this.win_lose = i;
		this.score = score;
		this.stage = stage;

		this.setProperties();
	}

	private void setProperties(){

		this.gameOverGc.setFill(Color.POWDERBLUE);										//set font color of text
		this.gameOverGc.fillRect(0,0,SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);

		Font theFont = Font.font("Times New Roman",FontWeight.BOLD,30);             	//set font type, style and size
		this.gameOverGc.setFont(theFont);

		gameOverGc.setTextAlign(TextAlignment.CENTER);

		// check if user wins or loses
		if (win_lose == 0) {
			gameOverRoot.getChildren().add(initCanvas(YOULOSE));

		} else {
			gameOverRoot.getChildren().add(initCanvas(CONGRATS));
		}

		vbox = this.createVBox(win_lose);

		gameOverRoot.getChildren().add(vbox);
	}

	// for setting background
	private ImageView initCanvas(Image image){

        ImageView view = new ImageView();
        view.setImage(image);
        view.setFitHeight(gameOverCanvas.getHeight());
        view.setFitWidth(gameOverCanvas.getWidth());
        gameOverGc.drawImage(image, 0, 0, gameOverCanvas.getWidth(), gameOverCanvas.getHeight());
        return view;
	}

	// content of the screen
	private VBox createVBox(int win_lose){
		VBox vbox = new VBox();

		Text score = new Text("Score: " + this.score);

		score.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 25));

		score.setStrokeWidth(.1);
		score.setStroke(Color.BLACK);
		if(win_lose == 0){
			score.setFill(Color.SNOW);
		} else {
			score.setFill(Color.BLACK);
		}

		gameOverRoot.getChildren().add(score);

		// creating buttons
		Button exitbtn = new Button("Exit Game");
		Button playagain = new Button("Play  Again");

		// handle button events
		this.addEventHandler(exitbtn, 0);
		this.addEventHandler(playagain, 1);

		// format buttons
		setButtons(exitbtn);
		setButtons(playagain);

		vbox.getChildren().addAll(score, exitbtn, playagain);


		// setting up properties
		vbox.setAlignment(Pos.CENTER);
		vbox.setTranslateY(60);
		vbox.setSpacing(15);

		return vbox;
	}

	// for formatting button
	private void setButtons(Button btn){
		btn.setStyle("-fx-border-color: GRAY; -fx-border-width: 2px;"
				+ "-fx-text-fill: BLACK; "
				+ "-fx-font-size: 15px; -fx-border-radius: 5; -fx-font-family: ArcadeClassic");
		btn.setMaxSize(SplashScreen.BUTTON_SIZE, SplashScreen.BUTTON_SIZE);
	}


	private void addEventHandler(Button btn, int i) {
		// exit
		if (i == 0){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent arg0) {
					System.exit(0);
				}
			});

		// return to splashscreen
		} else if (i == 1) {
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {

					SplashScreen GameStage = new SplashScreen();
					GameStage.setSplashScreen(stage);

				}
			});
		}
	}

	// getter
	Scene getScene(){
		return this.gameOverScene;
	}
}
