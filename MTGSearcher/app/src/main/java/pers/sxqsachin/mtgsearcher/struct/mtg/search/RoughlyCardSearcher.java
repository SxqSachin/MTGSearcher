package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import org.jsoup.nodes.Document;

import java.io.IOException;

import pers.sxqsachin.mtgsearcher.LocalConfigure;
import pers.sxqsachin.mtgsearcher.listener.NetworkEventListener;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.LocalConnection;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;

/**
 *
 * RoughlyCardSearcher
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public class RoughlyCardSearcher extends AbstractCardSearcher {
    public RoughlyCardSearcher() {
    }

    @Override
    public void search(final OnCompleteListener<CardSearchResult> listener, NetworkEventListener networkEventListener, final CardCondition cardCondition) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = getDocumentWithCondition(cardCondition);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (null == document) {
                    throw new RuntimeException("Document get error");
                }

                CardSearchResult result = getBasicSearchResult(document);
                if (isDocumentEmpty(document)) {
                    listener.onComplete(result);
                    return;
                }

                ElementExtractor extractor = LocalConfigure.getLocalElementExtractor();
                int cardCount = extractor.getCardCount(document);
                int pageCount = extractor.getPageCount(cardCount);
                result.setCount(cardCount);
                result.setMaxPage(pageCount);
                result.setCurrentPage(1);

                listener.onComplete(result);
            }
        });
        thread.start();
    }
}
