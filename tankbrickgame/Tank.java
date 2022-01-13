package tankbrickgame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Tank extends Sprite{
	public final static Image TANK_IMAGE = new Image("images/tank.png",Tank.TANK_WIDTH,Tank.TANK_WIDTH,false,false);
	public final static Image TANKREDTYPE_IMAGE = new Image("images/redBulletType.png",Tank.TANK_WIDTH,Tank.TANK_WIDTH,false,false);
	public final static Image TANKBLUETYPE_IMAGE = new Image("images/blueBulletType.png",Tank.TANK_WIDTH,Tank.TANK_WIDTH,false,false);
	public final static Image TANKHASIMMO_IMAGE = new Image("images/tankHasImmo.png",Tank.TANK_WIDTH,Tank.TANK_WIDTH,false,false);

	public final static int TANK_WIDTH = 50;
	public final static int STARTING_BULLETCOUNT = 20;

	protected int brickKilled;
	private int strength;

	private String name;
	private String currentBulletType;

	private boolean alive;

	private int bulletCount;

	private float immortality;

	private ArrayList<Bullet> bullets;

	Tank(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(51)+100;
		this.alive = true;
		this.immortality = 0;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Tank.TANKBLUETYPE_IMAGE);
		this.brickKilled = 0;
		this.bulletCount = STARTING_BULLETCOUNT;
		// initial bullet type is blue
		this.currentBulletType = Bullet.BLUE_TYPE;
	}

	//method that will get the bullets 'shot' by the tank
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		if (this.getBulletCount() > 0){
			//compute for the x and y initial position of the bullet
			int x = (int) (this.x + this.width/2);
			int y = (int) (this.y + this.height/3);
			/*
			 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
			 */
			this.bullets.add(new Bullet(x, y, this.getCurrentBulletType()));
			reduceBulletCount();
		}
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the tank if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if(this.getX()+this.dx <= SplashScreen.WINDOW_WIDTH-Tank.TANK_WIDTH && this.getX()+this.dx >= 0)
			this.x += this.dx;
		if(this.getY()+this.dy <= SplashScreen.WINDOW_HEIGHT-Tank.TANK_WIDTH && this.getY()+this.dy >= GameStage.GAMESTATUSBAR_HEIGHT)
			this.y += this.dy;
	}

	// collect powerup
	protected void collectPowerUp(PowerUps p){
		p.collected();
	}

	@Override
	protected void render(GraphicsContext gc){
		if(this.isImmortal()){
			gc.drawImage(TANKHASIMMO_IMAGE, this.x, this.y);
		} else{
			if(this.getCurrentBulletType().equals(Bullet.BLUE_TYPE)){
				gc.drawImage(TANKBLUETYPE_IMAGE, this.x, this.y);
			} else {
				gc.drawImage(TANKREDTYPE_IMAGE, this.x, this.y);
			}
		}
    }

	// toggle bullet type
	void changeBulletType(){
		if(this.getCurrentBulletType().equals(Bullet.BLUE_TYPE)){
			this.currentBulletType = Bullet.RED_TYPE;
		} else {
			this.currentBulletType = Bullet.BLUE_TYPE;
		}
	}

	// setters
	protected void die(){
    	this.alive = false;
    }

	protected void setStrength(int strength){
		this.strength += strength;

		if(this.strength <= 0){
			this.strength = 0;
			this.die();
		}
	}

	private void reduceBulletCount(){
		this.bulletCount--;
	}

	protected void addBulletCount(int i){
		this.bulletCount += i;
	}

	protected void addBrickKilled(){
		this.brickKilled++;
	}

	protected void setImmortality(float x){
		this.immortality += x;
	}

	// getters
	protected boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	protected boolean isImmortal(){
		if(this.immortality > 0) return true;
		return false;
	}

	protected int getBrickKilled(){
		return this.brickKilled;
	}

	protected String getName(){
		return this.name;
	}

	protected int getStrength(){
		return this.strength;
	}

	protected int getBulletCount(){
		return this.bulletCount;
	}

	private String getCurrentBulletType(){
		return this.currentBulletType;
	}
}
