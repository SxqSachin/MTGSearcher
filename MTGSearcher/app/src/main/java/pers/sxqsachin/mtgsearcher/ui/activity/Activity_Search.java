package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.database.MTGSearcherDatabase;
import pers.sxqsachin.mtgsearcher.database.table.SearchHistoryTable;
import pers.sxqsachin.mtgsearcher.database.table.value.SearchHistoryValue;
import pers.sxqsachin.mtgsearcher.databinding.ActivitySearchBinding;
import pers.sxqsachin.mtgsearcher.databinding.PopupTextOnlyBinding;
import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardKeywordCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardNameCondition;
import pers.sxqsachin.mtgsearcher.ui.BindableActivity;
import pers.sxqsachin.mtgsearcher.ui.adapter.RecyclerAdapter_SearchHistory;
import pers.sxqsachin.mtgsearcher.uidata.UIData_Search;
import pers.sxqsachin.mtgsearcher.util.StatusBarUtil;

/**
 *
 * Activity_Search
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class Activity_Search extends BindableActivity implements View.OnClickListener {

    private ActivitySearchBinding   mBinding;
    private UIData_Search           mData;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mData = new UIData_Search();
        mBinding.setData(mData);

        init();

        StatusBarUtil.trick(this);

        setSupportActionBar(mBinding.tbSearch);
        mBinding.tbSearch.setNavigationIcon(R.drawable.ic_arrow_back);
        mBinding.tbSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MTGSearcherDatabase db = MTGSearcherDatabase.getInstance();
                Cursor cursor = db.getCursor(new SearchHistoryTable());
                ArrayList<StringPair> historys = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String history = cursor.getString(cursor.getColumnIndex("history"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    historys.add(new StringPair(history, url));
                }
                Collections.reverse(historys);
                if (historys.size() > 0) {
                    createPopup(mBinding.etSearch, 0, 0, Gravity.CENTER | Gravity.START, historys);
                }
            }
        });

        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(mData.toCondition());
                }
                return false;
            }
        });

        mBinding.ibSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.ibSearch) {
            search(mData.toCondition());
        }
    }

    public void    search(String keyword, String history, boolean fromHistory) {
//        if (!fromHistory) {
//            MTGSearcherDatabase.getInstance().insert(new SearchHistoryTable(), new SearchHistoryValue(mData.toCondition().conditionToShow(), mData.toCondition().condition()));
//        }

        Intent intent = Activity_SearchResult.getIntent(this, new CardKeywordCondition(keyword), history);
        startActivity(intent);
        finish();
    }

    public void    search(CardCondition cardCondition) {
        String keyword = mData.getKeyword();
        if (keyword.isEmpty()) {
            keyword = mData.toCondition().condition();
        }

        MTGSearcherDatabase.getInstance().insert(new SearchHistoryTable(), new SearchHistoryValue(mData.toCondition().conditionToShow(), mData.toCondition().condition()));

        Intent intent = Activity_SearchResult.getIntent(this, cardCondition, cardCondition.conditionToShow());
        startActivity(intent);
        finish();
    }

    protected void  createPopup(View anchor, int xOffset, int yOffset, int gravity, ArrayList<StringPair> data) {
        PopupTextOnlyBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.popup_text_only, null, true);

        PopupWindow popupWindow = new PopupWindow(binding.getRoot(), -2, -2, false);
        binding.rvPe.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPe.setItemAnimator(new DefaultItemAnimator());
        binding.rvPe.setAdapter(new RecyclerAdapter_SearchHistory(this, popupWindow, data));
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(anchor, xOffset, yOffset, gravity);
    }
}

//magiccards.info/query?q=ct:%E8%25A6%2586%25E8%25AF%25B5&p=1
//magiccards.info/query?q=ct:%E8%A6%86%E8%AF%B5&p=1