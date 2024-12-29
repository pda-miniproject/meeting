public class TestProfile {
    public static void main(String[] args) {
        // ConnectDB 인스턴스 생성
        ConnectDB connectDB = new ConnectDB();

        // 권은비 DTO와 Profile 객체
        ProfileDTO dto1 = new ProfileDTO(
            "1", "1", "1", "1", "권은비", 60, "FEMALE", "ESTJ", 163, 65
        );
        Profile profile1 = new Profile(dto1);
        profile1.saveToDatabase(connectDB);

        // 차은우 DTO와 Profile 객체
        ProfileDTO dto2 = new ProfileDTO(
            "2", "2", "2", "2", "차은우", 70, "MALE", "ESFJ", 180, 80
        );
        Profile profile2 = new Profile(dto2);
        profile2.saveToDatabase(connectDB);

        // 권지언 DTO와 Profile 객체
        ProfileDTO dto3 = new ProfileDTO(
            "3", "3", "3", "3", "권지언", 75, "MALE", "ENTP", 195, 80
        );
        Profile profile3 = new Profile(dto3);
        profile3.saveToDatabase(connectDB);

        // 이민호 DTO와 Profile 객체
        ProfileDTO dto4 = new ProfileDTO(
            "4", "4", "4", "4", "이민호", 85, "MALE", "ESFJ", 188, 90
        );
        Profile profile4 = new Profile(dto4);
        profile4.saveToDatabase(connectDB);

        // 문종근 DTO와 Profile 객체
        ProfileDTO dto5 = new ProfileDTO(
            "5", "5", "5", "5", "문종근", 65, "MALE", "ESTJ", 180, 80
        );
        Profile profile5 = new Profile(dto5);
        profile5.saveToDatabase(connectDB);
    }
}
