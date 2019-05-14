package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class TileGraphics extends Canvas{
	private GraphicsContext graphics;
	private Image tileSheet;
	private Image[] tileSet;
	private int scale;
	
	public TileGraphics(){
		graphics = this.getGraphicsContext2D();
		changeTileSize(3);
		generateTileSheet(32*scale,1);
	
	}
	
	public void initRoom() {
		for(int i = 0;i<30;i++) {
				for(int ii = 0;ii<15;ii++) {
			graphics.drawImage(tileSet[0], 32*scale*i, 32*scale*ii);
			}
		}
	}
	
	protected void generateTileSheet(int dim,int...s) {
		int total = 0;
		int index = 0;
		
		for(Integer x : s)
			total += x;
		tileSet = new Image[total];
		
		for(int r = 0;r<s.length;r++) {
			for(int c = 0;c<s[r];c++) {
				tileSet[index] = new WritableImage(tileSheet.getPixelReader(),c*dim,r*dim,dim,dim);
				index++;
			}
		}
		
	}
	
	protected void changeTileSize(int scl) {
		try {
			scale = scl;
			Image tempImg =  new Image(new FileInputStream("src/res/pics/Tiles.png"));
			tileSheet = new Image(new FileInputStream("src/res/pics/Tiles.png"),scl * tempImg.getWidth(),scl*tempImg.getHeight(),true,false);
		} catch (FileNotFoundException e) {System.out.println("Error Loading Player");}
	}
}
