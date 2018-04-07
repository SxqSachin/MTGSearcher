package pers.sxqsachin.mtgsearcher.struct;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * StringPair
 *
 * Created by songxinqi-sachin on 16-7-15.
 */
public class StringPair implements Parcelable {
    public final String first;
    public final String second;

    public StringPair(String f, String s) {
        first = f;
        second = s;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.second);
    }

    protected StringPair(Parcel in) {
        this.first = in.readString();
        this.second = in.readString();
    }

    public static final Creator<StringPair> CREATOR = new Creator<StringPair>() {
        @Override
        public StringPair createFromParcel(Parcel source) {
            return new StringPair(source);
        }

        @Override
        public StringPair[] newArray(int size) {
            return new StringPair[size];
        }
    };
}
