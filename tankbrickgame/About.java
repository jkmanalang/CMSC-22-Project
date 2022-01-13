package tankbrickgame;


import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class About {
	public final static Image ABOUTBG = new Image("images/aboutBG.jpg");

	private Stage stage;
	private Scene aboutScene;
	private StackPane aboutRoot;
	private BorderPane aboutBorder;
	private Canvas aboutCanvas;
	private GraphicsContext aboutGc;
	private Scene splashScreenScene;

	public About(Stage stage, Scene splashScreenScene){
		this.aboutCanvas = new Canvas(SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);
		this.aboutGc = aboutCanvas.getGraphicsContext2D();
		this.aboutRoot = new StackPane(aboutCanvas);
		this.aboutScene = new Scene(aboutRoot, SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);
		this.stage = stage;
		this.splashScreenScene = splashScreenScene;
		this.aboutBorder = new BorderPane();

		this.setProperties();

	}

	// method for adding nodes to BorderPane and adding it to StackPane root
	private void setProperties(){

		aboutBorder.setCenter(addGridPane());
		aboutBorder.setBottom(createVBox());

		initCanvas();

		aboutRoot.getChildren().add(aboutBorder);
	}

	// adding GridPane to the center of BorderPane
	private GridPane addGridPane(){

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setTranslateX(100);
		grid.setTranslateY(70);
//		grid.setGridLinesVisible(true);

		// creating texts
		Text developer = createText("Developer: ");
		Text name = createText("John   Kenneth   Fader   Manalang");
		Text age = createText("19   yo   as   of   2021   December");
		Text section = createText("CMSC   22   Student   Section   W3L");
		Text references = createText("References: ");

		Text cmsc22basecode = createText("CMSC   22   base   code   (template)");
		Text images = createText("Images: ");

		TextArea imagestxt = addTextArea("1) https://www.shutterstock.com/image-vector/computer-gamer-keyboard-wasd-keys-vector-1534131284\n"
				+ "2) https://www.seekpng.com/ipng/u2q8a9i1u2i1o0e6_black-arrow-clip-art-at-clker-black-arrow/\n"
				+ "3) https://www.pinterest.ph/pin/593912269600850438/\n"
				+ "4) https://www.pinterest.ph/pin/335658978487990709/\n"
				+ "5) https://www.pikpng.com/pngvi/bmiiTm_oil-png-picture-petroleum-oil-clipart/\n"
				+ "6) https://imgbin.com/png/M6jcvuiY/oil-png\n"
				+ "7) https://www.wallpaperflare.com/pixels-pixel-art-8-bit-moon-stars-video-games-space-dragon-clouds-cave-story-wallpaper-hdavk");

		Text debugging = createText("Debugging: ");
		TextArea debuggingtxt = addTextArea("1) https://stackoverflow.com/questions/6020719/what-could-cause-java-lang-reflect-invocationtargetexception\n"
				+ "2) https://stackoverflow.com/questions/64865412/fonts-installed-in-windows-not-shown-in-scenebuilder");
		debuggingtxt. setPrefHeight(30);

		Text others = createText("Others: ");
		TextArea otherstxt = addTextArea("1) https://stackoverflow.com/questions/23104582/scaling-an-image-to-fit-on-canvas\n"
				+ "2) https://www.tabnine.com/code/java/methods/javafx.scene.canvas.GraphicsContext/drawImage\n"
				+ "3) https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html\n"
				+ "4) https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx\n"
				+ "5) https://www.programcreek.com/java-api-examples/?class=javafx.scene.shape.Rectangle&method=setOpacity\n"
				+ "6) https://htmlcolorcodes.com/\n"
				+ "7) http://tutorials.jenkov.com/javafx/button.html\n"
				+ "8) https://www.codegrepper.com/code-examples/java/javafx+textarea+size\n"
				+ "9) https://www.codegrepper.com/code-examples/java/javafx+textarea+size\n"
				+ "10) https://www.1001fonts.com/arcadeclassic-font.html");

		// layout of GridPane
		grid.add(developer, 0, 0);
		grid.add(name, 1, 0);
		grid.add(age, 1, 1);
		grid.add(section, 1, 2);

		grid.add(references, 0, 7);
		grid.add(cmsc22basecode, 1, 7);

		grid.add(images, 1, 8);
		grid.add(imagestxt, 2, 8);

		grid.add(debugging, 1, 9);
		grid.add(debuggingtxt, 2, 9);

		grid.add(others, 1, 10);
		grid.add(otherstxt, 2, 10);

		return grid;
	}

	// method helper to create and format text
	private Text createText(String str){
		Text text = new Text(str);
		text.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 17));
		text.setFill(Color.SNOW);

		return (text);
	}

	// method helper to create and format TextArea where the links will be put
	TextArea addTextArea(String link){
		TextArea lnk = new TextArea();
		lnk.setText(link);
		lnk.setPrefHeight(70);
		lnk.setPrefWidth(250);
		lnk.setStyle("-fx-background-color: rgba(53,89,119,0.2)");
		return lnk;
	}

	// VBox that will have the button that will return to splashscreen
	private VBox createVBox(){
		VBox vbox = new VBox();
		Button back = new Button("back");
		back.setMaxWidth(115);
		back.setStyle("-fx-border-color: GRAY; -fx-border-width: 1.5px;"
				+ "-fx-background-color: #8952D3; -fx-text-fill: WHITE; "
				+ "-fx-font-size: 15px; -fx-border-radius: 5; -fx-font-family: ArcadeClassic;");

		// handle hover
		handleHover(back);

		// return to splashscreen
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				stage.setScene(splashScreenScene);
			}
		});

		vbox.getChildren().add(back);

		// setting up the vbox position
		vbox.setAlignment(Pos.BOTTOM_RIGHT);
		vbox.setTranslateX(-5);
		vbox.setTranslateY(-5);

		return vbox;
	}

	// method for handling the hover properties of back button
	private void handleHover(Button btn){
		btn.setOnMouseEntered(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				btn.setStyle("-fx-background-color: #9B82BD; -fx-text-fill: WHITE; "
						+ " -fx-font-family: ArcadeClassic; -fx-font-size: 14px");

			}
		});
		btn.setOnMouseExited(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0) {
				btn.setStyle("-fx-border-color: GRAY; -fx-border-width: 1.5px;"
						+ "-fx-background-color: #8952D3; -fx-text-fill: WHITE; "
						+ "-fx-font-size: 15px; -fx-border-radius: 5; -fx-font-family: ArcadeClassic;");
			}
		});
	}

	// for about page background
	private void initCanvas(){
        aboutGc.drawImage(ABOUTBG, 0, 0, aboutCanvas.getWidth(), aboutCanvas.getHeight());
    }

	Scene getScene(){
		return this.aboutScene;
	}
}
