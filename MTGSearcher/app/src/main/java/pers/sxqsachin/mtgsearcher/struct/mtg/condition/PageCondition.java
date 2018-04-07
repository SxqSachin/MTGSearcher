package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

/**
 *
 * PageCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class PageCondition implements CardCondition {

    private int mPage;

    public PageCondition(int page) {
        mPage = page;
    }

    @Override
    public String condition() {
        return "p=" + mPage;
    }

    @Override
    public String conditionToShow() {
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPage);
    }

    protected PageCondition(Parcel in) {
        this.mPage = in.readInt();
    }

    public static final Creator<PageCondition> CREATOR = new Creator<PageCondition>() {
        @Override
        public PageCondition createFromParcel(Parcel source) {
            return new PageCondition(source);
        }

        @Override
        public PageCondition[] newArray(int size) {
            return new PageCondition[size];
        }
    };
}
