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

public class RazorEye extends Enemy{
	
	private boolean suicide;
	
	public RazorEye(int xcord, int ycord) {
		super(xcord, ycord);
		lootTable = new LootTable(new LootElement(4,Item.Misc.opticCable(),1),new LootElement(10,new Gear(Gear.Type.BRONZE),1));
		behavior = Behavior.PURSUE;
		speed = 1 + Math.random() * 0.6;
		scale = 1;
		damage = 1;
		health = 2;
		maxHealth = 2;
		setHitBox();
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,3);
		game.addSprite(this);
		autoAnimate(0.1);
		rescale();
	}

	
	@Override
	public void rescale() {
		
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,8);
		
		img.setScaleX(Game.scaleX);
		img.setScaleY(Game.scaleY);
		
		hitBox.setWidth(width*Game.scaleX*0.45);
		hitBox.setHeight(height * Game.scaleY*0.3);
		
	} 
	
	@Override
	public ArrayList<Item> getDrops() {
		ArrayList<Item> lootedItems = new ArrayList<Item>();
		lootedItems.addAll(lootTable.lootItems(false));
		return lootedItems;
	}

	@Override
	public void onCollide(Sprite s) {
		if(s instanceof Player) {
			((Player)s).loseHealth(damage);
			suicide = true;
			onDeath();
			
		}
		if(s instanceof WeaponSprite && canDamageFromSprite(s)) {
			if(((WeaponSprite)s).attacking) {
				loseHealth(((WeaponSprite)s).weapon.getDamage());
				addDamageSource(s);
			}
		}
		
	}

	@Override
	public void onDeath() {
		game.removeSprite(this);
		if(!suicide)
			lootItems();
	}

}
