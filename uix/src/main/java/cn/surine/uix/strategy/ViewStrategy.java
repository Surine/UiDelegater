package cn.surine.uix.strategy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import cn.surine.uix.R;
import cn.surine.uix.UixDrawable;

import static cn.surine.uix.R.styleable.ui_centerColor;
import static cn.surine.uix.R.styleable.ui_color;
import static cn.surine.uix.R.styleable.ui_dashGap;
import static cn.surine.uix.R.styleable.ui_dashWidth;
import static cn.surine.uix.R.styleable.ui_endColor;
import static cn.surine.uix.R.styleable.ui_focusColor;
import static cn.surine.uix.R.styleable.ui_gradient;
import static cn.surine.uix.R.styleable.ui_gradientRadius;
import static cn.surine.uix.R.styleable.ui_islock;
import static cn.surine.uix.R.styleable.ui_orientation;
import static cn.surine.uix.R.styleable.ui_pressColor;
import static cn.surine.uix.R.styleable.ui_radius;
import static cn.surine.uix.R.styleable.ui_rippleColor;
import static cn.surine.uix.R.styleable.ui_shape;
import static cn.surine.uix.R.styleable.ui_startColor;
import static cn.surine.uix.R.styleable.ui_strokeColor;
import static cn.surine.uix.R.styleable.ui_strokeWidth;


/**
 * Intro：
 *
 * @author sunliwei
 * @date 2019-12-11 17:04
 */
public class ViewStrategy implements UiStrategy {
    public static final int NORMAL_COLOR = Color.parseColor("#DDDDDD");

    @Override
    public View apply(Context context, AttributeSet attrs, View view) {


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ui);

        int N = typedArray.getIndexCount();
        if (N == 0) {
            return view;
        }

        UixDrawable.Builder builder = new UixDrawable.Builder();

        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == ui_shape) {
                builder.shape(typedArray.getInt(ui_shape, 0));
            } else if (attr == ui_color) {
                builder.color(typedArray.getColor(ui_color, NORMAL_COLOR));
            } else if (attr == ui_radius) {
                builder.radius(typedArray.getDimension(ui_radius, 0));
            } else if (attr == ui_pressColor) {
                builder.pressColor(typedArray.getColor(ui_pressColor, NORMAL_COLOR));
            } else if (attr == ui_focusColor) {
                builder.focusColor(typedArray.getColor(ui_focusColor, NORMAL_COLOR));
            } else if (attr == ui_startColor) {
                builder.startColor(typedArray.getColor(ui_startColor, NORMAL_COLOR));
            } else if (attr == ui_centerColor) {
                builder.centerColor(typedArray.getColor(ui_centerColor, NORMAL_COLOR));
            } else if (attr == ui_endColor) {
                builder.endColor(typedArray.getColor(ui_endColor, NORMAL_COLOR));
            } else if (attr == ui_gradient) {
                builder.gradient(typedArray.getInt(ui_gradient, 0));
            } else if (attr == R.styleable.ui_gradientRadius) {
                builder.gradientRadius(typedArray.getDimension(ui_gradientRadius,0));
            } else if (attr == R.styleable.ui_rippleColor) {
                builder.rippleColor(typedArray.getColor(ui_rippleColor, NORMAL_COLOR));
            } else if (attr == R.styleable.ui_strokeColor) {
                builder.strokeColor(typedArray.getColor(ui_strokeColor, NORMAL_COLOR));
            } else if (attr == R.styleable.ui_strokeWidth) {
                builder.strokeWidth(typedArray.getDimension(ui_strokeWidth,0));
            } else if (attr == R.styleable.ui_dashGap) {
                builder.dashGap(typedArray.getDimension(ui_dashGap,0));
            } else if (attr == R.styleable.ui_dashWidth) {
                builder.dashWidth(typedArray.getDimension(ui_dashWidth,0));
            } else if (attr == R.styleable.ui_orientation) {
                builder.orientation(typedArray.getInt(ui_orientation, 6));
            } else if (attr == R.styleable.ui_islock) {
                builder.lockPressColor(typedArray.getBoolean(ui_islock,true));
            }
        }

        //apply to all of the class and subclass of TextView
        if (view instanceof TextView) {
            view.setBackground(builder.getLayer());
        }

        typedArray.recycle();

        return view;
    }
}
