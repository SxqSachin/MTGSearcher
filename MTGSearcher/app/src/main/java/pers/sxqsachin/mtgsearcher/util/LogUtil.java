package pers.sxqsachin.mtgsearcher.util;

import android.util.Log;

/**
 *
 * LogUtil
 *
 * Created by songxinqi-sachin on 16-6-3.
 */
public class LogUtil {
    private final static int    DEBUG_OUT_LEVEL     =   0;
    private final static String DEBUG_OUT_TAG       =   "DebugOut";

    public static void  systemOut(String str) {
        System.out.println(str);
    }

    public static void  debugOut(String str) {
        debugOut(str, 1);
    }

    public static void  debugOut(String str, int level) {
        if (level > DEBUG_OUT_LEVEL) {
            Log.i(DEBUG_OUT_TAG, str);
        }
    }
}
