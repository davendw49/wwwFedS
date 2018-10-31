package wwwFedS.CrossDomain.travel.DataStructure;

public class Vertex {

	/*
	 * string path; int value; bool visit; Dis() { visit = false; value = 0; path =
	 * ""; }
	 */
	private String path;
	private int value;
	private boolean visit;

	public Vertex() {
		// TODO Auto-generated constructor stub
		this.path = "";
		this.value = Integer.MAX_VALUE;
		this.visit = false;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isVisit() {
		return visit;
	}

	public void setVisit(boolean visit) {
		this.visit = visit;
	}

	public Vertex(String path, int value, boolean visit) {
		super();
		this.path = path;
		this.value = value;
		this.visit = visit;
	}

}