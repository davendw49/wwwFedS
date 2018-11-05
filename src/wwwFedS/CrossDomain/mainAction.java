package wwwFedS.CrossDomain;

import java.util.ArrayList;
import java.util.HashMap;

import wwwFedS.CrossDomain.parse.ParsingUnit;
import wwwFedS.CrossDomain.query.SPARQL;
import wwwFedS.CrossDomain.travel.traverseAction;
import wwwFedS.CrossDomain.util.InitialHelper;


public class mainAction {

	public static ArrayList<HashMap<Integer, ArrayList<String>>> doAction(
			HashMap<String, HashMap<String, ArrayList<String>>> query) throws Exception {

		ParsingUnit pUnit = new ParsingUnit();
		InitialHelper iHelper = new InitialHelper();
		traverseAction tAction = new traverseAction();
		SPARQL sparql = new SPARQL();
		iHelper.init();

		// manufactured query input which can be seen over this method.

		pUnit.Parse(query, iHelper);
		tAction.start(pUnit, iHelper);
		System.out.println(pUnit.toString());
		sparql.generateExeclist(tAction.queryArray, tAction.plusArray, "unionMode");// "filterMode"

		/*
		 * for (int i = 0; i < 4; i++) {
		 * System.out.println("this is a query for database" + i + ": ");
		 * ArrayList<String> tmp = sparql.execList.get(i); for (String string : tmp) {
		 * System.out.println(string); } }
		 */

		return sparql.execList;

	}
}