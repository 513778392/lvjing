package com.iyoyogo.android;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.cj.videoeditor.MyApplication;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class MainApplication extends MyApplication {
    public static MainApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        init();
    }

    private void init() {

        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        Bugly.init(this,"95635f1bb3",false,strategy);
    }

    private void initUM() {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("mzh", "onTerminate");
        //停止定位服务

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public  String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
