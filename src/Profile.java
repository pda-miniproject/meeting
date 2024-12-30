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
        String profileSql = String.format(
            "INSERT INTO profile (user_id, nickname, rating, gender, mbti, height, weight) VALUES ('%s', '%s', %f, '%s', '%s', %f, %f)",
            userId, nickname, rating, gender, mbti, height, weight);
        String hobbyCheckSqlTemplate = "SELECT hobby_id FROM hobby WHERE hobby_name = '%s'";
        String hobbyInsertSqlTemplate = "INSERT INTO hobby (hobby_name) VALUES ('%s')";
        String userHobbySqlTemplate = "INSERT INTO userhobby (user_profile_id, hobby_id) VALUES (%d, %d)";

        try {
            // Insert profile and get generated user_profile_id
            int generatedProfileId = connectDB.insertExecuteWithGeneratedKeys(profileSql);
            if (generatedProfileId == 0) {
                throw new RuntimeException("Failed to retrieve generated user_profile_id.");
            }
            System.out.println("Generated user_profile_id: " + generatedProfileId);

            for (HobbyVO hobby : hobbies) {
                int hobbyId = 0;

                // Check if hobby exists
                String hobbyCheckSql = String.format(hobbyCheckSqlTemplate, hobby.getHobbyName());
                try (ResultSet resultSet = connectDB.selectExecuteWithResult(hobbyCheckSql)) {
                    if (resultSet != null && resultSet.next()) {
                        hobbyId = resultSet.getInt("hobby_id");
                        System.out.println("Existing hobby_id: " + hobbyId);
                    } else {
                        // Insert new hobby
                        String hobbyInsertSql = String.format(hobbyInsertSqlTemplate, hobby.getHobbyName());
                        hobbyId = connectDB.insertExecuteWithGeneratedKeys(hobbyInsertSql);
                        if (hobbyId == 0) {
                            throw new RuntimeException("Failed to retrieve generated hobby_id.");
                        }
                        System.out.println("Generated hobby_id: " + hobbyId);
                    }
                }

                // Insert into userhobby table
                String userHobbySql = String.format(userHobbySqlTemplate, generatedProfileId, hobbyId);
                connectDB.insertExecute(userHobbySql);
                System.out.println("Inserted into user_hobby: user_profile_id=" + generatedProfileId + ", hobby_id=" + hobbyId);
            }

            System.out.println("Profile and hobbies saved to database.");
        } catch (Exception e) {
            System.err.println("Error saving profile to database: " + e.getMessage());
        }
    }
}
