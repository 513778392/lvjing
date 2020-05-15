package okhttp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/2 0002.
 */
public class DengLu implements Serializable {
    String  token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DengLu{" +
                "token='" + token + '\'' +
                '}';
    }
}
