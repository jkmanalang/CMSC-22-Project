package tankbrickgame;

public class BossBrick extends Brick{

	private int health;

	BossBrick(int x, int y, int type){
		super(x,y,type);

		if(type == 1){
			this.loadImage(Brick.BLUEBOSSBRICK_IMAGE);
		} else {
			this.loadImage(Brick.REDBOSSBRICK_IMAGE);
		}

		this.health = Brick.BOSSBRICK_HEALTH;

	}

	// checker if boss collided with tank or tank's bullet
	@Override
	void checkCollision(Tank tank){

		// check if it collides with a bullet
		for	(int i = 0; i < tank.getBullets().size(); i++)	{

			if (this.collidesWith(tank.getBullets().get(i))){

				Bullet b = tank.getBullets().get(i);

				// checking the bullet type
				if(!(b.getType()).equals(this.getType())){
					this.setHealth(-tank.getStrength());
					tank.getBullets().get(i).setVisible(false);

					if(!this.isAlive()){
						tank.addBrickKilled();
					}

				} else {
					this.addHealth();
					tank.getBullets().get(i).setVisible(false);
				}
			}
		}

		// check if it collides with the tank
		if(this.collidesWith(tank)){
			if(!tank.isImmortal()){
				tank.setStrength(-Brick.BOSSBRICK_DAMAGE);
				tank.setImmortality(2);
			}
		}
	}

	// setter
	void setHealth(int damage){
		this.health += damage;

		if(this.health <= 0){
			this.die();
		}
	}

	int getHealth(){
		return this.health;
	}

	private void addHealth(){
		this.health += 50;
	}

	@Override
	void move(){
		/*
		 *     				     If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if(this.moveRight){
			setX(this.speed);
			if(this.getX() >= SplashScreen.WINDOW_WIDTH-Brick.BOSSBRICK_WIDTH)
				setMoveRight(false);
		} else {
			setX(-this.speed);
			if(this.getX() <= 0)
				setMoveRight(true);
		}
	}

	// toggle the type of the boss brick
	void toggleType(){
		if(this.getType().equals(Bullet.BLUE_TYPE)){
			this.type = Bullet.RED_TYPE;
			this.loadImage(Brick.REDBOSSBRICK_IMAGE);

		} else {
			this.type = Bullet.BLUE_TYPE;
			this.loadImage(Brick.BLUEBOSSBRICK_IMAGE);
		}
	}
}
