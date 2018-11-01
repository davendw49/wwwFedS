package wwwFedS.LifeScience.parse;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyMaterial {

	public String KeyName;
	public ArrayList<String> ClassSet = new ArrayList<>();
	public HashMap<String, ArrayList<String>> EntityMapping = new HashMap<>();
	public String ktype;//class\property\resource
	
	@Override
	public String toString() {
		return "KeyMaterial [KeyName=" + KeyName + ", ClassSet=" + ClassSet + ", EntityMapping=" + EntityMapping + "]";
	}

	
}
