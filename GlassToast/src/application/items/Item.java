package application.items;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Game;
import application.Interactable;
import application.sprites.Chest;
import application.sprites.InfoText;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.sprites.entities.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Item {
	protected int price;
	protected String name;
	protected String desc;
	protected ItemSprite itemSprite;
	private static Image[][] staticAnimationSet;
	private static Image itemSpriteSheet;

	
	public Item(int p, String n,String d,int spriteRow,int spriteCol) {
		price = p;
		name = n;
		desc = d;
		itemSprite = new ItemSprite(0,0,spriteRow,spriteCol,this);
	}
	
	
	public static void initFrameViewports(int dim,int sc,int...s) {
		Image[][] matrixHolder = new Image[s.length][0];
		Image[] holder;
		
		try {
			Image tempImg =  new Image(new FileInputStream("src/res/pics/ItemSheet.png"));
			itemSpriteSheet = new Image(new FileInputStream("src/res/pics/ItemSheet.png"),sc * tempImg.getWidth(),sc*tempImg.getHeight(),true,false);
		} catch (FileNotFoundException e) {}
		
		for(int r = 0;r<s.length;r++) {
			holder = new Image[s[r]];
			for(int c = 0;c<holder.length;c++) {
				holder[c] = new WritableImage(itemSpriteSheet.getPixelReader(),c*dim,r*dim,dim,dim);
			}
			matrixHolder[r] = holder;
		}
		
		staticAnimationSet = matrixHolder;

	}
	
	public abstract Item createNewItemObject();
	
	public String getDesc() {
		return desc;
	}
	
	public int getBuyPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSellPrice() {
		return (int)(price * 0.5);
	}
	
	public String toString() {
		return name;
	}
	
	public  Sprite getSprite() {
		return itemSprite;
	}
	
	public void dropItem(Sprite s) {
		itemSprite.addItemToWorld(s.getX(),s.getY(),s);
	}
		
	
	public class ItemSprite extends Sprite{
		
		private Item item;
		private boolean canPickUp,idleUp;
		private Timeline dropAnimation,idleAnimation;
		private int popOutMultiplier;
		private int oddityFactor;
		private double idleLevel = 0;
		
		public ItemSprite(double xcord, double ycord,int spriteRow,int spriteCol,Item i) {
			super(xcord, ycord);
			width = 32;
			height = 32;
			scale = 1;
			item = i;
			
			canPickUp = false;
			oddityFactor = (int)(Math.random() * 60)+40;
			popOutMultiplier = 50 < (int)(Math.random() * 100) ? 1 : -1;
			idleUp = true;
			dropAnimation = new Timeline();
			idleAnimation = new Timeline();
			
			dropAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0025), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					
					x = oddityFactor < (int)(Math.random() * 100) ? x+(1*popOutMultiplier) : x;
					y = 50 < (int)(Math.random() * 100) ? y+1 : y;
					
					
				}
			}));
			idleAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if(idleUp) {
						if(idleLevel < 3){
							y+=0.25;
							idleLevel+=0.25;
						}
						else 
							idleUp = false;
					}else {
						if(idleLevel > 0){
							y-=0.25;
							idleLevel-=0.25;
						}
						else 
							idleUp = true;
					}
				}
			}));
			setHitBox();
			img.setImage(staticAnimationSet[spriteRow][spriteCol]);
			rescale();
			hitBox.setVisible(false);
			img.setVisible(false);
		}

	
		public void addItemToWorld(double originX,double originY,Sprite s) {
			x = originX;
			y = originY;
			Game.getGame().addSprite(this);
			dropAnimation.setCycleCount(50);
			idleAnimation.setCycleCount(Timeline.INDEFINITE);
			dropAnimation.play();
			dropAnimation.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					canPickUp = true;
					idleAnimation.play();
				}
			});
		}
		
		@Override
		public void rescale() {
			img.setScaleX(Game.scaleX * 0.6);
			img.setScaleY(Game.scaleY* 0.6);
			
			hitBox.setWidth(width*Game.scaleX*0.7);
			hitBox.setHeight(height * Game.scaleY*0.7);
		}
		
		@Override
		protected void setHitBox() {
			hitBox = new Rectangle(x,y,width,height);
			hitBox.setFill(Color.MEDIUMPURPLE);
		}
		
		@Override
		public Rectangle getHitBox() {
			return hitBox;
		}

		@Override
		public void render() {
			img.setTranslateX(x * Game.scaleX);
			img.setTranslateY(y * Game.scaleY);
			if(!img.isVisible())
				img.setVisible(true);
		}

		@Override
		public void onCollide(Sprite s) {
			if(canPickUp && s instanceof Player) {
				if(item instanceof Gear) {
					((Player)s).addToInventory(item);
					Game.getGame().removeSprite(this);
					idleAnimation.stop();
					Game.getGame().addText(new InfoText("+1 " + item.getName(),(Entity)s));
				}else if(((Player)s).addToInventory(item)){
					Game.getGame().removeSprite(this);
					idleAnimation.stop();
					Game.getGame().addText(new InfoText("+1 " + item.getName(),(Entity)s));
				}
			}
		}

		
		
	}
	public static class Misc extends Item{
		int sr,sc;
		public Misc(int p, String n, String d, int spriteRow, int spriteCol) {
			super(p, n, d, spriteRow, spriteCol);
			sr = spriteRow;
			sc = spriteCol;
		}

		public static Misc opticCable() {
			return new Misc(2,"Optic Cable","Wiring used for optical/awareness augments",0,5);
		}

		@Override
		public Item createNewItemObject() {
			return new Misc(price,name,desc,sr,sc);
		}
	}
}
