package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import java.util.Vector;

import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;

/**
 *
 * CardSearchResult
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public class CardSearchResult {
    private int     mCount;
    private int     mMaxPage;
    private int     mCurrentPage;
    private Vector<MTGCard> mCards;

    public CardSearchResult() {
        mCount = 0;
        mMaxPage = 0;
        mCurrentPage = 0;
        mCards = new Vector<>();
    }

    public boolean  isEmpty() {
        return mCount == 0 ||
                mMaxPage == 0;
    }

    public void setEmpty() {
        setCount(0);
        setMaxPage(0);
        setCurrentPage(0);
        setCards(null);
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public int getMaxPage() {
        return mMaxPage;
    }

    public void setMaxPage(int maxPage) {
        this.mMaxPage = maxPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    public Vector<MTGCard>  getCards() {
        return mCards;
    }

    public void setCards(Vector<MTGCard> cards) {
        mCards.clear();
        if (null != cards && !cards.isEmpty()) {
            mCards.addAll(cards);
        }
    }
}
