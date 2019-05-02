package application.sprites.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.PriorityQueue;

import application.Game;
import application.Interactable;
import application.sprites.Chest;
import application.sprites.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity implements Serializable{

	private static final long serialVersionUID = -7625029116892596346L;
	
	public int level;
	public int health;
	public int maxHealth;
	public double speed;
	public int silver;
	public int xVel;
	public int yVel;
	private Game game;
	private PriorityQueue<Interactable> interactRequests;
	private boolean canInteract;
	
	public Player(int xcord,int ycord,Game g) {
		super(xcord,ycord,g);
		level = 1;
		health = 1;
		maxHealth = 1;
		x = 0;
		y = 0;
		silver = 0;
		speed = 3.2;
		game = g;
		width = 20;
		height = 30;
		interactRequests = new PriorityQueue<Interactable>();
		canInteract = true;
	
		img.setScaleX(1);
		img.setScaleY(1);
		
		
		setHitBox();
		
		game.addSprite(this);
		
		try {
		
			img.setImage(new Image(new FileInputStream("src/res/pics/Player.png")));
			
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
		img.setFocusTraversable(true);
		img.requestFocus();
		generateFrameViewports(1);
		
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
			if(event.getCode()  == KeyCode.SPACE) {
				if(canInteract) {
					if(!interactRequests.isEmpty())
						interactRequests.poll().interact();
					canInteract = false;
				}
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
			if(event.getCode()  == KeyCode.SPACE) {
				canInteract = true;
			}	
		});
		
	}
	
	public int frameRate() {
		return 0;
	}
	
	public void addInteractRequest(Interactable i) {
		if(!interactRequests.contains(i))
			interactRequests.add(i);
	}
	
	public void removeInteractRequest(Interactable i) {
		if(interactRequests.contains(i))
			interactRequests.remove(i);
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
				if(game.getKeyInputs().contains("W")) {
				
					moveY(newSpeedY);
				}
				if(game.getKeyInputs().contains("S")) {
				
					moveY(-newSpeedY);
				}
				if(game.getKeyInputs().contains("A")) {
				
					moveX(-newSpeedX);
				}
				if(game.getKeyInputs().contains("D")) { 
					
					moveX(newSpeedX);
				}
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
		}
		
	}


	
	
}
