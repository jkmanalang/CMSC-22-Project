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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Instruction {

	public final static Image INSTRUCTIONBG = new Image("images/instructionBG.gif");

	private Stage stage;
	private Scene instructionScene;
	private StackPane instructionRoot;
	private BorderPane instructionBorder;
	private Canvas instructionCanvas;
	private GraphicsContext instructionGc;
	private Scene splashScreenScene;

	public Instruction(Stage stage, Scene splashScreenScene){

		this.instructionBorder = new BorderPane();
		this.instructionCanvas = new Canvas(SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);
		this.instructionGc = instructionCanvas.getGraphicsContext2D();

		this.instructionRoot = new StackPane(instructionCanvas);

		this.instructionScene = new Scene(instructionRoot, SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);

		this.stage = stage;
		this.splashScreenScene = splashScreenScene;
		this.setProperties();

	}

	private void setProperties(){

		instructionBorder.setCenter(addGridPane());
		instructionBorder.setBottom(createVBox());

		// this is just for blocking the content of the tv
		Rectangle eraseTV = new Rectangle(127, 115);
		eraseTV.setFill(Color.MEDIUMPURPLE);
		eraseTV.setOpacity(.9);
		eraseTV.setTranslateX(110);
		eraseTV.setTranslateY(-75);
		tvHover(eraseTV);

		// visible area where the text will be located
		Rectangle space = new Rectangle(600, 400);
//		space.setTranslateX(-160);
		space.setFill(Color.MEDIUMPURPLE);
		space.setOpacity(.3);
		space.setArcHeight(30);
		space.setArcWidth(30);

		// instruction page label
		Text instruction = new Text("INSTRUCTIONS");
		instruction.setTranslateX(130);
		instruction.setTranslateY(-205);
		instruction.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, FontPosture.ITALIC, 45));
		instruction.setFill(Color.SNOW);
		instruction.setStrokeWidth(.5);
		instruction.setStroke(Color.BLACK);

		instructionRoot.getChildren().addAll(initCanvas(), space, instruction, instructionBorder, eraseTV);
	}

	// hover effect on eraseTV rectangle
	private void tvHover(Rectangle tv){
		tv.setOnMouseEntered(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				tv.setFill(Color.MEDIUMPURPLE);
				tv.setOpacity(0);
				tv.setTranslateX(110);
				tv.setTranslateY(-75);
			}
		});
		tv.setOnMouseExited(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				tv.setFill(Color.MEDIUMPURPLE);
				tv.setOpacity(1);
				tv.setTranslateX(110);
				tv.setTranslateY(-75);
			}
		});
	}

	// the center of the instruction page
	private GridPane addGridPane(){
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(5);
		grid.setTranslateX(106);
		grid.setTranslateY(70);
//		grid.setGridLinesVisible(true);

		// formatting the images
		ImageView arrowUp = createImageView(new Image("images/arrowUp.png"), 35);
		ImageView arrowDown = createImageView(new Image("images/arrowDown.png"), 35);
		ImageView arrowRight = createImageView(new Image("images/arrowRight.png"), 35);
		ImageView arrowLeft = createImageView(new Image("images/arrowLeft.png"), 35);
		ImageView spacebar = new ImageView(new Image("images/spacebar.png"));
		spacebar.setFitHeight(40);
		spacebar.setFitWidth(70);
		ImageView tank = createImageView(Tank.TANK_IMAGE, 35);
		ImageView brick = createImageView(Brick.BRICK_IMAGE, 35);
		ImageView oil = createImageView(PowerUps.OIL_IMAGE, 25);
		ImageView goldOil = createImageView(PowerUps.GOLDOIL_IMAGE, 25);
		ImageView ammo = createImageView(PowerUps.AMMO_IMAGE, 25);
		ImageView bossBrick = createImageView(Brick.BOSSBRICK_IMAGE, 75);

		// adding images to the grid
		grid.add(arrowUp, 0, 0);
		grid.add(arrowDown, 0, 1);
		grid.add(arrowRight, 0, 2);
		grid.add(arrowLeft, 0, 3);
		grid.add(spacebar, 0, 4);
		grid.add(tank, 0, 5);
		grid.add(brick, 0, 6);

//		grid.add(blueBullet, 0, 8);
		grid.add(oil, 10, 4);
		grid.add(goldOil, 10, 5);
		grid.add(ammo, 10, 6);
		grid.add(bossBrick, 10, 7, 2, 2);

		// creating text
		Text bullets = createText(" Bullets");
		Text bulletsDescrip = createText(" - Red:  destroy  blue  brick\n"
				+ "       : add  health  to  blue  boss\n"
				+ " - Blue: destroy  red  brick\n"
				+ "       : add  health  to  red  boss\n"
				+ " C   key:   to    change    bullet");
		Text arrowUp_text = createText(" - move up");
		Text arrowDown_text = createText(" - move down");
		Text arrowRight_text = createText(" - move right");
		Text arrowLeft_text = createText(" - move left");
		Text spacebar_text = createText(" - shoot bullet");
		Text tank_text = createText(" - user's tank");
		Text brick_text = createText(" - brick enemy");
		Text oil_text = createText(" - add    50    strength to tank");
		Text goldOil_text = createText(" - gain    3    second immortality");
		Text ammo_text = createText(" - recharge    10    bullets");
		Text bossBrick_text = createText("                     - boss enemy");

		// adding texts in grid
		grid.add(arrowUp_text, 1, 0);
		grid.add(arrowDown_text, 1, 1);
		grid.add(arrowRight_text, 1, 2);
		grid.add(arrowLeft_text, 1, 3);
		grid.add(spacebar_text, 1, 4);
		grid.add(tank_text, 1, 5);
		grid.add(brick_text, 1, 6);
		grid.add(bullets, 0, 8);
		grid.add(bulletsDescrip, 1, 8, 8, 1);
		grid.add(oil_text, 11, 4);
		grid.add(goldOil_text, 11, 5);
		grid.add(ammo_text, 11, 6);
		grid.add(bossBrick_text, 11, 7, 2, 2);

		return grid;
	}

	// method helper in creating and formatting text
	private Text createText(String str){
		Text text = new Text(str);
		text.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 17));
		text.setStrokeWidth(.15);
		text.setStroke(Color.BLACK);
		text.setFill(Color.SNOW);

		return (text);
	}

	// method helper in creating images with same size (35x35)
	private ImageView createImageView(Image img, int i){
		ImageView view = new ImageView(img);
		view.setFitHeight(i);
		view.setFitWidth(i);
		return view;
	}

	// for setting the button
	private VBox createVBox(){
		VBox vbox = new VBox();
		Button back = new Button("back");
		back.setMaxWidth(115);

		back.setStyle("-fx-border-color: GRAY; -fx-border-width: 1.5px;"
				+ "-fx-background-color: #8952D3; -fx-text-fill: WHITE; "
				+ "-fx-font-size: 15px; -fx-border-radius: 5; -fx-font-family: ArcadeClassic");

		handleButtonHover(back);

		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				stage.setScene(splashScreenScene);
			}
		});

		vbox.getChildren().add(back);

		// setting up properties
		vbox.setAlignment(Pos.BOTTOM_RIGHT);
		vbox.setTranslateX(-5);
		vbox.setTranslateY(-5);

		return vbox;
	}


	// hover effect for back button
	private void handleButtonHover(Button btn){
		btn.setOnMouseEntered(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				btn.setStyle("-fx-background-color: #9B82BD; -fx-text-fill: WHITE; "
						+ " -fx-font-size: 14px; -fx-font-family: ArcadeClassic;");
			}
		});
		btn.setOnMouseExited(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				btn.setStyle("-fx-border-color: GRAY; -fx-border-width: 1.5px;"
						+ "-fx-background-color: #8952D3; -fx-text-fill: WHITE; "
						+ "-fx-font-size: 15px; -fx-border-radius: 5; -fx-font-family: ArcadeClassic");
			}
		});
	}

	// method for the page background
	private ImageView initCanvas(){
        ImageView view = new ImageView();
        view.setImage(INSTRUCTIONBG);
        view.setFitHeight(instructionCanvas.getHeight());
        view.setFitWidth(instructionCanvas.getWidth());
        instructionGc.drawImage(INSTRUCTIONBG, 0, 0, instructionCanvas.getWidth(), instructionCanvas.getHeight());
        return view;
    }

	Scene getScene(){
		return this.instructionScene;
	}
}
