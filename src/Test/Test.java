package Test;

import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

class Data {
    String name, number, address;

    public String getA() {
        return name;
    }

    public void setA(String a) {
        this.name = a;
    }

    public String getB() {
        return number;
    }

    public void setB(String b) {
        this.number = b;
    }

    public String getC() {
        return address;
    }

    public void setC(String c) {
        this.address = c;
    }
}

class Sqlc {
    private static Connection conn;
    private static PreparedStatement pstmt;

    Sqlc() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phone","root","1234");
    }
    void DataInsert(Data d) throws SQLException {
        try{
            pstmt = conn.prepareStatement("insert ignore into phone values (?,?,?);");
            pstmt.setString(1,d.getA());
            pstmt.setString(2,d.getB());
            pstmt.setString(3,d.getC());

            int num = pstmt.executeUpdate();
            System.out.println(num);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    void findSelect(String name) throws SQLException {
        String sql = "select * from phone where name = ?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,name);
        ResultSet rs =pstmt.executeQuery();

        while (rs.next()){

            System.out.println(rs.getString("name")+"/");
            System.out.println(rs.getString("phoneNumber")+"/");
            System.out.println(rs.getString("address")+"/");
            System.out.println();
        }
    }

    void deleteSelect(String name) throws SQLException {
        String sql = "delete from phone where name = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.executeUpdate();
    }

    void selectAll() throws SQLException {
        String sql = "select * from phone;";
        pstmt=conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("name")+"/");
            System.out.println(rs.getString("phoneNumber")+"/");
            System.out.println(rs.getString("address")+"/");
            System.out.println();
        }

    }
}

class InputClass {
    Data valueReturn(){
        Data d = new Data();

        Scanner scp = new Scanner(System.in);

        System.out.println("이름을 입력하세요: ");
        d.setA(scp.nextLine());
        System.out.println("전화번호를 입력하세요: ");
        d.setB(scp.nextLine());
        System.out.println("주소를 입력하세요: ");
        d.setC(scp.nextLine());

        return d;
    }

    String findString(){
        Scanner fname = new Scanner(System.in);
        System.out.println("이름검색: ");

        return fname.next();
    }

}

public class Test {
    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);

        Sqlc sq = new Sqlc();
        InputClass ic = new InputClass();

        while (true) {
            System.out.println("1.입력 2.검색 3.삭제 4.출력 5.종료");
            int num = sc.nextInt();

            if (num == 1) {
                sq.DataInsert(ic.valueReturn());

            } else if (num == 2) {
                sq.findSelect(ic.findString());

            } else if (num == 3) {
                sq.deleteSelect(ic.findString());

            } else if (num == 4) {
                sq.selectAll();

            } else if (num == 5) {
                System.out.println("프로그램을 종료합니다.");

            } else {
                System.out.println("잘못된 입력입니다.");
            }

        }
    }
}
