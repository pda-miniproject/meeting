import java.util.Arrays;
import java.util.List;

public class TestProfile {
    public static void main(String[] args) {
        // ConnectDB instance
        ConnectDB connectDB = new ConnectDB();

        // Create profiles with hobbies

        // 권은비
        ProfileDTO dto1 = new ProfileDTO("1", "권은비", 60, "FEMALE", "ESTJ", 163, 65);
        List<HobbyVO> hobbies1 = Arrays.asList(
            new HobbyVO(0, "춤"),
            new HobbyVO(0, "노래")
        );
        Profile profile1 = new Profile(dto1, hobbies1);
        profile1.saveToDatabase(connectDB);

        // 차은우
        ProfileDTO dto2 = new ProfileDTO("2", "차은우", 70, "MALE", "ESFJ", 180, 80);
        List<HobbyVO> hobbies2 = Arrays.asList(
            new HobbyVO(0, "자전거"),
            new HobbyVO(0, "독서")
        );
        Profile profile2 = new Profile(dto2, hobbies2);
        profile2.saveToDatabase(connectDB);

        // 권지염
        ProfileDTO dto3 = new ProfileDTO("3", "권지염", 75, "MALE", "ENTP", 195, 80);
        List<HobbyVO> hobbies3 = Arrays.asList(
            new HobbyVO(0, "독서"),
            new HobbyVO(0, "영화")
        );
        Profile profile3 = new Profile(dto3, hobbies3);
        profile3.saveToDatabase(connectDB);

        // 이민호
        ProfileDTO dto4 = new ProfileDTO("4", "이민호", 85, "MALE", "ESFJ", 188, 90);
        List<HobbyVO> hobbies4 = Arrays.asList(
            new HobbyVO(0, "독서"),
            new HobbyVO(0, "와인")
        );
        Profile profile4 = new Profile(dto4, hobbies4);
        profile4.saveToDatabase(connectDB);

        // 문종금
        ProfileDTO dto5 = new ProfileDTO("5", "문종금", 65, "MALE", "ESTJ", 180, 80);
        List<HobbyVO> hobbies5 = Arrays.asList(
            new HobbyVO(0, "술"),
            new HobbyVO(0, "담배")
        );
        Profile profile5 = new Profile(dto5, hobbies5);
        profile5.saveToDatabase(connectDB);
    }
}
