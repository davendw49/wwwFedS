package wwwFedS.LifeScience.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import wwwFedS.LifeScience.parse.ParsingUnit;
import wwwFedS.LifeScience.travel.DataStructure.SGraphEdge;
import wwwFedS.LifeScience.util.InitialHelper;

public class traverseAction {

	private HashMap<Integer, ArrayList<SGraphEdge>> multiQueryUtil = new HashMap<>();
	private ArrayList<HashMap<Integer, ArrayList<String>>> queryArray = new ArrayList<>();// this is what i need
	private ArrayList<ArrayList<String>> KeywordPoint = new ArrayList<>();

	public HashMap<Integer, ArrayList<SGraphEdge>> getMultiQueryUtil() {
		return multiQueryUtil;
	}

	public void setMultiQueryUtil(HashMap<Integer, ArrayList<SGraphEdge>> multiQueryUtil) {
		this.multiQueryUtil = multiQueryUtil;
	}

	public ArrayList<HashMap<Integer, ArrayList<String>>> getQueryArray() {
		return queryArray;
	}

	public void setQueryArray(ArrayList<HashMap<Integer, ArrayList<String>>> queryArray) {
		this.queryArray = queryArray;
	}

	public ArrayList<ArrayList<String>> getKeywordPoint() {
		return KeywordPoint;
	}

	public void setKeywordPoint(ArrayList<ArrayList<String>> keywordPoint) {
		KeywordPoint = keywordPoint;
	}

	public traverseAction() {
		// TODO Auto-generated constructor stub
		ArrayList<SGraphEdge> a0 = new ArrayList<>();
		ArrayList<SGraphEdge> a1 = new ArrayList<>();
		ArrayList<SGraphEdge> a2 = new ArrayList<>();
		ArrayList<SGraphEdge> a3 = new ArrayList<>();

		multiQueryUtil.put(0, a0);
		multiQueryUtil.put(1, a1);
		multiQueryUtil.put(2, a2);
		multiQueryUtil.put(3, a3);
	}

	public void start(ParsingUnit pUnit, InitialHelper iHelper) throws Exception {

		ArrayList<ArrayList<Integer>> StartPoint = new ArrayList<>();
		StartPoint = pUnit.SubQuery;
		System.out.println(StartPoint.size());
		int possibleQuery = StartPoint.size();
		StructQuery gSubgraph = new StructQuery();
		// ArrayList<Integer> sp = new ArrayList<>();
		for (int index = 0; index < possibleQuery; index++) {
			System.out.println("**************************************************");
			gSubgraph.done(iHelper, StartPoint.get(index));
			/**
			 * SGraphEdge ed = new SGraphEdge(); ed.first = 220; ed.second = 1;
			 * gSubgraph.TheSubGraph.add(ed);
			 */
			// System.out.println("here we start");
			for (int i = 0; i < gSubgraph.TheSubGraph.size(); i++) {
				// System.out.println(gSubgraph.TheSubGraph.get(i).first + "--" +
				// gSubgraph.TheSubGraph.get(i).second);
				if (!iHelper.ifDirRight(Integer.valueOf(gSubgraph.TheSubGraph.get(i).first),
						Integer.valueOf(gSubgraph.TheSubGraph.get(i).second))) {
					int temp = gSubgraph.TheSubGraph.get(i).first;
					gSubgraph.TheSubGraph.get(i).first = gSubgraph.TheSubGraph.get(i).second;
					gSubgraph.TheSubGraph.get(i).second = temp;
				}
				// System.out.println(gSubgraph.TheSubGraph.get(i).first + "--" +
				// gSubgraph.TheSubGraph.get(i).second);
			}
			// System.out.println(gSubgraph.TheSubGraph);
			clear();
			for (int i = 0; i < gSubgraph.TheSubGraph.size(); i++) {

				// 获取类所在的数据集
				int thisdb = iHelper.getDN(gSubgraph.TheSubGraph.get(i).first);
				// System.out.println(thisdb);
				multiQueryUtil.get(thisdb).add(gSubgraph.TheSubGraph.get(i));
			}

			for (int keyindex = 0; keyindex < 4; keyindex++) {
				removeDuplicate(multiQueryUtil.get(keyindex));
			}

			System.out.println("0:" + multiQueryUtil.get(0));
			System.out.println("1:" + multiQueryUtil.get(1));
			System.out.println("2:" + multiQueryUtil.get(2));
			System.out.println("3:" + multiQueryUtil.get(3));
			// ************************************************************************************************************

			HashMap<Integer, ArrayList<String>> querylist = new HashMap<>();
			for (int i = 0; i < 4; i++) {
				ArrayList<SGraphEdge> edgeset = multiQueryUtil.get(i);
				ArrayList<ArrayList<String>> multiEdge = new ArrayList<>();
				for (int k = 0; k < edgeset.size(); k++) {
					ArrayList<String> preSet = new ArrayList<>();
					ArrayList<Integer> onList = iHelper.preSetPair.get(edgeset.get(k).first).get(edgeset.get(k).second);

					for (int p = 0; p < onList.size(); p++) {

					}
					multiEdge.add(preSet);
				}
			}
			int flag = 0;
			for (int j = 0; j < 4; j++) {
				if (querylist.get(j).isEmpty())
					flag++;
			}
			if (flag <= 3)
				queryArray.add(querylist);

			/*
			 * sp = StartPoint.get(index); ArrayList<String> ks = new ArrayList<>(); for
			 * (int i=0;i<sp.size();i++) {
			 * ks.add(kwlist.get(i).get(iHelper.getClassR(sp.get(i)))); }
			 * KeywordPoint.add(ks);
			 */
		}

		System.out.println("**************************************************");
		System.out.println("test output");
		System.out.println(queryArray.size());
		System.out.println(KeywordPoint);
		System.out.println("**************************************************");
		for (int i = 0; i < queryArray.size(); i++) {
			HashMap<Integer, ArrayList<String>> qlist = queryArray.get(i);
			System.out.println(i + " situation: ");
			for (int j = 0; j < 4; j++) {
				System.out.println(qlist.get(j));
			}
		}
		System.out.println("**************************************************");

	}

	public void clear() {
		multiQueryUtil.get(0).clear();
		multiQueryUtil.get(1).clear();
		multiQueryUtil.get(2).clear();
		multiQueryUtil.get(3).clear();
	}

	public static void main(String[] args) throws Exception {

		ParsingUnit pUnit = new ParsingUnit();
		InitialHelper iHelper = new InitialHelper();
		traverseAction tAction = new traverseAction();
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
	}

	private static void removeDuplicate(ArrayList<SGraphEdge> list) {
		
		ArrayList<String> tmp = new ArrayList<>();
		for (SGraphEdge sEdge : list) {
			String tString = String.valueOf(sEdge.first) + "," + String.valueOf(sEdge.second);
			tmp.add(tString);
			HashSet<String> set = new HashSet<>(tmp.size());
			ArrayList<String> result = new ArrayList<>(tmp.size());
			for (String str : tmp) {
				if (set.add(str)) {
					result.add(str);
				}
			}
			tmp.clear();
			tmp.addAll(result);
			list.clear();
			// System.out.println(tmp);
			for (String string : tmp) {
				String[] sArr = string.split(",");
				SGraphEdge sGraphEdge = new SGraphEdge(Integer.valueOf(sArr[0]), Integer.valueOf(sArr[1]));
				list.add(sGraphEdge);
			}
		}
	}
}
