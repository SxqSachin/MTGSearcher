package pers.sxqsachin.mtgsearcher.uidata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import pers.sxqsachin.mtgsearcher.BR;

/**
 *
 * UIData_CardPreview
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class UIData_CardPreview extends BaseObservable {
    private String mI;
    public UIData_CardPreview(String i) {
        mI = i;
    }

    @Bindable
    public String getI() {
        return mI;
    }

    public void setI(String i) {
        mI = i;
        notifyPropertyChanged(BR.i);
    }
}
