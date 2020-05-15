package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cj.videoeditor.gpufilter.FilterList;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterFactory;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.record.video.GPUImage;
import com.iyoyogo.android.MainApplication;
import com.iyoyogo.android.R;

import java.util.List;

import adapter_tool.BaseCommAdapter;
import adapter_tool.ViewHolder;
import viewlist.MLImageView;

/**
 * Created by Administrator on 2018/7/20.
 */

public class GuoLvAdapter extends BaseCommAdapter<MagicFilterType> {
    Bitmap bitmap =((BitmapDrawable) MainApplication.INSTANCE.getResources().getDrawable(
            R.mipmap.fengjing)).getBitmap();
    public GuoLvAdapter(List<MagicFilterType> data){
        super(data);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        MagicFilterType s = getItem(position);


        TextView text = holder.getItemView(R.id.text);
        text.setText(SlideGpuFilterGroup.FilterType2Name(s));
        text.getBackground().setAlpha(100);
        MLImageView imageView = holder.getItemView(R.id.image);
        MagicFilterType type = SlideGpuFilterGroup.types[position];
        Bitmap b = Bitmap.createBitmap(bitmap);
        if(MagicFilterType.NONE==type){
        }else {
            GPUImage image = new GPUImage(context);
            image.setImage(bitmap);
            image.setFilter(MagicFilterFactory.initFilters(type));
            b = image.getBitmapWithFilterApplied();
        }
        imageView.setImageBitmap(b);
        ImageView gou = holder.getItemView(R.id.gou);
        gou.setVisibility(View.GONE);
        if(weizhi ==position){
            gou.setVisibility(View.VISIBLE);
        }else {

            gou.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_guolv;
    }
    int weizhi =-1;

    public int getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(int weizhi) {
        this.weizhi = weizhi;
    }
}
