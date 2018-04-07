package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.database.MTGSearcherDatabase;
import pers.sxqsachin.mtgsearcher.databinding.ActivityInitBinding;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.listener.OnSimpleCompleteListener;
import pers.sxqsachin.mtgsearcher.net.bitmap.BitmapLoader;
import pers.sxqsachin.mtgsearcher.net.bitmap.Blur;
import pers.sxqsachin.mtgsearcher.struct.mtg.SingleCardParser;
import pers.sxqsachin.mtgsearcher.ui.BindableActivity;
import pers.sxqsachin.mtgsearcher.uidata.UIData_Init;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * Activity_Init
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class Activity_Init extends BindableActivity {

    private ActivityInitBinding     mBinding;
    private UIData_Init             mData;

    private boolean                 mAlreadyDestroy     =   false;

//    private Timer                   mTimer  =   new Timer();
//    private TimerTask               mTimerTask  =   new TimerTask() {
//        boolean read = false;
//        boolean readComplete = false;
//        int maxTime = 1090;
//        int ct = 0;
//        @Override
//        public void run() {
//            LogUtil.debugOut("PAST : " + ct);
//            if (ct >= maxTime && !readComplete) {
//                toMainActivity();
//                LogUtil.debugOut("TIMEOUT");
//            }
//
//            if (!read) {
//                read = true;
//                mData.setInitViewRandomCard(new SingleCardParser().parse("http://magiccards.info/random.html"));
//
//                BitmapLoader.load(mData.getInitViewRandomCard().getImgUrl(), new OnCompleteListener<BitmapDrawable>() {
//                    @Override
//                    public void onComplete(final BitmapDrawable obj) {
//                        mBinding.ivRandomCardBlur.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                long st = System.currentTimeMillis();
//                                final Bitmap blur = Blur.blur(Activity_Init.this, obj.getBitmap());
//                                long et = System.currentTimeMillis();
//                                LogUtil.debugOut("TIME : " + (et-st));
//
//                                mBinding.ivRandomCard.setImage(obj);
//                                mBinding.ivRandomCardBlur.setBackground(new BitmapDrawable(null, blur));
//
//                                readComplete = true;
//                                mBinding.ivRandomCard.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent();
//                                        intent.setClass(Activity_Init.this, Activity_CardDetail.class);
//                                        intent.putExtra("card", mData.getInitViewRandomCard());
//                                        toMainActivity();
//                                        blur.recycle();
//                                        startActivity(intent);
//                                    }
//                                });
//
//                                mBinding.ivRandomCard.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        toMainActivity();
//                                        blur.recycle();
//                                    }
//                                }, 3820 + mData.getInitViewRandomCard().getDescription().length() * 25);
//                            }
//                        });
//                    }
//                });
//            }
//            ct += 100;
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_init);
        mData = new UIData_Init();
        mBinding.setData(mData);

        MTGSearcherDatabase.init();

        toMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAlreadyDestroy = true;
    }

    protected void toMainActivity() {
//        if (mTimer != null) {
//            mTimerTask.cancel();
//            mTimer.cancel();
//        }
        if (!mAlreadyDestroy) {
            startActivity(new Intent(Activity_Init.this, Activity_Main.class));
            finish();
        }
    }
}
