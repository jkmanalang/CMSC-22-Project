package tankbrickgame;


import java.util.ArrayList;

import javafx.event.ActionEvent;
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
import javafx.stage.Stage;


public class SplashScreen {
	public final static Image BACKGROUND = new Image("images/splashbg.gif");

	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	public static final int BUTTON_SIZE = 125;

	private Stage stage;
	private Scene splashScene;
	private StackPane SplashScreenRoot;
	private Canvas splashCanvas;
	private GraphicsContext splashGc;
	private VBox splashVbox;

	private ArrayList<Button> buttons;

	//the class constructor
	public SplashScreen() {
		this.SplashScreenRoot = new StackPane();
		this.splashCanvas = new Canvas(SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);
		this.splashGc = splashCanvas.getGraphicsContext2D();
		this.splashVbox = new VBox();

		this.buttons = new ArrayList<Button>();
	}

	public void setSplashScreen(Stage stage){
		this.stage = stage;
		stage.setTitle("Tank Classic Brick Game");

		this.initSplash();

		stage.setScene( this.splashScene );
        stage.setResizable(false);
		stage.show();
	}

	private void initSplash(){
		SplashScreenRoot.getChildren().addAll(this.initCanvas(),this.createVBox());
        this.splashScene = new Scene(SplashScreenRoot);
	}

	// setting the background of this screen
	private ImageView initCanvas(){
        ImageView view = new ImageView();
        view.setImage(BACKGROUND);
        view.setFitHeight(splashCanvas.getHeight());
        view.setFitWidth(splashCanvas.getWidth());
        splashGc.drawImage(BACKGROUND, 0, 0, splashCanvas.getWidth(), splashCanvas.getHeight());
        return view;
    }

	// content of the splashscreen
    private VBox createVBox() {

    	splashVbox.setAlignment(Pos.CENTER);
        splashVbox.setSpacing(8);
//        splashVbox.setTranslateY(100);

        // title
		Text title = new Text("   Classic    Tank\n        Brick    Game");
		title.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 60));
		title.setStrokeWidth(1);
		title.setStroke(Color.BLACK);
		title.setFill(Color.SNOW);

		// creating buttons
        Button b1 = new Button("New Game");
        Button b2 = new Button("Instruction");
        Button b3 = new Button("About");
        Button b4 = new Button("Exit");

        this.buttons.add(b1);
        this.buttons.add(b2);
        this.buttons.add(b3);
        this.buttons.add(b4);

        // formatting all buttons
        for(int i = 0; i<buttons.size(); i++){
        	Button b = buttons.get(i);
        	b.setMaxSize(SplashScreen.BUTTON_SIZE, SplashScreen.BUTTON_SIZE);
        	b.setStyle("-fx-border-color: WHITE; -fx-border-width: 1px;"
					+ "-fx-background-color: #8952D3; -fx-text-fill: WHITE; "
					+ "-fx-font-size: 14px; -fx-border-radius: 5;"
					+ "-fx-font-family: ArcadeClassic");
        	b.setOpacity(.8);

        }

        // handle button hover
        handleHover();

        splashVbox.getChildren().addAll(title, b1, b2, b3, b4);

        // show game stage
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	showGameStage(stage);

            }
        });
        // show instruction
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	showInstruction(stage);

            }
        });
        // show about
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	showAbout(stage);

            }
        });
        // exit
        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	System.exit(0);
            }
        });

        return splashVbox;
    }

    // hover effect to the buttons
	private void handleHover(){
		for(int i = 0; i<buttons.size(); i++){
			Button b = buttons.get(i);
			b.setOnMouseEntered(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent arg0) {
					b.setOpacity(1);

				}
			});
			b.setOnMouseExited(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent arg0) {
		        	b.setOpacity(.8);
				}
			});
		}
	}

	void showInstruction(Stage stage){
		Instruction instruction = new Instruction(stage, this.getScene());
		stage.setScene(instruction.getScene());
	}

	void showAbout(Stage stage){
		About about = new About(stage, this.getScene());
		stage.setScene(about.getScene());
	}

	void showGameStage(Stage stage){
    	GameStage gameStage = new GameStage();
    	gameStage.setGameScreen(stage);
	}

	Scene getScene(){
		return this.splashScene;
	}
}
