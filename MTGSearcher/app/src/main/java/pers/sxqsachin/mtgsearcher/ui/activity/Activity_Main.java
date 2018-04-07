package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.databinding.ActivityMainBinding;
import pers.sxqsachin.mtgsearcher.ui.BindableActivity;
import pers.sxqsachin.mtgsearcher.uidata.UIData_Main;
import pers.sxqsachin.mtgsearcher.util.StatusBarUtil;

/**
 *
 * Activity_Main
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class Activity_Main extends BindableActivity {

    private ActivityMainBinding     mBinding;
    private UIData_Main             mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mData = new UIData_Main();
        mBinding.setData(mData);

        init();

        StatusBarUtil.trick(this);

        setSupportActionBar(mBinding.tbMain);
        mBinding.tbMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        startActivity(new Intent(Activity_Main.this, Activity_Search.class));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }
}
