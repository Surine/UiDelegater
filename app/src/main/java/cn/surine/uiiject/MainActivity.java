package cn.surine.uiiject;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.surine.uix.UixDrawable;

public class MainActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onInit() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestUiStrategy.TTF = "x.otf";
                recreate();
            }
        });

        UixDrawable.Builder builder = new UixDrawable.Builder()
                .color(getResources().getColor(R.color.colorAccent))
                .radius(400);
        button.setBackground(builder.getLayer());


    }

}
