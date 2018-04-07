package pers.sxqsachin.mtgsearcher.struct.mtg.condition;

import android.os.Parcelable;

/**
 *
 * CardCondition
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public interface CardCondition extends Parcelable {
    String  condition();

    String  conditionToShow();
}
