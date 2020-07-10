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

public class Main_DB {
/*
	public static void main(String[] args) throws IOException,
      InvalidShapeFileException {

    FileInputStream is = new FileInputStream(
        "C:\\Users\\ETRI\\Desktop\\HDMap_UTMK_New\\HDMap_UTMK_정표고\\A2_LINK.shp");
     ValidationPreferences prefs = new ValidationPreferences();
    prefs.setMaxNumberOfPointsPerShape(16650);
    ShapeFileReader r = new ShapeFileReader(is, prefs);

    ShapeFileHeader h = r.getHeader();
    System.out.println("The shape type of this files is " + h.getShapeType());

    int total = 0;
    int a=1;
    AbstractShape s;
    while ((s = r.next()) != null) {
    	
    	  switch (s.getShapeType()) {
          case POINT:
    	        System.out.println(s.getShapeType()+" "+a);a++;
    	        PointShape aPoint = (PointShape) s;
    	        System.out.println("("+aPoint.getX()+", "+aPoint.getY()+")"+"\n");
    	        break;
    	        
          case POINT_Z:
        	  System.out.println(s.getShapeType()+" "+a);a++;
        	  PointZShape aPointZ = (PointZShape) s;
        	  System.out.println("("+aPointZ.getX()+", "+aPointZ.getY()+", "+aPointZ.getZ()+")"+"\n");
    	      break; 
    	      
          case POINT_M:
        	  System.out.println(s.getShapeType()+" "+a);a++;
        	  PointMShape aPointM = (PointMShape) s;
        	  System.out.println("("+aPointM.getX()+", "+aPointM.getY()+", "+aPointM.getM()+")"+"\n");
        	  break;
        	          	 
          case POLYLINE:
        	  System.out.println(s.getShapeType()+" ");
              PolylineShape aPolyline = (PolylineShape) s;
              LinkedList shplist0 = new LinkedList();
              System.out.println("point 개수 : " + aPolyline.getPoints().length);
              for(int i=0;i<aPolyline.getPoints().length;i++) {
            	  //System.out.println(aPolyline.getPoints()[i].getX() + " , "+aPolyline.getPoints()[i].getY());
            	  shplist0.addLast(aPolyline.getPoints()[i].getX(), aPolyline.getPoints()[i].getY());
              }           
              System.out.println("리스트" + a + " : " + "\n" + shplist0 + "\n"); a++;              
              break;      
              
          case POLYLINE_Z:        	  
        	  System.out.println(s.getShapeType());
        	  PolylineZShape aPolylineZ = (PolylineZShape) s;
        	  LinkedList shplist = new LinkedList();
        	  System.out.println("point 개수 : " + aPolylineZ.getPoints().length); 
              for(int i=0;i<aPolylineZ.getPoints().length;i++) {
            	  //System.out.println(aPolylineZ.getPoints()[i].getX() + " , "+aPolylineZ.getPoints()[i].getY());
            	  shplist.addLast(aPolylineZ.getPoints()[i].getX(), aPolylineZ.getPoints()[i].getY());
              }
              System.out.println("리스트" + a + " : " + "\n" + shplist + "\n"); a++;              
              break; 
                  
          case POLYLINE_M:
        	  System.out.println(s.getShapeType());
        	  PolylineMShape aPolylineM = (PolylineMShape) s;
        	  LinkedList shplist2 = new LinkedList();
        	  System.out.println("point 개수 : " + aPolylineM.getPoints().length);
              for(int i=0;i<aPolylineM.getPoints().length;i++) {
            	  //System.out.println(aPolylineM.getPoints()[i].getX() + " , "+aPolylineM.getPoints()[i].getY());
            	  shplist2.addLast(aPolylineM.getPoints()[i].getX(), aPolylineM.getPoints()[i].getY());
              }       	  
              System.out.println("리스트" + a + " : " + "\n" + shplist2 + "\n"); a++;     
        	break;
           
          case POLYGON:
        	  System.out.println(s.getShapeType());
        	  PolygonShape aPolygon = (PolygonShape) s;
        	  LinkedList shplist3 = new LinkedList();
        	  System.out.println("point 개수  : " + aPolygon.getNumberOfPoints());
        	  for(int i=0;i<aPolygon.getPoints().length;i++) {
            	  //System.out.println(aPolylineZ.getPoints()[i].getX() + " , "+aPolylineZ.getPoints()[i].getY());
            	  shplist3.addLast(aPolygon.getPoints()[i].getX(), aPolygon.getPoints()[i].getY());
              }
        	  System.out.println("리스트" + a + " : " + "\n" + shplist3 + "\n"); a++;
        	  break;
        	  
          case POLYGON_Z:
        	  System.out.println(s.getShapeType());
        	  PolygonZShape aPolygonZ = (PolygonZShape) s;
        	  LinkedList shplist4 = new LinkedList();
        	  System.out.println("point 개수  : " + aPolygonZ.getNumberOfPoints());
        	  for(int i=0;i<aPolygonZ.getPoints().length;i++) {
            	  //System.out.println(aPolylineZ.getPoints()[i].getX() + " , "+aPolylineZ.getPoints()[i].getY());
            	  shplist4.addLast(aPolygonZ.getPoints()[i].getX(), aPolygonZ.getPoints()[i].getY());
              }
        	  System.out.println("리스트" + a + " : " + "\n" + shplist4 + "\n"); a++;
        	  break;
        	  
          case POLYGON_M:
        	  System.out.println(s.getShapeType());
        	  PolygonMShape aPolygonM = (PolygonMShape) s;
        	  LinkedList shplist5 = new LinkedList();
        	  System.out.println("point 개수  : " + aPolygonM.getNumberOfPoints());
        	  for(int i=0;i<aPolygonM.getPoints().length;i++) {
            	  //System.out.println(aPolylineZ.getPoints()[i].getX() + " , "+aPolylineZ.getPoints()[i].getY());
            	  shplist5.addLast(aPolygonM.getPoints()[i].getX(), aPolygonM.getPoints()[i].getY());
              }
        	  System.out.println("리스트" + a + " : " + "\n" + shplist5 + "\n"); a++;
        	  break;                    
    	  }
    	  total++;
    }
    System.out.println("Total shapes read: " + total);
    is.close();    
  }  */
}

