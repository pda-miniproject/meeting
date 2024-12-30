import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Match {
	
	
	public float calculateCompatibility(User user1, User user2) {
		return 3.0f; //임시값
	}
	
	public void activateMath() {}
	
	public void endMatch() {}
	
	public MatchVO generateMatchReport() {
		return new MatchVO();
	}
	public void searchMatching(String name , ConnectDB conn){
		String getUserIdSql = "SELECT user.user_id FROM user,profile WHERE user.user_id = profile.user_id and profile.nickname = ?";

		int uid = 0;


		try (Connection connection = conn.getConnection()) {
		    // 사용자 ID 조회
		    try (PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSql)) {
		        System.out.println("사용자 입력 이름: [" + name + "]");
		        getUserIdStmt.setString(1, name);
		        try (ResultSet rs = getUserIdStmt.executeQuery()) {
		            if (rs.next()) {
		                uid = rs.getInt("user_id");
		                System.out.println(uid);
		            } else {
		                System.out.println("해당 이름의 사용자를 찾을 수 없습니다: " + name);
		                return;
		            }
		        }
		    }

		    // 매칭 검색 쿼리 실행
		    String query = "SELECT DISTINCT " +
		                   "    p.nickname, " +
		                   "    p.user_id, " +
		                   "    p.height, " +
		                   "    p.weight, " +
		                   "    p.mbti " +
		                   "FROM Profile p " +
		                   "JOIN Preference pref ON pref.user_profile_id = ? " +
		                   "LEFT JOIN UserHobby uh ON uh.user_profile_id = p.user_profile_id " +
		                   "WHERE p.gender != (SELECT gender FROM Profile WHERE user_id = ?) " +
		                   "  AND p.height BETWEEN pref.height_min AND pref.height_max " +
		                   "  AND p.weight BETWEEN pref.weight_min AND pref.weight_max " +
		                   "  AND p.mbti = pref.mbti " +
		                   "  AND uh.hobby_id = pref.hobby_id " +
		                   "  AND p.user_id != ?";

		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setInt(1, uid);
		        pstmt.setInt(2, uid);
		        pstmt.setInt(3, uid);

		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                String nickname = rs.getString("nickname");
		                int userId = rs.getInt("user_id");
		                float height = rs.getFloat("height");
		                float weight = rs.getFloat("weight");
		                String mbti = rs.getString("mbti");

		                System.out.println("매칭된 사용자: " + nickname + ", 키: " + height + ", 몸무게: " + weight + ", MBTI: " + mbti);
		            }
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}


	}
	
}
