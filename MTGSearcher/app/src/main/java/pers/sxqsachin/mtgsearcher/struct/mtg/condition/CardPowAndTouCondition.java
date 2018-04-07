package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardPowAndTouCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class CardPowAndTouCondition implements CardCondition {
    private String mPT;
    private String  mPTToShow;

    public CardPowAndTouCondition(String pow, boolean equalTou, String tou, boolean equalPow) {
        String tPow = equalTou ? "pow=tou" : (pow.isEmpty() ? "" : "pow=" + pow);
        String tTou = equalPow ? "tou=pow" : (tou.isEmpty() ? "" : "tou=" + tou);

        String tsPow = equalTou ? "力量=防御" : (pow.isEmpty() ? "" : "力量=" + pow);
        String tsTou = equalPow ? "防御=力量" : (tou.isEmpty() ? "" : "防御=" + tou);


        mPT = StringUtil.linkWithSpace(tPow, tTou);
        mPTToShow = StringUtil.linkWithSpace(tsPow, tsTou);
    }

    @Override
    public String condition() {
        return mPT;
    }

    @Override
    public String conditionToShow() {
        return mPTToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPT);
        dest.writeString(this.mPTToShow);
    }

    protected CardPowAndTouCondition(Parcel in) {
        this.mPT = in.readString();
        this.mPTToShow = in.readString();
    }

    public static final Creator<CardPowAndTouCondition> CREATOR = new Creator<CardPowAndTouCondition>() {
        @Override
        public CardPowAndTouCondition createFromParcel(Parcel source) {
            return new CardPowAndTouCondition(source);
        }

        @Override
        public CardPowAndTouCondition[] newArray(int size) {
            return new CardPowAndTouCondition[size];
        }
    };
}
