package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;

import colordialog.ColorDialogController;
import feedbackform.FeedbackController;
import findAndReplaceDialog.FindAndReplaceController;
import fontdialog.FontDialogController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import searchHandle.SearchController;
import sizedialog.SizeDialogController;

public class SampleController {

	@FXML
	private BorderPane mainPane;

	@FXML
	private TextArea mainTextArea;

	@FXML
	private ToggleButton webButton;

	private Stage currentStage;

	private File currentFile;

	private String currentFont = "Times New Roman", currentColor = "black";
	int currentSize = 16;

	private static final FileChooser fileChooser = new FileChooser();

	static {
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("Java", "*.java"), new FileChooser.ExtensionFilter("C", "*.c"),
				new FileChooser.ExtensionFilter("C++", "*.cpp"), new FileChooser.ExtensionFilter("All Files", "*.*"));

	}

	public void initialize() {
		mainTextArea.setStyle("-fx-text-fill:black;");
		mainTextArea.setFont(Font.font("Times New Roman", 16));
	}

	@FXML
	public void createSpace() {
		mainTextArea.clear();
		mainTextArea.setStyle("-fx-text-fill:black;");
		mainTextArea.setFont(Font.font("Times New Roman", 16));
		currentFile = null;

		if (currentFile == null) {
			currentStage = (Stage) mainPane.getScene().getWindow();
		}
		currentStage.setTitle("Notepad");
	}

	@FXML
	public void showOpenDialog() {
		fileChooser.setTitle("Notepad");
		File selectedFile = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
		currentFile = selectedFile;
		if (currentFile != null) {
			readFile(selectedFile);
		}

		if (currentStage == null) {
			currentStage = (Stage) mainPane.getScene().getWindow();
		}

		if (selectedFile != null) {
			currentStage.setTitle(selectedFile.getName());
		}
	}

	@FXML
	public void saveFile() {
		if (currentFile != null) {
			String location = currentFile.getAbsolutePath();
			writeInFile(location);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initOwner(mainPane.getScene().getWindow());
			alert.setTitle("Error!");
			alert.setHeaderText("No file selected!");
			alert.setContentText("Please choose Save as option");
			alert.showAndWait();

		}
	}

	@FXML
	public void showSaveAsDialog() {
		fileChooser.setTitle("Save as");
		File savedFile = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
		currentFile = savedFile;
		writeInFile(savedFile.getAbsolutePath());
		currentStage.setTitle(savedFile.getName());
	}

	@FXML
	public void handleExit() {
		Platform.exit();
	}

	@FXML
	public void handleZoomIn() {
		Font font = new Font(currentFont, 36);
		mainTextArea.setFont(font);
		mainPane.setEffect(new javafx.scene.effect.InnerShadow(8.7, Color.GRAY));
	}

	@FXML
	public void handleZoomOut() {
		Font font = new Font(currentFont, 16);
		mainTextArea.setFont(font);
		mainPane.setEffect(null);
	}

	@FXML
	public void handleSearch() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Search Box");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/searchHandle/SearchDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		SearchController controller = new SearchController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String url = controller.getUrl();
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URL(url).toURI());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.initOwner(mainPane.getScene().getWindow());
				alert.setTitle("Redirecting to the website...");
				alert.setHeaderText("Loading ⌛⌛⌛");
				alert.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	public void handleFindAndReplace() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		int index = -1;
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Find & Replace");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/findAndReplaceDialog/FindAndReplaceDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		FindAndReplaceController controller = new FindAndReplaceController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String findText = controller.getText();
			String mainText = mainTextArea.getText();
			index = mainText.indexOf(findText);
			if (index != -1) {
				String replaceText = controller.getReplaceText();
				mainText = mainText.replace(findText, replaceText);
				mainTextArea.setText(mainText);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.initOwner(mainPane.getScene().getWindow());
				alert.setTitle("Found the word...");
				alert.setHeaderText("Find Replace Succesfull!!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.initOwner(mainPane.getScene().getWindow());
				alert.setTitle("Can't find the word");
				alert.setHeaderText("Find Replace Unsuccesfull!!!");
				alert.setContentText("Check the word you have entered");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void handleUppercase() {
		mainTextArea.setText(mainTextArea.getText().toUpperCase());
	}

	@FXML
	public void handleLowercase() {
		mainTextArea.setText(mainTextArea.getText().toLowerCase());
	}

	@FXML
	public void handleLock() {
		mainTextArea.setEditable(false);
	}

	@FXML
	public void handleUnlock() {
		mainTextArea.setEditable(true);
	}

	@FXML
	public void handleWordWrap() {
		if (mainTextArea.isWrapText()) {
			mainTextArea.setWrapText(false);
		} else {
			mainTextArea.setWrapText(true);
		}
	}

	@FXML
	public void showFontDialog() {

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Change Font");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/fontdialog/fontDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		FontDialogController controller = new FontDialogController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			currentFont = controller.getFont();
			Font font = Font.font(currentFont, 16);
			mainTextArea.setFont(font);
		}
	}

	@FXML
	public void showColorDialog() {

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Change Color");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/colordialog/ColorDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		ColorDialogController controller = new ColorDialogController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			currentColor = controller.getColor();
			mainTextArea.setStyle("-fx-text-fill: #" + currentColor.substring(2, 8) + ";");
		}
	}

	@FXML
	public void showSizeDialog() {

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Change size");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/sizedialog/SizeDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		SizeDialogController controller = new SizeDialogController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			currentSize = controller.getSize();
			Font font = new Font(currentFont, currentSize);
			mainTextArea.setFont(font);
		}
	}

	@FXML
	public void handleRegularFont() {
		mainTextArea.getStyleClass().remove("underlined-text-area");
		mainTextArea.setFont(Font.font(currentFont, FontWeight.NORMAL, currentSize));
	}

	@FXML
	public void handleBoldFont() {
		mainTextArea.getStyleClass().remove("underlined-text-area");
		mainTextArea.setFont(Font.font(currentFont, FontWeight.BOLD, currentSize));
		System.out.println(mainTextArea.getText());
	}

	@FXML
	public void handleItalicFont() {
		mainTextArea.getStyleClass().remove("underlined-text-area");
		mainTextArea.setFont(Font.font(currentFont, FontPosture.ITALIC, currentSize));
	}

	@FXML
	public void handleUnderLine() {
		mainTextArea.getStyleClass().add("underlined-text-area");
	}

	@FXML
	public void handleAbout() {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URL("https://github.com/sumit-codebrewer").toURI());
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.initOwner(mainPane.getScene().getWindow());
			// Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			alert.setTitle("About Creator");
			alert.setHeaderText("Notepad 2.0 is developed by:");
			alert.setContentText("Sumit-Codebrewer");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleFeedback() throws IOException {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPane.getScene().getWindow());
		dialog.setTitle("Feedback Form");
		dialog.setHeaderText("Give your valuable feedback here");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/feedbackform/FeedbackDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		FeedbackController controller = new FeedbackController();

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String name = controller.getName();
			String review = controller.getReview();
			String suggestions = controller.getSuggestions();
			try (FileWriter f = new FileWriter("feedbacks.txt", true);
					BufferedWriter b = new BufferedWriter(f);
					PrintWriter p = new PrintWriter(b);) {
				p.print(String.format("Name:%s\tReview:%s\t Suggestions:%s\n", name, review, suggestions));
				p.println();
			} catch (IOException i) {
				i.printStackTrace();
			}

		}
	}

	@FXML
	public void handleWebSwitch() {
		if (webButton.isSelected()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URL("https://sumit-codebrewer.github.io/Notepad-web-version/").toURI());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.initOwner(mainPane.getScene().getWindow());
				alert.setTitle("Loading web version");
				alert.setHeaderText("Please wait...");
				alert.setContentText("Redirecting to the web version of Notepad 2.0");
				alert.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void readFile(File selectedFile) {
		new Thread(() -> {
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
				String s;
				while ((s = br.readLine()) != null) {
					sb.append(s).append("\n");
				}
				mainTextArea.setText(sb.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}).start();

	}

	public void writeInFile(String location) {
		new Thread(() -> {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(location)))) {
				String text = mainTextArea.getText();
				bw.write(text);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}).start();

	}
}
