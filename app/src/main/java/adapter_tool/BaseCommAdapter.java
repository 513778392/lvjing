package adapter_tool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */

public abstract  class BaseCommAdapter<T> extends BaseAdapter
{
    private List<T> mDatas;

    public BaseCommAdapter(List<T> datas)
    {
        mDatas = datas;
    }
    @Override
    public int getCount()
    {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = ViewHolder
                .newsInstance(convertView, parent.getContext(), getLayoutId(),parent);

        setUI(holder,position,parent.getContext());

        return holder.getConverView();
    }

    protected abstract void setUI(ViewHolder holder, int position, Context context);

    protected abstract int getLayoutId();

}

