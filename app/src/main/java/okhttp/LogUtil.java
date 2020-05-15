package okhttp;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class LogUtil {
    public static  final  boolean DEBUG = true;
    public static final String TAG = "TAG";
    private static LogUtil sLogUtil;

    private LogUtil() {
    }

    public static LogUtil getInstance() {
        if (sLogUtil == null) {
            synchronized (LogUtil.class) {
                if (sLogUtil == null) {
                    sLogUtil = new LogUtil();
                }
            }
        }
        return sLogUtil;
    }

    public static void d(String msg){
        if(DEBUG){
            Log.d(TAG,msg);
        }
    }

    public static void i(String msg){
        if(DEBUG){
            Log.i(TAG,msg);
        }
    }

    public static void e(String msg){
        if(DEBUG){
            Log.e(TAG,msg);
        }
    }

    public static void v(String msg){
        if(DEBUG){
            Log.w(TAG,msg);
        }
    }
}
