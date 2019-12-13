package cn.surine.uix;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import cn.surine.uix.strategy.StrategyManager;
import cn.surine.uix.strategy.UiStrategy;

/**
 * Intro：
 * the factory to modify framework widgets
 *
 * @author sunliwei
 * @date 2019-12-10 18:12
 */
public class UixFactory implements LayoutInflater.Factory2 {

    private AppCompatDelegate mDelegate;

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (mDelegate == null) {
            return null;
        }
        View view = mDelegate.createView(parent, name, context, attrs);
        return strategy(context, attrs, view);
    }

    /**
     * apply the strategies
     */
    private View strategy(Context context, AttributeSet attrs, View view) {
        List<UiStrategy> strategyList = StrategyManager.getInstance().getStrategies();
        for (UiStrategy uiStrategy : strategyList) {
            uiStrategy.apply(context,attrs,view);
        }
        return view;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }


    public AppCompatDelegate getmDelegate() {
        return mDelegate;
    }

    public void setmDelegate(AppCompatDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }
}
