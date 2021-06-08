package findAndReplaceDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class FindAndReplaceController {
	
	@FXML
	private TextField findField;
	@FXML
	private TextField replaceField;
	
	@FXML
	private Label msgLabel;
	@FXML
	private Label anotherMsgLabel;	
	private static String text="Hello";
	private static String replaceText="World";
	
	public void initialize() {
		Font font=new Font("Arial bold",16);
		msgLabel.setVisible(false);
		msgLabel.setFont(font);
		anotherMsgLabel.setVisible(false);
		anotherMsgLabel.setFont(font);
	}
	
	@FXML
	public void handleClicked() {
		if(findField.getText().trim().length()==0) {
			msgLabel.setText("Enter text first!!!");
			msgLabel.setVisible(true);
		}
		else {
			text=findField.getText();
		}	
	}
	
	public String getText() {
		return text;
	}
	
	
	@FXML
	public void handleAnotherClicked() {
		if(replaceField.getText().trim().length()==0) {
			anotherMsgLabel.setText("Enter text first!!!");
			anotherMsgLabel.setVisible(true);
		}
		else {
			replaceText=replaceField.getText();
		}
	}
	
	public String getReplaceText() {
		return replaceText;
	}
	
	

}
