package application.entities;

import application.Game;
import application.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Sprite{
	public Entity(int xcord, int ycord,Game g) {
		super(xcord, ycord,g);
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);
	}
	
	@Override
	public void render() {
		img.setTranslateX(x);
		img.setTranslateY(y);
	}
}
