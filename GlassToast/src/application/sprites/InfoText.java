package application.sprites;

import application.Game;
import application.sprites.entities.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InfoText extends Text{
	String text;
	Entity parentEntity;
	Timeline textAnimation;
	double boostedY;
	private static int itemsAdded = 0;
	private int itemInLine;
	
	public InfoText(String text,Entity s) {
		Game.getGame().getChildren().add(this);
		setText(text);
		setOpacity(1);
		itemInLine = itemsAdded;
		this.setFont(new Font("Britannic Bold", 20));
		parentEntity = s;
		parentEntity.infoTexts.add(this);
		textAnimation = new Timeline();
		textAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setOpacity(getOpacity()-0.035);
				boostedY-=0.5;
			}
		}));
		textAnimation.setCycleCount(40);
		animate();
	}
	
	private void removeThis() {
		Game.getGame().removeText(this);
	}
	
	public void animate() {
		textAnimation.play();
		itemsAdded++;
		textAnimation.setOnFinished(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				removeThis();
				itemsAdded--;
			}
		});
	}
	public void render() {
		
		setTranslateX(parentEntity.getX() * Game.scaleX);
		setTranslateY((parentEntity.getY() * Game.scaleY)-(parentEntity.height*2)+boostedY-(itemInLine*20));
	}
}
