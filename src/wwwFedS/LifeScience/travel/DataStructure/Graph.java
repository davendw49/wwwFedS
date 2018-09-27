package wwwFedS.LifeScience.travel.DataStructure;

import java.util.ArrayList;
import wwwFedS.LifeScience.travel.DataStructure.SBrunch;

public class Graph {

	private int vexnum;
	private int edgenum;
	private int arc[][] = new int[404][404];
	private ArrayList<Vertex> dist = new ArrayList<>();

	/**
	 * //判断我们每次输入的的边的信息是否合法,顶点从0开始编号 bool check_edge_value(int start, int end, int
	 * weight); //创建图 void createGraph(); //打印邻接矩阵 void print(); //求最短路径 void
	 * Dijkstra(int begin); //打印最短路径 void print_path(int);
	 */

	public Graph() {
		// TODO Auto-generated constructor stub
		this.vexnum = 0;
		this.edgenum = 0;
	}

	public Graph(int vexnum, int edgenum) {
		this.vexnum = vexnum;
		this.edgenum = edgenum;
	}

	public int getVexnum() {
		return vexnum;
	}

	public void setVexnum(int vexnum) {
		this.vexnum = vexnum;
	}

	public int getEdgenum() {
		return edgenum;
	}

	public void setEdgenum(int edgenum) {
		this.edgenum = edgenum;
	}

	public int[][] getArc() {
		return arc;
	}

	public void setArc(int[][] arc) {
		this.arc = arc;
	}

	public ArrayList<Vertex> getDist() {
		return dist;
	}

	public void setDist(ArrayList<Vertex> dist) {
		this.dist = dist;
	}

	public Graph(int vexnum, int edgenum, int[][] arc) {
		super();
		this.vexnum = vexnum;
		this.edgenum = edgenum;
		this.arc = arc;
		this.dist = new ArrayList<>();
	}

	public boolean check_edge_value(int start, int end, int weight) {

		if (start < 0 || end < 0 || start > vexnum || end > vexnum || weight < 0) {
			return false;
		}
		return true;
	}

	// 创建图
	public void createGraph(int graph[][], int edgenum) {
		arc = graph;
		this.edgenum = edgenum;
	}

