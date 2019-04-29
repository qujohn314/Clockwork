package application.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.entities.Entity;
import application.items.Item;

public abstract class Enemy extends Entity{

	public Enemy(int xcord, int ycord,Game g) {
		super(xcord, ycord,g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract ArrayList<Item> getDrops();

	
	
}
