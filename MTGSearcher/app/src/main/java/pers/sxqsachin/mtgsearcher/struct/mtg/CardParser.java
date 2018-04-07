package pers.sxqsachin.mtgsearcher.struct.mtg;

import android.text.Html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Vector;

import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * CardParser
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class CardParser {

    public CardParser() {
    }

    public Vector<MTGCard> parse(Elements cardsElements) {
        Vector<MTGCard> cards = new Vector<>();

        int i = 0;
        for (Element cardElem : cardsElements) {
            i++;

            MTGCard card = new CardElementParser().parse(cardElem);

            cards.add(card);
        }

        return cards;
    }

}
