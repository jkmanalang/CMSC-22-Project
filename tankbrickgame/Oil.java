package tankbrickgame;

public final class Oil extends PowerUps{

	Oil(int x, int y){
		super(x, y);

		this.loadImage(PowerUps.OIL_IMAGE);
	}

	// if collided with tank, add tank's strength
	void checkCollision(Tank tank){

		// add 50 strength to tank if collected
		if(this.collidesWith(tank)){
			tank.setStrength(PowerUps.OIL_POWERUP);
			tank.collectPowerUp(this);
		}
	}
}
