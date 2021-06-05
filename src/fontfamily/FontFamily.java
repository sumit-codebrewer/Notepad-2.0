package fontfamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontFamily {
	private static FontFamily instance=new FontFamily();
	
	private FontFamily() {
		
	}
	
	public static FontFamily getInstance() {
		return instance;
	}
	
	public List<String> getFontNames(){
		return new ArrayList<>(Arrays.asList("Arial","Arial Black","Calibri","Century","Century Gothic",
                "Century Sams MS", "Consolas", "Constania", "Courier New", "Freestyle Script", "Garamond",
                "Georgia", "MV Boli", "Pristina", "Segoe Print", "Times New Roman"));
	}
}
