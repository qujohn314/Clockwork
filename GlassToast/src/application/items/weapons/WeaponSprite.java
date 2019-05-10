package application.items.weapons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Game;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.sprites.entities.Player;
import application.sprites.entities.enemies.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
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
	private boolean downSweep;
	private Rotate rotation;
	private int frameCount;
	
	public WeaponSprite(String pic,Weapon.WeaponType t,Entity e) {
		super(0, 0);
		type = t;
		width = 20;
		height = 30;
		imgPath = pic;
		entity = e;
		downSweep = true;
		img.setPreserveRatio(true);
		try {
			spriteSheet = new Image(new FileInputStream("src/res/pics/" + imgPath));
			img.setImage(spriteSheet);
		} catch (FileNotFoundException ex) {System.out.println("Error Loading Pic");}
		setHitBox();
		
		Game.getGame().addSprite(this);
		attacking = false;
		generateFrameViewports(10,7);
	
		rotation = new Rotate();
		rotation.setPivotX(x);
		rotation.setPivotY(y);

		
		attackAnimation = new Timeline();
		
		if(type == Weapon.WeaponType.SWORD) {
		attackAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.025), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				nextFrame();
				if(img.getRotate() >= 90)
					downSweep = false;
				
				if(!downSweep) {
					img.setRotate(img.getRotate()-15);
					hitBox.setRotate(hitBox.getRotate()-15);
					
				}
				else {
					img.setRotate(img.getRotate() + 15);
					hitBox.setRotate(hitBox.getRotate()+15);
				}
				
				if(img.getRotate() == 0) {
					downSweep = true;
					attacking = false;
					setAnimationFrame(0);
					System.out.println(attacking);
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
			attacking = true;
			System.out.println(attacking);
			attackAnimation.setCycleCount(12);
			attackAnimation.play();
		}
	}
	
	
	@Override 
	public void rescale(){
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/" + imgPath),32,32,true,false));
		} catch (FileNotFoundException e) {	}
		
		generateFrameViewports(32,7);
		
		img.setScaleX(Game.scaleX*0.5);
		img.setScaleY(Game.scaleY*0.5);
		
		hitBox.setWidth(width*Game.scaleX*0.7);
		hitBox.setHeight(height * Game.scaleY*1.2);
	}

	@Override
	public void renderHitBox(boolean b) {
		if(b) {
			if(!hitBox.isVisible())
				hitBox.setVisible(true);
			hitBox.setTranslateX(x);
			hitBox.setTranslateY(y);
		}else
			hitBox.setVisible(false);
	}
	
	@Override
	public void render() {
		x = entity.getHandX();
		y = entity.getHandY();
		
		img.setTranslateX(x);
		img.setTranslateY(y);
		renderHitBox(Game.getGame().showHitBoxes);
	}

	@Override
	public void onCollide(Sprite s) {
		if(entity instanceof Enemy) {
			
		}else if(entity instanceof Player) {
			
		}
	}

}
