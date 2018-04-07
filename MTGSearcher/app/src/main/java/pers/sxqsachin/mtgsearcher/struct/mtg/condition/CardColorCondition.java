package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.LocalConfigure;
import pers.sxqsachin.mtgsearcher.struct.mtg.local.MTGLocalText;

/**
 *
 * CardColorCondition
 *
 * Created by songxinqi-sachin on 16-7-18.
 */
public class CardColorCondition implements CardCondition {

    private String  mColors;
    private String  mColorsToShow;

    public CardColorCondition(boolean white,
                              boolean blue,
                              boolean black,
                              boolean red,
                              boolean green,
                              boolean colorless,
                              boolean mustMutiColor,
                              boolean excludeUnselected) {
        if (colorless) {
            if (!white &&
                    !blue &&
                    !black &&
                    !red &&
                    !green &&
                    !mustMutiColor) {
                mColors = "c:c";
            } else {
                mColors = "(c:c or c";
                mColors += excludeUnselected ? "!" : ":";
                mColors += white ? "w" : "";
                mColors += blue ? "u" : "";
                mColors += black ? "b" : "";
                mColors += red ? "r" : "";
                mColors += green ? "g" : "";
                mColors += mustMutiColor ? "m" : "";
                mColors += ")";
            }
        } else {
            if (!white &&
                    !blue &&
                    !black &&
                    !red &&
                    !green &&
                    !mustMutiColor) {
                mColors = "";
            } else {
                mColors = "c";
                mColors += excludeUnselected ? "!" : ":";
                mColors += white ? "w" : "";
                mColors += blue ? "u" : "";
                mColors += black ? "b" : "";
                mColors += red ? "r" : "";
                mColors += green ? "g" : "";
                mColors += mustMutiColor ? "m" : "";
            }
        }

        MTGLocalText localText = LocalConfigure.getLocalText();
        if (!white &&
                !blue &&
                !black &&
                !red &&
                !green &&
                !colorless) {
            mColorsToShow = "";
        } else {
            mColorsToShow = excludeUnselected ? "颜色为:" : "颜色包含:";
            mColorsToShow += white ? localText.white() : "";
            mColorsToShow += blue ? localText.blue() : "";
            mColorsToShow += black ? localText.black() : "";
            mColorsToShow += red ? localText.red() : "";
            mColorsToShow += green ? localText.green() : "";
            mColorsToShow += colorless ? localText.colorless() : "";
            mColorsToShow += mustMutiColor ? " 必须多色" : "";
        }
    }

    @Override
    public String condition() {
        return mColors;
    }

    @Override
    public String conditionToShow() {
        return mColorsToShow;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mColors);
        dest.writeString(this.mColorsToShow);
    }

    protected CardColorCondition(Parcel in) {
        this.mColors = in.readString();
        this.mColorsToShow = in.readString();
    }

    public static final Creator<CardColorCondition> CREATOR = new Creator<CardColorCondition>() {
        @Override
        public CardColorCondition createFromParcel(Parcel source) {
            return new CardColorCondition(source);
        }

        @Override
        public CardColorCondition[] newArray(int size) {
            return new CardColorCondition[size];
        }
    };
}
