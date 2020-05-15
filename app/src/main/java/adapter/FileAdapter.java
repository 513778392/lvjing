package adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;


import java.io.File;
import java.util.List;

import entity.PhotoDirectory;
import viewlist.SquareItemLayout;

public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<PhotoDirectory> data;
    String type;
    public FileAdapter(Context context, List<PhotoDirectory> data, String type){
        this.context = context;
        this.data = data;
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.__picker_item_photo,parent,false);
        MyViwHolder myViwHolder = new MyViwHolder(view);

        return myViwHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViwHolder holder1 = (MyViwHolder) holder;
        if(type.equals("图片")) {
            Glide.with(context).load(data.get(position).getCoverPath()).into(holder1.iv_photo);
        }else {
            final RequestOptions options = new RequestOptions();
            options.dontAnimate()
                    .dontTransform()
                    .placeholder(R.mipmap.__picker_ic_photo_black_48dp)
                    .error(R.mipmap.__picker_ic_broken_image_black_48dp);
            Glide
                    .with(context)
                    .load(Uri.fromFile(new File(data.get(position).getCoverPath())))
                    .into(holder1.iv_photo);

        }
            holder1.iv_photo.setSelected(data.get(position).isXuan());
        holder1.v_selected.setSelected(data.get(position).isXuan());

        holder1.square.setOnClickListener(new MyOnClick(position,holder1.itemView));

    }
    public static Bitmap getVideoThumb(String path) {
        try {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            return media.getFrameAtTime();
        }catch (Exception e){

        }
        return null;
    }
    class MyOnClick implements View.OnClickListener{
        int pos;
        View v;
        public MyOnClick(  int pos, View v){
            this.pos =pos;
            this.v=v;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(pos,v);
        }
    }
    public interface OnClick{
        void onClick(int pos, View v);
    }
    OnClick onClickListener;
    public void setOnClickListener(OnClick onClickListener){
        this.onClickListener = onClickListener;
    }


    class MyViwHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo,v_selected;
        SquareItemLayout square;
        public MyViwHolder(View view){
            super(view);
            square = view.findViewById(R.id.square);
            iv_photo = view.findViewById(R.id.iv_photo);
            v_selected = view.findViewById(R.id.v_selected);
        }
    }

    @Override
    public int getItemCount() {
        return data ==null?0:data.size();
    }
}
