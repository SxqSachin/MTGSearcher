package pers.sxqsachin.mtgsearcher.net.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pers.sxqsachin.mtgsearcher.MTGSearcher;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.manager.URLFileManager;
import pers.sxqsachin.mtgsearcher.util.BitmapUtil;
import pers.sxqsachin.mtgsearcher.util.CacheUtil;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * BitmapLoader
 *
 * Created by songxinqi-sachin on 16-7-3.
 */
public class BitmapLoader {

    private static LruCache<String, Bitmap> mMemoryCache;

    static {
        long maxMemory = Runtime.getRuntime().maxMemory();
        mMemoryCache = new LruCache<String, Bitmap>((int)(maxMemory / 4)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (!oldValue.isRecycled()) {
                    oldValue.recycle();
//                    oldValue = null;
                }
            }
        };
    }

    public static Bitmap    loadFromMemoryCache(final String src) {
        return getBitmapFromMemoryCache(src);
    }


    public static void  load(final String src, final OnBitmapLoadCompleteListener listener) {
        if (src == null ||
                src.isEmpty()) {
            listener.onBitmapLoadComplete(src, null);
            return;
        }

        final long st = System.currentTimeMillis();
        try {
            getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.debugOut("BitmapLoader load("+src+") : Start Load Image");
                    Bitmap bitmap;
                    if ((bitmap = getBitmapFromMemoryCache(src)) != null) {
                        if (listener != null) {
                            long et = System.currentTimeMillis();
                            LogUtil.debugOut("BitmapLoader load("+src+") : Load complete, from memory cache, past " + (et-st));
                            listener.onBitmapLoadComplete(src, bitmap);
                        }
                    } else {
                        File cacheFile = CacheUtil.getCacheFile(src);
                        if (!cacheFile.exists()) {
                            getFromUrl(src, new OnBitmapLoadCompleteListener() {
                                @Override
                                public void onBitmapLoadComplete(String src, Bitmap bitmap) {
                                    addToMemoryCache(src, bitmap);
                                    if (listener != null) {
                                        long et = System.currentTimeMillis();
                                        LogUtil.debugOut("BitmapLoader load("+src+") : Load complete, from Url , past " + (et-st));
                                        listener.onBitmapLoadComplete(src, bitmap);
                                    }
                                }
                            });
                        } else {
                            bitmap = BitmapUtil.getFromFile(cacheFile);
                            if (null == bitmap) {
                                LogUtil.debugOut("BitmapLoader : load("+src+") bitmap is null start reload from url");
                                getFromUrl(src, listener);
                            } else {
                                if (listener != null) {
                                    addToMemoryCache(src, bitmap);
                                    long et = System.currentTimeMillis();
                                    LogUtil.debugOut("BitmapLoader load("+src+") : Load complete, from cache file, past " + (et-st));
                                    listener.onBitmapLoadComplete(src, bitmap);
                                }
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            MTGSearcher.showException(e);
        }
    }

    protected static void  getFromUrl(final String src, final OnBitmapLoadCompleteListener listener) {
        getFromUrl(src, listener, 0);
    }

    protected static void   getFromUrl(final String src, final OnBitmapLoadCompleteListener listener, final int times) {
        final File cacheFile = CacheUtil.getCacheFile(src);

        try {
            if (cacheFile.exists()) {
                if (!cacheFile.delete()) {
                    throw new IOException("Cannot delete file : " + cacheFile.getPath());
                } else {
                    LogUtil.debugOut("Delete exist cache");
                }
            }
            if (!cacheFile.createNewFile()) {
                throw new IOException("Cannot create file : " + cacheFile.getPath());
            }

            final FileOutputStream fos = new FileOutputStream(cacheFile);
            URLFileManager.getObjectFromUrl(src, fos, new OnCompleteListener<Integer>() {
                @Override
                public void onComplete(Integer i) {
                    try {
                        fos.close();

                        Bitmap bitmap = BitmapUtil.getFromFile(cacheFile);
                        if (bitmap == null) {
                            LogUtil.debugOut("BitmapLoader getFromUrl("+src+")" + " Bitmap is null, Reload, Times " + times);
                            fos.flush();
                            fos.close();
                            getFromUrl(src, listener, times+1);
                        } else {
                            if (times != 0) {
                                LogUtil.debugOut("BitmapLoader getFromUrl(" + src + ")" + " Reload Complete, Times " + times);
                            }

                            if (null != listener) {
                                listener.onBitmapLoadComplete(src, bitmap);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, false);
        } catch (IOException e) {
            MTGSearcher.showException(e);
            e.printStackTrace();
        }
    }

    public static void   cancelTask() {
        ThreadPoolInstanceHolder.release();
    }

    protected static void   addToMemoryCache(String src, Bitmap bitmap) {
        String cacheName = CacheUtil.getCacheFileName(src);
        if (mMemoryCache.get(cacheName) == null) {
            mMemoryCache.put(cacheName, bitmap);
            LogUtil.debugOut("BitmapLoader addToMemoryCache("+src+") Complete, Cache Size : " + mMemoryCache.putCount());
        }
    }

    protected static Bitmap getBitmapFromMemoryCache(String src) {
        LogUtil.debugOut("BitmapLoader getBitmapFromMemoryCache("+src+") Complete");
        Bitmap bitmap = mMemoryCache.get(CacheUtil.getCacheFileName(src));
        if (bitmap == null) {
            LogUtil.debugOut("BitmapLoader getBitmapFromMemoryCache("+src+") Bitmap is null");
        }
        return bitmap;
    }

    protected static ExecutorService    getThreadPool() {
        return ThreadPoolInstanceHolder.getInstance();
    }

    public interface OnBitmapLoadCompleteListener {
        void    onBitmapLoadComplete(String src, Bitmap bitmap);
    }

    private static class ThreadPoolInstanceHolder {
        private static ExecutorService      mInstance   =   null;
        public static ExecutorService   getInstance() {
            if (mInstance == null) {
                synchronized (ThreadPoolInstanceHolder.class) {
                    if (mInstance == null) {
                        mInstance = Executors.newFixedThreadPool(4);
                    }
                }
            }

            return mInstance;
        }
        public static void  release() {
            if (mInstance != null) {
                List<Runnable> runnable = mInstance.shutdownNow();
                LogUtil.debugOut("BitmapLoader : ThreadPool shutdown, thread count is " + runnable.size());
                mInstance = null;
            }
        }
    }
}
