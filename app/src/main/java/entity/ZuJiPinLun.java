package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/18.
 */

public class ZuJiPinLun implements Serializable{
    String cmt_id;
    String fm_id;
    String user_id;
    String cmt_content;
    String city_addr;
    String up_num;
    String cmt_report;
    String addtime;
    String nu;
    String user_nick;
    String user_pic1;

    @Override
    public String toString() {
        return "ZuJiPinLun{" +
                "cmt_id='" + cmt_id + '\'' +
                ", fm_id='" + fm_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", cmt_content='" + cmt_content + '\'' +
                ", city_addr='" + city_addr + '\'' +
                ", up_num='" + up_num + '\'' +
                ", cmt_report='" + cmt_report + '\'' +
                ", addtime='" + addtime + '\'' +
                ", nu='" + nu + '\'' +
                ", user_nick='" + user_nick + '\'' +
                ", user_pic1='" + user_pic1 + '\'' +
                '}';
    }

    public String getCmt_id() {
        return cmt_id;
    }

    public void setCmt_id(String cmt_id) {
        this.cmt_id = cmt_id;
    }

    public String getFm_id() {
        return fm_id;
    }

    public void setFm_id(String fm_id) {
        this.fm_id = fm_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCmt_content() {
        return cmt_content;
    }

    public void setCmt_content(String cmt_content) {
        this.cmt_content = cmt_content;
    }

    public String getCity_addr() {
        return city_addr;
    }

    public void setCity_addr(String city_addr) {
        this.city_addr = city_addr;
    }

    public String getUp_num() {
        return up_num;
    }

    public void setUp_num(String up_num) {
        this.up_num = up_num;
    }

    public String getCmt_report() {
        return cmt_report;
    }

    public void setCmt_report(String cmt_report) {
        this.cmt_report = cmt_report;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_pic1() {
        return user_pic1;
    }

    public void setUser_pic1(String user_pic1) {
        this.user_pic1 = user_pic1;
    }
}
