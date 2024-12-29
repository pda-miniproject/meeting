
public class Profile {
    private String userProfileId;
    private String userId;
    private String collegeId;
    private String majorId;
    private String nickname;
    private float rating;
    private String gender;
    private String mbti;
    private float height;
    private float weight;
    public String hobbyId;

    // Constructor
    public Profile(ProfileDTO dto) {
        this.userProfileId = dto.userProfileId;
        this.userId = dto.userId;
        this.collegeId = dto.collegeId;
        this.majorId = dto.majorId;
        this.nickname = dto.nickname;
        this.rating = dto.rating;
        this.gender = dto.gender;
        this.mbti = dto.mbti;
        this.height = dto.height;
        this.weight = dto.weight;
        this.hobbyId = dto.hobbyId;
    }

    // Insert profile into the database
    public void saveToDatabase(ConnectDB connectDB) {
        String sql = "INSERT INTO profile (user_profile_id, user_id, college_id, major_id, nickname, rating, gender, mbti, height, weight, hobby_id) VALUES ('" +
                     userProfileId + "', '" + userId + "', '" + collegeId + "', '" + majorId + "', '" + nickname + "', " + rating + ", '" + gender + "', '" + mbti + "', " + height + ", " + weight  + ", " + hobbyId+ ")";

        connectDB.insertExecute(sql);
    }
}