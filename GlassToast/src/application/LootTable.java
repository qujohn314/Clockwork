package application;

import java.util.ArrayList;

import application.items.Item;

public class LootTable {
	
	private ArrayList<LootElement> lootTable;
	
	public LootTable(LootElement...loot) {
		for(LootElement e : loot)
			lootTable.add(e);
	}
	
	public Item lootItem() {
		
	}
	
	public class LootElement {
		
		private double chance;
		private Item item;
		
		public LootElement(double c,Item i) {
			chance = c;
			item = i;
		}
		
		public double getChance() {
			return chance;
		}
		
		public Item getItem() {
			return item;
		}
	}
}



