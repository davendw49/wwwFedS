package wwwFedS.run;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import wwwFedS.luceneSail.cdQuery;
import wwwFedS.luceneSail.lsQuery;

import wwwFedS.LifeScience.*;

//处理查询的线程
public class ServerThread extends Thread {

	Socket s = null;

// *****************************************************************************************************************//
	// public static String basepath = "/home/daven/";
	// public static String basepath = "/home/FedS_system/";
	// public static String basepath = "src/wwwFedS/LifeScience/";
	public static String basepath_ls = "/Users/daven/eclipse-workspace/wwwFedS/src/wwwFedS/LifeScience/";
	public static String basepath_cd = "/Users/daven/eclipse-workspace/wwwFedS/src/wwwFedS/CrossDomain/";
	HashMap<String, String> keyword_is_class_ls = new HashMap<>();
	HashMap<String, String> keyword_is_property_ls = new HashMap<>();
	HashMap<String, String> keyword_is_class_cd = new HashMap<>();
	HashMap<String, String> keyword_is_property_cd = new HashMap<>();
// *****************************************************************************************************************//

	/*
	 * time calculate
	 */
	public HashMap<Integer, ArrayList<Long>> timeCollection = new HashMap<>();

	public ServerThread(Socket s1) {
		s = s1;
	}

