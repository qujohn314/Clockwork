
package application.sprites;

import application.Game;
import application.sprites.entities.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Projectile extends Sprite{
	
	public enum PType{
		LASER;
		
		
		
	}
	
	
	private double damage;
	private Entity source;
	private PType type;
	
	public Projectile(double xcord, double ycord, Entity e, double dmg, PType t) {
		super(xcord, ycord);
		width = 32;
		height = 32;
		scale = 1;
		damage = dmg;
		source = e;
		type = t;
		
		setHitBox();
		setBaseSpriteSheet("projectiles.png",scale);
		generateFrameViewports(width*scale,5);
		game.addSprite(this);
	}
	
	
	

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);
		hitBox.setVisible(false);
	}

	@Override
	public void render() {
		img.setTranslateX(x * Game.scaleX);
		img.setTranslateY(y * Game.scaleY);
	}

	@Override
	public void onCollide(Sprite s) {
		if(s instanceof Entity){
			((Entity)s).loseHealth(damage);
		}
	}
	
}
