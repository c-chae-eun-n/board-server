package boardServer.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import boardServer.util.DBManager;
import boardServer.util.PasswordCrypto;

public class UserDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// UserDao 객체를 단일 인스턴스로 만들기 위해
	// Singleton Pattern 적용
	
	// 1. 생성자를 private으로
	private UserDao() {
//		setConnection();
	}
	
	// 2. 단일 인스턴스를 생성 (클래스 내부에서)
	private static UserDao instance = new UserDao();
	
	// 3. 단일 인스턴스에 대한 getter
	public static UserDao getInstance() {
		return instance;
	}
	
	public List<UserResponseDto> findUserAll() {
		List<UserResponseDto> list = new ArrayList<UserResponseDto>();
		
		try {
			conn = DBManager.getConnection();
			
			// 쿼리할 준비
			String sql = "SELECT id, email, name, birth, gender, country, telecom, phone, agree FROM users";
			pstmt = conn.prepareStatement(sql);
			
			// 쿼리 실행
			rs = pstmt.executeQuery();		// <- 결과가 rs 변수에 담김
			
			// 튜플 읽기
			while(rs.next()) {
				// database의 column index는 1부터 시작!
				String id = rs.getString(1);
				String email = rs.getString(2);
				String name = rs.getString(3);
				String birth = rs.getString(4);
				String gender = rs.getString(5);
				String country = rs.getString(6);
				String telecom = rs.getString(7);
				String phone = rs.getString(8);
				boolean agree = rs.getBoolean(9);
				
				UserResponseDto user = new UserResponseDto(id, email, name, birth, gender, country, telecom, phone, agree);
				list.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	// user가 null인지 아닌지에 따라 패스워드 일치 불일치 확인
	public UserResponseDto findUserByIdAndPassword(String id, String password) {
		UserResponseDto user = null;
		
		// 데이터베이스에 있는 암호화된 패스워드 str 을 얻어와
		// PasswordCrypto.decrypt(str) 를 통해
		// 일치 여부 확인 후
		// return
		
		try {
			System.out.println("findUserByIdAndPassword");
			conn = DBManager.getConnection();
			
			// 쿼리할 준비
			String sql = "SELECT id, email, name, birth, gender, country, telecom, phone, agree, password FROM users WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 쿼리 실행
			rs = pstmt.executeQuery();		// <- 결과가 rs 변수에 담김
			
			// 튜플 읽기
			if(rs.next()) {
				// database의 column index는 1부터 시작!
				String email = rs.getString(2);
				String name = rs.getString(3);
				String birth = rs.getString(4);
				String gender = rs.getString(5);
				String country = rs.getString(6);
				String telecom = rs.getString(7);
				String phone = rs.getString(8);
				boolean agree = rs.getBoolean(9);
				String encryptedPassword = rs.getString(10);
				
				if(PasswordCrypto.decrypt(password, encryptedPassword)) {
					// user 초기화
					user = new UserResponseDto(id, email, name, birth, gender, country, telecom, phone, agree);
					return user;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public boolean userExists(UserRequestDto userDto) {
		return findUserByIdAndPassword(userDto.getId(), userDto.getPassword()) != null;
	}
	
	public boolean userExists(String id) {
		return findUserById(id) != null;
	}
	
	public UserResponseDto createUser(UserRequestDto userDto) {
		// sql 구문을 쿼리하고
		// 쿼리한 실행의 결과 (ResultSet) 을 가져와
		// 성공을 했으면 -> UserResponseDto 객체 생성하여
		// 반환
		
		try {
			System.out.println("createUser");
			conn = DBManager.getConnection();
			
			String sql = "INSERT INTO  users(id, password, email, name, birth, gender, country, telecom, phone, agree) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		// <- 맵핑된 값이 들어갈 것이라고 하는 것 : ?
			
			pstmt = conn.prepareStatement(sql);
			
			// sql 구문에 맵핑할 값 설정
			pstmt.setString(1, userDto.getId());
			pstmt.setString(2, PasswordCrypto.encrypt(userDto.getPassword())); 	// PasswordCrypto.encrypt(userDto.getPassword()) -> 해싱된 암호값이 들어옴
			
			String email = userDto.getEmail().equals("") ? null : userDto.getEmail();
			pstmt.setString(3, email);
			
			pstmt.setString(4, userDto.getName());
			pstmt.setString(5, userDto.getBirth());
			pstmt.setString(6, userDto.getGender());
			pstmt.setString(7, userDto.getCountry());
			pstmt.setString(8, userDto.getTelecom());
			pstmt.setString(9, userDto.getPhone());
			pstmt.setBoolean(10, userDto.isAgree());
			
			pstmt.execute();
			
			return findUserByIdAndPassword(userDto.getId(), userDto.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return null;
	}
	
	public UserResponseDto updateUserPassword(UserRequestDto userDto, String newPassword) {
		UserResponseDto user = null;
		
		if(newPassword == null || newPassword.equals("")) {
			return user;
		}
		
		// 이때 쿼리 할 필요 X
		if(findUserByIdAndPassword(userDto.getId(), userDto.getPassword()) == null)
			return user;
		
		try {
			System.out.println("password");
			conn = DBManager.getConnection();
			
			String sql = "UPDATE users SET password=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, PasswordCrypto.encrypt(newPassword));
			pstmt.setString(2, userDto.getId());
			
			pstmt.execute();
			
			User userVo = findUserById(userDto.getId());
			
			user = new UserResponseDto(userVo);
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return user;
	}
	
	public UserResponseDto updateUserEmail(UserRequestDto userDto) {
		UserResponseDto user = null;
		
		if(findUserByIdAndPassword(userDto.getId(), userDto.getPassword()) == null)
			return user;
		
		try {
			conn = DBManager.getConnection();
			
			String sql = "UPDATE users SET email=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			// 맵핑하기
			pstmt.setString(1, userDto.getEmail());
			pstmt.setString(2, userDto.getId());
			
			// sql 구문 실행
			pstmt.execute();
			
			user = findUserByIdAndPassword(userDto.getId(), userDto.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return user;
	}
	
	public UserResponseDto updateUserPhone(UserRequestDto userDto) {
		UserResponseDto user = null;
		
		if(findUserByIdAndPassword(userDto.getId(), userDto.getPassword()) == null)
			return user;
		
		try {
			conn = DBManager.getConnection();
			
			String sql = "UPDATE users SET telecom=?, phone=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userDto.getTelecom());
			pstmt.setString(2, userDto.getPhone());
			pstmt.setString(3, userDto.getId());
			
			pstmt.execute();
			
			user = findUserByIdAndPassword(userDto.getId(), userDto.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return user;
	}
	
	// 패스워드가 일치하지 않으면 false가 됨
	public boolean deleteUser(UserRequestDto userDto) {
		
		if(findUserByIdAndPassword(userDto.getId(), userDto.getPassword()) == null) {
			return false;
		}
		
		try {
			conn = DBManager.getConnection();
			
			String sql = "DELETE FROM users WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userDto.getId());
			
			pstmt.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return false;
	}
	
	private User findUserById(String id) {
		User user = null;
		
		try {
			conn = DBManager.getConnection();
			
			// 쿼리할 준비
			String sql = "SELECT id, email, name, birth, gender, country, telecom, phone, agree, reg_date, mod_date FROM users WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 쿼리 실행
			rs = pstmt.executeQuery();		// <- 결과가 rs 변수에 담김
			
			// 튜플 읽기
			if(rs.next()) {
				// database의 column index는 1부터 시작!
				String email = rs.getString(2);
				String name = rs.getString(3);
				String birth = rs.getString(4);
				String gender = rs.getString(5);
				String country = rs.getString(6);
				String telecom = rs.getString(7);
				String phone = rs.getString(8);
				boolean agree = rs.getBoolean(9);
				Timestamp regDate = rs.getTimestamp(10);
				Timestamp modDate = rs.getTimestamp(11);
				
				// user 초기화
				user = new User(id, email, name, birth, gender, country, telecom, phone, agree, regDate, modDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return user;
	}
	
}
