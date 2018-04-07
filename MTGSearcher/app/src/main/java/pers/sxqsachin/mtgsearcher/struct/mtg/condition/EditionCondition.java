package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcel;

import pers.sxqsachin.mtgsearcher.struct.mtg.AllEdition;

/**
 *
 * EditionCondition
 *
 * Created by songxinqi-sachin on 16-7-16.
 */
public class EditionCondition implements CardCondition {

    private String mEdition;
    private String mEditionToShow;

    public EditionCondition(String edition) {
        mEdition = AllEdition.getAbbFromEditionName(edition);
        mEditionToShow = edition;
    }

    @Override
    public String condition() {
        return "++e:" + mEdition;
    }

    @Override
    public String conditionToShow() {
        return "版本:" + mEditionToShow;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mEdition);
        dest.writeString(this.mEditionToShow);
    }

    protected EditionCondition(Parcel in) {
        this.mEdition = in.readString();
        this.mEditionToShow = in.readString();
    }

    public static final Creator<EditionCondition> CREATOR = new Creator<EditionCondition>() {
        @Override
        public EditionCondition createFromParcel(Parcel source) {
            return new EditionCondition(source);
        }

        @Override
        public EditionCondition[] newArray(int size) {
            return new EditionCondition[size];
        }
    };
}
