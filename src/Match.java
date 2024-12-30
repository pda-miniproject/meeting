import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Match {
	
	
	public float calculateCompatibility(User user1, User user2) {
		return 3.0f; //임시값
	}
	
	public void activateMath() {}
	
	public void endMatch() {}
	
	public MatchDTO generateMatchReport() {
		return new MatchDTO();
	}
	public void searchMatching(String name , ConnectDB conn){
		String getUserIdSql = "SELECT user.user_id FROM user,profile WHERE user.user_id = profile.user_id and profile.nickname = ?";

		int uid = 0;


		try (Connection connection = conn.getConnection()) {
			// 사용자 ID 조회
			//Integer userId = null;
			try (PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSql)) {
				System.out.println("사용자 입력 이름: [" + name + "]");
				getUserIdStmt.setString(1, name); // 사용자 이름 설정
				try (ResultSet rs = getUserIdStmt.executeQuery()) {
					if (rs.next()) {
						uid = rs.getInt("user_id"); // user_id 가져오기
						System.out.println(uid);
					} else {
						System.out.println("해당 이름의 사용자를 찾을 수 없습니다: " + name);
						return;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String query = "SELECT p.nickname, u.user_id AS user_id, p.height, p.weight, p.mbti " +
				"FROM Profile p " +
				"JOIN User u ON p.user_id = u.user_id " +
				"JOIN Preference pref ON pref.user_profile_id =" +uid+" "+
				"LEFT JOIN UserHobby uh ON uh.user_profile_id = p.user_profile_id " +
				"AND p.gender != (SELECT gender FROM profile WHERE user_id = "+uid+") " +
				"AND p.height BETWEEN pref.height_min AND pref.height_max " +
				"AND p.weight BETWEEN pref.weight_min AND pref.weight_max " +
				"AND ( " +
				"uh.hobby_id IN ( " +
				"SELECT hobby_id " +
				"FROM UserHobby " +
				"WHERE user_profile_id = ( " +
				"SELECT user_profile_id " +
				"FROM Profile " +
				"WHERE user_id = "+uid+" " +
				") " +
				") " +
				") " +
				"AND u.user_id != "+uid+" " +
				"GROUP BY p.nickname, u.user_id, p.height, p.weight, p.mbti;";
		conn.selectExecute(query);

	}
	
}
