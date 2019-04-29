package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.entities.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Chest extends Sprite implements Interactable{

	public Chest(int xcord, int ycord,Game g) {
		super(xcord, ycord,g);
		width = 52;
		height = 52;
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/Chest.png")));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Pic");}
		img.setScaleX(1);
		img.setScaleY(1);
		setHitBox();
		game.addSprite(this);
	
		
	}

	@Override
	protected void setHitBox() {

		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);
		
	}
	
	@Override
	public void rescale() {
		img.setScaleX(Game.scaleX*0.5);
		img.setScaleY(Game.scaleY*0.5);
		
		hitBox.setWidth(width*Game.scaleX*0.5);
		hitBox.setHeight(height * Game.scaleY*0.5);
	}

	@Override
	public void render() {
		img.setTranslateX(x * Game.scaleX);
		img.setTranslateY(y * Game.scaleY);
		
	}
	@Override
	public Rectangle2D getHitBox() {
		return new Rectangle2D(x * Game.scaleX,y * Game.scaleY,width*Game.scaleX * 0.5,height * Game.scaleY * 0.5);
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide(Sprite s) {
		if(s instanceof Player) {
			System.out.println("Found Player!");
		}
		
	}

}
