/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CT030408
 */
public class DanhSachSV {

    private String maSV;
    private String ho;
    private String ten;
    private String lop;
    private String matKhau;

    public DanhSachSV() {
    }

    public DanhSachSV(String maSV, String ho, String ten, String lop, String matKhau) {
        this.maSV = maSV;
        this.ho = ho;
        this.ten = ten;
        this.lop = lop;
        this.matKhau = matKhau;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
