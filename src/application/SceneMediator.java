package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneMediator {
	public void changeScene(Stage stage, String fxml, String titleOfScene){
    	/*
    	 * This method sets the scene to the stage by loading the appropriate fxml file. It also
    	 * sets the title of this stage to match the new FXML file.
    	 */
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
	        stage.setTitle(titleOfScene);
	        Scene scene = new Scene(root);
	        //setSceneStyle(scene);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
