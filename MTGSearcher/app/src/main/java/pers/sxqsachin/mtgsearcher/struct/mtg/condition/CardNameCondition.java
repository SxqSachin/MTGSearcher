package pers.sxqsachin.mtgsearcher.struct.mtg.condition;


import android.os.Parcel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * CardNameCondition
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class CardNameCondition implements CardCondition {

    private String  mCardName;
    private String  mCardNameToShow;

    public CardNameCondition(String cardName, boolean matchFullName) {
        mCardName = matchFullName ? "!" + cardName : cardName;

        if (cardName != null && !cardName.isEmpty()) {
            mCardNameToShow = matchFullName ? "名称为:" + cardName : "名称包含:" + cardName;
        } else {
            mCardNameToShow = "";
        }
    }

    @Override
    public String condition() {
        try {
            return URLEncoder.encode(mCardName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mCardName;
    }

    @Override
    public String conditionToShow() {
        return mCardNameToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCardName);
        dest.writeString(this.mCardNameToShow);
    }

    protected CardNameCondition(Parcel in) {
        this.mCardName = in.readString();
        this.mCardNameToShow = in.readString();
    }

    public static final Creator<CardNameCondition> CREATOR = new Creator<CardNameCondition>() {
        @Override
        public CardNameCondition createFromParcel(Parcel source) {
            return new CardNameCondition(source);
        }

        @Override
        public CardNameCondition[] newArray(int size) {
            return new CardNameCondition[size];
        }
    };
}
