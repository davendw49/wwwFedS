package wwwFedS.LifeScience.util;

/**
 * test the utilities construction
 * 
 * @author daven update at 2018-6-18
 */
public class TestUtils {

	public static void main(String[] args) throws Exception {
		InitialHelper iHelper = new InitialHelper();
		iHelper.init();
		System.out.println(iHelper.getClassN("<http://www.w3.org/1999/02/22-rdf-syntax-ns#Property>"));
		System.out.println(iHelper.getDN(1));

		int sum=0;
		for (int i = 0; i < 404; i++) {
			for (int j = 0; j < 404; j++) {
				if (iHelper.graph[i][j] == 1)
					sum++;
				System.out.print(iHelper.graph[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("ok " + (double) sum / (404 * 404));

		System.out.println(iHelper.edgenum + "  " + iHelper.nodenum);

	}

}