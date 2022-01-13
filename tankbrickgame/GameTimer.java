package tankbrickgame;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{
	public final static Image GAMESTAGEBG = new Image("images/gameStageBG.jpg");

	public final static int MAX_NUM_BRICKS = 3;
	public final static int MAX_NUM_POWERUP = 1;
	public final static int STARTING_BRICKS = 7;

	private final static double SPAWNBRICK_DELAY = 5;
	private final static double SPAWNBOSS_DELAY = 30;
	private final static double SPAWNPOWERUP_DELAY = 10;
	private final static double BULLETREFRESH_DELAY = 2;
	private final static double TOGGLEBOSSTYPE_DELAY = 5;

	private final static float GAME_DURATION = 60;

	private long intervalSpawnBrick;
	private long intervalSpawnPowerUp;
	private long startSpawn;
	private long previousNanoTime;
	private long intervalbulletRefresh;
	private long intervalToggleBossType;

	private GraphicsContext gc;
	private Scene theScene;
	private GameStage gameStage;
	private Tank myTank;

	private ArrayList<Brick> bricks;
	private ArrayList<PowerUps> powerUps;

	private boolean bossSpawned;

	private float timer;

	GameTimer(GraphicsContext gc, Scene theScene, GameStage gameStage){
		this.timer = GameTimer.GAME_DURATION;
		this.gc = gc;
		this.theScene = theScene;
		this.gameStage = gameStage;
		this.myTank = new Tank("Going merry",150,250);

		// instantiate the ArrayList of Brick
		this.bricks = new ArrayList<Brick>();
		this.powerUps = new ArrayList<PowerUps>();

    	this.intervalSpawnBrick = this.intervalSpawnPowerUp = this.intervalToggleBossType = System.nanoTime();
    	this.startSpawn = this.previousNanoTime = this.intervalbulletRefresh = System.nanoTime();

		// call the spawnBricks method
		this.spawnBricks(STARTING_BRICKS);

		// call method to handle mouse click event
		this.handleKeyPressEvent();

		this.bossSpawned = false;
	}

	@Override
	public void handle(long currentNanoTime) {
		// computation needed for timing
		double fishSpawnElapsedTime = (currentNanoTime - this.intervalSpawnBrick) / 1000000000.0;
		double powerupSpawnElapsedTime = (currentNanoTime - this.intervalSpawnPowerUp) / 1000000000.0;
		double currentOverElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
		double bulletRefreshElapsedTime = (currentNanoTime - this.intervalbulletRefresh) / 1000000000.0;
		double toggleBossTypeElapsedTime = (currentNanoTime - this.intervalToggleBossType) / 1000000000.0;

		this.gc.clearRect(0, 0, SplashScreen.WINDOW_WIDTH,SplashScreen.WINDOW_HEIGHT);

		// draw background and status bar
		this.drawBackground();
		this.drawGameStatusBar();

		// move tank and bullets
		this.myTank.move();
		this.moveBullets();

		// randomly toggle boss type after 5 seconds
		if(toggleBossTypeElapsedTime > GameTimer.TOGGLEBOSSTYPE_DELAY) {
        	Random r = new Random();
			this.moveBricks(r.nextInt(2));
        	this.intervalToggleBossType = System.nanoTime();
        } else {
        	this.moveBricks(0);
        }

		// reducing the values of power up timer, immortality timer and timer
		this.checkPowerUp((float) ((currentNanoTime - this.previousNanoTime)/1000000000.0));
		this.checkShipImmortality((float) ((currentNanoTime - this.previousNanoTime)/1000000000.0));
		this.updateTimer((float) ((currentNanoTime - this.previousNanoTime)/1000000000.0));

		this.previousNanoTime = currentNanoTime;

		// spawning the necessary sprites
        if((int) currentOverElapsedTime == GameTimer.SPAWNBOSS_DELAY && !bossSpawned()){
        	this.spawnBossBrick();
        	this.spawnBoss();
        }

        if(fishSpawnElapsedTime > GameTimer.SPAWNBRICK_DELAY) {
        	this.spawnBricks(MAX_NUM_BRICKS);
        	this.intervalSpawnBrick = System.nanoTime();
        }

        if(powerupSpawnElapsedTime > GameTimer.SPAWNPOWERUP_DELAY) {
        	Random r = new Random();

//        	uncomment to spawn all powerups simultaneously
//        	this.spawnPowerUp(1);
//        	this.spawnPowerUp(2);
//        	this.spawnPowerUp(3);
        	this.spawnPowerUp(r.nextInt(PowerUps.POWERUPTYPE_COUNT)+1);
        	this.intervalSpawnPowerUp = System.nanoTime();
        }

        if(bulletRefreshElapsedTime > GameTimer.BULLETREFRESH_DELAY){
        	this.myTank.addBulletCount(1);
        	this.intervalbulletRefresh = System.nanoTime();
        }

        // render sprites
      	this.myTank.render(this.gc);
        this.renderBricks();
        this.renderBullets();
        this.renderPowerUp();

        // checking if the game will end at this time
        if(!this.myTank.isAlive()){
        	this.stop();
        	this.gameStage.drawGameOver(0, myTank.getBrickKilled());
        }

        if(currentOverElapsedTime > GameTimer.GAME_DURATION){
        	this.stop();
        	this.gameStage.drawGameOver(1, myTank.getBrickKilled());
        }

        // drawing the health of boss and bullet counts of tank
        drawBulletCount();
        drawBossHealth();
	}

	private void drawBackground(){
		this.gc.drawImage(GAMESTAGEBG, 0, 0, SplashScreen.WINDOW_WIDTH, SplashScreen.WINDOW_HEIGHT);
	}

	private void updateTimer(float x){
		this.timer -= x;
	}

	// for drawing the bullet count on top of the tank
	private void drawBulletCount(){
		this.gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText(myTank.getBulletCount()+"", myTank.getX()+(Tank.TANK_WIDTH/3), myTank.getY()-2);
	}

	// for drawing the health of the boss
	private void drawBossHealth(){
		for (Brick f : this.bricks){
			if(f instanceof BossBrick){
				this.gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
				this.gc.setFill(Color.BLACK);
				this.gc.fillText(((BossBrick) f).getHealth()+"", f.getX()+(Brick.BOSSBRICK_WIDTH/3), f.getY()-2);
			}
		}
	}

	// formatting the game status bar
	private void drawGameStatusBar(){
		// for score
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("Score:", 20, 35);
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 40));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText(myTank.getBrickKilled()+"", 120, 38);

		// for tank strength
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("Tank    Str:", (SplashScreen.WINDOW_WIDTH/2)-110, 35);
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 40));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText(myTank.getStrength()+"", (SplashScreen.WINDOW_WIDTH/2)+38, 38);

		// for timer
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("Timer:", (SplashScreen.WINDOW_WIDTH)-180, 35);
		this.gc.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 40));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText((int) this.getTimer()+"", (SplashScreen.WINDOW_WIDTH)-80, 38);
	}

	//method that will render/draw the bricks to the canvas
	private void renderBricks() {
		for (Brick f : this.bricks){
			f.render(this.gc);
		}
	}

	//method that will render/draw the power-up to the canvas
	private void renderPowerUp() {
		for (PowerUps p : this.powerUps){
			p.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {

		// loop through the bullets arraylist of myShip and render each bullet to the canvas
        for (Bullet b : this.myTank.getBullets())
        	b.render( this.gc );
	}

	//method that will spawn/instantiate three fishes at a random x,y location
	private void spawnBricks(int bricks){
		Random r = new Random();
		for(int i=0;i<bricks;i++){
			int x = r.nextInt(SplashScreen.WINDOW_WIDTH/2)+SplashScreen.WINDOW_WIDTH/2-Brick.BRICK_WIDTH;
			int y = r.nextInt(SplashScreen.WINDOW_HEIGHT-(Brick.BRICK_WIDTH+GameStage.GAMESTATUSBAR_HEIGHT))+GameStage.GAMESTATUSBAR_HEIGHT;

			// add new object Brick to the bricks arraylist
			this.bricks.add(new Brick(x, y, r.nextInt(2)));
		}
	}

	// for spawning random power up in random location
	private void spawnPowerUp(int type){
		Random r = new Random();
		int x = r.nextInt((SplashScreen.WINDOW_WIDTH/2)-PowerUps.POWERUPS_WIDTH);
		int y = r.nextInt(SplashScreen.WINDOW_HEIGHT-(PowerUps.POWERUPS_WIDTH+GameStage.GAMESTATUSBAR_HEIGHT))+GameStage.GAMESTATUSBAR_HEIGHT-PowerUps.POWERUPS_WIDTH;

		switch(type){
			case 1 : this.powerUps.add(new GoldOil(x,y)); break;
			case 2 : this.powerUps.add(new Oil(x,y)); break;
			case 3 : this.powerUps.add(new Ammo(x,y)); break;
			default: break;
		}
	}

	// for spawning boss brick in random location
	private void spawnBossBrick(){
		Random r = new Random();
		int x = r.nextInt(SplashScreen.WINDOW_WIDTH/2)+(SplashScreen.WINDOW_WIDTH/2)-Brick.BOSSBRICK_WIDTH;
		int y = r.nextInt(SplashScreen.WINDOW_HEIGHT-(Brick.BOSSBRICK_WIDTH+GameStage.GAMESTATUSBAR_HEIGHT))+GameStage.GAMESTATUSBAR_HEIGHT;

		this.bricks.add(new BossBrick(x,y,r.nextInt(2)));
	}

	// for checking if ship has immortality. if yes, reduce it by x
	private void checkShipImmortality(float x){
		if(this.myTank.isImmortal()){
			this.myTank.setImmortality(-x);
		} else
			this.myTank.setImmortality(0);
	}

	// checking if power ups has been collected or run out of time
	private void checkPowerUp(float x){
		for(int i = 0; i < this.powerUps.size(); i++){
			PowerUps p = this.powerUps.get(i);

			if(!p.isCollected() && !p.isTimeOut()){
				p.checkCollision(this.myTank);
				p.reduceLifeSpan(x);
			}else{
				this.powerUps.remove(i);
			}
		}
	}

	//method that will move the bullets shot by a tank
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myTank.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);

			// if a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			if(b.isVisible())
				b.move();
			else
				bList.remove(i);
		}
	}

	//method that will move the bricks
	private void moveBricks(int toggle){
		//Loop through the bricks arraylist
		for(int i = 0; i < this.bricks.size(); i++){
			Brick f = this.bricks.get(i);

			// if a brick is alive, it will move. Else, it will be removed from bricks arraylist.
			if(f.isAlive()){
				if(toggle == 1){
					if(f instanceof BossBrick){
						BossBrick b = (BossBrick) f;
						b.toggleType();
					}
				}
				f.move();
				f.checkCollision(this.myTank);
			}
			else this.bricks.remove(i);
		}
	}

	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				KeyCode code = e.getCode();
				stopMyTank(code);
			}
		});
    }

	//method that will move the tank depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) this.myTank.setDY(-3);

		if(ke==KeyCode.LEFT) this.myTank.setDX(-3);

		if(ke==KeyCode.DOWN) this.myTank.setDY(3);

		if(ke==KeyCode.RIGHT) this.myTank.setDX(3);

		if(ke==KeyCode.SPACE) this.myTank.shoot();

		if(ke==KeyCode.C) this.myTank.changeBulletType();

//		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the tank's movement; set the ship's DX and DY to 0
	private void stopMyTank(KeyCode ke){
		this.myTank.setDX(0);
		this.myTank.setDY(0);
	}

	// setter
	private void spawnBoss(){
		this.bossSpawned = true;
	}

	// getter
	private boolean bossSpawned(){
		return this.bossSpawned;
	}

	private float getTimer(){
		return this.timer;
	}
}
