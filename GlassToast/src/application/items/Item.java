package application.items;

public abstract class Item {
	protected int price;
	protected String name;
	
	public Item(int p, String n) {
		price = p;
		name = n;
	}
	
	public int getBuyPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSellPrice() {
		return (int)(price * 0.5);
	}
}
