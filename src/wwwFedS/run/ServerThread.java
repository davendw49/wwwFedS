package wwwFedS.run;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import wwwFedS.luceneSail.lsQuery;

import wwwFedS.LifeScience.*;

//处理查询的线程
public class ServerThread extends Thread {

	Socket s = null;
	// public static String basepath = "/home/daven/";
	// public static String basepath = "/home/FedS_system/";
	public static String basepath = "src/wwwFedS/LifeScience/";
	// public static String basepath =
	// "/Users/daven/eclipse-workspace/wwwFedS/src/wwwFedS/LifeScience/";
	//HashMap<String, String> keyword_is_class = new HashMap<>();
	//HashMap<String, String> keyword_is_property = new HashMap<>();

	public ServerThread(Socket s1) {
		s = s1;
	}

	//public ServerThread() {

	//}

	public void run() {

		/*ArrayList<String> classArray = new ArrayList<>();
		ArrayList<String> propertyArray = new ArrayList<>();
		try {
			init();
			classArray.addAll(keyword_is_class.keySet());
			propertyArray.addAll(keyword_is_property.keySet());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("file system error");
		}

		classArray.addAll(keyword_is_class.keySet());*/

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

					/*if (classArray.contains(sa[i].toLowerCase())) {
						HashMap<String, ArrayList<String>> classFormMap = new HashMap<>();
						
						classFormMap.put(keyword_is_class.get(sa[i].toLowerCase()), new ArrayList<>());
						classFormMap.get(keyword_is_class.get(sa[i].toLowerCase())).add("class");
						list.put(sa[i], classFormMap);
						
					} else if (propertyArray.contains(sa[i].toLowerCase())) {
						
						try {
							/*
							if (!list.containsKey(sa[i])) {
								list.put(sa[i], new HashMap<String, ArrayList<String>>());
							}
							new lsQuery();
							list.put(sa[i], lsQuery.lsExecute(sa[i]));
							//需要写一些东西

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}*/
					//else {

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
					//}
				}
				System.out.println("keyword--type---x finished");
				System.out.println(list.keySet());
				System.out.println("**************\n*************************\n******************\n***************");
				System.out.println(list.values());

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
						System.out.println(j + "slist.get(j).size(): " + slist.get(j).size());
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
									new lsQuery();
									result = result + lsQuery.chebiQuery(qs);
								}
								if (j == 1) {
									new lsQuery();
									result = result + lsQuery.keggQuery(qs);
								}
								if (j == 2) {
									new lsQuery();
									result = result + lsQuery.drugbankQuery(qs);
								}
								if (j == 3) {
									new lsQuery();
									result = result + lsQuery.dbpediaQuery(qs);
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

	/*
	 * //CrossDomain else if(str.contains("cm")) { String s=br.readLine();
	 * 
	 * String[] sa=s.split(";");
	 * 
	 * 
	 * //利用luceneSail进行查询
	 * 
	 * //查询结果集 ArrayList<HashMap<String,String>> list=new
	 * ArrayList<HashMap<String,String>>();
	 * 
	 * for(int i=0;i<sa.length;i++) { try { list.add(new
	 * lsQuery().cmExecute(sa[i])); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 * 
	 * System.out.println("type---x:"); System.out.println(list);
	 * 
	 * out.println("type---x:"); out.println(list);
	 * 
	 * if(list.size()==0) { System.out.println("No final result!");
	 * out.println("No final result!"); return; } else { for(int
	 * i=0;i<list.size();i++) { if(list.get(i).isEmpty()) {
	 * System.out.println("No final result!"); out.println("No final result!");
	 * return; } } }
	 * 
	 * //生成最后查询
	 * 
	 * ArrayList<HashMap<Integer, ArrayList<String>>> slist;
	 * 
	 * try {
	 * 
	 * slist = new CrossDomin.mainAction().FedS_Action(list); String result="";
	 * //执行最后的查询 for(int k=0;k<slist.size();k++) { for(int j=0;j<6;j++) { for(int
	 * i=0;i<slist.get(k).get(j).size();i++) { if(j==0) { result=result+new
	 * lsQuery().geonamesQuery(slist.get(k).get(j).get(i)); } if(j==1) {
	 * result=result+new lsQuery().jamendoQuery(slist.get(k).get(j).get(i)); }
	 * if(j==2) { result=result+new lsQuery().nytQuery(slist.get(k).get(j).get(i));
	 * } if(j==3) { result=result+new
	 * lsQuery().swdfoodQuery(slist.get(k).get(j).get(i)); } if(j==4) {
	 * result=result+new lsQuery().linkedmdbQuery(slist.get(k).get(j).get(i)); }
	 * if(j==5) { result=result+new
	 * lsQuery().dbpediaQuery(slist.get(k).get(j).get(i)); } } } }
	 * if(result.length()==0) { System.out.println("No final result!");
	 * out.println("No final result!"); } else {
	 * System.out.println("final result:"); System.out.println(result);
	 * 
	 * out.println("final result:"); out.println(result); }
	 * 
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	else

	{
		return;
	}

	os.close();out.close();is.close();isr.close();br.close();}catch(
	IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

	/*public void init() throws IOException {
		/**
		 * read the info of class_is_keyword mapping of life_science and cross_domain
		 
		FileInputStream inputStream = new FileInputStream(basepath + "dict_a/Keyword_is_class_ls.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] stringArr = str.split("%");
			keyword_is_class.put(stringArr[0].toLowerCase(), stringArr[1]);
		}

		FileInputStream inputStream1 = new FileInputStream(basepath + "dict_a/Keyword_is_property_ls.txt");
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream));
		String str1 = null;
		while ((str1 = bufferedReader.readLine()) != null) {
			String[] stringArr = str1.split("%");
			keyword_is_class.put(stringArr[0].toLowerCase(), stringArr[1]);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerThread sThread = new ServerThread();
		sThread.init();
		System.out.println(sThread.keyword_is_class);
	}*/
}
