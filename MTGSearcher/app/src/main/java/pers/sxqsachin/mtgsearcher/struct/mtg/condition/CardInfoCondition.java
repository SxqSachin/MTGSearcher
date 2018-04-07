package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardInfoCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class CardInfoCondition implements CardCondition {
    private String mTFA;
    private String mTFATOShow;

    public CardInfoCondition(String text, String flavor, String artist) {
        String tText = text.isEmpty() ? "" : "ct:" + StringUtil.toUrl(text);
        String tFlavor = flavor.isEmpty() ? "" : "ft:" + StringUtil.toUrl(flavor);
        String tArtist = artist.isEmpty() ? "" : "a:" + "\"" + artist + "\"";

        String tsText = text.isEmpty() ? "" : "规则包含:" + text;
        String tsFlavor = flavor.isEmpty() ? "" : "背景:" + flavor;
        String tsArtist = artist.isEmpty() ? "" : "画家:" + artist;

        mTFA = StringUtil.linkWithSpace(tText, tFlavor, tArtist);
        mTFATOShow = StringUtil.linkWithSpace(tsText, tsFlavor, tsArtist);
    }

    @Override
    public String condition() {
        return mTFA;
    }

    @Override
    public String conditionToShow() {
        return mTFATOShow;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTFA);
        dest.writeString(this.mTFATOShow);
    }

    protected CardInfoCondition(Parcel in) {
        this.mTFA = in.readString();
        this.mTFATOShow = in.readString();
    }

    public static final Creator<CardInfoCondition> CREATOR = new Creator<CardInfoCondition>() {
        @Override
        public CardInfoCondition createFromParcel(Parcel source) {
            return new CardInfoCondition(source);
        }

        @Override
        public CardInfoCondition[] newArray(int size) {
            return new CardInfoCondition[size];
        }
    };
}
