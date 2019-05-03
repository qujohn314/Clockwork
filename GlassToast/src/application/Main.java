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
			Game game = new Game();
			Scene scene = new Scene(game,1200,675);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Clockwork");
			primaryStage.getIcons().add(new Image(new FileInputStream("src/res/pics/icon.png")));
			//primaryStage.setFullScreenExitHint("Nice");
			//primaryStage.setFullScreen(true);
		
			game.init(scene);
			
			primaryStage.setMaximized(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
