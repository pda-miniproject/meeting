import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ConnectDB {

    public static void main(String[] args) {
        Properties properties = new Properties();
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // ClassLoader로 src 디렉터리 안의 파일 로드
            InputStream inputStream = ConnectDB.class.getResourceAsStream("/db_credentials.properties");
            if (inputStream == null) {
                throw new RuntimeException("Properties file not found in src folder.");
            }
            properties.load(inputStream);

            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // 데이터베이스 URL
            String dbUrl = "jdbc:mysql://localhost:3306/대학소개팅"; // 데이터베이스 이름에 맞게 변경

            // JDBC 연결
            connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Database connected successfully!");

            // SQL 실행 예제
            String query = "SELECT * FROM users"; // 테이블 이름에 맞게 변경
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
