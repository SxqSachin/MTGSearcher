package pers.sxqsachin.mtgsearcher.uidata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import pers.sxqsachin.mtgsearcher.BR;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;

/**
 *
 * UIData_Init
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class UIData_Init extends BaseObservable {

    private MTGCard mInitViewRandomCard;

    public UIData_Init() {
        mInitViewRandomCard = new MTGCard();
    }

    public MTGCard getInitViewRandomCard() {
        return mInitViewRandomCard;
    }

    public void setInitViewRandomCard(MTGCard initViewRandomCard) {
        this.mInitViewRandomCard = initViewRandomCard;
    }
}
