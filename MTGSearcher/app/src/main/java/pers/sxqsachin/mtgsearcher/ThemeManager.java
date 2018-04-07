package pers.sxqsachin.mtgsearcher;

import android.content.Context;
import android.util.TypedValue;

/**
 *
 * ThemeManager
 *
 * Created by songxinqi-sachin on 16-6-3.
 */
public class ThemeManager {
    private static int  mColorPrimary;
    private static int  mColorPrimaryDark;
    private static int  mColorAccent;

    static {
        mColorPrimary = 0;
        mColorPrimaryDark = 0;
        mColorAccent = 0;
    }

    public static int   getColorPrimary(Context context) {
        if (mColorPrimary == 0) {
            mColorPrimary = getColor(context, "colorPrimary");
        }

        return mColorPrimary;
    }

    public static int   getColorPrimaryDark(Context context) {
        if (mColorPrimaryDark == 0) {
            mColorPrimaryDark = getColor(context, "colorPrimaryDark");
        }

        return mColorPrimaryDark;
    }

    public static int   getColorAccent(Context context) {
        if (mColorAccent == 0) {
            mColorAccent = getColor(context, "colorAccent");
        }

        return mColorAccent;
    }

    private static int  getColor(Context context, String name) {
        int color;

        TypedValue tv = new TypedValue();

        int id = context.getResources().getIdentifier(name, "attr", context.getPackageName());
        if (id == 0) {
            throw new RuntimeException("Cannot find the attribute named " + name);
        } else {
            context.getTheme().resolveAttribute(id, tv, true);
            color = tv.data;
        }

        return color;
    }

    public static int dp2px(float dp) {
        final float scale = MTGSearcher.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = MTGSearcher.getInstance().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
