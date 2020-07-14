
public class Node {
	
	private int id;
	private double x;
	private double y;
	
	private int PartID;
	private int ShpaeID;


	public Node() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Node(int id, double x, double y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		PartID = -1;
		ShpaeID = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getPartID() {
		return PartID;
	}

	public void setPartID(int partID) {
		this.PartID = partID;
	}

	public int getShpaeID() {
		return ShpaeID;
	}

	public void setShpaeID(int shpaeID) {
		this.ShpaeID = shpaeID;
	}

}
