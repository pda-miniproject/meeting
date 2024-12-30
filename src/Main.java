import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();

        String sql = "";
        Scanner sc = new Scanner(System.in);
        
        ProfileDTO profileDTO = new ProfileDTO("1", "권은비", 60, "Female", "ENFP", 165.0f, 60.0f);
        Profile profile = new Profile(profileDTO);
        
        while(true) {
            System.out.println("1: 프로필입력 2: 취미입력 3: 유튜브입력 4: 선호타입입력 5: 매칭상대 조회 6: 종료");
            System.out.print("입력: ");
            int command = sc.nextInt();

            if(command == 6) {
                break;
            }
            switch (command) {
                case 1://프로필 입력
                    profile.saveProfileToDatabase(db);
                    break;
                case 2://취미입력
                	
                	int generatedProfileId = profile.getGeneratedProfileId(db);
                    System.out.println("생성된 user_profile_id: " + generatedProfileId);
                    List<HobbyVO> hobbies = List.of(
                        new HobbyVO("요리"),
                        new HobbyVO("독서")
                    );
                    Profile.saveHobbiesToDatabase(db, generatedProfileId, hobbies);
                    break;
                case 3://유튜브 입력
                    sql = "";
                    db.insertExecute(sql);
                    break;
                case 4://선호타입입력
                    sql = "";
                    db.insertExecute(sql);
                    break;

                case 5://매칭상대조회
                    sql = "select * from user";
                    db.selectExecute(sql);
                    break;
                default:
                    System.out.println("유효한 명령어를 입력하세요.");
            }
        }

    }
}
