package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

/**
 *
 * CardKeywordCondition
 *
 * Created by songxinqi-sachin on 16-7-23.
 */
public class CardKeywordCondition implements CardCondition {
    private String  mKeyword;

    public CardKeywordCondition(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public String condition() {
        return mKeyword;
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
        dest.writeString(this.mKeyword);
    }

    protected CardKeywordCondition(Parcel in) {
        this.mKeyword = in.readString();
    }

    public static final Creator<CardKeywordCondition> CREATOR = new Creator<CardKeywordCondition>() {
        @Override
        public CardKeywordCondition createFromParcel(Parcel source) {
            return new CardKeywordCondition(source);
        }

        @Override
        public CardKeywordCondition[] newArray(int size) {
            return new CardKeywordCondition[size];
        }
    };
}
