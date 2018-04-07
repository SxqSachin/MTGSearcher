package pers.sxqsachin.mtgsearcher.util;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import pers.sxqsachin.mtgsearcher.MTGSearcher;

/**
 *
 * CacheUtil
 *
 * Created by songxinqi-sachin on 16-7-19.
 */
public class CacheUtil {
    public static String getCachePath() {
        return MTGSearcher.getCachePath();
    }

    public static String getCacheFileName(String filename) {
        return MD5Util.string2MD5(filename);
    }

    public static String getCacheFilePath(String filename) {
        return getCachePath() + "/" + getCacheFileName(filename);
    }

    public static File  getCacheFile(String filename) {
        return new File(getCacheFilePath(filename));
    }

    public static boolean isCacheFileExist(String filename) {
        return getCacheFile(filename).exists();
    }
}
