import java.io.FileInputStream;
import java.io.IOException;

import org.nocrala.tools.gis.data.esri.shapefile.ShapeFileReader;
import org.nocrala.tools.gis.data.esri.shapefile.ValidationPreferences;
import org.nocrala.tools.gis.data.esri.shapefile.exception.InvalidShapeFileException;
import org.nocrala.tools.gis.data.esri.shapefile.header.ShapeFileHeader;
import org.nocrala.tools.gis.data.esri.shapefile.shape.AbstractShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.PointData;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PointShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolylineShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolylineZShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolylineMShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PointZShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PointMShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonMShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonZShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.PartType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main2 {

	public static void main(String[] args) throws Exception {
		
		FileInputStream is = new FileInputStream("C:\\Users\\ETRI\\Documents\\test.shp");

		ValidationPreferences prefs = new ValidationPreferences();

		prefs.setMaxNumberOfPointsPerShape(16650);
		ShapeFileReader r = new ShapeFileReader(is, prefs);
		ShapeFileHeader h = r.getHeader();

		System.out.println("The shape type of this files is " + h.getShapeType());

		AbstractShape s;
		s = r.next();

		int numOfShape = 0;
		int TotalPart = 0;
		int TotalPoint = 0;
		int a = 1;

		while ((s = r.next()) != null) {

			s.getShapeType();

			PolylineShape p = (PolylineShape) s;
			TotalPart += p.getNumberOfParts();
			TotalPoint += p.getNumberOfPoints();
			numOfShape++;
		}

		FileInputStream IS = new FileInputStream("C:\\Users\\ETRI\\Documents\\test.shp");

		ValidationPreferences PREFS = new ValidationPreferences();

		PREFS.setMaxNumberOfPointsPerShape(16650);
		ShapeFileReader R = new ShapeFileReader(IS, PREFS);
		ShapeFileHeader H = R.getHeader();
		AbstractShape S;
		S = R.next();

		Point[] point = new Point[TotalPoint];
		Node[] node = new Node[TotalPoint];
		Part[] part = new Part[TotalPart];
		Shape[] shape = new Shape[numOfShape];

		PointList[] pointlist = new PointList[TotalPart];
		PartList[] partlist = new PartList[numOfShape];
		ShapeList shapelist = new ShapeList();

		int ShapeID = 0;
		int PartID = 0;
		int PointID = 0;
		int NodeID = 0;

		while ((S = R.next()) != null) {

			PolylineShape PL = (PolylineShape) S;

			double minX = PL.getPoints()[0].getX();
			double minY = PL.getPoints()[0].getY();
			double maxX = PL.getPoints()[0].getX();
			double maxY = PL.getPoints()[0].getY();

			shape[ShapeID] = new Shape(ShapeID);
			partlist[ShapeID] = new PartList();

			for (int i = 0; i < PL.getNumberOfParts(); i++) {

				part[PartID] = new Part(PartID);
				part[PartID].setShpaeID(ShapeID);
				pointlist[PartID] = new PointList();
				for (int j = 0; j < PL.getPointsOfPart(i).length; j++) {

					point[PointID] = new Point(PL.getPointsOfPart(i)[j].getX(), PL.getPointsOfPart(i)[j].getY());
					pointlist[PartID].addLast(point[PointID]);

					if (PL.getPointsOfPart(i)[j].getX() < minX) {
						minX = PL.getPointsOfPart(i)[j].getX();
					}
					if (PL.getPointsOfPart(i)[j].getY() < minY) {
						minY = PL.getPointsOfPart(i)[j].getY();
					}
					if (PL.getPointsOfPart(i)[j].getX() > maxX) {
						maxX = PL.getPointsOfPart(i)[j].getX();
					}
					if (PL.getPointsOfPart(i)[j].getY() > maxY) {
						maxY = PL.getPointsOfPart(i)[j].getY();
					}

					if (j == 0) {
						node[NodeID] = new Node(NodeID, PL.getPointsOfPart(i)[j].getX(),
								PL.getPointsOfPart(i)[j].getY());
						node[NodeID].setPartID(PartID);
						node[NodeID].setShpaeID(ShapeID);
						part[PartID].setStart(node[NodeID]);
						if (i == 0) {
							shape[ShapeID].setStart(node[NodeID]);
						}
						NodeID++;
					}
					if (j == PL.getPointsOfPart(i).length - 1) {
						node[NodeID] = new Node(NodeID, PL.getPointsOfPart(i)[j].getX(),
								PL.getPointsOfPart(i)[j].getY());
						node[NodeID].setPartID(PartID);
						node[NodeID].setShpaeID(ShapeID);
						part[PartID].setEnd(node[NodeID]);
						if (i == PL.getNumberOfParts() - 1) {
							shape[ShapeID].setEnd(node[NodeID]);
						}
						NodeID++;
					}

					PointID++;

				}
				part[PartID].setList(pointlist[PartID]); // point list 추가

				partlist[ShapeID].addLast(part[PartID]);
				PartID++;

			}
			shape[ShapeID].setList(partlist[ShapeID]);

			BBox box = new BBox(minX, minY, maxX, maxY);
			shape[ShapeID].setBox(box);

			shapelist.addSorting(shape[ShapeID]);
			ShapeID++;

		}

		// 명령		
		long start = System.currentTimeMillis();
		shapelist.AddDegree();
		long end = System.currentTimeMillis();
		for(int i=0;i<shapelist.size();i++) {
			System.out.println(shapelist.getShape(i).getDsn()+" , "+shapelist.getShape(i).getDen());
		}
		System.out.println("시간 : "+(end-start)/1000.0);
		
		
		/*
		//quick정렬
		Shape[] arr=new Shape[shapelist.size()];
		for(int i=0; i<shapelist.size();i++) {
			arr[i] = shapelist.getShape(i);
		}
		
		quick_sort(arr, 0, shapelist.size()-1);
		
		
		ShapeList newlist = new ShapeList();
		for(int i=0;i<shapelist.size();i++) {
			newlist.addLast(arr[i]);
		}
	
		
		for(int i=0; i<newlist.size();i++) {
			System.out.println(newlist.getShape(i).getBox().getMinX()+"\n");
		}*/
	}

	 static int partition(Shape list[], int left, int right) {
		
		  Shape pivot = new Shape();
		  Shape temp = new Shape();
		  int low, high;

		  low = left;
		  high = right + 1;
		  pivot = list[left]; // 정렬할 리스트의 가장 왼쪽 데이터를 피벗으로 선택(임의의 값을 피벗으로 선택)

		  /* low와 high가 교차할 때까지 반복(low<high) */
		  do{
		    /* list[low]가 피벗보다 작으면 계속 low를 증가 */
		    do {
		      low++; // low는 left+1 에서 시작
		    } while (low<=right && list[low].getBox().getMinX()<pivot.getBox().getMinX());

		    /* list[high]가 피벗보다 크면 계속 high를 감소 */
		    do {
		      high--; //high는 right 에서 시작
		    } while (high>=left && list[high].getBox().getMinX()>pivot.getBox().getMinX());

		    // 만약 low와 high가 교차하지 않았으면 list[low]를 list[high] 교환
		    if(low<high){
		    	temp = list[low];
		    	list[low]=list[high];
		    	list[high]=temp;
		    }
		  } while (low<high);

		  temp=list[left];
		  list[left]=list[high];
		  list[high]=temp;

		  return high;
		}

		static // 퀵 정렬
		void quick_sort(Shape list[],int left, int right){
	
		  /* 정렬할 범위가 2개 이상의 데이터이면(리스트의 크기가 0이나 1이 아니면) */
		  if(left<right){
		    // partition 함수를 호출하여 피벗을 기준으로 리스트를 비균등 분할 -분할(Divide)
		    int q = partition(list, left, right); // q: 피벗의 위치

		    // 피벗은 제외한 2개의 부분 리스트를 대상으로 순환 호출
		    quick_sort(list, left, q-1); // (left ~ 피벗 바로 앞) 앞쪽 부분 리스트 정렬 -정복(Conquer)
		    quick_sort(list, q+1,right); // (피벗 바로 뒤 ~ right) 뒤쪽 부분 리스트 정렬 -정복(Conquer)
		  }

		}
}
