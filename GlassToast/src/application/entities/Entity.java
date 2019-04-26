package application.entities;

import application.Sprite;
import javafx.scene.image.ImageView;

public abstract class Entity extends Sprite{
	public Entity(int xcord, int ycord) {
		super(xcord, ycord);
	}

	
	@Override
	public void render() {
		img.setTranslateX(x);
		img.setTranslateY(y);
	}
}
