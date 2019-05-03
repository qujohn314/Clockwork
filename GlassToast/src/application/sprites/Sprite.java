package application.sprites;



import application.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Sprite {
	protected double x;
	protected double y;
	protected ImageView img;
	protected Rectangle hitBox;
	protected double width,height;
	protected Game game;
	protected Rectangle2D[] animationCycle;
	protected Rectangle2D[][] animationSet;
	protected int currentAnimationCycle;
	protected int currentImgFrame,spriteBoxDim,spriteBoxScale;
	protected Timeline autoAnimation;
	protected Image spriteSheet;
	protected double spriteSheetSize;
	
	public Sprite(int xcord, int ycord, Game g) {
		animationCycle = new Rectangle2D[0];
		animationSet = new Rectangle2D[][] {animationCycle};
		currentImgFrame = 0;
		currentAnimationCycle = 0;
		x = xcord;
		y = -ycord;
		game = g;
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
	 * NOTE: All spritesheet imgs are to be spaced 4 pixels away from surrounding imgs
	 * 
	 * @param s - Each s[n] value is the number of frames for an animationCycle. Each 's' is a new separate animation cycle
	 * @param dim -Dimension of imgBox
	 */
	protected void generateFrameViewports(int dim,int scale,int...s) {
		
		int imgX = 0;
		int imgY = 0;
		dim = (int)(dim*scale);
		
		Rectangle2D[][] holder = new Rectangle2D[s.length][0];
		for(int r = 0;r<s.length;r++) {
			holder[r] = new Rectangle2D[s[r]];
			for(int i = 0;i<holder[r].length;i++) {
				holder[r][i] = new Rectangle2D(imgX,imgY,dim,dim);
				imgX += dim + 4*scale;
			}
			imgY += dim ;
		}
		animationSet = holder;
		animationCycle = animationSet[0];
		img.setViewport(animationCycle[0]);
	}
	
	protected void setAnimationFrame(int i) {
		if(i > animationCycle.length-1)
			return;
		img.setViewport(animationCycle[i]);
	}
	
	protected void setAnimationCycle(int i) {
		if(i > animationSet.length-1)
			return;
		currentAnimationCycle = i;
		animationCycle = animationSet[i];
	}
	
	protected void autoAnimate(double animateDelaySECONDS) {
		autoAnimation = new Timeline();
		autoAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animateDelaySECONDS), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				img.setViewport(animationCycle[currentImgFrame]);
				currentImgFrame++;
				if(currentImgFrame >= animationCycle.length)
					currentImgFrame = 0;
			}
			
		}));
		autoAnimation.setCycleCount(Timeline.INDEFINITE);
		autoAnimation.play();
	}
	
	public void stopAutoAnimate() {
		autoAnimation.stop();
	}
	
	protected abstract void setHitBox();
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D(x * Game.scaleX,y * Game.scaleY,width*Game.scaleX,height * Game.scaleY);
	}
	
	public Rectangle getFakeHitBox() {
		return hitBox;
	}
	
	public boolean getCollision(Sprite s) {
		return this.getHitBox().intersects(s.getHitBox());
	}
	
	public void renderHitBox(boolean b) {
		if(b) {
			if(!hitBox.isVisible())
				hitBox.setVisible(true);
			hitBox.setTranslateX(x*Game.scaleX);
			hitBox.setTranslateY(y*Game.scaleY);
		}else
			hitBox.setVisible(false);
	}
	
	

	
	public abstract void render();
	
	public abstract void onCollide(Sprite s);

}
