package application.items;

public abstract class Item {
	protected int buyPrice;
	protected int sellPrice;
	protected String name;
	
	public Item(int buy, int sell, String n) {
		buyPrice = buy;
		sellPrice = sell;
		name = n;
	}
	
	public int getBuyPrice() {
		return buyPrice;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
}
