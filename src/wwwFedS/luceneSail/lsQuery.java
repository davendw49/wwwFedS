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

import wwwFedS.run.ResultList;

public class lsQuery {

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
				+ "SELECT DISTINCT ?t1 ?x WHERE { \n" + "?x " + property + " ?y. \n"
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t1. \n" + "}";
		return queryString;
	}

	public static String classQuery(String className) {
		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + "SELECT DISTINCT ?x WHERE { \n"
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " + className + ". \n" + "}";
		return queryString;
	}

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
					if (!sMap.get(type).contains(x))
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
					if (!sMap.get(type).contains(x))
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
					if (!sMap.get(type).contains(x))
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
	public static ResultList dbpediaQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		ResultList curRes = new ResultList();

		TupleQuery query;
		try {

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());

			int count = 0;
			while (result.hasNext()) {
				BindingSet bindings = result.next();

				ArrayList<String> resList = new ArrayList<String>();
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());
					if (count == 0) {
						curRes.variables.add(binding.getName().toString());
					}
					resList.add(binding.getValue().toString());
				}
				curRes.mappings.add(resList);
				count++;
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

		return curRes;
	}

	// chebi最终查询
	public static ResultList chebiQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		ResultList curRes = new ResultList();

		TupleQuery query;
		try {

			query = new chebiConnection().getChebiConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());

			int count = 0;
			while (result.hasNext()) {
				BindingSet bindings = result.next();

				ArrayList<String> resList = new ArrayList<String>();
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());
					if (count == 0) {
						curRes.variables.add(binding.getName().toString());
					}
					resList.add(binding.getValue().toString());
				}
				curRes.mappings.add(resList);
				count++;
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

		return curRes;
	}

	// drugbank
	public static ResultList drugbankQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		ResultList curRes = new ResultList();

		TupleQuery query;
		try {

			query = new drugbankConnection().getDrugbankConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());

			int count = 0;
			while (result.hasNext()) {
				BindingSet bindings = result.next();

				ArrayList<String> resList = new ArrayList<String>();
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());
					if (count == 0) {
						curRes.variables.add(binding.getName().toString());
					}
					resList.add(binding.getValue().toString());
				}
				curRes.mappings.add(resList);
				count++;
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

		return curRes;
	}

	// kegg
	public static ResultList keggQuery(String qs) {
		String lastResult = "";

		String queryString = "PREFIX search: <" + LuceneSailSchema.NAMESPACE + "> \n" + qs;

		System.out.println("Running query: \n" + queryString);

		ResultList curRes = new ResultList();

		TupleQuery query;
		try {

			query = new keggConnection().getKeggConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());

			int count = 0;
			while (result.hasNext()) {
				BindingSet bindings = result.next();

				ArrayList<String> resList = new ArrayList<String>();
				for (Binding binding : bindings) {
					// System.out.println(" "+binding.getName()+": "+binding.getValue());
					if (count == 0) {
						curRes.variables.add(binding.getName().toString());
					}
					resList.add(binding.getValue().toString());
				}
				curRes.mappings.add(resList);
				count++;
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

		return curRes;
	}

	public static HashMap<String, ArrayList<String>> lsProperty(String pro) {
		HashMap<String, ArrayList<String>> sMap = new HashMap<String, ArrayList<String>>();
		String property = pro;
		String queryString = propertyQuery(property);

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			while (result.hasNext()) {

				BindingSet bindings = result.next();
				String t1 = "";
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("t1")) {
						t1 = "<" + binding.getValue().toString() + ">";
					}
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(t1)) {
					sMap.put(t1, new ArrayList<>());
				}
				if (!sMap.get(t1).contains(x))
					sMap.get(t1).add(x);
			}

			query = new keggConnection().getKeggConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result1 = query.evaluate();
			System.out.println("结果  :  " + result1.hasNext());
			while (result1.hasNext()) {

				BindingSet bindings = result1.next();
				String t1 = "";
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("t1")) {
						t1 = "<" + binding.getValue().toString() + ">";
					}
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(t1)) {
					sMap.put(t1, new ArrayList<>());
				}
				if (!sMap.get(t1).contains(x))
					sMap.get(t1).add(x);
			}

			query = new chebiConnection().getChebiConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result2 = query.evaluate();
			System.out.println("结果  :  " + result2.hasNext());
			while (result2.hasNext()) {
				BindingSet bindings = result2.next();
				String t1 = "";
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("t1")) {
						t1 = "<" + binding.getValue().toString() + ">";
					}
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(t1)) {
					sMap.put(t1, new ArrayList<>());
				}
				if (!sMap.get(t1).contains(x))
					sMap.get(t1).add(x);
			}

			query = new drugbankConnection().getDrugbankConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result3 = query.evaluate();
			System.out.println("结果  :  " + result3.hasNext());
			while (result3.hasNext()) {
				BindingSet bindings = result3.next();
				String t1 = "";
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("t1")) {
						t1 = "<" + binding.getValue().toString() + ">";
					}
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(t1)) {
					sMap.put(t1, new ArrayList<>());
				}
				if (!sMap.get(t1).contains(x))
					sMap.get(t1).add(x);
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

	public static HashMap<String, ArrayList<String>> lsClass(String cName) {
		HashMap<String, ArrayList<String>> sMap = new HashMap<String, ArrayList<String>>();
		String className = cName;
		String queryString = classQuery(className);

		System.out.println("Running query: \n" + queryString);

		TupleQuery query;
		try {

			query = new dbpediaConnection().getDbpediaConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = query.evaluate();
			System.out.println("结果  :  " + result.hasNext());
			while (result.hasNext()) {
				BindingSet bindings = result.next();
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(cName)) {
					sMap.put(cName, new ArrayList<>());
				}
				if (!sMap.get(cName).contains(x))
					sMap.get(cName).add(x);
			}

			query = new keggConnection().getKeggConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result1 = query.evaluate();
			System.out.println("结果  :  " + result1.hasNext());
			while (result1.hasNext()) {
				BindingSet bindings = result1.next();
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(cName)) {
					sMap.put(cName, new ArrayList<>());
				}
				if (!sMap.get(cName).contains(x))
					sMap.get(cName).add(x);
			}

			query = new chebiConnection().getChebiConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result2 = query.evaluate();
			System.out.println("结果  :  " + result2.hasNext());
			while (result2.hasNext()) {
				BindingSet bindings = result2.next();
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(cName)) {
					sMap.put(cName, new ArrayList<>());
				}
				if (!sMap.get(cName).contains(x))
					sMap.get(cName).add(x);
			}

			query = new drugbankConnection().getDrugbankConn().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result3 = query.evaluate();
			System.out.println("结果  :  " + result3.hasNext());
			while (result3.hasNext()) {
				BindingSet bindings = result3.next();
				String x = "";
				for (Binding binding : bindings) {
					if (binding.getName().contains("x")) {
						x = "<" + binding.getValue().toString() + ">";
					}
				}
				if (!sMap.containsKey(cName)) {
					sMap.put(cName, new ArrayList<>());
				}
				if (!sMap.get(cName).contains(x))
					sMap.get(cName).add(x);
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

}
