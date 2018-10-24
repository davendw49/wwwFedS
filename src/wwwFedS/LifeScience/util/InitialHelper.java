package wwwFedS.LifeScience.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class InitialHelper {

	public int graph[][] = new int[404][404];
	public int graph_value[][] = new int[404][404];
	public int graph_path[][] = new int[404][404];
	public int nodenum;
	public int edgenum;
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> preSetPair = new HashMap<>();
	public HashMap<Integer, ArrayList<Integer>> predicateMap = new HashMap<>();
	public HashMap<Integer, String> MapOfPreR = new HashMap<>();
	public HashMap<Integer, String> MapOfId2Name = new HashMap<>();// id->name
	public HashMap<Integer, Integer> MapOfId2Num = new HashMap<>();// id->db_number
	public HashMap<Integer, Integer> MapOfId2Pri = new HashMap<>();// id->pri
	public HashMap<Integer, Integer> MapOfId2Rank = new HashMap<>();// id->rank
	public HashMap<String, Integer> MapOfName2Id = new HashMap<>();// name->id
	private BufferedReader bufferedReader;

	public static String basepath = "/home/daven/";
	//public static String basepath = "src/wwwFedS/LifeScience/";
	//public static String basepath = "/Users/daven/eclipse-workspace/wwwFedS/src/wwwFedS/LifeScience/";
	public void init() throws IOException {
		FileInputStream inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema.txt"); // 缁濆璺緞鎴栫浉瀵硅矾寰勯兘鍙互锛岃繖閲屾槸缁濆璺緞锛屽啓鍏ユ枃浠舵椂婕旂ず鐩稿璺緞S
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		int start=0, end;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split(" ");
			start = Integer.valueOf(stringArr[1]);
			end = Integer.valueOf(stringArr[3]);
			graph[start][end] = 0;
			graph_value[start][end] = Integer.MAX_VALUE;
			if (stringArr.length > 4) {
				graph[start][end] = 1;
				graph_value[start][end] = 1;
				edgenum++;
			}
		}

		nodenum = start+1;
		inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema_path.txt"); // 缁濆璺緞鎴栫浉瀵硅矾寰勯兘鍙互锛岃繖閲屾槸缁濆璺緞锛屽啓鍏ユ枃浠舵椂婕旂ず鐩稿璺緞S

		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		str = null;
		int pos = 0;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split(" ");
			// System.out.println(stringArr.length);
			for (int i = 0; i < stringArr.length; i++) {
				graph_path[pos][i] = Integer.valueOf(stringArr[i]);
			}
			pos++;
		}

		inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema_Dir.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		str = null;

		for (int i = 0; i < 261; i++) {
			HashMap<Integer, ArrayList<Integer>> pPair = new HashMap<>();
			preSetPair.put(i, pPair);
		}

		while ((str = bufferedReader.readLine()) != null) {
			String[] strArr = str.split(" ");
			int len = strArr.length;
			ArrayList<Integer> oneedge = new ArrayList<>();

			if (len > 4) {
				for (int i = 4; i < len; i++) {
					oneedge.add(Integer.valueOf(strArr[i]));
					// System.out.println(oneedge.size());
				}
				preSetPair.get(Integer.valueOf(strArr[1])).put(Integer.valueOf(strArr[3]), oneedge);
			}
		}

		inputStream = new FileInputStream(basepath + "dict_a/class_info.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] strArr = str.split(" ");
			MapOfId2Name.put(Integer.valueOf(strArr[0]), strArr[1]);
			MapOfId2Num.put(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[2]));
			MapOfId2Pri.put(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[3]));
			MapOfId2Rank.put(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[4]));
			MapOfName2Id.put(strArr[1], Integer.valueOf(strArr[0]));
		}

		/**
		 * GetPreReal
		 */
		inputStream = new FileInputStream(basepath + "dict_a/pre_dict.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] strArr = str.split(" ");
			MapOfPreR.put(Integer.valueOf(strArr[1]), strArr[0]);
		}

		inputStream = new FileInputStream(basepath + "dict_a/SchemaGraph/Schema_Dir.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		str = null;
		for (int i = 0; i < 261; i++) {
			ArrayList<Integer> arrayList = new ArrayList<>();
			predicateMap.put(i, arrayList);
		}

		while ((str = bufferedReader.readLine()) != null) {
			String[] strArr = str.split(" ");
			// System.out.println(str);
			int len = strArr.length;
			// System.out.println(len);
			int f = Integer.valueOf(strArr[1]);
			int s = Integer.valueOf(strArr[3]);
			if (len > 4) {
				predicateMap.get(f).add(s);
			}
		}
	}

	/**
	 * GetClassChild
	 * 
	 * @param a
	 * @return 杩斿洖鏄惁瀛樺湪杈瑰拰瀛樺湪濡傛鏂瑰悜鐨勮矾寰?
	 */
	public boolean ifHasChild(int a) {
		if (predicateMap.get(a).isEmpty())
			return false;
		else
			return true;
	}

	public boolean ifDirRight(int a, int b) {
		if (predicateMap.get(a).contains(b))
			return true;
		else
			return false;
	}

	/**
	 * GetClassNo
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 *             杩斿洖绫荤殑缂栧彿String->integer
	 */
	public int getClassN(String className) throws IOException {
		return MapOfName2Id.get(className).intValue();
	}

	/**
	 * GetClassReal
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 *             杩斿洖绫荤殑鍏蜂綋鍚嶇Оinteger->String
	 */
	public String getClassR(int id) throws IOException {
		return MapOfId2Name.get(id);
	}

	/**
	 * GetDBNo
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 *             杩斿洖鎵?鍦ㄧ殑鏁版嵁闆?
	 */
	public String getDNo(String className) throws IOException {
		return className;
		// return DBNo.get(className);
	}

	public int getDN(int id) throws IOException {
		return MapOfId2Num.get(id);
	}

	/**
	 * GetPreReal
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 *             鑾峰彇predict鐨勫師鐗堝瓧绗︿覆
	 */
	public String getPreR(int id) throws IOException {
		return MapOfPreR.get(id);
	}

}
