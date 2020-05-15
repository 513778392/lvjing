package com.iyoyogo.android;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import adapter.TongKuanAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.TongKuanBean;
import entity.TongKuanItemBean;
import okhttp.LogUtil;

public class TongKuanActivity extends BaseActivity {
    @BindView(R.id.tongkuan_recycler)
    RecyclerView tongkuan_recycler;
    List<TongKuanBean> data;
    TongKuanAdapter adapter;
    @OnClick(R.id.tongkuan_iv)
    void onClick(View view){
       finish();
    }
    File sdDir = Environment.getExternalStorageDirectory();
    File path = new File(sdDir+File.separator
            +"Pictures/record");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tong_kuan);
        ButterKnife.bind(this);

        setData();
        setList();
        //getData();
        adapter = new TongKuanAdapter(this,data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        tongkuan_recycler.setLayoutManager(manager);

        tongkuan_recycler.setAdapter(adapter);

    }
    private void setList(){
        List<TongKuanItemBean> list = new ArrayList<>();
        List<String> ss = getFileName(path.getPath());
        for (int i = 0; i <ss.size() ; i++) {
            TongKuanItemBean bean = new TongKuanItemBean();
            bean.setPath(ss.get(i));
            list.add(bean);
        }
        TongKuanBean bean2 = new TongKuanBean();
        bean2.setData(list);
        bean2.setViewType(TongKuanBean.INDEX_Three);
        data.add(bean2);
        if(ss.size()>1) {
            data.get(0).getData().get(0).setPath(ss.get(0));
            data.get(1).getData().get(0).setPath(ss.get(0));
            data.get(1).getData().get(1).setPath(ss.get(1));
        }
    }

    private void setData(){
        data = new ArrayList<>();
        TongKuanBean bean = new TongKuanBean();
        bean.setData(setList(1));
        bean.setViewType(TongKuanBean.INDEX_Title);
        data.add(bean);
        TongKuanBean bean1 = new TongKuanBean();
        bean1.setData(setList(2));
        bean1.setViewType(TongKuanBean.INDEX_Two);
        data.add(bean1);
    }
    private List<TongKuanItemBean> setList(int size){
        List<TongKuanItemBean> list = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            list.add(new TongKuanItemBean());
        }
        return list;
    }

    private void getData(){
        List<TongKuanItemBean> list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data1 = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            LogUtil.v(new String(data1, 0, data1.length - 1)+"============="+name+"+========"+desc);
            String path = new String(data1, 0, data1.length - 1);
          /*  names.add(name);
            descs.add(desc);
            fileNames.add(new String(data, 0, data.length - 1));*/
            TongKuanItemBean bean = new TongKuanItemBean();
            bean.setPath(path);
            list.add(bean);
        }
        TongKuanBean bean2 = new TongKuanBean();
        bean2.setData(list);
        bean2.setViewType(TongKuanBean.INDEX_Three);
        data.add(bean2);
    }
    public static Vector<String> getFileName(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if(null==subFile){
            return vecFile;
        }
        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                vecFile.add(subFile[iFileLength].getPath());
            }else {

            }
        }
        return vecFile;
    }



}
