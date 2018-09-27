package wwwFedS.LifeScience.parse;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsingUnit {

	ArrayList<KeyMaterial> Query = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public void Parse(HashMap<String, ArrayList<HashMap<String, ArrayList<String>>>> list) {

		ArrayList<String> kwList = (ArrayList<String>) list.keySet();
		for (String kString : kwList) {
			KeyMaterial kMaterial = new KeyMaterial();
			kMaterial.KeyName = kString;
			ArrayList<HashMap<String, ArrayList<String>>> tmp = list.get(kString);
			ArrayList<String> cSet = new ArrayList<>();
			HashMap<String, ArrayList<String>> eMap = new HashMap<>();
			for (HashMap<String, ArrayList<String>> tmHashMap : tmp) {
				ArrayList<String> eSet = (ArrayList<String>) tmHashMap.keySet();
				cSet.add(tmHashMap.get(eSet.get(0)).get(0));
				eMap.put(tmHashMap.get(eSet.get(0)).get(0), eSet);
			}
			kMaterial.ClassSet = cSet;
			kMaterial.EntityMapping = eMap;
			Query.add(kMaterial);
		}
	}
}
