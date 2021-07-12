package feedbackform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class FeedbackController {

	@FXML
	private TextField nameField;
	
	@FXML
	private RadioButton review1;
	@FXML
	private RadioButton review2;
	@FXML
	private RadioButton review3;
	@FXML
	private RadioButton review4;
	
	@FXML
	private Label msgLabel;
	
	@FXML
	private TextArea suggestionsArea;
	
	private static String name="Default name";
	private static String review="Default review";
	private static String suggestions="Default suggestions";
	
	public void initialize() {
		review1.setSelected(true);
		msgLabel.setVisible(false);
		msgLabel.setText("Now press OK button");
	}
	
	@FXML
	public void handleClicked() {
		if(nameField.getText().trim().length()!=0) {
			name=nameField.getText();
		}
		
		if(review1.isSelected()) {
			review=review1.getText();
		}
		else if(review2.isSelected()) {
			review=review2.getText();
		}
		else if(review3.isSelected()) {
			review=review3.getText();
		}
		else if(review4.isSelected()) {
			review=review4.getText();
		}
		
		if(suggestionsArea.getText().trim().length()!=0) {
			suggestions=suggestionsArea.getText();
		}
		msgLabel.setVisible(true);
	}
	
	public String getName() {
		return name;
	}
	
	public String getReview() {
		return review;
	}
	public String getSuggestions() {
		return suggestions;
	}


}
