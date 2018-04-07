package pers.sxqsachin.mtgsearcher;

import android.app.Application;
import android.graphics.Point;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;

/**
 *
 * MTGSearcher
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class MTGSearcher extends Application {
    private static MTGSearcher  mInstance;

    private static Point        mSize;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mSize = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(mSize);
    }

    public static void  showException(Exception e) {
//        Toast.makeText(getInstance(), e.toString(), Toast.LENGTH_LONG).show();
    }

    public static void  showToast(String str) {
//        Toast.makeText(getInstance(), str, Toast.LENGTH_LONG).show();
    }

    public static MTGSearcher   getInstance() {
        return mInstance;
    }

    public static String    getCachePath() {
        File cachePath = getInstance().getExternalCacheDir();
        if (cachePath != null) {
            return cachePath.getPath();
        }

        return getInstance().getCacheDir().getPath();
    }

    public static int   getDisplayWidth() {
        return mSize.x;
    }

    public static int   getDisplayHeight() {
        return mSize.y;
    }
}
