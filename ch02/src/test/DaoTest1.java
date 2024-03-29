package test;

import dao.DBC;
import dao.MariaDBCon;
import dto.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//getMemberList()
public class DaoTest1 {
    static Connection conn = null;
    static PreparedStatement pstmt = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        DBC con = new MariaDBCon();
        conn = con.connect();
        if (conn != null) {
            System.out.println("DB 연결 성공");
        }
        //getMemberList()
        try {
            String sql = "select * from  member";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> memList = new ArrayList<>();
            while (rs.next()) {
                Member mem = new Member();
                mem.setId(rs.getString("id"));
                mem.setPw(rs.getString("pw"));
                mem.setName(rs.getString("name"));
                mem.setEmail(rs.getString("email"));
                mem.setTel(rs.getString("tel"));
                mem.setPoint(rs.getInt("point"));
                memList.add(mem);
            }
            for (Member m : memList) {
                System.out.println(m.toString());
            }
        } catch (SQLException e) {
            System.out.println("SQL 구문이 처리되지 못했습니다.");
        } finally {
            con.close(rs, pstmt, conn);
        }

    }
}
