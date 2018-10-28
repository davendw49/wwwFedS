package wwwFedS.luceneSail;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.*;

import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.algebra.Str;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.lucene.LuceneSail;
import org.openrdf.sail.lucene.LuceneSailSchema;
import org.openrdf.sail.memory.MemoryStore;

public class lsQuery {

	public static String toQuery(String kw) {
		String keyword = kw;
		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n"
				+ "SELECT ?t ?snippet ?x WHERE { \n" + "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t. \n"
				+ "?x search:matches ?m. \n" + "?m search:query \"" + keyword + "\";\n" + "search:score ?score; \n"
				+ "search:snippet ?snippet. " + "}";
		return queryString;
	}
	// 对life science的第一次查询
	public static HashMap<String, ArrayList<String>> lsExecute(String kw) {

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
					sMap.get(type).add(x);
				}

			}

			query = new keggConnection().getKeggConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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
					sMap.get(type).add(x);
				}

			}

			query = new chebiConnection().getChebiConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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
					sMap.get(type).add(x);
				}

			}

			query = new drugbankConnection().getDrugbankConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

	// 对cross domain的第一次查询
	public static HashMap<String, String> cmExecute(String kw) {

		HashMap<String, String> sMap = new HashMap<String, String>();
		String keyword = kw;
		String queryString = toQuery(kw);

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new jamendoConnection().getJamendoConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
					}

					for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
					}

					for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
				}

			}

			query = new linkedmdbConnection().getLinkedmdbConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
					}

					for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
				}

			}

			query = new geonamesConnection().getGeonamesConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
					}

					for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
				}

			}

			query = new swdfoodConnection().getSwdfoodConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result4 = query.evaluate();
			System.out.println("结果  :  " + result4.hasNext());
			while (result4.hasNext()) {

				BindingSet bindings = result4.next();
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
					}

					for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
				}

			}

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result5 = query.evaluate();
			System.out.println("结果  :  " + result5.hasNext());
			while (result5.hasNext()) {

				BindingSet bindings = result5.next();
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

					System.out.println("found match: ");
					for (Binding binding : bindings) {
						System.out.println(" " + binding.getName() + ": " + binding.getValue());
						// }

						// for (Binding binding : bindings) {
						if (binding.getName().equals("t")) {
							type = binding.getValue().toString();
						}
						if (binding.getName().equals("x")) {
							x = binding.getValue().toString();
						}
					}

					sMap.put(type, x);
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

	// chebi最终查询
	public static String chebiQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new chebiConnection().getChebiConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

	// drugbank
	public static String drugbankQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new drugbankConnection().getDrugbankConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

	// kegg
	public static String keggQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new keggConnection().getKeggConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
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

	/*
	 * //example public static String exQuery(String qs) { String lastResult="";
	 * 
	 * String queryString="PREFIX search: <"+LuceneSailSchema.NAMESPACE+"> \n"+qs;
	 * 
	 * System.out.println("Running query: \n"+queryString);
	 * 
	 * TupleQuery query; try {
	 * 
	 * query = new
	 * exConnection().getExConn().prepareTupleQuery(QueryLanguage.SPARQL,queryString
	 * ); TupleQueryResult result = query.evaluate();
	 * System.out.println("结果  :  "+result.hasNext());
	 * lastResult=lastResult+"result: "+result.hasNext()+"\n"; while
	 * (result.hasNext()){ BindingSet bindings = result.next();
	 * System.out.println("found match: "); for (Binding binding : bindings) {
	 * System.out.println(" "+binding.getName()+": "+binding.getValue());
	 * 
	 * lastResult=lastResult+" "+binding.getName().toString()+": "+binding.getValue(
	 * ).toString()+"\n"; } } exConnection.closeConn();
	 * 
	 * } catch (MalformedQueryException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (RepositoryException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } catch (QueryEvaluationException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * return lastResult; }
	 */

}
