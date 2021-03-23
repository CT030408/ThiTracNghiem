/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Statement;
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
public class ChoPhepThiDAO {

    private Connection conn;

    public ChoPhepThiDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=thitracnghiem;username=sa;password=123456");
        } catch (ClassNotFoundException | SQLException e) {
        }
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

    public boolean bat(String tenMon) {
        int rs = 0;
        String sql = "UPDATE cauhoi SET trangthai = '1' WHERE mamon ='" + kiemTraTenMon(tenMon) + "'";
        try {
            Statement statement = conn.createStatement();
            rs = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs != 0;
    }
    public boolean tat(String tenMon) {
        int rs = 0;
        String sql = "UPDATE cauhoi SET trangthai = '0' WHERE mamon ='" + kiemTraTenMon(tenMon) + "'";
        try {
            Statement statement = conn.createStatement();
            rs = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs != 0;
    }
}
