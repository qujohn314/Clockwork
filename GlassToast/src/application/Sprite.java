package application;

import javafx.scene.image.ImageView;

public abstract class Sprite {
	protected double x;
	protected double y;
	public abstract void render();
	protected ImageView img;
	
	public Sprite(int xcord, int ycord) {
		x = xcord;
		y = ycord;
		img = new ImageView();
		img.setPreserveRatio(true);
	}
	
	
}
