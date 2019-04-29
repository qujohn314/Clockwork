package application.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import application.Chest;
import application.Game;
import application.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity implements Serializable{

	private static final long serialVersionUID = -7625029116892596346L;
	
	public int level;
	public int health;
	public int maxHealth;
	public int speed;
	public int silver;
	public int xVel;
	public int yVel;
	private Game game;
	
	public Player(int xcord,int ycord,Game g) {
		super(xcord,ycord,g);
		level = 1;
		health = 1;
		maxHealth = 1;
		x = 0;
		y = 0;
		silver = 0;
		speed = 3;
		game = g;
		width = 20;
		height = 30;
		
		img.setScaleX(1);
		img.setScaleY(1);
		setHitBox();
		
		game.addSprite(this);
		
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/Player.png")));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
		img.setFocusTraversable(true);
		img.requestFocus();
		img.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(event.getCode()  == KeyCode.W) {
				game.getKeyInputs().add("W");
			}
			if(event.getCode()  == KeyCode.S) {
				game.getKeyInputs().add("S");
			}
			if(event.getCode()  == KeyCode.A) {
				game.getKeyInputs().add("A");
			}
			if(event.getCode()  == KeyCode.D) {
				game.getKeyInputs().add("D");
			}
		});
		img.addEventFilter(javafx.scene.input.KeyEvent.KEY_RELEASED, event ->{
			if(event.getCode()  == KeyCode.W) {
				game.getKeyInputs().remove("W");
			}
			if(event.getCode()  == KeyCode.S) {
				game.getKeyInputs().remove("S");
			}
			if(event.getCode()  == KeyCode.A) {
				game.getKeyInputs().remove("A");
			}
			if(event.getCode()  == KeyCode.D) {
				game.getKeyInputs().remove("D");
			}
		});
		
		
	}
	
	
	public void moveY(double amt) {
		y += amt*-1;
	}
	
	public void moveX(double amt) {
		x += amt;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	@Override
	public void render() {
		double newSpeedY = speed;
		double newSpeedX = speed;
		
		
		if(Game.scaleX > Game.scaleY) {
			newSpeedY *= Game.scaleX/Game.scaleY;
		}else {
			newSpeedX *= Game.scaleY/Game.scaleX;
		}
		
		
		
		
		if(game.getKeyInputs().size() == 2) {
			if(game.getKeyInputs().contains("W") && game.getKeyInputs().contains("A")) {
				moveY(newSpeedY / Math.sqrt(2));
				moveX(-newSpeedX/ Math.sqrt(2));
			}
			else if(game.getKeyInputs().contains("W") && game.getKeyInputs().contains("D")) {
				moveY(newSpeedY / Math.sqrt(2));
				moveX(newSpeedX/ Math.sqrt(2));
			}
			else if(game.getKeyInputs().contains("S") && game.getKeyInputs().contains("A")) {
				moveY(-newSpeedY / Math.sqrt(2));
				moveX(-newSpeedX/ Math.sqrt(2));
			}
			else if(game.getKeyInputs().contains("S") && game.getKeyInputs().contains("D")) {
				moveY(-newSpeedY / Math.sqrt(2));
				moveX(newSpeedX/ Math.sqrt(2));
			}
		}
			else {
				if(game.getKeyInputs().contains("W"))
					moveY(newSpeedY);
				if(game.getKeyInputs().contains("S")) 
					moveY(-newSpeedY);
				if(game.getKeyInputs().contains("A")) 
					moveX(-newSpeedX);
				if(game.getKeyInputs().contains("D")) 
					moveX(newSpeedX);
			}
		
		img.setTranslateX(x * Game.scaleX);
		img.setTranslateY(y * Game.scaleY);
		
		
	//	System.out.println("X:"+x + " Y:" + y);
		
	}
	
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);

	}


	@Override
	public void onCollide(Sprite s) {
		if(s instanceof Chest) {
			System.out.println("Found Chest!");
		}
		
	}


	
	
}
