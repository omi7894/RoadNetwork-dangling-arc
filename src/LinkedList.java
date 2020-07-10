public class LinkedList {
	private Node head;
	private Node tail;
	private int size =0;
	private class Node{
		private Object x;
		private Object y;
		private Node next;
		public Node(Object input_x, Object input_y) {
			this.x = input_x;
			this.y = input_y;
			this.next = null;
		}
		public String toString() {
			return "("+String.valueOf(this.x)+","+String.valueOf(this.y)+")";
		}		
	}
	public void addFirst(Object input_x, Object input_y) {
		Node newNode = new Node(input_x, input_y);
		newNode.next = head;
		head = newNode;
		size++;
		if(head.next==null) {tail = head;}
	}
	public Node node(int index) {
		Node h=head;
		for(int i=0;i<index;i++) {
			h = h.next;
		}
		return h;
	}
	public void add(int k, Object input_x, Object input_y) {
		if(k==0) {
			addFirst(input_x, input_y);
		}else {
			Node temp1 = node(k-1);
			Node temp2 = temp1.next;
			Node newNode = new Node(input_x, input_y);
			temp1.next = newNode;
			newNode.next = temp2;
			size++;
			if(newNode.next==null) {
				tail = newNode;
			}
		}
	}
	public void addLast(Object input_x, Object input_y) {
		Node newNode = new Node(input_x, input_y);
		if(size==0) {
			addFirst(input_x, input_y);
		}else {
			tail.next = newNode;
			tail = newNode;
			size++;
		}
	}
	public void removeFirst() {
		Node temp = head;
		head = head.next;
		temp = null;
		size--;
	}
	public void remove(int k) {
		if(k==0) {removeFirst();}
		Node temp = node(k-1);
		Node todoDeleted = temp.next;
		temp.next = temp.next.next;
		if(todoDeleted ==tail) {
			tail = temp;
		}
		todoDeleted = null;
		size--;		
	}
	public void removeLast() {
		remove(size-1);
	}
	public String toString() {
		if(head==null) {return "[]";}
		Node temp = head;
		String str = "";
		while(temp.next != null) {
			str = str + "("+temp.x+", "+temp.y+")"+"\n";
			temp = temp.next;
		}
		str = str + "("+temp.x+", "+temp.y+")"+"\n";
		return str;		
	}
	public int size() {
		return size;
	}
	public Object getX(int k) {
		Node temp = node(k);
		return temp.x;
	}
	public Object getY(int k) {
		Node temp = node(k);
		return temp.y;
	}
}


