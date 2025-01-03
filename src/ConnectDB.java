import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ConnectDB {
    /**
     * db커넥션 메서드
     */
    public Connection getConnection() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = ConnectDB.class.getResourceAsStream("/db_credentials.properties");
        if (inputStream == null) {
            throw new RuntimeException("Properties file not found in src folder.");
        }
        properties.load(inputStream);

        String dbUrl = "jdbc:mysql://localhost:3306/대학소개팅";
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, username, password);
    }

    /**
     * insert 공통 프로필, 유저, 취미등등
     * @Param sql 만 잘짜서 그대로 실행시키면 됌
     *
     */
    public void insertExecute(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            System.out.println("Database connected successfully!");
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    // SELECT 실행 메서드
    public void selectExecute(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Database connected successfully!");

            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("user_id"));
                System.out.println("Nickname: " + resultSet.getString("nickname"));
                System.out.println("Height: " + resultSet.getDouble("height"));
                System.out.println("Weight: " + resultSet.getDouble("weight"));
                System.out.println("MBTI: " + resultSet.getString("mbti"));
                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public ResultSet selectExecuteWithResult(String sql) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            System.err.println("Error during select: " + e.getMessage());
            return null;
        }
    }


    public int insertExecuteWithGeneratedKeys(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during insert with generated keys: " + e.getMessage());
        }
        return 0;
    }
}
