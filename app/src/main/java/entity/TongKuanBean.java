package entity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class TongKuanBean {
    /**
     * 顶部ViewPager
     */
    public static int INDEX_Title = 0;
    /**
     *
     */
    public static int INDEX_Two = 1;

    /**
     *
     */
    public static int INDEX_Three = 2;


    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
    List<TongKuanItemBean> data;

    public List<TongKuanItemBean> getData() {

        return data;
    }

    public void setData(List<TongKuanItemBean> data) {
        this.data = data;
    }
}
