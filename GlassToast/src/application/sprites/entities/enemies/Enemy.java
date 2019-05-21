package application.sprites.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.LootTable;
import application.items.Item;
import application.items.weapons.WeaponSprite;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.util.Pair;

public abstract class Enemy extends Entity{

	protected LootTable lootTable;
	protected double damage;
	protected Behavior behavior;
	protected boolean dead;
	public double health,maxHealth;;
	public ArrayList<Sprite> damageSource;
	
	public Enemy(int xcord, int ycord) {
		super(xcord, ycord,0,0);
		damageSource = new ArrayList<Sprite>();
		width = 32;
		height = 32;
		img.setPreserveRatio(true);
	}

	public void loseHealth(double amt) {
		health -= amt;
	}
	
	public abstract ArrayList<Item> getDrops();

	public void lootItems() {
		for(Item i : getDrops())
			i.dropItem(this);
	}
	
	public boolean canDamageFromSprite(Sprite s) {
		if(!damageSource.contains(s)) 
			return true;
		else if(s instanceof WeaponSprite) {
			if(!((WeaponSprite)s).attacking) {
				damageSource.remove(s);
			}
		}
		
		return false;
		
	}
	
	protected void addDamageSource(Sprite s) {
		damageSource.add(s);
	}
	
	@Override
	public void render() {
		if(health <= 0) {
			dead = true;
			onDeath();
		}
		if(!dead) {
			ArrayList<Sprite> currentSprites = new ArrayList<Sprite>();
			for(Sprite s : damageSource)
				currentSprites.add(s);
			
			for(Sprite s : currentSprites)
				if(!getCollision(s))
					damageSource.remove(s);
			
			behavior.getBehavior(this);
			img.setTranslateX(x*Game.scaleX);
			img.setTranslateY(y*Game.scaleY);
		}
	}
	
}
