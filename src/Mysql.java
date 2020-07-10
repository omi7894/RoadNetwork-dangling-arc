import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Mysql {
   public static void main(String[] args) throws Exception{
	   getConnection();

	   createTable();
	   //   post();
   }
   
   public static Connection getConnection() throws Exception{
	   
	   try {
	   String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://localhost:3306/opentutorials?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	   String username = "root";
	   String password = "password12";
	   Class.forName(driver);
	   
	   Connection conn = DriverManager.getConnection(url, username, password);
	   System.out.println("Connected");
	   return conn;
	   }catch(Exception e) {System.out.println(e);}	   
	   
	   return null;
   }
   public static void createTable() throws Exception{
	   try {
		   Connection con = getConnection();
		   PreparedStatement create = con.prepareStatement("Create Table test1(id int NOT NULL, a varchar(50), b int(11), primary key(id))");
		   create.executeUpdate();
		   
	   }catch(Exception e) {System.out.println(e);}
	   finally {
		   System.out.println("Function Completed.");
	   };
   }
   
   
   public static void post() throws Exception{
	   try {
		   Connection con = getConnection();
		   PreparedStatement posted = con.prepareStatement("Insert into shape (id, point_x, point_y, next_point) values ('1','1','1','2')");
		   posted.executeUpdate();
	   }catch(Exception e){System.out.println("Insert Completed.");}
	   
	   finally {
		   System.out.println("Insert Completed.");
	   }
   }
}