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
		QuadTree quadtree = new QuadTree();
		quadtree.MakeQuadTree(shapelist);
		System.out.println(numOfShape);
		//quadtree.AddDegree();
		/*
		long start = System.currentTimeMillis();
		shapelist.AddDegree();
		//shapelist.AddDegreeBySorting();
		long end = System.currentTimeMillis();
		for(int i=0;i<shapelist.size();i++) {
			System.out.println(shapelist.getShape(i).getDsn()+" , "+shapelist.getShape(i).getDen());
		}
		System.out.println("시간 : "+(end-start)/1000.0);
		*/
	}
	/*
	public static boolean Include(Node node, TreeNode treenode) {
		if(treenode.getBox().getMinX()<=node.getX() && treenode.getBox().getMaxX()>=node.getX()
				&& treenode.getBox().getMinY()<=node.getY() && treenode.getBox().getMaxY()>=node.getY()) {
			return true;
		}
		return false;
	}
	
	public static void MakeQuadTree(ShapeList shapelist) {
		
		//QuadTree만들기...
		TreeNode[] treenode = new TreeNode[21];
		treenode[0]=new TreeNode(0);
		treenode[1]=new TreeNode(1);
		treenode[2]=new TreeNode(2);
		treenode[3]=new TreeNode(3);
		treenode[4]=new TreeNode(4);
		treenode[5]=new TreeNode(5);
		treenode[6]=new TreeNode(6);
		treenode[7]=new TreeNode(7);
		treenode[8]=new TreeNode(8);
		treenode[9]=new TreeNode(9);
		treenode[10]=new TreeNode(10);
		treenode[11]=new TreeNode(11);
		treenode[12]=new TreeNode(12);
		treenode[13]=new TreeNode(13);
		treenode[14]=new TreeNode(14);
		treenode[15]=new TreeNode(15);
		treenode[16]=new TreeNode(16);
		treenode[17]=new TreeNode(17);
		treenode[18]=new TreeNode(18);
		treenode[19]=new TreeNode(19);
		treenode[20]=new TreeNode(20);
		
		treenode[0].setBox(shapelist.getLargestBox());
		
		treenode[1].setBox(treenode[0].getBox().splitBox()[0]);
		treenode[2].setBox(treenode[0].getBox().splitBox()[1]);
		treenode[3].setBox(treenode[0].getBox().splitBox()[2]);
		treenode[4].setBox(treenode[0].getBox().splitBox()[3]);

		treenode[5].setBox(treenode[1].getBox().splitBox()[0]);
		treenode[6].setBox(treenode[1].getBox().splitBox()[1]);
		treenode[7].setBox(treenode[1].getBox().splitBox()[2]);
		treenode[8].setBox(treenode[1].getBox().splitBox()[3]);
		
		treenode[9].setBox(treenode[2].getBox().splitBox()[0]);
		treenode[10].setBox(treenode[2].getBox().splitBox()[1]);
		treenode[11].setBox(treenode[2].getBox().splitBox()[2]);
		treenode[12].setBox(treenode[2].getBox().splitBox()[3]);

		treenode[13].setBox(treenode[3].getBox().splitBox()[0]);
		treenode[14].setBox(treenode[3].getBox().splitBox()[1]);
		treenode[15].setBox(treenode[3].getBox().splitBox()[2]);
		treenode[16].setBox(treenode[3].getBox().splitBox()[3]);

		treenode[17].setBox(treenode[4].getBox().splitBox()[0]);
		treenode[18].setBox(treenode[4].getBox().splitBox()[1]);
		treenode[19].setBox(treenode[4].getBox().splitBox()[2]);
		treenode[20].setBox(treenode[4].getBox().splitBox()[3]);
		
		for(int i=1;i<=4;i++) {
			treenode[0].makeChild(i-1,treenode[i]);
			treenode[i].setParent(treenode[0]);
		}
		for(int i=5;i<=8;i++) {
			treenode[1].makeChild(i-5,treenode[i]);
			treenode[i].setParent(treenode[1]);
		}
		for(int i=9;i<=12;i++) {
			treenode[2].makeChild(i-9,treenode[i]);
			treenode[i].setParent(treenode[2]);
		}
		for(int i=13;i<=16;i++) {
			treenode[3].makeChild(i-13,treenode[i]);
			treenode[i].setParent(treenode[3]);
		}
		for(int i=17;i<=20;i++) {
			treenode[4].makeChild(i-17,treenode[i]);
			treenode[i].setParent(treenode[4]);
		}
		
		TreeNode currentTN = new TreeNode();

		for(int i=0;i<shapelist.size();i++) {
			currentTN=treenode[0];
			
			while(currentTN!=null) {
				if(Include(shapelist.getShape(i).getStart(), currentTN.getChild()[0])) {
					shapelist.getShape(i).setSntree(currentTN.getChild()[0]);
					currentTN=currentTN.getChild()[0];	
				}
				else if(Include(shapelist.getShape(i).getStart(), currentTN.getChild()[1])) {
					shapelist.getShape(i).setSntree(currentTN.getChild()[1]);
					currentTN=currentTN.getChild()[1];	
				}
				else if(Include(shapelist.getShape(i).getStart(), currentTN.getChild()[2])) {
					shapelist.getShape(i).setSntree(currentTN.getChild()[2]);
					currentTN=currentTN.getChild()[2];	
				}
				else {
					shapelist.getShape(i).setSntree(currentTN.getChild()[3]);
					currentTN=currentTN.getChild()[3];	
				}
					
			}
		}
		
		for(int i=0;i<shapelist.size();i++) {
			currentTN=treenode[0];
			
			while(currentTN!=null) {
				if(Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[0])) {
					shapelist.getShape(i).setEntree(currentTN.getChild()[0]);
					currentTN=currentTN.getChild()[0];	
				}
				else if(Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[1])) {
					shapelist.getShape(i).setEntree(currentTN.getChild()[1]);
					currentTN=currentTN.getChild()[1];	
				}
				else if(Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[2])) {
					shapelist.getShape(i).setEntree(currentTN.getChild()[2]);
					currentTN=currentTN.getChild()[2];	
				}
				else {
					shapelist.getShape(i).setEntree(currentTN.getChild()[3]);
					currentTN=currentTN.getChild()[3];	
				}
					
			}
		}
		
		
		for(int i=0;i<shapelist.size();i++) {
			while(true) {
				if(shapelist.getShape(i).getSntree() == shapelist.getShape(i).getEntree()) {
					treenode[shapelist.getShape(i).getSntree().getId()].addShape(shapelist.getShape(i));
					break;
				}else {
					shapelist.getShape(i).setSntree(shapelist.getShape(i).getSntree().getParent());
					shapelist.getShape(i).setEntree(shapelist.getShape(i).getEntree().getParent());
				}
			}
		}
		
		
		
	}

*/
	
}
