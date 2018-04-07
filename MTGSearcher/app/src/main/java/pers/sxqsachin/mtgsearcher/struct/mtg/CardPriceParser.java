package pers.sxqsachin.mtgsearcher.struct.mtg;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.HtmlReader;
import pers.sxqsachin.mtgsearcher.net.LocalConnection;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCardPrice;
import pers.sxqsachin.mtgsearcher.util.LogUtil;
import pers.sxqsachin.mtgsearcher.util.StringUtil;

/**
 *
 * CardPriceParser
 *
 * Created by songxinqi-sachin on 16-7-8.
 */
public class CardPriceParser {
    public CardPriceParser() {
    }

    public void parse(final MTGCard card, final OnCompleteListener<MTGCardPrice> listener) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            boolean first = false;
            int timeout = 3820;
            int curt = 0;
            boolean complete = false;
            @Override
            public void run() {
                if (curt > timeout && !complete) {
                    if (null != listener) {
                        MTGCardPrice cardPrice = new MTGCardPrice();
                        cardPrice.setLowPrice("$:NA");
                        cardPrice.setMidPrice("$:NA");
                        cardPrice.setHighPrice("$:NA");
                        listener.onComplete(cardPrice);
                    }

                    cancel();
                    timer.cancel();
                }

                if (!first) {
                    first = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            pparse(card, new OnCompleteListener<MTGCardPrice>() {
                                @Override
                                public void onComplete(MTGCardPrice obj) {
                                    complete = true;
                                    if (null != listener) {
                                        listener.onComplete(obj);
                                        cancel();
                                        timer.cancel();
                                    }
                                }
                            });
                        }
                    }).start();
                }

                curt += 10;
            }
        }, 0, 10);
    }

    protected void pparse(final MTGCard card, final OnCompleteListener<MTGCardPrice> listener) {
        MTGCardPrice cardPrice = new MTGCardPrice();
        try {
            String url = card.getCardUrl();
            Connection connection = LocalConnection.getLocalConnection(url, 2500);
            Document document = connection.get();

            if (document.select("script").size() <= 2) {
                if (listener != null) {
                    cardPrice.setLowPrice("$:NA");
                    cardPrice.setMidPrice("$:NA");
                    cardPrice.setHighPrice("$:NA");
                    listener.onComplete(cardPrice);
                }
                return;
            }

            String html = document.select("script").get(2).attr("src");
            String str = HtmlReader.read(html);

            int lowi = str.indexOf("$");
            String low = "L: " + parsePrice(str, lowi);

            int midi = str.indexOf("$", lowi+1);
            String mid = "M: " + parsePrice(str, midi);

            int hii = str.indexOf("$", midi+1);
            String hi = "H: " + parsePrice(str, hii);

            cardPrice.setLowPrice(low);
            cardPrice.setMidPrice(mid);
            cardPrice.setHighPrice(hi);

            listener.onComplete(cardPrice);

        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                if (null != listener) {
                    cardPrice.setLowPrice("Get price error");
                    listener.onComplete(cardPrice);
                }
            }
        }
    }

    protected String parsePrice(String str, int start) {
        String price = "null";
        int i = 0;
        String sub = "null";

        while (!sub.equals("<")) {
            if (i>10) {
                return "NA";
            }
            try {
                price = str.substring(start, start + ++i);
                sub = price.substring(price.length() - 1, price.length());
            } catch (StringIndexOutOfBoundsException e) {
                return "NA";
            }
        }

        return price.substring(0, price.length()-1);
    }
}
