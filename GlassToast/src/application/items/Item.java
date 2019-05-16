package application.items;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.sprites.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public abstract class Item {
	protected int price;
	protected String name;
	protected String desc;
	protected ItemSprite itemSprite;
	private static Image[][] staticAnimationSet;
	private static Image itemSpriteSheet;
	
	
	public Item(int p, String n,String d) {
		price = p;
		name = n;
		desc = d;
	}
	
	
	public static void initFrameViewports(int dim,int...s) {
		Image[][] matrixHolder = new Image[s.length][0];
		Image[] holder;
		
		try {
			Image tempImg =  new Image(new FileInputStream("src/res/pics/ItemSheet.png"));
			itemSpriteSheet = new Image(new FileInputStream("src/res/pics/ItemSheet.png"),dim * tempImg.getWidth(),dim*tempImg.getHeight(),true,false);
		} catch (FileNotFoundException e) {}
		
		for(int r = 0;r<s.length;r++) {
			holder = new Image[s[r]];
			for(int c = 0;c<holder.length;c++) {
				holder[c] = new WritableImage(itemSpriteSheet.getPixelReader(),c*dim,r*dim,dim,dim);
			}
			matrixHolder[r] = holder;
		}
		
		staticAnimationSet = matrixHolder;

	}
	
	public abstract Item createNewItemObject();
	
	public String getDesc() {
		return desc;
	}
	
	public int getBuyPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSellPrice() {
		return (int)(price * 0.5);
	}
	
	public String toString() {
		return name;
	}
	
	public  Sprite getSprite() {
		return itemSprite;
	}
	
	
	protected class ItemSprite extends Sprite{

		
		public ItemSprite(double xcord, double ycord) {
			super(xcord, ycord);
			width = 32;
			height = 32;
			scale = 1;
			setHitBox();
		}

	
		
		
		@Override
		protected void setHitBox() {
		
			
		}

		@Override
		public void render() {
			
			
		}

		@Override
		public void onCollide(Sprite s) {
		
			
		}
		
	}
}
