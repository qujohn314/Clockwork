package application;

import application.sprites.Sprite;
import application.sprites.entities.Player;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class PlayerHUD extends StackPane{
	
	public PlayerBars energy,health;
	private Player player;
	
	public PlayerHUD(Player p){
		energy = new PlayerBars(-280,80,"energy");
		health = new PlayerBars(-260,80,"health");
		
		this.getChildren().add(energy.getImg());
		this.getChildren().add(health.getImg());
		
		
		player = p;
	}
	
	public void rescale() {
		energy.rescale();
		health.rescale();
	}
	
	public void render() {
		energy.render();
		health.render();
	}
	
	private class PlayerBars extends Sprite{

		private String type;
		private double barHeight;
		private double percent;

		public PlayerBars(double xcord, double ycord,String t) {
			super(xcord, ycord);
			width = 64;
			height = 64;
			scale = 3;
			type = t;
			percent = 1;
			img.setPreserveRatio(true);
			setBaseSpriteSheet("PlayerBars.png",scale);
			generateFramesFromType(type);
			autoAnimate(0.1);
			
		}

		public void generateFramesFromType(String n) {
			generateFrameViewports(width*scale,4,1);
			if(n.equals("energy"))
				setAnimationCycle(0);
			else
				setAnimationCycle(1);
		}
		
		public void update() {
			barHeight = (getImg().getImage().getHeight());
			percent = player.health/(double)player.maxHealth;
			img.setScaleY(Game.scaleY*0.75*percent);
			
		}
		
		@Override
		public void rescale() {
			double previousHeight = getImg().getImage().getHeight();
			setBaseSpriteSheet("PlayerBars.png",scale);
			generateFramesFromType(type);
			
			img.setScaleX(Game.scaleX*0.15);
			img.setScaleY(Game.scaleY*0.75*percent);
		} 
		
		public void render() {
			update();
			img.setTranslateX(x * Game.scaleX);
			img.setTranslateY(y * Game.scaleY);
		}

		@Override
		protected void setHitBox() {
			return;
		}

		@Override
		public void onCollide(Sprite s) {
			return;
		}
		
	}
}
