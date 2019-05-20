package application.items.consumables;

import java.util.function.Consumer;

import application.items.Item;
import application.sprites.entities.Player;

public abstract class Consumable extends Item{
	
	private Consumer<Player> effect;
	
	
	public Consumable(int p,String n, String d,Consumer<Player> e,int spriteSheetRow,int spriteSheetCol) {
		super(p,n,d,spriteSheetRow,spriteSheetCol);
		effect = e;
	}
	
	public void use(Player p) {
		effect.accept(p);
	}
	
	@Override
	public int getSellPrice() {
		return (int)(price*0.65);
	}
	
	public abstract Consumable createThisItem();
	
	@Override
	public Item createNewItemObject() {
		return createThisItem();
	}
	
	
	
}
