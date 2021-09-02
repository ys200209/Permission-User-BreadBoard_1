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
			conn = ConnectionProvider.getConnection(); // MySQL Board �����ͺ��̽��� ���� Ŀ�ؼ� ȹ��.
			conn.setAutoCommit(false);
			
			Member member = memberDao.selectById(conn, userId); // ���̵� �̿��� ȸ���� �����ϴ��� Ȯ���ϰ� �����Ѵٸ�, ȸ���� �����͸� �����´�.
			if (member == null) {
				throw new MemberNotFoundException(); // ���� ȸ���� �������� �ʴ´ٸ� ������ �߻���Ų��.
			}
			if (!member.matchPassword(curPwd)) { // ȸ���� ��й�ȣ�� ��й�ȣ Ȯ���� ��ġ���� üũ�ϰ�
				throw new InvalidPasswordException(); // ��ġ���� �ʴ´ٸ� ������ �߻���Ų��.
			}
			member.changePassword(newPwd); // ȸ���� ��й�ȣ�� ��������ְ�
			memberDao.update(conn, member); // �����ͺ��̽����� ȸ�������� ��������ش�.
			conn.commit(); // ����� ������ �����Ѵ�.
		} catch (SQLException e) {
			JdbcUtil.rollback(conn); // ������ �߻��ߴٸ� ������׵��� �ѹ��Ų��.
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
}
