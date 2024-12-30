import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Profile {

    public void saveProfile(ConnectDB connectDB, ProfileDTO dto) {
        int generatedProfileId = 0;
        String insertSql = "INSERT INTO profile (nickname, rating, gender, mbti, height, weight) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE profile SET user_id = ? WHERE user_profile_id = ?";

        try (Connection connection = connectDB.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Insert profile
            insertStmt.setString(1, dto.getNickname());
            insertStmt.setFloat(2, dto.getRating());
            insertStmt.setString(3, dto.getGender());
            insertStmt.setString(4, dto.getMbti());
            insertStmt.setFloat(5, dto.getHeight());
            insertStmt.setFloat(6, dto.getWeight());
            insertStmt.executeUpdate();

            // Retrieve generated user_profile_id
            try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedProfileId = rs.getInt(1);
                    System.out.println("Generated user_profile_id: " + generatedProfileId);
                }
            }

            // Update user_id to match user_profile_id
            updateStmt.setInt(1, generatedProfileId);
            updateStmt.setInt(2, generatedProfileId);
            updateStmt.executeUpdate();
            System.out.println("Updated user_id to: " + generatedProfileId);

        } catch (Exception e) {
            System.err.println("Error saving profile: " + e.getMessage());
        }
    }
    
    public int getGeneratedProfileIdByNickname(ConnectDB connectDB, String nickname) {
        int generatedProfileId = 0;
        try {
            String selectSql = String.format("SELECT user_profile_id FROM profile WHERE nickname = '%s'", nickname);
            try (ResultSet resultSet = connectDB.selectExecuteWithResult(selectSql)) {
                if (resultSet.next()) {
                    generatedProfileId = resultSet.getInt("user_profile_id");
                    System.out.println("Generated user_profile_id: " + generatedProfileId);
                } else {
                    throw new RuntimeException("Failed to retrieve user_profile_id for nickname: " + nickname);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving user_profile_id: " + e.getMessage());
        }
        return generatedProfileId;
    }

    public static void saveHobbies(ConnectDB connectDB, int userProfileId, List<String> hobbies) {
        try {
            for (String hobby : hobbies) {
                int hobbyId = 0;

                String hobbyCheckSql = String.format("SELECT hobby_id FROM hobby WHERE hobby_name = '%s'", hobby);
                try (ResultSet resultSet = connectDB.selectExecuteWithResult(hobbyCheckSql)) {
                    if (resultSet.next()) {
                        hobbyId = resultSet.getInt("hobby_id");
                        System.out.println("Existing hobby_id: " + hobbyId);
                    } else {
                        System.out.println("Hobby does not exist. Skipping: " + hobby);
                        continue;
                    }
                }

                String userHobbySql = String.format(
                    "INSERT INTO userhobby (user_profile_id, hobby_id) VALUES (%d, %d)",
                    userProfileId, hobbyId);
                connectDB.insertExecute(userHobbySql);
                System.out.println("Inserted into user_hobby: user_profile_id=" + userProfileId + ", hobby_id=" + hobbyId);
            }
            System.out.println("Hobbies saved to database.");
        } catch (Exception e) {
            System.err.println("Error saving hobbies to database: " + e.getMessage());
        }
    }
}
