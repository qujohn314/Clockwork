package application.sprites.entities;

import java.util.ArrayList;

import application.Game;
import application.sprites.InfoText;
import application.sprites.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Sprite{
	
	protected double handX;
	protected double handY;
	public int direction;
	public ArrayList<InfoText> infoTexts;
	
	public Entity(int xcord, int ycord,double hX, double hY) {
		super(xcord, ycord);
		handX = hX;
		infoTexts = new ArrayList<InfoText>();
		handY = hY;
	}

	public double getHandX() {
		return handX; 
	}
	
	public double getHandY() {
		return handY;
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
