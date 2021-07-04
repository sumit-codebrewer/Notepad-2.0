package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			primaryStage.setTitle("Notepad 2.0");
			primaryStage.getIcons().add(new Image("Icons/logo.png"));
			primaryStage.setScene(new Scene(root));
			Screen screen=Screen.getPrimary();
			setUserAgentStylesheet(STYLESHEET_MODENA);
			Rectangle2D bounds=screen.getBounds();
		    primaryStage.setX(bounds.getMinX());
		    primaryStage.setY(bounds.getMinY());
		    primaryStage.setWidth(bounds.getWidth());
		    primaryStage.setHeight(bounds.getHeight());
		    primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
