package pers.sxqsachin.mtgsearcher.uidata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import pers.sxqsachin.mtgsearcher.BR;

/**
 *
 * UIData_SearchResult
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class UIData_SearchResult extends BaseObservable {

    private String  mSearchKeyword;

    private String  mTitle;
    private int     mTitleTextColor;

    public UIData_SearchResult() {
        mSearchKeyword = "";

        mTitle = "搜索\"" + mSearchKeyword + "\"的结果";
        mTitleTextColor = 0xFFFFFFFF;
    }

    public String getSearchKeyword() {
        return mSearchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.mSearchKeyword = searchKeyword;

        setTitle(mTitle = "搜索\"" + mSearchKeyword + "\"的结果");
    }

    @Bindable
    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.mTitleTextColor = titleTextColor;
        notifyPropertyChanged(BR.titleTextColor);
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        notifyPropertyChanged(BR.title);
    }
}
