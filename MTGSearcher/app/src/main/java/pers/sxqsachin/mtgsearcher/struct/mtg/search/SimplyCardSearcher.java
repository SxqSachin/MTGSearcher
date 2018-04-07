package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import pers.sxqsachin.mtgsearcher.LocalConfigure;
import pers.sxqsachin.mtgsearcher.listener.NetworkEventListener;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.struct.mtg.CardParser;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardConditions;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.PageCondition;

/**
 *
 * SimplyCardSearcher
 *
 * Created by songxinqi-sachin on 16-7-2.
 */
public class SimplyCardSearcher extends AbstractCardSearcher {

    private int mCurrentPage;
    private Semaphore   mSemaphore;

    public SimplyCardSearcher() {
        mCurrentPage = 1;
        mSemaphore = new Semaphore(1);
    }

    @Override
    public void search(final OnCompleteListener<CardSearchResult> listener, final NetworkEventListener networkEventListener, final CardCondition cardCondition) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Document document = null;
                try {
                    document = getDocumentWithCondition(new CardConditions(cardCondition, new PageCondition(mCurrentPage)));
                } catch (IOException e) {
                    e.printStackTrace();
                    if (e instanceof SocketTimeoutException) {
                        if (networkEventListener != null) {
                            networkEventListener.onSocketTimeOut();
                        }
                    }
                }

                if (document == null) {
                    if (networkEventListener != null) {
                        networkEventListener.onSocketTimeOut();
                    }
                    return;
                }

                CardSearchResult result = getBasicSearchResult(document);
                if (isDocumentEmpty(document)) {
                    if (listener != null) {
                        listener.onComplete(result);
                        return;
                    }
                }

                ElementExtractor extractor = LocalConfigure.getLocalElementExtractor();
                int cardCount = extractor.getCardCount(document);
                int pageCount = extractor.getPageCount(cardCount);
                result.setCount(cardCount);
                result.setMaxPage(pageCount);
                result.setCurrentPage(mCurrentPage);

                CardParser cardParser = new CardParser();
                Vector<MTGCard> cards = cardParser.parse(document.select("table[style]"));
                result.setCards(cards);

                if (listener != null) {
                    listener.onComplete(result);
                }

                mCurrentPage++;
                mSemaphore.release();
            }
        });
        thread.start();
    }


    public void testSearch(final OnCompleteListener<CardSearchResult> listener, final NetworkEventListener networkEventListener, final CardCondition cardCondition) {
        try {
            mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = getDocumentWithCondition(new CardConditions(cardCondition, new PageCondition(mCurrentPage)));
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                if (networkEventListener != null) {
                    networkEventListener.onSocketTimeOut();
                }
            }
        }

        if (document == null) {
            if (networkEventListener != null) {
                networkEventListener.onSocketTimeOut();
            }
            return;
        }

        CardSearchResult result = getBasicSearchResult(document);
        if (isDocumentEmpty(document)) {
            if (listener != null) {
                listener.onComplete(result);
                return;
            }
        }

        ElementExtractor extractor = LocalConfigure.getLocalElementExtractor();
        int cardCount = extractor.getCardCount(document);
        int pageCount = extractor.getPageCount(cardCount);
        result.setCount(cardCount);
        result.setMaxPage(pageCount);
        result.setCurrentPage(mCurrentPage);

        CardParser cardParser = new CardParser();
        Vector<MTGCard> cards = cardParser.parse(document.select("table[style]"));
        result.setCards(cards);

        if (listener != null) {
            listener.onComplete(result);
        }

        mCurrentPage++;
        mSemaphore.release();
    }
}
//
//    public void testSearch(final OnCompleteListener<CardSearchResult> listener, CardCondition cardCondition) {
//        Document document = getDocumentWithCondition(new CardConditions(cardCondition, new PageCondition(mCurrentPage)));
//
//        if (null == document) {
//            throw new RuntimeException("Document get error");
//        }
//
//        CardSearchResult result = getBasicSearchResult(document);
//        if (isDocumentEmpty(document)) {
//            listener.onComplete(result);
//            return;
//        }
//
//        ElementExtractor extractor = LocalConfigure.getLocalElementExtractor();
//        int cardCount = extractor.getCardCount(document);
//        int pageCount = extractor.getPageCount(cardCount);
//        result.setCount(cardCount);
//        result.setMaxPage(pageCount);
//        result.setCurrentPage(mCurrentPage);
//
//        CardParser cardParser = new CardParser();
//        Vector<MTGCard> cards = cardParser.parse(document.select("table[style]"));
//        result.setCards(cards);
//
//        listener.onComplete(result);
//
//        mCurrentPage++;
//    }
