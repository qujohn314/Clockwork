package application;

import java.util.ArrayList;
import java.util.HashSet;

import application.items.Item;
import application.items.Item.ItemSprite;
import application.items.weapons.WeaponSprite;
import application.sprites.Chest;
import application.sprites.InfoText;
import application.sprites.Sprite;
import application.sprites.entities.Player;
import application.sprites.entities.enemies.EyeFactory;
import application.sprites.entities.enemies.HealthBar;
import application.sprites.entities.enemies.RazorEye;
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
	private static Game game;
	public Player player;
	private Scene scene;
	protected double width;
	protected double height;
	private HashSet<String> keyInput;
	private TileGraphics canvas;
	public StackPane hitBoxes,interactables,textBoxes,itemBoxes,equippedWeapon,inanimateEntities,characters,backCharacters;
	public PlayerHUD HUD;
	public boolean showHitBoxes,gameOver;
	public static double scaleX,scaleY;
	private ArrayList<Sprite> sprites,removeSprites,addSprites;
	private AnimationTimer renderer;
	private static final float timeStep = 0.0125f;
	private float accumulatedTime = 0,previousTime = 0;
	private static boolean newGameMade = false;
	private boolean removeRequest = false;
	public boolean initRun = true;
	
	public void render() {	
		ArrayList<Sprite> currentSprites = new ArrayList<Sprite>();
		for(Sprite s : sprites)
			currentSprites.add(s);
		for(Sprite s:currentSprites) {

			for(int i = 0;i<currentSprites.size();i++) {
				if(currentSprites.get(i).equals(s))
					continue;
				if(s.getCollision(currentSprites.get(i))) {
					s.onCollide(currentSprites.get(i));
				}
			}
		}
		if(removeRequest) {
			sprites.removeAll(removeSprites);
			removeSprites.clear();
			removeRequest = false;
		}
		
	}
	
	public static Game getGame() {
		return game;
	}
	
	public static void newGame(){
		if(!newGameMade) {
			game = new Game();
			newGameMade = true;
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
		HUD.rescale();
	}
	
	public void addSprite(Sprite s) {
		sprites.add(s);
		if(s instanceof Interactable) {
			interactables.getChildren().add(s.getImg());
			hitBoxes.getChildren().add(s.getFakeHitBox());
		}
		
		else if(s instanceof WeaponSprite) {
			equippedWeapon.getChildren().add(s.getImg());
			itemBoxes.getChildren().add(s.getFakeHitBox());
		}
		else if(s instanceof ItemSprite) {
			inanimateEntities.getChildren().add(s.getImg());
			itemBoxes.getChildren().add(s.getFakeHitBox());
		}
		else {
			hitBoxes.getChildren().add(s.getFakeHitBox());
			if(!(s instanceof EyeFactory))
				characters.getChildren().add(s.getImg());
			else
				backCharacters.getChildren().add(s.getImg());
		}
	}
	
	public void addText(InfoText t) {
		textBoxes.getChildren().add(t);
	}
	
	public void removeText(InfoText t) {
		textBoxes.getChildren().remove(t);
	}
	
	public void addHealthBar(HealthBar b) {
		if(!textBoxes.getChildren().contains(b))
			textBoxes.getChildren().add(b);
		else
			System.out.println("Health Bar Error:Add");
	}
	
	public void removeHealthBar(HealthBar b) {
		if(textBoxes.getChildren().contains(b))
			textBoxes.getChildren().remove(b);
		else
			System.out.println("Health Bar Error:Remove");
	}
	
	public void removeSprite(Sprite s) {
		if(sprites.contains(s)) {
			removeSprites.add(s);
			removeRequest = true;
			
			if(s instanceof Interactable) {
				interactables.getChildren().remove(s.getImg());
				hitBoxes.getChildren().remove(s.getFakeHitBox());
			}
		
			else if(s instanceof WeaponSprite) {
				equippedWeapon.getChildren().remove(s.getImg());
				itemBoxes.getChildren().remove(s.getFakeHitBox());
			}
			else if(s instanceof ItemSprite) {
				inanimateEntities.getChildren().remove(s.getImg());
				itemBoxes.getChildren().remove(s.getFakeHitBox());
			}
			else {
				hitBoxes.getChildren().remove(s.getFakeHitBox());
				if(!(s instanceof EyeFactory))
					characters.getChildren().remove(s.getImg());
				else
					backCharacters.getChildren().remove(s.getImg());
			}
		}
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
	
		
		canvas = new TileGraphics();
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
		textBoxes.setStyle("-fx-alignment: center;");
		
		this.setStyle("-fx-alignment: center;");
		
		characters = new StackPane();
		characters.setPrefWidth(width);
		characters.setPrefHeight(height);
		
		equippedWeapon = new StackPane();
		equippedWeapon.setPrefWidth(width);
		equippedWeapon.setPrefHeight(height);
		
		itemBoxes = new StackPane();
		itemBoxes.setPrefWidth(width);
		itemBoxes.setPrefHeight(height);
		
		inanimateEntities = new StackPane();
		inanimateEntities.setPrefWidth(width);
		inanimateEntities.setPrefHeight(height);
		
		backCharacters = new StackPane();
		backCharacters.setPrefWidth(width);
		backCharacters.setPrefHeight(height);
		
		Item.initFrameViewports(32,1,10,10,10);
		player = new Player(0,0);
		
		HUD = new PlayerHUD(player);
		HUD.setPrefWidth(width);
		HUD.setPrefHeight(height);
		
		this.getChildren().add(canvas);
		this.getChildren().add(hitBoxes);
		this.getChildren().add(itemBoxes);
		this.getChildren().add(interactables);
		this.getChildren().add(backCharacters);
		this.getChildren().add(inanimateEntities);
		this.getChildren().add(textBoxes);
		
		this.getChildren().add(characters);
		this.getChildren().add(equippedWeapon);
		
		this.getChildren().add(HUD);

		canvas.initRoom();
		removeSprites = new ArrayList<Sprite>();
		addSprites = new ArrayList<Sprite>();
	
		
		this.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(event.getCode() == KeyCode.F1) {
				showHitBoxes = !showHitBoxes;
				System.out.println("toggleHitBoxesOn");
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
	
			
			
			new Chest(-30,20);
			new Chest(50,20);
			new Chest(130,20);
			new Chest(200,20);
			new Chest(-200,20);
			new Chest(-130,20);	
			
			//new EyeFactory(-150,-100);
			//new EyeFactory(150,-100);
			new EyeFactory(-150,100);
			new EyeFactory(150,100);
			
			
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
					
					if(!initRun) {
						while(accumulatedTime >= timeStep) {
							render();
							accumulatedTime -= timeStep;
						}
					}
					
					
					ArrayList<Sprite> currentSprites = new ArrayList<Sprite>();
					for(Sprite s : sprites)
						currentSprites.add(s);
					
					for(Sprite s:currentSprites) {
						if(!(s instanceof WeaponSprite)) {
							s.render();	
						s.renderHitBox(showHitBoxes);
						}
					}
					HUD.render();
					
						if(initRun) {
							initRun = false;
							for(Sprite s:currentSprites) {
								s.getImg().setVisible(true);
							}
						}
					}

			};
			
			renderer.start();
			
		
	}
}
