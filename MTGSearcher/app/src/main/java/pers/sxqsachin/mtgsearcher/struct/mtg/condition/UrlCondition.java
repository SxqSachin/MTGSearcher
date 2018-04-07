package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

/**
 *
 * UrlCondition
 *
 * Created by songxinqi-sachin on 16-7-15.
 */
public class UrlCondition implements CardCondition {

    private String  mUrl;

    public UrlCondition(String url) {
        mUrl = url;
    }

    @Override
    public String condition() {
        return mUrl;
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
        dest.writeString(this.mUrl);
    }

    protected UrlCondition(Parcel in) {
        this.mUrl = in.readString();
    }

    public static final Creator<UrlCondition> CREATOR = new Creator<UrlCondition>() {
        @Override
        public UrlCondition createFromParcel(Parcel source) {
            return new UrlCondition(source);
        }

        @Override
        public UrlCondition[] newArray(int size) {
            return new UrlCondition[size];
        }
    };
}
