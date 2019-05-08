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
	
	public Weapon(Entity e,int p, String n, String d,double dmg,WeaponType t,String pic) {
		super(p, n, d);
		entity = e;
		weaponSprite = new WeaponSprite(pic,t,e);
	}

	
	public void attack() {
		weaponSprite.attack();
	}
	
	public static class Melee{
		public static Weapon ghostIron(Entity e) {
			return new Weapon(e,70,"Ghost Iron Blade","A short sword made from a rare metal.",3.5,WeaponType.SWORD,"GhostSteelSword.png");
		}
		
		
	}




	@Override
	public Item createNewItemObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
