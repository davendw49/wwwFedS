package wwwFedS.LifeScience.travel;

import java.util.ArrayList;

import wwwFedS.LifeScience.travel.DataStructure.Graph;
import wwwFedS.LifeScience.travel.DataStructure.SBrunch;
import wwwFedS.LifeScience.travel.DataStructure.SGraphEdge;
import wwwFedS.LifeScience.util.InitialHelper;

public class StructQuery {

	public static int M = Integer.MAX_VALUE;; // 此路不通
	public SBrunch subgraph = new SBrunch();
	public ArrayList<SGraphEdge> TheSubGraph = new ArrayList<>();

	public void GenerateSubGraph(ArrayList<Integer> Keyword, InitialHelper iHelper) throws Exception {
		int minDis = Integer.MAX_VALUE;
		String minPath = "";
		/*
		 * 这里严重的浪费了时间
		 * 
		 * DGraph dGraph = new DGraph(iHelper.nodenum);
		 * dGraph.createGraph(iHelper.graph); for (int index = 0; index <
		 * iHelper.nodenum; index++) { dGraph.Traverse(index); int flag = 0; int sum =
		 * 0; String str = ""; ArrayList<Integer> brunchlist = new ArrayList<>(); for
		 * (int i = 0; i < Keyword.size(); i++) { int dist =
		 * dGraph.printTarge(Keyword.get(i)).brunchValue; sum += dist; str +=
		 * dGraph.printTarge(Keyword.get(i)).road + "\n"; brunchlist.add(dist); if (dist
		 * == -1) flag = 1; } if (flag == 0) { if (minDis > sum) { minDis = sum; minPath
		 * = str; } } }
		 */
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
		System.out.println(subgraph.road);
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
