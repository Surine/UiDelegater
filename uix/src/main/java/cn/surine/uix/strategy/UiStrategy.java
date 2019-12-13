package cn.surine.uix.strategy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sunliwei
 * @date 2019-12-10 18:30
 */
public interface UiStrategy {

    /**
     * apply the ui effect
     *
     * @param context
     * @param attrs   UI attributes
     * @param view    target view
     * @return view modified view
     */
    View apply(Context context, AttributeSet attrs, View view);
}
