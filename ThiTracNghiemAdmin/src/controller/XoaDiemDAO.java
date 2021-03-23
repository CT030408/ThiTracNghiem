/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author CT030408
 */
public class XoaDiemDAO {

    Connection conn;

    public XoaDiemDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=thitracnghiem;username=sa;password=123456");
        } catch (Exception e) {

        }
    }

    public boolean xoa(String maSV) {
        int rs = 0;
        try {
            Statement statement = conn.createStatement();
            String sql = "delete from ketqua where masv='" + maSV + "'";
            rs = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs != 0;
    }
}
