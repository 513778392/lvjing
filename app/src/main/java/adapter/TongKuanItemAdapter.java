package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;


import java.util.List;

import entity.TongKuanItemBean;
import okhttp.LogUtil;

/**
 * Created by Administrator on 2018/7/26.
 */

public class TongKuanItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TongKuanItemBean> data;
    int type;
    Context context;
    public TongKuanItemAdapter(Context context, List<TongKuanItemBean> data, int type){
        LogUtil.v(type+"=================");
        this.type = type;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view =null;

                    view = LayoutInflater.from(context).inflate(R.layout.list_tongkuan_titile, parent, false);


            viewHolder = new ItemViewHolder(view);
            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Glide.with(context).load(data.get(position).getPath()).into(itemViewHolder.tongkuan_iv);
                if (type == 0) {
                    itemViewHolder.one.setImageResource(R.drawable.no1);
                } else if (type == 1) {
                    if (position == 0) {
                        itemViewHolder.two.setImageResource(R.drawable.no2);
                    } else {
                        itemViewHolder.two.setImageResource(R.drawable.no3);
                    }
                }
                itemViewHolder.tongkuan_iv.setOnClickListener(new MyClick(itemViewHolder.itemView,position));

    }
    OnItemClick onItemClick;
    public void setOnclickItem(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
    class MyClick implements View.OnClickListener{
        View view ;
        int positon;
        public MyClick(View view , int positon){
            this.view = view;
            this.positon = positon;
        }
        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(v,positon,view);
        }
    }
    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView tongkuan_iv,one,two;
        ImageView three_iv;
        public ItemViewHolder(View view){
            super(view);
            if(type==2){
                three_iv = view.findViewById(R.id.three_iv);
            }else {
                two = view.findViewById(R.id.two);
                one = view.findViewById(R.id.one);
                tongkuan_iv = view.findViewById(R.id.tongkuan_iv);
            }
        }
    }

}
