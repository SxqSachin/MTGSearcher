package pers.sxqsachin.mtgsearcher;

import pers.sxqsachin.mtgsearcher.struct.mtg.local.MTGCNText;
import pers.sxqsachin.mtgsearcher.struct.mtg.local.MTGLocalText;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.CNElementExtractor;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.ElementExtractor;

/**
 *
 * LocalConfigure
 *
 * Created by songxinqi-sachin on 16-7-16.
 */
public class LocalConfigure {
    public static String getLocalLangurageSearchParamToShow() {
        return "语言:简体中文";
    }

    public static String getLocalLangurageSearchParam() {
        return "l:cn";
    }

    public static String getLocalLangurageEditionSearchParam() {
        return "/cn";
    }

    public static ElementExtractor getLocalElementExtractor() {
        return new CNElementExtractor();
    }

    public static MTGLocalText  getLocalText() {
        return new MTGCNText();
    }
}
