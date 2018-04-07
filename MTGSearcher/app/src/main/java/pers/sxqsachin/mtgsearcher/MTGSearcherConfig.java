package pers.sxqsachin.mtgsearcher;

/**
 *
 * MTGSearcherConfig
 *
 * Created by songxinqi-sachin on 16-7-2.
 */
public class MTGSearcherConfig {
    private String  mLangurage;

    private MTGSearcherConfig() {
        mLangurage = "";
    }

    public static MTGSearcherConfig getInstance() {
        return InstanceHolder.mInstance;
    }

    private static class InstanceHolder {
        private static MTGSearcherConfig mInstance = new MTGSearcherConfig();
    }
}
