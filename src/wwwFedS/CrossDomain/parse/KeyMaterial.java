package wwwFedS.CrossDomain.parse;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyMaterial {

	public String KeyName;
	public ArrayList<String> ClassSet = new ArrayList<>();
	public HashMap<String, ArrayList<String>> EntityMapping = new HashMap<>();
	@Override
	public String toString() {
		return "KeyMaterial [KeyName=" + KeyName + ", ClassSet=" + ClassSet + ", EntityMapping=" + EntityMapping + "]";
	}

	
}
