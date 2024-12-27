import java.util.List;

public class Profile {
    private String profileId;
    private String userId;
    private List<String> hobbies;
    private String mbti;
    private float height;
    private float weight;
    private String profilePicture;

    public void updateHobbies(List<String> hobbies) {
        this.hobbies.addAll(hobbies);
        //db에 넣기
    }
    public void updatePhysical(float height,float weight) {
        this.height = height;
        this.weight = weight;
        //db에 넣기

    }
}
