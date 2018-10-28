package wwwFedS.CrossDomain.travel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import wwwFedS.LifeScience.travel.DataStructure.Graph;

public class Test {

	public int g[][] = new int[404][404];
	public int g_path[][] = new int[404][404];
	public int edge = 0;
	public int vex = 0;

	public static String basepath = "/Users/daven/eclipse-workspace/wwwFedS/src/wwwFedS/LifeScience/";

	public void setmatrics() throws IOException {
		FileInputStream inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema.txt"); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径S
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		int start = 0, end;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split(" ");
			start = Integer.valueOf(stringArr[1]);
			end = Integer.valueOf(stringArr[3]);
			g[start][end] = Integer.MAX_VALUE;
			if (stringArr.length > 4) {
				g[start][end] = 1;
				edge++;
			}
		}
		vex = start;

		inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema_path.txt"); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径S

		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		str = null;
		int pos = 0;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split(" ");
			// System.out.println(stringArr.length);
			for (int i = 0; i < stringArr.length; i++) {
				g_path[pos][i] = Integer.valueOf(stringArr[i]);
			}
			pos++;
		}
	}

	public int StrucQuery(ArrayList<Integer> Keywords, int n) {
		int min = Integer.MAX_VALUE;
		int flag = 0;
		int sum;
		for (int i = 0; i < n - 1; i++) {
			sum = 0;
			for (Integer kw : Keywords) {
				sum += g_path[i][kw];
			}
			if (sum < min) {
				min = sum;
				flag = i;
			}
			// System.out.println(sum);
		}
		return flag;
	}

	public static void main(String[] args) throws IOException {

		Test test = new Test();
		test.setmatrics();

		int v = test.vex + 1, e = test.edge;
		System.out.println(v + " " + e);
		Graph graph = new Graph(v, e);
		ArrayList<Integer> Keywords = new ArrayList<>();
		Keywords.add(258);
		Keywords.add(252);
		// Keywords.add(252);
		graph.createGraph(test.g, e);
		// graph.print();
		// System.setOut(out);
		// System.out.println("finish");

		int point = test.StrucQuery(Keywords, v);
		// graph.Dijkstra(point);
		System.out.println(point + " is the shortest bridge point");
		for (Integer kw : Keywords) {
			graph.Dijkstra_print(point, kw);
		}

	}
}
