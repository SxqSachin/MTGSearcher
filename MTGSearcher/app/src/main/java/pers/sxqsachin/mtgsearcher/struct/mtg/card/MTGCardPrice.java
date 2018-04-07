package pers.sxqsachin.mtgsearcher.struct.mtg.card;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * MTGCardPrice
 *
 * Created by songxinqi-sachin on 16-7-11.
 */
public class MTGCardPrice implements Parcelable {
    private String  mLowPrice;
    private String  mMidPrice;
    private String  mHighPrice;

    public MTGCardPrice() {
        mLowPrice = "Price Loading...";
        mMidPrice = "";
        mHighPrice = "";
    }

    public String getLowPrice() {
        return mLowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.mLowPrice = lowPrice;
    }

    public String getMidPrice() {
        return mMidPrice;
    }

    public void setMidPrice(String midPrice) {
        this.mMidPrice = midPrice;
    }

    public String getHighPrice() {
        return mHighPrice;
    }

    public void setHighPrice(String highPrice) {
        this.mHighPrice = highPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLowPrice);
        dest.writeString(this.mMidPrice);
        dest.writeString(this.mHighPrice);
    }

    protected MTGCardPrice(Parcel in) {
        this.mLowPrice = in.readString();
        this.mMidPrice = in.readString();
        this.mHighPrice = in.readString();
    }

    public static final Creator<MTGCardPrice> CREATOR = new Creator<MTGCardPrice>() {
        @Override
        public MTGCardPrice createFromParcel(Parcel source) {
            return new MTGCardPrice(source);
        }

        @Override
        public MTGCardPrice[] newArray(int size) {
            return new MTGCardPrice[size];
        }
    };
}
