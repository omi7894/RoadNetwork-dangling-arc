package Polyline;
public class Shape {

	private int id;
	private Node start;
	private Node end;
	private BBox box;
	PartList list;
	Shape next;

	private int dsn;
	private int den;

	private TreeNode sntree;
	private TreeNode entree;
	
	private int listnum;
	private double SNminDist;
	private double ENminDist;
	
	public Shape() {
		super();
		// TODO Auto-generated constructor stub
		this.id = -1;
		this.start = null;
		this.end = null;
		this.list = null;
		this.box = null;
		this.next = null;
		this.dsn = 1;
		this.den = 1;
		this.sntree=null;
		this.entree=null;
		this.listnum = -1; 
		this.SNminDist = -1;
		this.ENminDist = -1;
	}

	public Shape(int id) {
		this.id = id;
		this.start = null;
		this.end = null;
		this.list = null;
		this.box = null;
		this.next = null;
		this.dsn = 1;
		this.den = 1;
		this.sntree=null;
		this.entree=null;
		this.listnum = -1; 
		this.SNminDist = -1;
		this.ENminDist = -1;
	}
	

	public double getSNminDist() {
		return SNminDist;
	}

	public void setSNminDist(double sNminDist) {
		SNminDist = sNminDist;
	}

	public double getENminDist() {
		return ENminDist;
	}

	public void setENminDist(double eNminDist) {
		ENminDist = eNminDist;
	}

	public int getListnum() {
		return listnum;
	}

	public void setListnum(int listnum) {
		this.listnum = listnum;
	}

	public TreeNode getSntree() {
		return sntree;
	}

	public void setSntree(TreeNode sntree) {
		this.sntree = sntree;
	}

	public TreeNode getEntree() {
		return entree;
	}

	public void setEntree(TreeNode entree) {
		this.entree = entree;
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
