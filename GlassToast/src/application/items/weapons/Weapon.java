package application.items.weapons;

import application.Game;
import application.items.Item;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.sprites.entities.Player;

public class Weapon extends Item{
	
	protected enum WeaponType{
		SWORD,STAFF;
	}
	
	private double damage;
	public WeaponSprite weaponSprite;
	private Entity entity;
	private WeaponType weaponType;
	private String picPath;
	private int spriteRow,spriteCol;
	
	public Weapon(Entity e,int p, String n, String d,double dmg,WeaponType t,String pic,int fspriteRow,int fspriteCol) {
		super(p, n, d,fspriteRow,fspriteCol);
		entity = e;
		weaponSprite = new WeaponSprite(pic,t,e,this);
		weaponType = t;
		picPath = pic;
		spriteRow = fspriteRow;
		spriteCol = fspriteCol;
		damage = dmg;
	}

	
	public void attack() {
		weaponSprite.attack();
	}
	
	public static class Melee{
		public static Weapon ghostIron(Entity e) {
			return new Weapon(e,70,"Ghost Iron Blade","A short sword made from a rare metal.",3.5,WeaponType.SWORD,"GhostSteelSword.png",0,1);
		}
		
		
	}

	public void equip() {
		Game.getGame().addSprite(weaponSprite);
	}


	public double getDamage() {
		return damage;
	}

	@Override
	public Item createNewItemObject() {
		return new Weapon(entity,price,name,desc,damage,weaponType,picPath,spriteRow,spriteCol);
	}
	
	
}
