package tankbrickgame;

public final class Ammo extends PowerUps{

	Ammo(int x, int y){
		super(x, y);

		this.loadImage(PowerUps.AMMO_IMAGE);
	}

	@Override
	void checkCollision(Tank tank) {

		// add bullet to the tank if it is collected/collides with tank
		if(this.collidesWith(tank)){
			tank.addBulletCount(PowerUps.AMMO_RECHARGE);
			tank.collectPowerUp(this);
		}
	}

}
