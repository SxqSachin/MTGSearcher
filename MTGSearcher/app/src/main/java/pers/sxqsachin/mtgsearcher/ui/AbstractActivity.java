package pers.sxqsachin.mtgsearcher.ui;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 *
 * AbstractActivity
 *
 * Created by songxinqi-sachin on 16-6-10.
 */
public abstract class AbstractActivity extends AppCompatActivity implements IActivity {

    protected void  init() {
        initData();
        initViews();
        beforeInitComplete();
    }

    protected void  initData() { }

    protected void  initViews() { }

    protected void  beforeInitComplete() { }

    protected void  startAcivity(Class clazz, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void hideView(View v, boolean b) {
        if (b) {
            if (v.getLayoutParams() instanceof  RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
                lp.height = 0;
                lp.topMargin = 0;
                v.setLayoutParams(lp);
            }
        }
    }

}
