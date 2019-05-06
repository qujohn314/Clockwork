package application.items.weapons;

import application.Game;
import application.items.Item;
import application.sprites.Sprite;
import application.sprites.entities.Entity;
import application.sprites.entities.Player;

public class Weapon extends Item{
	private double damage;
	private WeaponSprite weaponSprite;
	private Entity entity;
	
	public Weapon(Entity e,int p, String n, String d,double dmg,String pic) {
		super(p, n, d);
		entity = e;
		weaponSprite = new WeaponSprite(e.getX(),e.getY(),g);
	}

	

	
	public static class Melee{
		public static Weapon GhostIron(Entity e) {
			return new Weapon(g,70,"Ghost Iron Blade","A short sword made from a rare metal.",3.5,"GhostSteelSword.png");
		}
		
		
	}




	@Override
	public Item createNewItemObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
