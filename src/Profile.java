import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Profile {
    private String userId;
    private String nickname;
    private float rating;
    private String gender;
    private String mbti;
    private float height;
    private float weight;
    private List<HobbyVO> hobbies;

    public Profile(ProfileDTO dto, List<HobbyVO> hobbies) {
        this.userId = dto.getUserId();
        this.nickname = dto.getNickname();
        this.rating = dto.getRating();
        this.gender = dto.getGender();
        this.mbti = dto.getMbti();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.hobbies = hobbies;
    }

    public void saveToDatabase(ConnectDB connectDB) {
        String profileSql = "INSERT INTO profile (user_id, nickname, rating, gender, mbti, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String hobbyCheckSql = "SELECT hobby_id FROM hobby WHERE hobby_name = ?";
        String hobbyInsertSql = "INSERT INTO hobby (hobby_name) VALUES (?)";
        String userHobbySql = "INSERT INTO userhobby (user_profile_id, hobby_id) VALUES (?, ?)";

        Connection connection = null;
        try {
            connection = connectDB.getConnection();
            connection.setAutoCommit(false);

            int generatedProfileId;
            try (PreparedStatement profileStatement = connection.prepareStatement(profileSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                profileStatement.setString(1, userId);
                profileStatement.setString(2, nickname);
                profileStatement.setFloat(3, rating);
                profileStatement.setString(4, gender);
                profileStatement.setString(5, mbti);
                profileStatement.setFloat(6, height);
                profileStatement.setFloat(7, weight);
                profileStatement.executeUpdate();

                try (ResultSet generatedKeys = profileStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedProfileId = generatedKeys.getInt(1);
                        System.out.println("Generated user_profile_id: " + generatedProfileId);
                    } else {
                        throw new SQLException("Creating profile failed, no ID obtained.");
                    }
                }
            }

            for (HobbyVO hobby : hobbies) {
                int hobbyId = 0;

                try (PreparedStatement hobbyCheckStatement = connection.prepareStatement(hobbyCheckSql)) {
                    hobbyCheckStatement.setString(1, hobby.getHobbyName());
                    try (ResultSet resultSet = hobbyCheckStatement.executeQuery()) {
                        if (resultSet.next()) {
                            hobbyId = resultSet.getInt("hobby_id");
                            System.out.println("Existing hobby_id: " + hobbyId);
                        } else {
                            try (PreparedStatement hobbyInsertStatement = connection.prepareStatement(hobbyInsertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                                hobbyInsertStatement.setString(1, hobby.getHobbyName());
                                hobbyInsertStatement.executeUpdate();
                                try (ResultSet generatedKeys = hobbyInsertStatement.getGeneratedKeys()) {
                                    if (generatedKeys.next()) {
                                        hobbyId = generatedKeys.getInt(1);
                                        System.out.println("Generated hobby_id: " + hobbyId);
                                    }
                                }
                            }
                        }
                    }
                }

                try (PreparedStatement userHobbyStatement = connection.prepareStatement(userHobbySql)) {
                    userHobbyStatement.setInt(1, generatedProfileId);
                    userHobbyStatement.setInt(2, hobbyId);
                    userHobbyStatement.executeUpdate();
                    System.out.println("Inserted into user_hobby: user_profile_id=" + generatedProfileId + ", hobby_id=" + hobbyId);
                }
            }

            connection.commit();
            System.out.println("Profile and hobbies saved to database.");
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.err.println("Rollback failed: " + rollbackException.getMessage());
                }
            }
            System.err.println("Error saving profile to database: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException autoCommitException) {
                    System.err.println("Failed to reset auto-commit: " + autoCommitException.getMessage());
                }
            }
        }
    }
}
