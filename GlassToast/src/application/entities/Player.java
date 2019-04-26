package application.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player extends Entity implements Serializable{

	private static final long serialVersionUID = -7625029116892596346L;
	
	public int level;
	public int health;
	public int maxHealth;
	public int speed;
	public int silver;
	
	public Player(int xcord,int ycord) {
		super(xcord,ycord);
		level = 1;
		health = 1;
		maxHealth = 1;
		x = 0;
		y = 0;
		silver = 0;
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/Player.png")));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
		
		
	}
	
	public ImageView getPlayerSprite() {
		return img;
	}
	
	public void moveY(int amt) {
		y += amt*-1;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	@Override
	public void render() {
		img.setTranslateX(x);
		img.setTranslateY(y);
	}
	
	

	
	
}
