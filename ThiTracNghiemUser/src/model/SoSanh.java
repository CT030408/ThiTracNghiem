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
public class SoSanh {

    private String maMon;
    private String maCauHoi;
    private String chonDapAn;

    public SoSanh() {
    }

    public SoSanh(String maMon, String maCauHoi, String chonDapAn) {
        this.maMon = maMon;
        this.maCauHoi = maCauHoi;
        this.chonDapAn = chonDapAn;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getMaCauHoi() {
        return maCauHoi;
    }

    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    public String getChonDapAn() {
        return chonDapAn;
    }

    public void setChonDapAn(String chonDapAn) {
        this.chonDapAn = chonDapAn;
    }

    @Override
    public String toString() {
        return "SoSanh{" + "maMon=" + maMon + ", maCauHoi=" + maCauHoi + ", chonDapAn=" + chonDapAn + '}';
    }
    
}
