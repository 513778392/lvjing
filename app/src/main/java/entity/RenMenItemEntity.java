package entity;

/**
 * Created by Administrator on 2018/7/19.
 */

public class RenMenItemEntity {
    String addtime;
    String id;
    String searchContent;
    String searchNum;

    @Override
    public String toString() {
        return "RenMenItemEntity{" +
                "addtime='" + addtime + '\'' +
                ", id='" + id + '\'' +
                ", searchContent='" + searchContent + '\'' +
                ", searchNum='" + searchNum + '\'' +
                '}';
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(String searchNum) {
        this.searchNum = searchNum;
    }
}
