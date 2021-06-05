package colordialog;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorDialogController {

	@FXML
	private ColorPicker colorpicker;
	
	private static String currentColor="black";
	
	public void initialize() {
		colorpicker.setValue(Color.BLACK);
	}

	@FXML
	public void getSelectedColor() {
		currentColor=colorpicker.getValue().toString();
	}
	
	public String getColor() {
		return currentColor;
	}
}
