package pers.sxqsachin.mtgsearcher.util;

import android.os.Build;

/**
 *
 * BuildUtil
 *
 * Created by songxinqi-sachin on 16-6-3.
 */
public class BuildUtil {
    public static int   getCurrentApiLevel() {
        return Build.VERSION.SDK_INT;
    }
}
