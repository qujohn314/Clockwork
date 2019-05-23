package application.items.consumables;

import java.util.function.Consumer;

import application.items.Item;
import application.sprites.entities.Player;

public class Consumable extends Item{
	
	private Consumer<Player> effect;
	private int spriteRow,spriteCol;
	
	public Consumable(int p,String n, String d,Consumer<Player> e,int spriteSheetRow,int spriteSheetCol) {
		super(p,n,d,spriteSheetRow,spriteSheetCol);
		effect = e;
		spriteRow = spriteSheetRow;
		spriteCol = spriteSheetCol;
	}
	
	public void use(Player p) {
		System.out.println("nani");
		effect.accept(p);
	}
	
	@Override
	public int getSellPrice() {
		return (int)(price*0.65);
	}
	
	public static Consumable energyCell() {
		return new Consumable(50, "Energy Cell", "Container holding liquid energy.\nRestores 20 Energy", p -> {
			p.batteryPower = p.batteryPower + 15 > p.batteryPowerMax ? p.batteryPowerMax : p.batteryPower + 20;
		},0,6);
	}
	
	public static Consumable galvanicCell() {
		return new Consumable(400, "Galvanic Cell", "Powerful fluids bubble inside.\nRestores 50 Energy", p -> {
			p.batteryPower = p.batteryPower + 50 > p.batteryPowerMax ? p.batteryPowerMax : p.batteryPower + 50;
		},0,7);
	}
	
	
	
	@Override
	public Consumable createNewItemObject() {
		return new Consumable(price,name,desc,effect,spriteRow,spriteCol);
	}
	
	
	
}
