package Polyline;

public class Part {

	private int id;
	private Node start;
	private Node end;
	private int ShpaeID;

	PointList list;
	Part next;

	public Part() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Part(int id) {
		super();
		this.id = id;
		this.start = null;
		this.end = null;
		ShpaeID = -1;
		this.list = null;
		this.next = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public int getShpaeID() {
		return ShpaeID;
	}

	public void setShpaeID(int shpaeID) {
		this.ShpaeID = shpaeID;
	}

	public PointList getList() {
		return list;
	}

	public void setList(PointList list) {
		this.list = list;
	}

	public Part getNext() {
		return next;
	}

	public void setNext(Part next) {
		this.next = next;
	}

}
