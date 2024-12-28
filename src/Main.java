import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();

        String sql = "";
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("1: 프로필입력 2: 취미입력 3: 유튜브입력 4: 선호타입입력 5: 매칭상대 조회 6: 종료");
            System.out.print("입력: ");
            int command = sc.nextInt();

            if(command == 6) {
                break;
            }
            switch (command) {
                case 1://프로필 입력
                    sql = "Insert into user (user_id,password,phone,tier_id) values(1,'1234','010-1111',1)";//입의로 유저 생성sql 임
                    db.insertExecute(sql);
                    break;
                case 2://취미입력
                    sql = "";
                    db.insertExecute(sql);
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
