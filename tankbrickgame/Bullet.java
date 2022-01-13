package tankbrickgame;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	public final static Image REDBULLET_IMAGE = new Image("images/redbullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	public final static Image BLUEBULLET_IMAGE = new Image("images/bluebullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);

	public final static int BULLET_WIDTH = 15;
	public final static int BULLET_SPEED = 5;

	public final static String RED_TYPE = "red";
	public final static String BLUE_TYPE = "blue";

	private String type;

	public Bullet(int x, int y, String type){
		super(x,y);
		if(type.equals(Bullet.BLUE_TYPE)){
			this.loadImage(Bullet.BLUEBULLET_IMAGE);
		} else {
			this.loadImage(Bullet.REDBULLET_IMAGE);
		}

		this.type = type;
	}


	// method that will move/change the x position of the bullet
	public void move(){
		/*
		 *                Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */

		this.setX(Bullet.BULLET_SPEED);
		if(this.getX() >= SplashScreen.WINDOW_WIDTH){				// if this bullet passes through the top of the scene, set visible to false
			this.setVisible(false);
		}
	}

	// getter
	String getType(){
		return this.type;
	}
}