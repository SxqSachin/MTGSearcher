package pers.sxqsachin.mtgsearcher.uidata;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import pers.sxqsachin.mtgsearcher.BR;
import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.struct.mtg.ManaColor;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardColorCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardConditions;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCostCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardInfoCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardNameCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardPowAndTouCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.LocalCondition;
import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * UIData_Search
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class UIData_Search extends BaseObservable {

    private String  mKeyword;

    private String  mCardText;
    private String  mCardFlavor;
    private String  mCardArtist;

    private boolean mIsMatchTheFullName;
    private boolean mUseLocalLanguage;

    private boolean mColorState[];
    private boolean mColorMustMutiColorState;
    private boolean mColorNoChoosenColorState;

    private String  mManaCost;
    private String  mPow;
    private boolean mIsPowEqualTou;
    private String  mTou;
    private boolean mIsTouEqualPow;
    private String  mTotalManaCost;

    private boolean mIsPowEnable;
    private boolean mIsTouEnable;

    private String  mConditionToShow;

    public UIData_Search() {
        mKeyword = "";
        mCardText = "";
        mCardFlavor = "";
        mCardArtist = "";
        mManaCost = "";
        mPow = "";
        mTou = "";
        mTotalManaCost = "";

        mIsPowEnable = true;
        mIsTouEnable = true;

        mIsMatchTheFullName = false;
        mUseLocalLanguage = false;

        mColorState = new boolean[ManaColor.values().length];

        mColorMustMutiColorState = false;
        mColorNoChoosenColorState = true;

        mConditionToShow = "";
    }

    public String   getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public String getCardText() {
        return mCardText;
    }

    public void setCardText(String cardText) {
        this.mCardText = cardText;
    }

    public String getCardFlavor() {
        return mCardFlavor;
    }

    public void setCardFlavor(String cardFlavor) {
        this.mCardFlavor = cardFlavor;
    }

    public String getCardArtist() {
        return mCardArtist;
    }

    public void setCardArtist(String cardArtist) {
        this.mCardArtist = cardArtist;
    }

    @Bindable
    public boolean isMatchTheFullName() {
        return mIsMatchTheFullName;
    }

    public void setMatchTheFullName(boolean matchTheFullName) {
        mIsMatchTheFullName = matchTheFullName;
    }

    @Bindable
    public boolean isUseLocalLanguage() {
        return mUseLocalLanguage;
    }

    public void setUseLocalLanguage(boolean useLocalLanguage) {
        mUseLocalLanguage = useLocalLanguage;
    }

    public boolean getColorState(ManaColor color) {
        return mColorState[color.ordinal()];
    }

    public void setColorState(ManaColor color, boolean state) {
        mColorState[color.ordinal()] = state;
    }

    public boolean getColorMustMutiColorState() {
        return mColorMustMutiColorState;
    }

    public void setColorMustMutiColorState(boolean colorMustMutiColorState) {
        this.mColorMustMutiColorState = colorMustMutiColorState;
    }

    @Bindable
    public boolean getColorNoChoosenColorState() {
        return mColorNoChoosenColorState;
    }

    public void setColorNoChoosenColorState(boolean colorNoChoosenColorState) {
        this.mColorNoChoosenColorState = colorNoChoosenColorState;
    }

    public String getManaCost() {
        return mManaCost;
    }

    public void setManaCost(String manaCost) {
        this.mManaCost = manaCost;
    }

    @Bindable
    public String getPow() {
        return mPow;
    }

    public void setPow(String pow) {
        this.mPow = pow;
        notifyPropertyChanged(BR.pow);
    }

    public boolean isPowEqualTou() {
        return mIsPowEqualTou;
    }

    public void setPowEqualTou(boolean powEqualTou) {
        this.mIsPowEqualTou = powEqualTou;
    }

    @Bindable
    public String getTou() {
        return mTou;
    }

    public void setTou(String tou) {
        this.mTou = tou;
        notifyPropertyChanged(BR.tou);
    }

    public boolean isTouEqualPow() {
        return mIsTouEqualPow;
    }

    public void setTouEqualPow(boolean touEqualPow) {
        this.mIsTouEqualPow = touEqualPow;
    }

    public String getTotalManaCost() {
        return mTotalManaCost;
    }

    public void setTotalManaCost(String totalManaCost) {
        this.mTotalManaCost = totalManaCost;
    }

    @Bindable
    public boolean isPowEnable() {
        return mIsPowEnable;
    }

    public void setPowEnable(boolean enable) {
        mIsPowEnable = enable;
        notifyPropertyChanged(BR.powEnable);
    }

    @Bindable
    public boolean isTouEnable() {
        return mIsTouEnable;
    }

    public void setTouEnable(boolean enable) {
        mIsTouEnable = enable;
        notifyPropertyChanged(BR.touEnable);
    }

    public CardCondition toCondition() {
        return new CardConditions(
                toLocalCondition(),
                toCardNameCondition(),
                toCardInfoCondition(),
                toCardCostCondition(),
                toCardPowAndTouCondition(),
                toCardColorCondition());
    }

    private CardCondition toLocalCondition() {
        return new LocalCondition(isUseLocalLanguage());
    }

    private CardCondition toCardNameCondition() {
        return new CardNameCondition(getKeyword(), isMatchTheFullName());
    }

    private CardCondition toCardInfoCondition() {
        return new CardInfoCondition(getCardText(), getCardFlavor(), getCardArtist());
    }

    private CardCondition toCardCostCondition() {
        return new CardCostCondition(getManaCost(), getTotalManaCost());
    }

    private CardCondition toCardPowAndTouCondition() {
        return new CardPowAndTouCondition(getPow(), isPowEqualTou(), getTou(), isTouEqualPow());
    }

    private CardCondition toCardColorCondition() {
        return new CardColorCondition(getColorState(ManaColor.WHITE),
                getColorState(ManaColor.BLUE),
                getColorState(ManaColor.BLACK),
                getColorState(ManaColor.RED),
                getColorState(ManaColor.GREEN),
                getColorState(ManaColor.COLORLESS),
                getColorMustMutiColorState(),
                getColorNoChoosenColorState());
    }


    public void onClick(View v) {
        int id = v.getId();

        AppCompatCheckBox cb = null;
        if (v instanceof AppCompatCheckBox) {
            cb = (AppCompatCheckBox) v;
            switch (id) {
                case R.id.cb_match_the_full_name:
                    setMatchTheFullName(cb.isChecked());
                    break;
                case R.id.cb_local_language:
                    setUseLocalLanguage(cb.isChecked());
                    break;
                case R.id.cb_color_white:
                    setColorState(ManaColor.WHITE, cb.isChecked());
                    break;
                case R.id.cb_color_blue:
                    setColorState(ManaColor.BLUE, cb.isChecked());
                    break;
                case R.id.cb_color_black:
                    setColorState(ManaColor.BLACK, cb.isChecked());
                    break;
                case R.id.cb_color_red:
                    setColorState(ManaColor.RED, cb.isChecked());
                    break;
                case R.id.cb_color_green:
                    setColorState(ManaColor.GREEN, cb.isChecked());
                    break;
                case R.id.cb_color_colorless:
                    setColorState(ManaColor.COLORLESS, cb.isChecked());
                    break;
                case R.id.cb_color_must_muticolor:
                    setColorMustMutiColorState(cb.isChecked());
                    break;
                case R.id.cb_not_include_color_which_not_be_choosen:
                    setColorNoChoosenColorState(cb.isChecked());
                    break;
                case R.id.cb_pow_equal_tou:
                    setPowEqualTou(cb.isChecked());
                    setPow("");
                    setPowEnable(!cb.isChecked());
                    break;
                case R.id.cb_tou_equal_pow:
                    setTouEqualPow(cb.isChecked());
                    setTou("");
                    setTouEnable(!cb.isChecked());
                    break;
            }
        }
    }

    public final TextWatcher mKeywordTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mKeyword = s.toString();
        }
    };
    public final TextWatcher mCardTextTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mCardText = s.toString();
        }
    };
    public final TextWatcher mCardFlavorTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mCardFlavor = s.toString();
        }
    };
    public final TextWatcher mCardArtistTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mCardArtist = s.toString();
        }
    };
    public final TextWatcher mCardCostTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mManaCost = s.toString();
        }
    };
    public final TextWatcher mCardPowTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mPow = s.toString();
        }
    };
    public final TextWatcher mCardTouTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mTou = s.toString();
        }
    };
    public final TextWatcher mCardTotalCostTextWatcher = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mTotalManaCost = s.toString();
        }
    };

    abstract class AbstractTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
