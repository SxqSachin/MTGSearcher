package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.LocalConfigure;

/**
 *
 * LocalCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class LocalCondition implements CardCondition {
    private String  mLocal;
    private String  mLocalToShow;

    public LocalCondition(boolean useLocalLanguage) {
        mLocal = useLocalLanguage ? LocalConfigure.getLocalLangurageSearchParam() : "";
        mLocalToShow = useLocalLanguage ? LocalConfigure.getLocalLangurageSearchParamToShow() : "";
    }

    @Override
    public String condition() {
        return mLocal;
    }

    @Override
    public String conditionToShow() {
        return mLocalToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLocal);
        dest.writeString(this.mLocalToShow);
    }

    protected LocalCondition(Parcel in) {
        this.mLocal = in.readString();
        this.mLocalToShow = in.readString();
    }

    public static final Creator<LocalCondition> CREATOR = new Creator<LocalCondition>() {
        @Override
        public LocalCondition createFromParcel(Parcel source) {
            return new LocalCondition(source);
        }

        @Override
        public LocalCondition[] newArray(int size) {
            return new LocalCondition[size];
        }
    };
}
