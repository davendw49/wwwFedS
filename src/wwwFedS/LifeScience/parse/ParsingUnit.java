package wwwFedS.LifeScience.parse;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsingUnit {

	ArrayList<KeyMaterial> Query = new ArrayList<>();

	public void Parse(HashMap<String, HashMap<String, ArrayList<String>>> query) {

		ArrayList<String> kwList = new ArrayList<>();
		kwList.addAll(query.keySet());
		for (String kString : kwList) {
			KeyMaterial kMaterial = new KeyMaterial();
			kMaterial.KeyName = kString;
			HashMap<String, ArrayList<String>> eMap = query.get(kString);
			ArrayList<String> cSet = new ArrayList<>();
			cSet.addAll(eMap.keySet());

			kMaterial.ClassSet = cSet;
			kMaterial.EntityMapping = eMap;
			Query.add(kMaterial);
		}
	}

}
