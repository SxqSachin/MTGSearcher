package pers.sxqsachin.mtgsearcher.uidata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

/**
 *
 * ViewData_BitmapViewer
 *
 * Created by songxinqi-sachin on 16-7-26.
 */
public class ViewData_BitmapViewer extends BaseObservable {
    private String                  mBitmapName;
    private WeakReference<Bitmap>   mBitmap;

    public ViewData_BitmapViewer(String bitmapName, Bitmap bitmap) {
        mBitmapName = bitmapName;
        if (bitmap == null) {
            throw new RuntimeException("Bitmap is null");
        }
        mBitmap = new WeakReference<Bitmap>(bitmap);
    }

    @Bindable
    public String   getBitmapName() {
        return mBitmapName;
    }

    @Bindable
    public Bitmap   getBitmap() {
        return mBitmap.get();
    }

    public void     onStop() {
        mBitmapName = "";
        mBitmap.clear();
        mBitmap = null;
    }
}
