package application.items;

public class Gear extends Item{

	public enum Type{
		TITANIUM(10,"Titanium Gear","Large unbreakable gear with limitless potential.",0,4),
		STEEL(5,"Steel Gear","A Large trustworthy gear made of steel.",0,3),
		BRONZE(1,"Bronze Gear","A small gear made of bronze. Best for small jobs.",0,2);
		
		private int val;
		private String name;
		private String desc;
		private int spritesheetRow,spritesheetCol;
		
		private int getVal() {
			return val;
		}
		
		private String getName() {
			return name;
		}
		
		private String getDesc() {
			return desc;
		}
		
		private int getSpritesheetRow() {
			return spritesheetRow;
		}
		
		private int getSpritesheetCol() {
			return spritesheetCol;
		}
		
		private Type(int v,String n,String d,int sr,int sc) {
			val = v;
			name = n;
			desc = d;	
			spritesheetRow = sr;
			spritesheetCol = sc;
		}
	}
	
	private Type value;
	
	public Gear(Type v) {
		super(v.getVal(), v.getName(), v.getDesc(),v.getSpritesheetRow(),v.getSpritesheetCol());
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
