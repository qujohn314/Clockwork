package application.sprites.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.LootTable;
import application.LootTable.LootElement;
import application.items.Gear;
import application.items.Item;
import application.items.weapons.WeaponSprite;
import application.sprites.Sprite;
import application.sprites.entities.Player;
import application.util.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class EyeFactory extends Enemy{
	
	private Timeline spawnTimer,closeTimer;
	private double spawnRate;
	private int spawnCount;
	private boolean closed;
	
	public EyeFactory(int xcord, int ycord) {
		super(xcord, ycord);
		lootTable = new LootTable(new LootElement(70,new Gear(Gear.Type.STEEL),1));
		behavior = Behavior.STATIONARY;
		speed = 1;
		scale = 2;
		damage = 1;
		health = 20;
		spawnRate = (Math.random() * 3.5) + 1.5;
		spawnCount = ((int)Math.random()*15) + 10;
		
		setHitBox();
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,1,10,5);
		game.addSprite(this);
		setAnimationCycle(1);
		autoAnimate(0.1);
		
		spawnTimer = new Timeline();
		spawnTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(spawnRate), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new RazorEye((int)x,(int)-y);
			}
			
		}));
		
		spawnTimer.setCycleCount(spawnCount);
		spawnTimer.play();
		spawnTimer.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setAnimationCycle(2);
				closed = true;
				closeTimer.play();
				currentImgFrame = 0;
				stopAutoAnimate();
			}
			
		});
		
		closeTimer = new Timeline();
		closeTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				nextFrame();
			}
			
		}));
		
		closeTimer.setCycleCount(5);
		
	}

	
	@Override
	public void rescale() {
		
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,1,10,5);
		setAnimationCycle(currentAnimationCycle);
		setAnimationFrame(4);
		
		img.setScaleX(Game.scaleX*0.75);
		img.setScaleY(Game.scaleY*0.75);
		
		hitBox.setWidth(width*Game.scaleX*scale*0.75);
		hitBox.setHeight(height * Game.scaleY*scale*0.75);
		
	} 
	
	@Override
	public ArrayList<Item> getDrops() {
		ArrayList<Item> lootedItems = new ArrayList<Item>();
		lootedItems.addAll(lootTable.lootItems(false));
		return lootedItems;
	}

	@Override
	public void onCollide(Sprite s) {
		if(!closed) {
			if(s instanceof WeaponSprite && canDamageFromSprite(s)) {
				if(((WeaponSprite)s).attacking) {
					loseHealth(((WeaponSprite)s).weapon.getDamage());
					addDamageSource(s);
				}
			}
		}
	}

	@Override
	public void onDeath() {
		game.removeSprite(this);
		spawnTimer.stop();
		lootItems();
	}

}
