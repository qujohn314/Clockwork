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
	private boolean canAttack, idle;
	private int stepCount;
	
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
		stepCount = 0;
		idle = false;
		direction = 3;
		weapon = Weapon.Melee.ghostIron(this);

		setHitBox();
		
		setBaseSpriteSheet("Player.png",scale);
		generateFrameViewports(width*scale,4,4,4,4);
		img.setFocusTraversable(true);
		img.requestFocus();
		
		autoAnimate(0.12);
		
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
		Game.getGame().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event ->{
			if(canAttack) {
				if(!weapon.weaponSprite.attacking) {
					weapon.attack();
					canAttack = false;
				}
				
			}
		});
		Game.getGame().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_RELEASED, event ->{
			canAttack = true;
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
		double newY = (amt*-1) + y;
		if(!((newY*Game.scaleY >= (Game.getGame().getHeight()/2) - (height * img.getScaleY()))) &&
				!((newY*Game.scaleX <= -(Game.getGame().getHeight()/2) + (height * img.getScaleY()))))
		y += amt*-1;
	}
	
	public void moveX(double amt) {
		double newX = amt+x;
		if(!((newX*Game.scaleX >= (Game.getGame().getWidth()/2) - (width*img.getScaleX()))) &&
				!((newX*Game.scaleX <= -(Game.getGame().getWidth()/2) + (width*img.getScaleX()))))
			x += amt;
		
	}
	
	
	
	@Override
	public void rescale() {
		
		setBaseSpriteSheet("Player.png",scale);
		generateFrameViewports(width*scale,4,4,4,4);
		animationCycle = animationSet[currentAnimationCycle];
		img.setImage(animationCycle[currentImgFrame]);


		
		img.setScaleX(Game.scaleX*0.85);
		img.setScaleY(Game.scaleY*0.85);
		
		hitBox.setWidth(20*Game.scaleX*1.6);
		hitBox.setHeight(30 * Game.scaleY*1.5);
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
				idle = false;
				stepCount++;
				direction = 2;
			
				if(isAutoAnimate)
					stopAutoAnimate();
				if(currentAnimationCycle != 3)
					setAnimationCycle(3);
				else if(stepCount >= 5) {
					nextFrame();
					stepCount = 0;
				}
				
			}
			else if(game.getKeyInputs().contains("W") && game.getKeyInputs().contains("D")) {
				
				moveY(newSpeedY / Math.sqrt(2));
				moveX(newSpeedX/ Math.sqrt(2));
				idle = false;
				stepCount++;
				direction = 3;
			
				if(isAutoAnimate)
					stopAutoAnimate();
				if(currentAnimationCycle != 1)
					setAnimationCycle(1);
				else if(stepCount >= 5) {
					nextFrame();
					stepCount = 0;
				}
			}
			else if(game.getKeyInputs().contains("S") && game.getKeyInputs().contains("A")) {
				
				moveY(-newSpeedY / Math.sqrt(2));
				moveX(-newSpeedX/ Math.sqrt(2));
				stepCount++;
				direction = 2;
			
				if(isAutoAnimate)
					stopAutoAnimate();
				if(currentAnimationCycle != 3)
					setAnimationCycle(3);
				else if(stepCount >= 5) {
					nextFrame();
					stepCount = 0;
				}
			}
			else if(game.getKeyInputs().contains("S") && game.getKeyInputs().contains("D")) {
				
				moveY(-newSpeedY / Math.sqrt(2));
				moveX(newSpeedX/ Math.sqrt(2));
				idle = false;
				stepCount++;
				direction = 3;
		
				if(isAutoAnimate)
					stopAutoAnimate();
				if(currentAnimationCycle != 1)
					setAnimationCycle(1);
				else if(stepCount >= 5) {
					nextFrame();
					stepCount = 0;
				}
			}
		}
			else {
				if(game.getKeyInputs().contains("W")) {
					idle = false;
					stepCount++;
					moveY(newSpeedY);
					if(isAutoAnimate)
						stopAutoAnimate();
					if(direction == 3 && currentAnimationCycle != 1)
						setAnimationCycle(1);
					else if(direction == 2  && currentAnimationCycle != 3)
						setAnimationCycle(3);
					if(stepCount >= 5) {
						nextFrame();
						stepCount = 0;
					}
					
				}
				if(game.getKeyInputs().contains("S")) {
					idle = false;
					stepCount++;
					moveY(-newSpeedY);
					if(isAutoAnimate)
						stopAutoAnimate();
					if(direction == 3 && currentAnimationCycle != 1)
						setAnimationCycle(1);
					else if(direction == 2  && currentAnimationCycle != 3)
						setAnimationCycle(3);
					if(stepCount >= 5) {
						nextFrame();
						stepCount = 0;
					}
				}
				if(game.getKeyInputs().contains("A")) {
					idle = false;
					stepCount++;
					direction = 2;
					moveX(-newSpeedX);
					if(isAutoAnimate)
						stopAutoAnimate();
					if(currentAnimationCycle != 3)
						setAnimationCycle(3);
					else if(stepCount >= 5) {
						nextFrame();
						stepCount = 0;
					}
					
				}
				if(game.getKeyInputs().contains("D")) { 
					idle = false;
					stepCount++;
					direction = 3;
					moveX(newSpeedX);
					if(isAutoAnimate)
						stopAutoAnimate();
					if(currentAnimationCycle != 1)
						setAnimationCycle(1);
					else if(stepCount >= 5) {
						nextFrame();
						stepCount = 0;
					}
					
				}
				if(!isMoving()) {
					idle = true;
					if(currentAnimationCycle % 2 == 1) {
						setAnimationCycle(currentAnimationCycle-1);
						autoAnimate(0.12);
					}
						
				}
			}
		
		if(direction == 3) {
			handX = (x+width-12)*Game.scaleX;
			handY = (y+height-35)*Game.scaleY;
		}else if(direction == 2) {
			handX = (x-width+12)*Game.scaleX;
			handY = (y+height-35)*Game.scaleY;
		}
		
		
		
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
		return new Rectangle2D(x* Game.scaleX,y * Game.scaleY,20*Game.scaleX * 1.6,30 * Game.scaleY * 1.5);
	}


	@Override
	public void onCollide(Sprite s) {
		
	}

	public boolean isMoving() {
		return ((game.getKeyInputs().contains("W") || game.getKeyInputs().contains("A") || game.getKeyInputs().contains("S")
				|| game.getKeyInputs().contains("D")));
	}
	
	
}
