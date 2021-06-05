package fontdialog;

import fontfamily.FontFamily;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class FontDialogController {
	
	@FXML
	private ComboBox<String> fontfamily;
	
	@FXML
	private Label sample;
	
	private static String currentFont="Times New Roman";
	
	public void initialize() {
        fontfamily.setItems(FXCollections.observableList(FontFamily.getInstance().getFontNames()));
		fontfamily.setValue("Times New Roman");
	}
	
	@FXML
	public void getSelectedFont() {
		currentFont=fontfamily.getValue();
		sample.setFont(Font.font(currentFont));
	}
	
	public String getFont() {
		return currentFont;
	}
}
