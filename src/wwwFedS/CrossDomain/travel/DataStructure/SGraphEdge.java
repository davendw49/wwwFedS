package wwwFedS.CrossDomain.travel.DataStructure;
/**
 * store the path
 * @author daven
 * 2018-5-17
 */
public class SGraphEdge {

	public int first;
	public int second;

	public SGraphEdge() {
		// TODO Auto-generated constructor stub
	}

	public SGraphEdge(int a, int b) {
		first = a;
		second = b;
	}

	@Override
	public String toString() {
		return "SGraphEdge [first=" + first + ", second=" + second + "]";
	}
}
