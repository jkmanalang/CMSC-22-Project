package tankbrickgame;

import javafx.scene.image.Image;

public abstract class PowerUps extends Sprite{

	private final static float POWERUPS_LIFETIME = 5;
	private final static float POWERUP_WIDTH = 20;

	protected final static float GOLDOIL_IMMORTALITY = 3;

	protected final static int OIL_POWERUP = 50;
	protected final static int AMMO_RECHARGE = 10;
	protected final static int POWERUPS_WIDTH = 10;
	protected final static int POWERUPTYPE_COUNT = 3;

	protected final static Image GOLDOIL_IMAGE = new Image("images/goldOil.png",PowerUps.POWERUP_WIDTH,PowerUps.POWERUP_WIDTH,false,false);
	protected final static Image OIL_IMAGE = new Image("images/oil.png",PowerUps.POWERUP_WIDTH,PowerUps.POWERUP_WIDTH,false,false);
	protected final static Image AMMO_IMAGE = new Image("images/ammo.png",PowerUps.POWERUP_WIDTH,PowerUps.POWERUP_WIDTH,false,false);

	protected float lifeSpan;

	private boolean collected;

	PowerUps(int x, int y){
		super(x, y);
		this.lifeSpan = POWERUPS_LIFETIME;
		this.collected = false;
	}

	abstract void checkCollision(Tank tank);

	// getters
	boolean isCollected(){
		return this.collected;
	}

	boolean isTimeOut(){
		if(lifeSpan <= 0) return true;
		return false;
	}

	// setters
	void collected(){
		this.collected = true;
	}

	void reduceLifeSpan(float x){
		this.lifeSpan -= x;
	}
}
