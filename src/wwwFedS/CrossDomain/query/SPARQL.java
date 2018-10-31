package wwwFedS.CrossDomain.query;

import java.util.ArrayList;
import java.util.HashMap;

import wwwFedS.CrossDomain.parse.ParsingUnit;
import wwwFedS.CrossDomain.travel.traverseAction;
import wwwFedS.CrossDomain.util.InitialHelper;



public class SPARQL {

	public HashMap<Integer, ArrayList<String>> execList = new HashMap<>();

	public void generateExeclist(ArrayList<HashMap<Integer, ArrayList<String>>> queryArray, ArrayList<HashMap<String, HashMap<Integer, String>>> plusArray, String modeName) {
		//System.out.println(queryArray);
		System.out.println("Start Query In Federated RDF System.");
		// 生成合理的sparql查询语句
		
		execList.put(0, new ArrayList<>());
		execList.put(1, new ArrayList<>());
		execList.put(2, new ArrayList<>());
		execList.put(3, new ArrayList<>());
		
		if (queryArray.size() == plusArray.size()) System.out.println("number is correct, sparql query is under making");
		
		for (int i = 0; i < queryArray.size(); i++) {
			HashMap<Integer, ArrayList<String>> qlist = queryArray.get(i);
			// System.out.println(i + " situation: ");
			for (int j = 0; j < 4; j++) {
				
				if (qlist.get(j).size() > 0) {
					for (String str : qlist.get(j)) {
						//生成附加filter或者union的sparql语句：
						String plus = generatePlus(str, plusArray.get(i), modeName);
						String tmp = "SELECT * WHERE{\n" + str + plus + "}";
						if (!plus.equals("")) execList.get(j).add(tmp);
					}
				}
				//execList.put(j, oneBaseQuery);
			}
		}
	}
	
	public String generatePlus(String origin, HashMap<String, HashMap<Integer, String>> modePlus, String modeName) {
		HashMap<Integer, String> mode = modePlus.get(modeName);
		ArrayList<Integer> kwnum = new ArrayList<>();
		kwnum.addAll(mode.keySet());
		//System.out.println(kwnum);
		String plusSPARQL = "";
		for (Integer kn : kwnum) {
			if (origin.indexOf("k"+String.valueOf(kn))!=-1) {
				plusSPARQL+=mode.get(kn);
			}
		}
		return plusSPARQL;
	}

	public static void main(String[] args) throws Exception {

		ParsingUnit pUnit = new ParsingUnit();
		InitialHelper iHelper = new InitialHelper();
		traverseAction tAction = new traverseAction();
		SPARQL sparql = new SPARQL();
		iHelper.init();

		HashMap<String, HashMap<String, ArrayList<String>>> query = new HashMap<>();

		HashMap<String, ArrayList<String>> h1 = new HashMap<>();
		ArrayList<String> oArrayList0 = new ArrayList<>();
		oArrayList0.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01397>");
		oArrayList0.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01398>");
		oArrayList0.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01399>");
		h1.put("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/drugs>", oArrayList0);

		ArrayList<String> oArrayList1 = new ArrayList<>();
		oArrayList1.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01397>");
		oArrayList1.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01398>");
		oArrayList1.add("<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01399>");
		h1.put("<http://www4.wiwiss.fu-berlin.de/drugbank/vocab/resource/class/Offer>", oArrayList1);

		HashMap<String, ArrayList<String>> h2 = new HashMap<>();
		ArrayList<String> oArrayList2 = new ArrayList<>();
		oArrayList2.add("<http://dbpedia.org/resource/John_Robert_Vane>");
		h2.put("<http://dbpedia.org/ontology/Scientist>", oArrayList2);

		ArrayList<String> oArrayList3 = new ArrayList<>();
		oArrayList3.add("<http://dbpedia.org/resource/John_Robert_Vane>");
		h2.put("<http://dbpedia.org/ontology/Person>", oArrayList3);

		query.put("asprin", h1);
		query.put("john robert vane", h2);

		pUnit.Parse(query, iHelper);
		tAction.start(pUnit, iHelper);
		System.out.println(pUnit.toString());
		sparql.generateExeclist(tAction.queryArray, tAction.plusArray, "unionMode");//"filterMode"
		
		for (int i=0;i<4;i++) {
			System.out.println("this is a query for database" + i + ": ");
			ArrayList<String> tmp = sparql.execList.get(i);
			for (String string : tmp) {
				System.out.println(string);
			}
		}
	}
}
