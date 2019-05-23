package application.items.weapons;


import application.Game;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.sprites.entities.Player;
import application.sprites.entities.enemies.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class WeaponSprite extends Sprite{

	Entity entity;
	String imgPath;
	Weapon.WeaponType type;
	Timeline attackAnimation;
	public boolean attacking;
	private boolean downSweep,flipped;
	private Rotate rotation;
	private int frameCount;
	private int rotationD;
	private int atkCount = 0;
	public Weapon weapon;
	
	public WeaponSprite(String pic,Weapon.WeaponType t,Entity e,Weapon w) {
		super(0, 0);
		type = t;
		scale = 2;
		width = 32;
		height = 32;
		weapon = w;
		imgPath = pic;
		entity = e;
		if(e.direction == 2) 
			rotationD = -1;
		if(e.direction == 3) 
			rotationD = 1;
		downSweep = true;
		img.setPreserveRatio(true);
		setBaseSpriteSheet(imgPath,scale);
		generateFrameViewports(width*scale,7);
		setHitBox();
		
	
		attacking = false;
	
		rotation = new Rotate();
		rotation.setPivotX(x);
		rotation.setPivotY(y);

		
		attackAnimation = new Timeline();
		
		if(type == Weapon.WeaponType.SWORD) {
		attackAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.03), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				atkCount++;
			//	System.out.println(img.getRotate());
				nextFrame();
				if(atkCount >=2 && downSweep)
					attacking = true;
				if(img.getRotate() >= (90) && rotationD == 1) 
					downSweep = false;
				else if(img.getRotate() <= (-90) && rotationD == -1)
					downSweep = false;
				
				if(!downSweep) {
					img.setRotate(img.getRotate()-(15*rotationD));
					hitBox.setRotate(hitBox.getRotate()-(15*rotationD));
					attacking = false;
				}
				else {
					img.setRotate(img.getRotate() + (15*rotationD));
					hitBox.setRotate(hitBox.getRotate()+(15*rotationD));
				}
				
				if(img.getRotate() == 0) {
					stopAttack();
				}
			}
			
		}));
		}
		

	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.GREEN);
	}
	
	protected void attack() {
		if(!attacking) {
			if(entity instanceof Player)
				((Player)entity).batteryPower-=weapon.energyUsage;
			attackAnimation.setCycleCount(Timeline.INDEFINITE);
			attackAnimation.play();
		}
	}
	

	
	protected void stopAttack() {
		flipped = true;
		atkCount = 0;
		attackAnimation.stop();
		img.setRotate(0);
		hitBox.setRotate(0);
		downSweep = true;
		attacking = false;
		setAnimationFrame(0);
	}
	
	@Override 
	public void rescale(){
		img.setScaleX(Game.scaleX*0.5);
		img.setScaleY(Game.scaleY*0.5);
		
		hitBox.setWidth(width*Game.scaleX*0.2);
		hitBox.setHeight(height * Game.scaleY*0.9);
	}

	@Override
	public void renderHitBox(boolean b) {
		if(b) {
			if(!hitBox.isVisible())
				hitBox.setVisible(true);
		}else
			hitBox.setVisible(false);
		hitBox.setTranslateX(x);
		hitBox.setTranslateY(y);
	}
	
	@Override
	public void render() {
		x = entity.getHandX();
		y = entity.getHandY();
		if((rotationD == -1 && entity.direction == 3) ||(rotationD == 1 && entity.direction == 2))
			stopAttack();
		
		if(entity.direction == 2) 
			rotationD = -1;
		if(entity.direction == 3) 
			rotationD = 1;
		img.setTranslateX(x);
		img.setTranslateY(y);
		renderHitBox(Game.getGame().showHitBoxes);
	}

	@Override
	public void onCollide(Sprite s) {
		if(entity instanceof Enemy) {
			
		}
	}

}
