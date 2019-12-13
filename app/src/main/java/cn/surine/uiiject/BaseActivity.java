package cn.surine.uiiject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import cn.surine.uix.UisBaseActivity;
import cn.surine.uix.strategy.StrategyManager;


/**
 * Intro：BaseActivity for project
 *
 * @author sunliwei
 * @date 2019-06-18 14:27
 */
public abstract class BaseActivity extends UisBaseActivity {
    public static final String PARAM = "_param";

    /**
     * return the special layout
     *
     * @return layout id
     */
    public abstract int layoutId();


    /**
     * initialization work
     */
    public abstract void onInit();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //必须在super.onCreate(savedInstanceState);前添加自定义策略
        StrategyManager.getInstance().addUiStrategies(new TestUiStrategy());
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        onInit();
    }

    /**
     * start an activity with param
     *
     * @param context from
     * @param cls     to
     * @param param   bundle param
     */
    public void start(Context context, Class<? extends Activity> cls, Bundle param) {
        final Intent intent = new Intent(context, cls);
        if (null != param) {
            intent.putExtra(PARAM, param);
        }
        context.startActivity(intent);
    }

    /**
     * overload
     */
    public void start(Context context, Class<? extends Activity> cls) {
        start(context, cls, null);
    }

    /**
     * overload
     */
    public void start(Class<? extends Activity> cls) {
        start(this, cls);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }

}
