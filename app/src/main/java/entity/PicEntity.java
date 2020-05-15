package entity;

/**
 * Created by Administrator on 2018/7/18.
 */

public class PicEntity {
    String fm_pic_id;
    String fm_id;
    String city_addr;
    String start_date;
    String end_date;
    String pic_addr1;
    String pic_addr2;
    String addtime;
    String user_id;
    String remark;

    @Override
    public String toString() {
        return "PicEntity{" +
                "fm_pic_id='" + fm_pic_id + '\'' +
                ", fm_id='" + fm_id + '\'' +
                ", city_addr='" + city_addr + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", pic_addr1='" + pic_addr1 + '\'' +
                ", pic_addr2='" + pic_addr2 + '\'' +
                ", addtime='" + addtime + '\'' +
                ", user_id='" + user_id + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getFm_pic_id() {
        return fm_pic_id;
    }

    public void setFm_pic_id(String fm_pic_id) {
        this.fm_pic_id = fm_pic_id;
    }

    public String getFm_id() {
        return fm_id;
    }

    public void setFm_id(String fm_id) {
        this.fm_id = fm_id;
    }

    public String getCity_addr() {
        return city_addr;
    }

    public void setCity_addr(String city_addr) {
        this.city_addr = city_addr;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPic_addr1() {
        return pic_addr1;
    }

    public void setPic_addr1(String pic_addr1) {
        this.pic_addr1 = pic_addr1;
    }

    public String getPic_addr2() {
        return pic_addr2;
    }

    public void setPic_addr2(String pic_addr2) {
        this.pic_addr2 = pic_addr2;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
