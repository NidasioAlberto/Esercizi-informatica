package quintaib;

import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    public static void main(String[] args) {
        try {
            System.out.println("Connecting");

            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.102:3306/AlbertoNidasio", "NidasioAlberto", "NidasioAlberto");

            System.out.println("Connected to database");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
