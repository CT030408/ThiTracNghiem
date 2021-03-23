/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DanhSachSV;

/**
 *
 * @author CT030408
 */
public class DangNhapDAO {

    private Connection conn;

    public DangNhapDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=thitracnghiem;username=sa;password=123456");
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public boolean kiemTraDangNhap(DanhSachSV dssv) {
        String maSV = null, hoTen = null;
        String sql = "select masv,ho,ten from danhsachsv where masv='" + dssv.getMaSV() + "'and matkhau='" + dssv.getMatKhau() + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                maSV = rs.getString("masv");
                hoTen = (rs.getString("ho")) + " " + (rs.getString("ten"));
            }
        } catch (SQLException e) {
        }
        ghiFile(maSV, hoTen);
        return (maSV != null && hoTen != null);
    }

    public void ghiFile(String maSV, String hoTen) {
        try {
            FileOutputStream fos = new FileOutputStream("MaSVHoTen.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(maSV + " - " + hoTen);
            oos.close();
            fos.close();
        } catch (IOException e) {
        }
    }
}
