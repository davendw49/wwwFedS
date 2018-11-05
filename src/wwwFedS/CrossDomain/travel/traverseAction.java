package wwwFedS.CrossDomain.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import wwwFedS.CrossDomain.parse.ParsingUnit;
import wwwFedS.CrossDomain.parse.calComb;
import wwwFedS.CrossDomain.travel.DataStructure.SGraphEdge;
import wwwFedS.CrossDomain.util.InitialHelper;

public class traverseAction {

	public HashMap<Integer, ArrayList<SGraphEdge>> multiQueryUtil = new HashMap<>();

	public ArrayList<HashMap<Integer, ArrayList<String>>> queryArray = new ArrayList<>();// this is what i need
	public ArrayList<HashMap<String, HashMap<Integer, String>>> plusArray = new ArrayList<>();// this is what we want to
																								// plus

	public ArrayList<ArrayList<String>> KeywordPoint = new ArrayList<>();

	public traverseAction() {
		// TODO Auto-generated constructor stub
		ArrayList<SGraphEdge> a0 = new ArrayList<>();
		ArrayList<SGraphEdge> a1 = new ArrayList<>();
		ArrayList<SGraphEdge> a2 = new ArrayList<>();
		ArrayList<SGraphEdge> a3 = new ArrayList<>();
		ArrayList<SGraphEdge> a4 = new ArrayList<>();
		ArrayList<SGraphEdge> a5 = new ArrayList<>();

		multiQueryUtil.put(0, a0);
		multiQueryUtil.put(1, a1);
		multiQueryUtil.put(2, a2);
		multiQueryUtil.put(3, a3);
		multiQueryUtil.put(4, a4);
		multiQueryUtil.put(5, a5);
	}

	public void start(ParsingUnit pUnit, InitialHelper iHelper) throws Exception {

		ArrayList<ArrayList<Integer>> StartPoint = new ArrayList<>();
		StartPoint = pUnit.SubQuery;
		System.out.println("the candidate query size is : " + StartPoint.size());
		int possibleQuery = StartPoint.size();
		StructQuery gSubgraph = new StructQuery();
		// ArrayList<Integer> sp = new ArrayList<>();
		for (int index = 0; index < possibleQuery; index++) {
			System.out.print(index + "***");

			/**
			 * this keyword list plus query
			 */

			gSubgraph.done(iHelper, StartPoint.get(index));
			HashMap<String, HashMap<Integer, String>> onePlus = generatePlus(StartPoint.get(index), pUnit, iHelper);

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

			for (int keyindex = 0; keyindex < 6; keyindex++) {
				removeDuplicate(multiQueryUtil.get(keyindex));
			}

			System.out.println("0:" + multiQueryUtil.get(0));
			System.out.println("1:" + multiQueryUtil.get(1));
			System.out.println("2:" + multiQueryUtil.get(2));
			System.out.println("3:" + multiQueryUtil.get(3));
			System.out.println("4:" + multiQueryUtil.get(4));
			System.out.println("5:" + multiQueryUtil.get(5));
			System.out.println("Structural Query:finished");
			// ************************************************************************************************************
			// 这里成功的在各个数据集中生成子图，下面需要开始查询的构建，根据点和边的信息生成sparql查询语句
			HashMap<Integer, ArrayList<String>> querylist = new HashMap<>();
			for (int i = 0; i < 6; i++) {
				ArrayList<SGraphEdge> edgeset = multiQueryUtil.get(i);
				ArrayList<ArrayList<String>> multiEdge = new ArrayList<>();
				for (int k = 0; k < edgeset.size(); k++) {
					ArrayList<String> preSet = new ArrayList<>();
					ArrayList<Integer> onList = iHelper.preSetPair.get(edgeset.get(k).first).get(edgeset.get(k).second);
					// 取出每条边包含的所有中间属性集合
					// System.out.println(onList);
					for (int p = 0; p < onList.size(); p++) {
						String oneSant = "";
						oneSant += "?" + String.valueOf(returnNodename(edgeset.get(k).first, StartPoint.get(index)));
						oneSant += " ";
						oneSant += iHelper.getPreR(onList.get(p));
						oneSant += " ";
						oneSant += "?" + String.valueOf(returnNodename(edgeset.get(k).second, StartPoint.get(index)));
						oneSant += ". \n";
						preSet.add(oneSant);
						// System.out.println(oneSant);
					}
					multiEdge.add(preSet);
				}
				// System.out.println(multiEdge);
				// 同一个数据集中进行排列组合
				if (multiEdge.size() != 0) {
					// System.out.println(new calComb().calculateCombinationStr(multiEdge));
					querylist.put(i, new calComb().calculateCombinationStr(multiEdge));
				} else {
					querylist.put(i, new ArrayList<>());
				}

			}

			int flag = 0;
			for (int j = 0; j < 6; j++) {
				if (querylist.get(j).isEmpty())
					flag++;
			}
			if (flag <= 2) {
				queryArray.add(querylist);
				plusArray.add(onePlus);
			}

		}

		// System.out.println("**************************************************");
		// System.out.println("test output");
		// System.out.println(queryArray.size());
		// System.out.println(KeywordPoint);
		/*
		 * System.out.println("**************************************************"); for
		 * (int i = 0; i < queryArray.size(); i++) { HashMap<Integer, ArrayList<String>>
		 * qlist = queryArray.get(i); System.out.println(i + " situation: "); for (int j
		 * = 0; j < 4; j++) { System.out.println(qlist.get(j)); } }
		 * System.out.println("**************************************************");
		 * System.out.println(filterMode);
		 */
		System.out.println("SPARQL Query:Established.");
	}

