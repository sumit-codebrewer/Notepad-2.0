package sizedialog;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


public class SizeDialogController {
	
    
	@FXML
	private ComboBox<Integer> sizebox;
	
	private static int currentSize=16;
	
	public void initialize() {
		List<Integer> sizes=new ArrayList<Integer>();
		for(int i=10;i<=70;i++) {
			sizes.add(i);
		}
		sizebox.setItems(FXCollections.observableList(sizes));
		sizebox.setValue(currentSize);
	}
	
	@FXML
	public void getSelectedSize() {
		currentSize=sizebox.getValue();
	}
	
	public int getSize() {
			return currentSize;
	}
}
