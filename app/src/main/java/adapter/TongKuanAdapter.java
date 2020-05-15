package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iyoyogo.android.ActivityUtils;
import com.iyoyogo.android.R;

import java.util.List;

import entity.TongKuanBean;
import entity.TongKuanItemBean;


/**
 * Created by Administrator on 2018/7/26.
 */

public class TongKuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TongKuanBean> data;
    Context context;
    public TongKuanAdapter(Context context,List<TongKuanBean> data){
        this.data = data;
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if(viewType == TongKuanBean.INDEX_Title){
            view = LayoutInflater.from(context).inflate(R.layout.item_person_zuji_comment,parent,false);
            viewHolder = new TongkuanTitle(view);
            return viewHolder;
        }else  if(viewType == TongKuanBean.INDEX_Two){
            view = LayoutInflater.from(context).inflate(R.layout.item_person_zuji_comment,parent,false);
            viewHolder = new TongkuanTitle(view);
            return viewHolder;
        }else  if(viewType == TongKuanBean.INDEX_Three){
            view = LayoutInflater.from(context).inflate(R.layout.item_person_zuji_comment,parent,false);
            viewHolder = new TongkuanThree(view);
            return viewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TongkuanTitle ){
            setItemValues((TongkuanTitle) holder,position);
        }else if(holder instanceof TongkuanThree){
            setThreeValues((TongkuanThree) holder,position);
        }
    }
    private void setItemValues(TongkuanTitle tongkuanViewHolder,final int position) {

        if(position ==0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            tongkuanViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        }else if(position ==1){
            GridLayoutManager linearLayoutManager = new GridLayoutManager(context,2);
            tongkuanViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        }

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.line));
            tongkuanViewHolder.recyclerView.addItemDecoration(dividerItemDecoration);
            TongKuanItemAdapter adapter = new TongKuanItemAdapter(context, data.get(position).getData(), position);
            tongkuanViewHolder.recyclerView.setAdapter(adapter);
        adapter.setOnclickItem(new OnItemClick() {
            @Override
            public void onItemClick(View v, int postion, View view) {
                ActivityUtils.goTongkuanItemActivity(context,data.get(position).getData().get(postion).getPath());
            }
        });


    }
    private void setThreeValues(TongkuanThree tongkuanViewHolder, int position) {
       final   List<TongKuanItemBean> bean = data.get(position).getData();
            GridLayoutManager linearLayoutManager = new GridLayoutManager(context,3);
            tongkuanViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.line));
        tongkuanViewHolder.recyclerView.addItemDecoration(dividerItemDecoration);
        TongkuanThreeAdapter adapter = new TongkuanThreeAdapter(context, bean, position);
        tongkuanViewHolder.recyclerView.setAdapter(adapter);
        adapter.setOnclickItem(new OnItemClick() {
            @Override
            public void onItemClick(View v, int postion, View view) {
                ActivityUtils.goTongkuanItemActivity(context,bean.get(postion).getPath());
            }
        });

    }
    class TongkuanTitle extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public TongkuanTitle(View view){
            super(view);
            recyclerView = view.findViewById(R.id.recyclerView_comment);
        }
    }
    class TongkuanThree extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public TongkuanThree(View view){
            super(view);
            recyclerView = view.findViewById(R.id.recyclerView_comment);
        }
    }



    @Override
    public int getItemCount() {
        return data ==null? 0:data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }
}
