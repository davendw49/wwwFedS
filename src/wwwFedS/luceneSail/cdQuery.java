package wwwFedS.luceneSail;

import java.util.ArrayList;
import java.util.HashMap;

import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.openrdf.sail.lucene.LuceneSailSchema;

public class cdQuery {

	public static String toQuery(String kw) {
		String keyword = kw;
		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n"
				+ "SELECT DISTINCT ?t ?snippet ?x WHERE { \n"
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t. \n" + "?x search:matches ?m. \n"
				+ "?m search:query \"" + keyword + "\";\n" + "search:score ?score; \n" + "search:snippet ?snippet. "
				+ "}";
		return queryString;
	}

	public static String propertyQuery(String property) {

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n"
				+ "SELECT DISTINCT ?t1 ?t2 WHERE { \n" + "?x " + property + " ?y. \n"
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t1. \n"
				+ "?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t2. \n" + "}";
		return queryString;
	}

	public static String classQuery(String className) {
		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + "SELECT DISTINCT ?x WHERE { \n"
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " + className + ". \n" + "}";
		return queryString;
	}

	public static HashMap<String, ArrayList<String>> cdExecute(String kw) {

		HashMap<String, ArrayList<String>> sMap = new HashMap<String, ArrayList<String>>();
		String keyword = kw;
		String queryString = toQuery(kw);

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			while (result.hasNext()) {

				BindingSet bindings = result.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}

					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}

			query = new nytConnection().getNytConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result1 = query.evaluate();
			System.out.println("结果  :  " + result1.hasNext());
			while (result1.hasNext()) {

				BindingSet bindings = result1.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}
					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}

			query = new jamendoConnection().getJamendoConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result2 = query.evaluate();
			System.out.println("结果  :  " + result2.hasNext());
			while (result2.hasNext()) {

				BindingSet bindings = result2.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}

					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}

			query = new swdfoodConnection().getSwdfoodConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result3 = query.evaluate();
			System.out.println("结果  :  " + result3.hasNext());
			while (result3.hasNext()) {

				BindingSet bindings = result3.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}

					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}
			
			query = new geonamesConnection().getGeonamesConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result4 = query.evaluate();
			System.out.println("结果  :  " + result3.hasNext());
			while (result3.hasNext()) {

				BindingSet bindings = result3.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}

					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}
			
			query = new linkedmdbConnection().getLinkedmdbConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result5 = query.evaluate();
			System.out.println("结果  :  " + result3.hasNext());
			while (result3.hasNext()) {

				BindingSet bindings = result3.next();
				int pd = 0;
				String snippet = "";
				String x = "";
				String type = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("snippet")) {
						snippet = binding.getValue().toString().replace("<B>", "");
						snippet = snippet.replace("</B>", "");
						snippet = snippet.toLowerCase();
					}
				}

				if (snippet.contains(kw.toLowerCase())) {

					// System.out.println("found match: ");
					for (Binding binding : bindings) {
						// System.out.println(" "+binding.getName()+": "+binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
							type = "<" + type + ">";
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
							x = "<" + x + ">";
						}
					}

					if (!sMap.containsKey(type)) {
						sMap.put(type, new ArrayList<>());
					}
					if (!sMap.get(type).contains(x))
						sMap.get(type).add(x);
				}

			}
			

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sMap;

	}

	

	// 对各个数据集最后一次查询
	public static String dbpediaQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

	// geonames
	public static String geonamesQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new geonamesConnection().getGeonamesConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

	// jamendo
	public static String jamendoQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new jamendoConnection().getJamendoConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

	// linkedMDB
	public static String linkedmdbQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new linkedmdbConnection().getLinkedmdbConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

	// nyt
	public static String nytQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new nytConnection().getNytConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

	// swdfood
	public static String swdfoodQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		if (queryString.length() > 15000)
			return lastResult + "result: false \n";

		TupleQuery query;
		try {

			query = new swdfoodConnection().getSwdfoodConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			lastResult = lastResult + "result: " + result.hasNext() + "\n";
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				// System.out.println("found match: ");
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());

					lastResult = lastResult + " " + binding.getName().toString() + ": " + binding.getValue().toString()
							+ "\n";
				}
			}

		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastResult;
	}

}
