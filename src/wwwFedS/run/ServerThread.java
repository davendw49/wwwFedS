package wwwFedS.run;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import wwwFedS.luceneSail.lsQuery;

import wwwFedS.LifeScience.*;

//处理查询的线程
public class ServerThread extends Thread {

	Socket s = null;

	public ServerThread(Socket s1) {
		s = s1;
	}

	public void run() {
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
					try {
						// list.add(new lsQuery().exExecute(sa[i]));
						
						if (!list.containsKey(sa[i])) {
							list.put(sa[i], new HashMap<String, ArrayList<String>>());
						}
						new lsQuery();
						list.put(sa[i],lsQuery.lsExecute(sa[i]));
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("keyword--type---x:");
				System.out.println(list);

				out.println("keyword--type---x:");
				out.println(list);

				if (list.size() == 0) {
					System.out.println("No final result!");
					out.println("No final result!");
					return;
				} else {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).isEmpty()) {
							System.out.println("No final result!");
							out.println("No final result!");
							return;
						}
					}
				}

				long endTime_for_fulltext = System.currentTimeMillis();

				HashMap<Integer, ArrayList<String>> slist;

				try {

					// 生成中间查询
					
					new wwwFedS.LifeScience.mainAction();
					slist = mainAction.doAction(list);

					String result = "";
					long endTime_for_structquery = System.currentTimeMillis();

					// ִ查询最终结果
					//for (int k = 0; k < slist.size(); k++) {

						for (int j = 0; j < 4; j++) {
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
					//}
					if (result.length() == 0) {
						System.out.println("No final result!");

						out.println("No final result!");
					} else {
						System.out.println("final result:");
						System.out.println(result);

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

			else {
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
}
