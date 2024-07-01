import acsse.csc2a.fmb.gui.FireworkDisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Mr J Orfao
 * @version P05
 */
public class Main extends Application{
	//Scene for our Team to be placed on the Stage
	private FireworkDisplayPane pane = null;

	public static void main(String[] args) {
		//Launch the JavaFX Application
		launch(args);
	}
	//Implement the start method required to get the Stage for the JavaFX Application
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("HAM Team Viewer");
		//Create the TeamPane
		pane = new FireworkDisplayPane();
		//Set the Scene
		Scene scene = new Scene(pane);
		primaryStage.setWidth(400 + 15*50);
		primaryStage.setHeight(100 + 15*50);
		primaryStage.setScene(scene);
		//Open the Curtains
		primaryStage.show();
	}

}
