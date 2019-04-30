package application.items;

public abstract class Item {
	protected int price;
	protected String name;
	protected String desc;
	
	
	public Item(int p, String n,String d) {
		price = p;
		name = n;
		desc = d;
	}
	
	public abstract Item createNewItemObject();
	
	public String getDesc() {
		return desc;
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
