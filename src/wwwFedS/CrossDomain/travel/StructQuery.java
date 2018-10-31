package wwwFedS.CrossDomain.travel;

import java.util.ArrayList;

import wwwFedS.CrossDomain.travel.DataStructure.Graph;
import wwwFedS.CrossDomain.travel.DataStructure.SBrunch;
import wwwFedS.CrossDomain.travel.DataStructure.SGraphEdge;
import wwwFedS.CrossDomain.util.InitialHelper;



public class StructQuery {

	public static int M = Integer.MAX_VALUE;; // 此路不通
	public SBrunch subgraph = new SBrunch();
	public ArrayList<SGraphEdge> TheSubGraph = new ArrayList<>();

	public void GenerateSubGraph(ArrayList<Integer> Keyword, InitialHelper iHelper) throws Exception {
		String str = "";
		int sum = 0;
		Graph graph = new Graph(iHelper.nodenum, iHelper.edgenum, iHelper.graph_value);
		int point = graph.StrucQuery(Keyword, iHelper.nodenum);
		for (Integer kw : Keyword) {
			SBrunch sBrunch = new SBrunch();
			sBrunch = graph.Dijkstra_result(point, kw);
			if (sBrunch.road != "not") {
				str += sBrunch.road + "\n";
				sum += sBrunch.brunchValue;
			}
		}
		subgraph.brunchValue = sum;
		subgraph.road = str;
		System.out.println("Subgraph Generated.");
		
	}

	public void done(InitialHelper iHelper, ArrayList<Integer> Keyword) throws Exception {
		// Keyword.add(198);
		// Keyword.add(258);
		// System.out.println(Keyword);
		TheSubGraph = new ArrayList<>();
		// TheSubGraph.clear();
		// System.out.println("after clear: " + TheSubGraph.size());
		GenerateSubGraph(Keyword, iHelper);
		String sg = subgraph.road;
		String brunchset[] = sg.split("\n");
		for (int i = 0; i < brunchset.length; i++) {
			String strArr[] = brunchset[i].split(" ");
			for (int j = 0; j < strArr.length - 1; j++) {
				SGraphEdge newEdge = new SGraphEdge();
				newEdge.first = Integer.valueOf(strArr[j]);
				newEdge.second = Integer.valueOf(strArr[j + 1]);
				if (iHelper.ifDirRight(newEdge.first, newEdge.second)
						|| iHelper.ifDirRight(newEdge.second, newEdge.first))
					TheSubGraph.add(newEdge);
			}
		}
		// System.out.println("after generate: " + TheSubGraph.size());

		for (int i = 0; i < TheSubGraph.size(); i++) {
			if (TheSubGraph.get(i).first == TheSubGraph.get(i).second) {
				TheSubGraph.remove(i);
				i--;
			}
		}
	}
}
