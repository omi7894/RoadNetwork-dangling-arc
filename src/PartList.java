public class PartList {
	
	private Part head;
	private Part tail;
	private int size =0;
	
	public void addLast(Part part) {
		Part newPart = new Part();
		newPart = part;
		
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
	public Part getPart(int index) {
		Part temp = head;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp;
	}

}


