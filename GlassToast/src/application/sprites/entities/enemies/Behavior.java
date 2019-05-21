package application.sprites.entities.enemies;

import application.Game;
import application.sprites.entities.Player;

public enum Behavior {
	
	PURSUE,PASSIVE,STATIONARY,OBSERVANT;
	
	private double previousSlope;
	private double xCount,yCount;
	private int negX,negY;
	
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
			e.getFakeHitBox().setRotate(rotateAngle);
			
			
			double a = p.getX() - e.getX();
			double b = p.getY() - e.getY();
			double h = (int)Math.sqrt(Math.pow(a, 2)) + (int)Math.sqrt(Math.pow(b, 2));
			
			if(h>0) {
				double xMove = a/h*e.speed;	
				double yMove = b/h*e.speed;
			
				e.setX(e.getX() + xMove);
				e.setY(e.getY() + yMove);
			
			}
		}
		if(this == OBSERVANT) {
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
			e.getFakeHitBox().setRotate(rotateAngle);
		}
	}
}
