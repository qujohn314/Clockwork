package application.sprites;

import application.Game;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Sprite {
	protected double x;
	protected double y;
	protected ImageView img;
	protected Rectangle hitBox;
	protected double width,height;
	protected Game game;
	
	
	
	public Sprite(int xcord, int ycord, Game g) {
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
