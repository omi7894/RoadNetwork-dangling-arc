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
		


		FileInputStream IS = new FileInputStream(
				"C:\\Users\\ETRI\\Documents\\test.shp");

		ValidationPreferences PREFS = new ValidationPreferences();

		PREFS.setMaxNumberOfPointsPerShape(16650);
		ShapeFileReader R = new ShapeFileReader(IS, PREFS);
		ShapeFileHeader H = R.getHeader();
		AbstractShape S;
		S = R.next();

		Point[] point = new Point[TotalPoint];
		Node[] node = new Node[TotalPart+1];
		Part[] part = new Part[TotalPart];
		Shape[] shape = new Shape[numOfShape];
		
		PointList[] pointlist = new PointList[TotalPart];
		PartList[] partlist = new PartList[numOfShape];
		ShapeList shapelist = new ShapeList();


		int ShapeID = 0;
		int PartID=0;
		int PointID=0;
		int NodeID=0;

		while ((S = R.next()) != null) {
			
			PolylineShape PL = (PolylineShape) S;
			
			
			shape[ShapeID] = new Shape(ShapeID);
			partlist[ShapeID] = new PartList();

			for (int i = 0; i<PL.getNumberOfParts(); i++) {
			
			part[PartID] = new Part(PartID);	
			pointlist[PartID] = new PointList();
				for(int j=0; j<PL.getPointsOfPart(i).length;j++) {
					
					//if(j==0 || j==PL.getPointsOfPart(i).length-1) {
					//	node[NodeID] = new Node(NodeID, PL.getPointsOfPart(i)[j].getX(), PL.getPointsOfPart(i)[j].getY());
					//	NodeID++;
					//}
					point[PointID] = new Point(PL.getPointsOfPart(i)[j].getX(), PL.getPointsOfPart(i)[j].getY() );					
					pointlist[PartID].addLast(point[PointID].getX(), point[PointID].getY());					
					PointID++;

				
				}
				
				partlist[ShapeID].addLast(PartID);				
				PartID++;
				
			
			}
			shapelist.addLast(ShapeID);
			ShapeID++;
			
			

		}

		is.close();

		
	}
	
	

}