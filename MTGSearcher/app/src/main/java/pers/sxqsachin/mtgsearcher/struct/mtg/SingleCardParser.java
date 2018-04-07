package pers.sxqsachin.mtgsearcher.struct.mtg;

import org.jsoup.nodes.Document;

import java.io.IOException;

import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.LocalConnection;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;

/**
 *
 * SingleCardParser
 *
 * Created by songxinqi-sachin on 16-7-11.
 */
public class SingleCardParser {
    public SingleCardParser() {
    }

    public MTGCard parse(String url) {
        Document document = null;
        try {
            document = LocalConnection.getLocalConnection(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null == document) {
            throw new RuntimeException("Get document error : " + url);
        }

        return new CardElementParser().parse(document.select("table[style]").first());
    }

    public void parse(final String url, final OnCompleteListener<MTGCard> listener) {
        if (url.isEmpty() || listener == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = LocalConnection.getLocalConnection(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (null == document) {
                    throw new RuntimeException("Get document error : " + url);
                }

                MTGCard card = new CardElementParser().parse(document.select("table[style]").first());

                listener.onComplete(card);
            }
        }).start();
    }
}
