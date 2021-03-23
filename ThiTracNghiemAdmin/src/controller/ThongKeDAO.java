/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import model.MonThi;
import model.ThongKe;

/**
 *
 * @author CT030408
 */
public class ThongKeDAO {

    Connection conn;

    public ThongKeDAO() {
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

    public String thongKe(String tenMon) {
        ArrayList<ThongKe> list = new ArrayList<>();
        String sql = "select diem from ketqua where mamon='" + kiemTraTenMon(tenMon) + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setDiem(rs.getFloat("diem"));
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        float demG = 0, demK = 0, demTB = 0, demY = 0;
        for (int i = 0; i < list.size(); i++) {
            ThongKe tk = (ThongKe) list.get(i);
             System.out.println(tk.getDiem());
            if (tk.getDiem() >= 8) {
                demG++;
            } else if (tk.getDiem() >= 6.5) {
                demK++;
            } else if (tk.getDiem() >= 4) {
                demTB++;
            } else {
                demY++;
            }
        }
        return "Gioi: " + (demG / list.size()) * 100 + "% - " + "Kha: " + (demK / list.size()) * 100 + "% - " + "Trung binh: " + (demTB
                / list.size()) * 100 + "% - " + "Yeu: " + (demY / list.size()) * 100 + "%";
    }
}
