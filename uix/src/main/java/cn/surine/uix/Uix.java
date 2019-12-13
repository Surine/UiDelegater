package cn.surine.uix;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Intro：Uix
 *
 * @author sunliwei
 * @date 2019-12-10 17:56
 */
public class Uix {

    public static final String TAG = "Uix";

    /**
     * add the ui delegate to the activity
     *
     * @param context the context of activity
     * @return
     */
    public static LayoutInflater init(Context context) {
        LayoutInflater layoutInflater = null;
        if (context instanceof AppCompatActivity) {
            layoutInflater = ((Activity) context).getLayoutInflater();
        } else if (context instanceof Activity) {
            layoutInflater = LayoutInflater.from(context);
        }
        if (layoutInflater == null) {
            return null;
        }
        if (layoutInflater.getFactory2() == null) {
            UixFactory factory = getUixFactory(context);
            layoutInflater.setFactory2(factory);
        } else if (!(layoutInflater.getFactory2() instanceof UixFactory)) {
            Log.i(TAG, "The Activity's LayoutInflater already has a Factory installed"
                    + " so we can not install AppCompat's");
        }
        return layoutInflater;
    }


    /**
     * get the factory for LayoutInflater
     * @param context the context of activity
     * */
    private static UixFactory getUixFactory(Context context) {
        UixFactory uixFactory = new UixFactory();
        if(context instanceof AppCompatActivity){
            AppCompatDelegate delegate = ((AppCompatActivity)context).getDelegate();
            uixFactory.setmDelegate(delegate);
        }
        return uixFactory;
    }

}
