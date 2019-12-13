package cn.surine.uix;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

import androidx.annotation.RequiresApi;

import static cn.surine.uix.strategy.ViewStrategy.NORMAL_COLOR;

/**
 * Intro：Drawable Manager
 *
 * @author sunliwei
 * @date 2019-12-10 19:52
 */
public class UixDrawable {

    public enum Shape {

        /**
         * annotation for suppress warning
         */
        Rectangle(0), Oval(1), Line(2), Ring(3);

        int value;

        Shape(int value) {
            this.value = value;
        }

    }

    public enum Gradient {
        /**
         * annotation for suppress warning
         */
        Linear(0), Radial(1), Sweep(2);

        int value;

        Gradient(int value) {
            this.value = value;
        }
    }


    public static final class Builder {
        Shape shape = Shape.Rectangle;
        int color = NORMAL_COLOR;
        int pressColor = NORMAL_COLOR;
        int focusColor = NORMAL_COLOR;
        int rippleColor = NORMAL_COLOR;
        int strokeColor = NORMAL_COLOR;
        int startColor = NORMAL_COLOR;
        int centerColor = NORMAL_COLOR;
        int endColor = NORMAL_COLOR;

        float radius = 0;
        float[] cornerArray;

        float strokeWidth = 0;
        float dashGap = 0;
        float dashWidth = 0;

        Gradient gradient = Gradient.Linear;
        float gradientRadius = 0;
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        int[] gradientColors;

        boolean isLock = true;


        public Builder() {
        }


        public Builder shape(Shape shape) {
            this.shape = shape;
            return this;
        }

        public Builder shape(int i) {
            this.shape = Shape.values()[i];
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder pressColor(int pressColor) {
            this.pressColor = pressColor;
            return this;
        }

        public Builder focusColor(int focusColor) {
            this.focusColor = focusColor;
            return this;
        }

        public Builder rippleColor(int rippleColor) {
            this.rippleColor = rippleColor;
            return this;
        }

        public Builder strokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder startColor(int startColor) {
            this.startColor = startColor;
            return this;
        }

        public Builder centerColor(int centerColor) {
            this.centerColor = centerColor;
            return this;
        }

        public Builder endColor(int endColor) {
            this.endColor = endColor;
            return this;
        }

        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder cornerArray(float[] cornerArray) {
            this.cornerArray = cornerArray;
            return this;
        }

        public Builder strokeWidth(float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder dashGap(float dashGap) {
            this.dashGap = dashGap;
            return this;
        }

        public Builder dashWidth(float dashWidth) {
            this.dashWidth = dashWidth;
            return this;
        }

        public Builder gradient(Gradient gradient) {
            this.gradient = gradient;
            return this;
        }

        public Builder gradient(int i) {
            this.gradient = Gradient.values()[i];
            return this;
        }

        public Builder gradientRadius(float gradientRadius) {
            this.gradientRadius = gradientRadius;
            return this;
        }

        public Builder orientation(GradientDrawable.Orientation gradientOrientation) {
            this.orientation = gradientOrientation;
            return this;
        }

        public Builder orientation(int i) {
            this.orientation = GradientDrawable.Orientation.values()[i];
            return this;
        }

        public Builder gradientColors(int[] gradientColors) {
            this.gradientColors = gradientColors;
            return this;

        }

        public Builder lockPressColor(boolean lockPressColor) {
            isLock = lockPressColor;
            return this;
        }


        /**
         * parse the drawable
         *
         * @param drawable
         */
        public Builder parseBackground(Drawable drawable) {
            try {
                LayerDrawable lyDrawable = (LayerDrawable) drawable;
                StateListDrawable stateListDrawable = (StateListDrawable) lyDrawable.getDrawable(0);
                RippleDrawable rippleDrawable = (RippleDrawable) lyDrawable.getDrawable(1);

                return this;
            }catch (Exception e){
                throw new IllegalArgumentException("drawable is not config by uix,need a instance of layer drawable");
            }
        }


        /**
         * get the build result of ripple layer
         * invoke this method can get the ripple layer
         * Note：may be of little use of you，it's up to your application scene
         * <p>
         * only for LOLLIPOP （Material Design Support）
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public RippleDrawable getRipple() {
            int[][] stateList = new int[][]{
                    new int[]{android.R.attr.state_pressed},
                    new int[]{}
            };

            int[] stateColorList = new int[]{
                    rippleColor,
                    rippleColor
            };
            ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

            //控制水波纹边界
            float[] outRadius = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
            RoundRectShape roundRectShape = new RoundRectShape(outRadius, null, null);
            ShapeDrawable maskDrawable = new ShapeDrawable();
            maskDrawable.setShape(roundRectShape);

            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, maskDrawable);
            return rippleDrawable;
        }


        /**
         * get the build result
         * invoke this method will return a layer drawable which can be applied for the
         * button background.
         * if you need to add your custom drawable , you can use the
         *
         * @param drawable your drawables
         */
        public LayerDrawable getLayer(Drawable... drawable) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{getStateList(), getRipple()});
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && drawable != null) {
                for (int i = 0; i < drawable.length; i++) {
                    layerDrawable.addLayer(layerDrawable);
                }
            }
            return layerDrawable;
        }


        /**
         * return the normal drawable layer
         */
        public Drawable getNormal() {
            return copy(color, true);
        }


        /**
         * return the press drawable layer
         */
        public Drawable getPress() {
            return copy(pressColor, isLock);
        }

        /**
         * return the focus drawable layer
         */
        public Drawable getFocus() {
            return copy(focusColor, isLock);
        }


        /**
         * get the result for
         * different from the
         *
         * @see #getLayer this method will return the drawable without ripple
         * that is to say this result only have one layer
         * <p>
         * if you don't need it,you can ignore it.
         */
        public StateListDrawable getStateList() {
            StateListDrawable stateListDrawable = new StateListDrawable();
            Drawable normal = copy(color, true);
            Drawable pressed = copy(pressColor, isLock);
            Drawable focus = copy(focusColor, isLock);

            stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focus);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normal);
            stateListDrawable.addState(new int[]{}, normal);
            return stateListDrawable;
        }


        private Drawable copy(int copyColor, boolean isLock) {
            GradientDrawable drawable;
            if (isLock) {
                drawable = new GradientDrawable(orientation, gradientColors == null ? new int[]{startColor, centerColor, endColor} : gradientColors);
                boolean condition = (startColor == NORMAL_COLOR && centerColor == NORMAL_COLOR && endColor == NORMAL_COLOR)
                        && gradientColors == null;
                if (condition) {
                    drawable.setColor(copyColor);
                }
            } else {
                drawable = new GradientDrawable();
                drawable.setColor(copyColor);
            }

            drawable.setShape(shape.value);
            drawable.setCornerRadius(radius);
            if(cornerArray != null){
                drawable.setCornerRadii(cornerArray);
            }
            drawable.setStroke((int) strokeWidth, strokeColor, dashWidth, dashGap);
            drawable.setGradientType(gradient.value);
            drawable.setGradientRadius(gradientRadius);

            return drawable;
        }

    }
}
