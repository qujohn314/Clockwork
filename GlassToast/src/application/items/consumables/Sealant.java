package application.items.consumables;

import java.util.function.Consumer;

import application.items.Item;
import application.sprites.entities.Player;

public class Sealant extends Consumable{

	public Sealant() {
		super(120, "Sealant", "Apply liberally to cracks and scrapes.\nRestores 1 HP", e -> {
			e.health = e.health + 1 > e.maxHealth ? e.health : e.health + 1;
		});
		
	}

	@Override
	public Consumable createThisItem() {
		return new Sealant();
	}

	

}
