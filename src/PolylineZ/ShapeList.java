package PolylineZ;
public class ShapeList {

	private Shape head;
	private Shape tail;
	private int size = 0;

	public void addLast(Shape shape) {
		Shape newShape = new Shape();
		newShape = shape;

		if (size == 0) {
			newShape.next = head;
			head = newShape;
			size++;
			if (head.next == null) {
				tail = head;
			}
		} else {
			tail.next = newShape;
			tail = newShape;
			size++;
		}
	}

	public void addSorting(Shape shape) {
		Shape cur = new Shape();
		Shape temp = new Shape();
		cur = head;
		if (size == 0||cur.getBox().getMinX()>shape.getBox().getMinX()) {
			shape.next = head;
			head = shape;
			size++;
			if (head.next == null) {
				tail = head;
			}
		} 
		else {
			for(int i=0; i<size-1; i++) {
				if (cur.next.getBox().getMinX() >= shape.getBox().getMinX()) {
					break;
				} else {
					cur=cur.next;
				}
			}
			temp = cur.next;
			cur.next = shape;
			shape.next = temp;
			size++;
			if (shape.next == null) {
				tail = shape;
			}

		}

	}

	public int size() {
		return size;
	}

	public Shape getShape(int index) {
		Shape temp = head;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp;
	}

	public double getDistance(double x, double y, double x1, double y1) {

		return Math.sqrt(Math.pow(Math.abs(x1 - x), 2) + Math.pow(Math.abs(y1 - y), 2));

	}

	public void AddDegree() {

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();

		for (int i = 0; i < this.size()-1; i++) {
			for (int j = i+1; j < this.size(); j++) {
				SN1 = this.getShape(i).getStart();
				EN1 = this.getShape(i).getEnd();
				SN2 = this.getShape(j).getStart();
				EN2 = this.getShape(j).getEnd();

				double dt = 0.1;

				double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
				double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
				double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
				double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

				int con = 0;
				if (cal1 <= dt) {
					con++;
				}
				if (cal2 <= dt) {
					con++;
				}
				if (cal3 <= dt) {
					con++;
				}
				if (cal4 <= dt) {
					con++;
				}

				if (con == 1) {
					if (cal1 <= dt) {
						this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
						this.getShape(j).setDen(this.getShape(j).getDen() + 1);
					} else if (cal2 <= dt) {
						this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
						this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
					} else if (cal3 <= dt) {
						this.getShape(i).setDen(this.getShape(i).getDen() + 1);
						this.getShape(j).setDen(this.getShape(j).getDen() + 1);
					} else {
						this.getShape(i).setDen(this.getShape(i).getDen() + 1);
						this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
					}

				}

			}
		}

	}
public void AddDegreeBySorting() {
		
		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();
		
		for(int i=0;i<this.size();i++) {
			
			for (int j = i+1; j < this.size()-1; j++) {
				
				if(this.getShape(i).getBox().getMaxX()>=this.getShape(j).getBox().getMinX()) {
					
					if(this.getShape(i).getBox().getMinY()<=this.getShape(j).getBox().getMaxY()||
							this.getShape(j).getBox().getMinY()<=this.getShape(i).getBox().getMaxY()) {
						
						SN1 = this.getShape(i).getStart();
						EN1 = this.getShape(i).getEnd();
						SN2 = this.getShape(j).getStart();
						EN2 = this.getShape(j).getEnd();

						double dt = 0.1;

						double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
						double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
						double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
						double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

						int con = 0;
						if (cal1 <= dt) {
							con++;
						}
						if (cal2 <= dt) {
							con++;
						}
						if (cal3 <= dt) {
							con++;
						}
						if (cal4 <= dt) {
							con++;
						}

						if (con == 1) {
							if (cal1 <= dt) {
								this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
								this.getShape(j).setDen(this.getShape(j).getDen() + 1);
							} else if (cal2 <= dt) {
								this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
								this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
							} else if (cal3 <= dt) {
								this.getShape(i).setDen(this.getShape(i).getDen() + 1);
								this.getShape(j).setDen(this.getShape(j).getDen() + 1);
							} else {
								this.getShape(i).setDen(this.getShape(i).getDen() + 1);
								this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
							}

						}
						
						
					}else {
						break;
					}
										
				}else {
					break;
				}
				
			}
			
		}
		
		
	}
	
	public BBox getLargestBox() {
		BBox Lbox = new BBox();
		
		double minX=this.getShape(0).getBox().getMinX();
		double minY=this.getShape(0).getBox().getMinY();
		double maxX=this.getShape(0).getBox().getMaxX();
		double maxY=this.getShape(0).getBox().getMaxY();
		
		for(int i=1; i<this.size();i++) {
			if(minX>this.getShape(i).getBox().getMinX()) {
				minX=this.getShape(i).getBox().getMinX();
			}
			if(minY>this.getShape(i).getBox().getMinY()) {
				minY=this.getShape(i).getBox().getMinY();
			}
			if(maxX<this.getShape(i).getBox().getMaxX()) {
				maxX=this.getShape(i).getBox().getMaxX();
			}
			if(maxY<this.getShape(i).getBox().getMaxY()) {
				maxY=this.getShape(i).getBox().getMaxY();
			}
		}
		
		Lbox.setMinX(minX);
		Lbox.setMaxX(maxX);
		Lbox.setMinY(minY);
		Lbox.setMaxY(maxY);
				
		return Lbox;
	}

}
