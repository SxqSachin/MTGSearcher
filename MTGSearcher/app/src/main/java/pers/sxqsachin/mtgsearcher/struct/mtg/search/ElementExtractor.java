package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import org.jsoup.nodes.Document;

/**
 *
 * ElementExtractor
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public interface ElementExtractor {
    int getCardCount(Document document);
    int getPageCount(int cardCount);
}