	// 打印邻接矩阵
	public void print() {
		for (int i = 0; i < vexnum; i++) {
			for (int j = 0; j < vexnum; j++) {
				if (arc[i][j] == Integer.MAX_VALUE) {
					System.out.print("∞" + " ");
				} else {
					System.out.print(arc[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	// 求最短路径
	public void Dijkstra(int begin) {

		// 初始化
		for (int i = 0; i < vexnum; i++) {
			// 设置当前的路径
			Vertex vertex = new Vertex();
			vertex.setPath("v" + begin + "-->v" + (i));
			vertex.setValue(arc[begin][i]);
			dist.add(vertex);
		}
		// 设置起点的到起点的路径为0
		dist.get(begin).setValue(0);
		dist.get(begin).setVisit(true);

		int count = 1;
		// 计算剩余的顶点的最短路径（剩余this->vexnum-1个顶点）
		while (count != vexnum) {
			// temp用于保存当前dis数组中最小的那个下标
			// min记录的当前的最小值
			int temp = 0;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < vexnum; i++) {
				if (!dist.get(i).isVisit() && dist.get(i).getValue() < min) {
					min = dist.get(i).getValue();
					temp = i;
				}
			}

			// 把temp对应的顶点加入到已经找到的最短路径的集合中
			dist.get(temp).setVisit(true);
			count++;
			for (int i = 0; i < vexnum; i++) {
				// 注意这里的条件arc[temp][i]!=INT_MAX必须加，不然会出现溢出，从而造成程序异常
				if (!dist.get(i).isVisit() && arc[temp][i] != Integer.MAX_VALUE
						&& (dist.get(temp).getValue() + arc[temp][i]) < dist.get(i).getValue()) {
					// 如果新得到的边可以影响其他为访问的顶点，那就就更新它的最短路径和长度
					dist.get(i).setValue(dist.get(temp).getValue() + arc[temp][i]);
					dist.get(i).setPath(dist.get(temp).getPath() + "-->v" + (i));
				}
			}
		}

	}

	// 打印最短路径
	public void print_path(int begin) {

		System.out.println(begin + "node's shortest path");
		for (int i = 0; i != vexnum; i++) {
			if (dist.get(i).getValue() != Integer.MAX_VALUE)
				System.out.println(dist.get(i).getPath() + " = " + dist.get(i).getValue());
			else {
				System.out.println(dist.get(i).getPath() + "是无最短路径的");
			}
		}
	}

	// 求最短路径
	public void Dijkstra_print(int begin, int end) {

		// 初始化
		for (int i = 0; i < vexnum; i++) {
			// 设置当前的路径
			Vertex vertex = new Vertex();
			vertex.setPath(begin + " " + (i));
			vertex.setValue(arc[begin][i]);
			dist.add(vertex);
		}
		// 设置起点的到起点的路径为0
		dist.get(begin).setValue(0);
		dist.get(begin).setVisit(true);

		int count = 1;
		// 计算剩余的顶点的最短路径（剩余this->vexnum-1个顶点）
		while (count != vexnum) {
			// temp用于保存当前dis数组中最小的那个下标
			// min记录的当前的最小值
			int temp = 0;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < vexnum; i++) {
				if (!dist.get(i).isVisit() && dist.get(i).getValue() < min) {
					min = dist.get(i).getValue();
					temp = i;
				}
			}

			// 把temp对应的顶点加入到已经找到的最短路径的集合中
			dist.get(temp).setVisit(true);
			count++;
			for (int i = 0; i < vexnum; i++) {
				// 注意这里的条件arc[temp][i]!=INT_MAX必须加，不然会出现溢出，从而造成程序异常
				if (!dist.get(i).isVisit() && arc[temp][i] != Integer.MAX_VALUE
						&& (dist.get(temp).getValue() + arc[temp][i]) < dist.get(i).getValue()) {
					// 如果新得到的边可以影响其他为访问的顶点，那就就更新它的最短路径和长度
					dist.get(i).setValue(dist.get(temp).getValue() + arc[temp][i]);
					dist.get(i).setPath(dist.get(temp).getPath() + " " + (i));
				}
			}
		}

		// System.out.println(begin + "node's shortest path");

		if (dist.get(end).getValue() != Integer.MAX_VALUE)
			System.out.println(dist.get(end).getPath() + " = " + dist.get(end).getValue());
		else {
			System.out.println(dist.get(end).getPath() + "是无最短路径的");
		}

	}

	public int StrucQuery(ArrayList<Integer> Keywords, int n) {
		int min = Integer.MAX_VALUE;
		int flag = 0;
		int sum;
		for (int i = 0; i < n - 1; i++) {
			sum = 0;
			for (Integer kw : Keywords) {
				sum += arc[i][kw];
			}
			if (sum < min) {
				min = sum;
				flag = i;
			}
			// System.out.println(sum);
		}
		return flag;
	}

	public SBrunch Dijkstra_result(int begin, int end) {
		SBrunch sBrunch = new SBrunch();
		// 初始化
		for (int i = 0; i < vexnum; i++) {
			// 设置当前的路径
			Vertex vertex = new Vertex();
			vertex.setPath(begin + " " + (i));
			vertex.setValue(arc[begin][i]);
			dist.add(vertex);
		}
		// 设置起点的到起点的路径为0
		dist.get(begin).setValue(0);
		dist.get(begin).setVisit(true);

		int count = 1;
		// 计算剩余的顶点的最短路径（剩余this->vexnum-1个顶点）
		while (count != vexnum) {
			// temp用于保存当前dis数组中最小的那个下标
			// min记录的当前的最小值
			int temp = 0;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < vexnum; i++) {
				if (!dist.get(i).isVisit() && dist.get(i).getValue() < min) {
					min = dist.get(i).getValue();
					temp = i;
				}
			}

			// 把temp对应的顶点加入到已经找到的最短路径的集合中
			dist.get(temp).setVisit(true);
			count++;
			for (int i = 0; i < vexnum; i++) {
				// 注意这里的条件arc[temp][i]!=INT_MAX必须加，不然会出现溢出，从而造成程序异常
				if (!dist.get(i).isVisit() && arc[temp][i] != Integer.MAX_VALUE
						&& (dist.get(temp).getValue() + arc[temp][i]) < dist.get(i).getValue()) {
					// 如果新得到的边可以影响其他为访问的顶点，那就就更新它的最短路径和长度
					dist.get(i).setValue(dist.get(temp).getValue() + arc[temp][i]);
					dist.get(i).setPath(dist.get(temp).getPath() + " " + (i));
				}
			}
		}

		// System.out.println(begin + "node's shortest path");

		if (dist.get(end).getValue() != Integer.MAX_VALUE) {
			// System.out.println(dist.get(end).getPath() + " = " +
			// dist.get(end).getValue());
			sBrunch.road = dist.get(end).getPath();
			sBrunch.brunchValue = dist.get(end).getValue();
		} else {
			// System.out.println(dist.get(end).getPath() + "是无最短路径的");
			sBrunch.road = "not";
			sBrunch.brunchValue = Integer.MAX_VALUE;
		}
		// System.out.println(sBrunch.brunchValue + " " + sBrunch.road);
		return sBrunch;

	}
}