package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import org.jsoup.nodes.Document;

import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * CNElementExtractor
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public class CNElementExtractor implements ElementExtractor {
    @Override
    public int getCardCount(Document document) {
        String cardCountText = document.select("table").get(2).select("td").get(2).text();
        if (cardCountText.substring(cardCountText.length()-1, cardCountText.length()).equals("→")) {
            return 1;
        }
        return Integer.valueOf(cardCountText.substring(0, cardCountText.length()-2));
    }

    @Override
    public int getPageCount(int cardCount) {
        return (int) (Math.ceil(cardCount)/(double)20)+1;
    }
}
