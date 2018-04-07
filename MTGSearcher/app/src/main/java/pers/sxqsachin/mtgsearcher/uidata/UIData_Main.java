package pers.sxqsachin.mtgsearcher.uidata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import pers.sxqsachin.mtgsearcher.BR;

/**
 *
 * UIData_Main
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class UIData_Main extends BaseObservable {
    private String  mTitle;
    private int     mTitleTextColor;

    public UIData_Main() {
        mTitle = "MtgSearcher";
        mTitleTextColor = 0xFFFFFFFF;
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