	public void clear() {
		multiQueryUtil.get(0).clear();
		multiQueryUtil.get(1).clear();
		multiQueryUtil.get(2).clear();
		multiQueryUtil.get(3).clear();
		multiQueryUtil.get(4).clear();
		multiQueryUtil.get(5).clear();
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
		// System.out.println(list);
		ArrayList<String> tmp = new ArrayList<>();
		for (SGraphEdge sEdge : list) {
			String tString = String.valueOf(sEdge.first) + "," + String.valueOf(sEdge.second);
			tmp.add(tString);
		}

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

	private static String returnNodename(int node, ArrayList<Integer> kw) {
		int flag = -1;
		for (Integer i : kw) {
			if (i == node) {
				flag = kw.indexOf(i);
				break;
			}
		}
		if (flag == -1) {
			return "e" + String.valueOf(node);
		} else {
			return "k" + String.valueOf(flag);
		}
	}

	private HashMap<String, HashMap<Integer, String>> generatePlus(ArrayList<Integer> kw, ParsingUnit pUnit,
			InitialHelper iHelper) throws Exception {
		HashMap<Integer, String> unionMode = new HashMap<>();
		HashMap<Integer, String> filterMode = new HashMap<>();
		String plus = "";
		for (int i = 0; i < kw.size(); i++) {
			plus = "";
			String onePlus = "FILTER(";
			int onekw = kw.get(i);
			// System.out.println(pUnit.Query.get(i).EntityMapping.get(iHelper.getClassR(onekw)));
			// 获取该类的对应实体应该组成的filter和union语句
			int sum = 0;
			for (int j = 0; j < pUnit.Query.get(i).EntityMapping.get(iHelper.getClassR(onekw)).size(); j++) {

				if (j == 0)
					onePlus += "?k" + String.valueOf(i) + "="
							+ pUnit.Query.get(i).EntityMapping.get(iHelper.getClassR(onekw)).get(j);
				else {
					onePlus += " || ?k" + String.valueOf(i) + "="
							+ pUnit.Query.get(i).EntityMapping.get(iHelper.getClassR(onekw)).get(j);
					sum++;
				}
				if (sum > 499) {
					System.out.println(sum);
					break;
				}
			}
			onePlus += ")\n";
			// System.out.println(onePlus);
			plus += onePlus;
			unionMode.put(i, plus);
		}
		System.out.println("unionMode:finished.");
		// return plus;
		// unionMode = plus;
		// plus = "";
		for (int i = 0; i < kw.size(); i++) {
			plus = "";
			String keyword = pUnit.Query.get(i).KeyName;
			String onePlus = "?k" + String.valueOf(i) + " search:matches ?m" + String.valueOf(i) + ". \n" + "?m"
					+ String.valueOf(i) + " search:query \"" + keyword + "\";\n" + "search:score ?score; \n"
					+ "search:snippet ?snippet. \n";
			plus += onePlus;
			filterMode.put(i, plus);
		}
		// System.out.println("unionMode:\n"+unionMode);
		// System.out.println("filterMode:\n"+filterMode);
		System.out.println("filterMode:finished.");

		HashMap<String, HashMap<Integer, String>> onePlus = new HashMap<>();
		onePlus.put("unionMode", unionMode);
		onePlus.put("filterMode", filterMode);

		return onePlus;
	}
}
