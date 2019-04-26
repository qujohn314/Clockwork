package application;

import application.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
 import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class Game extends StackPane{
	
	private Scene scene;
	private double width;
	private double height;
	private Player player;
	private AnimationTimer renderer;
	
	
	public void render() {
		player.render();
	}
	
	public void init(Scene s) {
		scene = s;
		width = scene.getWidth();
		height = scene.getHeight();
		
		this.setFocused(true);
		player = new Player(0,0);
		
		this.getChildren().add(player.getPlayerSprite());
		this.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(event.getCode()  == KeyCode.SPACE) {
				player.moveY(5);
				System.out.println("Up");
			}
		});
		
		
		
		
		
		
		renderer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				render();
				System.out.println("Running");
			}
			
		};
		
		renderer.start();
		
	}
}
