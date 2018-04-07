package pers.sxqsachin.mtgsearcher.struct.mtg.search;

import pers.sxqsachin.mtgsearcher.listener.NetworkEventListener;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;

/**
 *
 * CardSearcher
 *
 * Created by songxinqi-sachin on 16-7-1.
 */
public interface CardSearcher {
    void    search(OnCompleteListener<CardSearchResult> listener, NetworkEventListener networkEventListener, CardCondition cardCondition);
}
