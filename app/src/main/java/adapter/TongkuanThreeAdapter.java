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

/**
 * Created by Administrator on 2018/7/26.
 */

public class TongkuanThreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TongKuanItemBean> data;
    int type;
    Context context;
    public TongkuanThreeAdapter(Context context, List<TongKuanItemBean> data, int type){
        this.type = type;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_tongkuan_three,parent,false);
        viewHolder = new TongkuanThree(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TongkuanThree itemViewHolder = (TongkuanThree) holder;

        itemViewHolder.imageView.setOnClickListener(new MyClick(itemViewHolder.itemView,position));
        Glide.with(context).load(data.get(position).getPath()).into(itemViewHolder.imageView);
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
    class TongkuanThree extends RecyclerView.ViewHolder{
        ImageView imageView;
        public TongkuanThree(View view){
            super(view);
            imageView = view.findViewById(R.id.three_iv);
        }
    }
}
