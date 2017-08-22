package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class mainMenuController implements Initializable{
	@FXML Button chooseFileButton;
	@FXML Label fileInfoLabel;
	@FXML TextField processorCountField;
	@FXML Button startProcessingButton;

	private String _fileName;
	private String _filePath;
	private Integer _noOfProcessors;

	public void uploadFileAction(){
		/*
		 * This method handles the uploading of files. It will throw an error
		 * if the file does not follow the % format, or does not end in .dot.
		 */
		// credit to http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
		// for the file choosing code
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog((Stage)chooseFileButton.getScene().getWindow());
		if(file.getAbsolutePath().contains(".dot")){ // checks if the file contains the .dot extension
			_fileName=file.getName();
			_filePath=file.getAbsolutePath();
			fileInfoLabel.setText("File Name: "+_fileName);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid file!");
			alert.setContentText("Uploaded file must be a dot file.");
			alert.show();
		}

	}

	public void ProcessorCountAction() {
		String inputText = processorCountField.getText();
		_noOfProcessors = Integer.parseInt(inputText);
		//check for only integers and no spaces. if not. give an alert
	}

	public void StartProcessingAction() {
		ProcessorCountAction();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}








}
