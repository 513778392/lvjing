package com.iyoyogo.android;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongkuanItemActivity extends BaseActivity {

    @OnClick({R.id.paitongkuan,R.id.back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.paitongkuan:
                if(null==path){
                    return;
                }
                if(path.equalsIgnoreCase(".mp4")){
                    show("请选择正确的图片类型");
                }else {
                    ActivityUtils.goTongKuanCameraActivity(this, path);
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    String path;
    @BindView(R.id.fengjing)
    ImageView fengjing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tongkuan_item);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("path");

        Glide.with(this).load(path).into(fengjing);

    }
}
