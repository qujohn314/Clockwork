package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Game.newGame();
			Scene scene = new Scene(Game.getGame(),1200,675);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Clockwork");
			primaryStage.getIcons().add(new Image(new FileInputStream("src/res/pics/icon.png")));
			//primaryStage.setFullScreenExitHint("Nice");
			//primaryStage.setFullScreen(true);
			
			

			primaryStage.setMaximized(true);
			Game.getGame().init(scene);
			Game.scaleX = (Game.getGame().width / 1200) * 2;
			Game.scaleY = (Game.getGame().height / 675) * 2;
			Game.getGame().rescale();
			//
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
