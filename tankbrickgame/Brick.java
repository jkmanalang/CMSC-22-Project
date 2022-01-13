package tankbrickgame;

import java.util.Random;

import javafx.scene.image.Image;

public class Brick extends Sprite {
	public final static Image BRICK_IMAGE = new Image("images/brick.png",Brick.BRICK_WIDTH,Brick.BRICK_WIDTH,false,false);
	public final static Image BLUEBRICK_IMAGE = new Image("images/bluebrick.png",Brick.BRICK_WIDTH,Brick.BRICK_WIDTH,false,false);
	public final static Image REDBRICK_IMAGE = new Image("images/redbrick.png",Brick.BRICK_WIDTH,Brick.BRICK_WIDTH,false,false);

	public final static Image BOSSBRICK_IMAGE = new Image("images/bossbrick.png",Brick.BOSSBRICK_WIDTH,Brick.BOSSBRICK_WIDTH,false,false);
	public final static Image REDBOSSBRICK_IMAGE = new Image("images/redbossbrick.png",Brick.BOSSBRICK_WIDTH,Brick.BOSSBRICK_WIDTH,false,false);
	public final static Image BLUEBOSSBRICK_IMAGE = new Image("images/bluebossbrick.png",Brick.BOSSBRICK_WIDTH,Brick.BOSSBRICK_WIDTH,false,false);

	public final static Image BRICKGOTHIT_IMAGE = new Image("images/brickgothit.png",Brick.BRICK_WIDTH,Brick.BRICK_WIDTH,false,false);

	public final static int MAX_BRICK_SPEED = 5;
	public final static int BRICK_WIDTH=50;
	public final static int BRICK_DAMAGE=30;
	public final static int BOSSBRICK_WIDTH=130;
	public final static int BOSSBRICK_DAMAGE=50;
	public final static int BOSSBRICK_HEALTH=3000;

	private boolean alive;
	//attribute that will determine if a brick will initially move to the right
	protected boolean moveRight;

	protected int speed;

	protected String type;


	Brick(int x, int y, int type){
		super(x,y);
		this.alive = true;
		if(type == 1){
			this.type = Bullet.BLUE_TYPE;
			this.loadImage(Brick.BLUEBRICK_IMAGE);
		} else {
			this.type = Bullet.RED_TYPE;
			this.loadImage(Brick.REDBRICK_IMAGE);
		}

		/*
		 * Randomize speed of brick and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt(MAX_BRICK_SPEED+1)+1;
		this.moveRight = false;
	}

	//method that changes the x position of the brick
	void move(){
		/*
		 *          				If moveRight is true and if the brick hasn't reached the right boundary yet,
		 *    						move the brick to the right by changing the x position of the brick depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the brick hasn't reached the left boundary yet,
		 * 	 						move the brick to the left by changing the x position of the brick depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if(this.moveRight){
			setX(this.speed);
			if(this.getX() >= SplashScreen.WINDOW_WIDTH-Brick.BRICK_WIDTH)
				setMoveRight(false);
		} else {
			setX(-this.speed);
			if(this.getX() <= 0)
				setMoveRight(true);
		}
	}

	// for checker if it collided with tank or tank's bullet
	void checkCollision(Tank tank){

		// check if it collides with a bullet
		for	(int i = 0; i < tank.getBullets().size(); i++)	{
			if (this.collidesWith(tank.getBullets().get(i))){

				Bullet b = tank.getBullets().get(i);

				// check the bullet type
				if(!(b.getType()).equals(this.getType())){
					this.loadImage(BRICKGOTHIT_IMAGE);
					this.die();
					tank.getBullets().get(i).setVisible(false);
					tank.addBrickKilled();

				} else {
					tank.getBullets().get(i).setVisible(false);
				}

			}
		}

		// check if it collides with tank
		if(this.collidesWith(tank)){
			this.die();
			if(!tank.isImmortal())
				tank.setStrength(-Brick.BRICK_DAMAGE);
		}
	}

	// getter
	public boolean isAlive() {
		return this.alive;
	}

	String getType(){
		return this.type;
	}

	// setter
	protected void setMoveRight(boolean moveRight){
		this.moveRight = moveRight;
	}

	protected void die(){
		this.alive = false;
	}
}
