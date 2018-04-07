package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardCostCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class CardCostCondition implements CardCondition {

    private String  mCost;
    private String  mCostToShow;

    public CardCostCondition(String cost) {
        this(cost, "");
    }

    public CardCostCondition(String cost, String totalCost) {
        String tCost = cost.isEmpty() ? "" : "mana=" + cost;
        String tTcost = totalCost.isEmpty() ? "" : "cmc=" + totalCost;

        String ctsCost = cost.isEmpty() ? "" : "费用:" + cost;
        String ctsTCost = totalCost.isEmpty() ? "" : "总费用:" + totalCost;

        mCost = StringUtil.linkWithSpace(tCost, tTcost);
        mCostToShow = StringUtil.linkWithSpace(ctsCost, ctsTCost);
    }

    @Override
    public String condition() {
        return mCost;
    }

    @Override
    public String conditionToShow() {
        return mCostToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCost);
        dest.writeString(this.mCostToShow);
    }

    protected CardCostCondition(Parcel in) {
        this.mCost = in.readString();
        this.mCostToShow = in.readString();
    }

    public static final Creator<CardCostCondition> CREATOR = new Creator<CardCostCondition>() {
        @Override
        public CardCostCondition createFromParcel(Parcel source) {
            return new CardCostCondition(source);
        }

        @Override
        public CardCostCondition[] newArray(int size) {
            return new CardCostCondition[size];
        }
    };
}

