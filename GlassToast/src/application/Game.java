package application;

import java.util.ArrayList;
import java.util.HashSet;

import application.sprites.Chest;
import application.sprites.Sprite;
import application.sprites.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Game extends StackPane{
	private Player player;
	private Scene scene;
	private double width;
	private double height;
	private HashSet<String> keyInput;
	public GraphicsContext graphics;
	private Canvas canvas;
	public StackPane hitBoxes,interactables,textBoxes;
	private boolean showHitBoxes;
	public static double scaleX,scaleY;
	private ArrayList<Sprite> sprites;
	Chest chest;
	AnimationTimer renderer;
	private static final float timeStep = 0.0125f;
	private float accumulatedTime = 0,previousTime = 0;
	
	public void render() {	
		for(Sprite s:sprites) {
			
			for(int i = 0;i<sprites.size();i++) {
				if(sprites.get(i).equals(s))
					continue;
				if(s.getCollision(sprites.get(i))) {
					s.onCollide(sprites.get(i));
				}
			}
		}
	}

	public HashSet<String> getKeyInputs() {
		return keyInput;
	}
	
	public boolean isHitBox() {
		return showHitBoxes;
	}
	
	public void rescale() {
		for(Sprite s:sprites)
			s.rescale();
	}
	
	public void addSprite(Sprite s) {
		sprites.add(s);
		if(s instanceof Interactable)
			interactables.getChildren().add(s.getImg());
		else
			this.getChildren().add(s.getImg());
		
		hitBoxes.getChildren().add(s.getFakeHitBox());
	}
	
	public void removeSprite(Sprite s) {
		if(sprites.contains(s))
			sprites.remove(s);
		this.getChildren().remove(s.getImg());
	}
	
	public void init(Scene s) {
		
		showHitBoxes = false;
		scene = s;
		width = scene.getWidth();
		height = scene.getHeight();
		this.setWidth(width);
		this.setHeight(height);
		sprites = new ArrayList<Sprite>();
		keyInput = new HashSet<String>();
	
		
		canvas = new Canvas();
		graphics = canvas.getGraphicsContext2D();
		canvas.setHeight(height);
		canvas.setWidth(width);
		
		hitBoxes = new StackPane();
		hitBoxes.setPrefWidth(width);
		hitBoxes.setPrefHeight(height);
		
		interactables = new StackPane();
		interactables.setPrefWidth(width);
		interactables.setPrefHeight(height);
		
		textBoxes = new StackPane();
		textBoxes.setPrefWidth(width);
		textBoxes.setPrefHeight(height);
		
		this.getChildren().add(hitBoxes);
		this.getChildren().add(interactables);
		this.getChildren().add(textBoxes);

		player = new Player(0,0,this);
		 chest = new Chest(-30,20,this);
		Chest chest2 = new Chest(50,20,this);
		Chest chest3 = new Chest(130,20,this);
		
		
	
		
		this.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(event.getCode() == KeyCode.F1) {
				showHitBoxes = !showHitBoxes;
				System.out.println("toggleOn");
			}
		});
		
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			   @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				   width = (Double)newSceneWidth;
				   scaleX = (width / 1200) * 2;
				   rescale();
			    }
			});
			scene.heightProperty().addListener(new ChangeListener<Number>() {
			   @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				   height = (Double)newSceneHeight;
				   scaleY = (height / 675) * 2;
				   rescale();
			    }
			});
	
		
			renderer = new AnimationTimer() {
				@Override
				public void handle(long currentTime) {
					if(previousTime == 0) {
						previousTime = currentTime;
						return;
					}
					float secondsElapsed = (currentTime - previousTime) / 1e9f;
					float secondsElapsedCapped = Math.min(secondsElapsed, 0.0167f);
					accumulatedTime += secondsElapsedCapped;
					previousTime = currentTime;
					
					while(accumulatedTime >= timeStep) {
						render();
						accumulatedTime -= timeStep;
					}
					
					for(Sprite s:sprites) {
						s.render();	
						s.renderHitBox(showHitBoxes);
					}
					
					
				}
			};
			
			renderer.start();
		
		
	}
}
