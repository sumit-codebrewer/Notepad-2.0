package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.Optional;

import colordialog.ColorDialogController;
import fontdialog.FontDialogController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleController {
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private TextArea mainTextArea;
	
	private Stage currentStage;
	
	private File currentFile;
	
	private static final FileChooser fileChooser=new FileChooser();
	
	static {
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home")));
		
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("Java", "*.java"),
				new FileChooser.ExtensionFilter("C", "*.c"),
				new FileChooser.ExtensionFilter("C++", "*.cpp"),
				new FileChooser.ExtensionFilter("All Files","*.*"));
		
	}
	
	public void initialize() {
		mainTextArea.setStyle("-fx-text-fill:black;");
		mainTextArea.setFont(Font.font("Times New Roman",16));
	}
	
	@FXML
	public void createSpace() {
		mainTextArea.clear();
		currentFile=null;
		
		if(currentFile==null) {
			currentStage=(Stage)mainPane.getScene().getWindow();
		}
		currentStage.setTitle("Notepad");
	}
	
	@FXML
	public void showOpenDialog() {
		fileChooser.setTitle("Notepad");
		File selectedFile=fileChooser.showOpenDialog(mainPane.getScene().getWindow());
		currentFile=selectedFile;
		if(currentFile!=null) {
			readFile(selectedFile);
		}
		
		if(currentStage==null) {
			currentStage=(Stage)mainPane.getScene().getWindow();
		}
		
		if(selectedFile!=null) {
			currentStage.setTitle(selectedFile.getName());
		}
	}
	
	@FXML
	public void saveFile() {
		if(currentFile!=null) {
			String location=currentFile.getAbsolutePath();
			writeInFile(location);
		}
		else {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.initOwner(mainPane.getScene().getWindow());
			alert.setTitle("Error!");
			alert.setHeaderText("No file selected!");
			alert.setContentText("Please choose Save option");
			alert.showAndWait();
			
		}
	}
	
	@FXML
	public void showSaveAsDialog() {
		fileChooser.setTitle("Save as");
		File savedFile=fileChooser.showSaveDialog(mainPane.getScene().getWindow());
		currentFile=savedFile;
		writeInFile(savedFile.getAbsolutePath());
		currentStage.setTitle(savedFile.getName());
	}
	
	@FXML
	public void handleExit() {
		Platform.exit();
	}
	
	@FXML
	public void handleWordWrap() {
		if(mainTextArea.isWrapText()) {
			mainTextArea.setWrapText(false);
		}
		else {
			mainTextArea.setWrapText(true);
		}
	}
	
	@FXML
	public void showFontDialog() {
		
		Dialog<ButtonType> dialog=new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Change Font");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fontdialog/fontDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        FontDialogController controller = new FontDialogController();

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            String currFont = controller.getFont();
            Font font = Font.font(currFont, 16);
            mainTextArea.setFont(font);
        }
	}
	
	@FXML
	public void showColorDialog() {
		
		Dialog<ButtonType> dialog=new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Change Color");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/colordialog/ColorDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        ColorDialogController controller = new ColorDialogController();

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        	String currentColor=controller.getColor();
        	mainTextArea.setStyle("-fx-text-fill: #"+currentColor.substring(2,8)+";");
        }
	}
	
	@FXML
	public void handleAbout() {
		Desktop desktop=Desktop.getDesktop();
		try {
			desktop.browse(new URL("https://github.com/sumit-codebrewer").toURI());
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.initOwner(mainPane.getScene().getWindow());
			//Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			alert.setTitle("About Creator");
			alert.setHeaderText("Notepad is developed by:");
			alert.setContentText("Sumit-Codebrewer");
			alert.showAndWait();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readFile(File selectedFile) {
		new Thread(()->{
			StringBuilder sb=new StringBuilder();
			try(BufferedReader br=new BufferedReader(new FileReader(selectedFile))){
				String s;
				while((s=br.readLine())!=null) {
					sb.append(s).append("\n");
				}
				mainTextArea.setText(sb.toString());
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}).start();
		
		
	}
	
	
	public void writeInFile(String location) {
		new Thread(()->{
			try(BufferedWriter bw=new BufferedWriter(new FileWriter(new File(location)))){
				String text=mainTextArea.getText();
				bw.write(text);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}).start();
		
	}
}
