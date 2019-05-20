package application;

import java.util.ArrayList;

import application.items.Item;

public class LootTable {
	
	private ArrayList<LootElement> lootTable;
	
	public LootTable(LootElement...loot) {
		lootTable = new ArrayList<LootElement>();
		for(LootElement e : loot)
			lootTable.add(e);
	}
	
	public ArrayList<Item> lootItems() {
		boolean pityItem = true;
		ArrayList<Item> loot = new ArrayList<Item>();
		for(LootElement e : lootTable) {
			double chance = (Math.random()*100)+1;
			if(chance <= e.getChance()) {
				for(int i = 0;i<e.getQuantity();i++)
					loot.add(e.getItem().createNewItemObject());
				pityItem = false;
			}
		}
		if(pityItem)
			if(indexOfLargestDropChance() != -1)
				loot.add(lootTable.get(indexOfLargestDropChance()).getItem());
		return loot;
	}
	
	private int indexOfLargestDropChance() {
		double dropChance = 0;
		int index = -1;
		for(int i = 0;i<lootTable.size();i++) {
			if(lootTable.get(i).getChance() > dropChance) {
				index = i;
				dropChance = lootTable.get(i).getChance();
			}
		}
		return index;
	}
	
	public static class LootElement {
		
		private double chance;
		private Item item;
		private int amt;
		
		public LootElement(double c,Item i,int a) {
			chance = c;
			item = i;
			amt = a;
		}
		
		public int getQuantity() {
			return amt;
		}
		
		public double getChance() {
			return chance;
		}
		
		public Item getItem() {
			return item.createNewItemObject();
		}
	}
}



