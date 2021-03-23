/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import model.MonThi;

/**
 *
 * @author CT030408
 */
public class ChonMonThiDAO {

    private Connection conn;

    public ChonMonThiDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=thitracnghiem;username=sa;password=123456");
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public String docFile() {
        String maSVHoTen = null;
        try {
            FileInputStream fis = new FileInputStream("MaSVHoTen.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            maSVHoTen = (String) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
        }
        return maSVHoTen;
    }

    public ArrayList<MonThi> taiXuongThongTinMon() {
        ArrayList<MonThi> list = new ArrayList();
        String sql = "select mamon,tenmon from monthi";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MonThi mt = new MonThi();
                mt.setMaMon(rs.getString("mamon"));
                mt.setTenMon(rs.getString("tenmon"));
                list.add(mt);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public DefaultComboBoxModel themVaoComboBox() {
        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
        for (MonThi mt : taiXuongThongTinMon()) {
            boxModel.addElement(mt.getTenMon());
        }
        return boxModel;
    }

    public String kiemTraTenMon(String tenMon) {
        String s = null;
        for (MonThi mt : taiXuongThongTinMon()) {
            if (mt.getTenMon().equalsIgnoreCase(tenMon)) {
                s = mt.getMaMon();
            }
        }
        return s;
    }

    public boolean kiemTraDiem(String tenMon) {
        float diem = 0;
        String sql = "select diem from ketqua where masv='" + docFile1() + "'and mamon='" + kiemTraTenMon(tenMon) + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                diem = rs.getFloat("diem");
            }
        } catch (SQLException e) {
        }
        return diem == 0;
    }

    public boolean kiemTraTrangThai(String tenMon) {
        boolean trangthai = false;
        String sql = "select trangthai from cauhoi where mamon='" + kiemTraTenMon(tenMon) + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                trangthai = rs.getBoolean("trangthai");
            }
        } catch (SQLException e) {
        }
        return trangthai;
    }

    public void ghiFile(String tenMon) {
        try {
            FileOutputStream fos = new FileOutputStream("MaMonTenMon.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(kiemTraTenMon(tenMon) + " - " + tenMon);
            oos.close();
            fos.close();
        } catch (IOException e) {
        }
    }

    public String docFile1() {
        String maSV = null;
        try {
            FileInputStream fis = new FileInputStream("MaSVHoTen.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String s = (String) ois.readObject();
            String arr[] = s.split(" - ");
            maSV = arr[0];
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
        }
        return maSV;
    }

}
