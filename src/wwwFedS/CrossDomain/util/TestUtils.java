package wwwFedS.CrossDomain.util;

/**
 * test the utilities construction
 * 
 * @author daven update at 2018-6-18
 */
public class TestUtils {

	public static void main(String[] args) throws Exception {
		InitialHelper iHelper = new InitialHelper();
		iHelper.init();
		System.out.println(iHelper.getClassN("<http://dbpedia.org/ontology/SportsLeague>"));
		System.out.println(iHelper.getDN(1));
		/*
		 * PrintStream ps = new PrintStream("z://matrix.txt"); PrintStream out =
		 * System.out; System.setOut(ps); for (int i = 0; i < 404; i++) { for (int j =
		 * 0; j < 404; j++) { if (iHelper.graph[i][j] == 1) sum++;
		 * System.out.print(iHelper.graph[i][j] + " "); } System.out.println(); }
		 * System.setOut(out); System.out.println("ok " + (double) sum / (404 * 404));
		 */
		System.out.println(iHelper.edgenum + "  " + iHelper.nodenum);

	}

}