/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CT030408
 */
public class DangNhapDAO {

    Connection conn;

    public DangNhapDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=thitracnghiem;username=sa;password=123456");
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public boolean kiemTraDangNhap(String taiKhoan, String matKhau) {
        String tk = null, mk = null;
        String sql = "select taikhoan,matkhau from quantri where taikhoan='" + taiKhoan + "'and matkhau='" + matKhau + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tk = rs.getString("taikhoan");
                mk = (rs.getString("matkhau"));
            }
        } catch (SQLException e) {
        }
        return (tk != null && mk != null);
    }
}
