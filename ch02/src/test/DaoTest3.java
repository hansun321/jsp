package test;

import dao.DBC;
import dao.MariaDBCon;
import dto.Member;

import java.sql.*;
import java.util.Scanner;

//insertMember()
public class DaoTest3 {
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
        Scanner sc = new Scanner(System.in);
        System.out.println("가입할 회원의 아이디를 입력 : ");
        String id = sc.nextLine();
        String pw = "", name = "", tel = "", email = "";
        int cnt = 0;
        try {
            String sql = "select * from member where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("해당 아이디의 회원이 존재하므로 회원가입이 불가능합니다.");
            } else {
                rs.close();
                pstmt.close();
                System.out.println("비밀번호 입력 : ");
                pw = sc.nextLine();
                System.out.println("이름 입력 : ");
                name = sc.nextLine();
                System.out.println("전화번호 입력 : ");
                tel = sc.nextLine();
                System.out.println("이메일 입력 : ");
                email = sc.nextLine();

                sql = "insert into member(id, pw, name, tel, email)";
                sql += "values (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, pw);
                pstmt.setString(3, name);
                pstmt.setString(4, tel);
                pstmt.setString(5, email);
                cnt = pstmt.executeUpdate();

                if (cnt > 0) {
                    System.out.println("회원등록이 성공적으로 처리되었습니다.");
                } else {
                    System.out.println("회원가입 실패");
                }
                con.close(pstmt, conn);
            }
        } catch (SQLException e) {
            System.out.println("SQL 구문이 처리되지 못했습니다.");
        }

    }
}
