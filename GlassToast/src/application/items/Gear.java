package application.items;

public class Gear extends Item{

	public enum Type{
		TITANIUM(10,"Titanium Gear","Large unbreakable gear with limitless potential."),
		STEEL(5,"Steel Gear","A Large trustworthy gear made of steel."),
		BRONZE(1,"Bronze Gear","A small gear made of bronze. Best for small jobs.");
		
		private int val;
		private String name;
		private String desc;
		
		private int getVal() {
			return val;
		}
		
		private String getName() {
			return name;
		}
		
		private String getDesc() {
			return desc;
		}
		
		private Type(int v,String n,String d) {
			val = v;
			name = n;
			desc = d;
			
		}
	}
	
	private Type value;
	
	public Gear(Type v) {
		super(v.getVal(), v.getName(), v.getDesc());
		value = v;
	}
	
	@Override
	public int getSellPrice() {
		return price;
	}
	
	@Override
	public Item createNewItemObject() {
		return new Gear(value);
	}
	
	

}
