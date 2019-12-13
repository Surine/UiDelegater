package cn.surine.uiiject;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import cn.surine.uix.strategy.UiStrategy;

/**
 * Intro：
 *
 * @author sunliwei
 * @date 2019-12-11 17:19
 */
public class TestUiStrategy implements UiStrategy {
    public static String TTF = "lx.ttf";

    @Override
    public View apply(Context context, AttributeSet attrs, View view) {
        if (view instanceof TextView) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), TTF);
            ((TextView) view).setTypeface(typeface);
        }
        return view;
    }
}
