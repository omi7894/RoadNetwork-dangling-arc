

public class Point {
	

	private double x;
	private double y;
	Point next;

	
	
	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.next = null;
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

	public Point getNext() {
		return next;
	}

	public void setNext(Point next) {
		this.next = next;
	}


}
