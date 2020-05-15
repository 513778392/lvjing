package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iyoyogo.android.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class ShipinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
      Context context;
        List<Bitmap> data;
public ShipinAdapter(Context context, List<Bitmap> data){
        this.context = context;
        this.data = data;
        }
@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_shipin,parent,false);
        holder = new MyRecler(view);

        return holder;
        }

class MyRecler extends RecyclerView.ViewHolder{

    ImageView iv;
    public MyRecler(View view){
        super(view);

        iv = view.findViewById(R.id.iv);

    }
}
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        MyRecler hol = (MyRecler) holder;
        Bitmap s = data.get(position);
        hol.iv.setImageBitmap(s);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
