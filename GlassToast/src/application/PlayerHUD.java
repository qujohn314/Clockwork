package application;

import application.sprites.Sprite;
import application.sprites.entities.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayerHUD extends StackPane{
	
	public PlayerBars energy,health;
	private Player player;
	private Rectangle barCell1,barCell2;
	
	public PlayerHUD(Player p){
		energy = new PlayerBars(-280,80,"energy");
		health = new PlayerBars(-260,80,"health");
		
		player = p;
		
		barCell1 = new Rectangle();
		barCell1.setFill(Color.DIMGREY);
		barCell1.setStroke(Color.BLACK);
		barCell1.setStrokeWidth(2.5);
		
		barCell2 = new Rectangle();
		barCell2.setFill(Color.DIMGREY);
		barCell2.setStroke(Color.BLACK);
		barCell2.setStrokeWidth(2.5);
		
		this.getChildren().add(barCell1);
		this.getChildren().add(barCell2);
		this.getChildren().add(energy.getImg());
		this.getChildren().add(health.getImg());
	}
	
	public void rescale() {
		energy.rescale();
		health.rescale();
		
		barCell1.setWidth(14*Game.scaleX);
		barCell2.setWidth(14*Game.scaleX);
		
		barCell1.setHeight(energy.origHeight*Game.scaleY+2);
		barCell2.setHeight(health.origHeight*Game.scaleY+2);

	}
	
	public void render() {
		energy.render();
		health.render();
		
		barCell1.setTranslateY(-80*Game.scaleY);
		barCell2.setTranslateY(-80*Game.scaleY);
		
		barCell1.setTranslateX(-280*Game.scaleX);
		barCell2.setTranslateX(-260*Game.scaleX);
		
	
	}
	
	private class PlayerBars extends Sprite{

		private String type;
		private double percent,diffHeight,barDifference,diffCount,animateHeightDiff;
		double origHeight;
		Timeline change;

		public PlayerBars(double xcord, double ycord,String t) {
			super(xcord, ycord);
			width = 64;
			height = 64;
			scale = 3;
			type = t;
			percent = 1;
			img.setPreserveRatio(false);
			setBaseSpriteSheet("PlayerBars.png",scale);
			generateFramesFromType(type);
			autoAnimate(0.1);
			origHeight = 120;
			change = new Timeline();
			
			change.getKeyFrames().add(new KeyFrame(Duration.seconds(0.001), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if(diffCount < barDifference) {
						double previousHeight = img.getFitHeight();
						
						
						img.setFitHeight(img.getFitHeight()-0.1);
						
						double newHeight = img.getFitHeight();
						animateHeightDiff = newHeight-previousHeight;
						diffCount+=0.1;
						
						img.setTranslateX(x * Game.scaleX);
						img.setTranslateY((y * Game.scaleY)-(animateHeightDiff/2));
						y = img.getTranslateY()/Game.scaleY;
					}
					else {
						change.stop();
						diffCount = 0;
					}
				}
				
			}));
		}

		public void generateFramesFromType(String n) {
			generateFrameViewports(width*scale,4,1);
			if(n.equals("energy"))
				setAnimationCycle(0);
			else
				setAnimationCycle(1);
			img.setPreserveRatio(false);
		}
		
		public void update() {
			if(!type.equals("energy")) {
				double previousHeight = img.getFitHeight();
				percent = player.health/(double)player.maxHealth;
				if(percent == 0)
					percent = 0.0001;
				decreaseBar(previousHeight-(120*Game.scaleY*percent));
			}else {
				double previousHeight = img.getFitHeight();
				percent = player.batteryPower/(double)player.batteryPowerMax;
				if(percent == 0)
					percent = 0.0001;
				img.setFitHeight(120*Game.scaleY*percent);
				double newHeight = img.getFitHeight();
				diffHeight = newHeight-previousHeight;
			}
		}
		
		@Override
		public void rescale() {
			setBaseSpriteSheet("PlayerBars.png",scale);
			generateFramesFromType(type);
			
			img.setFitHeight(120*Game.scaleY*percent);
			img.setFitWidth(30*Game.scaleX);
		} 
		
		public void render() {
			update();
			img.setTranslateX(x * Game.scaleX);
			img.setTranslateY((y * Game.scaleY)-(diffHeight/2));
			y = img.getTranslateY()/Game.scaleY;
			if(!img.isVisible())
					img.setVisible(true);

		}

		public void decreaseBar(double diff) {
			barDifference = diff;
			change.setCycleCount(Timeline.INDEFINITE);
			change.play();
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
