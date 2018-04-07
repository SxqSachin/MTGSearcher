package pers.sxqsachin.mtgsearcher;

import android.content.Context;

/**
 *
 * ResourceManager
 *
 * Created by songxinqi-sachin on 16-6-3.
 */
public class ResourceManager {
    private static int      mStatusBarHeight;

    static {
        mStatusBarHeight = 0;
    }

    public static int   getStatusBarHeight(Context context) {
        if (mStatusBarHeight == 0) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }

        return mStatusBarHeight;
    }
}
