package com.iyoyogo.android;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;


/**
 *
 * 创建时间：2018/5/26
 * 描述：
 */
public class BaseActivity extends AppCompatActivity {

    private int LOACTION_REQUEST_CODE = 1001;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager am = ActivityManager.getInstance();
        am.pushActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager am = ActivityManager.getInstance();
        am.popActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager am = ActivityManager.getInstance();
        am.clearRecord(this);
    }

    public void popAllActivity() {
        ActivityManager am = ActivityManager.getInstance();
        am.popAllActivity();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        if (requestCode == LOACTION_REQUEST_CODE) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    public void show(String text){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
    }
    ProgressDialog progressDialog;
    public void showProgressDialog( String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "请稍等", message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle("请稍等");
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    public void hideShijian(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
