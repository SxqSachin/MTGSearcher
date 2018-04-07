package pers.sxqsachin.mtgsearcher.struct.mtg.card;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import pers.sxqsachin.mtgsearcher.BR;
import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * MTGCard
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class MTGCard extends BaseObservable implements Parcelable {
    private String  mCardIndexInView;

    private String  mCardUrl;

    private String  mImgUrl;
    private int     mImgWidth;
    private int     mImgHeight;

    private String  mCardName;

    private String  mCardRarity;

    private String  mTypeAndCost;

    private String  mDescription;
    private String  mBgDescription;

    private String  mArtist;

    private String  mNotes;
    private String  mBannedOrLegal;

    private boolean mHasOtherPart;
    private String  mOtherPartName;
    private String  mOtherPartUrl;

    /**
     * <Url, Name>
     */

    private String  mCurrentPrintingName;
    private ArrayList<StringPair> mPrintings;
    private String  mCurrentEditionName;
    private ArrayList<StringPair> mEditions;
    private ArrayList<StringPair> mLanguages;

    private MTGCardPrice    mCardPrice;

    public MTGCard() {
        mCardUrl = "";

        mCardIndexInView = "";

        mImgUrl = "";
        mImgHeight = 445;
        mImgWidth = 312;

        mCardName = "";
        mCardRarity = "";
        mTypeAndCost = "";
        mDescription = "";
        mBgDescription = "";
        mArtist = "";

        mNotes = "";
        mBannedOrLegal = "";

        mHasOtherPart = false;
        mOtherPartName = "";
        mOtherPartUrl = "";

        mCurrentPrintingName = "";
        mPrintings = new ArrayList<>();
        mCurrentEditionName = "";
        mEditions = new ArrayList<>();
        mLanguages = new ArrayList<>();

        mCardPrice = new MTGCardPrice();
    }

    @Bindable
    public String getCardUrl() {
        return mCardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.mCardUrl = cardUrl;
        notifyPropertyChanged(BR.cardUrl);
    }

    @Bindable
    public String getCardIndexInView() {
        return mCardIndexInView;
    }

    public void setCardIndexInView(String cardIndexInView) {
        this.mCardIndexInView = cardIndexInView;
        notifyPropertyChanged(BR.cardIndexInView);
    }

    @Bindable
    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.mImgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }

    @Bindable
    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String cardName) {
        this.mCardName = cardName;
        notifyPropertyChanged(BR.cardName);
    }

    @Bindable
    public String getCardRarity() {
        return mCardRarity;
    }

    public void setCardRarity(String cardRarity) {
        this.mCardRarity = cardRarity;
        notifyPropertyChanged(BR.cardRarity);
    }

    @Bindable
    public String getTypeAndCost() {
        return mTypeAndCost;
    }

    public void setTypeAndCost(String typeAndCost) {
        this.mTypeAndCost = typeAndCost;
        notifyPropertyChanged(BR.typeAndCost);
    }

    @Bindable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getBgDescription() {
        return mBgDescription;
    }

    public void setBgDescription(String bgDescription) {
        this.mBgDescription = bgDescription;
        notifyPropertyChanged(BR.bgDescription);
    }

    @Bindable
    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
        notifyPropertyChanged(BR.artist);
    }

    @Bindable
    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
        notifyPropertyChanged(BR.notes);
    }

    @Bindable
    public String getBannedOrLegal() {
        return mBannedOrLegal;
    }

    public void setBannedOrLegal(String bannedOrLegal) {
        mBannedOrLegal = bannedOrLegal;
        notifyPropertyChanged(BR.bannedOrLegal);
    }

    public boolean hasOtherPart() {
        return mHasOtherPart;
    }

    public void setHasOtherPart(boolean hasOtherPart) {
        this.mHasOtherPart = hasOtherPart;
    }

    @Bindable
    public String getOtherPartName() {
        return mOtherPartName;
    }

    public void setOtherPartName(String otherPartName) {
        this.mOtherPartName = otherPartName;
        notifyPropertyChanged(BR.otherPartName);
    }

    @Bindable
    public String getOtherPartUrl() {
        return mOtherPartUrl;
    }

    public void setOtherPartUrl(String otherPartUrl) {
        this.mOtherPartUrl = otherPartUrl;
        notifyPropertyChanged(BR.otherPartUrl);
    }

    @Bindable
    public String getCurrentPrintingName() {
        return mCurrentPrintingName;
    }

    public void setCurrentPrintingName(String currentPrintingName) {
        this.mCurrentPrintingName = currentPrintingName;
        notifyPropertyChanged(BR.currentPrintingName);
    }

    @Bindable
    public ArrayList<StringPair> getPrintings() {
        return mPrintings;
    }

    public void setPrintings(ArrayList<StringPair> printings) {
        mPrintings.clear();
        mPrintings.addAll(printings);
    }

    @Bindable
    public String getCurrentEditionName() {
        return mCurrentEditionName;
    }

    public void setCurrentEditionName(String currentEditionName) {
        this.mCurrentEditionName = currentEditionName;
        notifyPropertyChanged(BR.currentEditionName);
    }

    @Bindable
    public ArrayList<StringPair> getEditions() {
        return mEditions;
    }

    public void setEditions(ArrayList<StringPair> editions) {
        mEditions.clear();
        mEditions.addAll(editions);
    }

    @Bindable
    public ArrayList<StringPair> getLanguages() {
        return mLanguages;
    }

    public void setLanguages(ArrayList<StringPair> languages) {
        mLanguages.clear();
        mLanguages.addAll(languages);
    }

    @Bindable
    public MTGCardPrice getCardPrice() {
        return mCardPrice;
    }

    public void setCardPrice(MTGCardPrice cardPrice) {
        this.mCardPrice = cardPrice;
        notifyPropertyChanged(BR.cardPrice);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCardIndexInView);
        dest.writeString(this.mCardUrl);
        dest.writeString(this.mImgUrl);
        dest.writeInt(this.mImgWidth);
        dest.writeInt(this.mImgHeight);
        dest.writeString(this.mCardName);
        dest.writeString(this.mCardRarity);
        dest.writeString(this.mTypeAndCost);
        dest.writeString(this.mDescription);
        dest.writeString(this.mBgDescription);
        dest.writeString(this.mArtist);
        dest.writeString(this.mNotes);
        dest.writeString(this.mBannedOrLegal);
        dest.writeByte(this.mHasOtherPart ? (byte) 1 : (byte) 0);
        dest.writeString(this.mOtherPartName);
        dest.writeString(this.mOtherPartUrl);
        dest.writeString(this.mCurrentPrintingName);
        dest.writeTypedList(this.mPrintings);
        dest.writeString(this.mCurrentEditionName);
        dest.writeTypedList(this.mEditions);
        dest.writeTypedList(this.mLanguages);
        dest.writeParcelable(this.mCardPrice, flags);
    }

    protected MTGCard(Parcel in) {
        this.mCardIndexInView = in.readString();
        this.mCardUrl = in.readString();
        this.mImgUrl = in.readString();
        this.mImgWidth = in.readInt();
        this.mImgHeight = in.readInt();
        this.mCardName = in.readString();
        this.mCardRarity = in.readString();
        this.mTypeAndCost = in.readString();
        this.mDescription = in.readString();
        this.mBgDescription = in.readString();
        this.mArtist = in.readString();
        this.mNotes = in.readString();
        this.mBannedOrLegal = in.readString();
        this.mHasOtherPart = in.readByte() != 0;
        this.mOtherPartName = in.readString();
        this.mOtherPartUrl = in.readString();
        this.mCurrentPrintingName = in.readString();
        this.mPrintings = in.createTypedArrayList(StringPair.CREATOR);
        this.mCurrentEditionName = in.readString();
        this.mEditions = in.createTypedArrayList(StringPair.CREATOR);
        this.mLanguages = in.createTypedArrayList(StringPair.CREATOR);
        this.mCardPrice = in.readParcelable(MTGCardPrice.class.getClassLoader());
    }

    public static final Creator<MTGCard> CREATOR = new Creator<MTGCard>() {
        @Override
        public MTGCard createFromParcel(Parcel source) {
            return new MTGCard(source);
        }

        @Override
        public MTGCard[] newArray(int size) {
            return new MTGCard[size];
        }
    };
}
