package adapter_tool;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017/7/19.
 */

public class ViewHolder {
    /**
     * 保存所有itemview的集合
     */
    private SparseArray<View> mViews;

    private View mConvertView;

    private ViewHolder(Context context, int layoutId, ViewGroup parent)
    {
        mConvertView =  LayoutInflater.from(context).inflate( layoutId,parent, false);
        mConvertView.setTag(this);
        mViews = new SparseArray<>();
    }

    public static ViewHolder newsInstance(View convertView, Context context, int layoutId, ViewGroup parent)

    {
        if (convertView == null)
        {
            return new ViewHolder(context, layoutId,parent);

        } else
        {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 获取根view
     * @author 漆可
     * @date 2016-6-28 下午3:29:21
     * @return
     */
    public View getConverView()
    {
        return mConvertView;
    }

    /**
     * 获取节点view
     * @author 漆可
     * @date 2016-6-28 下午4:24:26
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getItemView(int id)
    {
        View view =  mViews.get(id);
        if (view == null)
        {
            view = mConvertView.findViewById(id);
            mViews.append(id, view);
        }

        return (T) view;
    }
}
