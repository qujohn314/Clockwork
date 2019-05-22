package application.sprites;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import application.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Sprite {
	protected double x,y;
	protected ImageView img;
	protected Rectangle hitBox;
	protected int width,height;
	protected Game game;
	protected Image[] animationCycle;
	protected Image[][] animationSet;
	protected int currentAnimationCycle,currentImgFrame;;
	protected Timeline autoAnimation;
	protected Image spriteSheet;
	protected int scale;
	protected boolean isAutoAnimate;

	
	public Sprite(double xcord,double ycord) {
		animationCycle = new Image[0];
		animationSet = new Image[][] {animationCycle};
		currentImgFrame = 0;
		currentAnimationCycle = 0;
		x = xcord;
		y = -ycord;
		game = Game.getGame();
		img = new ImageView();
		img.setPreserveRatio(true);

		
	}	
	
	public void rescale() {
		img.setScaleX(Game.scaleX);
		img.setScaleY(Game.scaleY);
		
		hitBox.setWidth(width*Game.scaleX);
		hitBox.setHeight(height * Game.scaleY);
	}
	
	public ImageView getImg() {
		return img;
	}
	
	/**
	 * 
	 * 
	 * @param s - Each s[n] value is the number of frames for an animationCycle. Each 's' is a new separate animation cycle
	 * @param dim -Dimension of a sprite box.
	 * 
	 */
	protected void generateFrameViewports(int dim,int...s) {
		Image[][] matrixHolder = new Image[s.length][0];
		Image[] holder;
		
		for(int r = 0;r<s.length;r++) {
			holder = new Image[s[r]];
			for(int c = 0;c<holder.length;c++) {
				holder[c] = new WritableImage(img.getImage().getPixelReader(),c*dim,r*dim,dim,dim);
			}
			matrixHolder[r] = holder;
		}
		
		animationSet = matrixHolder;
		animationCycle = animationSet[0];
		img.setImage(animationCycle[0]);
	}
	
	protected void setAnimationFrame(int i) {
		if(i > animationCycle.length-1)
			return;
		img.setImage(animationCycle[i]);
		currentImgFrame = i;
	}
	
	protected void nextFrame() {
		img.setImage(animationCycle[currentImgFrame]);
		currentImgFrame++;
		if(currentImgFrame >= animationCycle.length)
			currentImgFrame = 0;
	}
	
	protected void setAnimationCycle(int i) {
	
		if(i > animationSet.length-1)
			return;
		currentAnimationCycle = i;
		animationCycle = animationSet[i];
		img.setImage(animationCycle[0]);
		currentImgFrame = 0;
	}
	
	protected void autoAnimate(double animateDelaySECONDS) {
		autoAnimation = new Timeline();
		autoAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animateDelaySECONDS), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				img.setImage(animationCycle[currentImgFrame]);
				currentImgFrame++;
				if(currentImgFrame >= animationCycle.length)
					currentImgFrame = 0;
			}
			
		}));
		autoAnimation.setCycleCount(Timeline.INDEFINITE);
		autoAnimation.play();
		isAutoAnimate = true;
	}
	
	public void stopAutoAnimate() {
		autoAnimation.stop();
		isAutoAnimate = false;
	}
	
	protected abstract void setHitBox();
	
	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public Rectangle getFakeHitBox() {
		return hitBox;
	}
	
	public boolean getCollision(Sprite s) {
		return hitBox.intersects(hitBox.parentToLocal(s.getHitBox().getBoundsInParent()));
	}
	
	public void renderHitBox(boolean b) {
		hitBox.setTranslateX(x*Game.scaleX);
		hitBox.setTranslateY(y*Game.scaleY);
		if(b) {
			if(!hitBox.isVisible())
				hitBox.setVisible(true);
		}else
			hitBox.setVisible(false);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}

	protected void setBaseSpriteSheet(String n,int scl) {
		try {
			Image tempImg =  new Image(new FileInputStream("src/res/pics/" + n));
			spriteSheet = new Image(new FileInputStream("src/res/pics/"+n),scl * tempImg.getWidth(),scl*tempImg.getHeight(),true,false);
			img.setImage(spriteSheet);
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
	}
	
	public abstract void render();
	
	public abstract void onCollide(Sprite s);

}
