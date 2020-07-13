
public class PartList {
	
	private Part head;
	private Part tail;
	private int size =0;
	
	public void addLast(int id) {
		Part newPart = new Part(id);
		if(size==0) {			
			newPart.next = head;
			head = newPart;
			size++;
			if(head.next==null) {tail = head;}
		}
		else {
			tail.next = newPart;
			tail = newPart;
			size++;
		}
	}

	public int size() {
		return size;
	}
}


