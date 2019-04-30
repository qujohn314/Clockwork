package application.items.consumables;

import java.util.function.Consumer;

import application.items.Item;
import application.sprites.entities.Player;

public class Consumable extends Item{
	
	private Consumer<Player> effect;
	
	
	public Consumable(int s,String n, String d,Consumer<Player> e) {
		super(s,n,d);
		effect = e;
	}
	
	public void use(Player p) {
		effect.accept(p);
	}
	
	@Override
	public int getSellPrice() {
		return (int)(price*0.65);
	}
	
	public static Consumable Glaze() {
		return new Consumable(150,"Rosé Sealant", "Repairs minors cracks. Restores 1 HP", p ->  {
			p.health = p.health + 1 > p.maxHealth ? p.maxHealth : p.health + 1;
		});
	}
}
