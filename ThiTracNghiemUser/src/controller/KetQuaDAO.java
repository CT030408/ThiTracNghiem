/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.SoSanh;

/**
 *
 * @author CT030408
 */
public class KetQuaDAO {

    private Connection conn;

    public KetQuaDAO() {
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

    public String docFile3() {
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

    public String docFile1() {
        String tenMon = null;
        try {
            FileInputStream fis = new FileInputStream("MaMonTenMon.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String s = (String) ois.readObject();
            String arr[] = s.split(" - ");
            tenMon = arr[1];
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
        }
        return " - " + tenMon;
    }

    public String docFile4() {
        String maMon = null;
        try {
            FileInputStream fis = new FileInputStream("MaMonTenMon.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String s = (String) ois.readObject();
            String arr[] = s.split(" - ");
            maMon = arr[0];
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
        }
        return maMon;
    }

    public ArrayList<SoSanh> docFile2() {
        ArrayList<SoSanh> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("MaMonMaCauHoiChonDapAn.dat");
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            while ((s = br.readLine()) != null) {
                String arr[] = s.split(" - ");
                SoSanh ss = new SoSanh(arr[0], arr[1], arr[2]);
                list.add(ss);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
        }
        return list;
    }

    public float tinhDiem() {
        Object arr[] = new Object[docFile2().size()];
        for (int i = 0; i < docFile2().size(); i++) {
            SoSanh ss = (SoSanh) docFile2().get(i);
            String sql = "SELECT a FROM cauhoi where mamon=N'" + ss.getMaMon() + "'and macauhoi= N'" + ss.getMaCauHoi() + "'";
            try {
                PreparedStatement ps1 = conn.prepareStatement(sql);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    arr[i] = rs1.getString("a");
                }
            } catch (SQLException e) {
            }
        }
        int dem = 0;
        for (int i = 0; i < docFile2().size(); i++) {
            SoSanh ss = (SoSanh) docFile2().get(i);
            if (ss.getChonDapAn().equals(arr[i])) {
                dem++;
            }
        }
        capNhatDiem(docFile3(), docFile4(), (float) 10 / arr.length * dem);
        return (float) 10 / arr.length * dem;
    }

    public void capNhatDiem(String maSV, String maMon, float diem) {
        System.out.println(maSV);
        System.out.println(maMon);
        System.out.println(diem);
        String sql = "insert into ketqua values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maSV);
            ps.setString(2, maMon);
            ps.setFloat(3, diem);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
