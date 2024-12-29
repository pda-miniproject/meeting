// ProfileDTO.java
public class ProfileDTO {
    public String userProfileId;
    public String userId;
    public String collegeId;
    public String majorId;
    public String nickname;
    public float rating;
    public String gender;
    public String mbti;
    public float height;
    public float weight;

    // Constructor
    public ProfileDTO(String userProfileId, String userId, String collegeId, String majorId, String nickname, float rating, String gender, String mbti, float height, float weight) {
        this.userProfileId = userProfileId;
        this.userId = userId;
        this.collegeId = collegeId;
        this.majorId = majorId;
        this.nickname = nickname;
        this.rating = rating;
        this.gender = gender;
        this.mbti = mbti;
        this.height = height;
        this.weight = weight;
    }
}