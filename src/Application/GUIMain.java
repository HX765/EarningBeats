package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMain extends Application {
	
/**
* @author      Chao Qiu <chaoqiu@seas.upenn.edu>
*/	
	
	/**
	 * This method will initiate the UI window
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/GUIView.fxml"));
			Scene scene = new Scene(root, 600, 800);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launch the program from here!
	 * @param args
	 */
	
	public static void main(String[] args) {
		launch(args);
	}

}