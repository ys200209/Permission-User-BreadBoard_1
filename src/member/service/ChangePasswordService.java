package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {
	
	private MemberDao memberDao = new MemberDao();
	
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // MySQL Board 데이터베이스에 대한 커넥션 획득.
			conn.setAutoCommit(false);
			
			Member member = memberDao.selectById(conn, userId); // 아이디를 이용해 회원이 존재하는지 확인하고 존재한다면, 회원의 데이터를 가져온다.
			if (member == null) {
				throw new MemberNotFoundException(); // 만약 회원이 존재하지 않는다면 에러를 발생시킨다.
			}
			if (!member.matchPassword(curPwd)) { // 회원의 비밀번호와 비밀번호 확인이 일치한지 체크하고
				throw new InvalidPasswordException(); // 일치하지 않는다면 에러를 발생시킨다.
			}
			member.changePassword(newPwd); // 회원의 비밀번호를 변경시켜주고
			memberDao.update(conn, member); // 데이터베이스상의 회원정보도 변경시켜준다.
			conn.commit(); // 변경된 사항을 저장한다.
		} catch (SQLException e) {
			JdbcUtil.rollback(conn); // 에러가 발생했다면 변경사항들을 롤백시킨다.
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
}
