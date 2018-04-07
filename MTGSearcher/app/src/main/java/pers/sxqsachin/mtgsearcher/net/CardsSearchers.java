//package pers.sxqsachin.mtgsearcher.net;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.Vector;
//
//import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
//import pers.sxqsachin.mtgsearcher.struct.mtg.CardParser;
//import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
//import pers.sxqsachin.mtgsearcher.struct.mtg.search.CardSearchResult;
//import pers.sxqsachin.mtgsearcher.util.LogUtil;
//
///**
// *
// * CardsSearchers
// *
// * Created by songxinqi-sachin on 16-6-30.
// */
//public class CardsSearchers {
//    private CardCondition mFilter;
//
//    private int                 mCardsCount;
//
//    private int                 mPage;
//    private int                 mMaxPage;
//
//    private onCardSearchCompleteListener    mListener;
//
//    private boolean             mNoMore;
//    private boolean             mLock;
//
//    private CardSearchResult    mResult;
//
//    public CardsSearchers(onCardSearchCompleteListener listener) {
//        mFilter = null;
//
//        mCardsCount = 0;
//
//        mPage = 1;
//        mMaxPage = 1;
//
//        mListener = listener;
//
//        mNoMore = true;
//        mLock = false;
//
//        mResult = new CardSearchResult();
//    }
//
//    public void search() {
//    }
//
//    public void continueSearch() {
//        pSearch();
//    }
//
//    private void    perSearch() {
//
//    }
//
//    private void pSearch() {
//        if (mLock) {
//            return;
//        }
//
//        mLock = true;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (mNoMore) {
//                    mLock = false;
//                    return;
//                }
//
//                String url = "http://magiccards.info/query?q=" + mFilter.condition() + "&s=issue" + "&p="+mPage;
//
//                Connection connection = Jsoup.connect(url);
//                try {
//                    Document document = connection.get();
//                    String pageCountText = document.select("table").get(2).select("td").get(2).text();
//                    mCardsCount = Integer.valueOf(pageCountText.substring(0, pageCountText.length()-6));
//                    mMaxPage = (int) (Math.ceil(mCardsCount)/(double)20)+1;
//
//                    LogUtil.debugOut(pageCountText + "/" + mCardsCount + "/"  + mMaxPage+"");
//
//                    Elements elements = document.select("table[style]");
//
//                    CardParser cardParser = new CardParser();
//                    Vector<MTGCard> cards = cardParser.parse(elements);
//
//                    mListener.onComplete(cards);
//
//                    LogUtil.debugOut("as:" + elements.size());
//                    if (elements.size() < 20) {
//                        mNoMore = true;
//                        LogUtil.debugOut("end");
//                    } else {
//                        mPage++;
//                    }
//
//                    mLock = false;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public boolean  isNoMore() {
//        return mNoMore;
//    }
//
//    public interface onCardSearchCompleteListener {
//        void    onSearchComplete(CardSearchResult result, Vector<MTGCard> cards);
//    }
//}
