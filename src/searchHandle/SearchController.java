package searchHandle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SearchController {
	
	@FXML
	private TextField searchField;
	
	@FXML
	private Label infoLabel;
	
	private static String url="https://www.google.com";
	
	public void initialize() {
		infoLabel.setVisible(false);
	}
	
	@FXML
	public void handleClicked() {
		if(searchField.getText().trim().length()==0) {
			infoLabel.setText("Invalid URL!!! Search yourself");
			infoLabel.setVisible(true);
		}
		else {
			url=searchField.getText();
			infoLabel.setText("Now press OK button");
			infoLabel.setVisible(true);
		}
	}
	
	public String getUrl() {
		return url;
	}

}
