package pers.sxqsachin.mtgsearcher.struct.mtg;

import android.support.annotation.Nullable;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardElementParser
 *
 * Created by songxinqi-sachin on 16-7-11.
 */
public class CardElementParser {

    public static final String HEAD = "http://magiccards.info";

    public CardElementParser() {
    }

    public MTGCard  parse(Element cardElem) {
        MTGCard mtgCard = new MTGCard();

        CharSequence imgUrl = cardElem.select("img").first().attr("src");
        CharSequence cardName = cardElem.select("a[href]").first().text();
        CharSequence cardUrl = HEAD + cardElem.select("a[href]").attr("href");
        CharSequence typeAndCost = cardElem.select("p").first().text();
        CharSequence description = cardElem.select("b").first().text();
        CharSequence bgDescription = cardElem.select("i").first().text();
        int plen = cardElem.select("td").get(1).select("p").size();
        CharSequence artist = cardElem.select("p").get(plen == 5 ? 3 : 4).text();
        CharSequence notes = "";
        CharSequence banOrLegals;

        Elements uls = cardElem.select("ul");
        switch (uls.size()) {
            case 1:
                banOrLegals = getBanOrLegal(uls.first().children());
                break;
            case 2:
                notes = getNotes(uls.first().select("li"));
                banOrLegals = getBanOrLegal(uls.get(1).children());
                break;
            case 3:
                notes = getNotes(uls.first().select("li"));
                banOrLegals = getBanOrLegal(uls.get(2).children());
                break;
            default:
                throw new RuntimeException("Another ul tag At : " + cardElem.text());
        }

        mtgCard.setCardUrl(cardUrl.toString());
        mtgCard.setImgUrl(imgUrl.toString());
        mtgCard.setCardName(cardName.toString());
        mtgCard.setTypeAndCost(typeAndCost.toString());
        mtgCard.setDescription(description.toString());
        mtgCard.setBgDescription(bgDescription.toString());
        mtgCard.setArtist(artist.toString());
        mtgCard.setNotes(notes.toString());
        mtgCard.setBannedOrLegal(banOrLegals.toString());

        parseThirdTable(cardElem, mtgCard);

        return mtgCard;
    }

    private String    getBanOrLegal(Elements banOrLegalElems) {
//        StringBuilder sb = new StringBuilder();
//
//        for (Element banOrLegalElem : banOrLegalElems) {
//            sb.append(banOrLegalElem.text());
//            sb.append("\n");
//        }

        String[] strings = new String[banOrLegalElems.size()];
        for (int i = 0; i < banOrLegalElems.size(); i++) {
            strings[i] = banOrLegalElems.get(i).text();
        }

        return StringUtil.linkNewLine(strings);
    }

    private String  getNotes(Elements notesElems) {
        String[] strings = new String[notesElems.size()];
        for (int i = 0; i < notesElems.size(); i++) {
            strings[i] = notesElems.get(i).text();
        }

        return StringUtil.linkNewLine(strings);
    }

    private void parseThirdTable(Element element, MTGCard card) {
        Elements smalls = element.select("small");
        Element small;
        if (smalls.size() > 1) {
            small = smalls.get(1);
        } else {
            small = smalls.first();
        }

        parseOtherPart(small, card);
        parsePEL(small, card);
//
//        System.out.println(card.getCardName());
//        if (card.hasOtherPart()) {
//            System.out.println("Other Part : " + card.getOtherPartName() + " " + card.getOtherPartUrl());
//        }
//
//        System.out.println("Printings");
//        for (StringPair printing : card.getPrintings()) {
//            System.out.println(printing.first + " " + printing.second);
//        }
//
//        System.out.println("Editions");
//        for (StringPair edition: card.getEditions()) {
//            System.out.println(edition.first + " " + edition.second);
//        }
    }

    protected void  parsePEL(Element small, MTGCard card) {
        int ucount = 0;
        boolean printingsStart = false;
        boolean editionsStart = false;
        boolean languagesStart = false;

        int printingsUStartCount = hasOtherPart(small) ? 2 : 1;
        int printingsUEndCount = printingsUStartCount + 1;

        int editionsUEndCount = printingsUEndCount + 1;

        int languagesUEndCount = editionsUEndCount + 1;

        ArrayList<StringPair> printings = new ArrayList<>();
        ArrayList<StringPair> editions = new ArrayList<>();
        ArrayList<StringPair> languages = new ArrayList<>();

        int line = 0;
        for (Element element : small.getAllElements()) {
            String tag = element.tagName();
            if (tag.equals("u")) {
                ++ucount;
            }

            if (ucount == printingsUStartCount && !printingsStart) {
                printingsStart = true;
            }
            if (ucount == printingsUEndCount && printingsStart) {
                printingsStart = false;
            }

            if (ucount == printingsUEndCount && !editionsStart) {
                editionsStart = true;
            }
            if (ucount == editionsUEndCount && editionsStart) {
                editionsStart = false;
            }

            if (ucount == editionsUEndCount && !languagesStart) {
                languagesStart = true;
            }
            if (ucount == languagesUEndCount && languagesStart) {
                languagesStart = false;
            }

            if (printingsStart) {
                StringPair printing = parsePair(element);

                if (null != printing) {
                    printings.add(printing);
                }
            }

            if (editionsStart) {
                StringPair edition = parsePair(element);

                if (null != edition) {
                    editions.add(edition);
                }
            }

            if (languagesStart) {
                StringPair language = parsePair(element);

                if (null != language) {
                    languages.add(language);
                }
            }

            ++line;
        }

        if (printings.size() > 0) {
            printings.remove(0);
        } else {
            throw new RuntimeException("Printings' size is 0");
        }

        if (editions.size() > 0) {
            editions.remove(0);
        } else {
            throw new RuntimeException("Editions' size is 0");
        }

        if (languages.size() > 0) {
            languages.remove(0);
            languages.remove(languages.size()-1);
        } else {
            throw new RuntimeException("Language' size is 0");
        }

        for (StringPair printing : printings) {
            if (printing.first.isEmpty()) {
                String currentPrinting = printing.second;

                card.setCurrentPrintingName(currentPrinting);
                break;
            }
        }

        for (StringPair edition : editions) {
            if (edition.first.isEmpty()) {
                String currentEdition = edition.second;
                int lci = currentEdition.indexOf("(");

                String edit = currentEdition.substring(0, lci-1);
                String rarity = currentEdition.substring(lci+1, currentEdition.length()-1);

                card.setCurrentEditionName(edit);
                card.setCardRarity(rarity);
                break;
            }
        }

        card.setPrintings(printings);
        card.setEditions(editions);
        card.setLanguages(languages);
    }

    @Nullable
    protected StringPair parsePair(Element element) {
        String tag = element.tagName();
        String pairUrl = "";
        String pairName;
        StringPair pair = null;

        if (tag.equals("b")) {
            pairName = element.text();

            pair = new StringPair(pairUrl, pairName);
        }
        if (tag.equals("a")) {
            pairUrl = HEAD + element.attr("href");
            pairName = element.text();

            pair = new StringPair(pairUrl, pairName);
        }

        return pair;
    }

    protected boolean   hasOtherPart(Element small) {
        return small.select("u").size() == 4;
    }

    protected void parseOtherPart(Element small, MTGCard card) {
        if (!hasOtherPart(small)) {
            card.setHasOtherPart(false);
            return;
        }

        String url = HEAD + small.select("a").first().attr("href");
        String name = small.select("a[href]").first().text();

        card.setHasOtherPart(true);
        card.setOtherPartUrl(url);
        card.setOtherPartName(name);
    }
}
