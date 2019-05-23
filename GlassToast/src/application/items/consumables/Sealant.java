package application.items.consumables;

import java.util.function.Consumer;

import application.items.Item;
import application.sprites.entities.Player;

public class Sealant extends Consumable{

	private static final int SPRITESHEET_ROW = 0,SPRITESHEET_COL = 0;
	
	
	public Sealant() {
		super(120, "Sealant", "Apply liberally to cracks and scrapes.\nRestores 1 HP", e -> {
			e.health = e.health + 1 > e.maxHealth ? e.maxHealth : e.health + 1;
		System.out.println(e.health);},SPRITESHEET_ROW,SPRITESHEET_COL);
		
		
	}

	@Override
	public Consumable createNewItemObject() {
		return new Sealant();
	}
	
	

}
