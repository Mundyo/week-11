//package projects.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import projects.Exception.DbException;
//
//
//public class DbConnection {
//	
//
//    private static final String HOST = "localhost";
//    private static final int PORT = 3306;
//    private static final String SCHEMA = "project";
//    private static final String USER = "project";
//    private static final String PASSWORD = "project";
//    private static Connection conn;
//
//    public static Connection getConnection() {
//    	
//		
//        if (conn == null) {
//            String uri = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, SCHEMA);
//            try {
//                conn = DriverManager.getConnection(uri, USER, PASSWORD);
//                System.out.println("Connection to schema " + SCHEMA + " is successful.");
//                return getConnection();
//            } catch (SQLException e) {
//            	 throw new DbException(e);
//               
//               
//            }
//        }
//		return conn;
//       
//    }
//}

package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects.Exception.DbException;


public class DbConnection {
	

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String SCHEMA = "project";
    private static final String USER = "project";
    private static final String PASSWORD = "project";
  
	

    public static Connection getConnection() {
    	
            String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER, PASSWORD);
            try {
                Connection conn = DriverManager.getConnection(uri);
                System.out.println("Connection to schema " + SCHEMA + " is successful.");
                return conn;
            } catch (SQLException e) {
            	 throw new DbException("Unable to get Connection at "+ uri);
               
               
            }

       
    }
}

