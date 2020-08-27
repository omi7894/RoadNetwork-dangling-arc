package Polyline;

import java.util.LinkedList;
import java.util.Queue;


public class ShapeList {

	private Shape head;
	private Shape tail;
	private int size = 0;
	public double[] dist;
	public int numOfDist;

	public ShapeList() {
		head = null;
		tail = null;
		size = 0;
		dist = new double[10000];
		numOfDist = 0;
	}

	public void addLast(Shape shape) { //리스트에 추가할때, 맨뒤에 삽입하기
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

	public void addSorting(Shape shape) { //리스트에 추가할때, 정렬하며 삽입하기
		Shape cur = new Shape();
		Shape temp = new Shape();
		cur = head;
		if (size == 0 || cur.getBox().getMinX() > shape.getBox().getMinX()) {
			shape.next = head;
			head = shape;
			size++;
			if (head.next == null) {
				tail = head;
			}
		} else {
			for (int i = 0; i < size - 1; i++) {
				if (cur.next.getBox().getMinX() >= shape.getBox().getMinX()) {
					break;
				} else {
					cur = cur.next;
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
		Shape temp = new Shape();
		temp = head;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp;
	}

	public double getDistance(double x, double y, double x1, double y1) { //두 좌표의 거리

		return Math.sqrt(Math.pow(Math.abs(x1 - x), 2) + Math.pow(Math.abs(y1 - y), 2));

	}

	public void AddDegree(double dt) { //평범한 전체 탐색으로 degree늘리기

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();

		for (int i = 0; i < this.size() - 1; i++) {
			for (int j = i + 1; j < this.size(); j++) {
				SN1 = this.getShape(i).getStart();
				EN1 = this.getShape(i).getEnd();
				SN2 = this.getShape(j).getStart();
				EN2 = this.getShape(j).getEnd();

				//double dt=0;

				double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
				double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
				double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
				double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

				if (cal1 <= dt) {
					this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
					this.getShape(j).setDen(this.getShape(j).getDen() + 1);
				}
				if (cal2 <= dt) {
					this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
					this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
				}
				if (cal3 <= dt) {
					this.getShape(i).setDen(this.getShape(i).getDen() + 1);
					this.getShape(j).setDen(this.getShape(j).getDen() + 1);
				}
				if (cal4<=dt) {
					this.getShape(i).setDen(this.getShape(i).getDen() + 1);
					this.getShape(j).setDen(this.getShape(j).getDen() + 1);
				}
				
			}
		}
	}

	public void AddDegreeBySorting(double dt) { // 바운드박스정렬로 degree늘리기

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();
		//double dt = 0;

		for (int i = 0; i < this.size() - 1; i++) {

			for (int j = i + 1; j < this.size(); j++) {

				if (this.getShape(i).getBox().getMaxX() + dt >= this.getShape(j).getBox().getMinX()) {

					if (this.getShape(i).getBox().getMinY() <= this.getShape(j).getBox().getMaxY() + dt
							|| this.getShape(j).getBox().getMinY() <= this.getShape(i).getBox().getMaxY() + dt) {

						SN1 = this.getShape(i).getStart();
						EN1 = this.getShape(i).getEnd();
						SN2 = this.getShape(j).getStart();
						EN2 = this.getShape(j).getEnd();

						double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
						double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
						double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
						double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

						if (cal1 <= dt) {
							this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
							this.getShape(j).setDen(this.getShape(j).getDen() + 1);
						}
						if (cal2 <= dt) {
							this.getShape(i).setDsn(this.getShape(i).getDsn() + 1);
							this.getShape(j).setDsn(this.getShape(j).getDsn() + 1);
						}
						if (cal3 <= dt) {
							this.getShape(i).setDen(this.getShape(i).getDen() + 1);
							this.getShape(j).setDen(this.getShape(j).getDen() + 1);
						}
						if (cal4<=dt) {
							this.getShape(i).setDen(this.getShape(i).getDen() + 1);
							this.getShape(j).setDen(this.getShape(j).getDen() + 1);
						}

					}

				}

			}

		}

	}

	public BBox getLargestBox() { //지도 전체의 바운드 박스 구하기
		BBox Lbox = new BBox();

		double minX = this.getShape(0).getBox().getMinX();
		double minY = this.getShape(0).getBox().getMinY();
		double maxX = this.getShape(0).getBox().getMaxX();
		double maxY = this.getShape(0).getBox().getMaxY();

		for (int i = 1; i < this.size(); i++) {
			if (minX > this.getShape(i).getBox().getMinX()) {
				minX = this.getShape(i).getBox().getMinX();
			}
			if (minY > this.getShape(i).getBox().getMinY()) {
				minY = this.getShape(i).getBox().getMinY();
			}
			if (maxX < this.getShape(i).getBox().getMaxX()) {
				maxX = this.getShape(i).getBox().getMaxX();
			}
			if (maxY < this.getShape(i).getBox().getMaxY()) {
				maxY = this.getShape(i).getBox().getMaxY();
			}
		}

		Lbox.setMinX(minX);
		Lbox.setMaxX(maxX);
		Lbox.setMinY(minY);
		Lbox.setMaxY(maxY);

		return Lbox;
	}

	public void AddDegreeBySorting2() { // delta값 이하의 모든 연결된것의 갯수 구하기

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();
		double dt = 0;

		for (int i = 0; i < this.size() - 1; i++) {

			for (int j = i + 1; j < this.size(); j++) {

				if (this.getShape(i).getBox().getMaxX() + dt >= this.getShape(j).getBox().getMinX()) {

					if (this.getShape(i).getBox().getMinY() <= this.getShape(j).getBox().getMaxY() + dt
							|| this.getShape(j).getBox().getMinY() <= this.getShape(i).getBox().getMaxY() + dt) {

						SN1 = this.getShape(i).getStart();
						EN1 = this.getShape(i).getEnd();
						SN2 = this.getShape(j).getStart();
						EN2 = this.getShape(j).getEnd();

						double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
						double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
						double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
						double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

						if (cal1 <= dt) {
							this.dist[this.numOfDist] = cal1;
							numOfDist++;
						}
						if (cal2 <= dt) {
							this.dist[this.numOfDist] = cal2;
							numOfDist++;
						}
						if (cal3 <= dt) {
							this.dist[this.numOfDist] = cal3;
							numOfDist++;
						}
						if (cal4 <= dt) {
							this.dist[this.numOfDist] = cal4;
							numOfDist++;
						}

					}

				}

			}

		}

	}

	public void MakeLinkedList() { // 연결된 것들 리스트로 만들기

		LinkedList<Shape> L = new LinkedList<Shape>();

		for (int i = 0; i < this.size(); i++) {
			L.add(this.getShape(i));
		}

		ShapeList[] list = new ShapeList[20000];

		int numOflist = 0;

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();
		double dt = 0;

		for (int j = 0; j < L.size(); j++) {

			if (L.get(j).getListnum() == -1) {
				L.get(j).setListnum(numOflist);
				list[numOflist] = new ShapeList();
				Queue<Shape> q = new LinkedList<>();
				q.add(L.get(j));
				list[numOflist].addLast(L.get(j));

				if (j != L.size() - 1) {
					while (!q.isEmpty()) {

						Shape curShape = new Shape();
						curShape = q.peek();
						q.poll();
						for (int i = j + 1; i < L.size(); i++) {

							if (L.get(i).getListnum() == -1) {
								if (curShape.getBox().getMaxX() + dt >= L.get(i).getBox().getMinX()) {

									if (curShape.getBox().getMinY() <= L.get(i).getBox().getMaxY() + dt
											|| L.get(i).getBox().getMinY() <= curShape.getBox().getMaxY() + dt) {

										SN1 = curShape.getStart();
										EN1 = curShape.getEnd();
										SN2 = L.get(i).getStart();
										EN2 = L.get(i).getEnd();

										double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
										double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
										double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
										double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

										if (cal1 <= dt || cal2 <= dt || cal3 <= dt || cal4 <= dt) {
											q.add(L.get(i));
											L.get(i).setListnum(numOflist);
											list[numOflist].addLast(L.get(i));
										}
									}
								}
							}
						}

					} // while문
				}

				numOflist++;
			}
		}
		System.out.println("delta : "+dt);
		int  i=0;
		for (i = 0; i < numOflist; i++) {
			System.out.println(i+1+"번 리스트  : "+list[i].size()+"개");
		}
		System.out.println("총"+i+"개의 리스트");

	}

	public void MinDistance() { // 각 polyline의 가장 가까운 노드와의 거리 저장

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();

		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				if (i != j) {

					SN1 = this.getShape(i).getStart();
					EN1 = this.getShape(i).getEnd();
					SN2 = this.getShape(j).getStart();
					EN2 = this.getShape(j).getEnd();

					double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
					double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
					double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
					double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());

					if (this.getShape(i).getSNminDist() > cal1 || this.getShape(i).getSNminDist() == -1) {
						this.getShape(i).setSNminDist(cal1);
					}
					if (this.getShape(i).getSNminDist() > cal2 || this.getShape(i).getSNminDist() == -1) {
						this.getShape(i).setSNminDist(cal2);
					}
					if (this.getShape(i).getENminDist() > cal3 || this.getShape(i).getENminDist() == -1) {
						this.getShape(i).setENminDist(cal3);
					}
					if (this.getShape(i).getENminDist() > cal4 || this.getShape(i).getENminDist() == -1) {
						this.getShape(i).setENminDist(cal4);
					}
				}

			}
		}
		
		int[] arr = new int[13];
		for (int i = 0; i < this.size(); i++) {

			for (int j = 0; j < 13; j++) {
				if (j <= this.getShape(i).getSNminDist() && this.getShape(i).getSNminDist() < j + 1) {
					arr[j]++;
				}
				if (j <= this.getShape(i).getENminDist() && this.getShape(i).getENminDist() < j + 1) {
					arr[j]++;
				}

			}

		}
		int total=0;
		for(int i=0;i<13;i++) {
			System.out.println("거리가"+i+":"+arr[i]+"개");
		}

	}
	
	
}


