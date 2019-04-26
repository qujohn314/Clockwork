package application.entities.enemies;

import java.util.ArrayList;

import application.entities.Entity;
import application.items.Item;

public class Enemy extends Entity{

	public Enemy(int xcord, int ycord) {
		super(xcord, ycord);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Item> getDrops(){
		return null;
		
	}
	
}
