package wwwFedS.LifeScience.parse;

import java.util.ArrayList;
import java.util.HashMap;

import wwwFedS.LifeScience.util.InitialHelper;

public class ParsingUnit {

	public HashMap<Integer, KeyMaterial> Query = new HashMap<>();
	public ArrayList<ArrayList<Integer>> SubQuery = new ArrayList<>();
	public String filterMode = "";
	public String unionMode = "";

	public void Parse(HashMap<String, HashMap<String, ArrayList<String>>> query, InitialHelper iHelper)
			throws Exception {

		ArrayList<String> kwList = new ArrayList<>();
		kwList.addAll(query.keySet());
		
		ArrayList<ArrayList<Integer>> allKey = new ArrayList<>();
		for (String kString : kwList) {
			ArrayList<Integer> oneKey = new ArrayList<>();
			
			KeyMaterial kMaterial = new KeyMaterial();
			kMaterial.KeyName = kString;
			HashMap<String, ArrayList<String>> eMap = query.get(kString);
			ArrayList<String> cSet = new ArrayList<>();
			cSet.addAll(eMap.keySet());
			
			for (String str : cSet) {
				oneKey.add(iHelper.getClassN(str));
			}
			
			allKey.add(oneKey);
			kMaterial.ClassSet = cSet;
			kMaterial.EntityMapping = eMap;
			Query.put(kwList.indexOf(kString), kMaterial);
		}
		System.out.println(Query);
		SubQuery = new calComb().calculateCombinationInt(allKey);
		System.out.println("Finish Parse the Keywords, Our System has been told What You Want to Search.");
	}

	@Override
	public String toString() {
		return "ParsingUnit [Query=" + Query + ";\nSubQuery=" + SubQuery + "]";
	}

	

}
