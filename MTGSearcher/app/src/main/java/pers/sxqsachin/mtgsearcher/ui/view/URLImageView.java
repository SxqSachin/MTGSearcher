package pers.sxqsachin.mtgsearcher.ui.view;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import pers.sxqsachin.mtgsearcher.net.bitmap.BitmapLoader;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * URLImageView
 *
 * Created by songxinqi-sachin on 16-7-7.
 */
public class URLImageView extends AppCompatImageView {

    private ValueAnimator               mFadeIn;

    private String                      mLastBitmapSrc;

    private boolean                     mLoadComplete;

    public URLImageView(Context context) {
        super(context);
        init();
    }

    public URLImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public URLImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLastBitmapSrc = "";
        mLoadComplete = false;

        mFadeIn = ValueAnimator.ofInt(0, 255);
        mFadeIn.setDuration(382);
        mFadeIn.setInterpolator(new LinearInterpolator());
        mFadeIn.setEvaluator(new IntEvaluator());
    }

    public void setImage(final CharSequence url) {
        mLastBitmapSrc = url.toString();
        mLoadComplete = false;
        load(url.toString());
    }

    private void load(final String url) {
        BitmapLoader.load(url, new BitmapLoader.OnBitmapLoadCompleteListener() {
            @Override
            public void onBitmapLoadComplete(String src, Bitmap bitmap) {
                if (bitmap == null) {
                    LogUtil.debugOut("URLImageView : load("+url+") Bitmap is null");
                }
                if (mLastBitmapSrc.equals(src)) {
                    setImg(bitmap);
                }
                mLoadComplete = true;
            }
        });
    }

    protected void  setImg(final Bitmap bitmap) {
        setImg(bitmap, true);
    }

    protected void setImg(final Bitmap bitmap, final boolean fade) {
        LogUtil.debugOut("URLImageView : setImg");
        post(new Runnable() {
            @Override
            public void run() {
                setImageBitmap(bitmap);
                if (fade) {
                    setImageAlpha(0);
                    mFadeIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            final int alpha = (int) animation.getAnimatedValue();
                            setImageAlpha(alpha);
                        }
                    });
                    mFadeIn.start();
                }
            }
        });
        getDrawable();
    }

    public boolean  isLoadComplete() {
        return mLoadComplete;
    }

    public void recycle() {
        if (mFadeIn != null) {
            if (mFadeIn.isStarted() || mFadeIn.isRunning()) {
                mFadeIn.removeAllUpdateListeners();
                mFadeIn.pause();
                mFadeIn.cancel();
            }
        }

        setImageDrawable(null);
        mLastBitmapSrc = "";

//        if (mBitmap != null) {
//            if (mBitmap.isRecycled()) {
//                mBitmap.recycle();
//                if (mCount > 5) {
//                    mCount = 0;
//                    System.gc();
//                }
//                mCount++;
//            }
//            mBitmap = null;
//        }
    }
}
