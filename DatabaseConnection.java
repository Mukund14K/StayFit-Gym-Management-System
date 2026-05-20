import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gym_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    
    private static final String PASSWORD = "aitd123"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("\n❌ [DRIVER ERROR] Java cannot find your JAR file!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("\n❌ [SQL CONNECTION ERROR] Handshake failed with MySQL Server!");
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Vendor Error Code: " + e.getErrorCode());
            e.printStackTrace(); 
        }
        return conn;
    }
}