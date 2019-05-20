package application.sprites.entities.enemies;

import java.util.ArrayList;

import application.Game;
import application.LootTable;
import application.LootTable.LootElement;
import application.items.Gear;
import application.items.Item;
import application.sprites.Sprite;

public class RazorEye extends Enemy{
	
	public RazorEye(int xcord, int ycord) {
		super(xcord, ycord);
		lootTable = new LootTable(new LootElement(25,Item.Misc.opticCable(),1),new LootElement(50,new Gear(Gear.Type.BRONZE),1));
		behavior = Behavior.PURSUE;
		speed = 5;
		scale = 1;
		setHitBox();
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,1);
		game.addSprite(this);
	}

	@Override
	public void render() {
		behavior.getBehavior(this);
	}
	
	@Override
	public void rescale() {
		
		setBaseSpriteSheet("RazorEye.png",scale);
		generateFrameViewports(width*scale,2);
		
		img.setScaleX(Game.scaleX);
		img.setScaleY(Game.scaleY);
		
		hitBox.setWidth(width*Game.scaleX*0.5);
		hitBox.setHeight(height * Game.scaleY*0.5);
		
	} 
	
	@Override
	public ArrayList<Item> getDrops() {
		ArrayList<Item> lootedItems = new ArrayList<Item>();
		lootedItems.addAll(lootTable.lootItems(false));
		return lootedItems;
	}

	@Override
	public void onCollide(Sprite s) {
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