	public void run() {
// *****************************************************************************************************************//
		ArrayList<String> classArray_ls = new ArrayList<>();
		ArrayList<String> propertyArray_ls = new ArrayList<>();
		ArrayList<String> classArray_cd = new ArrayList<>();
		ArrayList<String> propertyArray_cd = new ArrayList<>();
		try {
			init_ls();
			init_cd();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("file system error");
		}

		classArray_ls.addAll(keyword_is_class_ls.keySet());
		propertyArray_ls.addAll(keyword_is_property_ls.keySet());
		classArray_cd.addAll(keyword_is_class_cd.keySet());
		propertyArray_cd.addAll(keyword_is_property_cd.keySet());

// *****************************************************************************************************************//

		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String str = br.readLine();
			OutputStream os = s.getOutputStream();
			PrintWriter out = new PrintWriter(os, true);
			long startTime = System.currentTimeMillis();
			if (str.contains("ls")) {
				String s = br.readLine();
				String[] sa = s.split(";");
				System.out.println("input keywords are: ");
				for (int i = 0; i < sa.length; i++) {
					System.out.println(sa[i]);
				}
				// 开始查询
				// 查询类
				// list:keyword,type,x
				HashMap<String, HashMap<String, ArrayList<String>>> list = new HashMap<>();
				for (int i = 0; i < sa.length; i++) {
// *****************************************************************************************************************//
					if (classArray_ls.contains(sa[i].toLowerCase())) {
						HashMap<String, ArrayList<String>> classFormMap = new HashMap<>();
						classFormMap.put(keyword_is_class_ls.get(sa[i].toLowerCase()), new ArrayList<>());
						classFormMap.get(keyword_is_class_ls.get(sa[i].toLowerCase())).add("class");
						list.put(sa[i], classFormMap);
					} else if (propertyArray_ls.contains(sa[i].toLowerCase())) {
						try {
							if (!list.containsKey(sa[i])) {
								list.put(sa[i], new HashMap<String, ArrayList<String>>());
							}
							new lsQuery();
							list.put(sa[i], lsQuery.lsExecute(sa[i]));
							// 需要写一些东西
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
// *****************************************************************************************************************//
					else {
						try {
							// list.add(new lsQuery().exExecute(sa[i]));
							if (!list.containsKey(sa[i])) {
								list.put(sa[i], new HashMap<String, ArrayList<String>>());
							}
							new lsQuery();
							list.put(sa[i], lsQuery.lsExecute(sa[i]));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				System.out.println("keyword--type---x finished");
				// System.out.println(list.keySet());
				System.out.println("**************\n*************************\n******************\n***************");
				// System.out.println(list.values());
				// out.println("keyword--type---x:");
				// out.println(list);
				out.println("Keyword->Entity->Class are Mapping");
				/*
				 * if (list.size() == 0) { System.out.println("No final result!");
				 * out.println("No final result!"); return; } else { for (int i = 0; i <
				 * list.size(); i++) { if (list.get(i).isEmpty()) {
				 * System.out.println("No final result!"); out.println("No final result!");
				 * return; } } }
				 */
				long endTime_for_fulltext = System.currentTimeMillis();
				HashMap<Integer, ArrayList<String>> slist;
				try {
					// 生成中间查询
					new wwwFedS.LifeScience.mainAction();
					slist = mainAction.doAction(list);
					String result = "";
					long endTime_for_structquery = System.currentTimeMillis();
					// 查询最终结果
					// for (int k = 0; k < slist.size(); k++) {
					for (int j = 0; j < 4; j++) {
						timeCollection.put(j, new ArrayList<>());
						System.out.println(j + " slist.get(j).size(): " + slist.get(j).size());
						// System.out.println(slist.get(j));
						for (int i = 0; i < slist.get(j).size(); i++) {
							String qs = slist.get(j).get(i);
							String qs1 = qs;
							qs1 = qs1.replace("SELECT", "");
							qs1 = qs1.replace("*", "");
							qs1 = qs1.replace("WHERE", "");
							qs1 = qs1.replace("{", "");
							qs1 = qs1.replace("}", "");
							qs1 = qs1.trim();
							if (!qs1.equals("")) {
								if (j == 0) {
									long chebi_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.chebiQuery(qs);
									long chebi_end_time = System.currentTimeMillis();
									timeCollection.get(0).add(chebi_end_time - chebi_start_time);
								}
								if (j == 1) {
									long kegg_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.keggQuery(qs);
									long kegg_end_time = System.currentTimeMillis();
									timeCollection.get(1).add(kegg_end_time - kegg_start_time);
								}
								if (j == 2) {
									long drugbank_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.drugbankQuery(qs);
									long drugbank_end_time = System.currentTimeMillis();
									timeCollection.get(2).add(drugbank_end_time - drugbank_start_time);
								}
								if (j == 3) {
									long dbpedia_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.dbpediaQuery(qs);
									long dbpedia_end_time = System.currentTimeMillis();
									timeCollection.get(3).add(dbpedia_end_time - dbpedia_start_time);
								}
							}
						}
					}
					// }
					if (result.length() == 0) {
						System.out.println("No final result!");
						out.println("No final result!");
					} else {
						// System.out.println("final result:");
						// System.out.println(result);
						out.println("final result:");
						out.println(result);
					}
					long endTime_for_sparql = System.currentTimeMillis();
					System.out.println("fisrt ls: " + (endTime_for_fulltext - startTime) + "ms");
					System.out.println("traversing: " + (endTime_for_structquery - endTime_for_fulltext) + "ms");
					System.out.println("final: " + (endTime_for_sparql - endTime_for_structquery) + "ms");
					out.println("fisrt ls: " + (endTime_for_fulltext - startTime) + "ms");
					out.println("traversing: " + (endTime_for_structquery - endTime_for_fulltext) + "ms");
					out.println("final: " + (endTime_for_sparql - endTime_for_structquery) + "ms");
					for (int num = 0; num < 4; num++) {
						long time_repository = 0;
						for (long time_one : timeCollection.get(num)) {
							time_repository += time_one;
						}
						System.out.println(num + "'s times: " + time_repository / timeCollection.size() + "ms");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (str.contains("cm")) {
				String s = br.readLine();
				String[] sa = s.split(";");
				System.out.println("input keywords are: ");
				for (int i = 0; i < sa.length; i++) {
					System.out.println(sa[i]);
				}
				// 开始查询
				// 查询类
				// list:keyword,type,x
				HashMap<String, HashMap<String, ArrayList<String>>> list = new HashMap<>();
				for (int i = 0; i < sa.length; i++) {
					// *****************************************************************************************************************//
					if (classArray_cd.contains(sa[i].toLowerCase())) {
						HashMap<String, ArrayList<String>> classFormMap = new HashMap<>();
						classFormMap.put(keyword_is_class_cd.get(sa[i].toLowerCase()), new ArrayList<>());
						classFormMap.get(keyword_is_class_cd.get(sa[i].toLowerCase())).add("class");
						list.put(sa[i], classFormMap);
					} else if (propertyArray_cd.contains(sa[i].toLowerCase())) {
						try {
							if (!list.containsKey(sa[i])) {
								list.put(sa[i], new HashMap<String, ArrayList<String>>());
							}
							new cdQuery();
							list.put(sa[i], cdQuery.cdExecute(sa[i]));
							// 需要写一些东西
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// *****************************************************************************************************************//
					else {
						try {
							// list.add(new lsQuery().exExecute(sa[i]));
							if (!list.containsKey(sa[i])) {
								list.put(sa[i], new HashMap<String, ArrayList<String>>());
							}
							new lsQuery();
							list.put(sa[i], lsQuery.lsExecute(sa[i]));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				System.out.println("keyword--type---x finished");
				// System.out.println(list.keySet());
				System.out.println("**************\n*************************\n******************\n***************");
				// System.out.println(list.values());
				// out.println("keyword--type---x:");
				// out.println(list);
				out.println("Keyword->Entity->Class are Mapping");
				/*
				 * if (list.size() == 0) { System.out.println("No final result!");
				 * out.println("No final result!"); return; } else { for (int i = 0; i <
				 * list.size(); i++) { if (list.get(i).isEmpty()) {
				 * System.out.println("No final result!"); out.println("No final result!");
				 * return; } } }
				 */
				long endTime_for_fulltext = System.currentTimeMillis();
				HashMap<Integer, ArrayList<String>> slist;
				try {
					// 生成中间查询
					new wwwFedS.LifeScience.mainAction();
					slist = mainAction.doAction(list);
					String result = "";
					long endTime_for_structquery = System.currentTimeMillis();
					// 查询最终结果
					// for (int k = 0; k < slist.size(); k++) {
					for (int j = 0; j < 4; j++) {
						timeCollection.put(j, new ArrayList<>());
						System.out.println(j + " slist.get(j).size(): " + slist.get(j).size());
						// System.out.println(slist.get(j));
						for (int i = 0; i < slist.get(j).size(); i++) {
							String qs = slist.get(j).get(i);
							String qs1 = qs;
							qs1 = qs1.replace("SELECT", "");
							qs1 = qs1.replace("*", "");
							qs1 = qs1.replace("WHERE", "");
							qs1 = qs1.replace("{", "");
							qs1 = qs1.replace("}", "");
							qs1 = qs1.trim();
							if (!qs1.equals("")) {
								if (j == 0) {
									long chebi_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.chebiQuery(qs);
									long chebi_end_time = System.currentTimeMillis();
									timeCollection.get(0).add(chebi_end_time - chebi_start_time);
								}
								if (j == 1) {
									long kegg_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.keggQuery(qs);
									long kegg_end_time = System.currentTimeMillis();
									timeCollection.get(1).add(kegg_end_time - kegg_start_time);
								}
								if (j == 2) {
									long drugbank_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.drugbankQuery(qs);
									long drugbank_end_time = System.currentTimeMillis();
									timeCollection.get(2).add(drugbank_end_time - drugbank_start_time);
								}
								if (j == 3) {
									long dbpedia_start_time = System.currentTimeMillis();
									new lsQuery();
									result = result + lsQuery.dbpediaQuery(qs);
									long dbpedia_end_time = System.currentTimeMillis();
									timeCollection.get(3).add(dbpedia_end_time - dbpedia_start_time);
								}
							}
						}
					}
					// }
					if (result.length() == 0) {
						System.out.println("No final result!");
						out.println("No final result!");
					} else {
						// System.out.println("final result:");
						// System.out.println(result);
						out.println("final result:");
						out.println(result);
					}
					long endTime_for_sparql = System.currentTimeMillis();
					System.out.println("fisrt ls: " + (endTime_for_fulltext - startTime) + "ms");
					System.out.println("traversing: " + (endTime_for_structquery - endTime_for_fulltext) + "ms");
					System.out.println("final: " + (endTime_for_sparql - endTime_for_structquery) + "ms");
					out.println("fisrt ls: " + (endTime_for_fulltext - startTime) + "ms");
					out.println("traversing: " + (endTime_for_structquery - endTime_for_fulltext) + "ms");
					out.println("final: " + (endTime_for_sparql - endTime_for_structquery) + "ms");
					for (int num = 0; num < 4; num++) {
						long time_repository = 0;
						for (long time_one : timeCollection.get(num)) {
							time_repository += time_one;
						}
						System.out.println(num + "'s times: " + time_repository / timeCollection.size() + "ms");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else

			{
				return;
			}

			os.close();
			out.close();
			is.close();
			isr.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

// *****************************************************************************************************************//
	public ServerThread() {

	}

	public void init_ls() throws IOException {
		/*
		 * read the info of class_is_keyword mapping of life_science and cross_domain
		 */
		FileInputStream inputStream = new FileInputStream(basepath_ls + "dict_a/Keyword_is_class_ls.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split("%");
			keyword_is_class_ls.put(stringArr[0].toLowerCase(), stringArr[1]);
		}

		FileInputStream inputStream1 = new FileInputStream(basepath_ls + "dict_a/Keyword_is_property_ls.txt");
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
		String str1 = null;
		while ((str1 = bufferedReader1.readLine()) != null) {
			String[] stringArr = str1.split("%");
			keyword_is_property_ls.put(stringArr[0].toLowerCase(), stringArr[1]);
		}
	}

	public void init_cd() throws IOException {
		/*
		 * read the info of class_is_keyword mapping of life_science and cross_domain
		 */
		FileInputStream inputStream = new FileInputStream(basepath_cd + "dict_b/Keyword_is_class_cd.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split("%");
			keyword_is_class_cd.put(stringArr[0].toLowerCase(), stringArr[1]);
		}

		FileInputStream inputStream1 = new FileInputStream(basepath_cd + "dict_b/Keyword_is_property_cd.txt");
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
		String str1 = null;
		while ((str1 = bufferedReader1.readLine()) != null) {
			String[] stringArr = str1.split("%");
			keyword_is_property_cd.put(stringArr[0].toLowerCase(), stringArr[1]);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerThread sThread = new ServerThread();
		sThread.init_ls();
		sThread.init_cd();
		System.out.println(sThread.keyword_is_class_ls);
		System.out.println(sThread.keyword_is_class_cd);
		System.out.println(sThread.keyword_is_property_ls);
		System.out.println(sThread.keyword_is_property_cd);
	}
// *****************************************************************************************************************//
}
