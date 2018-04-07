package pers.sxqsachin.mtgsearcher;

import org.junit.Test;

import java.net.URLDecoder;

import pers.sxqsachin.mtgsearcher.listener.NetworkEventListener;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardColorCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardConditions;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardInfoCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardNameCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.LocalCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.CardSearchResult;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.SimplyCardSearcher;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 * Test2
 * Created by songxinqi-sachin on 16-7-1.
 */
public class Test2 implements OnCompleteListener<CardSearchResult> {
    @Test
    public void test2() {
        SimplyCardSearcher cardSearcher = new SimplyCardSearcher();
//        CardCondition nameCondition = new CardNameCondition("万", false);
//        CardCondition textCondition = new CardInfoCondition("", "", "");
//        CardCondition colorCondition = new CardColorCondition(true,true,true,true,true,true,true,true);
        CardCondition localCondition = new LocalCondition(true);
        CardCondition cyCondition = new CardInfoCondition("覆诵", "", "");
        CardConditions cardConditions = new CardConditions(cyCondition, localCondition);
        cardSearcher.testSearch(this, new NetworkEventListener() {
            @Override
            public void onSocketTimeOut() {
                LogUtil.systemOut("Time Out");
            }
        }, cardConditions);
    }

    @Override
    public void onComplete(CardSearchResult obj) {
        System.out.println(obj.getCount());
    }
}
