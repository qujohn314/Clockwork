package application.sprites;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import application.Game;
import application.Interactable;
import application.LootTable;
import application.LootTable.LootElement;
import application.items.Gear;
import application.items.consumables.Sealant;
import application.sprites.entities.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Chest extends Sprite implements Interactable, Comparable<Interactable>{

	public ImageView openLabel;
	private boolean opened;
	private LootTable lootTable;
	
	
	public Chest(int xcord, int ycord,Game g) {
		super(xcord, ycord,g);
		width = 56;
		height = 56;
		opened = false;
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/Chest.png"),32,32,true,false));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Pic");}
		img.setScaleX(1);
		img.setScaleY(1);
		setHitBox();
		game.addSprite(this);
		openLabel = new ImageView();
		openLabel.setVisible(false);
		try {
			openLabel.setImage(new Image(new FileInputStream("src/res/pics/openLabel.png")));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
		game.textBoxes.getChildren().add(openLabel);
	
		lootTable = new LootTable(new LootElement(5,new Gear(Gear.Type.STEEL),1),new LootElement(15,new Sealant(),1),new LootElement(50,new Gear(Gear.Type.BRONZE),2));
		
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(x,y,width,height);
		hitBox.setFill(Color.RED);
	}
	
	
	
	@Override
	public void rescale() {
		img.setScaleX(Game.scaleX*.6);
		img.setScaleY(Game.scaleY*.6);
		
		hitBox.setWidth(width*Game.scaleX*.55);
		hitBox.setHeight(height * Game.scaleY*.55);
		
		
		openLabel.setScaleX(Game.scaleX*0.15);
		openLabel.setScaleY(Game.scaleY*0.15);
	}

	@Override
	public void render() {
		img.setTranslateX(x * Game.scaleX);
		img.setTranslateY(y * Game.scaleY);
		
		openLabel.setTranslateX((x) * Game.scaleX);
		openLabel.setTranslateY((y-20) * Game.scaleY);
		
	}
	@Override
	public Rectangle2D getHitBox() {
		return new Rectangle2D((x-5)* Game.scaleX,y * Game.scaleY,width*Game.scaleX * 0.55,height * Game.scaleY * 0.55);
	}

	@Override
	public void interact() {
		
		opened = true;
		openLabel.setVisible(false);
		System.out.println(Arrays.toString(lootTable.lootItems().toArray()));
		try {
			img.setImage(new Image(new FileInputStream("src/res/pics/ChestOpen.png"),32,32,true,false));
		} catch (FileNotFoundException e) {System.out.println("Error Loading Pic");}
	}
	
	@Override
	public boolean getCollision(Sprite s) {
		if(opened)
			return false;
		if (s instanceof Player && !this.getHitBox().intersects(s.getHitBox())) {
			openLabel.setVisible(false);
			((Player)s).removeInteractRequest(this);
		}

		return this.getHitBox().intersects(s.getHitBox());
	}

	@Override
	public void onCollide(Sprite s) {
		if(opened)
			return;
		if(s instanceof Player) {
			if(!openLabel.isVisible()) {
				openLabel.setVisible(true);
				((Player)s).addInteractRequest(this);
			}
		}
		
	}

	@Override
	public int compareTo(Interactable i) {
		if(this.getPriorityValue() < i.getPriorityValue())
			return -1;
		else if(this.getPriorityValue() == i.getPriorityValue())
			return 0;
		return 1;
	}

	@Override
	public int getPriorityValue() {
		return 3;
	}

}
