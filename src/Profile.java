import java.sql.ResultSet;
import java.util.List;

public class Profile {
    private String userId;
    private String nickname;
    private float rating;
    private String gender;
    private String mbti;
    private float height;
    private float weight;

    public Profile(ProfileDTO dto) {
        this.userId = dto.getUserId();
        this.nickname = dto.getNickname();
        this.rating = dto.getRating();
        this.gender = dto.getGender();
        this.mbti = dto.getMbti();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
    }

    public void saveProfileToDatabase(ConnectDB connectDB) {
        try {
            // Insert profile
            String profileSql = String.format(
                "INSERT INTO profile (user_id, nickname, rating, gender, mbti, height, weight) VALUES ('%s', '%s', %f, '%s', '%s', %f, %f)",
                userId, nickname, rating, gender, mbti, height, weight);
            connectDB.insertExecute(profileSql);

            System.out.println("Profile saved to database.");
        } catch (Exception e) {
            System.err.println("Error saving profile to database: " + e.getMessage());
        }
    }

    public int getGeneratedProfileId(ConnectDB connectDB) {
        int generatedProfileId = 0;
        try {
            String selectSql = String.format("SELECT user_profile_id FROM profile WHERE user_id = '%s'", userId);
            try (ResultSet resultSet = connectDB.selectExecuteWithResult(selectSql)) {
                if (resultSet.next()) {
                    generatedProfileId = resultSet.getInt("user_profile_id");
                    System.out.println("Generated user_profile_id: " + generatedProfileId);
                } else {
                    throw new RuntimeException("Failed to retrieve user_profile_id for user_id: " + userId);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving user_profile_id: " + e.getMessage());
        }
        return generatedProfileId;
    }

    public static void saveHobbiesToDatabase(ConnectDB connectDB, int userProfileId, List<HobbyVO> hobbies) {
        try {
            for (HobbyVO hobby : hobbies) {
                int hobbyId = 0;

                // Check if hobby exists
                String hobbyCheckSql = String.format("SELECT hobby_id FROM hobby WHERE hobby_name = '%s'", hobby.getHobbyName());
                try (ResultSet resultSet = connectDB.selectExecuteWithResult(hobbyCheckSql)) {
                    if (resultSet.next()) {
                        hobbyId = resultSet.getInt("hobby_id");
                        System.out.println("Existing hobby_id: " + hobbyId);
                    } else {
                        // Insert new hobby
                        String hobbyInsertSql = String.format("INSERT INTO hobby (hobby_name) VALUES ('%s')", hobby.getHobbyName());
                        connectDB.insertExecute(hobbyInsertSql);

                        // Get generated hobby_id
                        try (ResultSet generatedKeys = connectDB.selectExecuteWithResult("SELECT LAST_INSERT_ID()")) {
                            if (generatedKeys.next()) {
                                hobbyId = generatedKeys.getInt(1);
                                System.out.println("Generated hobby_id: " + hobbyId);
                            }
                        }
                    }
                }

                // Insert into userhobby table
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
