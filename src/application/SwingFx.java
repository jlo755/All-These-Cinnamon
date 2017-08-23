package application;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class SwingFx{

	@FXML private StackPane stackPane;

    public void begin(Stage stage) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("GraphStream.fxml"));
    	Scene scene = new Scene(root);
        final SwingNode swingNode = new SwingNode();
        stackPane = (StackPane) scene.lookup("#graphStackPane");
        createSwingContent(swingNode);
        stackPane.getChildren().add(swingNode);

        stage.setTitle("Swing in JavaFX");
        stage.setScene(scene);
        stage.show();
        }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(new JButton("Click me!"));
            }
        });
    }


}