package application.sprites.entities.enemies;

import application.Game;
import application.sprites.entities.Player;

public enum Behavior {
	PURSUE,PASSIVE,STATIONARY;
	
	public void getBehavior(Enemy e){
		Player p = Game.getGame().player;
		
		if(this == PURSUE) {
			double rotateAngle = 0;
			
			if(p.getX() >= e.getX()) {
				rotateAngle = Math.atan2(p.getX()-e.getX(), p.getY()-e.getY());

				rotateAngle*=-1;
				rotateAngle = rotateAngle*180/Math.PI;
				rotateAngle+=180;
			}else {
				rotateAngle = Math.atan2(e.getX()-p.getX(), p.getY()-e.getY());
				rotateAngle = rotateAngle*180/Math.PI;
				rotateAngle+=180;
			}

			
			
			e.getImg().setRotate(rotateAngle);
			System.out.println(p.getX() + " " + e.getX()+ " " + rotateAngle);
		}
	}
}
