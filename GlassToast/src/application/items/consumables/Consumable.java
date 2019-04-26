package application.items.consumables;

import java.util.function.Consumer;

import application.entities.Player;
import application.items.Item;

public class Consumable extends Item{
	
	private Consumer<Player> effect;
	
	
	public Consumable(int s,String n, Consumer<Player> e) {
		super(s,n);
		effect = e;
	}
	
	public void use(Player p) {
		effect.accept(p);
	}
	
	@Override
	public int getSellPrice() {
		return (int)(price*0.65);
	}
	
	public static Consumable yeast() {
		return new Consumable(150,"Yeast Injection", p ->  {
			p.health = p.health + 1 > p.maxHealth ? p.maxHealth : p.health + 1;
		});
	}
}
