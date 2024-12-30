import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class YouTubeSubscription {
	private String subscriptionId;
	private String channelName;
	private String userId;
	
	public void updateSubscriptions(String userId, List channels) {}

	public void addYoutubeSubscription(String name, String channelName,ConnectDB conn) {
		// 사용자 ID를 가져오는 SQL
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
		String insertSubscriptionSql = "INSERT INTO youtubesubscription (user_id, channel_name) VALUES ("+uid+",'"+channelName+"');";
		System.out.println(insertSubscriptionSql);

		conn.insertExecute(insertSubscriptionSql);
	}
}
