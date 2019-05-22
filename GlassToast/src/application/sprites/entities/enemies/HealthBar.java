package application.sprites.entities.enemies;

import application.Game;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar extends HBox{
	private double width,height;
	private Rectangle healthRect,missingRect;
	private Color currentHealth,missingHealth;
	private Enemy enemy;
	
	{	
		healthRect = new Rectangle();
		missingRect = new Rectangle();
		this.setSpacing(0);
		this.getChildren().add(healthRect);
		this.getChildren().add(missingRect);
		this.setStyle("-fx-alignment: center;");
		setVisible(false);
	}
	
	public HealthBar(Enemy e) {
		enemy = e;
		
		width = 0;
		height = 10;
		
		

		healthRect.setWidth(width);
		missingRect.setWidth(0);
		healthRect.setHeight(height);
		missingRect.setHeight(height);
		
		currentHealth = Color.DARKGREEN;
		missingHealth = Color.DARKRED;
		
		healthRect.setFill(currentHealth);
		missingRect.setFill(missingHealth);
		

		
	}
	
	public HealthBar(Enemy e,Color ch,Color mh) {
		enemy = e;
		
		width = 0;
		height = 10;
		currentHealth = ch;
		missingHealth = mh;
		
		healthRect.setWidth(0);
		missingRect.setWidth(0);
		healthRect.setHeight(0);
		missingRect.setHeight(0);
		
		currentHealth = Color.DARKGREEN;
		missingHealth = Color.DARKRED;
		
		healthRect.setFill(currentHealth);
		missingRect.setFill(missingHealth);
		

	}
	
	public void update() {
		width = (enemy.getImg().getImage().getWidth());
		if(enemy.health != enemy.maxHealth)
			setVisible(true);
		double percent = enemy.health/enemy.maxHealth;
		healthRect.setWidth(width * percent);
		missingRect.setWidth(width-healthRect.getWidth());
		
	}
	
	public void render() {
		
		setTranslateX(enemy.getX()*Game.scaleX);
		setTranslateY(enemy.getY()*Game.scaleY-enemy.getImg().getImage().getHeight());
		update();
	}
}
