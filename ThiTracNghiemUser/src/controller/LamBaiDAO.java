/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.CauHoi;
import java.util.Random;

/**
 *
 * @author CT030408
 */
public class LamBaiDAO {

    private Connection conn;

    public LamBaiDAO() {
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

    public String docFile1() {
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

    public String docFile2() {
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
        return " - " + tenMon.toUpperCase();
    }

    public ArrayList<CauHoi> taiCauHoi() {
        ArrayList<CauHoi> list = new ArrayList<>();
        String sql1 = "SELECT TOP 16*FROM cauhoi where mamon=N'" + docFile1() + "'and macauhoi like 'C%' ORDER BY NEWID()";
        String sql2 = "SELECT TOP 16*FROM cauhoi where mamon=N'" + docFile1() + "'and macauhoi like 'B%' ORDER BY NEWID()";
        String sql3 = "SELECT TOP 8*FROM cauhoi where mamon=N'" + docFile1() + "'and macauhoi like 'A%' ORDER BY NEWID()";
        String s = null;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                s = sql1;
            } else if (i == 1) {
                s = sql2;
            } else {
                s = sql3;
            }
            try {
                PreparedStatement ps1 = conn.prepareStatement(s);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    CauHoi ch1 = new CauHoi();
                    ch1.setMaMon(rs1.getString("mamon"));
                    ch1.setMaCauHoi(rs1.getString("macauhoi"));
                    ch1.setCauHoi(rs1.getString("cauhoi"));
                    ch1.setA(rs1.getString("a"));
                    ch1.setB(rs1.getString("b"));
                    ch1.setC(rs1.getString("c"));
                    ch1.setD(rs1.getString("d"));
                    list.add(ch1);
                }
            } catch (SQLException e) {
            }
        }
        //Collections.shuffle(list);
        return daoDapAn(list);
    }

    public ArrayList<CauHoi> daoDapAn(ArrayList list) {
        ArrayList listDaoDapAn = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            CauHoi ch = (CauHoi) list.get(i);
            Random rd = new Random();
            ArrayList listKhongLap = new ArrayList();
            CauHoi ch1 = new CauHoi();
            while (true) {
                int a = rd.nextInt(4);
                if (!listKhongLap.contains(a)) {
                    listKhongLap.add(a);
                    if (a == 0) {
                        ch1.setA(ch.getA());
                    } else if (a == 1) {
                        ch1.setA(ch.getB());
                    } else if (a == 2) {
                        ch1.setA(ch.getC());
                    } else {
                        ch1.setA(ch.getD());
                    }
                    break;
                }
            }
            while (true) {
                int b = rd.nextInt(4);
                if (!listKhongLap.contains(b)) {
                    listKhongLap.add(b);
                    if (b == 0) {
                        ch1.setB(ch.getA());
                    } else if (b == 1) {
                        ch1.setB(ch.getB());
                    } else if (b == 2) {
                        ch1.setB(ch.getC());
                    } else {
                        ch1.setB(ch.getD());
                    }
                    break;
                }
            }
            while (true) {
                int c = rd.nextInt(4);
                if (!listKhongLap.contains(c)) {
                    listKhongLap.add(c);
                    if (c == 0) {
                        ch1.setC(ch.getA());
                    } else if (c == 1) {
                        ch1.setC(ch.getB());
                    } else if (c == 2) {
                        ch1.setC(ch.getC());
                    } else {
                        ch1.setC(ch.getD());
                    }
                    break;
                }
            }
            while (true) {
                int d = rd.nextInt(4);
                if (!listKhongLap.contains(d)) {
                    listKhongLap.add(d);
                    if (d == 0) {
                        ch1.setD(ch.getA());
                    } else if (d == 1) {
                        ch1.setD(ch.getB());
                    } else if (d == 2) {
                        ch1.setD(ch.getC());
                    } else {
                        ch1.setD(ch.getD());
                    }
                    break;
                }
            }
            listKhongLap.removeAll(listKhongLap);
            ch1.setMaMon(ch.getMaMon());
            ch1.setMaCauHoi(ch.getMaCauHoi());
            ch1.setCauHoi(ch.getCauHoi());
            listDaoDapAn.add(ch1);
        }
        return listDaoDapAn;
    }

    public void ghiFile(String[] maMon, String[] maCauHoi, String[] chonDapAn) {
        try {
            FileWriter fw = new FileWriter("MaMonMaCauHoiChonDapAn.dat");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < maMon.length; i++) {
                bw.append(maMon[i] + " - " + maCauHoi[i] + " - " + chonDapAn[i]);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
        }
    }
}
