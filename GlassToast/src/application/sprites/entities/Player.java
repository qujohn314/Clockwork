package application.sprites.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

import application.Game;
import application.Interactable;
import application.items.Item;
import application.items.weapons.Weapon;
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
	private PriorityQueue<Interactable> interactRequests;
	private ArrayList<Item> inventory;
	private Weapon weapon;
	private boolean canAttack;
	
	private boolean canInteract;
	
	public Player(int xcord,int ycord) {
		super(xcord,ycord,0,0);
		level = 1;
		health = 1;
		maxHealth = 1;
		canAttack = true;
		x = 0;
		y = 0;
		silver = 0;
		speed = 2.5;
		width = 32;
		height = 32;
		interactRequests = new PriorityQueue<Interactable>();
		canInteract = true;
		img.setPreserveRatio(true);
		scale = 2;
		
		weapon = Weapon.Melee.ghostIron(this);

		setHitBox();
		
		setBaseSpriteSheet("Player.png",scale);
		generateFrameViewports(width*scale,2);
		autoAnimate(0.07);
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
			if(event.getCode()  == KeyCode.SPACE) {
				if(canInteract) {
					if(!interactRequests.isEmpty())
						interactRequests.poll().interact();
					canInteract = false;
				}
				if(canAttack) {
					if(!weapon.weaponSprite.attacking) {
						weapon.attack();
						canAttack = false;
					}
					
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
				
					canAttack = true;
			}	
		});
		img.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event ->{
			
		});
		img.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_RELEASED, event ->{
			
		});
		game.addSprite(this);
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
	
	
	
	@Override
	public void rescale() {
		
		setBaseSpriteSheet("Player.png",scale);
		generateFrameViewports(width*scale,2);
		
		
		img.setScaleX(Game.scaleX*0.85);
		img.setScaleY(Game.scaleY*0.85);
		
		hitBox.setWidth(width*Game.scaleX*1.6);
		hitBox.setHeight(height * Game.scaleY*1.5);
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
		
		
		handX = (x+width-12)*Game.scaleX;
		handY = (y+height-35)*Game.scaleY;
		
		img.setTranslateX(x * Game.scaleX);
		img.setTranslateY(y * Game.scaleY);
		
		weapon.weaponSprite.render();
		
	//	System.out.println("X:"+x + " Y:" + y);
		
	}
	
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);

	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D(x* Game.scaleX,y * Game.scaleY,width*Game.scaleX * 1.6,height * Game.scaleY * 1.5);
	}


	@Override
	public void onCollide(Sprite s) {
		
	}


	
	
}
