package cn.surine.uix;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Intro：
 *
 * @author sunliwei
 * @date 2019-12-10 20:45
 */
public class UisBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Uix.init(this);
        super.onCreate(savedInstanceState);
    }
}
