import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Preference {
	
    public void addPreference(String name, String mbti,float minHeight, float maxHeight, float minWeight, float maxWeight, String preferredHobbies, ConnectDB conn) {
        // 사용자 ID를 가져오는 SQL
        String getUserIdSql = "SELECT user_profile_id FROM profile WHERE profile.nickname = ?";
        Integer userId = null;
        
        try (Connection connection = conn.getConnection()) {
           // 사용자 ID 조회
           
           try (PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSql)) {
              getUserIdStmt.setString(1, name); // 사용자 이름 설정
              try (ResultSet rs = getUserIdStmt.executeQuery()) {
            	  
                 if (rs.next()) {
                    userId = rs.getInt("user_profile_id");
                    
                    
                 } else {
                    System.out.println("해당 이름의 사용자를 찾을 수 없습니다: " + name);
                    return;
                 }
              }
           }

        } catch (Exception e) {
           e.printStackTrace();
        }
        Integer HobbyId = null;
        
        String hobbySql = "SELECT hobby_id FROM hobby WHERE ? = hobby_name";
        try (Connection connection = conn.getConnection()) {
            // 사용자 ID 조회
            
            try (PreparedStatement getHobbyIdStmt = connection.prepareStatement(hobbySql)) {
            	getHobbyIdStmt.setString(1, preferredHobbies); // 사용자 이름 설정
               try (ResultSet rs = getHobbyIdStmt.executeQuery()) {
                  if (rs.next()) {
                     HobbyId = rs.getInt("hobby_id");
                     
                     
                  } else {
                     System.out.println("취미 id 찾는중 오류발생.");
                     return;
                  }
               }
            }

         } catch (Exception e) {
            e.printStackTrace();
         }
       
        
        
        String preferenceSql = String.format(
                "INSERT INTO preference (user_profile_id, mbti, height_min, height_max, weight_min, weight_max, hobby_id) VALUES ('%d', '%s', %f, '%f', '%f', %f, %d)",
                userId, mbti, minHeight, maxHeight, minWeight, maxWeight, HobbyId);
        conn.insertExecute(preferenceSql);

    }
}
