import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();

        String sql = "";
        Scanner sc = new Scanner(System.in);
        
        YouTubeSubscription youTubeSubscription = new YouTubeSubscription();
        Profile profile = new Profile();
        
        Match match = new Match();

        while(true) {
            System.out.println("1: 프로필입력 2: 취미입력 3: 유튜브입력 4: 선호타입입력 5: 매칭상대 조회 6: 종료");
            System.out.print("입력: ");
            int command = sc.nextInt();
            sc.nextLine();

            if(command == 6) {
                break;
            }
            switch (command) {
                case 1: // 프로필 입력
                    System.out.print("Nickname: ");
                    String nickname = sc.nextLine();

                    System.out.print("Rating (float): ");
                    float rating = sc.nextFloat();
                    sc.nextLine(); // Consume leftover newline character

                    System.out.print("Gender: ");
                    String gender = sc.nextLine();

                    System.out.print("MBTI: ");
                    String mbti = sc.nextLine();

                    System.out.print("Height (float): ");
                    float height = sc.nextFloat();
                    sc.nextLine(); // Consume leftover newline character

                    System.out.print("Weight (float): ");
                    float weight = sc.nextFloat();
                    sc.nextLine(); // Consume leftover newline character

                    ProfileVO vo = new ProfileVO(nickname, rating, gender, mbti, height, weight);
                    profile.saveProfile(db, vo);
                    break;

                case 2: // 취미 입력
                    System.out.print("Nickname for hobbies: ");
                    String nicknameForHobbies = sc.nextLine();

                    int userProfileId = profile.getGeneratedProfileIdByNickname(db, nicknameForHobbies);

                    if (userProfileId == 0) {
                        System.out.println("해당 Nickname으로 프로필을 찾을 수 없습니다.");
                        break;
                    }

                    System.out.print("취미를 입력하세요 (쉼표로 구분): ");
                    String hobbiesInput = sc.nextLine();
                    List<String> hobbies = new ArrayList<>();
                    for (String hobby : hobbiesInput.split(",")) {
                        hobbies.add(hobby.trim());
                    }

                    Profile.saveHobbies(db, userProfileId, hobbies);
                    break;
                case 3://유튜브 입력
                    sql = "";
                    db.insertExecute(sql);
                    break;
                case 4://선호타입입력
                	Scanner scanner = new Scanner(System.in);
                	System.out.print("이름을 입력하세요 : ");
                	String name = scanner.nextLine();
                	System.out.print("선호하는 mbti를 입력하세요 : ");
                	String mbti = scanner.nextLine();
                	System.out.print("선호하는 최소 키를 입력하세요 : ");
                	float minHeight = scanner.nextFloat();
                	System.out.print("선호하는 최대 키를 입력하세요 : ");
                    float maxHeight = scanner.nextFloat();
                    System.out.print("선호하는 최소 몸무게를 입력하세요 : ");
                    float minWeight = scanner.nextFloat();
                    System.out.print("선호하는 최대 몸무게를 입력하세요 : ");
                    float maxWeight = scanner.nextFloat();
                    System.out.print("선호하는 취미를 입력하세요 : ");
                    scanner.nextLine();
                    String preferredHobbies = scanner.nextLine();
                    preference.addPreference(name, mbti, minHeight, maxHeight, minWeight, maxWeight,preferredHobbies, db);
                    break;

                case 5://매칭상대조회
                    System.out.print("조회할 유저이름:");
                    String name = sc.nextLine();
                    System.out.println(name);
                    match.searchMatching(name,db);
                    break;
                default:
                    System.out.println("유효한 명령어를 입력하세요.");
            }
        }
        sc.close();
    }
}