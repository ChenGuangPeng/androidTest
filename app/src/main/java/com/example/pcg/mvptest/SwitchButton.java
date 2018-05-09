package com.example.pcg.mvptest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Mr_Peng
 * @created at 2017-08-26.
 * @describe: 仿iso滑动开关
 */

public class SwitchButton extends View {

    private SwitchListener switchListener;

    public interface SwitchListener{
        void switchSelect(boolean selected);
    }
    public SwitchButton(Context context) {
        super(context);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
