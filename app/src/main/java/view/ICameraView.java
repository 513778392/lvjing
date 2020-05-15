package view;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2018/7/20.
 */

public interface ICameraView {
    Activity getActivity();
    View getView();
    void onPosition(int position);
}
