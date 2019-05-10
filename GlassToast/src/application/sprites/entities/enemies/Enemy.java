package application.sprites.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.items.Item;
import application.sprites.entities.Entity;

public abstract class Enemy extends Entity{

	public Enemy(int xcord, int ycord) {
		super(xcord, ycord,0,0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract ArrayList<Item> getDrops();

	
	
}
