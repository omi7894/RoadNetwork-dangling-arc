public class Shape {

	private int id;
	private Node start;
	private Node end;
	private BBox box;
	PartList list;
	Shape next;

	private int dsn;
	private int den;

	public Shape() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shape(int id) {
		super();
		this.id = id;
		this.start = null;
		this.end = null;
		this.list = null;
		this.box = null;
		this.next = null;
		this.dsn = 1;
		this.den = 1;
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

	public PartList getList() {
		return list;
	}

	public void setList(PartList list) {
		this.list = list;
	}

	public BBox getBox() {
		return box;
	}

	public void setBox(BBox box) {
		this.box = box;
	}

	public Shape getNext() {
		return next;
	}

	public void setNext(Shape next) {
		this.next = next;
	}

	public int getDsn() {
		return dsn;
	}

	public void setDsn(int dsn) {
		this.dsn = dsn;
	}

	public int getDen() {
		return den;
	}

	public void setDen(int den) {
		this.den = den;
	}

}
