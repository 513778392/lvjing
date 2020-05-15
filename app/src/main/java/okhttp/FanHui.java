package okhttp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1 0001.
 */
 public class FanHui<T> implements Serializable{
    String code;
    T data;

    @Override
    public String toString() {
        return "FanHui{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
