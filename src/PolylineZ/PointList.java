package PolylineZ;
public class PointList {

	private Point head;
	private Point tail;
	private int size = 0;

	public void addLast(Point point) {
		Point newPoint = new Point();
		newPoint = point;
		if (size == 0) {
			newPoint.next = head;
			head = newPoint;
			size++;
			if (head.next == null) {
				tail = head;
			}
		} else {
			tail.next = newPoint;
			tail = newPoint;
			size++;
		}
	}

	public String toString() {
		if (head == null) {
			return "[]";
		}
		Point temp = head;
		String str = "";
		while (temp.next != null) {
			str = str + "(" + temp.getX() + ", " + temp.getY() + ")" + "\n";
			temp = temp.next;
		}
		str = str + "(" + temp.getX() + ", " + temp.getY() + ")" + "\n";
		return str;
	}

	public int size() {
		return size;
	}

	public Point getPoint(int index) {
		Point temp = head;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp;
	}

}