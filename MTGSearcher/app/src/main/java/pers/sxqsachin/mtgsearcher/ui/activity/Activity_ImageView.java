package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.databinding.ActivityImageViewBinding;
import pers.sxqsachin.mtgsearcher.net.bitmap.BitmapLoader;
import pers.sxqsachin.mtgsearcher.ui.AbstractActivity;
import pers.sxqsachin.mtgsearcher.uidata.ViewData_BitmapViewer;

/**
 *
 * Activity_ImageView
 *
 * Created by songxinqi-sachin on 16-7-26.
 */
public class Activity_ImageView extends AbstractActivity {

    private final static String INTENT_EXTRA_BITMAP_SRC =   "bitmap_Src";

    private ActivityImageViewBinding    mBinding;
    private ViewData_BitmapViewer       mData;

    public static Intent    getIntent(Context context, String src) {
        Intent intent = new Intent();
        intent.setClass(context, Activity_ImageView.class);
        intent.putExtra(INTENT_EXTRA_BITMAP_SRC, src);

        return intent;
    }

    @Nullable
    private String getBitmapSrc(Intent intent) {
        return intent.getStringExtra(INTENT_EXTRA_BITMAP_SRC);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_view);

        Intent intent = getIntent();

        mData = new ViewData_BitmapViewer("Test", BitmapLoader.loadFromMemoryCache(getBitmapSrc(intent)));

        mBinding.setData(mData);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mData.onStop();
        mBinding.ivBitmapViewer.setImageBitmap(null);
    }
}
