package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Collection;

import pers.sxqsachin.mtgsearcher.util.LogUtil;
import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardConditions
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class CardConditions implements CardCondition {

    private String  mCondition;
    private String  mConditionToShow;

    public CardConditions(CardCondition... conditions) {
        if (conditions.length == 1) {
            if (conditions[0] instanceof CardConditions) {
                mCondition = conditions[0].condition();
                mConditionToShow = conditions[0].conditionToShow();
                return;
            }
        }

        mCondition = toCondition(conditions);
        mConditionToShow = toConditionToShow(conditions);
    }

    @NonNull
    private String toCondition(CardCondition... conditions) {
        StringBuilder sb = new StringBuilder();

        String[] condition = new String[conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            condition[i] = conditions[i].condition();
            sb.append(condition[i]);
            if (!sb.toString().isEmpty()) {
                if (i != conditions.length-1) {
                    if (!conditions[i+1].condition().isEmpty()) {
                        if (conditions[i+1] instanceof PageCondition) {
                            sb.append("&");
                        } else {
                            sb.append(" ");
                        }
                    }
                }
            }
        }

        return sb.toString();
    }

    @NonNull
    private String toConditionToShow(CardCondition... conditions) {
        String[] condition = new String[conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            condition[i] = conditions[i].conditionToShow();
        }

        return StringUtil.linkWithSpace(condition);
    }

    @Override
    public String condition() {
        return mCondition;
    }

    @Override
    public String conditionToShow() {
        return mConditionToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCondition);
        dest.writeString(this.mConditionToShow);
    }

    protected CardConditions(Parcel in) {
        this.mCondition = in.readString();
        this.mConditionToShow = in.readString();
    }

    public static final Creator<CardConditions> CREATOR = new Creator<CardConditions>() {
        @Override
        public CardConditions createFromParcel(Parcel source) {
            return new CardConditions(source);
        }

        @Override
        public CardConditions[] newArray(int size) {
            return new CardConditions[size];
        }
    };
}

//
//magiccards.info/query?q=%2B%2Be%3Aogw%2Fcn&p=2
//magiccards.info/query?q=%2B%2Be%3Aogw%2Fcn%26p%3D1
//magiccards.info/query?q=l%3Acn+(c%3Ac+or+c!wubrgm)
//magiccards.info/query?q=l%3Acn+(c%3Ac+or+c!wubrgm)
//magiccards.info/query?q=l:cn+(c:c or c!wubrgm)+p=1
//magiccards.info/query?q=(c:c or c!wubrgm)+l:cn+p=1
