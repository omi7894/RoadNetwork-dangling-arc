package Polyline;

import java.util.Scanner;
import java.io.FileInputStream;
import org.nocrala.tools.gis.data.esri.shapefile.ShapeFileReader;
import org.nocrala.tools.gis.data.esri.shapefile.ValidationPreferences;
import org.nocrala.tools.gis.data.esri.shapefile.header.ShapeFileHeader;
import org.nocrala.tools.gis.data.esri.shapefile.shape.AbstractShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolylineShape;
import java.util.Queue;
import java.util.LinkedList;


public class Main_polyline {
	
	static String txt_file;//파일이름
	static int numOfShape = 0;
	static ShapeList shapelist;
	static String shapeType;
	static double delta;//오차범위

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//"C:\\Users\\ETRI\\Desktop\\소규모 도로 데이터2\\cheongju3.shp" - 24개
		//"C:\\Users\\ETRI\\Documents\\test.shp"  - 485개
		//"C:\\Users\\ETRI\\Desktop\\상수관로(WTL_PIPE_LM)\\WTL_PIPE_LM_Down3.shp" - 상수관(565)
		//"C:\\Users\\ETRI\\Desktop\\하수관로(SWL_PIPE_LM)\\SWL_PIPE_LM_Down3.shp" - 하수관(454)
		//shapelist.AddDegree(1);		
		//shapelist.AddDegreeBySorting();
		//shapelist.MakeLinkedList();
		//QuadTree quadtree = new QuadTree();
		//quadtree.MakeQuadTree(shapelist);
		//quadtree.AddDegree();
		//shapelist.MakeLinkedList();
		// long start = System.currentTimeMillis();
		// long end = System.currentTimeMillis();
		// System.out.println("시간 : "+(end-start)/1000.0);
		
		int a=1;//종료여부확인
		while(true) {
			txt_file = "";
			numOfShape = 0;
			delta = 0;
				
			showStartUI();
			
			ReadFile(txt_file);
			System.out.println("데이터 타입 : "+shapeType);		
			System.out.println("데이터 갯수 : "+numOfShape+"\n");
			int n=SelectUI();
			
			if(n==1) {
				shapelist.AddDegree(delta);
			}else if(n==2) {
				shapelist.AddDegreeBySorting(delta);
			}
			else{
				QuadTree quadtree = new QuadTree();
				quadtree.MakeQuadTree(shapelist,delta);
				quadtree.AddDegree(delta);
			}

				
			int cnt = 0;
			for(int i=0;i<shapelist.size();i++) {
				if(shapelist.getShape(i).getDsn()==1 && shapelist.getShape(i).getDen()==1) {
					cnt++;
				}
			}
			System.out.println("dangling 개수 : " + cnt+"\n");
			
			a=QuitUI();
			if(a==0) break;
		}
		return;

	}
	static void showStartUI() {
		Scanner scan = new Scanner(System.in);
		Scanner scan2 = new Scanner(System.in);
		int n;
		System.out.println("-----------도로망 댕글링 검출-------------");
		System.out.println("파일 : 1.선택   2.직접입력");
		n=scan.nextInt();
		if(n==1) {
			System.out.println("1.청주 도로망(485)"+"\n"+"2.상수관로(565)"+"\n"+"3.하수관로(454)");
			n=scan.nextInt();
			if(n==1) {
				txt_file = "C:\\Users\\ETRI\\Documents\\test.shp";
			}
			else if(n==2) {
				txt_file = "C:\\Users\\ETRI\\Desktop\\상수관로(WTL_PIPE_LM)\\WTL_PIPE_LM_Down3.shp";
			}
			else if(n==3) {
				txt_file = "C:\\Users\\ETRI\\Desktop\\하수관로(SWL_PIPE_LM)\\SWL_PIPE_LM_Down3.shp";
			}else {
				System.out.println("입력오류");
				showStartUI();
			}
			
		}else if(n==2){
			System.out.println("직접입력 : ");			 
			txt_file = scan2.nextLine();			
		}else {
			System.out.println("입력오류");
			showStartUI();
		}
		
		
	}
	static int SelectUI() {
		Scanner scan = new Scanner(System.in);
		int n;
		System.out.println("<댕글링 찾기>"+"\n"+"1.전체 탐색 "+"\n"+"2.Bound Box Sorting"+"\n"+"3.Quad Tree");
		n=scan.nextInt();
		System.out.println("오차범위(dt<=|n|) n :");
		delta=scan.nextInt();
		if(n==1) {return 1;}
		else if(n==2) {return 2;}
		else if(n==3) {return 3;}
		else {
			System.out.println("입력오류");
			return SelectUI();
		}
		
	}
	static int QuitUI() {
		Scanner scan = new Scanner(System.in);
		System.out.println("1.종료    2.처음으로");
		int n=scan.nextInt();
		if(n==1) {
			return 0;
		}else if(n==2) {
			return 1;
		}
		else {
			System.out.println("입력오류");
			return QuitUI();
		}
	
		
	}
	static void ReadFile(String txt_file) throws Exception{
		FileInputStream is = new FileInputStream(txt_file);

		ValidationPreferences prefs = new ValidationPreferences();

		prefs.setMaxNumberOfPointsPerShape(16650);
		ShapeFileReader r = new ShapeFileReader(is, prefs);
		ShapeFileHeader h = r.getHeader();
		
		shapeType = h.getShapeType().toString();

		//System.out.println("The shape type of this files is " + h.getShapeType());

		AbstractShape s;
		// s = r.next();

		//int numOfShape = 0;
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

		FileInputStream IS = new FileInputStream(txt_file);

		ValidationPreferences PREFS = new ValidationPreferences();

		PREFS.setMaxNumberOfPointsPerShape(16650);
		ShapeFileReader R = new ShapeFileReader(IS, PREFS);
		ShapeFileHeader H = R.getHeader();
		AbstractShape S;
		// S = R.next();

		Point[] point = new Point[TotalPoint];
		Node[] node = new Node[TotalPoint];
		Part[] part = new Part[TotalPart];
		Shape[] shape = new Shape[numOfShape];

		PointList[] pointlist = new PointList[TotalPart];
		PartList[] partlist = new PartList[numOfShape];
		shapelist = new ShapeList();

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

	}
}