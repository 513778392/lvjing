package com.iyoyogo.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp.LogUtil;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.paizhao)
    void onClick(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                   /* PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setPreviewEnabled(false)
                            .start(MainActivity.this);*/
        int i = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
            LogUtil.v(i+"==============");
        if (i == PackageManager.PERMISSION_GRANTED) {
            LogUtil.v(i+"=============="+ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE));
            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                LogUtil.v("==============");
                ActivityUtils.goShowCameraActivity(MainActivity.this);
            }else {
                ActivityUtils.goShowCameraActivity(MainActivity.this);
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CAMERA);
            }

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

    } else {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_READ);
    }

    }
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_EXTERNAL_READ = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_READ && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        }
        if(grantResults[0] ==PackageManager.PERMISSION_GRANTED){
        }
    }

}
