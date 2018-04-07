package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import android.support.annotation.Nullable;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;

import pers.sxqsachin.mtgsearcher.net.LocalConnection;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardConditions;

/**
 *
 * AbstractCardSearcher
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public abstract class AbstractCardSearcher implements CardSearcher {

    final protected static String   SEARCH_URL  =   "http://magiccards.info/query?q=";

    @Nullable
    protected Document  getDocumentWithCondition(CardCondition cardCondition) throws IOException {
        String url = SEARCH_URL + cardCondition.condition();
        System.out.println(url);

        Connection connection = LocalConnection.getLocalConnection(url);

        return connection.get();
    }

    protected CardSearchResult  getBasicSearchResult(Document document) {
        CardSearchResult result = new CardSearchResult();
        result.setCards(null);
        if (isDocumentEmpty(document)) {
            result.setEmpty();
        }

        return result;
    }
//
//    @Nullable
//    protected Document  getDocumentWithPageCondition(int page, CardCondition... conditions) {
//        String url;
//
//        if (hasUrlCondition(conditions)) {
//            url = getUrlConditionUrl(conditions);
//        } else {
//            String condition = makeCondition(conditions);
//            url = SEARCH_URL + condition + "&p=" + page + "&s=issue";
//        }
//
//        Connection connection = LocalConnection.getLocalConnection(url);
//        Document document = null;
//
//        try {
//            document = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return document;
//    }

    protected boolean   isDocumentEmpty(Document document) {
        return document.select("table").size() <= 2;
    }
}
