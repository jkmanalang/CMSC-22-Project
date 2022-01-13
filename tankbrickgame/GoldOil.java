package tankbrickgame;

public final class GoldOil extends PowerUps{


	GoldOil(int x, int y){
		super(x, y);

		this.loadImage(PowerUps.GOLDOIL_IMAGE);
	}

	// if collided with tank, add immortality to the tank
	void checkCollision(Tank tank){

		// add 3 second immortality to tank if collected
		if(this.collidesWith(tank)){
			tank.setImmortality(PowerUps.GOLDOIL_IMMORTALITY);
			tank.collectPowerUp(this);
		}
	}
}
