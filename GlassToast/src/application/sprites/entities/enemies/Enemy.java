package application.sprites.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.LootTable;
import application.LootTable.LootElement;
import application.items.Gear;
import application.items.Item;
import application.sprites.Sprite;
import application.sprites.entities.Entity;

public abstract class Enemy extends Entity{

	protected LootTable lootTable;
	protected double health;
	protected double damage;
	protected Behavior behavior;
	protected int speed;
	
	public Enemy(int xcord, int ycord) {
		super(xcord, ycord,0,0);
		width = 32;
		height = 32;
		img.setPreserveRatio(true);
	}

	
	public abstract ArrayList<Item> getDrops();

	public abstract void onDeath();
	
	
	
}
