package pers.sxqsachin.mtgsearcher.util;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pers.sxqsachin.mtgsearcher.ResourceManager;
import pers.sxqsachin.mtgsearcher.ThemeManager;


/**
 *
 * StatusBarUtil
 *
 * Created by songxinqi-sachin on 16-6-3.
 */
public class StatusBarUtil {

    public static void trick(Activity activity) {
        if (BuildUtil.getCurrentApiLevel() >= Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (BuildUtil.getCurrentApiLevel() >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View statusView = createStatusBar(activity);

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);

            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public static void trickStatusBar_DrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (BuildUtil.getCurrentApiLevel() >= Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        if (BuildUtil.getCurrentApiLevel() >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View statusBar= createStatusBar(activity);

            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);

            addStatusBar(contentLayout, statusBar);

            drawerLayout.setFitsSystemWindows(false);
            contentLayout.setFitsSystemWindows(false);
            contentLayout.setClipToPadding(true);

            ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
            drawer.setFitsSystemWindows(false);
        }
    }

    private static void addStatusBar(ViewGroup contentLayout, View statusBar) {
        if (contentLayout instanceof LinearLayout) {
            contentLayout.addView(statusBar, 0);
            return;
        }

        if (contentLayout instanceof RelativeLayout) {
            View view = contentLayout.getChildAt(0);
            if (!(view instanceof ViewGroup)) {
                contentLayout.addView(statusBar, 0);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                lp.addRule(RelativeLayout.BELOW, statusBar.getId());
                view.setLayoutParams(lp);
            } else {
                addStatusBar((ViewGroup) view, statusBar);
            }
        }
    }

    private static View createStatusBar(Activity activity) {
        View statusBar = new View(activity);

        int statusBarHeight = ResourceManager.getStatusBarHeight(activity);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);

        statusBar.setLayoutParams(params);
        statusBar.setBackgroundColor(ThemeManager.getColorPrimaryDark(activity));

        return statusBar;
    }
}
